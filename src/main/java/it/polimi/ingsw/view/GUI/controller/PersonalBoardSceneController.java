package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PersonalBoardSceneController {
    private GUI gui;

    @FXML
    private Button buyCardButton, buyFromMarketButton, moveResourceButton, endTurnButton, activePPButton, visitOtherBoardButton, activeLeaderCardButton, discardLeaderCardButton;

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
