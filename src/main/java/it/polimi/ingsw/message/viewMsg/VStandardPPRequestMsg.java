package it.polimi.ingsw.message.viewMsg;

public class VStandardPPRequestMsg extends ViewGameMsg{
    private String username;

    public VStandardPPRequestMsg(String msgContent, String username){
        super(msgContent);
        this.username=username;
    }
}
