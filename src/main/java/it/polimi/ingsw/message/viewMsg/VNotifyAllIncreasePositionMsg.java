package it.polimi.ingsw.message.viewMsg;

/**
 * msg send to all players when this username increase his position
 */
public class VNotifyAllIncreasePositionMsg extends ViewGameMsg{

    private String username;
    private int newPosition;

    public VNotifyAllIncreasePositionMsg(String msgContent, String username, int newPosition) {
        super(msgContent);
        this.username = username;
        this.newPosition = newPosition;
    }

    public String getUsername() {
        return username;
    }

    public int getNewPosition() {
        return newPosition;
    }
}
