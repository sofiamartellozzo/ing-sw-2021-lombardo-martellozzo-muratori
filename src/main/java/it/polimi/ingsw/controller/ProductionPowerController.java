package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;

/**
 * this class is a part of the controller that manages all the actions regarding the production powers:
 * base production power, cards spaces' production power, and the ones of the leader cards (if activated)
 * this class manages the different powers' activation, removes the payed resources from strongbox/warehouse and
 * loads the resources into the strongbox (but only AFTER that the CStopPP message is received)
 */
public class ProductionPowerController extends Observable implements ControllerObserver {

    public PlayerInterface getPlayer() {
        return player;
    }

    private BoardManager boardManager;

    private final PlayerInterface player;
    private boolean notInterrupt;
    private final ArrayList<Resource> receivedResources;

    private Map<String, VirtualView> virtualView;

    /**
     * constructor used in a Multi Player Mode
     * @param player
     * @param virtualView of the players
     */
    public ProductionPowerController(Player player, Map<String, VirtualView> virtualView) {
        this.player = (Player) player;
        this.receivedResources = new ArrayList<>();
        this.virtualView = virtualView;
        this.notInterrupt = false;
        attachAllVV();
    }

    /**
     * constructor of the class, override the previous one, used in Single Player Mode
     * @param player
     * @param virtualView of the player
     */
    public ProductionPowerController(SoloPlayer player, Map<String, VirtualView> virtualView) {
        this.player = (SoloPlayer) player;
        this.receivedResources = new ArrayList<>();
        this.virtualView = virtualView;
        this.notInterrupt = false;
        attachAllVV();
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    private void attachAllVV() {
        for (String username : virtualView.keySet()) {
            attachObserver(ObserverType.VIEW, virtualView.get(username));
        }
    }

    public ArrayList<Resource> getReceivedResources() {
        return receivedResources;
    }

    /**
     * start production power action
     */
    public void start() {

        ArrayList<Integer> activatablePowers = player.getGameSpace().getActivatableCardSpace(player);
        if (activatablePowers.size() > 0 && !notInterrupt) {
            VActivateProductionPowerRequestMsg requestMsg = new VActivateProductionPowerRequestMsg("You ask to activate Production Power from the Personal Board, please choose which production power want to activate: ", player.getUsername(), activatablePowers);
            notifyAllObserver(ObserverType.VIEW, requestMsg);
            activatablePowers = player.getGameSpace().getActivatableCardSpace(player);
        }
        if (activatablePowers == null || activatablePowers.size() == 0) {
            VActivateProductionPowerRequestMsg requestMsg = new VActivateProductionPowerRequestMsg("You ask to activate Production Power from the Personal Board, no power activable", player.getUsername(), activatablePowers);
            notifyAllObserver(ObserverType.VIEW, requestMsg);
        }

    }

    /**
     * this methods gives to the player the received resources and put them
     * in the strongbox, but only when he has decided to stop using the production powers !!
     */
    public void receiveResources() {
        ArrayList<Resource> resourcesToStrongBox = new ArrayList<>();
        for (Resource resource : receivedResources) {
            if (resource.getType().equals(TypeResource.FAITHMARKER)) {
                int section=player.increasePosition();
                if(section!=0) {
                    for (int i : this.boardManager.getPlayers().keySet()) {
                        this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().doVaticanReport(section);
                    }
                }
                VNotifyPositionIncreasedByMsg requestMsg1 = new VNotifyPositionIncreasedByMsg("The player's faithmarker is increased by one", player.getUsername(), player.calculateVictoryPoints(), 1);
                VUpdateFaithTrackMsg requestMsg2 = new VUpdateFaithTrackMsg("updated faith Track", player.getUsername(), player.getGameSpace().getFaithTrack());
                //put the player in the msg
                notifyAllObserver(ObserverType.VIEW, requestMsg1);
                notifyAllObserver(ObserverType.VIEW, requestMsg2);
            } else {
                resourcesToStrongBox.add(resource);
            }
        }

        try {
            if (resourcesToStrongBox != null && resourcesToStrongBox.size() > 0) {
                player.getGameSpace().getStrongbox().addResources(resourcesToStrongBox);

            }
            VUpdateStrongboxMsg update = new VUpdateStrongboxMsg("remove resources from strong box", player.getUsername(), player.getGameSpace().getStrongbox());
            notifyAllObserver(ObserverType.VIEW, update);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    /**
     * manage the response of the client that decides which production power he wants to activate,
     * and in case the resources that he wants
     * @param msg
     */
    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        boolean putResource = false;
        if (msg.getUsername().equals(player.getUsername())) {
            if (msg.getWhich() == 0) {

                ArrayList<Resource> resourcesToRemove = new ArrayList<>();
                //remove 2 resources
                for (TypeResource r : msg.getResourcesToPay()) {
                    resourcesToRemove.add(new Resource(r.getThisColor()));
                }
                if (msg.getWhere().equals("warehouse")) {
                    Warehouse warehouse = player.getGameSpace().getWarehouse();
                    try {
                        warehouse.removeResources(resourcesToRemove);
                        putResource = true;
                        VUpdateWarehouseMsg update = new VUpdateWarehouseMsg("removed resources from warehouse", player.getUsername(), warehouse);
                        notifyAllObserver(ObserverType.VIEW, update);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        //if the player doesn't have the resources
                        VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);

                    }

                } else if (msg.getWhere().equals("strongbox")) {
                    StrongBox strongBox = player.getGameSpace().getStrongbox();
                    try {
                        strongBox.removeResources(resourcesToRemove);
                        VUpdateStrongboxMsg update = new VUpdateStrongboxMsg("remove resources from strong box", player.getUsername(), strongBox);
                        notifyAllObserver(ObserverType.VIEW, update);
                        putResource = true;
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);
                    }
                }
                if (putResource) {
                    receivedResources.add(new Resource(msg.getResourceToGet().getThisColor()));
                    //VUpdateStrongboxMsg msg1 = new VUpdateStrongboxMsg("here is your strongbox updated", player.getUsername(), player.getGameSpace().getStrongbox());
                    //notifyAllObserver(ObserverType.VIEW, msg1);
                }

            } else if (msg.getWhich() >= 1 && msg.getWhich() <= 3) {
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                DevelopmentCard developmentCard = player.getGameSpace().getCardSpace(msg.getWhich() - 1).getUpperCard();
                if (msg.getWhere().equals("warehouse")) {
                //if (warehouse.checkEnoughResources(developmentCard.showCostProductionPower())) {
                    try {
                        warehouse.removeResources(developmentCard.showCostProductionPower());
                        VUpdateWarehouseMsg update = new VUpdateWarehouseMsg("here is your warehouse updated", player.getUsername(), player.getGameSpace().getWarehouse());
                        notifyAllObserver(ObserverType.VIEW, update);
                        putResource = true;
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);
                    }
                } else if (msg.getWhere().equals("strongbox")) {
                    try {
                        strongBox.removeResources(developmentCard.showCostProductionPower());
                        VUpdateStrongboxMsg update = new VUpdateStrongboxMsg("here is your warehouse updated", player.getUsername(), player.getGameSpace().getStrongbox());
                        notifyAllObserver(ObserverType.VIEW, update);
                        putResource = true;

                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);
                    }
                }
                if (putResource) {
                    receivedResources.addAll(developmentCard.showProceedsProductionPower());
                    VUpdateStrongboxMsg msg1 = new VUpdateStrongboxMsg("here is your strongbox updated", player.getUsername(), player.getGameSpace().getStrongbox());
                    notifyAllObserver(ObserverType.VIEW, msg1);
                }

            } else if (msg.getWhich() >= 4 && msg.getWhich() <= 5) {
                int choose = 4 - msg.getWhich();
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                SpecialCard specialCard = player.getSpecialCard().get(choose);
                if (msg.getWhere().equals("warehouse")) {
                    if (warehouse.checkEnoughResources(specialCard.getCostProductionPower())) {
                        for (Resource resource : specialCard.getCostProductionPower()) {
                            try {
                                warehouse.removeResource(warehouse.searchResource(resource));
                                VUpdateWarehouseMsg update = new VUpdateWarehouseMsg("here is your warehouse updated", player.getUsername(), player.getGameSpace().getWarehouse());
                                notifyAllObserver(ObserverType.VIEW, update);
                                putResource = true;
                            } catch (InvalidActionException e) {
                                //e.printStackTrace();
                                VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                                notifyAllObserver(ObserverType.VIEW, msg1);
                            }
                        }
                    }
                //} else if (strongBox.checkEnoughResources(specialCard.getCostProductionPower())) {
                } else if (msg.getWhere().equals("strongbox")) {
                    try {
                        strongBox.removeResources(specialCard.getCostProductionPower());
                        VUpdateStrongboxMsg update = new VUpdateStrongboxMsg("here is your warehouse updated",player.getUsername(),player.getGameSpace().getStrongbox());
                        notifyAllObserver(ObserverType.VIEW, update);
                        putResource = true;
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        VResourcesNotFoundMsg msg1 = new VResourcesNotFoundMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);
                    }
                }
                if (putResource) {
                    receivedResources.add(new Resource(msg.getResourceToGet()));
                    receivedResources.add(new Resource(Color.RED));
                    VUpdateStrongboxMsg msg1 = new VUpdateStrongboxMsg("here is your strongbox updated", player.getUsername(), player.getGameSpace().getStrongbox());
                    notifyAllObserver(ObserverType.VIEW, msg1);
                }
            }
            start();
        }
    }


    /**
     * received when the player decides to stop using production powers, so the resources will be put in his strongox
     * @param msg
     */
    @Override
    public void receiveMsg(CStopPPMsg msg) {
        notInterrupt = true;
        if (receivedResources != null && receivedResources.size() > 0) {
            receiveResources();
        }
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
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

    /*-----------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Virtual View
    }

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
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
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Initialized Controller/ActionC
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby (Room)
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //NOT IMPLEMENTED HERE, but in Initialize Controller/ActionC
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        //NOT HERE, implemented in Turn Controller
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }


}
