package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.VActionTokenActivateMsg;
import it.polimi.ingsw.message.viewMsg.VActivateProductionPowerRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseActionTurnRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.Depot;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.LeaderCardDeck;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;


public class PersonalBoardSceneController {
    private GUI gui;

    @FXML
    private Button buyCardButton, buyFromMarketButton, moveResourceButton, endTurnButton, activePPButton, visitOtherBoardButton, activeLeaderCardButton, discardLeaderCardButton,okButton;

    @FXML
    private Label chooseActionMessage;

    @FXML
    private Pane backgroundBox0;

    @FXML
    private ImageView blackFaith0,blackFaith1,blackFaith2,blackFaith3,blackFaith4,blackFaith5,blackFaith6,blackFaith7,blackFaith8,blackFaith9,blackFaith10,blackFaith11,blackFaith12,blackFaith13,blackFaith14,blackFaith15,blackFaith16,blackFaith17,blackFaith18,blackFaith19,blackFaith20,blackFaith21,blackFaith22,blackFaith23,blackFaith24;

    @FXML
    private ImageView faithMarker0,faithMarker1,faithMarker2,faithMarker3,faithMarker4,faithMarker5,faithMarker6,faithMarker7,faithMarker8,faithMarker9,faithMarker10,faithMarker11,faithMarker12,faithMarker13,faithMarker14,faithMarker15,faithMarker16,faithMarker17,faithMarker18,faithMarker19,faithMarker20,faithMarker21,faithMarker22,faithMarker23,faithMarker24;

    @FXML
    private ImageView popesFavorTile1,popesFavorTile2,popesFavorTile3;

    @FXML
    private ImageView resource1_1,resource2_1,resource2_2,resource3_1,resource3_2,resource3_3,resource4_1,resource4_2,resource5_1,resource5_2;

    @FXML
    private Label servantLabel,coinLabel,shieldLabel,stoneLabel;

    @FXML
    private ImageView cardSpace1,cardSpace2,cardSpace3;

    @FXML
    private ImageView leaderCard1,leaderCard2;

    @FXML
    private ImageView specialDepot1,specialDepot2;

    @FXML
    private Pane depot1Pane,depot2Pane,depot3Pane,depot4Pane,depot5Pane;
    @FXML
    private Label victoryPoints;

    @FXML
    private TitledPane actionButtons,errorMessagePane;

    @FXML
    private Pane standardPPPane,strongBoxPane;

    @FXML
    private Label errorMessage;

    @FXML
    private Button stopPPPowerButton;

    @FXML
    private TitledPane chooseResourcePane;

    @FXML
    private Label chooseResourceLabel;

    @FXML
    private ImageView coin,servant,shield,stone;

    @FXML
    private TitledPane chooseOtherPlayerPane;

    @FXML
    private Button player1Button, player2Button, player3Button;

    @FXML
    private ImageView specialCard1,specialCard2;

    @FXML
    private TitledPane waitPane;

    @FXML
    private Pane lastActionTokenPane;

    @FXML
    private ImageView lastActionToken;

    @FXML
    private Label actionLabel;

    private boolean returnToMarket;
    private boolean actionButtonsVisible;

    private TurnAction action;
    private ArrayList<Integer> activatablePP;
    private String where;
    private Integer whichPP;
    private CActivateProductionPowerResponseMsg response;
    private ArrayList<TypeResource> resourcesToRemove=new ArrayList<>();

    private ArrayList<Integer> chosenDepots=new ArrayList<>();
    private ArrayList<String> otherPlayers=new ArrayList<>();

    private HashMap<Integer,ImageView> leaderCardWithID;

    public void start(){
        actionLabel.setVisible(false);
        updateFaithTrackView(gui.getPlayer().getGameSpace().getFaithTrack(),gui.getPlayer().getGameSpace());
        updateResourceManagerView(gui.getPlayer().getGameSpace().getResourceManager());
        updateCardSpacesView(gui.getPlayer().getGameSpace().getCardSpaces());
        updateVictoryPointsView(gui.getPlayer().getVictoryPoints());
        updateAdditionalPPView(gui.getPlayer().getSpecialCard());
        setAllLeaderDisable();
        disableActionButtons();
        disableDepotPanes();
        notVisibleDepotPanes();
        okButton.setDisable(true);
        errorMessagePane.setVisible(false);
        actionButtons.setVisible(false);
        strongBoxPane.setVisible(false);
        strongBoxPane.setDisable(true);
        disablePP();
        disableCardSpaces();
        stopPPPowerButton.setVisible(false);
        stopPPPowerButton.setDisable(true);
        chooseResourcePane.setVisible(false);
        chooseOtherPlayerPane.setVisible(false);
        disableOtherPlayersButtons();
        lastActionTokenPane.setVisible(false);
    }

    public void chooseAction(VChooseActionTurnRequestMsg msg){
        waitPane.setVisible(false);
        if(!returnToMarket) {
            setLabelText(chooseActionMessage,"These are your available actions.\nChoose one action:");

            actionButtons.setVisible(true);
            showActionButtons(msg.getAvailableActions());
        }else{
            returnToMarket=false;
        }
    }

    //BUY DEVELOPMENT CARD
    public void clickBuyCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    //BUY FROM MARKET
    public void clickBuyFromMarket(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_FROM_MARKET);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    //PRODUCTION POWER
    public void clickActivatePP(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_PRODUCTION_POWER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.ACTIVE_PRODUCTION_POWER;
    }

    public void choosePP(VActivateProductionPowerRequestMsg msg){
        activatablePP=msg.getActivatablePP();
        if(activatablePP.size()>0) {
            setLabelText(actionLabel,"Choose where to pay from");
            actionLabel.setVisible(true);
            chooseDepots();
            strongBoxPane.setVisible(true);
            strongBoxPane.setDisable(false);
            stopPPPowerButton.setVisible(true);
            stopPPPowerButton.setDisable(false);
        }else{
            stopPPPowerButton.setVisible(false);
            okButton.setDisable(false);
            errorMessagePane.setVisible(true);
            setLabelText(errorMessage,"No available Production Power");
        }
    }

