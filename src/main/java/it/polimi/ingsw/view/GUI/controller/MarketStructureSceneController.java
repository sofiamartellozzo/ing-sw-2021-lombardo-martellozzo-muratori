package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CBuyFromMarketInfoMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.updateMsg.VUpdateMarketMsg;
import it.polimi.ingsw.message.viewMsg.VBuyFromMarketRequestMsg;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.util.ArrayList;


public class MarketStructureSceneController {
    private GUI gui;

    @FXML
    private ImageView marble1_1,marble1_2,marble1_3,marble1_4,marble2_1,marble2_2,marble2_3,marble2_4,marble3_1,marble3_2,marble3_3,marble3_4,outMarble;

    @FXML
    private Button row1Button,row2Button,row3Button,column1Button,column2Button,column3Button,column4Button;

    public void start(){
        update(gui.getMarketStructureData());
        ArrayList<Button> buttons = getButtons();
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
        ArrayList<Button> buttons=getButtons();
        for(Button button:buttons){
            button.setDisable(false);
        }
    }

    public void clickRow1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickRow2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickRow3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"row",3);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickColumn1(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",1);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickColumn2(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",2);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickColumn3(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",3);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }
    public void clickColumn4(){
        CBuyFromMarketInfoMsg responseMsg= new CBuyFromMarketInfoMsg("I chose the row/column that I want to take from the market",gui.getUsername(),"column",4);
        gui.sendMsg(responseMsg);
        disableButtons();
        gui.getDevCardTableStage().show();
        gui.getStage().show();
    }

    public void setGui(GUI gui){this.gui=gui;}

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
}
