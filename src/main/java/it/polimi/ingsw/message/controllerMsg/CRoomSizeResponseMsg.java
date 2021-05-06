package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI ---> VV ----> Lobby
 *
 * msg by the client as a response for a request of the room size
 * the Lobby will set the Romm size request
 */
public class CRoomSizeResponseMsg extends ControllerGameMsg{

    private int roomSize;
    private final String username;
    private String roomID;

    public CRoomSizeResponseMsg(String msgContent,int roomSize, String username, String roomID) {
        super(msgContent);
        this.roomSize = roomSize;
        this.username = username;
        this.roomID = roomID;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public String getUsername() {
        return username;
    }

    public String getRoomID() {
        return roomID;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
