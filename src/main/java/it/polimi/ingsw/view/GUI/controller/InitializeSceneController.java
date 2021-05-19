package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseResourceAndDepotMsg;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.resourceManagement.Depot;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class InitializeSceneController {
    private GUI gui;
    private PlayerInterface player;

    @FXML
    private Label player1,player2,player3,player4;

    @FXML
    private Label message;

    @FXML
    private TitledPane depot, resource, leaderCard;

    @FXML
    private ImageView resource1_1, resource2_1,resource2_2,resource3_1,resource3_2,resource3_3;

    @FXML
    private Label depotMessage, resourceLabel;

    @FXML
    private ImageView resourceChosen;


    @FXML
    private Button depot1, depot2, depot3;

    @FXML
    private ImageView coin,servant,shield,stone;

    @FXML
    private ImageView leaderCard1,leaderCard2,leaderCard3,leaderCard4;

    @FXML
    private GridPane commands;

    private Color chosenColor;

    public void start(){
        depot.setVisible(false);
        resource.setVisible(false);
        leaderCard.setVisible(false);

        message.setText("You're the player "+player.getNumber());

        ArrayList<ArrayList<ImageView>> warehouse = getWarehouseGraphics();

        for(int i=0;i<warehouse.size();i++){
            ArrayList<ImageView> depot=warehouse.get(i);
            for(int j=0;j<depot.size();j++){
                depot.get(j).setVisible(false);
            }
        }
        disableResource();
        disableButtons();

        commands.setVisible(false);

    }

    public void chooseResource(VChooseResourceAndDepotMsg msg){
        coin.setDisable(false);
        coin.setEffect(null);
        servant.setDisable(false);
        servant.setEffect(null);
        shield.setDisable(false);
        shield.setEffect(null);
        stone.setDisable(false);
        stone.setEffect(null);
        resource.setVisible(true);
    }

    public void coinMouseEntered(){
        coin.setEffect(new Glow());
    }
    public void shieldMouseEntered(){
        shield.setEffect(new Glow());
    }
    public void servantMouseEntered(){servant.setEffect(new Glow());}
    public void stoneMouseEntered(){
        stone.setEffect(new Glow());
    }

    public void coinMouseExited(){
        coin.setEffect(null);
    }
    public void shieldMouseExited(){
        shield.setEffect(null);
    }
    public void servantMouseExited(){servant.setEffect(null);}
    public void stoneMouseExited(){
        stone.setEffect(null);
    }

    public void coinMouseClick(){
        chosenColor =Color.YELLOW;
        resource.setVisible(false);
        resourceChosen.setImage(coin.getImage());
        resourceLabel.setText("COIN");
        resourceLabel.setTextFill((new javafx.scene.paint.Color(255,221,0,1)));
        chooseDepot();
    }
    public void shieldMouseClick(){
        chosenColor =Color.BLUE;
        resource.setVisible(false);
        resourceChosen.setImage(shield.getImage());
        resourceLabel.setText("SHIELD");
        resourceLabel.setTextFill((new javafx.scene.paint.Color(0,175,255,1)));
        chooseDepot();
    }
    public void servantMouseClick(){
        chosenColor =Color.PURPLE;
        resource.setVisible(false);
        resourceChosen.setImage(servant.getImage());
        chooseDepot();
    }
    public void stoneMouseClick(){
        chosenColor =Color.GREY;
        resource.setVisible(false);
        resourceChosen.setImage(stone.getImage());
        chooseDepot();
    }

    private void chooseDepot(){
        setWarehouseGraphics();
        Warehouse warehouse = player.getGameSpace().getWarehouse();
        ArrayList<Button> depotButtons=getDepotButton();
        for(Button button:depotButtons){
            button.setDisable(false);
        }
        depot.setVisible(true);
    }

    public void clickDepot1(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+player.getUsername()+" has chosen a "+chosenColor+" resource and depot 1",chosenColor,1,player.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void clickDepot2(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+player.getUsername()+" has chosen a "+chosenColor+" resource and depot 2",chosenColor,2,player.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void clickDepot3(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+player.getUsername()+" has chosen a "+chosenColor+" resource and depot 3",chosenColor,3,player.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void chooseLeaderCard(VChooseLeaderCardRequestMsg msg){

    }

    public void setGui(GUI gui) { this.gui = gui; }

    public void setPlayer(PlayerInterface player){this.player=player;}

    private ArrayList<ArrayList<ImageView>> getWarehouseGraphics(){
        ArrayList<ArrayList<ImageView>> warehouseGraphics=new ArrayList<>();
        ArrayList<ImageView> depot1=new ArrayList<>();
        ArrayList<ImageView> depot2=new ArrayList<>();
        ArrayList<ImageView> depot3=new ArrayList<>();

        depot1.add(resource1_1);
        depot2.add(resource2_1);
        depot2.add(resource2_2);
        depot3.add(resource3_1);
        depot3.add(resource3_2);
        depot3.add(resource3_3);

        warehouseGraphics.add(depot1);
        warehouseGraphics.add(depot2);
        warehouseGraphics.add(depot3);

        return warehouseGraphics;
    }

    private void disableResource(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);

        coin.setEffect(colorAdjust);
        coin.setDisable(true);
        servant.setEffect(colorAdjust);
        servant.setDisable(true);
        shield.setEffect(colorAdjust);
        shield.setDisable(true);
        stone.setEffect(colorAdjust);
        stone.setDisable(true);
    }

    private void setWarehouseGraphics(){
        ArrayList<ArrayList<ImageView>> warehouseGraphics=getWarehouseGraphics();
        Warehouse warehouse = player.getGameSpace().getWarehouse();

        for(int i=0;i<warehouse.getDepots().size();i++){
            Depot depot = warehouse.getDepots().get(i);
            ArrayList<ImageView> depotGraphics=warehouseGraphics.get(i);
            for(int j=0;j<depot.getResources().size();j++){
                ImageView resourceGraphics=depotGraphics.get(j);
                Resource resource=depot.getResources().get(j);
                if(resource.getType().equals(TypeResource.COIN)){
                    resourceGraphics.setImage(coin.getImage());
                }else if(resource.getType().equals(TypeResource.SHIELD)){
                    resourceGraphics.setImage(shield.getImage());
                }else if(resource.getType().equals(TypeResource.SERVANT)){
                    resourceGraphics.setImage(servant.getImage());
                }else if(resource.getType().equals(TypeResource.STONE)){
                    resourceGraphics.setImage(stone.getImage());
                }
                resourceGraphics.setVisible(true);
            }
        }
    }

    private ArrayList<Button> getDepotButton(){
        ArrayList<Button> buttons=new ArrayList<>();
        buttons.add(depot1);
        buttons.add(depot2);
        buttons.add(depot3);
        return buttons;
    }

    private void disableButtons(){
        ArrayList<Button> buttons = getDepotButton();
        for(Button button:buttons){
            button.setDisable(true);
        }
    }
}
