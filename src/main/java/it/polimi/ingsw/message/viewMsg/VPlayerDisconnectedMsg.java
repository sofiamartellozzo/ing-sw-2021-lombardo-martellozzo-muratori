package it.polimi.ingsw.message.viewMsg;

public class VPlayerDisconnectedMsg extends ViewGameMsg {

    private String disconnectedMotivation;

    public VPlayerDisconnectedMsg(String msgContent, String disconnectionMotivation) {
        super(msgContent);
        this.disconnectedMotivation = disconnectionMotivation;
    }

    public String getDisconnectedMotivation() {
        return disconnectedMotivation;
    }
}
