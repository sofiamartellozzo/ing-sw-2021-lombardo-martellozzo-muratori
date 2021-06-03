package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 *  VV ---> Lobby ---> Room ---> Turn Controller
 *
 *  msg to the controller after a reconnection to notify to restart
 *  or go to the next turn
 */
public class CResumeGameMsg extends ControllerGameMsg{
    private String username;

    public CResumeGameMsg(String msgContent, String username) {
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
