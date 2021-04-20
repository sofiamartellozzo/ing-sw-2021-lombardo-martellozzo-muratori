package it.polimi.ingsw.message;

import it.polimi.ingsw.message.controllerMsg.CNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.message.viewMsg.VChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.viewMsg.VNotifyAllIncreasePositionMsg;
import it.polimi.ingsw.message.viewMsg.VRoomSizeRequestMsg;

/**
 * OBSERVER
 * interface with the method of the view
 */
public interface ViewObserver extends Observer{

    public void receiveMsg(CNackConnectionRequestMsg msg);
    public void receiveMsg(VRoomSizeRequestMsg msg);
    public void receiveMsg(VChooseLeaderCardRequestMsg msg);
    public void receiveMsg(VChooseResourceAndDepotMsg msg);
    public void receiveMsg(VNotifyAllIncreasePositionMsg msg);
}
