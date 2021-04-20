package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.GameMsg;

/**
 * msg send to/by the view
 */
public abstract class ViewGameMsg extends GameMsg {
    public ViewGameMsg(String msgContent) {
        super(msgContent);
    }
}
