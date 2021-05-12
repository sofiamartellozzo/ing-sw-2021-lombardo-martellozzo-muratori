package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

public class VStandardPPRequestMsg extends ViewGameMsg{
    private String username;

    public VStandardPPRequestMsg(String msgContent, String username){
        super(msgContent);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
