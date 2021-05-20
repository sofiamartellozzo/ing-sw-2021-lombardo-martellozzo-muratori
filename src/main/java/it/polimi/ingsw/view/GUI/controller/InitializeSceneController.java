package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CChooseDiscardResourceMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class InitializeSceneController {
    private GUI gui;

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
    private Button discardResource;

    @FXML
    private Button depot1, depot2, depot3;

    @FXML
    private ImageView coin,servant,shield,stone;

    @FXML
    private ImageView leaderCard1,leaderCard2,leaderCard3,leaderCard4;

    private Color chosenColor;

    private ArrayList<Integer> idLeaderCards;
    private ArrayList<Integer> chosenLeaderCards;

    public void start(){
        depot.setVisible(false);
        resource.setVisible(false);
        leaderCard.setVisible(false);

        message.setText("You're the player "+gui.getPlayer().getNumber());

        setPlayers();

        ArrayList<ArrayList<ImageView>> warehouse = getWarehouseGraphics();

        for(int i=0;i<warehouse.size();i++){
            ArrayList<ImageView> depot=warehouse.get(i);
            for(int j=0;j<depot.size();j++){
                depot.get(j).setVisible(false);
            }
        }

        disableResource();
        disableButtons();

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
        resourceLabel.setTextFill((new javafx.scene.paint.Color((double)255/255,(double)221/255,(double)0/255,1)));
        chooseDepot();
    }
    public void shieldMouseClick(){
        chosenColor =Color.BLUE;
        resource.setVisible(false);
        resourceChosen.setImage(shield.getImage());
        resourceLabel.setText("SHIELD");
        resourceLabel.setTextFill((new javafx.scene.paint.Color((double)0/255,(double)175/255,(double)255/255,1)));
        chooseDepot();
    }
    public void servantMouseClick(){
        chosenColor =Color.PURPLE;
        resource.setVisible(false);
        resourceChosen.setImage(servant.getImage());
        resourceLabel.setText("SERVANT");
        resourceLabel.setTextFill((new javafx.scene.paint.Color((double)187/255,(double)0/255,(double)255/255,1)));
        chooseDepot();
    }
    public void stoneMouseClick(){
        chosenColor =Color.GREY;
        resource.setVisible(false);
        resourceChosen.setImage(stone.getImage());
        resourceLabel.setText("STONE");
        resourceLabel.setTextFill((new javafx.scene.paint.Color((double)99/255,(double)99/255,(double)99/255,1)));
        chooseDepot();
    }

    private void chooseDepot(){
        setWarehouseGraphics();
        depotMessage.setText("You choose this resource:");
        Warehouse warehouse = gui.getWarehouse();
        ArrayList<Button> depotButtons=getDepotButton();
        for(Button button:depotButtons){
            button.setDisable(false);
        }
        depot.setVisible(true);
    }

    public void clickDepot1(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+gui.getUsername()+" has chosen a "+chosenColor+" resource and depot 1",chosenColor,1,gui.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void clickDepot2(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+gui.getUsername()+" has chosen a "+chosenColor+" resource and depot 2",chosenColor,2,gui.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void clickDepot3(){
        depot.setVisible(false);
        CChooseResourceAndDepotMsg responseMsg=new CChooseResourceAndDepotMsg("The player "+gui.getUsername()+" has chosen a "+chosenColor+" resource and depot 3",chosenColor,3,gui.getUsername());
        gui.sendMsg(responseMsg);
    }

    public void chooseLeaderCard(VChooseLeaderCardRequestMsg msg){
        ArrayList<ImageView> leaderCards = getLeaderCards();
        idLeaderCards=msg.getMiniDeckLeaderCardFour();
        for(int i=0;i<msg.getMiniDeckLeaderCardFour().size();i++){
            Integer id=msg.getMiniDeckLeaderCardFour().get(i);
            if(i<leaderCards.size()){
                leaderCards.get(i).setImage(new Image("/images/frontCards/LeaderCard ("+id+").png"));
            }else{
                leaderCards.get(i).setVisible(false);
            }
        }
        leaderCard.setVisible(true);
    }

    public void leaderCard1Entered(){leaderCard1.setEffect(new Glow());}
    public void leaderCard2Entered(){leaderCard2.setEffect(new Glow());}
    public void leaderCard3Entered(){leaderCard3.setEffect(new Glow());}
    public void leaderCard4Entered(){leaderCard4.setEffect(new Glow());}

    public void leaderCard1Exited(){leaderCard1.setEffect(null);}
    public void leaderCard2Exited(){leaderCard2.setEffect(null);}
    public void leaderCard3Exited(){leaderCard3.setEffect(null);}
    public void leaderCard4Exited(){leaderCard4.setEffect(null);}

    public void leaderCard1Click(){
        if(chosenLeaderCards==null){
            chosenLeaderCards=new ArrayList<>();
        }
        chosenLeaderCards.add(idLeaderCards.get(0));
        leaderCard1.setEffect(new Glow());
        if(chosenLeaderCards.size()==2){
            CChooseLeaderCardResponseMsg responseMsg= new CChooseLeaderCardResponseMsg("I chose two leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose");
            gui.sendMsg(responseMsg);
        }
    }
    public void leaderCard2Click(){
        if(chosenLeaderCards==null){
            chosenLeaderCards=new ArrayList<>();
        }
        chosenLeaderCards.add(idLeaderCards.get(1));
        leaderCard2.setEffect(new Glow());
        if(chosenLeaderCards.size()==2){
            CChooseLeaderCardResponseMsg responseMsg= new CChooseLeaderCardResponseMsg("I chose two leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose");
            gui.sendMsg(responseMsg);
        }
    }
    public void leaderCard3Click(){
        if(chosenLeaderCards==null){
            chosenLeaderCards=new ArrayList<>();
        }
        chosenLeaderCards.add(idLeaderCards.get(2));
        leaderCard3.setEffect(new Glow());
        if(chosenLeaderCards.size()==2){
            CChooseLeaderCardResponseMsg responseMsg= new CChooseLeaderCardResponseMsg("I chose two leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose");
            gui.sendMsg(responseMsg);
        }
    }
    public void leaderCard4Click(){
        if(chosenLeaderCards==null){
            chosenLeaderCards=new ArrayList<>();
        }
        chosenLeaderCards.add(idLeaderCards.get(3));
        leaderCard1.setEffect(new Glow());
        if(chosenLeaderCards.size()==2){
            CChooseLeaderCardResponseMsg responseMsg= new CChooseLeaderCardResponseMsg("I chose two leader cards",chosenLeaderCards,gui.getUsername(),"firstChoose");
            gui.sendMsg(responseMsg);
        }
    }

    public void setGui(GUI gui) { this.gui = gui; }


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
        Warehouse warehouse = gui.getWarehouse();

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

    public void clickDiscardResource(){
        discardResource.setDisable(false);
        this.gui.sendMsg(new CChooseDiscardResourceMsg("jajsjf",gui.getUsername()));
    }

    private ArrayList<Label> getPlayers(){
        ArrayList<Label> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        return players;
    }

    private void setPlayers(){
        ArrayList<Label> players = getPlayers();
        Object[] boardManagerPlayers = gui.getBoardManager().getPlayers().values().toArray();
        for(int i=0;i<4;i++){
            if(i<boardManagerPlayers.length){
                players.get(i).setText(((PlayerInterface) boardManagerPlayers[i]).getUsername());
            }else{
                players.get(i).setVisible(false);
            }
        }
    }

    private ArrayList<ImageView> getLeaderCards(){
        ArrayList<ImageView> leaderCards=new ArrayList<>();
        leaderCards.add(leaderCard1);
        leaderCards.add(leaderCard2);
        leaderCards.add(leaderCard3);
        leaderCards.add(leaderCard4);
        return leaderCards;
    }
}
