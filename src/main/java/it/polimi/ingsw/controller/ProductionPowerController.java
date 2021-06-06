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

public class ProductionPowerController extends Observable implements ControllerObserver {

    private final PlayerInterface player;
    private boolean notInterrupt;
    private final ArrayList<Resource> receivedResources;

    private Map<String, VirtualView> virtualView;

    public ProductionPowerController(Player player, Map<String, VirtualView> virtualView) {
        this.player = (Player) player;
        this.receivedResources = new ArrayList<>();
        this.virtualView = virtualView;
        this.notInterrupt = false;
        attachAllVV();
    }

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

    public void start() {
        //start production power action
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

    public void receiveResources() {
        ArrayList<Resource> resourcesToStrongBox = new ArrayList<>();
        for (Resource resource : receivedResources) {
            if (resource.getType().equals(TypeResource.FAITHMARKER)) {
                player.getGameSpace().getFaithTrack().increasePosition();
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

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        boolean putResource = false;
        //System.out.println("Receiving REQUEST OF WITCH PP");
        if (msg.getUsername().equals(player.getUsername())) {
            if (msg.getWhich() == 0) {

                //System.out.println("IN REMOVING RESOURCE");
                ArrayList<Resource> resourcesToRemove = new ArrayList<>();
                //remove 2 resources
                for (TypeResource r : msg.getResourcesToPay()) {
                    resourcesToRemove.add(new Resource(r.getThisColor()));
                }
                if (msg.getWhere().equals("warehouse")) {
                    //System.out.println("TRY WWWW1");
                    Warehouse warehouse = player.getGameSpace().getWarehouse();
                    try {
                        //System.out.println("TRY WWWWWWWWWWW2");
                        warehouse.removeResources(resourcesToRemove);
                        putResource = true;
                        VUpdateWarehouseMsg update = new VUpdateWarehouseMsg("removed resources from warehouse", player.getUsername(), warehouse);
                        notifyAllObserver(ObserverType.VIEW, update);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        //if the player doesn't have the resources
                        VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);

                    }

                } else if (msg.getWhere().equals("strongbox")) {
                    //System.out.println("TRY SSSSSSS1");
                    StrongBox strongBox = player.getGameSpace().getStrongbox();
                    try {
                        //System.out.println("TRY SSSSSSSSS2");
                        strongBox.removeResources(resourcesToRemove);
                        VUpdateStrongboxMsg update = new VUpdateStrongboxMsg("remove resources from strong box", player.getUsername(), strongBox);
                        notifyAllObserver(ObserverType.VIEW, update);
                        putResource = true;
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
                        notifyAllObserver(ObserverType.VIEW, msg1);
                    }
                }
                if (putResource) {
                    receivedResources.add(new Resource(msg.getResourceToGet().getThisColor()));
                    VUpdateStrongboxMsg msg1 = new VUpdateStrongboxMsg("here is your strongbox updated", player.getUsername(), player.getGameSpace().getStrongbox());
                    notifyAllObserver(ObserverType.VIEW, msg1);
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
                        VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
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
                        VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
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
                                VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
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
                        VResourcesNotValidMsg msg1 = new VResourcesNotValidMsg("Error you can't activate the ProductionPower, because you don't have the resources you chose!", player.getUsername());
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


    @Override
    public void receiveMsg(CStopPPMsg msg) {
        //System.out.println("enter in STOP");
        notInterrupt = true;
        if (receivedResources != null && receivedResources.size() > 0) {
            //System.out.println("enter in STOP2");
            receiveResources();
        }
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {

    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {

    }



    @Override
    public void receiveMsg(CCloseRoomMsg msg) {

    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {

    }





    /*-----------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {

    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {


    }


    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //in Lobby (Room)
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {

    }


    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {

    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {

    }


}
