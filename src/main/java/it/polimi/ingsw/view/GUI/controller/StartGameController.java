package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class StartGameController {
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    private Button onlineServerButton;

    @FXML
    private Button offlineButton;

    public void start(){
        onlineServerButton.setVisible(true);
        offlineButton.setVisible(true);
    }

    public void clickOnlineServerButton() {

        gui.setOffline(false);
        gui.changeScene(gui.getIntroScene());
        gui.getIntroSceneController().start();

    }
    public void clickOfflineButton(){

        gui.setOffline(true);
        gui.changeScene(gui.getIntroScene());
        gui.getIntroSceneController().start();
    }

    public void serverUnavailable() {
        onlineServerButton.setDisable(true);
    }
}
