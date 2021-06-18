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
 * When the game ends, the scene changes to the EndGameScene which shows the outcome (winner/loser),
 * this controller handles it.
 */
public class EndGameSceneController {
    private GUI gui;

    @FXML
    private ImageView loserImage,winnerImage;


    @FXML
    private Pane askNewGamePane;

    @FXML
    private Button yesButton,noButton;

    /**
     * To set the GUI which refers
     * @param gui the GUI of the player
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * To prepare the scene after it's set
     */
    public void start(){
        loserImage.setVisible(false);
        winnerImage.setVisible(false);
        askNewGamePane.setVisible(false);
        yesButton.setDisable(true);
        noButton.setDisable(true);
    }

    /**
     * To show the outcome
     * @param isTheWinner true=winner, false=loser
     */
    public void showOutcome(boolean isTheWinner){
        if(isTheWinner){
            winnerImage.setVisible(true);
        }else{
            loserImage.setVisible(true);
        }
    }

    /**
     * Show a message to ask if the player wants to play again
     */
    public void askNewGame(){
        askNewGamePane.setVisible(true);
        yesButton.setDisable(false);
        noButton.setDisable(false);
    }

    /**
     * If the player click on YES the scene is restart to the StartScene
     */
    public void clickYesButton(){
        if(!yesButton.isDisable()){
            gui.restartIntroScene();
        }
    }

    /**
     * If the player click on NO a message of end game is send and then the GUI closes.
     */
    public void clickNoButton(){
        if(!noButton.isDisable()){
            gui.sendMsg(new CNotStartAgainMsg("I don't want to play again"));
            gui.close();
        }
    }
}
