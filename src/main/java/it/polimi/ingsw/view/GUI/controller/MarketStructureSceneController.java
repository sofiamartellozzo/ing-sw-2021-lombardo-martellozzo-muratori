package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.CBuyFromMarketInfoMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseDiscardResourceMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.controllerMsg.CStopMarketMsg;
import it.polimi.ingsw.message.viewMsg.VBuyFromMarketRequestMsg;
import it.polimi.ingsw.message.viewMsg.VNotValidDepotMsg;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.market.MarketStructure;
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

import static it.polimi.ingsw.model.TypeResource.BLANK;

/**
 * When the player interacts with the market, the scene is set to the MarketStructureScene and
 * every action is managed here.
 */
public class MarketStructureSceneController {
    private GUI gui;

    @FXML
    private ImageView m00,m01,m02,m03,m10,m11,m12,m13,m20,m21,m22,m23,slideMarble;

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

    @FXML
    private TitledPane chooseResourcePane;

    @FXML
    private ImageView coin,servant,stone,shield;

    @FXML
    private TitledPane warningPane;

    @FXML
    private Label moveNotValidLabel;

    @FXML
    private Button okButton;

    private ArrayList<TypeResource> resourcesToStore;
    private ArrayList<TypeResource> whiteSpecial;
    private boolean waitMove;
    private TypeResource resourceToStore;
    private boolean resourceStored;

    /**
     * When the MarketStructureScene is set, this method prepares it
     */
    public void start(){
        moveNotValidLabel.setVisible(false);
        warningPane.setVisible(false);
        update(gui.getMarketStructureData());
        ArrayList<Button> buttons = getButtons();
        disableResources();
        chooseDepotPane.setVisible(false);
        chooseResourcePane.setVisible(false);
        for(Button button:buttons){
            button.setDisable(true);
        }
    }

