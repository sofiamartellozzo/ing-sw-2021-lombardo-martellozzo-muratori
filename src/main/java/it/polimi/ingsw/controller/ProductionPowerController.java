package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
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
    private final ArrayList<Resource> receivedResources;

    private Map<String, VirtualView> virtualView;

    public ProductionPowerController(Player player, Map<String, VirtualView> virtualView){
        this.player = (Player) player;
        this.receivedResources = new ArrayList<>();
        this.virtualView = virtualView;
        attachAllVV();
    }

    public ProductionPowerController(SoloPlayer player, Map<String, VirtualView> virtualView){
        this.player = (SoloPlayer) player;
        this.receivedResources=new ArrayList<>();
        this.virtualView = virtualView;
        attachAllVV();
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    private void attachAllVV(){
        for (String username: virtualView.keySet()) {
            attachObserver(ObserverType.VIEW, virtualView.get(username));
        }
    }

    public ArrayList<Resource> getReceivedResources(){return receivedResources;}

    public void start(){
        //start production power action
        ArrayList<Integer> activatablePowers = player.getGameSpace().getActivatableCardSpace(player);
        while(activatablePowers.size()>0){
            VActivateProductionPowerRequestMsg requestMsg = new VActivateProductionPowerRequestMsg("You ask to activate Production Power from the Personal Board, please choose which production power want to activate: ",player.getUsername());
            notifyAllObserver(ObserverType.VIEW,requestMsg);
            activatablePowers = player.getGameSpace().getActivatableCardSpace(player);
        }
        if(receivedResources!=null && receivedResources.size()>0) {
            receiveResources();
        }
    }

    public void receiveResources(){
        ArrayList<Resource> resourcesToStrongBox= new ArrayList<>();
        for(Resource resource: receivedResources){
            if(resource.getType().equals(TypeResource.FAITHMARKER)){
                VNotifyPositionIncreasedByMsg requestMsg1= new VNotifyPositionIncreasedByMsg("The player's faithmarker is increased by one", player.getUsername(),1);
                //put the player in the msg
                notifyAllObserver(ObserverType.VIEW,requestMsg1);
            }else{
                resourcesToStrongBox.add(resource);
            }
        }
        try {
            player.getGameSpace().getStrongbox().addResources(resourcesToStrongBox);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        if(msg.getUsername().equals(player.getUsername())){
            if(msg.getWhich()==0){

                ArrayList<Resource> resourcesToRemove = new ArrayList<>();
                //remove 2 resources
                for (TypeResource r: msg.getResourcesToPay()) {
                    resourcesToRemove.add(new Resource(r.getThisColor()));
                }
                if(msg.getWhere().equals("warehouse")){
                    Warehouse warehouse = player.getGameSpace().getWarehouse();
                    try {
                        warehouse.removeResources(resourcesToRemove);
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                }else if(msg.getWhere().equals("strongbox")){
                    StrongBox strongBox = player.getGameSpace().getStrongbox();
                    try {
                        strongBox.removeResources(resourcesToRemove);
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                }
                receivedResources.add(new Resource(msg.getResourceToGet().getThisColor()));

            }else if(msg.getWhich()>=1 && msg.getWhich()<=3){
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                DevelopmentCard developmentCard = player.getGameSpace().getCardSpace(msg.getWhich()-1).getUpperCard();
                if(warehouse.checkEnoughResources(developmentCard.showCostProductionPower())){
                try {
                    warehouse.removeResources(developmentCard.showCostProductionPower());
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
                }else if(strongBox.checkEnoughResources(developmentCard.showCostProductionPower())){
                    try{
                        strongBox.removeResources(developmentCard.showCostProductionPower());
                    }catch (InvalidActionException e){
                        e.printStackTrace();
                    }
                }
                receivedResources.addAll(developmentCard.showProceedsProductionPower());

            }else if(msg.getWhich()>=4 && msg.getWhich()<=5){
                int choose=4-msg.getWhich();
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                SpecialCard specialCard= player.getSpecialCard().get(choose);
                if(warehouse.checkEnoughResources(specialCard.getCostProductionPower())){
                    for(Resource resource: specialCard.getCostProductionPower()){
                        try{
                            warehouse.removeResource(warehouse.searchResource(resource));
                        }catch(InvalidActionException e){
                            e.printStackTrace();
                        }
                    }
                }else if(strongBox.checkEnoughResources(specialCard.getCostProductionPower())){
                    try{
                        strongBox.removeResources(specialCard.getCostProductionPower());
                    }catch (InvalidActionException e){
                        e.printStackTrace();
                    }
                }
                receivedResources.add(new Resource(msg.getResourceToGet()));
                receivedResources.add(new Resource(Color.RED));
            }
        }
    }

    @Override
    public void receiveMsg(CChooseDiscardResponseMsg msg) {

    }





    /*-----------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {

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



}
