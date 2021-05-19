package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;

/**
 * update after a change in strongbox to the client (CLI or GUI)
 */
public class VUpdateStrongboxMsg extends ViewGameMsg {
    private String username;
    private StrongBox strongBox;

    public VUpdateStrongboxMsg(String msgContent, String username, StrongBox strongBox) {
        super(msgContent);
        this.username = username;
        this.strongBox = strongBox;
    }

    public String getUsername() {
        return username;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
