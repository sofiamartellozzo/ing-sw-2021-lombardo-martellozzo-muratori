package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CBuyFromMarketInfoMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseDiscardResourceMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.controllerMsg.CStopMarketMsg;
import it.polimi.ingsw.message.viewMsg.VBuyFromMarketRequestMsg;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class MarketStructureSceneController {
    private GUI gui;

    @FXML
    private ImageView marble1_1,marble1_2,marble1_3,marble1_4,marble2_1,marble2_2,marble2_3,marble2_4,marble3_1,marble3_2,marble3_3,marble3_4,outMarble;

    @FXML
    private Button row1Button,row2Button,row3Button,column1Button,column2Button,column3Button,column4Button,backButton;

    @FXML
    private Label message, resourceLabel;

    @FXML
    private ImageView resource;

    @FXML
    private Button moveResourceButton,discardButton;

    @FXML
    private ImageView resource1_1,resource2_1,resource2_2,resource3_1,resource3_2,resource3_3,resource4_1,resource4_2,resource5_1,resource5_2;

    @FXML
    private Pane depot1,depot2,depot3,depot4,depot5;

    @FXML
    private ImageView specialDepot1,specialDepot2;

    @FXML
    private TitledPane chooseDepotPane;

    private ArrayList<TypeResource> resourcesToStore;
    private TypeResource resourceToStore;

    public void start(){
        update(gui.getMarketStructureData());
        ArrayList<Button> buttons = getButtons();
        chooseDepotPane.setVisible(false);
        for(Button button:buttons){
            button.setDisable(true);
        }
    }

    public void update(MarketStructure marketStruct){
        MarketStructure marketStructure=marketStruct;
        ImageView[][] marketStructureView=getMarketStructureView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                switch(marketStructure.getStructure()[i][j].getColor()){
                    case RED: marketStructureView[i][j].setImage(new Image("/images/market/red_marble.png"));break;
                    case YELLOW:marketStructureView[i][j].setImage(new Image("/images/market/yellow_marble.png"));break;
                    case BLUE:marketStructureView[i][j].setImage(new Image("/images/market/blue_marble.png"));break;
                    case GREY:marketStructureView[i][j].setImage(new Image("/images/market/grey_marble.png"));break;
                    case PURPLE:marketStructureView[i][j].setImage(new Image("/images/market/purple_marble.png"));break;
                    case WHITE:marketStructureView[i][j].setImage(new Image("/images/market/white_marble.png"));break;
                }
            }
        }
        switch(marketStructure.getSlide().getColor()){
            case RED: outMarble.setImage(new Image("/images/market/red_marble.png"));break;
            case YELLOW:outMarble.setImage(new Image("/images/market/yellow_marble.png"));break;
            case BLUE:outMarble.setImage(new Image("/images/market/blue_marble.png"));break;
            case GREY:outMarble.setImage(new Image("/images/market/grey_marble.png"));break;
            case PURPLE:outMarble.setImage(new Image("/images/market/purple_marble.png"));break;
            case WHITE:outMarble.setImage(new Image("/images/market/white_marble.png"));break;
        }
    }

    public void chooseRowColumn(VBuyFromMarketRequestMsg msg){
        update(msg.getMarket());
        backButton.setDisable(true);
        ArrayList<Button> buttons=getButtons();
        for(Button button:buttons){
            button.setDisable(false);
        }
    }

    public void clickRow1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",0);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickRow2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickRow3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickColumn1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",0);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickColumn2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickColumn3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    public void clickColumn4(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",3);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }

    public void chooseResource(){}
    public void chooseDepot(){
        if(resourcesToStore.size()>0){
            gui.seeMarketBoard();
            backButton.setDisable(true);
            copyWarehouseFromPersonalBoard();
            enableDepotPane();
            setLabelText(message,"Choose a depot\n" +
                    "where to store this resource:");
            resourceToStore = resourcesToStore.remove(0);
            setResourceAndLabel(resourceToStore);
            moveResourceButton.setDisable(false);
            discardButton.setDisable(false);
            chooseDepotPane.setVisible(true);
        }else{
            gui.seePersonalBoard();
            backButton.setDisable(false);
            disableDepotPane();
            chooseDepotPane.setVisible(false);
            gui.sendMsg(new CStopMarketMsg("Finished to buy",gui.getUsername(), TurnAction.BUY_FROM_MARKET));
        }
    }

    public void mouseEnteredDepot1(){
        if(!depot1.isDisable()){
            depot1.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot2(){
        if(!depot2.isDisable()){
            depot2.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot3(){
        if(!depot3.isDisable()){
            depot3.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot4(){
        if(!depot4.isDisable()){
            depot4.setEffect(new Glow());
        }
    }
    public void mouseEnteredDepot5(){
        if(!depot5.isDisable()){
            depot5.setEffect(new Glow());
        }
    }

    public void mouseExitedDepot1(){
        if(!depot1.isDisable()){
            depot1.setEffect(null);
        }
    }
    public void mouseExitedDepot2(){
        if(!depot2.isDisable()){
            depot2.setEffect(null);
        }
    }
    public void mouseExitedDepot3(){
        if(!depot3.isDisable()){
            depot3.setEffect(null);
        }
    }
    public void mouseExitedDepot4(){
        if(!depot4.isDisable()){
            depot4.setEffect(null);
        }
    }
    public void mouseExitedDepot5(){
        if(!depot5.isDisable()){
            depot5.setEffect(null);
        }
    }

    public void clickDepot1(){
        if(!depot1.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot",resourceToStore.getThisColor(),1,gui.getUsername()));
            chooseDepot();
        }
    }
    public void clickDepot2(){
        if(!depot2.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot",resourceToStore.getThisColor(),2,gui.getUsername()));
            chooseDepot();
        }
    }
    public void clickDepot3(){
        if(!depot3.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot",resourceToStore.getThisColor(),3,gui.getUsername()));
            chooseDepot();
        }
    }
    public void clickDepot4(){
        if(!depot4.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot",resourceToStore.getThisColor(),4,gui.getUsername()));
            chooseDepot();
        }
    }
    public void clickDepot5(){
        if(!depot5.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot",resourceToStore.getThisColor(),5,gui.getUsername()));
            chooseDepot();
        }
    }


    public void clickDiscardButton(){
        gui.sendMsg(new CChooseDiscardResourceMsg("I want to discard this resource",gui.getUsername()));
        chooseDepotPane.setVisible(false);
        discardButton.setDisable(true);
        moveResourceButton.setDisable(true);
        chooseDepot();
        //CHOOSE RESOURCE PANE DISABLE
    }

    public void clickMoveResourceButton(){
        gui.getPersonalBoardSceneController().setAction(TurnAction.MOVE_RESOURCE);
        gui.seePersonalBoard();
        gui.getPersonalBoardSceneController().setReturnToMarket(true);
        gui.getPersonalBoardSceneController().chooseDepots();
    }

    public void clickBackButton(){
        gui.seePersonalBoard();
    }

    public void setGui(GUI gui){this.gui=gui;}

    public void setResourcesToStore(ArrayList<TypeResource> resourcesToStore){this.resourcesToStore = resourcesToStore;}

    private ImageView[][] getMarketStructureView(){
        ImageView[][] marketStructureView = new ImageView[3][4];
        marketStructureView[0][0]=marble1_1;
        marketStructureView[0][1]=marble1_2;
        marketStructureView[0][2]=marble1_3;
        marketStructureView[0][3]=marble1_4;
        marketStructureView[1][0]=marble2_1;
        marketStructureView[1][1]=marble2_2;
        marketStructureView[1][2]=marble2_3;
        marketStructureView[1][3]=marble2_4;
        marketStructureView[2][0]=marble3_1;
        marketStructureView[2][1]=marble3_2;
        marketStructureView[2][2]=marble3_3;
        marketStructureView[2][3]=marble3_4;
        return marketStructureView;
    }
    private ArrayList<Button> getButtons(){
        ArrayList<Button> buttons=new ArrayList<>();
        buttons.add(row1Button);
        buttons.add(row2Button);
        buttons.add(row3Button);
        buttons.add(column1Button);
        buttons.add(column2Button);
        buttons.add(column3Button);
        buttons.add(column4Button);
        return buttons;
    }
    private void disableButtons(){
        ArrayList<Button> buttons = getButtons();
        for(Button button:buttons){
            button.setDisable(true);
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
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1);
        depotPanes.add(depot2);
        depotPanes.add(depot3);
        depotPanes.add(depot4);
        depotPanes.add(depot5);
        return depotPanes;
    }
    private ArrayList<ImageView> getSpecialDepotView(){
        ArrayList<ImageView> specialDepotView = new ArrayList<>();
        specialDepotView.add(specialDepot1);
        specialDepotView.add(specialDepot2);
        return specialDepotView;
    }
    public void copyWarehouseFromPersonalBoard(){
        ArrayList<ArrayList<ImageView>> warehouseFromPersonalBoardView = gui.getPersonalBoardSceneController().getWarehouseView();
        ArrayList<ImageView> specialDepotsFromPersonalBoardView = gui.getPersonalBoardSceneController().getSpecialDepotsView();
        ArrayList<ArrayList<ImageView>> warehouseView = getWarehouseView();
        ArrayList<ImageView> specialDepotView = getSpecialDepotView();
        for(int i=0;i<warehouseView.size();i++){
            ArrayList<ImageView> depotView=warehouseView.get(i);
            ArrayList<ImageView> depotFromPersonalBoardView=warehouseFromPersonalBoardView.get(i);
            for(int j=0;j<depotView.size();j++){
                depotView.get(j).setVisible(depotFromPersonalBoardView.get(j).isVisible());
                depotView.get(j).setImage(depotFromPersonalBoardView.get(j).getImage());
            }
        }
        for(int i=0;i<specialDepotView.size();i++){
            specialDepotView.get(i).setVisible(specialDepotsFromPersonalBoardView.get(i).isVisible());
            specialDepotView.get(i).setImage(specialDepotsFromPersonalBoardView.get(i).getImage());
        }
    }
    private void enableDepotPane(){
        for(int i=0;i<5;i++){
            if(i<3) {
                getDepotPanes().get(i).setVisible(true);
                getDepotPanes().get(i).setDisable(false);
            }else{
                getDepotPanes().get(i).setVisible(getSpecialDepotView().get(i-3).isVisible());
                getDepotPanes().get(i).setDisable(!getSpecialDepotView().get(i-3).isVisible());
            }
        }
    }
    private void disableDepotPane(){
        for(Pane pane:getDepotPanes()){
            pane.setDisable(true);
        }
    }
    private void setResourceAndLabel(TypeResource type){
        switch(type){
            case COIN:
                resource.setImage(new Image("/images/punchboard/coin.png"));
                setLabelText(resourceLabel,"COIN");
                resourceLabel.setTextFill(new Color((double) 228/255,(double) 198/255,0,1));
                break;
            case SHIELD:
                resource.setImage(new Image("/images/punchboard/shield.png"));
                setLabelText(resourceLabel,"SHIELD");
                resourceLabel.setTextFill(new Color(0,(double) 172/255,(double) 227/255,1));
                break;
            case SERVANT:
                resource.setImage(new Image("/images/punchboard/servant.png"));
                setLabelText(resourceLabel,"SERVANT");
                resourceLabel.setTextFill(new Color((double) 118/255,0,(double) 224/255,1));
                break;
            case STONE:
                resource.setImage(new Image("/images/punchboard/stone.png"));
                setLabelText(resourceLabel,"STONE");
                resourceLabel.setTextFill(new Color((double) 169/255,(double) 169/255,(double) 169/255,1));
                break;
        }
    }

    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }
}
