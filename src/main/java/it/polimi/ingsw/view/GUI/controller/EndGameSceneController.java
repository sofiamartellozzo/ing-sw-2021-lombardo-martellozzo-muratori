package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CNotStartAgainMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * this class handles the end of a game
 * it shows the outcome to the player (winner or loser) and asks him if he wants to play another game or stop there
 */
public class EndGameSceneController {
    private GUI gui;

    @FXML
    private ImageView loserImage,winnerImage;


    @FXML
    private Pane askNewGamePane;

    @FXML
    private Button yesButton,noButton;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void start(){
        loserImage.setVisible(false);
        winnerImage.setVisible(false);
        askNewGamePane.setVisible(false);
        yesButton.setDisable(true);
        noButton.setDisable(true);
    }

    public void showOutcome(boolean isTheWinner){
        if(isTheWinner){
            winnerImage.setVisible(true);
        }else{
            loserImage.setVisible(true);
        }
    }

    public void askNewGame(){
        askNewGamePane.setVisible(true);
        yesButton.setDisable(false);
        noButton.setDisable(false);
    }

    public void clickYesButton(){
        if(!yesButton.isDisable()){
            gui.restartIntroScene();
        }
    }

    public void clickNoButton(){
        if(!noButton.isDisable()){
            gui.sendMsg(new CNotStartAgainMsg("I don't want to play again"));
            gui.close();
        }
    }
}
