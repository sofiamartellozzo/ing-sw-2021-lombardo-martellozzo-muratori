package it.polimi.ingsw.message.controllerMsg;

/**
 * msg from client to discard a resource from the market
 */
public class CChooseDiscardResourceMsg extends ControllerGameMsg{

    private final String username;

    public CChooseDiscardResourceMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
