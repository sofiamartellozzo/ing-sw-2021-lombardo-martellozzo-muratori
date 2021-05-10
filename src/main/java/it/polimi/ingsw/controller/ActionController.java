package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.utility.MarketStructureCopy;
import it.polimi.ingsw.utility.TableCardCopy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;

public class ActionController extends Observable implements ControllerObserver {

    /*the actual player of the turn*/
    private PlayerInterface player;

    private PlayerTurn turn;

    //private SoloPlayer soloPlayer;
    private SoloPlayerTurn soloPlayerTurn;

    private BoardManager boardManager;

    private boolean actionWentWrong;

    public ActionController(Player player, PlayerTurn turn, BoardManager boardManager){
        this.player = (Player) player;
        this.turn = turn;
        this.boardManager = boardManager;
        actionWentWrong = true;
    }

    public ActionController(SoloPlayer player, SoloPlayerTurn turn, BoardManager boardManager){
        this.player = (SoloPlayer) player;
        this.soloPlayerTurn = turn;
        this.boardManager = boardManager;
        actionWentWrong = true;
    }


    private List<String> getPlayerAsList(Map<Integer, PlayerInterface> players){
        List<String> p = new ArrayList<>();
        for (Integer i: players.keySet()) {
            p.add(players.get(i).getUsername());
        }
        return p;
    }
    /*---------------------------------------------------------------------------------------------------------------------*/

    /**
     * receive the msg from the client with the Action he choose
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) throws InvalidActionException {

        switch (msg.getActionChose()){
            case BUY_CARD:
                //I need the input from the real player (person)
                //from boardManager.getAvailable

                //send the Development Card Table to the client inside the msg
                DevelopmentCardTable currentDevTable = null;
                currentDevTable = boardManager.getDevelopmentCardTable();
                boolean[][] matrix = new boolean[4][3]; //this will be 3x4
                matrix[1][1] = true; //only to not have errors right now

                VChooseDevelopCardRequestMsg request = new VChooseDevelopCardRequestMsg("You chose to buy a Development Card, select which one: ", player.getUsername(),currentDevTable,matrix);
                notifyAllObserver(ObserverType.VIEW, request);
                break;
            case MOVE_RESOURCE:
                ArrayList<TypeResource> r = new ArrayList<>();
                r.add(TypeResource.COIN);
                HashMap<Integer, ArrayList<TypeResource>> situation = new HashMap<>();
                situation.put(1, r);
                // player.resourceManager
                VMoveResourceRequestMsg request1 = new VMoveResourceRequestMsg("You ask to move your resource from one depot to another, chose which one and where to store his resource:", player.getUsername(), situation);
                notifyAllObserver(ObserverType.VIEW, request1);
                break;
            case BUY_FROM_MARKET:
                //send the Market structure to the Client, sending it throw the net will serialize the object
                MarketStructure currentMarket = null;
                currentMarket = boardManager.getMarketStructure();

                VBuyFromMarketRequestMsg request2 = new VBuyFromMarketRequestMsg("You ask to buy from the Market at this Turn, please choose column or row and which one: ", player.getUsername(),currentMarket);
                notifyAllObserver(ObserverType.VIEW, request2);
                break;
            case ACTIVE_PRODUCTION_POWER:
                ProductionPowerController productionPowerController = null;
                if(player instanceof Player){
                    productionPowerController = new ProductionPowerController((Player) player);
                }else if(player instanceof SoloPlayer){
                    productionPowerController = new ProductionPowerController((SoloPlayer) player);
                }
                attachObserver(ObserverType.CONTROLLER,productionPowerController);
                productionPowerController.start();
                break;
            case REMOVE_LEADER_CARD:
                //ask the player which card he want to remove, before see if there are any that
                //is inactive, otherwise send a msg that this action can't be made
                //player.
                //and remove from the possible action
                //this.currentPlayer.removeLeaderCard(1);
                ArrayList<Integer> possibleCardToBeDiscarded = new ArrayList<>();
                possibleCardToBeDiscarded.add(1);
                VChooseLeaderCardRequestMsg request4 = new VChooseLeaderCardRequestMsg("Because you chose to discard a card, select which one", possibleCardToBeDiscarded, player.getUsername());
                notifyAllObserver(ObserverType.VIEW,request4);
                break;
            case ACTIVE_LEADER_CARD:
                //ask the player which card he want to active, before see if there are any that
                //is inactive, otherwise send a msg that this action can't be made
                ArrayList<Integer> possibleCardToBeActive = new ArrayList<>();
                possibleCardToBeActive.add(1);
                VChooseLeaderCardRequestMsg request5 = new VChooseLeaderCardRequestMsg("Because you chose to activated a card, select which one", possibleCardToBeActive, player.getUsername());
                notifyAllObserver(ObserverType.VIEW,request5);
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

    /**
     * the response from the client to buy a development card
     * @param msg
     */
    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

