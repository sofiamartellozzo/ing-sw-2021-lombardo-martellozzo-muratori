package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

/**
 *
 * from server to View to notify a change in the amount of victory points:
 *          by get a development card (that update in the update of the Table)
 *          activate a Leader Card
 *          moving the faith marker in the faith track(in msg of notify increased position)
 */
public class VUpdateVictoryPointsMsg extends ViewGameMsg {
    private String username;
    private int updateVictoryPoints;

    public VUpdateVictoryPointsMsg(String msgContent, String username, int updateVictoryPoints) {
        super(msgContent);
        this.username = username;
        this.updateVictoryPoints = updateVictoryPoints;
    }

    public String getUsername() {
        return username;
    }

    public int getUpdateVictoryPoints() {
        return updateVictoryPoints;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
