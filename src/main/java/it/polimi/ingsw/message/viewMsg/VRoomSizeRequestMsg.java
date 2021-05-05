package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;

/**
 *
 * Lobby ----> VV ----> CLI
 * msg send by the controller (Lobby) to the client for ask the size of the room
 */
public class VRoomSizeRequestMsg extends ViewGameMsg {
    private final String username;
    private String roomID;

    public VRoomSizeRequestMsg(String content, String username, String roomID) {
        super(content);
        this.username = username;
        this.roomID = roomID;
    }

    public String getUsername() {
        return username;
    }

    public String getRoomID() {
        return roomID;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
