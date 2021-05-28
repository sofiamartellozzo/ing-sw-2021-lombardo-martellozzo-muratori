package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CActivateProductionPowerResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseActionTurnResponseMsg;
import it.polimi.ingsw.message.viewMsg.VActivateProductionPowerRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseActionTurnRequestMsg;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.Depot;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;


public class PersonalBoardSceneController {
    private GUI gui;

    @FXML
    private Button buyCardButton, buyFromMarketButton, moveResourceButton, endTurnButton, activePPButton, visitOtherBoardButton, activeLeaderCardButton, discardLeaderCardButton;

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
    private Label specialDepotLabel;

    @FXML
    private Label victoryPoints;

    @FXML
    private TitledPane actionButtons;

    @FXML
    private Pane standardPPPane,cardSpace1PPPane,cardSpace2PPPane,cardSpace3PPPane,leaderCard1PPPane,leaderCard2PPPane,warehousePane,strongBoxPane;

    private int chosenPP;

    public void start(){
        updateFaithTrackView(gui.getPlayer().getGameSpace().getFaithTrack());
        updateResourceManagerView(gui.getPlayer().getGameSpace().getResourceManager());
        updateCardSpacesView(gui.getPlayer().getGameSpace().getCardSpaces());
        updateLeaderCardsView(gui.getPlayer().getLeaderCards());
        updateVictoryPointsView(gui.getPlayer().getVictoryPoints());
        disableActionButtons();
        actionButtons.setVisible(false);
        disableAndHidePPPanes();
        warehousePane.setVisible(false);
        warehousePane.setDisable(true);
        strongBoxPane.setVisible(false);
        strongBoxPane.setDisable(true);

    }