    private void activatePP(){
        ArrayList<ImageView> cardSpacesView = getCardSpacesView();
        for(Integer i:activatablePP){
            if(i==0) {
                standardPPPane.setVisible(true);
                standardPPPane.setDisable(false);
            }else if(i>=1 && i<=3){
                cardSpacesView.get(i-1).setDisable(false);
                cardSpacesView.get(i-1).setEffect(null);
            }else if(i>=4 && i<=5){
                getSpecialCardsView().get(i-4).setDisable(false);
                getSpecialCardsView().get(i-4).setEffect(null);
            }
        }
    }

    public void clickStopPPPowerButton(){
        gui.sendMsg(new CStopPPMsg("I don't want to activate any production power anymore",gui.getUsername()));
        stopPPPowerButton.setDisable(true);
        stopPPPowerButton.setVisible(false);
        notVisibleDepotPanes();
        disableDepotPanes();
        strongBoxPane.setVisible(false);
        strongBoxPane.setDisable(true);
        action=null;
    }

    public void mouseEnteredStrongBoxPane(){
        if(!strongBoxPane.isDisable()){
            strongBoxPane.setEffect(new Glow());
        }
    }
    public void mouseExitedStrongBoxPane(){
        if(!strongBoxPane.isDisable()){
            strongBoxPane.setEffect(null);
        }
    }
    public void clickStrongBoxPane(){
        if(!strongBoxPane.isDisable()){
            where="strongbox";
            disableDepotPanes();
            strongBoxPane.setVisible(false);
            strongBoxPane.setDisable(true);
            activatePP();
            strongBoxPane.setDisable(true);
            strongBoxPane.setVisible(false);
            disableDepotPanes();
            setLabelText(actionLabel,"Choose the Production Power you want to activate");
            actionLabel.setVisible(true);
        }
    }

