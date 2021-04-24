package it.polimi.ingsw.message.viewMsg;

/**
 * msg to all users that a player (usernameIncreased) increased position in faith track
 */
public class VNotifyPositionIncreasedByMsg extends ViewGameMsg{
    private String usernameIncreased;
    private int numberOfPositionIncreased;

    public VNotifyPositionIncreasedByMsg(String msgContent, String usernameIncreased, int numberOfPositionIncreased) {
        super(msgContent);
        this.usernameIncreased = usernameIncreased;
        this.numberOfPositionIncreased = numberOfPositionIncreased;
    }

    public String getUsernameIncreased() {
        return usernameIncreased;
    }

    public int getNumberOfPositionIncreased() {
        return numberOfPositionIncreased;
    }
}
