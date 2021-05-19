package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

/**
 * Initialized Controller ---> VV ---> CLI GUI
 *
 * at initialization of the game to notify the player to wait the choices of the others
 */
public class VWaitOtherPlayerInitMsg extends ViewGameMsg {
    private String username;

    public VWaitOtherPlayerInitMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
