package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.controller.Room;
import it.polimi.ingsw.message.viewMsg.VRoomInfoMsg;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * this class handles all the Room's updates from the server and manages the players of the game
 */

public class LobbySceneController {
    private GUI gui;

    @FXML
    private Label firstPlayer, secondPlayer, thirdPlayer, fourthPlayer;
    @FXML
    private Label idRoom;
    @FXML
    private Label numberOfPlayers;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void updateLobby(VRoomInfoMsg msg){
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

    private ArrayList<Label> getPlayers(){
        ArrayList<Label> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(thirdPlayer);
        players.add(fourthPlayer);
        return players;
    }

}
