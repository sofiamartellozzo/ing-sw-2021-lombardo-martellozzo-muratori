package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.utility.MarketStructureCopy;
import it.polimi.ingsw.utility.TableCardCopy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionController extends Observable implements ControllerObserver {

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
                ArrayList<Integer> possibleCardToBeActive = cardAbleForPlayer();
                VChooseLeaderCardRequestMsg request5 = new VChooseLeaderCardRequestMsg("Because you chose to activated a card, select which one", possibleCardToBeActive, player.getUsername(), "active");
                notifyAllObserver(ObserverType.VIEW, request5);
                break;
            case END_TURN:
                this.player.endTurn();
                break;
            default:
                //choose not available
        }
        //remove tre 3 action from the able ones because can be made only once
        //check if the action has been made!!!
    }

    private ArrayList<Integer> cardAbleForPlayer() {
        ArrayList<Integer> possibleCardToBeDiscarded = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = this.player.getLeaderCards();
        System.out.println("bug1");
        System.out.println(leaderCards);
        if (leaderCards != null) {
            System.out.println("bug2");
            for (LeaderCard card : leaderCards) {
                System.out.println("bug3");
                if (card.getState() instanceof Inactive) {
                    System.out.println(card.getCardID());
                    System.out.println("id of card of p");
                    possibleCardToBeDiscarded.add(card.getCardID());
                }
            }
        }
        return possibleCardToBeDiscarded;
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

                //remove tre 3 action from the able ones because can be made only once
                turn.removeAction(msg.getActionChose());
                //add the action that allows to end the player turn
                turn.addAction(TurnAction.END_TURN);

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
                /*check for each resources returned from the market...*/
                for (TypeResource resource : resourcesFromMarket) {

                    /* BLANK is the special type, set from the white marble if the player has two Special White Marble Ability activated*/
                    if (!resource.equals(TypeResource.BLANK)) {

                        if (resource.equals(TypeResource.FAITHMARKER)) {
                            /* the FAITHMARKER is not a real resources, it increased the position of the player in FT*/
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
                turn.removeAction(msg.getActionChose());
                /* add the action that allows to end the player turn */
                turn.addAction(TurnAction.END_TURN);

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
        turn.removeAction(msg.getActionChose());
        //add the action that allows to end the player turn
        turn.addAction(TurnAction.END_TURN);

        nextAction();
    }

    @Override
    public void receiveMsg(CChooseDiscardResponseMsg msg) {

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
                            turn.activeLeaderCard(msg.getDiscardOrActiveCard());
                        } else {
                            soloPlayerTurn.activeLeaderCard(msg.getDiscardOrActiveCard());
                        }

                        nextAction();
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                        System.out.println("Cannot active " + msg.getChosenLeaderCard().get(0) + " Leader Card!");
                    }
                    break;
                case "remove":
                    //if the player ask to discard it
                    //then this player proceed in the faith track of one
                    try {

                        if (!isSolo) {
                            turn.discardLeaderCard(msg.getDiscardOrActiveCard());
                        } else {
                            soloPlayerTurn.discardLeaderCard(msg.getDiscardOrActiveCard());
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
                player.getGameSpace().getResourceManager().addResourceToWarehouse(r, msg.getDepot());
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
        if (!isSolo){
            VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), turn.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, msg);
        }
        else{
            VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), soloPlayerTurn.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, msg);
        }

    }

}
