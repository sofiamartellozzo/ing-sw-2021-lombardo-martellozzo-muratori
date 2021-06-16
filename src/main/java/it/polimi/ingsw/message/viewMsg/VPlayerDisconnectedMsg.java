package it.polimi.ingsw.message.viewMsg;

/**
 * Action Controller --> VV --> CLI/GUI
 * msg sent is a client disconnects from the game and it contains the motivation of his disconnection
 */
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
