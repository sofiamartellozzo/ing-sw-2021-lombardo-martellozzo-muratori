package it.polimi.ingsw.message;

import it.polimi.ingsw.message.controllerMsg.CNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.*;

/**
 * OBSERVER
 * interface with the method of the view
 * not all yet
 */
public interface ViewObserver extends Observer{

    public void receiveMsg(CNackConnectionRequestMsg msg);
    public void receiveMsg(VRoomSizeRequestMsg msg);
    public void receiveMsg(VChooseLeaderCardRequestMsg msg);
    public void receiveMsg(VChooseResourceAndDepotMsg msg);
    public void receiveMsg(VNotifyAllIncreasePositionMsg msg);
    public void receiveMsg(VNotValidDepotMsg msg);
    public void receiveMsg(VChooseDevelopCardRequestMsg msg);
    public void receiveMsg(VMoveResourceRequestMsg msg);
    public void receiveMsg(VBuyFromMarketRequestMsg msg);
    public void receiveMsg(VShowEndGameResultsMsg msg);
}
