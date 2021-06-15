package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;

/**
 * this msg is created when a client disconnected
 * and the server need to handle this situation
 */

public class CClientDisconnectedMsg extends GameMsg {
    private String usernameDisconnected;
    public CClientDisconnectedMsg(String msgContent, String usernameDisconnected) {
        super(msgContent);
        this.usernameDisconnected = usernameDisconnected;
    }

    public String getUsernameDisconnected() {
        return usernameDisconnected;
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
