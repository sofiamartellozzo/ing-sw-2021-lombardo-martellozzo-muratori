package it.polimi.ingsw.controller;

import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.market.MarketStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is a part of the Controller that manages all the actions of the game,
 * so it sends to the client the messages containing the possible ACTIONS
 * the possible actions of a game are BUY FROM MARKET,BUY DEVELOPMENT CARD,ACTIVE LEADER CARD, REMOVE LEADER CARD,MOVE RESOURCE
 *                                    ACTIVE PRODUCTION POWER, SEE OTHER PLAYER, END TURN (when possible)
 * after receiving the client answer, this controller manages all the possible responses
 */
public class ActionController extends Observable implements ControllerObserver {

    public PlayerInterface getPlayer() {
        return player;
    }

    /*the actual player of the turn*/
    private PlayerInterface player;

    private PlayerTurn turn;

    private SoloPlayerTurn soloPlayerTurn;

    private BoardManager boardManager;

    private boolean endAction;

    private boolean isSolo;

    private int numberResourcesFromM;

    /* list of VV of the players*/
    private Map<String, VirtualView> virtualView;

    /**
     * constructor of the class used with Player so in Multi Player Mode
     * @param player to manage
     * @param turn
     * @param boardManager
     * @param virtualView of the players
     */
    public ActionController(Player player, PlayerTurn turn, BoardManager boardManager, Map<String, VirtualView> virtualView) {
        this.player = (Player) player;
        this.turn = turn;
        this.boardManager = boardManager;
        this.virtualView = virtualView;
        attachAllVV();
        this.endAction = false;
        this.isSolo = false;
        this.numberResourcesFromM = 0;
    }

    /**
     * constructor of the class, override the previous one, used in Single Player Mode
     * @param player (SOLO Player) to manage
     * @param turn
     * @param boardManager
     * @param virtualView of the Solo Player
     */
    public ActionController(SoloPlayer player, SoloPlayerTurn turn, BoardManager boardManager, Map<String, VirtualView> virtualView) {
        this.player = (SoloPlayer) player;
        this.soloPlayerTurn = turn;
        this.boardManager = boardManager;
        this.virtualView = virtualView;
        attachAllVV();
        this.endAction = false;
        this.isSolo = true;
        this.numberResourcesFromM = 0;
    }