    /**
     * To update the market situation from information of the Model
     * @param marketStruct the market structure of the model
     */
    public void update(MarketStructure marketStruct){
        MarketStructure marketStructure=marketStruct;
        ImageView[][] marketStructureView=getMarketStructureView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                System.out.println(marketStruct.getStructure()[i][j].getColor());
                switch(marketStructure.getStructure()[i][j].getColor()){
                    case RED: System.out.println((i+1)+" "+(j+1)+": RED");marketStructureView[i][j].setImage(new Image("/images/market/red_marble.png"));break;
                    case YELLOW:System.out.println((i+1)+" "+(j+1)+": YELLOW");marketStructureView[i][j].setImage(new Image("/images/market/yellow_marble.png"));break;
                    case BLUE:System.out.println((i+1)+" "+(j+1)+": BLUE");marketStructureView[i][j].setImage(new Image("/images/market/blue_marble.png"));break;
                    case GREY:System.out.println((i+1)+" "+(j+1)+": GREY");marketStructureView[i][j].setImage(new Image("/images/market/grey_marble.png"));break;
                    case PURPLE:System.out.println((i+1)+" "+(j+1)+": PURPLE");marketStructureView[i][j].setImage(new Image("/images/market/purple_marble.png"));break;
                    case WHITE:System.out.println((i+1)+" "+(j+1)+": WHITE");marketStructureView[i][j].setImage(new Image("/images/market/white_marble.png"));break;
                }
            }
        }
        switch(marketStructure.getSlide().getColor()){
            case RED: System.out.println("Slide: RED");slideMarble.setImage(new Image("/images/market/red_marble.png"));break;
            case YELLOW:System.out.println("Slide: YELLOW");slideMarble.setImage(new Image("/images/market/yellow_marble.png"));break;
            case BLUE:System.out.println("Slide: BLUE");slideMarble.setImage(new Image("/images/market/blue_marble.png"));break;
            case GREY:System.out.println("Slide: GREY");slideMarble.setImage(new Image("/images/market/grey_marble.png"));break;
            case PURPLE:System.out.println("Slide: PURPLE");slideMarble.setImage(new Image("/images/market/purple_marble.png"));break;
            case WHITE:System.out.println("Slide: WHITE");slideMarble.setImage(new Image("/images/market/white_marble.png"));break;
        }
    }

    /**
     * When the player choose to buy from the market, the market situation view is updated, every buttons enabled
     * to allow the player to choose a row/column
     * @param msg VBuyFromMarketRequestMsg
     */
    public void chooseRowColumn(VBuyFromMarketRequestMsg msg){
        update(msg.getMarket());
        moveNotValidLabel.setVisible(false);
        backButton.setDisable(true);
        ArrayList<Button> buttons=getButtons();
        for(Button button:buttons){
            button.setDisable(false);
        }
    }

    /**
     * When the player clicks on "Row 1" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickRow1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",0);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Row 2" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickRow2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Row 3" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickRow3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Column 1" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickColumn1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",0);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Column 2" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickColumn2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Column 3" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickColumn3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }
    /**
     * When the player clicks on "Column 4" button, a message with the choice made is send to the server/message handler
     * every buttons disabled and the scene temporarily returns to the PersonalBoard.
     */
    public void clickColumn4(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",3);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.seePersonalBoard();
        backButton.setDisable(false);
    }

    /**
     * When the player gets a white marble and has two leader cards of type WHITE_MARBLE,
     * a pop-up with the possible resources appears.
     */
    public void chooseResource(){
        for(TypeResource type: whiteSpecial){
            int which=0;
            switch(type){
                case COIN:
                    which=0;
                    break;
                case SERVANT:
                    which=1;
                    break;
                case SHIELD:
                    which=2;
                    break;
                case STONE:;
                    which=3;
                    break;
            }
            getResourcesView().get(which).setEffect(null);
            getResourcesView().get(which).setDisable(false);
        }
        chooseResourcePane.setVisible(true);
        chooseDepotPane.setVisible(false);
    }

    /**
     * When the mouse enters on "COIN" image
     */
    public void mouseEnteredCoin(){
        if(!coin.isDisable()){
            coin.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on "SERVANT" image
     */
    public void mouseEnteredServant(){
        if(!servant.isDisable()){
            servant.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on "SHIELD" image
     */
    public void mouseEnteredShield(){
        if(!shield.isDisable()){
            shield.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters on "STONE" image
     */
    public void mouseEnteredStone(){
        if(!stone.isDisable()){
            stone.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from "COIN" image
     */
    public void mouseExitedCoin(){
        if(!coin.isDisable()){
            coin.setEffect(null);
        }
    }
    /**
     * When the mouse exits from "SERVANT" image
     */
    public void mouseExitedServant(){
        if(!servant.isDisable()){
            servant.setEffect(null);
        }
    }
    /**
     * When the mouse exits from "SHIELD" image
     */
    public void mouseExitedShield(){
        if(!shield.isDisable()){
            shield.setEffect(null);
        }
    }
    /**
     * When the mouse exits from "STONE" image
     */
    public void mouseExitedStone(){
        if(!stone.isDisable()){
            stone.setEffect(null);
        }
    }

    /**
     * When the player clicks on "COIN" image, the controller saves his choice and makes appear
     * the pop-up with the depot to put the resource chosen in the warehouse.
     */
    public void clickCoin(){
        if(!coin.isDisable()){
            resourceToStore=TypeResource.COIN;
            resourcesToStore.remove(0);
            resourcesToStore.add(0,resourceToStore);
            disableResources();
            chooseResourcePane.setVisible(false);
            setResourceAndLabel(resourceToStore);
            moveResourceButton.setDisable(false);
            discardButton.setDisable(false);
            chooseDepotPane.setVisible(true);
        }
    }
    /**
     * When the player clicks on "SERVANT" image, the controller saves his choice and makes appear
     * the pop-up with the depot to put the resource chosen in the warehouse.
     */
    public void clickServant(){
        if(!servant.isDisable()){
            resourceToStore=TypeResource.SERVANT;
            resourcesToStore.remove(0);
            resourcesToStore.add(0,resourceToStore);
            disableResources();
            chooseResourcePane.setVisible(false);
            setResourceAndLabel(resourceToStore);
            moveResourceButton.setDisable(false);
            discardButton.setDisable(false);
            chooseDepotPane.setVisible(true);
        }
    }
    /**
     * When the player clicks on "SHIELD" image, the controller saves his choice and makes appear
     * the pop-up with the depot to put the resource chosen in the warehouse.
     */
    public void clickShield(){
        if(!shield.isDisable()){
            resourceToStore=TypeResource.SHIELD;
            resourcesToStore.remove(0);
            resourcesToStore.add(0,resourceToStore);
            disableResources();
            chooseResourcePane.setVisible(false);
            setResourceAndLabel(resourceToStore);
            moveResourceButton.setDisable(false);
            discardButton.setDisable(false);
            chooseDepotPane.setVisible(true);
        }
    }
    /**
     * When the player clicks on "STONE" image, the controller saves his choice and makes appear
     * the pop-up with the depot to put the resource chosen in the warehouse.
     */
    public void clickStone(){
        if(!stone.isDisable()){
            resourceToStore=TypeResource.STONE;
            resourcesToStore.remove(0);
            resourcesToStore.add(0,resourceToStore);
            disableResources();
            chooseResourcePane.setVisible(false);
            setResourceAndLabel(resourceToStore);
            moveResourceButton.setDisable(false);
            discardButton.setDisable(false);
            chooseDepotPane.setVisible(true);
        }
    }

    /**
     * For every resource received the player has to choose where to put them in the Warehouse,
     * is checked what type of resource is received and the possible ability activated, to prepare the pop-up.
     * If the player has finished to manage the resources received, the scene returns to personalboard.
     */
    public void chooseDepot(){
        if(resourceStored){
            resourcesToStore.remove(0);
        }

        if(resourcesToStore.size()>0){
            resourceStored=false;
            gui.seeMarketBoard();
            backButton.setDisable(true);
            copyWarehouseFromPersonalBoard();
            enableDepotPane();
            setLabelText(message,"Choose a depot\n" +
                    "where to store this resource:");
            resourceToStore=resourcesToStore.get(0);
            if(whiteSpecial!=null && whiteSpecial.size()==1 && resourceToStore.equals(TypeResource.BLANK)){
                resourceToStore=whiteSpecial.get(0);
                setResourceAndLabel(resourceToStore);
                moveResourceButton.setDisable(false);
                discardButton.setDisable(false);
                chooseDepotPane.setVisible(true);
            }else if(whiteSpecial!=null && whiteSpecial.size()>1 && resourceToStore.equals(TypeResource.BLANK)){
                chooseResource();
            }else if(!resourceToStore.equals(TypeResource.BLANK)){
                setResourceAndLabel(resourceToStore);
                moveResourceButton.setDisable(false);
                discardButton.setDisable(false);
                chooseDepotPane.setVisible(true);
            }
        }else{
            gui.seePersonalBoard();
            backButton.setDisable(false);
            disableDepotPane();
            chooseDepotPane.setVisible(false);
            if(gui.getPersonalBoardSceneController().getAction().equals(TurnAction.BUY_FROM_MARKET)) {
                gui.getPersonalBoardSceneController().setTurnAction(null);
            }
            gui.sendMsg(new CStopMarketMsg("Finished to buy",gui.getUsername(), TurnAction.BUY_FROM_MARKET));
        }
    }

    /**
     * When the player choose a depot where he can't put the resource,
     * the pop-up of the depots is shown to put again correctly the resource.
     * The cycle to manage the resource received is momentarily interrupted
     * @param msg VNotValidDepotMsg
     */
    public void chooseDepot(VNotValidDepotMsg msg){
        gui.seeMarketBoard();
        backButton.setDisable(true);
        copyWarehouseFromPersonalBoard();
        enableDepotPane();
        chooseResourcePane.setVisible(false);
        getDepotPanes().get(msg.getUnableDepot()-1).setDisable(true);
        setLabelText(message,"Chosen depot not valid.\n" +
                "Choose a depot\n" +
                "where to store this resource:");
        resourceToStore = fromColorToType(msg.getResourceChooseBefore());
        setResourceAndLabel(resourceToStore);
        moveResourceButton.setDisable(false);
        discardButton.setDisable(false);
        chooseDepotPane.setVisible(true);
    }

    /**
     * When the mouse enters in the "Depot 1" pane
     */
    public void mouseEnteredDepot1(){
        if(!depot1.isDisable()){
            depot1.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 2" pane
     */
    public void mouseEnteredDepot2(){
        if(!depot2.isDisable()){
            depot2.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 3" pane
     */
    public void mouseEnteredDepot3(){
        if(!depot3.isDisable()){
            depot3.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 4" pane
     */
    public void mouseEnteredDepot4(){
        if(!depot4.isDisable()){
            depot4.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Depot 5" pane
     */
    public void mouseEnteredDepot5(){
        if(!depot5.isDisable()){
            depot5.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "Depot 1" pane
     */
    public void mouseExitedDepot1(){
        if(!depot1.isDisable()){
            depot1.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 2" pane
     */
    public void mouseExitedDepot2(){
        if(!depot2.isDisable()){
            depot2.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 3" pane
     */
    public void mouseExitedDepot3(){
        if(!depot3.isDisable()){
            depot3.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 4" pane
     */
    public void mouseExitedDepot4(){
        if(!depot4.isDisable()){
            depot4.setEffect(null);
        }
    }
    /**
     * When the mouse exits from the "Depot 5" pane
     */
    public void mouseExitedDepot5(){
        if(!depot5.isDisable()){
            depot5.setEffect(null);
        }
    }

    /**
     * When the player clicks on the "Depot 1" pane,
     * if the action was to put again a resource (NotValidDepot) send a message to restore it and resume the
     * management of the remaining resources, else just send a message to store the resource.
     */
    public void clickDepot1(){
        if(!depot1.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot", resourceToStore.getThisColor(), 1, gui.getUsername()));
            moveNotValidLabel.setVisible(false);
            resourceStored =false;
        }
    }
    /**
     * When the player clicks on the "Depot 2" pane,
     * if the action was to put again a resource (NotValidDepot) send a message to restore it and resume the
     * management of the remaining resources, else just send a message to store the resource.
     */
    public void clickDepot2(){
        if(!depot2.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot", resourceToStore.getThisColor(), 2, gui.getUsername()));
            moveNotValidLabel.setVisible(false);
            resourceStored=false;
        }
    }
    /**
     * When the player clicks on the "Depot 3" pane,
     * if the action was to put again a resource (NotValidDepot) send a message to restore it and resume the
     * management of the remaining resources, else just send a message to store the resource.
     */
    public void clickDepot3(){
        if(!depot3.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot", resourceToStore.getThisColor(), 3, gui.getUsername()));
            moveNotValidLabel.setVisible(false);
            resourceStored=false;
        }
    }
    /**
     * When the player clicks on the "Depot 4" pane,
     * if the action was to put again a resource (NotValidDepot) send a message to restore it and resume the
     * management of the remaining resources, else just send a message to store the resource.
     */
    public void clickDepot4(){
        if(!depot4.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot", resourceToStore.getThisColor(), 4, gui.getUsername()));
            moveNotValidLabel.setVisible(false);
            resourceStored=false;
        }
    }
    /**
     * When the player clicks on the "Depot 5" pane,
     * if the action was to put again a resource (NotValidDepot) send a message to restore it and resume the
     * management of the remaining resources, else just send a message to store the resource.
     */
    public void clickDepot5(){
        if(!depot5.isDisable()){
            gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the depot", resourceToStore.getThisColor(), 5, gui.getUsername()));
            moveNotValidLabel.setVisible(false);
            resourceStored=false;
        }
    }

    /**
     * To discard the resource received
     */
    public void clickDiscardButton(){
        gui.sendMsg(new CChooseDiscardResourceMsg("I want to discard this resource",gui.getUsername()));
        resourceStored =true;
        chooseDepot();
    }

    /**
     * To move the resources in the warehouse before put the resources received, the scene temporary
     * returns to the PersonalBoardScene which manages the action
     */
    public void clickMoveResourceButton(){
        gui.getPersonalBoardSceneController().setTurnAction(TurnAction.MOVE_RESOURCE);
        gui.seePersonalBoard();
        waitMove=true;
        gui.getPersonalBoardSceneController().setReturnToMarket(true);
        gui.getPersonalBoardSceneController().chooseDepots();
    }

    /**
     * If the player wants just see the market before make the action, this button allows him to return in the
     * personal board, it appears just when the player choose the action "SEE MARKET BOARD"
     */
    public void clickBackButton(){
        gui.seePersonalBoard();
    }

    /**
     * To set the GUI which the controllers refers
     * @param gui the GUI of the player
     */
    public void setGui(GUI gui){this.gui=gui;}

    /**
     * To set the resources received from the BUY FROM MARKET action
     * @param resourcesToStore
     */
    public void setResourcesToStore(ArrayList<TypeResource> resourcesToStore){this.resourcesToStore = resourcesToStore;}

    /**
     * @return the market structure view similar to the structure of the market model
     */
    private ImageView[][] getMarketStructureView(){
        ImageView[][] marketStructureView = new ImageView[3][4];
        marketStructureView[0][0]=m00;
        marketStructureView[0][1]=m01;
        marketStructureView[0][2]=m02;
        marketStructureView[0][3]=m03;
        marketStructureView[1][0]=m10;
        marketStructureView[1][1]=m11;
        marketStructureView[1][2]=m12;
        marketStructureView[1][3]=m13;
        marketStructureView[2][0]=m20;
        marketStructureView[2][1]=m21;
        marketStructureView[2][2]=m22;
        marketStructureView[2][3]=m23;
        return marketStructureView;
    }

    /**
     * @return an arraylist with every "row/column" button
     */
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

    /**
     * To disable the "row/column" buttons
     */
    private void disableButtons(){
        ArrayList<Button> buttons = getButtons();
        for(Button button:buttons){
            button.setDisable(true);
        }
    }

    /**
     * @return the structure of the warehouse view similar to the warehouse model
     */
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

    /**
     * @return a list of the depot panes
     */
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1);
        depotPanes.add(depot2);
        depotPanes.add(depot3);
        depotPanes.add(depot4);
        depotPanes.add(depot5);
        return depotPanes;
    }

    /**
     * @return a list of the special depot images
     */
    private ArrayList<ImageView> getSpecialDepotView(){
        ArrayList<ImageView> specialDepotView = new ArrayList<>();
        specialDepotView.add(specialDepot1);
        specialDepotView.add(specialDepot2);
        return specialDepotView;
    }

    /**
     * To copy the warehouse view in the market from the personal board
     */
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

    /**
     * To enable the depot panes
     */
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

    /**
     * To disable the depot panes
     */
    private void disableDepotPane(){
        for(Pane pane:getDepotPanes()){
            pane.setDisable(true);
        }
    }

    /**
     * In the "depot" pop-up this method set the resource image and its label based on the type
     * @param type the type of the resource to store or discard
     */
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
     * To get the type resource from a color
     * @param color the color of the resource
     * @return the type of the resource
     */
    private TypeResource fromColorToType(it.polimi.ingsw.model.Color color){
        switch(color){
            case YELLOW:return TypeResource.COIN;
            case PURPLE:return TypeResource.SERVANT;
            case BLUE:return TypeResource.SHIELD;
            case GREY:return TypeResource.STONE;
            default:return null;
        }
    }

    /**
     * To set if the player has some ability WHITE_MARBLE activated
     * @param whiteSpecial
     */
    public void setWhiteSpecial(ArrayList<TypeResource> whiteSpecial){this.whiteSpecial=whiteSpecial;}

    /**
     * @return a list of the resources view in the pop-up to choose the resource the player wants
     */
    private ArrayList<ImageView> getResourcesView(){
        ArrayList<ImageView> resources=new ArrayList<>();
        resources.add(coin);
        resources.add(servant);
        resources.add(shield);
        resources.add(stone);
        return resources;
    }

    /**
     * To disable every resource image
     */
    private void disableResources(){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        for(ImageView imageView:getResourcesView()){
            imageView.setEffect(colorAdjust);
            imageView.setDisable(true);
        }
    }

    /**
     * When the server is unable, every pop-up is hidden and every action is disabled.
     * Then an error message is shown.
     * @param msg VServerUnableMsg
     */
    public void setWarningPane(VServerUnableMsg msg) {
        warningPane.setVisible(true);
        ArrayList<Button> buttons = getButtons();
        disableResources();
        chooseDepotPane.setVisible(false);
        chooseResourcePane.setVisible(false);
        for(Button button:buttons){
            button.setDisable(true);
        }
        backButton.setVisible(false);
    }

    /**
     * When the player received the VServerUnableMsg and click on the OK button, the GUI closes automatically
     */
    public void clickOkButton(){
        gui.close();
    }

    public void setResourceStored(boolean resourceStored) {
        this.resourceStored = resourceStored;
    }

    public void setWaitMove(boolean waitMove) {
        this.waitMove=waitMove;
    }

    public boolean isWaitMove() {
        return waitMove;
    }

    public void moveNotValid(){moveNotValidLabel.setVisible(true);}
}
