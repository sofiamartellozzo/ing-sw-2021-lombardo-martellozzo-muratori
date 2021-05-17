package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

/**
 * msg from client main (Socket) if the server is unreachable
 * (ex. launch client before server)
 */
public class VServerUnableMsg extends ViewGameMsg {

    public VServerUnableMsg(String msgContent) {
        super(msgContent);
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
