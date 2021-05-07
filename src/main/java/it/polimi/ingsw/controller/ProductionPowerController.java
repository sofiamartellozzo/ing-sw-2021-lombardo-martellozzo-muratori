package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.cardAbility.AdditionalPower;

import java.lang.reflect.Array;
import java.util.*;

public class ProductionPowerController extends Observable implements ControllerObserver {

    private PlayerInterface player;
    private ArrayList<Resource> receivedResources;

    public ProductionPowerController(Player player){
        this.player = (Player) player;
        this.receivedResources=new ArrayList<>();
    }

    public ProductionPowerController(SoloPlayer player){
        this.player = (SoloPlayer) player;
        this.receivedResources=new ArrayList<>();
    }

    public void start(){
        //start production power action
        ArrayList<Integer> activatablePowers = player.getGameSpace().getActivatableCardSpace(player);
        while(activatablePowers.size()>0){
            VActivateProductionPowerRequestMsg requestMsg = new VActivateProductionPowerRequestMsg("You ask to activate Production Power from the Personal Board, please choose which production power want to activate: ",player.getUsername());
            notifyAllObserver(ObserverType.VIEW,requestMsg);
        }
        try {
            player.getGameSpace().getStrongbox().addResources(receivedResources);
        } catch (InvalidActionException e) {
            //create a msg to notify the error
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        if(msg.getUsername().equals(player.getUsername())){
            if(msg.getWhich()==0){
                if(msg.getWhere().equals("Warehouse")){
                    VChooseResourceRequestMsg requestMsg = new VChooseResourceRequestMsg("Please choose two resources to pay the production power from the warehouse", player.getUsername());
                    notifyAllObserver(ObserverType.VIEW,requestMsg);
                }else if(msg.getWhere().equals("Strongbox")){
                    VChooseResourceRequestMsg requestMsg = new VChooseResourceRequestMsg("Please two resources to pay the production power from the strongbox", player.getUsername());
                    notifyAllObserver(ObserverType.VIEW,requestMsg);
                }
                VChooseSingleResourceToPutInStrongBoxRequestMsg requestMsg = new VChooseSingleResourceToPutInStrongBoxRequestMsg("Please choose which resource you want to receive", this.player.getUsername());
                notifyAllObserver(ObserverType.VIEW,requestMsg);
            }else if(msg.getWhich()>=1 && msg.getWhich()<=3){
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                DevelopmentCard developmentCard = player.getGameSpace().getCardSpace(msg.getWhich()).getUpperCard();
                if(warehouse.checkEnoughResources(developmentCard.showCostProductionPower())){
                    for(Resource resource: developmentCard.showCostProductionPower()){
                        try {
                            warehouse.removeResource(warehouse.searchResource(resource));
                        }catch(InvalidActionException e){
                            e.printStackTrace();
                        }
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
                VChooseSingleResourceToPutInStrongBoxRequestMsg requestMsg = new VChooseSingleResourceToPutInStrongBoxRequestMsg("Please choose the resource you want",player.getUsername());
                notifyAllObserver(ObserverType.VIEW,requestMsg);
                VNotifyPositionIncreasedByMsg requestMsg1= new VNotifyPositionIncreasedByMsg("The player's faithmarker is increased by one", player.getUsername(),1);
                //put the player in the msg
                notifyAllObserver(ObserverType.VIEW,requestMsg1);
            }
        }
    }

    @Override
    public void receiveMsg(CChooseResourceResponseMsg msg) {
        if(msg.getUsername().equals(player.getUsername())){
            if(msg.getWhere().equals("Warehouse")){
                Warehouse warehouse = player.getGameSpace().getWarehouse();
                for(TypeResource resource: msg.getResources()){
                    int depot = warehouse.searchResource(new Resource(resource));
                    try {
                        warehouse.removeResource(depot);
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.getWhere().equals("Strongbox")){
                StrongBox strongBox = player.getGameSpace().getStrongbox();
                ArrayList<Resource> resourcesToRemove = new ArrayList<>();
                for(TypeResource resource: msg.getResources()){
                    resourcesToRemove.add(new Resource(resource));
                }
                try {
                    strongBox.removeResources(resourcesToRemove);
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg) {
        if(msg.getUsername().equals(player.getUsername())){
            receivedResources.add(new Resource(msg.getResource()));
        }
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
    public void receiveMsg(CMoveResourceInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

    }



    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

}