    /**
     * this method returns a list of playerInterfaces that represents all the players of the game
     * @param players of the game
     * @return -> username of all players
     */
    private List<String> getPlayerAsList(Map<Integer, PlayerInterface> players) {
        List<String> p = new ArrayList<>();
        for (Integer i : players.keySet()) {
            p.add(players.get(i).getUsername());
        }
        return p;
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    private void attachAllVV() {
        for (String username : virtualView.keySet()) {
            attachObserver(ObserverType.VIEW, virtualView.get(username));
        }
    }

    public boolean endAction() {
        return endAction;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/

    /**
     * receive the msg from the client with the Action he choose,
     * the possible actions are: BUY_CARD,MOVE_RESOURCE,BUY_FROM_MARKET,ACTIVE_PRODUCTION_POWER,REMOVE_LEADER_CARD,ACTIVE_LEADER_CARD,SEE_OTHER_PLAYER,END_TURN (not as first action of the turn )
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

        switch (msg.getActionChose()) {
            case BUY_CARD:
                System.out.println("enter in buy card of action controller");
                //I need the input from the real player (person)
                //from boardManager.getAvailable

                //send the Development Card Table to the client inside the msg
                DevelopmentCardTable currentDevTable = null;
                currentDevTable = boardManager.getDevelopmentCardTable();
                boolean[][] matrix = new boolean[4][3]; //this will be 3x4
                matrix = boardManager.getAvailable(player); //only to not have errors right now
                //from boardManager.getAvailable
                VChooseDevelopCardRequestMsg request = new VChooseDevelopCardRequestMsg("You chose to buy a Development Card, select which one: ", player.getUsername(), boardManager.getDevelopmentCardTable(), matrix);
                notifyAllObserver(ObserverType.VIEW, request);
                break;
            case MOVE_RESOURCE:
                /* get the actual situation from the warehouse */
                HashMap<Integer, ArrayList<TypeResource>> situation = player.getGameSpace().getWarehouse().getInstanceContent();
                VMoveResourceRequestMsg request1 = new VMoveResourceRequestMsg("You ask to move your resource from one depot to another, chose which one and where to store his resource:", player.getUsername(), situation);
                notifyAllObserver(ObserverType.VIEW, request1);
                break;
            case BUY_FROM_MARKET:
                //send the Market structure to the Client, sending it throw the net will serialize the object
                MarketStructure currentMarket = null;
                currentMarket = boardManager.getMarketStructure();

                VBuyFromMarketRequestMsg request2 = new VBuyFromMarketRequestMsg("You ask to buy from the Market at this Turn, please choose column or row and which one: ", player.getUsername(), currentMarket);
                notifyAllObserver(ObserverType.VIEW, request2);
                break;
            case ACTIVE_PRODUCTION_POWER:
                ProductionPowerController productionPowerController = null;
                if (player instanceof Player) {
                    productionPowerController = new ProductionPowerController((Player) player, virtualView);
                } else if (player instanceof SoloPlayer) {
                    productionPowerController = new ProductionPowerController((SoloPlayer) player, virtualView);
                }
                attachObserver(ObserverType.CONTROLLER, productionPowerController);
                productionPowerController.start();
                productionPowerController.setBoardManager(boardManager);
                break;
            case REMOVE_LEADER_CARD:
                //ask the player which card he want to remove, before see if there are any that
                //is inactive, otherwise send a msg that this action can't be made
                //player.
                //and remove from the possible action
                System.out.println("choice of removing leader card");
                ArrayList<Integer> possibleCardToBeDiscarded = cardAbleForPlayer();
                VChooseLeaderCardRequestMsg request4 = new VChooseLeaderCardRequestMsg("Because you chose to discard a card, select which one", possibleCardToBeDiscarded, player.getUsername(), "remove");
                notifyAllObserver(ObserverType.VIEW, request4);
                break;
            case ACTIVE_LEADER_CARD:
                //ask the player which card he want to active, before see if there are any that
                //is inactive, otherwise send a msg that this action can't be made
                ArrayList<Integer> possibleCardToBeActive = cardActivatableForPlayer();
                VChooseLeaderCardRequestMsg request5 = new VChooseLeaderCardRequestMsg("Because you chose to activated a card, select which one", possibleCardToBeActive, player.getUsername(), "active");
                notifyAllObserver(ObserverType.VIEW, request5);
                break;
            case SEE_OTHER_PLAYER:
                //ask the player which palyer wants to see
                VWhichPlayerRequestMsg message = new VWhichPlayerRequestMsg("", player.getUsername(), getNotPlaying());
                System.out.println(message);
                notifyAllObserver(ObserverType.VIEW, message);
            case END_TURN:
                this.player.endTurn();
                break;
            default:
                //choose not available
        }

    }

    /**
     * returns an arrayList of integer representing the Leader cards that a player can discard
     * @return -> list of cards (integers) that can be discarded
     */
    private ArrayList<Integer> cardAbleForPlayer() {
        ArrayList<Integer> possibleCardToBeDiscarded = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = this.player.getLeaderCards();
        if (leaderCards != null) {
            //System.out.println("bug2");       DEBUGGING
            for (LeaderCard card : leaderCards) {
                //System.out.println("bug3");           DEBUGGING
                if (card.getState() instanceof Inactive) {
                    //System.out.println(card.getCardID());     DEBUGGING
                    possibleCardToBeDiscarded.add(card.getCardID());
                }
            }
        }
        return possibleCardToBeDiscarded;
    }

