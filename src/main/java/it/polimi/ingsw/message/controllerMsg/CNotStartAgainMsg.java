package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

public class CNotStartAgainMsg extends ControllerGameMsg{
    public CNotStartAgainMsg(String msgContent) {
        super(msgContent);
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
