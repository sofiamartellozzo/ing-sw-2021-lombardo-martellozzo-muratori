package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ViewObserver;

public class CNackConnectionRequestMsg extends ControllerGameMsg{

    private final String username;

    public CNackConnectionRequestMsg(String content, String username) {
        super(content);
        this.username = username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
