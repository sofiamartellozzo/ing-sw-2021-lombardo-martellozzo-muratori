package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.util.ArrayList;

public class VRoomInfoMsg extends ViewGameMsg{
    private ArrayList<String> playersId;
    private String roomId;
    private int numberOfPlayers;
    private int size;

    public VRoomInfoMsg(String msgContent, ArrayList<String> playersId, String roomId, int numberOfPlayers, int size) {
        super(msgContent);
        this.playersId = playersId;
        this.roomId = roomId;
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<String> getPlayersId() {
        return playersId;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getSize() { return size; }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
