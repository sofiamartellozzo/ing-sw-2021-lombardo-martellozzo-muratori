package it.polimi.ingsw.message.viewMsg;

public class VChooseResourceRequestMsg extends ViewGameMsg{
    private String username;

    public VChooseResourceRequestMsg(String msgContent, String username){
        super(msgContent);
        this.username=username;
    }
}
