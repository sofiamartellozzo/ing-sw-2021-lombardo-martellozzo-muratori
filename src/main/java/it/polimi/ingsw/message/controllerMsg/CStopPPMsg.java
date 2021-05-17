package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

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
