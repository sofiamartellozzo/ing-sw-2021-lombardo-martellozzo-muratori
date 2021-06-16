package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI (Market CLI)/GUI --> VV --> Action Controller --> PPController
 * msg sent from CLI to PPController to notify it that the client has stopped using the production powers
 */
public class CStopPPMsg extends ControllerGameMsg{
    private String username;

    public CStopPPMsg(String msgContent, String username){
        super(msgContent);
        this.username=username;
    }

    public String getUsername(){ return username;}

    @Override
    public void notifyHandler(ControllerObserver controllerObserver){controllerObserver.receiveMsg(this);}
}
