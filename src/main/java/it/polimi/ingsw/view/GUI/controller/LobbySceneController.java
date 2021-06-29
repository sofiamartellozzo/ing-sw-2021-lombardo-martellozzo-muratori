package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.controller.Room;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.viewMsg.VRoomInfoMsg;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;

/**
 * When the player access and, in case has chosen the size of the room too, is redirect to this scene,
 * where he has to wait other players connect, the scene is updated every time a player is insert in the room.
 */

public class LobbySceneController {
    private GUI gui;

    @FXML
    private Label firstPlayer, secondPlayer, thirdPlayer, fourthPlayer;
    @FXML
    private Label idRoom;
    @FXML
    private Label numberOfPlayers;
    @FXML
    private TitledPane errorPane;
    @FXML
    private Button okButton;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * To update the info of the Room
     * @param msg VRoomInfoMsg
     */
    public void updateLobby(VRoomInfoMsg msg){
        errorPane.setVisible(false);
        ArrayList<Label> players = getPlayers();
        ArrayList<String> idPlayers = msg.getPlayersId();
        idRoom.setText(msg.getRoomId());
        numberOfPlayers.setText(msg.getNumberOfPlayers()+"/"+msg.getSize());
        for(int i=0;i<4;i++){
            if(i+1<=msg.getNumberOfPlayers()){
                players.get(i).setVisible(true);
                players.get(i).setText(idPlayers.get(i));
            }else{
                players.get(i).setVisible(false);
            }
        }
    }

    /**
     * Getter Method of the Players Label View
     * @return an ordered arraylist of the players' label view
     */
    private ArrayList<Label> getPlayers(){
        ArrayList<Label> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(thirdPlayer);
        players.add(fourthPlayer);
        return players;
    }

    public void clickOkButton(){
        if(!okButton.isDisable()){
            gui.close();
        }
    }

    public void serverUnable(VServerUnableMsg msg){
        errorPane.setVisible(true);
        okButton.setDisable(false);
    }
}
