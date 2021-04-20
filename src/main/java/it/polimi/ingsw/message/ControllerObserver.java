package it.polimi.ingsw.message;

import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.controllerMsg.CConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.VConnectionRequestMsg;

/**
 * OBSERVER
 * interface with the method of the controllers
 */
public interface ControllerObserver extends Observer{

    public void receiveMsg(CConnectionRequestMsg msg);
    public void receiveMsg(VConnectionRequestMsg msg);
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);
    public void receiveMsg(CChooseResourceAndDepotMsg msg);
}
