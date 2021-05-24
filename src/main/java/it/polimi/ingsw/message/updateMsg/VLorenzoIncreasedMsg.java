package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.SoloPlayer;

/**
 * Turn Controller ---> VV ---> CLI/ GUI
 *
 * msg from controller to notify the client that Lorenzo has increased his position
 * in FT, so make it visible
 */
public class VLorenzoIncreasedMsg extends ViewGameMsg {
    private String username;
    private SoloPlayer player;
    private int numberStep;

    public VLorenzoIncreasedMsg(String msgContent, String username, SoloPlayer player, int numberStep) {
        super(msgContent);
        this.username = username;
        this.player = player;
        this.numberStep = numberStep;
    }

    public String getUsername() {
        return username;
    }

    public SoloPlayer getPlayer() {
        return player;
    }

    public int getNumberStep() {
        return numberStep;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
