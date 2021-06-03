package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;

/**
 * this msg is created when a client disconnected
 * and the server need to handle this situation
 */

public class CClientDisconnectedMsg extends GameMsg {
    private String username;
    public CClientDisconnectedMsg(String msgContent, String username) {
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

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
