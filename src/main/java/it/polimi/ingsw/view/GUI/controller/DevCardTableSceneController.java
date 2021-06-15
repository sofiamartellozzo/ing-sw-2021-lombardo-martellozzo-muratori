package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.CBuyDevelopCardResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChangeActionTurnMsg;
import it.polimi.ingsw.message.viewMsg.VChooseDevelopCardRequestMsg;
import it.polimi.ingsw.message.viewMsg.VNotValidCardSpaceMsg;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
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

import java.util.ArrayList;

public class DevCardTableSceneController {
    private GUI gui;

    @FXML
    private ImageView devCard1_1,devCard1_2,devCard1_3,devCard1_4,devCard2_1,devCard2_2,devCard2_3,devCard2_4,devCard3_1,devCard3_2,devCard3_3,devCard3_4;

    @FXML
    private TitledPane chooseCardSpacePane,errorPopup;

    @FXML
    private ImageView cardSpace1,cardSpace2,cardSpace3;

    @FXML
    private Button cardSpace1Button, cardSpace2Button,cardSpace3Button,backButton,okButton;

    @FXML
    private TitledPane warningPane;

    @FXML
    private Label errorMessage;

    int chosenRow,chosenColumn;
    boolean justSee;

    public void start(){
        warningPane.setVisible(false);
        update(gui.getDevelopmentCardTable());
        setAllDevCardTableDisable();
        chooseCardSpacePane.setVisible(false);
        setAllButtonsDisable();
        errorPopup.setVisible(false);
        justSee=true;
    }

