package it.polimi.ingsw.message;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;

/**
 * OBSERVER
 * interface with the method of the controllers
 * not all yet
 *
 */
public interface ControllerObserver extends Observer{

    /* received from VV, the first msg*/
    public void receiveMsg(VVConnectionRequestMsg msg) throws InvalidActionException;

    /* form VV to Lobby*/
    public void receiveMsg(CConnectionRequestMsg msg) throws InvalidActionException;

    /* from cli, then VV and finally to Lobby---> set room size*/
    public void receiveMsg(CRoomSizeResponseMsg msg) throws InvalidActionException;

    /* notification by VV of a starting initialization*/
    public void receiveMsg(CVStartInitializationMsg msg);

    /* from CLI to InitializedC*/
    public void receiveMsg(CChooseResourceAndDepotMsg msg) throws InvalidActionException;
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);



    public void receiveMsg(CChooseActionTurnResponseMsg msg) throws InvalidActionException;
    public void receiveMsg(CBuyDevelopCardResponseMsg msg);
    public void receiveMsg(CMoveResourceInfoMsg msg);
    public void receiveMsg(CBuyFromMarketInfoMsg msg);
    public void receiveMsg(CActivateProductionPowerResponseMsg msg);
    public void receiveMsg(CChooseDiscardResourceMsg msg);
    public void receiveMsg(CChooseResourceResponseMsg msg);
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg);



}
