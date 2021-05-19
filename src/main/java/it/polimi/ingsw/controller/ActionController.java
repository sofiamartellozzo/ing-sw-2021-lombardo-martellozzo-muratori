package it.polimi.ingsw.controller;

import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
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

public class ActionController extends Observable implements ControllerObserver {

    public PlayerInterface getPlayer() {
        return player;
    }

    /*the actual player of the turn*/
    private PlayerInterface player;

    private PlayerTurn turn;

    //private SoloPlayer soloPlayer;
    private SoloPlayerTurn soloPlayerTurn;

    private BoardManager boardManager;

    private boolean endAction;

    private boolean isSolo;

    /* list of VV of the players*/
    private Map<String, VirtualView> virtualView;

    public ActionController(Player player, PlayerTurn turn, BoardManager boardManager, Map<String, VirtualView> virtualView) {
        this.player = (Player) player;
        this.turn = turn;
        this.boardManager = boardManager;
        this.virtualView = virtualView;
        attachAllVV();
        this.endAction = false;
        this.isSolo = false;
    }

    public ActionController(SoloPlayer player, SoloPlayerTurn turn, BoardManager boardManager, Map<String, VirtualView> virtualView) {
        this.player = (SoloPlayer) player;
        this.soloPlayerTurn = turn;
        this.boardManager = boardManager;
        this.virtualView = virtualView;
        attachAllVV();
        this.endAction = false;
        this.isSolo = true;
    }


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
     * receive the msg from the client with the Action he choose
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
            case END_TURN:
                this.player.endTurn();
                break;
            default:
                //choose not available
        }

    }

    private ArrayList<Integer> cardAbleForPlayer() {
        ArrayList<Integer> possibleCardToBeDiscarded = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = this.player.getLeaderCards();
        //System.out.println("bug1");       DEBUGGING
        //System.out.println(leaderCards);
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
     * the response from the client to buy a development card
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

        if (player.getUsername().equals(msg.getUsername())) {
            try {
                player.buyCard(msg.getRow(), msg.getColumn(), boardManager, msg.getCardSpaceToStoreIt());

                VUpdateDevTableMsg update = new VUpdateDevTableMsg("new develop table", player.getUsername(), boardManager.getDevelopmentCardTable(), boardManager.getPlayers());
                notifyAllObserver(ObserverType.VIEW, update);

                //remove tre 3 action from the able ones because can be made only once
                removeAction(msg.getActionChose());

                nextAction();

            } catch (InvalidActionException e) {
                System.out.println("Cannot buy this card, sorry!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {

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

                nextAction();

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
                /*check for each resources returned from the market...*/
                for (TypeResource resource : resourcesFromMarket) {

                    /* BLANK is the special type, set from the white marble if the player has two Special White Marble Ability activated*/
                    if (!resource.equals(TypeResource.BLANK)) {

                        if (resource.equals(TypeResource.FAITHMARKER)) {
                            /* the FAITHMARKER is not a real resources, it increased the position of the player in FT*/
                            if (!isSolo) {
                                player.increasePosition();
                            } else {
                                soloPlayerTurn.getCurrentPlayer().increasePosition();
                            }

                            VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("because of a red marble, this player increased his position", player.getUsername(), 1);
                            Map<Integer, PlayerInterface> players = boardManager.getPlayers();
                            notification.setAllPlayerToNotify(getPlayerAsList(players));
                            notifyAllObserver(ObserverType.VIEW, notification);
                        } else {
                            VChooseDepotMsg request = new VChooseDepotMsg("chose the depot where to store this \"" + resource + "\" resource", this.player.getUsername(), resource);
                            notifyAllObserver(ObserverType.VIEW, request);
                        }
                    } else {
                        //the player has 2 whiteSpecialMarble
                        VChooseResourceAndDepotMsg request = new VChooseResourceAndDepotMsg("Buying from the market gave you a white marble, you also have two WhiteSpecialMarble Ability activated so choose which one to use from...", player.getUsername(), player.getWhiteSpecialResources());
                        notifyAllObserver(ObserverType.VIEW, request);
                    }
                }

                /* remove tre 3 action from the able ones because can be made only once */
                removeAction(msg.getActionChose());

                nextAction();

            } catch (InvalidActionException e) {
                e.printStackTrace();
                System.out.println("Something went wrong in buying from the market...");
            }
        }
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        /*notify so send the msg with the info to the right controller*/
        notifyAllObserver(ObserverType.CONTROLLER, msg);
        //remove tre 3 action from the able ones because can be made only once
        removeAction(msg.getActionChose());

        //nextAction();
    }

    @Override
    public void receiveMsg(CChooseDiscardResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CStopPPMsg msg) {
        notifyAllObserver(ObserverType.CONTROLLER, msg);
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
                            turn.activeLeaderCard(msg.getLeaderCards().get(0));
                        } else {
                            soloPlayerTurn.activeLeaderCard(msg.getLeaderCards().get(0));
                        }

                        nextAction();
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                        System.out.println("Cannot active " + msg.getLeaderCards().get(0) + " Leader Card!");
                    }
                    break;
                case "remove":
                    //if the player ask to discard it
                    //then this player proceed in the faith track of one
                    try {
                        if (!isSolo) {
                            turn.discardLeaderCard(msg.getLeaderCards().get(0));
                        } else {
                            soloPlayerTurn.discardLeaderCard(msg.getLeaderCards().get(0));
                        }

                        //and then notify everyone that this player increase the position
                        VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("Someone increased his position: ", player.getUsername(), 1);
                        notifyAllObserver(ObserverType.VIEW, notification);

                        nextAction();
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                    break;
                case "firstChoose":
                    //not handled here
                    break;
                default:
                    System.out.println("error, action not valid in the msg!");
                    break;
            }

        }
    }

    @Override
    public void receiveMsg(CGameCanStratMsg msg) {

    }

    /**
     * this after receive a White marble when the player has, and the same when he have to
     * choose a depots for a specific resource, the last one is the same as VChooseDepotsRequestMsg
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
                VUpdateWarehouseMsg notification = new VUpdateWarehouseMsg("The warehouse has changed..", player.getUsername(), player.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, notification);
            } catch (InvalidActionException e) {
                e.printStackTrace();
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
        }
    }






    /*---------------------------------------------------------------------------------------------------------------------*/
    //NOT IMPLEMENTED HERE

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //not here, in (Lobby)
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {

    }
    /*---------------------------------------------------------------------------------------------------------------------*/

    private void nextAction() {
        this.endAction = true;
        //send the msg to the client, to choose the next action he want to make
        if (!isSolo) {
            VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), turn.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, msg);
        } else {
            VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), soloPlayerTurn.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, msg);
        }

    }

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

}