    public void setChooseActionMessage(String content){
        chooseActionMessage.setText(content);
    }
    public void chooseAction(VChooseActionTurnRequestMsg msg){
        setChooseActionMessage("These are your available actions.\nChoose one action:");
        actionButtons.setVisible(true);
        showActionButtons(msg.getAvailableActions());
    }
    public void clickBuyCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickBuyFromMarket(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.BUY_FROM_MARKET);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickActivatePP(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_PRODUCTION_POWER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickMoveResources(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.MOVE_RESOURCE);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickActivateLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.ACTIVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickDiscardLeaderCard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.REMOVE_LEADER_CARD);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
    public void clickVisitOtherPersonalBoard(){
        CChooseActionTurnResponseMsg msg = new CChooseActionTurnResponseMsg("I choose the action",gui.getUsername(),TurnAction.SEE_OTHER_PLAYER);
        gui.sendMsg(msg);
        disableActionButtons();
        actionButtons.setVisible(false);
    }
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
        gui.seeDevCardTable();
    }

    public void choosePP(VActivateProductionPowerRequestMsg msg){
        ArrayList<Pane> panes=getPPPanes();
        for(Integer i:msg.getActivatablePP()){
            panes.get(i).setVisible(true);
            panes.get(i).setDisable(false);
        }
    }
    public void chooseFromWhere(){
        warehousePane.setDisable(false);
        warehousePane.setVisible(true);
        strongBoxPane.setDisable(false);
        strongBoxPane.setVisible(true);
    }
    public void chooseStandardPPPane(){
        chosenPP=0;
        chooseFromWhere();
    }
    public void chooseCardSpace1PPPane(){
        chosenPP=1;
        chooseFromWhere();
    }
    public void chooseCardSpace2PPPane(){
        chosenPP=2;
        chooseFromWhere();
    }
    public void chooseCardSpace3PPPane(){
        chosenPP=3;
        chooseFromWhere();
    }
    public void chooseLeaderCard1PPPane(){
        chosenPP=4;
        chooseFromWhere();
    }
    public void chooseLeaderCard2PPPane(){
        chosenPP=5;
        chooseFromWhere();
    }
    public void chooseWarehousePane(){
        gui.sendMsg(new CActivateProductionPowerResponseMsg("I choose the Production Power",gui.getUsername(),"warehouse",chosenPP));
    }
    public void chooseStrongBoxPane(){
        gui.sendMsg(new CActivateProductionPowerResponseMsg("I choose the Production Power",gui.getUsername(),"strongbox",chosenPP));
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    private void disableActionButtons(){
        for(Button button:getActionButtons()){
            button.setDisable(true);
        }
    }
    private void disableAndHidePPPanes(){
        for(Pane pane:getPPPanes()){
            pane.setDisable(true);
            pane.setVisible(false);
        }
    }

    //Update FXML Elements based on Model Elements
    public void updateFaithTrackView(FaithTrack faithTrack){
        if(faithTrack instanceof SoloFaithTrack){
            SoloFaithTrack soloFaithTrack = (SoloFaithTrack) faithTrack;
            for(int i=0;i<25;i++){
                if(soloFaithTrack.getLorenzoFaithMarker().getPosition()==i){
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
        }else {
            for (int i = 0; i < 25; i++) {
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
        if(warehouse.getDepots().size()>3){
            specialDepotLabel.setVisible(true);
        }else{
            specialDepotLabel.setVisible(false);
        }
        for(int i=0;i<warehouse.getDepots().size();i++){
            Depot depot = warehouse.getDepots().get(i);
            ArrayList<ImageView> depotView = getWarehouseView().get(i);
            if(i==4){
                switch(depot.getType()){
                    case COIN:getSpecialDepotsView().get(0).setImage(new Image(""));break;
                    case SERVANT:getSpecialDepotsView().get(0).setImage(new Image(""));break;
                    case STONE:getSpecialDepotsView().get(0).setImage(new Image(""));break;
                    case SHIELD:getSpecialDepotsView().get(0).setImage(new Image(""));break;
                }
                getSpecialDepotsView().get(0).setVisible(true);
            }else if(i==5){
                switch(depot.getType()){
                    case COIN:getSpecialDepotsView().get(1).setImage(new Image(""));break;
                    case SERVANT:getSpecialDepotsView().get(1).setImage(new Image(""));break;
                    case STONE:getSpecialDepotsView().get(1).setImage(new Image(""));break;
                    case SHIELD:getSpecialDepotsView().get(1).setImage(new Image(""));break;
                }
                getSpecialDepotsView().get(1).setVisible(true);
            }else{
                getSpecialDepotsView().get(0).setVisible(false);
                getSpecialDepotsView().get(1).setVisible(false);
            }
            for(int j=0;j<depot.getSize();j++){
                if(j+1<=depot.getNumberResources()){
                    Resource resource= depot.getResources().get(j);
                    switch(resource.getType()){
                        case COIN: depotView.get(j).setImage(new Image("/images/punchboard/coin.png"));break;
                        case SHIELD: depotView.get(j).setImage(new Image("/images/punchboard/shield.png"));break;
                        case SERVANT: depotView.get(j).setImage(new Image("/images/punchboard/servant.png"));break;
                        case STONE: depotView.get(j).setImage(new Image("/images/punchboard/stone.png"));break;
                    }
                    depotView.get(j).setVisible(true);
                }else{
                    depotView.get(j).setImage(null);
                    depotView.get(j).setVisible(false);
                }
            }
        }
    }
    public void updateStrongBoxView(StrongBox strongBox){
        HashMap<TypeResource,Integer> countResourcesInStrongBox=new HashMap<>();
        countResourcesInStrongBox.put(TypeResource.SERVANT,0);
        countResourcesInStrongBox.put(TypeResource.SHIELD,0);
        countResourcesInStrongBox.put(TypeResource.COIN,0);
        countResourcesInStrongBox.put(TypeResource.STONE,0);
        for(Resource resource:strongBox.getContent()){
            Integer countType=countResourcesInStrongBox.get(resource.getType());
            countType++;
        }
        for(TypeResource key:countResourcesInStrongBox.keySet()){
            getStrongboxLabelsView().get(key).setText(""+countResourcesInStrongBox.get(key));
        }
    }
    public void updateCardSpacesView(ArrayList<CardSpace> cardSpaces){
        for(int i=0;i<cardSpaces.size();i++){
            if(cardSpaces.get(i).getNumberOfCards()!=0){
                getCardSpacesView().get(i).setImage(new Image("/images/frontCards/DevelopmentCard ("+cardSpaces.get(i).getUpperCard().getId()+").png"));
                getCardSpacesView().get(i).setVisible(true);
            }else{
                getCardSpacesView().get(i).setVisible(false);
            }
        }
    }
    public void updateLeaderCardsView(ArrayList<LeaderCard> leaderCards){
        for(int i=0;i<leaderCards.size();i++){
            getLeaderCardsView().get(i).setImage(new Image("/images/frontCards/LeaderCard ("+leaderCards.get(i).getCardID()+").png"));
            getLeaderCardsView().get(i).setVisible(true);
            if(leaderCards.get(i).getState() instanceof Inactive){
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.5);

                getLeaderCardsView().get(i).setEffect(colorAdjust);
            }
        }
    }
    public void updateVictoryPointsView(int victoryPoints){
        this.victoryPoints.setText(""+victoryPoints);
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
    private ArrayList<ImageView> getCardSpacesView(){
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
    private ArrayList<ImageView> getSpecialDepotsView(){
        ArrayList<ImageView> specialDepotsView = new ArrayList<>();
        specialDepotsView.add(specialDepot1);
        specialDepotsView.add(specialDepot2);
        return specialDepotsView;
    }
    private ArrayList<Pane> getPPPanes(){
        ArrayList<Pane> panes = new ArrayList<>();
        panes.add(standardPPPane);
        panes.add(cardSpace1PPPane);
        panes.add(cardSpace2PPPane);
        panes.add(cardSpace3PPPane);
        panes.add(leaderCard1PPPane);
        panes.add(leaderCard2PPPane);
        return panes;
    }
}
