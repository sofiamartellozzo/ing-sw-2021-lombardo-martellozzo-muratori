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

/**
 * When every player is connected to the room, the initialization of the game starts with this scene,
 * to allocate the resources and faith point to the players.
 */

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
    private int countResource=0;
    private int resourceToReceive;

    /**
     * To prepare the scene when this scene is set
     */
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

    /**
     * To show a pop-up to choose which resource the player wants
     */
    public void chooseResource(){
        waitPane.setVisible(false);
        discardButton.setDisable(false);
        chooseResourcePane.setVisible(true);
    }

    /**
     * When the player entered with the mouse in the "COIN" image
     */
    public void mouseEnteredCoin(){
        coin.setEffect(new Glow());
    }
    /**
     * When the player entered with the mouse in the "SERVANT" image
     */
    public void mouseEnteredServant(){
        servant.setEffect(new Glow());
    }
    /**
     * When the player entered with the mouse in the "SHIELD" image
     */
    public void mouseEnteredShield(){
        shield.setEffect(new Glow());
    }
    /**
     * When the player entered with the mouse in the "STONE" image
     */
    public void mouseEnteredStone(){
        stone.setEffect(new Glow());
    }

    /**
     * When the player exited with the mouse in the "COIN" image
     */
    public void mouseExitedCoin(){coin.setEffect(null);}
    /**
     * When the player exited with the mouse in the "SERVANT" image
     */
    public void mouseExitedServant(){servant.setEffect(null);}
    /**
     * When the player exited with the mouse in the "SHIELD" image
     */
    public void mouseExitedShield(){shield.setEffect(null);}
    /**
     * When the player exited with the mouse in the "STONE" image
     */
    public void mouseExitedStone(){stone.setEffect(null);}

    /**
     * When the player click with the mouse in the "COIN" image, set the type in the scene and prepare and show
     * a pop-up to choose in which depot store it
     */
    public void clickCoin(){
        chosenType =TypeResource.COIN;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    /**
     * When the player click with the mouse in the "SERVANT" image, set the type in the scene and prepare and show
     * a pop-up to choose in which depot store it
     */
    public void clickServant(){
        chosenType =TypeResource.SERVANT;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    /**
     * When the player click with the mouse in the "SHIELD" image, set the type in the scene and prepare and show
     * a pop-up to choose in which depot store it
     */
    public void clickShield(){
        chosenType =TypeResource.SHIELD;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }
    /**
     * When the player click with the mouse in the "STONE" image, set the type in the scene and prepare and show
     * a pop-up to choose in which depot store it
     */
    public void clickStone(){
        chosenType =TypeResource.STONE;
        chooseResourcePane.setVisible(false);
        discardButton.setDisable(true);
        chooseDepot();
    }

    /**
     * When the player click to the Discard Button every pop-up is hidden and send a message to server/message handler
     */
    public void clickDiscardButton(){
        chooseResourcePane.setVisible(false);
        chooseDepotPane.setVisible(false);
        discardButton.setDisable(true);
        gui.sendMsg(new CChooseDiscardResourceMsg("I want to discard this resource",gui.getUsername()));
    }

    /**
     * To prepare a pop-up to choose the depot where to store the resource chosen
     */
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

    /**
     * In case the depot chosen is not valid, show another time a pop-up to choose a different depot.
     * @param msg VNotValidDepotMsg
     */
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

    /**
     * When the mouse enters in the "Depot 1" pane
     */
    public void mouseEnteredDepot1(){depot1Pane.setEffect(new Glow());}
    /**
     * When the mouse enters in the "Depot 2" pane
     */
    public void mouseEnteredDepot2(){depot2Pane.setEffect(new Glow());}
    /**
     * When the mouse enters in the "Depot 3" pane
     */
    public void mouseEnteredDepot3(){depot3Pane.setEffect(new Glow());}
    /**
     * When the mouse exits from the "Depot 1" pane
     */
    public void mouseExitedDepot1(){depot1Pane.setEffect(null);}
    /**
     * When the mouse exits from the "Depot 2" pane
     */
    public void mouseExitedDepot2(){depot2Pane.setEffect(null);}
    /**
     * When the mouse exits in the "Depot 3" pane
     */
    public void mouseExitedDepot3(){depot3Pane.setEffect(null);}

    /**
     * When the player click on the "Depot 1" pane, a response message with the chosen type and depot is send
     */
    public void clickDepot1(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),1,gui.getUsername()));
        resourceToReceive--;
        if(resourceToReceive!=0){
            chooseResource();
        }
    }
    /**
     * When the player click on the "Depot 2" pane, a response message with the chosen type and depot is send
     */
    public void clickDepot2(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),2,gui.getUsername()));
        resourceToReceive--;
        if(resourceToReceive!=0){
            chooseResource();
        }
    }
    /**
     * When the player click on the "Depot 3" pane, a response message with the chosen type and depot is send
     */
    public void clickDepot3(){
        disableDepotPanes();
        discardButton.setDisable(true);
        chooseDepotPane.setVisible(false);
        gui.sendMsg(new CChooseResourceAndDepotMsg("I choose the resource and the depot",fromTypeToColor(chosenType),3,gui.getUsername()));
        resourceToReceive--;
        if(resourceToReceive!=0){
            chooseResource();
        }
    }

    /**
     * To prepare and show a pop-up with the possible leader cards to choose,
     * every image is set based on the ID of the card
     */
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

    /**
     * When the mouse enters in the "Leader Card 1" image
     */
    public void mouseEnteredLeaderCard1(){
        if(!leaderCard1.isDisable()){
            leaderCard1.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Leader Card 2" image
     */
    public void mouseEnteredLeaderCard2(){
        if(!leaderCard2.isDisable()){
            leaderCard2.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Leader Card 3" image
     */
    public void mouseEnteredLeaderCard3(){
        if(!leaderCard3.isDisable()){
            leaderCard3.setEffect(new Glow());
        }
    }
    /**
     * When the mouse enters in the "Leader Card 4" image
     */
    public void mouseEnteredLeaderCard4(){
        if(!leaderCard4.isDisable()){
            leaderCard4.setEffect(new Glow());
        }
    }
    /**
     * When the mouse exits from the "Leader Card 1" image
     */
    public void mouseExitedLeaderCard1(){
        leaderCard1.setEffect(null);
    }
    /**
     * When the mouse exits from the "Leader Card 2" image
     */
    public void mouseExitedLeaderCard2(){
        leaderCard2.setEffect(null);
    }
    /**
     * When the mouse exits from the "Leader Card 3" image
     */
    public void mouseExitedLeaderCard3(){
        leaderCard3.setEffect(null);
    }
    /**
     * When the mouse exits from the "Leader Card 4" image
     */
    public void mouseExitedLeaderCard4(){
        leaderCard4.setEffect(null);
    }
    /**
     * When the player click on the "Leader Card 1" image, the card is switch to the back,
     * memorize in the scene temporary and if the player has already chosen two leader cards
     * a response message is send to the Server/Message Handler
     */
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
    /**
     * When the player click on the "Leader Card 2" image, the card is switch to the back,
     * memorize in the scene temporary and if the player has already chosen two leader cards
     * a response message is send to the Server/Message Handler
     */
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
    /**
     * When the player click on the "Leader Card 3" image, the card is switch to the back,
     * memorize in the scene temporary and if the player has already chosen two leader cards
     * a response message is send to the Server/Message Handler
     */
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
    /**
     * When the player click on the "Leader Card 4" image, the card is switch to the back,
     * memorize in the scene temporary and if the player has already chosen two leader cards
     * a response message is send to the Server/Message Handler
     */
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

    /**
     * Private method to convert easily a type to color
     * @param type the type to convert
     * @return the relative color
     */
    private it.polimi.ingsw.model.Color fromTypeToColor(TypeResource type){
        switch(type){
            case COIN:return it.polimi.ingsw.model.Color.YELLOW;
            case SERVANT:return it.polimi.ingsw.model.Color.PURPLE;
            case SHIELD:return it.polimi.ingsw.model.Color.BLUE;
            case STONE:return it.polimi.ingsw.model.Color.GREY;
            default:return null;
        }
    }

    /**
     * Private method to convert easily a color to a type
     * @param color the color to convert
     * @return the relative type of resource
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
     * To disable every depot pane
     */
    private void disableDepotPanes(){
        depot1Pane.setDisable(true);
        depot2Pane.setDisable(true);
        depot3Pane.setDisable(true);
    }

    /**
     * To enable every depot pane
     */
    private void enableDepotPanes(){
        depot1Pane.setDisable(false);
        depot2Pane.setDisable(false);
        depot3Pane.setDisable(false);
    }

    /**
     * Copy the view of the warehouse from the personal board, so the images are set automatically from there.
     */
    private void copyWarehouseFromPersonalBoardView(){
        ArrayList<ArrayList<ImageView>> warehouseFromPersonalBoardView = gui.getPersonalBoardSceneController().getWarehouseView();
        ArrayList<ArrayList<ImageView>> warehouseView = getWarehouseView();
        for(int i=0;i<3;i++){
            for(int j=0;j<warehouseView.get(i).size();j++){
                warehouseView.get(i).get(j).setImage(warehouseFromPersonalBoardView.get(i).get(j).getImage());
            }
        }
    }

    /**
     * To obtain a structure of the resources images of the warehouse similar to the Warehouse Model structure,
     * in order to change easily and quickly the images
     * @return the warehouse view
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

        warehouseView.add(depot1View);
        warehouseView.add(depot2View);
        warehouseView.add(depot3View);

        return warehouseView;
    }

    /**
     * To set the GUI which the scene is refered
     * @param gui The GUI of the player
     */
    public void setGui(GUI gui){this.gui=gui;}

    /**
     * To get an ordered list of every depot pane
     * @return
     */
    private ArrayList<Pane> getDepotPanes(){
        ArrayList<Pane> depotPanes=new ArrayList<>();
        depotPanes.add(depot1Pane);
        depotPanes.add(depot2Pane);
        depotPanes.add(depot3Pane);
        return depotPanes;
    }

    /**
     * To get an ordered list of the leader cards images
     * @return
     */
    private ArrayList<ImageView> getLeaderCardsView(){
        ArrayList<ImageView> leaderCardsView=new ArrayList<>();
        leaderCardsView.add(leaderCard1);
        leaderCardsView.add(leaderCard2);
        leaderCardsView.add(leaderCard3);
        leaderCardsView.add(leaderCard4);
        return leaderCardsView;
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
     * To show a message that says that the player has to wait the others
     */
    public void showWaitPane(){
        if(resourceToReceive==0) {
            waitPane.setVisible(true);
        }
    }

    /**
     * To show a message that the server is no more available and the game ends, every pane is hidden
     * @param msg VServerUnableMsg
     */
    public void setWarningPane(VServerUnableMsg msg) {
        warningPane.setVisible(true);
        chooseDepotPane.setVisible(false);
        chooseResourcePane.setVisible(false);
        leaderCardPane.setVisible(false);
        waitPane.setVisible(false);
    }

    /**
     * If after the "VServerUnableMsg" the player click on the OK Button the GUI is closed automatically
     */
    public void clickOkButton(){
        gui.close();
    }

    /**
     * Setter method
     * @param resourceToReceive to memorize how many resource the player should receive
     */
    public void setResourceToReceive(int resourceToReceive) {
        this.resourceToReceive = resourceToReceive;
    }
}
