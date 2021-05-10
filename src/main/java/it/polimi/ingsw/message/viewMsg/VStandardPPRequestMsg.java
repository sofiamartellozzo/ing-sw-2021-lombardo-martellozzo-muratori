package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

public class VStandardPPRequestMsg extends ViewGameMsg{
    private String username;
    private String where;

    public VStandardPPRequestMsg(String msgContent, String username, String where){
        super(msgContent);
        this.username=username;
        this.where = where;
    }

    public String getUsername() {
        return username;
    }

    public String getWhere() {
        return where;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
