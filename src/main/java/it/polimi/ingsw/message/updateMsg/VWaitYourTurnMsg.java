package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

/**
 *
 * msg send from Server to client to notify him that is not his turn
 * but another player is actually playing
 */
public class VWaitYourTurnMsg extends ViewGameMsg {
    private String username;

    public VWaitYourTurnMsg(String msgContent, String username) {
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
