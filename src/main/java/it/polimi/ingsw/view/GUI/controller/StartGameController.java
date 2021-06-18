package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class manages the first scene of the game in which the player has to choose
 * if he wants to play in OFFLINE or ONLINE mode
 */
public class StartGameController {
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    private Button onlineServerButton;

    @FXML
    private Button offlineButton;

    /**
     * When the scene is set, this method is used to  it
     */
    public void start(){
        onlineServerButton.setVisible(true);
        offlineButton.setVisible(true);
    }

    /**
     * If click on the OnlineServerButton set the GUI to ONLINE,
     * prepare the IntroScene and change the stage to it
     */
    public void clickOnlineServerButton() {

        gui.setOffline(false);
        gui.changeScene(gui.getIntroScene());
        gui.getIntroSceneController().start();

    }

    /**
     * If click on the OnlineServerButton set the GUI to OFFLINE,
     * prepare the IntroScene and change the stage to it
     */
    public void clickOfflineButton(){

        gui.setOffline(true);
        gui.changeScene(gui.getIntroScene());
        gui.getIntroSceneController().start();
    }

    /**
     * To set the ONLINE button disable in case of server unable
     */
    public void serverUnavailable() {
        onlineServerButton.setDisable(true);
    }
}
