package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EndGameSceneController {
    private GUI gui;

    @FXML
    private ImageView outcome;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void showOutcome(boolean isTheWinner){
        if(isTheWinner){
            outcome.setImage(new Image("/images/winnerLogo.png"));
        }else{
            outcome.setImage(new Image("/images/loserLogo.png"));
        }
    }
}