    public void choose(VChooseDevelopCardRequestMsg msg){
        justSee=false;
        backButton.setVisible(false);
        backButton.setDisable(true);
        update(msg.getDevelopmentCardTable());
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        ImageView[][] tableView = getDevCardTableView();
        boolean[][] availableCards = msg.getCardAvailable();
        boolean atLeastOne=false;
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                if(availableCards[i][j]){
                    tableView[i][j].setEffect(null);
                    tableView[i][j].setDisable(false);
                    atLeastOne=true;
                }else{
                    tableView[i][j].setEffect(colorAdjust);
                    tableView[i][j].setDisable(true);
                }
            }
        }
        if(!atLeastOne){
            setLabelText(errorMessage,"You can't buy any development card.\n Choose another action!");
            backButton.setDisable(false);
            backButton.setVisible(true);
            errorPopup.setVisible(true);
        }
    }

    public void chooseCardSpace(VNotValidCardSpaceMsg msg){
        justSee=false;
        backButton.setVisible(false);
        backButton.setDisable(true);
        chosenColumn=msg.getColumnTable();
        chosenColumn=msg.getRowTable();
        chooseCardSpace();
    }

    public void update(DevelopmentCardTable developmentCardTable){
        DevelopmentCardDeck[][] table = developmentCardTable.getTable();
        ImageView[][] devCardTableView = getDevCardTableView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                if(table[i][j].getDevelopDeck()!=null && !table[i][j].getDevelopDeck().isEmpty()) {
                    devCardTableView[i][j].setImage(new Image("/images/frontCards/DevelopmentCard (" + table[i][j].getUpperCard().getId() + ").png"));
                    devCardTableView[i][j].setVisible(true);
                }else{
                    devCardTableView[i][j].setVisible(false);
                }
            }
        }
        setAllDevCardTableDisable();
        chooseCardSpacePane.setVisible(false);
        setAllButtonsDisable();
    }

    public void clickDevCard1_1(){
        if(!devCard1_1.isDisable() && !justSee) {
            chosenRow = 0;
            chosenColumn = 0;
            chooseCardSpace();
        }
    }

    public void clickDevCard1_2(){
        if(!devCard1_2.isDisable() && !justSee) {
            chosenRow = 0;
            chosenColumn = 1;
            chooseCardSpace();
        }
    }
    public void clickDevCard1_3(){
        if(!devCard1_3.isDisable() && !justSee) {
            chosenRow = 0;
            chosenColumn = 2;
            chooseCardSpace();
        }
    }
    public void clickDevCard1_4(){
        if(!devCard1_4.isDisable() && !justSee) {
            chosenRow = 0;
            chosenColumn = 3;
            chooseCardSpace();
        }
    }
    public void clickDevCard2_1(){
        if(!devCard2_1.isDisable() && !justSee) {
            chosenRow = 1;
            chosenColumn = 0;
            chooseCardSpace();
        }
    }
    public void clickDevCard2_2(){
        if(!devCard2_2.isDisable() && !justSee) {
            chosenRow = 1;
            chosenColumn = 1;
            chooseCardSpace();
        }
    }
    public void clickDevCard2_3(){
        if(!devCard2_3.isDisable() && !justSee) {
            chosenRow = 1;
            chosenColumn = 2;
            chooseCardSpace();
        }
    }
    public void clickDevCard2_4(){
        if(!devCard2_4.isDisable() && !justSee) {
            chosenRow = 1;
            chosenColumn = 3;
            chooseCardSpace();
        }
    }
    public void clickDevCard3_1(){
        if(!devCard3_1.isDisable() && !justSee) {
            chosenRow = 2;
            chosenColumn = 0;
            chooseCardSpace();
        }
    }
    public void clickDevCard3_2(){
        if(!devCard3_2.isDisable() && !justSee) {
            chosenRow = 2;
            chosenColumn = 1;
            chooseCardSpace();
        }
    }
    public void clickDevCard3_3(){
        if(!devCard3_3.isDisable() && !justSee) {
            chosenRow = 2;
            chosenColumn = 2;
            chooseCardSpace();
        }
    }
    public void clickDevCard3_4(){
        if(!devCard3_4.isDisable() && !justSee) {
            chosenRow = 2;
            chosenColumn = 3;
            chooseCardSpace();
        }
    }

    public void mouseEnteredDevCard1_1(){
        if(!devCard1_1.isDisable() && !justSee){
            devCard1_1.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard1_2(){
        if(!devCard1_2.isDisable() && !justSee){
            devCard1_2.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard1_3(){
        if(!devCard1_3.isDisable() && !justSee){
            devCard1_3.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard1_4(){
        if(!devCard1_4.isDisable() && !justSee){
            devCard1_4.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard2_1(){
        if(!devCard2_1.isDisable() && !justSee){
            devCard2_1.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard2_2(){
        if(!devCard2_2.isDisable() && !justSee){
            devCard2_2.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard2_3(){
        if(!devCard2_3.isDisable() && !justSee){
            devCard2_3.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard2_4(){
        if(!devCard2_4.isDisable() && !justSee){
            devCard1_1.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard3_1(){
        if(!devCard3_1.isDisable() && !justSee){
            devCard3_1.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard3_2(){
        if(!devCard3_2.isDisable() && !justSee){
            devCard3_2.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard3_3(){
        if(!devCard3_3.isDisable() && !justSee){
            devCard3_3.setEffect(new Glow());
        }
    }
    public void mouseEnteredDevCard3_4(){
        if(!devCard3_4.isDisable() && !justSee){
            devCard3_4.setEffect(new Glow());
        }
    }
    public void mouseExitedDevCard1_1(){
        if(!devCard1_1.isDisable() && !justSee){
            devCard1_1.setEffect(null);
        }
    }
    public void mouseExitedDevCard1_2(){
        if(!devCard1_2.isDisable() && !justSee){
            devCard1_2.setEffect(null);
        }
    }
    public void mouseExitedDevCard1_3(){
        if(!devCard1_3.isDisable() && !justSee){
            devCard1_3.setEffect(null);
        }
    }
    public void mouseExitedDevCard1_4(){
        if(!devCard1_4.isDisable() && !justSee){
            devCard1_4.setEffect(null);
        }
    }
    public void mouseExitedDevCard2_1(){
        if(!devCard2_1.isDisable() && !justSee){
            devCard2_1.setEffect(null);
        }
    }
    public void mouseExitedDevCard2_2(){
        if(!devCard2_2.isDisable() && !justSee){
            devCard2_2.setEffect(null);
        }
    }
    public void mouseExitedDevCard2_3(){
        if(!devCard2_3.isDisable() && !justSee){
            devCard2_3.setEffect(null);
        }
    }
    public void mouseExitedDevCard2_4(){
        if(!devCard2_4.isDisable() && !justSee){
            devCard2_4.setEffect(null);
        }
    }
    public void mouseExitedDevCard3_1(){
        if(!devCard3_1.isDisable() && !justSee){
            devCard3_1.setEffect(null);
        }
    }
    public void mouseExitedDevCard3_2(){
        if(!devCard3_2.isDisable() && !justSee){
            devCard3_2.setEffect(null);
        }
    }
    public void mouseExitedDevCard3_3(){
        if(!devCard3_3.isDisable() && !justSee){
            devCard3_3.setEffect(null);
        }
    }
    public void mouseExitedDevCard3_4(){
        if(!devCard3_4.isDisable() && !justSee){
            devCard3_4.setEffect(null);
        }
    }


    private void chooseCardSpace(){
        setCardSpaceView();
        setAllButtonsEnable();
        chooseCardSpacePane.setVisible(true);
    }

    public void clickCardSpace1Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,0));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
        backButton.setVisible(true);
        backButton.setDisable(false);
    }

    public void clickCardSpace2Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,1));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
        backButton.setVisible(true);
        backButton.setDisable(false);
    }
    public void clickCardSpace3Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,2));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
        backButton.setVisible(true);
        backButton.setDisable(false);
    }
    public void clickBackButton(){
        gui.seePersonalBoard();
        setAllDevCardTableDisable();
        if(!justSee){
            errorPopup.setVisible(false);
            gui.sendMsg(new CChangeActionTurnMsg("I can't buy anything,change action",gui.getUsername(),TurnAction.BUY_CARD));
            justSee=true;
        }
    }

    public void setGui(GUI gui){this.gui=gui;}

    private ImageView[][] getDevCardTableView(){
        ImageView[][] devCardTableView = new ImageView[3][4];
        devCardTableView[0][0]=devCard1_1;
        devCardTableView[0][1]=devCard1_2;
        devCardTableView[0][2]=devCard1_3;
        devCardTableView[0][3]=devCard1_4;
        devCardTableView[1][0]=devCard2_1;
        devCardTableView[1][1]=devCard2_2;
        devCardTableView[1][2]=devCard2_3;
        devCardTableView[1][3]=devCard2_4;
        devCardTableView[2][0]=devCard3_1;
        devCardTableView[2][1]=devCard3_2;
        devCardTableView[2][2]=devCard3_3;
        devCardTableView[2][3]=devCard3_4;
        return devCardTableView;
    }

    private void setAllDevCardTableDisable(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        ImageView[][] devCardTableView = getDevCardTableView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                devCardTableView[i][j].setEffect(colorAdjust);
                devCardTableView[i][j].setDisable(true);
            }
        }
    }

    private ArrayList<ImageView> getCardSpaceView(){
        ArrayList<ImageView> cardSpaceView = new ArrayList<>();
        cardSpaceView.add(cardSpace1);
        cardSpaceView.add(cardSpace2);
        cardSpaceView.add(cardSpace3);
        return cardSpaceView;
    }

    private void setCardSpaceView(){
        ArrayList<ImageView> cardSpaceTableView = getCardSpaceView();
        //Remove comment when getCardSpaceView() is implemented in the GameSceneController
        ArrayList<ImageView> cardSpacePersonalBoardView = gui.getGameSceneController().getCardSpacesView();
        for(int i=0;i<3;i++){
            cardSpaceTableView.get(i).setImage(cardSpacePersonalBoardView.get(i).getImage());
        }
    }

    private ArrayList<Button> getCardSpaceButtons(){
        ArrayList<Button> buttons= new ArrayList<>();
        buttons.add(cardSpace1Button);
        buttons.add(cardSpace2Button);
        buttons.add(cardSpace3Button);
        return buttons;
    }

    private void setAllButtonsDisable(){
        ArrayList<Button> buttons = getCardSpaceButtons();
        for(Button button:buttons){
            button.setDisable(true);
        }
    }

    private void setAllButtonsEnable(){
        ArrayList<Button> buttons = getCardSpaceButtons();
        for(Button button:buttons){
            button.setDisable(false);
        }
    }

    public void setAllCardNormal(){
        ImageView[][] devCardTableView=getDevCardTableView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                devCardTableView[i][j].setEffect(null);
            }
        }
    }

    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }


    public void setWarningPane(VServerUnableMsg msg) {
        warningPane.setVisible(true);
        setAllDevCardTableDisable();
        chooseCardSpacePane.setVisible(false);
        setAllButtonsDisable();
        errorPopup.setVisible(false);
        backButton.setVisible(false);
    }

    public void clickOkButton(){
        gui.close();
    }
}
