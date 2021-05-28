package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CBuyDevelopCardResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChangeActionTurnMsg;
import it.polimi.ingsw.message.viewMsg.VChooseDevelopCardRequestMsg;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class DevCardTableSceneController {
    private GUI gui;

    @FXML
    private ImageView devCard1_1,devCard1_2,devCard1_3,devCard1_4,devCard2_1,devCard2_2,devCard2_3,devCard2_4,devCard3_1,devCard3_2,devCard3_3,devCard3_4,backButton;

    @FXML
    private TitledPane chooseCardSpacePane;

    @FXML
    private ImageView cardSpace1,cardSpace2,cardSpace3;

    @FXML
    private Button cardSpace1Button, cardSpace2Button,cardSpace3Button;

    int chosenRow,chosenColumn;

    public void start(){
        update(gui.getDevelopmentCardTable());
        setAllDevCardTableDisable();
        chooseCardSpacePane.setVisible(false);
        setAllButtonsDisable();
    }

    public void choose(VChooseDevelopCardRequestMsg msg){
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
            gui.getPersonalBoardSceneController().setChooseActionMessage("You can't buy any development card.\n Choose another action:");
            gui.sendMsg(new CChangeActionTurnMsg("This action can't be executed",gui.getUsername(), TurnAction.BUY_CARD));
            gui.seePersonalBoard();
            backButton.setDisable(false);
        }
    }

    public void update(DevelopmentCardTable developmentCardTable){
        DevelopmentCardDeck[][] table = developmentCardTable.getTable();
        ImageView[][] devCardTableView = getDevCardTableView();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                devCardTableView[i][j].setImage(new Image("/images/frontCards/DevelopmentCard ("+table[i][j].getUpperCard().getId()+").png"));
                devCardTableView[i][j].setVisible(true);
            }
        }
        setAllDevCardTableDisable();
        chooseCardSpacePane.setVisible(false);
        setAllButtonsDisable();
    }

    public void clickDevCard1_1(){
        if(!devCard1_1.isDisable()) {
            chosenRow = 1;
            chosenColumn = 1;
            chooseCardSpace();
        }
    }

    public void clickDevCard1_2(){
        if(!devCard1_2.isDisable()) {
            chosenRow = 1;
            chosenColumn = 2;
            chooseCardSpace();
        }
    }
    public void clickDevCard1_3(){
        chosenRow = 1;
        chosenColumn=3;
        chooseCardSpace();
    }
    public void clickDevCard1_4(){
        chosenRow = 1;
        chosenColumn=4;
        chooseCardSpace();
    }
    public void clickDevCard2_1(){
        chosenRow = 2;
        chosenColumn=1;
        chooseCardSpace();
    }
    public void clickDevCard2_2(){
        chosenRow = 2;
        chosenColumn=2;
        chooseCardSpace();
    }
    public void clickDevCard2_3(){
        chosenRow = 2;
        chosenColumn=3;
        chooseCardSpace();
    }
    public void clickDevCard2_4(){
        chosenRow = 2;
        chosenColumn=4;
        chooseCardSpace();
    }
    public void clickDevCard3_1(){
        chosenRow = 3;
        chosenColumn=1;
        chooseCardSpace();
    }
    public void clickDevCard3_2(){
        chosenRow = 3;
        chosenColumn=2;
        chooseCardSpace();
    }
    public void clickDevCard3_3(){
        chosenRow = 3;
        chosenColumn=3;
        chooseCardSpace();
    }
    public void clickDevCard3_4(){
        chosenRow = 3;
        chosenColumn=4;
        chooseCardSpace();
    }

    private void chooseCardSpace(){
        setCardSpaceView();
        setAllButtonsEnable();
        chooseCardSpacePane.setVisible(true);
    }

    public void clickCardSpace1Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,1));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
    }

    public void clickCardSpace2Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,2));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
    }
    public void clickCardSpace3Button(){
        gui.sendMsg(new CBuyDevelopCardResponseMsg("I choose a development card to buy",gui.getUsername(),chosenRow,chosenColumn,3));
        chooseCardSpacePane.setVisible(false);
        gui.seePersonalBoard();
    }
    public void clickBackButton(){
        gui.seePersonalBoard();
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
        //ArrayList<ImageView> cardSpacePersonalBoardView = gui.getGameSceneController().getCardSpaceView();
        for(int i=0;i<3;i++){
            //cardSpaceTableView.get(i).setImage(cardSpacePersonalBoardView.get(i).getImage());
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

}
