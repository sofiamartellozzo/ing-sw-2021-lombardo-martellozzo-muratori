package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * Turn Controller ---> VV ---> Lobby [CLOSE ROOM]
 *
 * msg send buy turn controller to the VV and then comunicate
 * the msg to the lobby and close the room
 */
public class CCloseRoomMsg extends ControllerGameMsg{
    private String username;

    public CCloseRoomMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
