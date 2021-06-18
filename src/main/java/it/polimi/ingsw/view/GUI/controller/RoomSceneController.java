package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * After login, if the player is the first in his room, he has to choose with how many players wants to play,
 * if he choose in MultiPlayerMode.
 */
public class RoomSceneController {
    private GUI gui;

    @FXML
    private ImageView twoPlayers;

    @FXML
    private ImageView threePlayers;

    @FXML
    private ImageView fourPlayers;

    private int choice;

    private String idRoom;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setIdRoom(String idRoom){this.idRoom=idRoom;}

    /**
     * To prepare the scene, after the setting
     */
    public void start(){
        twoPlayers.setVisible(false);
        threePlayers.setVisible(false);
        fourPlayers.setVisible(false);

        display();
    }

    /**
     * To display the choices with some transition
     */
    public void display(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(twoPlayers);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setDuration(Duration.millis(500));
        fadeTransition1.setNode(threePlayers);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);

        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setDuration(Duration.millis(500));
        fadeTransition2.setNode(fourPlayers);
        fadeTransition2.setFromValue(0);
        fadeTransition2.setToValue(1);

        twoPlayers.setVisible(true);
        threePlayers.setVisible(true);
        fourPlayers.setVisible(true);

        fadeTransition.play();
        fadeTransition1.play();
        fadeTransition2.play();
    }

    /**
     * When the player click to "Two Players" Image
     */
    public void twoPlayersClick(){
        choice=2;
        turnDown();
    }

    /**
     * When the player click to "Three Players" Image
     */
    public void threePlayersClick(){
        choice=3;
        turnDown();
    }
    /**
     * When the player click to "Four Players" Image
     */
    public void fourPlayersClick(){
        choice=4;
        turnDown();

    }

    /**
     * When the player entered with the mouse to "Two Players" Image
     */
    public void twoPlayersMouseEntered(){
        twoPlayers.setEffect(new Glow());
    }

    /**
     * When the player entered with the mouse to "Three Players" Image
     */
    public void threePlayersMouseEntered(){
        threePlayers.setEffect(new Glow());
    }

    /**
     * When the player entered with the mouse to "Four Players" Image
     */
    public void fourPlayersMouseEntered(){
        fourPlayers.setEffect(new Glow());
    }

    /**
     * When the player exited with the mouse from "Two Players" Image
     */
    public void twoPlayersMouseExited(){
        twoPlayers.setEffect(null);
    }

    /**
     * When the player exited with the mouse from "Three Players" Image
     */
    public void threePlayersMouseExited(){
        threePlayers.setEffect(null);
    }

    /**
     * When the player exited with the mouse from "Four Players" Image
     */
    public void fourPlayersMouseExited(){
        fourPlayers.setEffect(null);
    }

    /**
     * To close the scene and send the response to the Server/Message Handler
     */
    public void turnDown(){
        gui.roomSizeResponse(choice,idRoom);
    }
}
