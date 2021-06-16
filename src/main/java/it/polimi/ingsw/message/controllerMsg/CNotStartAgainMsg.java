package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI/GUI --> VV
 * msg sent from the client if he doesn't want to start another game after finishing the previous one
 */
public class CNotStartAgainMsg extends ControllerGameMsg{
    public CNotStartAgainMsg(String msgContent) {
        super(msgContent);
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
