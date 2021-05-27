package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;

/**
 * this msg is created when a client disconnected
 * and the server need to handle this situation
 */

public class CClientDisconnectedMsg extends ControllerGameMsg {
    private String username;
    public CClientDisconnectedMsg(String msgContent) {
        super(msgContent);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
