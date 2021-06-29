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

/**
 * After initialization, the scene changes to the PersonalBoardScene which is the main scene where the game
 * is concentrated.
 */
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

    /**
     * When the PersonalBoardScene is set, this method prepares it, rendering every element,
     * like pop-up, panes or selectable images, hidden and updating the view situation of the various components
     * of the Personal Board
     */
    public void start(){
        actionLabel.setVisible(false);
        updateFaithTrackView(gui.getPlayer().getGameSpace().getFaithTrack());
        if(gui.getSoloMode()){
            updateBlackFaithMarkerView(gui.getPlayer().getGameSpace());
        }
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

    /**
     * It's the player turn so the message of wait is hidden and
     * a pop-up with all possible actions that the player can do is shown
     * @param msg VChooseActionTurnRequestMsg
     */
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

    /**
     * The player has chosen the BUY_CARD action, send a message to Server/Message Handler.
     * The execution of the action is managed in the DevCardTableScene and DevCardTableSceneController.
     */
    public void clickBuyCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    //BUY FROM MARKET

    /**
     * The player has chosen the BUY_FROM_MARKET action, send a message to Server/Message Handler.
     * The execution of the action is managed in the MarketStructureScene and MarketStructureSceneController.
     */
    public void clickBuyFromMarket(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_FROM_MARKET);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    //PRODUCTION POWER

    /**
     * The player has chosen the ACTIVATE_PRODUCTION_POWER action, send a message to Server/Message Handler.
     * Save the action in the controller
     */
    public void clickActivatePP(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_PRODUCTION_POWER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.ACTIVE_PRODUCTION_POWER;
    }

    /**
     * When the player choose to activate some production powers, if there's activatable some Production Power,
     * the controller highlight the warehouse and the strongbox to allow to choose where take resources to pay
     * the power and the StopPP Button is shown in case the player has finished.
     * If not the StopPP Button is hidden and a message to alert the player is shown.
     * The activatable Production Powers are saved in the controller, because they are useful for others method.
     * @param msg VActivateProductionPowerRequestMsg
     */
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
            setLabelText(errorMessage,"No available\n Production Power");
        }
    }

    /**
     * When the player has chosen where to take the resources, based on the Activatable Production Power
     * memorized in the controller, this last one highlight the StandardPP, the card spaces and/or, if
     * there are, the additional power of the leader cards.
     */
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

    /**
     * If the StopPP Button is clicked, the client send a message to the Server/Message Handler to end the choices
     * and executes the chosen ones.
     * All panes visible are disabled.
     */
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

    /**
     * When the mouse enters on the "StrongBox" Pane
     */
    public void mouseEnteredStrongBoxPane(){
        if(!strongBoxPane.isDisable()){
            strongBoxPane.setEffect(new Glow());
        }
    }

    /**
     * When the mouse exits from the "StrongBox" Pane
     */
    public void mouseExitedStrongBoxPane(){
        if(!strongBoxPane.isDisable()){
            strongBoxPane.setEffect(null);
        }
    }

    /**
     * When the player clicks on the strongbox pane, the controller memorize that he choose
     * to take the resources to activate the production power from the strongbox, then disables and
     * hides all panes
     */
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

    /**
     * When the mouse enters in the "StandardPP" Pane
     */
    public void mouseEnteredStandardPPPane(){
        if(!standardPPPane.isDisable()){
            standardPPPane.setEffect(new Glow());
        }
    }

    /**
     * When the mouse exits from the "StandardPP" Pane
     */
    public void mouseExitedStandardPPPane(){
        if(!standardPPPane.isDisable()){
            standardPPPane.setEffect(null);
        }
    }

    /**
     * When the player clicks on the "StandardPP" Pane, the controller memorizes the choice disable all
     * production power panes/images and prepares the pop-up to choose the resources to pay and to receive.
     */
    public void clickStandardPPPane(){
        if(!standardPPPane.isDisable()){
            whichPP=0;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose first resource to remove");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }

    /**
     * When the mouse enters on the "SpecialCard1" image, which is the first additional power
     */
    public void mouseEnteredSpecialCard1(){
        if(!specialCard1.isDisable()){
            specialCard1.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "SpecialCard1" image, which is the first additional power
     */
    public void mouseExitedSpecialCard1(){
        if(!specialCard1.isDisable()){
            specialCard1.setEffect(null);
        }
    }
    /**
     * When the player clicks on the "SpecialCard1" image, which is the first additional power,
     * the choice is memorized in the controller and prepares a pop-up to make the player choose
     * the resource to received.
     */
    public void clickSpecialCard1(){
        if(!specialCard1.isDisable()) {
            whichPP = 4;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose the resource you want");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }
    /**
     * When the mouse enters on the "SpecialCard2" image, which is the second additional power
     */
    public void mouseEnteredSpecialCard2(){
        if(!specialCard2.isDisable()){
            specialCard2.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "SpecialCard2" image, which is the second additional power
     */
    public void mouseExitedSpecialCard2(){
        if(!specialCard2.isDisable()){
            specialCard2.setEffect(null);
        }
    }
    /**
     * When the mouse enters on the "SpecialCard2" image, which is the second additional power,
     * the choice is memorized in the controller and prepares a pop-up to make the player choose
     * the resource to received.
     */
    public void clickSpecialCard2(){
        if(!specialCard2.isDisable()) {
            whichPP = 5;
            disablePP();
            setLabelText(chooseResourceLabel,"Choose the resource you want");
            chooseResourcePane.setVisible(true);
            actionLabel.setVisible(false);
        }
    }

    /**
     * When the mouse enters on the "COIN" image
     */
    public void mouseEnteredCoin(){
        if(!coin.isDisable()){
            coin.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on the "SHIELD" image
     */
    public void mouseEnteredShield(){
        if(!shield.isDisable()){
            shield.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on the "STONE" image
     */
    public void mouseEnteredStone(){
        if(!stone.isDisable()){
            stone.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on the "SERVANT" image
     */
    public void mouseEnteredServant(){
        if(!servant.isDisable()){
            servant.setEffect(new Glow());
        }
    }

    /**
     * When the mouse exits from the "COIN" image
     */
    public void mouseExitedCoin(){
        if(!coin.isDisable()){
            coin.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "SHIELD" image
     */
    public void mouseExitedShield(){
        if(!shield.isDisable()){
            shield.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "STONE" image
     */
    public void mouseExitedStone(){
        if(!stone.isDisable()){
            stone.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "SERVANT" image
     */
    public void mouseExitedServant(){
        if(!servant.isDisable()){
            servant.setEffect(null);
        }
    }

    /**
     * When the player clicks on the "COIN" image, based on the choices made the resources chosen are
     * memorized and the pop-up compares one or more time.
     * When the player has made all resource choices the response message of the production power with all
     * choices made is send to server/message handler.
     */
    public void clickCoin(){
        if(!coin.isDisable()){
            if(whichPP==0 && resourcesToRemove.size()==0){
                //STANDARD PP AND 0 RESOURCES ALREADY CHOSEN, SO CHOSEN THE FIRST RESOURCE
                resourcesToRemove.add(TypeResource.COIN);
                setLabelText(chooseResourceLabel,"Choose second resource to remove");
            }else if(whichPP==0 && resourcesToRemove.size()==1){
                //STANDARD PP AND 1 RESOURCES ALREADY CHOSEN, SO CHOSEN THE SECOND RESOURCE
                resourcesToRemove.add(TypeResource.COIN);
                setLabelText(chooseResourceLabel,"Choose the resource you want to receive");
            }else if(whichPP==0 && resourcesToRemove.size()==2){
                //STANDARD PP AND 2 RESOURCES ALREADY CHOSEN, SO CHOSEN THE THIRD RESOURCE AND SEND MESSAGE
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
                //ADDITIONAL POWER CHOSEN, SO CHOSEN THE UNIQUE RESOURCE TO RECEIVE AND SEND MSG
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
    /**
     * When the player clicks on the "SHIELD" image, based on the choices made the resources chosen are
     * memorized and the pop-up compares one or more time.
     * When the player has made all resource choices the response message of the production power with all
     * choices made is send to server/message handler.
     */
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
    /**
     * When the player clicks on the "STONE" image, based on the choices made the resources chosen are
     * memorized and the pop-up compares one or more time.
     * When the player has made all resource choices the response message of the production power with all
     * choices made is send to server/message handler.
     */
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
    /**
     * When the player clicks on the "SERVANT" image, based on the choices made the resources chosen are
     * memorized and the pop-up compares one or more time.
     * When the player has made all resource choices the response message of the production power with all
     * choices made is send to server/message handler.
     */
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

    /**
     * When the mouse enters on the "Card Space 1" image
     */
    public void mouseEnteredCardSpace1(){
        if(!cardSpace1.isDisable()){
            cardSpace1.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on the "Card Space 2" image
     */
    public void mouseEnteredCardSpace2(){
        if(!cardSpace2.isDisable()){
            cardSpace2.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on the "Card Space 3" image
     */
    public void mouseEnteredCardSpace3(){
        if(!cardSpace3.isDisable()){
            cardSpace3.setEffect(new Glow());
        }
    }

    /**
     * When the mouse exits from the "Card Space 1" image
     */
    public void mouseExitedCardSpace1(){
        if(!cardSpace1.isDisable()){
            cardSpace1.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Card Space 2" image
     */
    public void mouseExitedCardSpace2(){
        if(!cardSpace2.isDisable()){
            cardSpace2.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Card Space 3" image
     */
    public void mouseExitedCardSpace3(){
        if(!cardSpace3.isDisable()){
            cardSpace3.setEffect(null);
        }
    }
    /**
     * When the player clicks on the "Card Space 1" image, send a response
     * message of the production power with the card space chosen and disable all Production Power panes and images
     */
    public void clickCardSpace1(){
        if(!cardSpace1.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,1);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }
    /**
     * When the player clicks on the "Card Space 2" image, send a response
     * message of the production power with the card space chosen and disable all Production Power panes and images
     */
    public void clickCardSpace2(){
        if(!cardSpace2.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,2);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }
    /**
     * When the player clicks on the "Card Space 3" image, send a response
     * message of the production power with the card space chosen and disable all Production Power panes and images
     */
    public void clickCardSpace3(){
        if(!cardSpace3.isDisable()){
            response=new CActivateProductionPowerResponseMsg("I choose my production power",gui.getUsername(),where,3);
            disablePP();
            gui.sendMsg(response);
            actionLabel.setVisible(false);
        }
    }

    /**
     * To disable and hide all production power panes and images
     */
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

    /**
     * The player has chosen the MOVE_RESOURCES action, send a message to server/message handler.
     * The action is saved in the controller.
     */
    public void clickMoveResources(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.MOVE_RESOURCE);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.MOVE_RESOURCE;
    }

    /**
     * The depots panes became visible and active to allow the player to choose the "from" depot.
     */
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

    /**
     * When the mouse enters in the "Depot 1" Pane
     */
    public void mouseEnteredDepot1Pane(){
        if(!depot1Pane.isDisable()){
            depot1Pane.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 2" Pane
     */
    public void mouseEnteredDepot2Pane(){
        if(!depot2Pane.isDisable()){
            depot2Pane.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 3" Pane
     */
    public void mouseEnteredDepot3Pane(){
        if(!depot3Pane.isDisable()){
            depot3Pane.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 4" Pane
     */
    public void mouseEnteredDepot4Pane(){
        if(!depot4Pane.isDisable()){
            depot4Pane.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 5" Pane
     */
    public void mouseEnteredDepot5Pane(){
        if(!depot5Pane.isDisable()){
            depot5Pane.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "Depot 1" Pane
     */
    public void mouseExitedDepot1Pane(){
        if(!depot1Pane.isDisable()){
            depot1Pane.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 2" Pane
     */
    public void mouseExitedDepot2Pane(){
        if(!depot2Pane.isDisable()){
            depot2Pane.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 3" Pane
     */
    public void mouseExitedDepot3Pane(){
        if(!depot3Pane.isDisable()){
            depot3Pane.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 4" Pane
     */
    public void mouseExitedDepot4Pane(){
        if(!depot4Pane.isDisable()){
            depot4Pane.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 5" Pane
     */
    public void mouseExitedDepot5Pane(){
        if(!depot5Pane.isDisable()){
            depot5Pane.setEffect(null);
        }
    }
    /**
     * When the player clicks on the "Depot 1" Pane:
     * - if the action memorized is "MOVE_RESOURCE", saved the depot choice in the controller and if
     * it is the second depot chosen send a message to server/message handler to make the move, if not
     * allows the player to choose the second depot. In addition if the returnToMarket is true that means the
     * move resource action was made in the MarketStructureScene so the scene is changed to the MarketStructureScene.
     * - if the action memorized is "ACTIVATE_PRODUCTION_POWER", the controller memorize that the resources to
     * take when the production power is execute must be from the warehouse, then disable the warehouse and strongbox
     * panes.
     */
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
    /**
     * When the player clicks on the "Depot 2" Pane:
     * - if the action memorized is "MOVE_RESOURCE", saved the depot choice in the controller and if
     * it is the second depot chosen send a message to server/message handler to make the move, if not
     * allows the player to choose the second depot. In addition if the returnToMarket is true that means the
     * move resource action was made in the MarketStructureScene so the scene is changed to the MarketStructureScene.
     * - if the action memorized is "ACTIVATE_PRODUCTION_POWER", the controller memorize that the resources to
     * take when the production power is execute must be from the warehouse, then disable the warehouse and strongbox
     * panes.
     */
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
    /**
     * When the player clicks on the "Depot 3" Pane:
     * - if the action memorized is "MOVE_RESOURCE", saved the depot choice in the controller and if
     * it is the second depot chosen send a message to server/message handler to make the move, if not
     * allows the player to choose the second depot. In addition if the returnToMarket is true that means the
     * move resource action was made in the MarketStructureScene so the scene is changed to the MarketStructureScene.
     * - if the action memorized is "ACTIVATE_PRODUCTION_POWER", the controller memorize that the resources to
     * take when the production power is execute must be from the warehouse, then disable the warehouse and strongbox
     * panes.
     */
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
    /**
     * When the player clicks on the "Depot 4" Pane:
     * - if the action memorized is "MOVE_RESOURCE", saved the depot choice in the controller and if
     * it is the second depot chosen send a message to server/message handler to make the move, if not
     * allows the player to choose the second depot. In addition if the returnToMarket is true that means the
     * move resource action was made in the MarketStructureScene so the scene is changed to the MarketStructureScene.
     * - if the action memorized is "ACTIVATE_PRODUCTION_POWER", the controller memorize that the resources to
     * take when the production power is execute must be from the warehouse, then disable the warehouse and strongbox
     * panes.
     */
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
    /**
     * When the player clicks on the "Depot 5" Pane:
     * - if the action memorized is "MOVE_RESOURCE", saved the depot choice in the controller and if
     * it is the second depot chosen send a message to server/message handler to make the move, if not
     * allows the player to choose the second depot. In addition if the returnToMarket is true that means the
     * move resource action was made in the MarketStructureScene so the scene is changed to the MarketStructureScene.
     * - if the action memorized is "ACTIVATE_PRODUCTION_POWER", the controller memorize that the resources to
     * take when the production power is execute must be from the warehouse, then disable the warehouse and strongbox
     * panes.
     */
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

    /**
     * The player has chosen the "ACTIVE_LEADER_CARD" action, so notify to server/message handler and save
     * the action in the controller.
     */
    public void clickActivateLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.ACTIVE_LEADER_CARD;
    }
    /**
     * The player has chosen the "REMOVE_LEADER_CARD" action, so notify to server/message handler and save
     * the action in the controller.
     */
    public void clickDiscardLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.REMOVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
        action=TurnAction.REMOVE_LEADER_CARD;
    }

    /**
     * When the player choose to activate or discard a leader card, he receive through a message which can be
     * activate/removed, so the controller highlights with which ones he can interact.
     * @param msg VChooseLeaderCardRequestMsg
     */
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

    /**
     * When the player clicks on the OK button of the alert message, based on the action, notify the
     * server/message handler, set the action to null and close the alert message.
     * If the alert message notify a SERVER UNABLE error,
     * the GUI is automatically closed.
     */
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

    /**
     * When the mouse enters on the "Leader Card 1" image
     */
    public void mouseEnteredLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "Leader Card 1" image
     */
    public void mouseExitedLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(null);
        }
    }
    /**
     * When the mouse enters on the "Leader Card 2" image
     */
    public void mouseEnteredLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "Leader Card 2" image
     */
    public void mouseExitedLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(null);
        }

    }

    /**
     * When the player clicks on the "Leader Card 1" image, send a message to activate/discard the chosen
     * leader card and, if it is a remove action, cover the card.
     */
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
    /**
     * When the player clicks on the "Leader Card 2" image, send a message to activate/discard the chosen
     * leader card and, if it is a remove action, cover the card.
     */
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
    /**
     * The player has chosen the "SEE_OTHER_PERSONALBOARD" action, notify the server/message handler.
     */
    public void clickVisitOtherPersonalBoard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.SEE_OTHER_PLAYER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    /**
     * After chosen to visit another player, the server replies with a list of player that he can see,
     * so this method make a pop-up with the list of player to see.
     * @param players the list of player.
     */
    public void chooseOtherPlayer(ArrayList<String> players){
        otherPlayers=players;
        for(int i=0;i<players.size();i++){
            setButtonText(getOtherPlayersButtons().get(i),"Player "+(i+1)+": "+players.get(i));
            getOtherPlayersButtons().get(i).setDisable(false);
        }
        chooseOtherPlayerPane.setVisible(true);
    }

    /**
     * When the client clicks on the "Player 1" button, notify the server which player wants to see and
     * hide the pop-up
     */
    public void clickPlayer1Button(){
        if(!player1Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(0)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }
    /**
     * When the client clicks on the "Player 2" button, notify the server which player wants to see and
     * hide the pop-up
     */
    public void clickPlayer2Button(){
        if(!player2Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(1)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }
    /**
     * When the client clicks on the "Player 3" button, notify the server which player wants to see and
     * hide the pop-up
     */
    public void clickPlayer3Button(){
        if(!player3Button.isDisable()){
            gui.sendMsg(new CAskSeeSomeoneElseMsg("I want to see player",gui.getUsername(),otherPlayers.get(2)));
            otherPlayers=new ArrayList<>();
            chooseOtherPlayerPane.setVisible(false);
            disableOtherPlayersButtons();
        }
    }

    //END TURN

    /**
     * The player has chosen to end his turn, so notify the server/message handler and hide the actions pop-up
     */
    public void clickEndTurn(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.END_TURN);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }

    /**
     * The player can see the MarketStructureScene without interact with it.
     */
    public void clickSeeMarketBoardButton(){
        gui.getMarketStructureSceneController().update(gui.getMarketStructureData());
        gui.seeMarketBoard();
    }
    /**
     * The player can see the DevCardTableScene without interact with it.
     */
    public void clickSeeDevCardTableButton(){
        gui.getDevCardTableSceneController().setAllCardNormal();
        gui.getDevCardTableSceneController().setJustSee(true);
        gui.seeDevCardTable();
    }

    /**
     * To set the GUI which refers to.
     * @param gui the GUI of the player
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * To disable every action buttons.
     */
    private void disableActionButtons(){
        for(Button button:getActionButtons()){
            button.setDisable(true);
        }
    }

    //Update FXML Elements based on Model Elements

    /**
     * To update the leader cards view of the personal board based on the leader cards data of the player
     * in the model
     * @param leaderCards the leader cards in the model.
     */
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

    /**
     * To update the faithtrack view of the personal board based on the faithtrack data of the player in the model
     * @param faithTrack the faithtrack in the model
     */
    public void updateFaithTrackView(FaithTrack faithTrack){
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
            if(!gui.getSoloMode()) {
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

    /**
     * To update the Black Faith Marker in the Personal Board based on the position of the Black Faith Marker in the model
     * @param personalBoard the personal board to access at the position of Lorenzo
     */
    public void updateBlackFaithMarkerView(PersonalBoard personalBoard){
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
            }
    }

    /**
     * To update the resource manager view based on the resource manager in the model
     * @param resourceManager the resource manager in the model
     */
    public void updateResourceManagerView(ResourceManager resourceManager){
        updateWarehouseView(resourceManager.getWarehouse());
        updateStrongBoxView(resourceManager.getStrongBox());
    }

    /**
     * To update the warehouse view based on the warehouse in the model
     * @param warehouse the warehouse in the model
     */
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

    /**
     * To update the strongbox view based on the strongbox in the model
     * @param strongBox the strongbox in the model
     */
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

    /**
     * To update the card spaces view based on the card spaces in the model
     * @param cardSpaces the card spaces in the model
     */
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

    /**
     * Update the special cards/additional power view based on the special cards/additional power in the model
     * @param specialCards the special cards/additional power in the model
     */
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

    /**
     * Update the count of the victory points view based on the count of the victory points in the model
     * @param victoryPoints the victory points in the model
     */
    public void updateVictoryPointsView(int victoryPoints){
        setLabelText(this.victoryPoints,""+victoryPoints);
    }

    /**
     * Update the action token view based on the last action token drew.
     * @param msg VActionTokenActivateMsg
     */
    public void updateLastActionToken(VActionTokenActivateMsg msg){
        lastActionToken.setImage(new Image("/images/punchboard/actiontoken ("+msg.getActionToken().getCardID()+").png"));
        if(!lastActionTokenPane.isVisible()){
            lastActionTokenPane.setVisible(true);
        }
    }

    /**
     * To activate the action buttons
     * @param activatableActions the list of the activatable actions
     */
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

    /**
     * @return a list of all action buttons
     */
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

    /**
     * @return a list of black markers images
     */
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

    /**
     * @return a list of faith markers images
     */
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

    /**
     * @return a list of pope's favor tiles images
     */
    private ArrayList<ImageView> getPopesFavorTilesView(){
        ArrayList<ImageView> popesFavorTilesView = new ArrayList<>();
        popesFavorTilesView.add(popesFavorTile1);
        popesFavorTilesView.add(popesFavorTile2);
        popesFavorTilesView.add(popesFavorTile3);
        return popesFavorTilesView;
    }

    /**
     * @return the structure of images of the warehouse view
     */
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

    /**
     * @return a map with all label of the strongbox referring to the type
     */
    private HashMap<TypeResource,Label> getStrongboxLabelsView(){
        HashMap<TypeResource,Label> strongboxLabel = new HashMap<>();
        strongboxLabel.put(TypeResource.SERVANT,servantLabel);
        strongboxLabel.put(TypeResource.SHIELD,shieldLabel);
        strongboxLabel.put(TypeResource.COIN,coinLabel);
        strongboxLabel.put(TypeResource.STONE,stoneLabel);
        return strongboxLabel;
    }

    /**
     * @return a list of the card spaces images
     */
    public ArrayList<ImageView> getCardSpacesView(){
        ArrayList<ImageView> cardSpaceView = new ArrayList<>();
        cardSpaceView.add(cardSpace1);
        cardSpaceView.add(cardSpace2);
        cardSpaceView.add(cardSpace3);
        return cardSpaceView;
    }

    /**
     * @return a list of the leader cards images
     */
    private ArrayList<ImageView> getLeaderCardsView(){
        ArrayList<ImageView> leaderCardsView = new ArrayList<>();
        leaderCardsView.add(leaderCard1);
        leaderCardsView.add(leaderCard2);
        return leaderCardsView;
    }

    /**
     * @return a list of the special depots images
     */
    public ArrayList<ImageView> getSpecialDepotsView(){
        ArrayList<ImageView> specialDepotsView = new ArrayList<>();
        specialDepotsView.add(specialDepot1);
        specialDepotsView.add(specialDepot2);
        return specialDepotsView;
    }

    /**
     * @return a list of the depot panes
     */
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1Pane);
        depotPanes.add(depot2Pane);
        depotPanes.add(depot3Pane);
        depotPanes.add(depot4Pane);
        depotPanes.add(depot5Pane);
        return depotPanes;
    }

    /**
     * To disable the depot panes
     */
    private void disableDepotPanes(){
        for(Pane pane:getDepotPanes()){
            pane.setDisable(true);
        }
    }

    /**
     * To hide the first three depot panes
     */
    private void notVisibleDepotPanes(){
        for(int i=0;i<3;i++){
            getDepotPanes().get(i).setVisible(false);
        }
    }

    /**
     * To set the turn action in the controller
     * @param action which turn action
     */
    public void setTurnAction(TurnAction action){this.action =action;}

    /**
     * To disable the leader cards images
     */
    private void setAllLeaderDisable(){
        for(ImageView image: getLeaderCardsView()){
            image.setDisable(true);
        }
    }

    /**
     * To set the returnToMarket boolean
     * @param returnToMarket if true the controller should return in the MarketStructureScene
     */
    public void setReturnToMarket(boolean returnToMarket) {
        this.returnToMarket = returnToMarket;
    }

    /**
     * To disable the card spaces images
     */
    private void disableCardSpaces(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        for(ImageView imageView: getCardSpacesView()){
            imageView.setDisable(true);
            imageView.setEffect(colorAdjust);
        }
    }

    /**
     * To set a label text
     * @param label the label to change
     * @param content the text to set
     */
    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }

    /**
     * @return a list of the players buttons
     */
    private ArrayList<Button> getOtherPlayersButtons(){
        ArrayList<Button> otherPlayersButtons=new ArrayList<>();
        otherPlayersButtons.add(player1Button);
        otherPlayersButtons.add(player2Button);
        otherPlayersButtons.add(player3Button);
        return otherPlayersButtons;
    }

    /**
     * To disable the players buttons
     */
    private void disableOtherPlayersButtons(){
        for(Button button:getOtherPlayersButtons()){
            button.setDisable(true);
        }
    }

    /**
     * To set a button text
     * @param button the button to change
     * @param content the text to set
     */
    private void setButtonText(Button button,String content){
        Platform.runLater(()->{
            button.setText(content);
        });
    }

    /**
     * To show a message of wait
     */
    public void showWaitPane(){
        waitPane.setVisible(true);
    }

    /**
     * To get the leader card image by the ID
     * @param id the ID of the leader card in the model
     * @return the leader card if the id exists
     */
    private ImageView getLeaderCardViewByID(Integer id){
        if(leaderCardWithID.keySet().contains(id)){
            return leaderCardWithID.get(id);
        }else{
            return null;
        }
    }

    /**
     * @return a list of special cards images
     */
    private ArrayList<ImageView> getSpecialCardsView(){
        ArrayList<ImageView> specialCardsView=new ArrayList<>();
        specialCardsView.add(specialCard1);
        specialCardsView.add(specialCard2);
        return specialCardsView;
    }

    /**
     * To set an alert message because a player is disconnected
     * @param msg CClientDisconnectedMsg
     */
    public void setWarningPane(CClientDisconnectedMsg msg){
        setLabelText(errorMessage,msg.getUsernameDisconnected()+" is disconnected!");
        errorMessagePane.setVisible(true);
        okButton.setDisable(false);
    }

    /**
     * To set an alert message because the server is unable, every actions is interrupt and every pop-up or pane
     * is hidden, then if the player clicks on the OK button the GUI closes automatically
     * @param msg VServerUnableMsg
     */
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