    /**
     * returns an arrayList of integers representing the Leader cards that a player can activate, so foreach card
     * we have to control that the player has all the requirements
     * @return -> list of activatable cards
     */
    public ArrayList<Integer> cardActivatableForPlayer() {
        //The result
        ArrayList<Integer> possibleActivatableCards = new ArrayList<>();
        //The player's leader cards
        ArrayList<LeaderCard> leaderCards = this.player.getLeaderCards();
        //The resource manager to check if has enough resources
        ResourceManager resourceManager = this.player.getGameSpace().getResourceManager();
        //All the development card in the personal board
        ArrayList<DevelopmentCard> allDevelopmentCards = (ArrayList<DevelopmentCard>) player.getGameSpace().getAllCards().clone();
        if (leaderCards != null) {
            //For each leader card
            for (LeaderCard card : leaderCards) {
                //Check if inactive
                if (card.getState() instanceof Inactive) {
                    //If true, checking the requirements
                    ArrayList<Object> requirements = card.getRequirements();
                    ArrayList<Resource> requiredResources = new ArrayList<>();
                    //If at the end the counter will be 0, all requirements are satisfied
                    int counter = requirements.size();
                    for (int i = 0; i < requirements.size(); i++) {
                        Object[] keys = ((LinkedTreeMap) requirements.get(i)).keySet().toArray();
                        Object[] values = ((LinkedTreeMap) requirements.get(i)).values().toArray();
                        Color color = null;
                        for (int j = 0; j < keys.length; j++) {
                            String key = (String) keys[j];
                            if (key.equals("color")) {
                                String value = (String) values[j];
                                switch (value) {
                                    case "GREEN":
                                        color = Color.GREEN;
                                        break; //Color -> Card
                                    case "BLUE":
                                        color = Color.BLUE;
                                        break; //Color -> Card/Resource
                                    case "YELLOW":
                                        color = Color.YELLOW;
                                        break;//Color -> Card/Resource
                                    case "PURPLE":
                                        color = Color.PURPLE;
                                        break;//Color -> Card/Resource
                                    case "GREY":
                                        color = Color.GREY;
                                        break;//Color -> Resource
                                }
                            } else if (key.equals("typeResource")) {
                                String value = (String) values[j];
                                //Adding the resource to count at the end
                                switch (value) {
                                    case "SHIELD":
                                        requiredResources.add(new Resource(TypeResource.SHIELD));
                                        break;
                                    case "COIN":
                                        requiredResources.add(new Resource(TypeResource.COIN));
                                        break;
                                    case "STONE":
                                        requiredResources.add(new Resource(TypeResource.STONE));
                                        break;
                                    case "SERVANT":
                                        requiredResources.add(new Resource(TypeResource.SERVANT));
                                        break;
                                }
                            } else if (key.equals("level")) {
                                //The requirements it's a card
                                Double value = (Double) values[j];
                                for (int k = 0; k < allDevelopmentCards.size(); k++) {
                                    DevelopmentCard developmentCard = allDevelopmentCards.get(k);
                                    if ((developmentCard.getColor().equals(color) && ((value == 0) || (value != 0 && developmentCard.getlevel() == value)))) {
                                        allDevelopmentCards.remove(k);
                                        counter--;
                                    }
                                }
                            }
                        }
                    }
                    if (requiredResources.size() > 0 && resourceManager.checkEnoughResources(requiredResources)) {
                        counter = 0;
                    }
                    if (counter == 0) {
                        possibleActivatableCards.add(card.getCardID());
                    }
                }
            }
        }
        return possibleActivatableCards;
    }

    /**
     * returns an array of strings containing all the usernames of the players that aren't playing in that moment
     * @return list of Players' usernames that aren't playing
     */
    private ArrayList<String> getNotPlaying() {
        ArrayList<String> players = new ArrayList<>();
        for (PlayerInterface p : boardManager.getPlayers().values()) {
            //I do not check if is or not in Solo Mode because this action is possible only in Multiplayer mode
            if (!player.getUsername().equals(p.getUsername())) {
                players.add(p.getUsername());
                //System.out.println(players);
            }
        }
        return players;
    }

