package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.Resource;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class manages the case in which the player wants to see the personal board of another player,
 * so it handles the scene of other's personal boards
 */

public class OtherPersonalBoardSceneController {
    private GUI gui;
    @FXML
    private Pane backgroundBox0;
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
    private Label victoryPoints;

    @FXML
    private Button backButton;

    public void start(){
        return;
    }

    public void update(PlayerInterface player){
        updateLeaderCardsView(player.getLeaderCards());
        updateCardSpacesView(player.getGameSpace().getCardSpaces());
        updateFaithTrackView(player.getGameSpace().getFaithTrack());
        updateResourceManagerView(player.getGameSpace().getResourceManager());
        updateVictoryPointsView(player.getVictoryPoints());
    }

    public void clickBackButton(){
        if(!backButton.isDisable()){
            gui.seePersonalBoard();
        }
    }

    //Update FXML Elements based on Model Elements
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
                if (i == 4||i==5) {
                    switch (depot.getType()) {
                        case COIN:
                            getSpecialDepotsView().get(0).setImage(new Image("/images/personalBoard/SpecialDepotCoin.png"));
                            break;
                        case SERVANT:
                            getSpecialDepotsView().get(0).setImage(new Image("/images/personalBoard/SpecialDepotServant.png"));
                            break;
                        case STONE:
                            getSpecialDepotsView().get(0).setImage(new Image("/images/personalBoard/SpecialDepotStone.png"));
                            break;
                        case SHIELD:
                            getSpecialDepotsView().get(0).setImage(new Image("/images/personalBoard/SpecialDepotShield.png"));
                            break;
                    }
                    getSpecialDepotsView().get(i-4).setVisible(true);
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
    public void updateLeaderCardsView(ArrayList<LeaderCard> leaderCards){
        for(int i=0;i<2;i++) {
            if (i <= leaderCards.size()) {
                if(leaderCards.get(i).getState() instanceof Active) {
                    getLeaderCardsView().get(i).setImage(new Image("/images/frontCards/LeaderCard (" + leaderCards.get(i).getCardID() + ").png"));
                    getLeaderCardsView().get(i).setVisible(true);
                }else if (leaderCards.get(i).getState() instanceof Inactive) {
                    getLeaderCardsView().get(i).setImage(new Image(("/images/backCards/LeaderCard (1).png")));
                    getLeaderCardsView().get(i).setVisible(true);
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.5);

                    getLeaderCardsView().get(i).setEffect(colorAdjust);
                }
            } else {
                getLeaderCardsView().get(i).setImage(new Image(("/images/backCards/LeaderCard (1).png")));
                getLeaderCardsView().get(i).setVisible(true);
            }
            getLeaderCardsView().get(i).setDisable(true);
        }
    }
    public void updateVictoryPointsView(int victoryPoints){
        setLabelText(this.victoryPoints,""+victoryPoints);
    }


    //Getter Method for FXML Elements
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
    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }

    public void setGui(GUI gui){this.gui=gui;}
}
