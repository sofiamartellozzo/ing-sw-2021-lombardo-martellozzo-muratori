package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.GameMsg;

/**
 * msg send by/to the controller
 */
public abstract class ControllerGameMsg extends GameMsg {
    public ControllerGameMsg(String msgContent) {
        super(msgContent);
    }
}
