package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.board.FaithTrack;

/**
 * after something in the faithTrack changes (for examples positions or Popes Favor Tiles)
 */
public class VUpdateFaithTrackMsg extends ViewGameMsg {

    private String username;
    private FaithTrack faithTrack;

    public VUpdateFaithTrackMsg(String msgContent, String username, FaithTrack faithTrack) {
        super(msgContent);
        this.faithTrack = faithTrack;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
