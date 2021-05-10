package it.polimi.ingsw.message.controllerMsg;

/**
 * msg from client to discard a resource from the market
 */
public class CChooseDiscardResponseMsg extends ControllerGameMsg{

    private final String username;
    private boolean discarded;

    public CChooseDiscardResponseMsg(String msgContent, String username, boolean discarded) {
        super(msgContent);
        this.username = username;
        this.discarded=discarded;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDiscarded() {
        return discarded;
    }
}
