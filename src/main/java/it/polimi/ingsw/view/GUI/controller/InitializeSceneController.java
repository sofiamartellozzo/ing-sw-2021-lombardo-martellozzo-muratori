package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CChooseDiscardResourceMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.view.GUI.GUI;
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


    private TypeResource chosenType;

    public void start(){
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        disableDepotPanes();
    }

    public void chooseResource(){
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

    public void mouseEnteredDepot1(){depot1Pane.setEffect(new Glow());}
    public void mouseEnteredDepot2(){depot2Pane.setEffect(new Glow());}
    public void mouseEnteredDepot3(){depot3Pane.setEffect(new Glow());}
    public void mouseExitedDepot1(){depot1Pane.setEffect(null);}
    public void mouseExitedDepot2(){depot2Pane.setEffect(null);}
    public void mouseExitedDepot3(){depot3Pane.setEffect(null);}
    public void clickDepot1(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),1,gui.getUsername()));
    }
    public void clickDepot2(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),2,gui.getUsername()));
    }
    public void clickDepot3(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),3,gui.getUsername()));
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
            for(int j=0;j<warehouseView.size();i++){
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
}
