package it.polimi.ingsw.message.viewMsg;

public class VActivateProductionPowerRequestMsg extends ViewGameMsg{
    private String username;

    public VActivateProductionPowerRequestMsg(String msgContent, String username) {
        super(msgContent);
        this.username=username;
    }

    public String getUsername(){return username;}
}
