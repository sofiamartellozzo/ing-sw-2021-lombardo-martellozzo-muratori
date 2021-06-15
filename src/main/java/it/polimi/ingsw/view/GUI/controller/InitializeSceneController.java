package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseDiscardResourceMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.message.viewMsg.VNotValidDepotMsg;
import it.polimi.ingsw.model.TypeResource;
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
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class InitializeSceneController {
    private GUI gui;

    @FXML
    private TitledPane chooseResourcePane, chooseDepotPane;

    @FXML
    private ImageView coin,servant,shield,stone;

    @FXML
    private Button discardButton;

    @FXML
    private Label messageDepot, labelChosenResource;

    @FXML
    private ImageView chosenResource;

    @FXML
    private Pane depot1Pane,depot2Pane,depot3Pane;

    @FXML
    private ImageView resource1_1,resource2_1,resource2_2,resource3_1,resource3_2,resource3_3;

    @FXML
    private TitledPane leaderCardPane;

    @FXML
    private ImageView leaderCard1,leaderCard2,leaderCard3,leaderCard4;

    @FXML
    private Label playerLabel;

    @FXML
    private TitledPane waitPane;

    @FXML
    private TitledPane warningPane;

    @FXML
    private Button okButton;

    private TypeResource chosenType;
    private ArrayList<Integer> possibleLeaderCards;
    private ArrayList<Integer> chosenLeaderCards;

    public void start(){
        warningPane.setVisible(false);
        waitPane.setVisible(true);
        setLabelText(playerLabel,"You're the player "+gui.getPlayer().getNumber());
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        disableDepotPanes();
        leaderCardPane.setVisible(false);
        for(ImageView image:getLeaderCardsView()){
            image.setVisible(false);
            image.setDisable(true);
        }
        chosenLeaderCards=new ArrayList<>();
    }

    public void chooseResource(){
        waitPane.setVisible(false);
        discardButton.setDisable(false);
        chooseResourcePane.setVisible(true);
    }

    public void mouseEnteredCoin(){
        coin.setEffect(new Glow());
    }
    public void mouseEnteredServant(){
        servant.setEffect(new Glow());
    }
    public void mouseEnteredShield(){
        shield.setEffect(new Glow());
    }
    public void mouseEnteredStone(){
        stone.setEffect(new Glow());
    }

    public void mouseExitedCoin(){coin.setEffect(null);}
    public void mouseExitedServant(){servant.setEffect(null);}
    public void mouseExitedShield(){shield.setEffect(null);}
    public void mouseExitedStone(){stone.setEffect(null);}

    public void clickCoin(){
        chosenType =TypeResource.COIN;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    public void clickServant(){
        chosenType =TypeResource.SERVANT;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    public void clickShield(){
        chosenType =TypeResource.SHIELD;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    public void clickStone(){
        chosenType =TypeResource.STONE;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }

    public void clickDiscardButton(){
        chooseResourcePane.setVisible(false);
        chooseDepotPane.setVisible(false);
        discardButton.setDisable(true);
        gui.sendMsg(new CChooseDiscardResourceMsg("I want to discard this resource",gui.getUsername()));
    }

    public void chooseDepot(){
        messageDepot.setText("Select a depot for:");
        enableDepotPanes();
        copyWarehouseFromPersonalBoardView();
        discardButton.setDisable(false);
        switch(chosenType){
            case COIN:
                chosenResource.setImage(new Image("/images/punchboard/coin.png"));
                labelChosenResource.setText("COIN");
                labelChosenResource.setTextFill(new Color((double) 228/255,(double) 198/255,0,1));
                break;
            case SHIELD:
                chosenResource.setImage(new Image("/images/punchboard/shield.png"));
                labelChosenResource.setText("SHIELD");
                labelChosenResource.setTextFill(new Color(0,(double) 172/255,(double) 227/255,1));
                break;
            case SERVANT:
                chosenResource.setImage(new Image("/images/punchboard/servant.png"));
                labelChosenResource.setText("SERVANT");
                labelChosenResource.setTextFill(new Color((double) 118/255,0,(double) 224/255,1));
                break;
            case STONE:
                chosenResource.setImage(new Image("/images/punchboard/stone.png"));
                labelChosenResource.setText("STONE");
                labelChosenResource.setTextFill(new Color((double) 169/255,(double) 169/255,(double) 169/255,1));
                break;
        }
        chooseDepotPane.setVisible(true);
    }

    public void chooseDepot(VNotValidDepotMsg msg){
        messageDepot.setText("You can't put the resource here.\n Select a new depot for:");
        enableDepotPanes();
        getDepotPanes().get(msg.getUnableDepot()-1).setDisable(true);
        copyWarehouseFromPersonalBoardView();
        discardButton.setDisable(false);
        switch(fromColorToType(msg.getResourceChooseBefore())){
            case COIN:
                chosenResource.setImage(new Image("/images/punchboard/coin.png"));
                labelChosenResource.setText("COIN");
                labelChosenResource.setTextFill(new Color((double) 228/255,(double) 198/255,0,1));
                break;
            case SHIELD:
                chosenResource.setImage(new Image("/images/punchboard/shield.png"));
                labelChosenResource.setText("SHIELD");
                labelChosenResource.setTextFill(new Color(0,(double) 172/255,(double) 227/255,1));
                break;
            case SERVANT:
                chosenResource.setImage(new Image("/images/punchboard/servant.png"));
                labelChosenResource.setText("SERVANT");
                labelChosenResource.setTextFill(new Color((double) 118/255,0,(double) 224/255,1));
                break;
            case STONE:
                chosenResource.setImage(new Image("/images/punchboard/stone.png"));
                labelChosenResource.setText("STONE");
                labelChosenResource.setTextFill(new Color((double) 169/255,(double) 169/255,(double) 169/255,1));
                break;
        }
        chooseDepotPane.setVisible(true);
    }

    public void mouseEnteredDepot1(){depot1Pane.setEffect(new Glow());}
    public void mouseEnteredDepot2(){depot2Pane.setEffect(new Glow());}
    public void mouseEnteredDepot3(){depot3Pane.setEffect(new Glow());}
    public void mouseExitedDepot1(){depot1Pane.setEffect(null);}
    public void mouseExitedDepot2(){depot2Pane.setEffect(null);}
    public void mouseExitedDepot3(){depot3Pane.setEffect(null);}
    public void clickDepot1(){
        waitPane.setVisible(true);
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),1,gui.getUsername()));
    }
    public void clickDepot2(){
        waitPane.setVisible(true);
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),2,gui.getUsername()));
    }
    public void clickDepot3(){
        waitPane.setVisible(true);
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),3,gui.getUsername()));
    }



    public void chooseLeaderCard(VChooseLeaderCardRequestMsg msg){
        waitPane.setVisible(false);
        for(int i=0;i<4;i++){
            getLeaderCardsView().get(i).setImage(new Image("/images/frontCards/LeaderCard ("+msg.getMiniDeckLeaderCardFour().get(i)+").png"));
            getLeaderCardsView().get(i).setDisable(false);
            getLeaderCardsView().get(i).setVisible(true);
        }
        possibleLeaderCards=msg.getMiniDeckLeaderCardFour();
        leaderCardPane.setVisible(true);
    }

    public void mouseEnteredLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(new Glow());
        }
    }
    public void mouseEnteredLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(new Glow());
        }
    }
    public void mouseEnteredLeaderCard3(){
        if(!leaderCard3.isDisable()){
            leaderCard3.setEffect(new Glow());
        }
    }
    public void mouseEnteredLeaderCard4(){
        if(!leaderCard4.isDisable()){
            leaderCard4.setEffect(new Glow());
        }
    }
    public void mouseExitedLeaderCard1(){
        leaderCard1.setEffect(null);
    }
    public void mouseExitedLeaderCard2(){
        leaderCard2.setEffect(null);
    }
    public void mouseExitedLeaderCard3(){
        leaderCard3.setEffect(null);
    }
    public void mouseExitedLeaderCard4(){
        leaderCard4.setEffect(null);
    }
    public void clickLeaderCard1(){
        if(!leaderCard1.isDisable() && chosenLeaderCards.size()<2){
            chosenLeaderCards.add(possibleLeaderCards.get(0));
            leaderCard1.setDisable(true);
            ColorAdjust colorAdjust=new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            leaderCard1.setEffect(colorAdjust);
            leaderCard1.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            if(chosenLeaderCards.size()==2){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose my leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose"));
                leaderCardPane.setVisible(false);
            }
        }
    }
    public void clickLeaderCard2(){
        if(!leaderCard2.isDisable() && chosenLeaderCards.size()<2){
            chosenLeaderCards.add(possibleLeaderCards.get(1));
            leaderCard2.setDisable(true);
            ColorAdjust colorAdjust=new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            leaderCard2.setEffect(colorAdjust);
            leaderCard2.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            if(chosenLeaderCards.size()==2){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose my leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose"));
                leaderCardPane.setVisible(false);
            }
        }
    }
    public void clickLeaderCard3(){
        if(!leaderCard3.isDisable() && chosenLeaderCards.size()<2){
            chosenLeaderCards.add(possibleLeaderCards.get(2));
            leaderCard3.setDisable(true);
            ColorAdjust colorAdjust=new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            leaderCard3.setEffect(colorAdjust);
            leaderCard3.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            if(chosenLeaderCards.size()==2){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose my leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose"));
                leaderCardPane.setVisible(false);
            }
        }
    }
    public void clickLeaderCard4(){
        if(!leaderCard4.isDisable() && chosenLeaderCards.size()<2){
            chosenLeaderCards.add(possibleLeaderCards.get(3));
            leaderCard4.setDisable(true);
            ColorAdjust colorAdjust=new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            leaderCard4.setEffect(colorAdjust);
            leaderCard4.setImage(new Image("/images/backCards/LeaderCard (1).png"));
            if(chosenLeaderCards.size()==2){
                gui.sendMsg(new CChooseLeaderCardResponseMsg("I choose my leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose"));
                leaderCardPane.setVisible(false);
            }
        }
    }

    private it.polimi.ingsw.model.Color fromTypeToColor(TypeResource type){
        switch(type){
            case COIN:return it.polimi.ingsw.model.Color.YELLOW;
            case SERVANT:return it.polimi.ingsw.model.Color.PURPLE;
            case SHIELD:return it.polimi.ingsw.model.Color.BLUE;
            case STONE:return it.polimi.ingsw.model.Color.GREY;
            default:return null;
        }
    }
    private TypeResource fromColorToType(it.polimi.ingsw.model.Color color){
        switch(color){
            case YELLOW:return TypeResource.COIN;
            case PURPLE:return TypeResource.SERVANT;
            case BLUE:return TypeResource.SHIELD;
            case GREY:return TypeResource.STONE;
            default:return null;
        }
    }
    private void disableDepotPanes(){
        depot1Pane.setDisable(true);
        depot2Pane.setDisable(true);
        depot3Pane.setDisable(true);
    }
    private void enableDepotPanes(){
        depot1Pane.setDisable(false);
        depot2Pane.setDisable(false);
        depot3Pane.setDisable(false);
    }
    private void copyWarehouseFromPersonalBoardView(){
        ArrayList<ArrayList<ImageView>> warehouseFromPersonalBoardView = gui.getPersonalBoardSceneController().getWarehouseView();
        ArrayList<ArrayList<ImageView>> warehouseView = getWarehouseView();
        for(int i=0;i<3;i++){
            for(int j=0;j<warehouseView.get(i).size();j++){
                warehouseView.get(i).get(j).setImage(warehouseFromPersonalBoardView.get(i).get(j).getImage());
            }
        }
    }
    private ArrayList<ArrayList<ImageView>> getWarehouseView(){
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

        warehouseView.add(depot1View);
        warehouseView.add(depot2View);
        warehouseView.add(depot3View);

        return warehouseView;
    }
    public void setGui(GUI gui){this.gui=gui;}
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1Pane);
        depotPanes.add(depot2Pane);
        depotPanes.add(depot3Pane);
        return depotPanes;
    }
    private ArrayList<ImageView> getLeaderCardsView(){
        ArrayList<ImageView> leaderCardsView=new ArrayList<>();
        leaderCardsView.add(leaderCard1);
        leaderCardsView.add(leaderCard2);
        leaderCardsView.add(leaderCard3);
        leaderCardsView.add(leaderCard4);
        return leaderCardsView;
    }

    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }
    public void showWaitPane(){
        waitPane.setVisible(true);
    }

    public void setWarningPane(VServerUnableMsg msg) {
        warningPane.setVisible(true);
        chooseDepotPane.setVisible(false);
        chooseResourcePane.setVisible(false);
        leaderCardPane.setVisible(false);
        waitPane.setVisible(false);
    }

    public void clickOkButton(){
        gui.close();
    }
}
