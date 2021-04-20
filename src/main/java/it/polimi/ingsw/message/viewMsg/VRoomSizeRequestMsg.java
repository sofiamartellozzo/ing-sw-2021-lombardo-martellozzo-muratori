package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;

public class VRoomSizeRequestMsg extends ViewGameMsg {
    private String IP;
    private int Port;
    private String username;
    private int actualNumberOfPlayersInRoom;

    public VRoomSizeRequestMsg(String content,String IP, int port, String username, int actualNumberOfPlayersInRoom) {
        super(content);
        this.IP = IP;
        Port = port;
        this.username = username;
        this.actualNumberOfPlayersInRoom = actualNumberOfPlayersInRoom;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return Port;
    }

    public String getUsername() {
        return username;
    }

    public int getActualNumberOfPlayersInRoom() {
        return actualNumberOfPlayersInRoom;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
