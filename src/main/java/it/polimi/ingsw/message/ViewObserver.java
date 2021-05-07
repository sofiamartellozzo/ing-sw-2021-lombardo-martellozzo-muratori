package it.polimi.ingsw.message;

import it.polimi.ingsw.message.controllerMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.VNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.*;

/**
 * OBSERVER
 * interface with the method of the view
 * not all yet
 */
public interface ViewObserver extends Observer{

    /* first msg, only multiple players*/
    public void receiveMsg(VRoomSizeRequestMsg msg);

    /* multiple player, from initialized C...*/
    public void receiveMsg(VChooseResourceAndDepotMsg msg);
    /* if error in chose depot*/
    public void receiveMsg(VNotValidDepotMsg msg);

    /* send to all player to notify this action of one*/
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg);

    /* send to the player after try to initialized the game*/
    public void receiveMsg(VNackConnectionRequestMsg msg);

    /* from InitC to each player to select the 2 L_Card*/
    public void receiveMsg(VChooseLeaderCardRequestMsg msg);


    public void receiveMsg(VChooseActionTurnRequestMsg msg);

    public void receiveMsg(VChooseDevelopCardRequestMsg msg);
    public void receiveMsg(VMoveResourceRequestMsg msg);
    public void receiveMsg(VBuyFromMarketRequestMsg msg);
    public void receiveMsg(VShowEndGameResultsMsg msg);

    /* only SOLO mode */
    public void receiveMsg(VActionTokenActivateMsg msg);

    void receiveMsg(CVStartInitializationMsg msg);
}
