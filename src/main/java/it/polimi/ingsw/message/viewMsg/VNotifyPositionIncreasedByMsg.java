package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * INITIALIZED CONTROLLER ---> VV ---> CLI
 *
 * msg to all users that a player (usernameIncreased) increased position in faith track
 */
public class VNotifyPositionIncreasedByMsg extends ViewGameMsg{
    private final String usernameIncreased;
    private int updateVictoryPoints;
    private int numberOfPositionIncreased;
    private List<String> allPlayerToNotify;


    public VNotifyPositionIncreasedByMsg(String msgContent, String usernameIncreased,int updateVictoryPoints, int numberOfPositionIncreased) {
        super(msgContent);
        this.usernameIncreased = usernameIncreased;
        this.updateVictoryPoints = updateVictoryPoints;
        this.numberOfPositionIncreased = numberOfPositionIncreased;
        this.allPlayerToNotify = new ArrayList<>();
    }

    public String getUsernameIncreased() {
        return usernameIncreased;
    }

    public int getUpdateVictoryPoints() {
        return updateVictoryPoints;
    }

    public int getNumberOfPositionIncreased() {
        return numberOfPositionIncreased;
    }

    public List<String> getAllPlayerToNotify() {
        return allPlayerToNotify;
    }

    public void setAllPlayerToNotify(List<String> allPlayerToNotify) {
        this.allPlayerToNotify = allPlayerToNotify;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
