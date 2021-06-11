package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI/GUI ---> VV
 *
 * msg sent if the client, after the game ended
 * decided to start a new game
 */
public class CNewStartMsg extends ControllerGameMsg{
    private String username;

    public CNewStartMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