        if (player.getUsername().equals(msg.getUsername())){
            try {
                player.buyCard(msg.getRow(), msg.getColumn(), boardManager, msg.getCardSpaceToStoreIt());
                actionWentWrong = false;
                //remove tre 3 action from the able ones because can be made only once
                turn.removeAction(msg.getActionChose());
                //add the action that allows to end the player turn
                turn.addAction(TurnAction.END_TURN);
            } catch (InvalidActionException e) {
                System.out.println("Cannot buy this card, sorry!");
                e.printStackTrace();
            }
        }
    }

    /**
     * manage the request of the client to move a resource from a depot to another
     * @param msg
     */
    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

        if (player.getUsername().equals(msg.getUsername())){
            try {
                player.moveResource(msg.getFromDepot(), msg.getToDepot());
                System.out.println("the move went successfully!!");
                actionWentWrong = false;
                //remove tre 3 action from the able ones because can be made only once
                //turn.removeAction(msg.getActionChose());
            } catch (InvalidActionException e) {
                e.printStackTrace();
                System.out.println("Not able to move the resource from " +msg.getFromDepot()+ " depot, to " +msg.getToDepot()+ " depot!");
            }
        }
    }

    /**
     * manage the request of the client to buy from the market
     * @param msg
     */
    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

        ArrayList<TypeResource> resourcesFromMarket = null;

        if (player.getUsername().equals(msg.getUsername())){
            try {
                resourcesFromMarket = player.buyFromMarket(msg.getWhichRorC(), msg.getRowOrColumn(), boardManager);
                for (TypeResource resource: resourcesFromMarket) {
                    if (!resource.equals(TypeResource.BLANK)){
                        if (resource.equals(TypeResource.FAITHMARKER)){
                            VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("because of a red marble, this player increased his position", player.getUsername(), 1);
                            Map<Integer, PlayerInterface> players = boardManager.getPlayers();
                            notification.setAllPlayerToNotify(getPlayerAsList(players));
                            notifyAllObserver(ObserverType.VIEW, notification);
                        }
                        else{
                            VChooseDepotMsg request = new VChooseDepotMsg("chose the depot where to store this resource",this.player.getUsername(), resource);
                            notifyAllObserver(ObserverType.VIEW, request);
                        }
                    }
                    else{
                        //the player has 2 whiteSpecialMarble
                        VChooseResourceAndDepotMsg request = new VChooseResourceAndDepotMsg("",player.getUsername(), player.getWhiteSpecialResources());
                        notifyAllObserver(ObserverType.VIEW, request);
                    }
                }
                actionWentWrong = false;
                //remove tre 3 action from the able ones because can be made only once
                turn.removeAction(msg.getActionChose());
                //add the action that allows to end the player turn
                turn.addAction(TurnAction.END_TURN);
            } catch (InvalidActionException e) {
                e.printStackTrace();
                System.out.println("Something went wrong in buying from the market...");
            }
        }
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        //remove tre 3 action from the able ones because can be made only once
        turn.removeAction(msg.getActionChose());
        //add the action that allows to end the player turn
        turn.addAction(TurnAction.END_TURN);
    }

    /**
     * this msg from the client is for active a Leader Card or Discard it
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        if (player.getUsername().equals(msg.getUsername())){
            switch (msg.getAction()){
                case "active":
                    //if the player ask to active it
                    try {
                        turn.activeLeaderCard(msg.getChosenLeaderCard().get(0));
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                        System.out.println("Cannot active " +msg.getChosenLeaderCard().get(0)+ " Leader Card!");
                    }
                    break;
                case "remove":
                    //if the player ask to discard it
                    //then this player proceed in the faith track of one
                    try {
                        turn.discardLeaderCard(msg.getChosenLeaderCard().get(0));
                        //and then notify everyone that this player increase the position
                        VNotifyPositionIncreasedByMsg notification = new VNotifyPositionIncreasedByMsg("Someone increased his position: ", player.getUsername(), 1);
                        notifyAllObserver(ObserverType.VIEW, notification);
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
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) throws InvalidActionException {
        if (msg.getUsername().equals(player.getUsername())){
            Resource r = new Resource(msg.getResource());
            try {
                player.getGameSpace().getResourceManager().addResourceToWarehouse(r, msg.getDepot());
            } catch (InvalidActionException e) {
                e.printStackTrace();
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
        }
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseResourceResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg) {

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


}