    /**
     * the response from the client to buy a development card
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

        if (player.getUsername().equals(msg.getUsername())) {
            try {

                player.buyCard(msg.getRow(), msg.getColumn(), boardManager, msg.getCardSpaceToStoreIt());

                VUpdateDevTableMsg update = new VUpdateDevTableMsg("new develop table", player.getUsername(), player.calculateVictoryPoints(), player.getGameSpace().getCardSpaces(), boardManager.getDevelopmentCardTable(), boardManager.getPlayers());
                notifyAllObserver(ObserverType.VIEW, update);
                VUpdateWarehouseMsg secondUpdate = new VUpdateWarehouseMsg("update the warehose", player.getUsername(), player.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, secondUpdate);
                VUpdateStrongboxMsg thirdUpdate = new VUpdateStrongboxMsg("update the strongbox", player.getUsername(), player.getGameSpace().getStrongbox());
                notifyAllObserver(ObserverType.VIEW, thirdUpdate);
                VUpdateCardSpaces fourthUpdate=new VUpdateCardSpaces("update card spaces",player.getUsername(),player.getGameSpace().getCardSpaces());
                notifyAllObserver(ObserverType.VIEW,fourthUpdate);
                //remove tre 3 action from the able ones because can be made only once
                removeAction(msg.getActionChose());
                endAction = true;
                nextAction();

            } catch (InvalidActionException e) {
                System.out.println("Cannot buy this card, sorry!");
                e.printStackTrace();
            } catch (CardSpaceException e) {

                VNotValidCardSpaceMsg notification = new VNotValidCardSpaceMsg("you choose a card space not valid", player.getUsername(), msg.getRow(), msg.getColumn());
                notifyAllObserver(ObserverType.VIEW, notification);
            }
        }
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
      //NOT HERE, implemented in Turn Controller
    }

    /**
     * manage the request of the client to move a resource from a depot to another
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

        if (player.getUsername().equals(msg.getUsername())) {
            try {
                player.moveResource(msg.getFromDepot(), msg.getToDepot());
                System.out.println("the move went successfully!!");
                VUpdateWarehouseMsg notification = new VUpdateWarehouseMsg("The warehouse has changed..", player.getUsername(), player.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, notification);
                System.out.println(notification);

                if (msg.isNextA()) {
                    endAction = true;
                    nextAction();
                }


            } catch (InvalidActionException e) {
                e.printStackTrace();
                System.out.println("Not able to move the resource from " + msg.getFromDepot() + " depot, to " + msg.getToDepot() + " depot!");
            }
        }
    }

    /**
     * manage the request of the client to buy from the market
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

        ArrayList<TypeResource> resourcesFromMarket = null;
        ArrayList<TypeResource> resourcesToStore = new ArrayList<>();
        boolean sent = false;

        if (player.getUsername().equals(msg.getUsername())) {
            try {
                /*get the resources returned buy the market with the choice of the player*/
                resourcesFromMarket = player.buyFromMarket(msg.getWhichRorC(), msg.getRowOrColumn(), boardManager);
                if (!isSolo) {
                    VUpdateMarketMsg update = new VUpdateMarketMsg("the market has changed", msg.getUsername(), boardManager.getMarketStructure(), boardManager.getPlayers());
                    notifyAllObserver(ObserverType.VIEW, update);
                } else {
                    Map<Integer, PlayerInterface> singleP = new HashMap<>();
                    singleP.put(1, soloPlayerTurn.getCurrentPlayer());
                    VUpdateMarketMsg update = new VUpdateMarketMsg("the market has change", msg.getUsername(), boardManager.getMarketStructure(), singleP);
                    notifyAllObserver(ObserverType.VIEW, update);
                }

