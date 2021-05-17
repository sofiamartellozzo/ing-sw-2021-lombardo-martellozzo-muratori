package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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

    public void start(){
        twoPlayers.setVisible(false);
        threePlayers.setVisible(false);
        fourPlayers.setVisible(false);

        display();
    }

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

    public void twoPlayersClick(){
        choice=2;
        turnDown();
    }

    public void threePlayersClick(){
        choice=3;
        turnDown();
    }

    public void fourPlayersClick(){
        choice=4;
        turnDown();

    }

    public void twoPlayersMouseEntered(){
        twoPlayers.setEffect(new Glow());
    }

    public void threePlayersMouseEntered(){
        threePlayers.setEffect(new Glow());
    }

    public void fourPlayersMouseEntered(){
        fourPlayers.setEffect(new Glow());
    }

    public void twoPlayersMouseExited(){
        twoPlayers.setEffect(null);
    }

    public void threePlayersMouseExited(){
        threePlayers.setEffect(null);
    }

    public void fourPlayersMouseExited(){
        fourPlayers.setEffect(null);
    }

    public void turnDown(){
        gui.roomSizeResponse(choice,idRoom);
    }
}
