package it.polimi.ingsw.message;

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
    public void receiveMsg(VVConnectionRequestMsg msg);

    /* form VV to Lobby*/
    public void receiveMsg(CConnectionRequestMsg msg);

    /* from cli, then VV and finally to Lobby---> set room size*/
    public void receiveMsg(CRoomSizeResponseMsg msg);

    /* notification by VV of a starting initialization*/
    public void receiveMsg(CVStartInitializationMsg msg);

    /* from CLI to InitializedC*/
    public void receiveMsg(CChooseResourceAndDepotMsg msg);
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);

    /* from CLI to TurnController*/
    public void receiveMsg(CChooseActionTurnResponseMsg msg);

    /* from CLI to Action Controller, to buy a Develop Card*/
    public void receiveMsg(CBuyDevelopCardResponseMsg msg);


    public void receiveMsg(CMoveResourceInfoMsg msg);
    public void receiveMsg(CBuyFromMarketInfoMsg msg);
    public void receiveMsg(CActivateProductionPowerResponseMsg msg);
    public void receiveMsg(CChooseDiscardResourceMsg msg);
    public void receiveMsg(CChooseResourceResponseMsg msg);
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg);



}