                for (TypeResource resource : resourcesFromMarket) {
                    if (!resource.equals(TypeResource.FAITHMARKER)) {
                        resourcesToStore.add(resource);
                    }
                }

                ArrayList<TypeResource> whiteSpecialResource = new ArrayList<>();
                int numberWhiteSpecial = 0;
                /*check for each resources returned from the market...*/
                for (TypeResource resource : resourcesFromMarket) {

                    /* BLANK is the special type, set from the white marble if the player has two Special White Marble Ability activated*/


                    if (resource.equals(TypeResource.FAITHMARKER)) {
                        /* the FAITHMARKER is not a real resources, it increased the position of the player in FT*/
                        if (!isSolo) {
                            int section = player.increasePosition();
                            for(int i:this.boardManager.getPlayers().keySet()){
                                this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().setPopeBoxActiveInSection(section);
                            }
                            System.out.println(player.getGameSpace().getFaithTrack().getPositionFaithMarker());
                        } else {
                            soloPlayerTurn.getCurrentPlayer().increasePosition();
                            System.out.println(soloPlayerTurn.getCurrentPlayer().getGameSpace().getFaithTrack().getPositionFaithMarker());
                        }

                        VUpdateFaithTrackMsg notification1 = new VUpdateFaithTrackMsg("because of a red marble, this player increased his position", player.getUsername(), player.getGameSpace().getFaithTrack());
                        VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("because of a red marble, this player increased his position", player.getUsername(), player.calculateVictoryPoints(), 1);
                        Map<Integer, PlayerInterface> players = boardManager.getPlayers();
                        notification.setAllPlayerToNotify(getPlayerAsList(players));
                        notifyAllObserver(ObserverType.VIEW, notification1);
                        System.out.println(notification1.getFaithTrack().getPopesFavorTiles().get(0).getState());
                        notifyAllObserver(ObserverType.VIEW, notification);

                        if (numberResourcesFromM == resourcesToStore.size() && !sent) {
                            VChooseDepotMsg request = new VChooseDepotMsg("Now choose the depots where to store that resources: " + resourcesToStore.toString(), this.player.getUsername(), resourcesToStore);
                            sent = true;
                            notifyAllObserver(ObserverType.VIEW, request);
                        }

                    } else {
                        /* a normal resource*/
                        numberResourcesFromM++;
                        if (resource.equals(TypeResource.BLANK)) {
                            //the player has 2 whiteSpecialMarble
                            numberWhiteSpecial++;
                            if (!isSolo) {
                                whiteSpecialResource = player.getWhiteSpecialResources();
                            } else {
                                whiteSpecialResource = soloPlayerTurn.getCurrentPlayer().getWhiteSpecialResources();
                            }

                        }
                        if (numberResourcesFromM == resourcesToStore.size() && !sent) {
                            VChooseDepotMsg request = new VChooseDepotMsg("Now choose the depots where to store that resources: " + resourcesToStore.toString(), this.player.getUsername(), resourcesToStore);
                            request.setWhiteSpecialResources(whiteSpecialResource);
                            request.setNumberWhiteMarbleSpecial(numberWhiteSpecial);
                            sent = true;
                            notifyAllObserver(ObserverType.VIEW, request);
                        }
                    }
                }


                /*we suppose that the action ended, if one depot is not valid the boolean will turn false and the next action wait*/
                endAction = true;

                /* remove tre 3 action from the able ones because can be made only once */
                removeAction(msg.getActionChose());

