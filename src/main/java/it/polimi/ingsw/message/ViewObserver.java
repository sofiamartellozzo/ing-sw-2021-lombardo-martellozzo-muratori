package it.polimi.ingsw.message;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.controllerMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.VNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.*;

import java.io.IOException;

/**
 * OBSERVER
 * interface with the method of the view
 * not all yet
 */
public interface ViewObserver extends Observer{

    /* first msg, only multiple players*/
    public void receiveMsg(VRoomSizeRequestMsg msg);

    /* from Room after initialization, containing Player's data*/
    public void receiveMsg(VSendPlayerDataMsg msg);

    /* from Room containing the info about the Room*/
    void receiveMsg(VRoomInfoMsg msg);

    /* multiple player, from initialized C...*/
    public void receiveMsg(VChooseResourceAndDepotMsg msg);
    /* if not error in chose depot */
    public void receiveMsg(VUpdateWarehouseMsg msg);
    /* if error in chose depot*/
    public void receiveMsg(VNotValidDepotMsg msg);

    /* send to all player to notify this action of one*/
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg);

    /* send to the player after try to initialized the game*/
    public void receiveMsg(VNackConnectionRequestMsg msg);

    /* from InitC to each player to select the 2 L_Card*/
    public void receiveMsg(VChooseLeaderCardRequestMsg msg);

    /* from Turn Controller to the client */
    public void receiveMsg(VChooseActionTurnRequestMsg msg);

    /* fist (only in code order) of action chosen in ActionController, so send this msg to client for asking which card to buy */
    public void receiveMsg(VChooseDevelopCardRequestMsg msg);

    /* from ActionC to the client */
    public void receiveMsg(VMoveResourceRequestMsg msg);

    /* from ActionC to the client, so send to the client asking which choice in market */
    public void receiveMsg(VBuyFromMarketRequestMsg msg);

    /* from ActionC to the client, so send to the client asking which depot to store a resource */
    public void receiveMsg(VChooseDepotMsg msg);

    /*from PPController to client*/
    public void receiveMsg(VActivateProductionPowerRequestMsg msg);



    public void receiveMsg(VShowEndGameResultsMsg msg);

    /* only SOLO mode, after the player end the turn (is Lorenzo playing) */
    public void receiveMsg(VActionTokenActivateMsg msg);

    void receiveMsg(CVStartInitializationMsg msg) throws IOException;

}