    public void mouseEnteredStandardPPPane(){
        if(!standardPPPane.isDisable()){
            standardPPPane.setEffect(new Glow());
        }
    }
    public void mouseExitedStandardPPPane(){
        if(!standardPPPane.isDisable()){
            standardPPPane.setEffect(null);
        }
    }
    public void clickStandardPPPane(){
        if(!standardPPPane.isDisable()){
            whichPP=0;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose first resource to remove");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }

    public void mouseEnteredSpecialCard1(){
        if(!specialCard1.isDisable()){
            specialCard1.setEffect(new Glow());
        }
    }
    public void mouseExitedSpecialCard1(){
        if(!specialCard1.isDisable()){
            specialCard1.setEffect(null);
        }
    }
    public void clickSpecialCard1(){
        if(!specialCard1.isDisable()) {
            whichPP = 4;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose the resource you want");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }
    public void mouseEnteredSpecialCard2(){
        if(!specialCard2.isDisable()){
            specialCard2.setEffect(new Glow());
        }
    }
    public void mouseExitedSpecialCard2(){
        if(!specialCard2.isDisable()){
            specialCard2.setEffect(null);
        }
    }
    public void clickSpecialCard2(){
        if(!specialCard2.isDisable()) {
            whichPP = 5;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose the resource you want");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }

    public void mouseEnteredCoin(){
        if(!coin.isDisable()){
            coin.setEffect(new Glow());
        }
    }
    public void mouseEnteredShield(){
        if(!shield.isDisable()){
            shield.setEffect(new Glow());
        }
    }
    public void mouseEnteredStone(){
        if(!stone.isDisable()){
            stone.setEffect(new Glow());
        }
    }
    public void mouseEnteredServant(){
        if(!servant.isDisable()){
            servant.setEffect(new Glow());
        }
    }

    public void mouseExitedCoin(){
        if(!coin.isDisable()){
            coin.setEffect(null);
        }
    }
    public void mouseExitedShield(){
        if(!shield.isDisable()){
            shield.setEffect(null);
        }
    }
    public void mouseExitedStone(){
        if(!stone.isDisable()){
            stone.setEffect(null);
        }
    }
    public void mouseExitedServant(){
        if(!servant.isDisable()){
            servant.setEffect(null);
        }
    }

    public void clickCoin(){
        if(!coin.isDisable()){
            if(whichPP==0 && resourcesToRemove.size()==0){
                resourcesToRemove.add(TypeResource.COIN);
                setLabelText(chooseResourceLabel,"Choose second resource to remove");
            }else if(whichPP==0 && resourcesToRemove.size()==1){
                resourcesToRemove.add(TypeResource.COIN);
                setLabelText(chooseResourceLabel,"Choose the resource you want to receive");
            }else if(whichPP==0 && resourcesToRemove.size()==2){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourcesToPay(resourcesToRemove);
                responseMsg.setResourceToGet(TypeResource.COIN);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                resourcesToRemove=new ArrayList<>();
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }else if((whichPP>=4 && whichPP<=5)){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourceToGet(TypeResource.COIN);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }
        }
    }
    public void clickShield(){
        if(!shield.isDisable()){
            if(whichPP==0 && resourcesToRemove.size()==0){
                resourcesToRemove.add(TypeResource.SHIELD);
                setLabelText(chooseResourceLabel,"Choose second resource to remove");
            }else if(whichPP==0 && resourcesToRemove.size()==1){
                resourcesToRemove.add(TypeResource.SHIELD);
                setLabelText(chooseResourceLabel,"Choose the resource you want to receive");
            }else if(whichPP==0 && resourcesToRemove.size()==2){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourcesToPay(resourcesToRemove);
                responseMsg.setResourceToGet(TypeResource.SHIELD);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                resourcesToRemove=new ArrayList<>();
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }else if((whichPP>=4 && whichPP<=5)){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourceToGet(TypeResource.SHIELD);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }
        }
    }
    public void clickStone(){
        if(!stone.isDisable()){
            if(whichPP==0 && resourcesToRemove.size()==0){
                resourcesToRemove.add(TypeResource.STONE);
                setLabelText(chooseResourceLabel,"Choose second resource to remove");
            }else if(whichPP==0 && resourcesToRemove.size()==1){
                resourcesToRemove.add(TypeResource.STONE);
                setLabelText(chooseResourceLabel,"Choose the resource you want to receive");
            }else if(whichPP==0 && resourcesToRemove.size()==2){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourcesToPay(resourcesToRemove);
                responseMsg.setResourceToGet(TypeResource.STONE);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                resourcesToRemove=new ArrayList<>();
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }else if((whichPP>=4 && whichPP<=5)){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourceToGet(TypeResource.STONE);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }
        }
    }
    public void clickServant(){
        if(!servant.isDisable()){
            if(whichPP==0 && resourcesToRemove.size()==0){
                resourcesToRemove.add(TypeResource.SERVANT);
                setLabelText(chooseResourceLabel,"Choose second resource to remove");
            }else if(whichPP==0 && resourcesToRemove.size()==1){
                resourcesToRemove.add(TypeResource.SERVANT);
                setLabelText(chooseResourceLabel,"Choose the resource you want to receive");
            }else if(whichPP==0 && resourcesToRemove.size()==2){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourcesToPay(resourcesToRemove);
                responseMsg.setResourceToGet(TypeResource.SERVANT);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                resourcesToRemove=new ArrayList<>();
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }else if((whichPP>=4 && whichPP<=5)){
                CActivateProductionPowerResponseMsg responseMsg = new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,whichPP);
                responseMsg.setResourceToGet(TypeResource.SERVANT);
                disableCardSpaces();
                standardPPPane.setVisible(false);
                standardPPPane.setDisable(true);
                chooseResourcePane.setVisible(false);
                gui.sendMsg(responseMsg);
            }
        }
    }

    public void mouseEnteredCardSpace1(){
        if(!cardSpace1.isDisable()){
            cardSpace1.setEffect(new Glow());
        }
    }
    public void mouseEnteredCardSpace2(){
        if(!cardSpace2.isDisable()){
            cardSpace2.setEffect(new Glow());
        }
    }
    public void mouseEnteredCardSpace3(){
        if(!cardSpace3.isDisable()){
            cardSpace3.setEffect(new Glow());
        }
    }

    public void mouseExitedCardSpace1(){
        if(!cardSpace1.isDisable()){
            cardSpace1.setEffect(null);
        }
    }
    public void mouseExitedCardSpace2(){
        if(!cardSpace2.isDisable()){
            cardSpace2.setEffect(null);
        }
    }
    public void mouseExitedCardSpace3(){
        if(!cardSpace3.isDisable()){
            cardSpace3.setEffect(null);
        }
    }

    public void clickCardSpace1(){
        if(!cardSpace1.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,1);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }
    public void clickCardSpace2(){
        if(!cardSpace2.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,2);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }
    public void clickCardSpace3(){
        if(!cardSpace1.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,3);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }

    private void disablePP(){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        standardPPPane.setVisible(false);
        standardPPPane.setDisable(true);
        for(ImageView cardSpaceView:getCardSpacesView()){
            cardSpaceView.setDisable(true);
            cardSpaceView.setEffect(colorAdjust);
        }
        for(ImageView specialCardView: getSpecialCardsView()){
            specialCardView.setDisable(true);
            specialCardView.setEffect(colorAdjust);
        }
    }

    //MOVE RESOURCES
    public void clickMoveResources(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.MOVE_RESOURCE);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.MOVE_RESOURCE;
    }

    public void chooseDepots(){
        if(actionButtons.isVisible()){
            actionButtons.setVisible(false);
            actionButtonsVisible=true;
        }
        ArrayList<ImageView> specialDepotView = getSpecialDepotsView();
        ArrayList<Pane> depotPanes=getDepotPanes();
        for(int i=0;i<5;i++){
            if((i+1==4||i+1==5) && specialDepotView.get(i-3).isVisible()){
                depotPanes.get(i).setDisable(false);
            }else{
                depotPanes.get(i).setDisable(false);
                depotPanes.get(i).setVisible(true);
            }
        }
        setLabelText(actionLabel,"Choose the depot where to move the resource from");
        actionLabel.setVisible(true);
    }

    public void mouseEnteredDepot1Pane(){
        if(!depot1Pane.isDisable()){
            depot1Pane.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot2Pane(){
        if(!depot2Pane.isDisable()){
            depot2Pane.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot3Pane(){
        if(!depot3Pane.isDisable()){
            depot3Pane.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot4Pane(){
        if(!depot4Pane.isDisable()){
            depot4Pane.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot5Pane(){
        if(!depot5Pane.isDisable()){
            depot5Pane.setEffect(new Glow());
        }
    }

    public void mouseExitedDepot1Pane(){
        if(!depot1Pane.isDisable()){
            depot1Pane.setEffect(null);
        }
    }
    public void mouseExitedDepot2Pane(){
        if(!depot2Pane.isDisable()){
            depot2Pane.setEffect(null);
        }
    }
    public void mouseExitedDepot3Pane(){
        if(!depot3Pane.isDisable()){
            depot3Pane.setEffect(null);
        }
    }
    public void mouseExitedDepot4Pane(){
        if(!depot4Pane.isDisable()){
            depot4Pane.setEffect(null);
        }
    }
    public void mouseExitedDepot5Pane(){
        if(!depot5Pane.isDisable()){
            depot5Pane.setEffect(null);
        }
    }

    public void clickDepot1Pane(){
        if(!depot1Pane.isDisable()){
            if(this.action.equals(TurnAction.MOVE_RESOURCE)){
                chosenDepots.add(1);
                setLabelText(actionLabel,"Choose the depot where to move the resource to");
                actionLabel.setVisible(true);
                if(chosenDepots.size()==2){
                    actionLabel.setVisible(false);
                    notVisibleDepotPanes();
                    disableDepotPanes();
                    gui.sendMsg(new CMoveResourceInfoMsg("I choose the depots",gui.getUsername(),chosenDepots.get(0),chosenDepots.get(1),true));
                    chosenDepots=new ArrayList<>();
                    action=null;
                    if(returnToMarket){
                        gui.seeMarketBoard();
                        gui.getMarketStructureSceneController().copyWarehouseFromPersonalBoard();
                        action=TurnAction.BUY_FROM_MARKET;
                        returnToMarket=!returnToMarket;
                    }
                    if(actionButtonsVisible){
                        actionButtonsVisible=false;
                        actionButtons.setVisible(true);
                    }
                }
            }else if(this.action.equals(TurnAction.ACTIVE_PRODUCTION_POWER)){
                where="warehouse";
                disableDepotPanes();
                strongBoxPane.setVisible(false);
                strongBoxPane.setDisable(true);
                activatePP();
                strongBoxPane.setDisable(true);
                strongBoxPane.setVisible(false);
                disableDepotPanes();
                notVisibleDepotPanes();
                setLabelText(actionLabel,"Choose the Production Power you want to activate");
                actionLabel.setVisible(true);
            }
        }
    }
    public void clickDepot2Pane(){
        if(!depot2Pane.isDisable()){
            if(this.action.equals(TurnAction.MOVE_RESOURCE)){
                chosenDepots.add(2);
                setLabelText(actionLabel,"Choose the depot where to move the resource to");
                actionLabel.setVisible(true);
                if(chosenDepots.size()==2){
                    actionLabel.setVisible(false);
                    notVisibleDepotPanes();
                    disableDepotPanes();
                    gui.sendMsg(new CMoveResourceInfoMsg("I choose the depots",gui.getUsername(),chosenDepots.get(0),chosenDepots.get(1),true));
                    chosenDepots=new ArrayList<>();
                    action=null;
                    if(returnToMarket){
                        gui.seeMarketBoard();
                        gui.getMarketStructureSceneController().copyWarehouseFromPersonalBoard();
                        action=TurnAction.BUY_FROM_MARKET;
                        returnToMarket=!returnToMarket;
                    }

                    if(actionButtonsVisible){
                        actionButtonsVisible=false;
                        actionButtons.setVisible(true);
                    }
                }
            }else if(this.action.equals(TurnAction.ACTIVE_PRODUCTION_POWER)){
                where="warehouse";
                disableDepotPanes();
                strongBoxPane.setVisible(false);
                strongBoxPane.setDisable(true);
                activatePP();
                strongBoxPane.setDisable(true);
                strongBoxPane.setVisible(false);
                disableDepotPanes();
                notVisibleDepotPanes();
                setLabelText(actionLabel,"Choose the Production Power you want to activate");
                actionLabel.setVisible(true);
            }
        }
    }
    public void clickDepot3Pane(){
        if(!depot3Pane.isDisable()){
            if(this.action.equals(TurnAction.MOVE_RESOURCE)){
                chosenDepots.add(3);
                setLabelText(actionLabel,"Choose the depot where to move the resource to");
                actionLabel.setVisible(true);
                if(chosenDepots.size()==2){
                    actionLabel.setVisible(false);
                    notVisibleDepotPanes();
                    disableDepotPanes();
                    gui.sendMsg(new CMoveResourceInfoMsg("I choose the depots",gui.getUsername(),chosenDepots.get(0),chosenDepots.get(1),true));
                    chosenDepots=new ArrayList<>();
                    action=null;
                    if(returnToMarket){
                        gui.seeMarketBoard();
                        gui.getMarketStructureSceneController().copyWarehouseFromPersonalBoard();
                        action=TurnAction.BUY_FROM_MARKET;
                        returnToMarket=!returnToMarket;
                    }
                    if(actionButtonsVisible){
                        actionButtonsVisible=false;
                        actionButtons.setVisible(true);
                    }
                }
            }else if(this.action.equals(TurnAction.ACTIVE_PRODUCTION_POWER)){
                where="warehouse";
                disableDepotPanes();
                strongBoxPane.setVisible(false);
                strongBoxPane.setDisable(true);
                activatePP();
                strongBoxPane.setDisable(true);
                strongBoxPane.setVisible(false);
                disableDepotPanes();
                notVisibleDepotPanes();
                setLabelText(actionLabel,"Choose the Production Power you want to activate");
                actionLabel.setVisible(true);
            }
        }
    }
    public void clickDepot4Pane(){
        if(!depot4Pane.isDisable()){
            if(this.action.equals(TurnAction.MOVE_RESOURCE)){
                chosenDepots.add(4);
                setLabelText(actionLabel,"Choose the depot where to move the resource to");
                actionLabel.setVisible(true);
                if(chosenDepots.size()==2){
                    actionLabel.setVisible(false);
                    notVisibleDepotPanes();
                    disableDepotPanes();
                    gui.sendMsg(new CMoveResourceInfoMsg("I choose the depots",gui.getUsername(),chosenDepots.get(0),chosenDepots.get(1),true));
                    chosenDepots=new ArrayList<>();
                    action=null;
                    if(returnToMarket){
                        gui.seeMarketBoard();
                        gui.getMarketStructureSceneController().copyWarehouseFromPersonalBoard();
                        action=TurnAction.BUY_FROM_MARKET;
                    }
                    if(actionButtonsVisible){
                        actionButtonsVisible=false;
                        actionButtons.setVisible(true);
                    }
                }
            }else if(this.action.equals(TurnAction.ACTIVE_PRODUCTION_POWER)){
                where="warehouse";
                disableDepotPanes();
                strongBoxPane.setVisible(false);
                strongBoxPane.setDisable(true);
                activatePP();
                strongBoxPane.setDisable(true);
                strongBoxPane.setVisible(false);
                disableDepotPanes();
                notVisibleDepotPanes();
                setLabelText(actionLabel,"Choose the Production Power you want to activate");
                actionLabel.setVisible(true);
            }
        }
    }
    public void clickDepot5Pane(){
        if(!depot5Pane.isDisable()){
            if(this.action.equals(TurnAction.MOVE_RESOURCE)){
                chosenDepots.add(5);
                setLabelText(actionLabel,"Choose the depot where to move the resource to");
                actionLabel.setVisible(true);
                if(chosenDepots.size()==2){
                    actionLabel.setVisible(false);
                    notVisibleDepotPanes();
                    disableDepotPanes();
                    gui.sendMsg(new CMoveResourceInfoMsg("I choose the depots",gui.getUsername(),chosenDepots.get(0),chosenDepots.get(1),true));
                    chosenDepots=new ArrayList<>();
                    action=null;
                    if(returnToMarket){
                        gui.seeMarketBoard();
                        gui.getMarketStructureSceneController().copyWarehouseFromPersonalBoard();
                        action=TurnAction.BUY_FROM_MARKET;
                    }
                    if(actionButtonsVisible){
                        actionButtonsVisible=false;
                        actionButtons.setVisible(true);
                    }
                }
            }else if(this.action.equals(TurnAction.ACTIVE_PRODUCTION_POWER)){
                where="warehouse";
                disableDepotPanes();
                strongBoxPane.setVisible(false);
                strongBoxPane.setDisable(true);
                activatePP();
                strongBoxPane.setDisable(true);
                strongBoxPane.setVisible(false);
                disableDepotPanes();
                notVisibleDepotPanes();
                setLabelText(actionLabel,"Choose the Production Power you want to activate");
                actionLabel.setVisible(true);
            }
        }
    }

    //ACTIVE OR DISCARD LEADER CARD ACTION
    public void clickActivateLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.ACTIVE_LEADER_CARD;
    }
    public void clickDiscardLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.REMOVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.REMOVE_LEADER_CARD;
    }

    public void chooseLeaderCard(VChooseLeaderCardRequestMsg msg){
        ArrayList<Integer> leaderCards=msg.getMiniDeckLeaderCardFour();
        if(msg.getMiniDeckLeaderCardFour().size()>0) {
            for(int i=0;i<leaderCards.size();i++){
                System.out.println("LeaderCardsView: "+leaderCards.get(i));
                getLeaderCardViewByID(leaderCards.get(i)).setDisable(false);
                getLeaderCardViewByID(leaderCards.get(i)).setEffect(null);
                setLabelText(actionLabel,"Choose leader card to "+msg.getWhatFor());
                actionLabel.setVisible(true);
            }
        }else{
            setLabelText(errorMessage,"No card to "+msg.getWhatFor());
            okButton.setDisable(false);
            errorMessagePane.setVisible(true);
        }
    }

    public void clickOKButton(){
        if(action==TurnAction.ACTIVE_LEADER_CARD||action==TurnAction.REMOVE_LEADER_CARD) {
            gui.sendMsg(new CChangeActionTurnMsg("Not possible to do action, I want to change action", gui.getUsername(), action));
        }else if(action==TurnAction.ACTIVE_PRODUCTION_POWER){
            gui.sendMsg(new CStopPPMsg("No Production Power Available",gui.getUsername()));
        }else if(!gui.isServerAvailable()){
            gui.close();
        }
        action = null;
        okButton.setDisable(true);
        errorMessagePane.setVisible(false);
        action=null;
    }

    public void mouseEnteredLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(new Glow());
        }
    }
    public void mouseExitedLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(null);
        }
    }

    public void mouseEnteredLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(new Glow());
        }
    }
    public void mouseExitedLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(null);
        }

    }

    public void clickLeaderCard1(){
        if(!leaderCard1.isDisable()){
            if(this.action.equals(TurnAction.ACTIVE_LEADER_CARD)){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose the leader card",gui.getLeaderCards().get(0).getCardID(),gui.getUsername(),"active"));
                leaderCard1.setEffect(null);
                leaderCard1.setDisable(true);
            }else if(this.action.equals(TurnAction.REMOVE_LEADER_CARD)){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose the leader card",gui.getLeaderCards().get(0).getCardID(),gui.getUsername(),"remove"));
                leaderCard1.setEffect(null);
                leaderCard1.setDisable(true);
                leaderCard1.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            }
            actionLabel.setVisible(false);
            action=null;
        }
    }
    public void clickLeaderCard2(){
        if(!leaderCard2.isDisable()){
            if(this.action.equals(TurnAction.ACTIVE_LEADER_CARD)){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose the leader card",gui.getLeaderCards().get(1).getCardID(),gui.getUsername(),"active"));
                leaderCard2.setEffect(null);
                leaderCard2.setDisable(true);
            }else if(this.action.equals(TurnAction.REMOVE_LEADER_CARD)){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose the leader card",gui.getLeaderCards().get(1).getCardID(),gui.getUsername(),"remove"));
                leaderCard2.setEffect(null);
                leaderCard2.setDisable(true);
                leaderCard2.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            }
            actionLabel.setVisible(false);
            action=null;
        }
    }

    //VISIT OTHER PLAYER
    public void clickVisitOtherPersonalBoard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.SEE_OTHER_PLAYER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    public void chooseOtherPlayer(ArrayList<String> players){
        otherPlayers=players;
        for(int i=0;i<players.size();i++){
            setButtonText(getOtherPlayersButtons().get(i),"Player "+(i+1)+": "+players.get(i));
            getOtherPlayersButtons().get(i).setDisable(false);
        }
        chooseOtherPlayerPane.setVisible(true);
    }

    public void clickPlayer1Button(){
        if(!player1Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(0)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }
    public void clickPlayer2Button(){
        if(!player2Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(1)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }
    public void clickPlayer3Button(){
        if(!player3Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(2)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }

    //END TURN
    public void clickEndTurn(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.END_TURN);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    public void clickSeeMarketBoardButton(){
        gui.seeMarketBoard();
    }
    public void clickSeeDevCardTableButton(){
        gui.getDevCardTableSceneController().setAllCardNormal();
        gui.seeDevCardTable();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    private void disableActionButtons(){
        for(Button button:getActionButtons()){
            button.setDisable(true);
        }
    }

    //Update FXML Elements based on Model Elements
    public void updateLeaderCards(ArrayList<LeaderCard> leaderCards){
        leaderCardWithID=new HashMap<>();
        for(int i=0;i<2;i++){
            if(i+1<=leaderCards.size()){
                System.out.println(leaderCards.get(i).getCardID());
                leaderCardWithID.put(leaderCards.get(i).getCardID(),getLeaderCardsView().get(i));
                getLeaderCardsView().get(i).setImage(new Image("/images/frontCards/LeaderCard ("+leaderCards.get(i).getCardID()+").png"));
            }else{
                getLeaderCardsView().get(i).setImage(new Image("/images/backCards/LeaderCard (1).png"));
            }
        }
    }

    public void updateFaithTrackView(FaithTrack faithTrack,PersonalBoard personalBoard){
        if(gui.getSoloMode()){
            SoloPersonalBoard soloPersonalBoard = (SoloPersonalBoard) personalBoard;
            for(int i=0;i<25;i++){
                if(soloPersonalBoard.getLorenzoIlMagnifico().getPosition()==i){
                    if(i==0){
                        backgroundBox0.setVisible(true);
                    }else{
                        backgroundBox0.setVisible(false);
                    }
                    getBlackMarkersView().get(i).setVisible(true);
                }else{
                    if(i==0){
                        backgroundBox0.setVisible(false);
                    }else{
                        backgroundBox0.setVisible(true);
                    }
                    getBlackMarkersView().get(i).setVisible(false);
                }
                if (faithTrack.getPositionFaithMarker() == i) {
                    if (i == 0) {
                        backgroundBox0.setVisible(true);
                    } else {
                        backgroundBox0.setVisible(false);
                    }
                    getFaithMarkersView().get(i).setVisible(true);
                } else {
                    if (i == 0) {
                        backgroundBox0.setVisible(false);
                    } else {
                        backgroundBox0.setVisible(true);
                    }
                    getFaithMarkersView().get(i).setVisible(false);
                }
            }
        } else {
            for (int i = 0; i < 25; i++) {
                if (faithTrack.getPositionFaithMarker() == i) {
                    if (i == 0) {
                        backgroundBox0.setVisible(true);
                    }
                    getFaithMarkersView().get(i).setVisible(true);
                } else {
                    if (i == 0) {
                        backgroundBox0.setVisible(false);
                    }
                    getFaithMarkersView().get(i).setVisible(false);
                }
                getBlackMarkersView().get(i).setVisible(false);
            }

        }
        for (int i = 0; i < 3; i++) {
            if (faithTrack.getPopesFavorTiles().get(i).getState() instanceof Active) {
                getPopesFavorTilesView().get(i).setImage(new Image("/images/punchboard/pope's_tile" + (i + 1) + "Active.png"));
            } else {
                getPopesFavorTilesView().get(i).setImage(new Image("/images/punchboard/pope's_tile" + (i + 1) + "Inactive.png"));
            }
        }

    }
    public void updateResourceManagerView(ResourceManager resourceManager){
        updateWarehouseView(resourceManager.getWarehouse());
        updateStrongBoxView(resourceManager.getStrongBox());
    }
    public void updateWarehouseView(Warehouse warehouse){
        for(int i=0;i<5;i++){
            if (i+1<=warehouse.getDepots().size()) {
                Depot depot = warehouse.getDepots().get(i);
                ArrayList<ImageView> depotView = getWarehouseView().get(i);
                if (i+1==4||i+1==5) {
                    switch (depot.getType()) {
                        case COIN:
                            getSpecialDepotsView().get(i-3).setImage(new Image("/images/personalBoard/SpecialDepotCoin.png"));
                            break;
                        case SERVANT:
                            getSpecialDepotsView().get(i-3).setImage(new Image("/images/personalBoard/SpecialDepotServant.png"));
                            break;
                        case STONE:
                            getSpecialDepotsView().get(i-3).setImage(new Image("/images/personalBoard/SpecialDepotStone.png"));
                            break;
                        case SHIELD:
                            getSpecialDepotsView().get(i-3).setImage(new Image("/images/personalBoard/SpecialDepotShield.png"));
                            break;
                    }
                    getDepotPanes().get(i).setVisible(true);
                    getSpecialDepotsView().get(i-3).setVisible(true);
                }
                for (int j = 0; j < depot.getSize(); j++) {
                    if (j + 1 <= depot.getNumberResources()) {
                        Resource resource = depot.getResources().get(j);
                        switch (resource.getType()) {
                            case COIN:
                                depotView.get(j).setImage(new Image("/images/punchboard/coin.png"));
                                break;
                            case SHIELD:
                                depotView.get(j).setImage(new Image("/images/punchboard/shield.png"));
                                break;
                            case SERVANT:
                                depotView.get(j).setImage(new Image("/images/punchboard/servant.png"));
                                break;
                            case STONE:
                                depotView.get(j).setImage(new Image("/images/punchboard/stone.png"));
                                break;
                        }
                        depotView.get(j).setVisible(true);
                    } else {
                        depotView.get(j).setImage(null);
                        depotView.get(j).setVisible(false);
                    }
                }
            }else{
                if(i==3||i==4){
                    getSpecialDepotsView().get(i-3).setVisible(false);
                    ArrayList<ImageView> depotView = getWarehouseView().get(i);
                    for(int k=0;k<2;k++){
                        depotView.get(k).setVisible(false);
                    }
                }
            }
        }
    }
    public void updateStrongBoxView(StrongBox strongBox){
        HashMap<TypeResource,Integer> countResourcesInStrongBox=new HashMap<>();
        int servant=0;
        int shield=0;
        int coin=0;
        int stone=0;
        for(Resource resource:strongBox.getContent()){
            switch(resource.getType()){
                case COIN:coin++;break;
                case SHIELD:shield++;break;
                case STONE:stone++;break;
                case SERVANT:servant++;break;
            }
        }
        setLabelText(getStrongboxLabelsView().get(TypeResource.COIN),""+coin);
        setLabelText(getStrongboxLabelsView().get(TypeResource.SHIELD),""+shield);
        setLabelText(getStrongboxLabelsView().get(TypeResource.SERVANT),""+servant);
        setLabelText(getStrongboxLabelsView().get(TypeResource.STONE),""+stone);
    }
    public void updateCardSpacesView(ArrayList<CardSpace> cardSpaces){
        for(int i=0;i<cardSpaces.size();i++){
            if(cardSpaces.get(i).getNumberOfCards()!=0){
                getCardSpacesView().get(i).setImage(new Image("/images/frontCards/DevelopmentCard ("+cardSpaces.get(i).getUpperCard().getId()+").png"));
                getCardSpacesView().get(i).setVisible(true);
            }else{
                getCardSpacesView().get(i).setImage(null);
                getCardSpacesView().get(i).setVisible(false);
            }
        }
    }

    public void updateAdditionalPPView(ArrayList<SpecialCard> specialCards){
        for(int i=0;i<2;i++){
            if(specialCards!=null && i+1<=specialCards.size()){
                String type="";
                switch(specialCards.get(i).getCostProductionPower().get(0).getType()){
                    case COIN:type="Coin";break;
                    case SHIELD:type="Shield";break;
                    case STONE:type="Stone";break;
                    case SERVANT:type="Servant";break;
                }
                getSpecialCardsView().get(i).setImage(new Image("/images/personalboard/AdditionalPower_"+type+".png"));
                getSpecialCardsView().get(i).setVisible(true);
            }else{
                getSpecialCardsView().get(i).setVisible(false);
            }
        }
    }

    public void updateVictoryPointsView(int victoryPoints){
        setLabelText(this.victoryPoints,""+victoryPoints);
    }

    public void updateLastActionToken(VActionTokenActivateMsg msg){
        lastActionToken.setImage(new Image("/images/punchboard/actiontoken ("+msg.getActionToken().getCardID()+").png"));
        if(!lastActionTokenPane.isVisible()){
            lastActionTokenPane.setVisible(true);
        }
    }

    private void showActionButtons(ArrayList<TurnAction> activatableActions){
        for(TurnAction action:activatableActions){
            switch(action){
                case BUY_CARD:buyCardButton.setDisable(false);break;
                case BUY_FROM_MARKET:buyFromMarketButton.setDisable(false);break;
                case ACTIVE_PRODUCTION_POWER:activePPButton.setDisable(false);break;
                case ACTIVE_LEADER_CARD:activeLeaderCardButton.setDisable(false);break;
                case REMOVE_LEADER_CARD:discardLeaderCardButton.setDisable(false);break;
                case MOVE_RESOURCE:moveResourceButton.setDisable(false);break;
                case SEE_OTHER_PLAYER:visitOtherBoardButton.setDisable(false);break;
                case END_TURN:endTurnButton.setDisable(false);break;
            }
        }
    }

    //Getter Method for FXML Elements
    private ArrayList<Button> getActionButtons(){
        ArrayList<Button> actionButtons = new ArrayList<>();
        actionButtons.add(buyCardButton);
        actionButtons.add(buyFromMarketButton);
        actionButtons.add(activePPButton);
        actionButtons.add(activeLeaderCardButton);
        actionButtons.add(discardLeaderCardButton);
        actionButtons.add(moveResourceButton);
        actionButtons.add(visitOtherBoardButton);
        actionButtons.add(endTurnButton);
        return actionButtons;
    }
    private ArrayList<ImageView> getBlackMarkersView(){
        ArrayList<ImageView> blackMarkersView=new ArrayList<>();
        blackMarkersView.add(blackFaith0);
        blackMarkersView.add(blackFaith1);
        blackMarkersView.add(blackFaith2);
        blackMarkersView.add(blackFaith3);
        blackMarkersView.add(blackFaith4);
        blackMarkersView.add(blackFaith5);
        blackMarkersView.add(blackFaith6);
        blackMarkersView.add(blackFaith7);
        blackMarkersView.add(blackFaith8);
        blackMarkersView.add(blackFaith9);
        blackMarkersView.add(blackFaith10);
        blackMarkersView.add(blackFaith11);
        blackMarkersView.add(blackFaith12);
        blackMarkersView.add(blackFaith13);
        blackMarkersView.add(blackFaith14);
        blackMarkersView.add(blackFaith15);
        blackMarkersView.add(blackFaith16);
        blackMarkersView.add(blackFaith17);
        blackMarkersView.add(blackFaith18);
        blackMarkersView.add(blackFaith19);
        blackMarkersView.add(blackFaith20);
        blackMarkersView.add(blackFaith21);
        blackMarkersView.add(blackFaith22);
        blackMarkersView.add(blackFaith23);
        blackMarkersView.add(blackFaith24);
        return blackMarkersView;


    }
    private ArrayList<ImageView> getFaithMarkersView(){
        ArrayList<ImageView> faithMarkerPositionsView = new ArrayList<>();
        faithMarkerPositionsView.add(faithMarker0);
        faithMarkerPositionsView.add(faithMarker1);
        faithMarkerPositionsView.add(faithMarker2);
        faithMarkerPositionsView.add(faithMarker3);
        faithMarkerPositionsView.add(faithMarker4);
        faithMarkerPositionsView.add(faithMarker5);
        faithMarkerPositionsView.add(faithMarker6);
        faithMarkerPositionsView.add(faithMarker7);
        faithMarkerPositionsView.add(faithMarker8);
        faithMarkerPositionsView.add(faithMarker9);
        faithMarkerPositionsView.add(faithMarker10);
        faithMarkerPositionsView.add(faithMarker11);
        faithMarkerPositionsView.add(faithMarker12);
        faithMarkerPositionsView.add(faithMarker13);
        faithMarkerPositionsView.add(faithMarker14);
        faithMarkerPositionsView.add(faithMarker15);
        faithMarkerPositionsView.add(faithMarker16);
        faithMarkerPositionsView.add(faithMarker17);
        faithMarkerPositionsView.add(faithMarker18);
        faithMarkerPositionsView.add(faithMarker19);
        faithMarkerPositionsView.add(faithMarker20);
        faithMarkerPositionsView.add(faithMarker21);
        faithMarkerPositionsView.add(faithMarker22);
        faithMarkerPositionsView.add(faithMarker23);
        faithMarkerPositionsView.add(faithMarker24);
        return faithMarkerPositionsView;
    }
    private ArrayList<ImageView> getPopesFavorTilesView(){
        ArrayList<ImageView> popesFavorTilesView = new ArrayList<>();
        popesFavorTilesView.add(popesFavorTile1);
        popesFavorTilesView.add(popesFavorTile2);
        popesFavorTilesView.add(popesFavorTile3);
        return popesFavorTilesView;
    }
    public ArrayList<ArrayList<ImageView>> getWarehouseView(){
        ArrayList<ArrayList<ImageView>> warehouseView= new ArrayList<>();

        ArrayList<ImageView> depot1View=new ArrayList<>();
        depot1View.add(resource1_1);

        ArrayList<ImageView> depot2View=new ArrayList<>();
        depot2View.add(resource2_1);
        depot2View.add(resource2_2);

        ArrayList<ImageView> depot3View=new ArrayList<>();
        depot3View.add(resource3_1);
        depot3View.add(resource3_2);
        depot3View.add(resource3_3);

        ArrayList<ImageView> depot4View=new ArrayList<>();
        depot4View.add(resource4_1);
        depot4View.add(resource4_2);

        ArrayList<ImageView> depot5View=new ArrayList<>();
        depot5View.add(resource5_1);
        depot5View.add(resource5_2);

        warehouseView.add(depot1View);
        warehouseView.add(depot2View);
        warehouseView.add(depot3View);
        warehouseView.add(depot4View);
        warehouseView.add(depot5View);

        return warehouseView;
    }
    private HashMap<TypeResource,Label> getStrongboxLabelsView(){
        HashMap<TypeResource,Label> strongboxLabel = new HashMap<>();
        strongboxLabel.put(TypeResource.SERVANT,servantLabel);
        strongboxLabel.put(TypeResource.SHIELD,shieldLabel);
        strongboxLabel.put(TypeResource.COIN,coinLabel);
        strongboxLabel.put(TypeResource.STONE,stoneLabel);
        return strongboxLabel;
    }
    public ArrayList<ImageView> getCardSpacesView(){
        ArrayList<ImageView> cardSpaceView = new ArrayList<>();
        cardSpaceView.add(cardSpace1);
        cardSpaceView.add(cardSpace2);
        cardSpaceView.add(cardSpace3);
        return cardSpaceView;
    }
    private ArrayList<ImageView> getLeaderCardsView(){
        ArrayList<ImageView> leaderCardsView = new ArrayList<>();
        leaderCardsView.add(leaderCard1);
        leaderCardsView.add(leaderCard2);
        return leaderCardsView;
    }
    public ArrayList<ImageView> getSpecialDepotsView(){
        ArrayList<ImageView> specialDepotsView = new ArrayList<>();
        specialDepotsView.add(specialDepot1);
        specialDepotsView.add(specialDepot2);
        return specialDepotsView;
    }
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1Pane);
        depotPanes.add(depot2Pane);
        depotPanes.add(depot3Pane);
        depotPanes.add(depot4Pane);
        depotPanes.add(depot5Pane);
        return depotPanes;
    }
    private void disableDepotPanes(){
        for(Pane pane:getDepotPanes()){
            pane.setDisable(true);
        }
    }
    private void notVisibleDepotPanes(){
        for(int i=0;i<3;i++){
            getDepotPanes().get(i).setVisible(false);
        }
    }
    public void setTurnAction(TurnAction action){this.action =action;}
    private void setAllLeaderDisable(){
        for(ImageView image: getLeaderCardsView()){
            image.setDisable(true);
        }
    }
    public void setReturnToMarket(boolean returnToMarket) {
        this.returnToMarket = returnToMarket;
    }
    public void setAction(TurnAction action){this.action=action;}
    private LeaderCard searchLeaderCardInDeckById(int id){
        if(id>=1 && id<=16) {
            LeaderCardDeck leaderCardDeck = gui.getLeaderCardsDeck();
            for (LeaderCard leaderCard : leaderCardDeck.getCards()) {
                if (id == leaderCard.getCardID()) {
                    return leaderCard;
                }
            }
        }
        return null;
    }

    private void disableCardSpaces(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        for(ImageView imageView: getCardSpacesView()){
            imageView.setDisable(true);
            imageView.setEffect(colorAdjust);
        }
    }

    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }
    private ArrayList<Button> getOtherPlayersButtons(){
        ArrayList<Button> otherPlayersButtons=new ArrayList<>();
        otherPlayersButtons.add(player1Button);
        otherPlayersButtons.add(player2Button);
        otherPlayersButtons.add(player3Button);
        return otherPlayersButtons;
    }

    private void disableOtherPlayersButtons(){
        for(Button button:getOtherPlayersButtons()){
            button.setDisable(true);
        }
    }
    private void setButtonText(Button button,String content){
        Platform.runLater(()->{
            button.setText(content);
        });
    }

    public void showWaitPane(){
        waitPane.setVisible(true);
    }
    private ImageView getLeaderCardViewByID(Integer id){
        if(leaderCardWithID.keySet().contains(id)){
            return leaderCardWithID.get(id);
        }else{
            return null;
        }
    }

    private ArrayList<ImageView> getSpecialCardsView(){
        ArrayList<ImageView> specialCardsView=new ArrayList<>();
        specialCardsView.add(specialCard1);
        specialCardsView.add(specialCard2);
        return specialCardsView;
    }

    public void setWarningPane(CClientDisconnectedMsg msg){
        setLabelText(errorMessage,msg.getUsernameDisconnected()+" is disconnected!");
        errorMessagePane.setVisible(true);
        okButton.setDisable(false);
    }

    public void setWarningPane(VServerUnableMsg msg){
        setLabelText(errorMessage,"Server unable!\n Sorry the game ends here!");
        actionButtons.setVisible(false);
        errorMessagePane.setVisible(true);
        okButton.setDisable(false);
        setAllLeaderDisable();
        disableActionButtons();
        disableDepotPanes();
        notVisibleDepotPanes();
        actionButtons.setVisible(false);
        strongBoxPane.setVisible(false);
        strongBoxPane.setDisable(true);
        disablePP();
        disableCardSpaces();
        stopPPPowerButton.setVisible(false);
        stopPPPowerButton.setDisable(true);
        chooseResourcePane.setVisible(false);
        chooseOtherPlayerPane.setVisible(false);
        disableOtherPlayersButtons();
    }
}
