package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

/**
 * Room ---> VV ---> ClientHandler
 *
 * at least one client reconnected to the game, so stop the timeout
 * to not close the ROOM
 */
public class VStopWaitReconnectionMsg extends ViewGameMsg{
    public VStopWaitReconnectionMsg(String msgContent) {
        super(msgContent);
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
