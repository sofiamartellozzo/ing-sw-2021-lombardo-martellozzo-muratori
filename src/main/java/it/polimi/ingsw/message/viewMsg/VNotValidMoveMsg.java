package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;

/**
 * Action Controller --> VV --> CLI/GUI
 * msg used when a client in Move Resources chooses two depots that can't be moved
 * (for example if to depot has more resources than from depot)
 */
public class VNotValidMoveMsg extends GameMsg {

    private String username;

    public VNotValidMoveMsg(String msgContent,String username) {
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
