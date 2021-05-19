package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;

public class CGameCanStratMsg extends ControllerGameMsg {

    private String onePlayer;

    public CGameCanStratMsg(String msgContent, String onePlayer) {
        super(msgContent);
        this.onePlayer = onePlayer;
    }

    public String getOnePlayer() {
        return onePlayer;
    }


    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