                //nextAction();

            } catch (InvalidActionException e) {
                e.printStackTrace();
                System.out.println("Something went wrong in buying from the market...");
            }
        }
    }

    /**
     * this msg is received when the player has finished storing the resources taken from the market
     * @param msg
     */
    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        endAction = true;

        removeAction(msg.getTurnAction());
        nextAction();
    }

    /**
     * manage the choose of the player to activate a production power
     * @param msg
     */
    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        /*notify so send the msg with the info to the right controller*/
        notifyAllObserver(ObserverType.CONTROLLER, msg);
        //remove tre 3 action from the able ones because can be made only once
        removeAction(msg.getActionChose());
        endAction = false;
        //nextAction();
    }


    /**
     * received when a client decides to stop activating production powers
     * @param msg
     */
    @Override
    public void receiveMsg(CStopPPMsg msg) {
        notifyAllObserver(ObserverType.CONTROLLER, msg);
        endAction = true;
        nextAction();

    }

    /**
     * manage the action of the client that asks to see the personal board of another player
     * @param msg
     */
    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        if (this.player.getUsername().equals(msg.getUsernameAsking())) {
            for (PlayerInterface p : boardManager.getPlayers().values()) {
                if (p.getUsername().equals(msg.getUsernameAsked())) {
                    VAnotherPlayerInfoMsg info = new VAnotherPlayerInfoMsg("", p, boardManager, player.getUsername());
                    System.out.println(info);
                    notifyAllObserver(ObserverType.VIEW, info);
                }
            }
        }
        endAction = true;
        nextAction();
    }



    /**
     * this msg from the client is for active a Leader Card or Discard it
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        if (player.getUsername().equals(msg.getUsername())) {
            switch (msg.getAction()) {
                case "active":
                    //if the player ask to active it
                    try {
                        if (!isSolo) {
                            //System.out.println(" in active multiplayer ");        DEBUG
                            turn.activeLeaderCard(msg.getLeaderCards().get(0));
                            VUpdateVictoryPointsMsg update = new VUpdateVictoryPointsMsg("activating a Leader Card your Victory points has changed", player.getUsername(), player.calculateVictoryPoints());
                            notifyAllObserver(ObserverType.VIEW, update);
                            VUpdateWarehouseMsg secondUpdate = new VUpdateWarehouseMsg("update warehouse", player.getUsername(), player.getGameSpace().getWarehouse());
                            notifyAllObserver(ObserverType.VIEW, secondUpdate);
                            VSendPlayerDataMsg allData = new VSendPlayerDataMsg("all new info", player, boardManager, false);
                            notifyAllObserver(ObserverType.VIEW, allData);
                        } else {
                            //System.out.println(" in active1 ");           DEBUG
                            soloPlayerTurn.activeLeaderCard(msg.getLeaderCards().get(0));
                            VUpdateVictoryPointsMsg update = new VUpdateVictoryPointsMsg("activating a Leader Card your Victory points has changed", soloPlayerTurn.getCurrentPlayer().getUsername(), soloPlayerTurn.getCurrentPlayer().calculateVictoryPoints());
                            notifyAllObserver(ObserverType.VIEW, update);
                            VUpdateWarehouseMsg secondUpdate = new VUpdateWarehouseMsg("update warehouse", soloPlayerTurn.getCurrentPlayer().getUsername(), soloPlayerTurn.getCurrentPlayer().getGameSpace().getWarehouse());
                            notifyAllObserver(ObserverType.VIEW, secondUpdate);
                            VSendPlayerDataMsg allData = new VSendPlayerDataMsg("all new info", soloPlayerTurn.getCurrentPlayer(), boardManager, true);
                            notifyAllObserver(ObserverType.VIEW, allData);
                        }

                        endAction = true;
                        nextAction();
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                        System.out.println("Cannot active " + msg.getLeaderCards().get(0) + " Leader Card!");
                    } finally {
                        endAction = true;
                    }
                    break;
                case "remove":
                    //if the player ask to discard it
                    //then this player proceed in the faith track of one
                    try {
                        if (!isSolo) {
                            int section = turn.discardLeaderCard(msg.getLeaderCards().get(0));
                            for(int i:this.boardManager.getPlayers().keySet()){
                                this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().setPopeBoxActiveInSection(section);
                            }
                        } else {
                            soloPlayerTurn.discardLeaderCard(msg.getLeaderCards().get(0));
                        }

                        //and then notify everyone that this player increase the position
                        VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("Someone increased his position: ", player.getUsername(), player.calculateVictoryPoints(), 1);
                        VUpdateFaithTrackMsg msg1 = new VUpdateFaithTrackMsg("Increase yhe faith marker position", msg.getUsername(), player.getGameSpace().getFaithTrack());
                        notifyAllObserver(ObserverType.VIEW, notification);
                        notifyAllObserver(ObserverType.VIEW, msg1);


                        endAction = true;
                        nextAction();
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    } finally {
                        endAction = true;
                    }
                    break;
                case "firstChoose":
                    //not handled here
                    break;
                default:
                    System.out.println("error, action not valid in the msg!");
                    break;
            }
            VUpdateLeaderCards request = new VUpdateLeaderCards("Update leader cards",player.getUsername(),player.getLeaderCards());
            notifyAllObserver(ObserverType.VIEW,request);

        }
    }


    /**
     * this msg is received when the player receives from the market a white marble and has two TransformWhiteMarble activated
     * and the same when he has to choose a depot for a specific resource, the last one is the same as VChooseDepotsRequestMsg
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        if (msg.getUsername().equals(player.getUsername())) {
            Resource r = new Resource(msg.getResource());
            try {
                //player.getGameSpace().getResourceManager().addResourceToWarehouse(r, msg.getDepot());
                player.getGameSpace().getWarehouse().addResource(r, msg.getDepot());
                endAction = true;
                VUpdateWarehouseMsg notification = new VUpdateWarehouseMsg("The warehouse has changed..", player.getUsername(), player.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, notification);
            } catch (InvalidActionException e) {
                //e.printStackTrace();
                endAction = false;
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
        }
    }






    /*---------------------------------------------------------------------------------------------------------------------*/
    //NOT IMPLEMENTED HERE

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Virtual View
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }


    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //NOT IMPLEMENTED HERE, (VV) but in Lobby
    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CNewStartMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //NOT HERE, implemented in Turn Controller
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //NOT HERE, implemented in Lobby (Room)

    }
    /*---------------------------------------------------------------------------------------------------------------------*/

    private void nextAction() {
        if (endAction) {
            //send the msg to the client, to choose the next action he want to make
            VUpdateVictoryPointsMsg update = new VUpdateVictoryPointsMsg("You're actual amount of Victory Points is: ", player.getUsername(), player.calculateVictoryPoints());
            notifyAllObserver(ObserverType.VIEW, update);
            if (!isSolo) {
                VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), turn.getAvailableAction());
                notifyAllObserver(ObserverType.VIEW, msg);
                //System.out.println("next action");        DEBUG
            } else {
                VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), soloPlayerTurn.getAvailableAction());
                notifyAllObserver(ObserverType.VIEW, msg);
            }
        }

    }

    /**
     * remove an action from the possible actions that a player can do
     * @param action that has to be removed
     */
    private void removeAction(TurnAction action) {
        if (!isSolo) {
            turn.removeAction(action);
            /* add the action that allows to end the player turn */
            if (!turn.getAvailableAction().contains(TurnAction.END_TURN)) {
                turn.addAction(TurnAction.END_TURN);
            }
        } else {
            soloPlayerTurn.removeAction(action);
            /* add the action that allows to end the player turn */
            if (!soloPlayerTurn.getAvailableAction().contains(TurnAction.END_TURN)) {
                soloPlayerTurn.addAction(TurnAction.END_TURN);
            }
        }
    }


    /*
            NOT USED ANYMORE
     */
    public void decrementNumberResourcesFromM() {
        this.numberResourcesFromM--;
        checkNextActionMarket();
    }

    private void checkNextActionMarket() {
        if (numberResourcesFromM == 0) {
            nextAction();
        }
    }


}
