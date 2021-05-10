package it.polimi.ingsw.message.viewMsg;

public class VChooseDiscardRequestMsg extends ViewGameMsg{
    private String username;

    public VChooseDiscardRequestMsg(String content, String username){
        super(content);
        this.username=username;
    }
}
