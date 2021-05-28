package it.polimi.ingsw.message;

import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
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
    public void receiveMsg(CChooseResourceAndDepotMsg msg);     //also in Action Controller
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);   //this also to ActionController

    public void receiveMsg(CGameCanStartMsg msg);

    /* from CLI to TurnController*/
    public void receiveMsg(CChooseActionTurnResponseMsg msg);

    /* from CLI to Action Controller, to buy a Develop Card*/
    public void receiveMsg(CBuyDevelopCardResponseMsg msg);

    /*from CLI to choose another action turn*/
    public void receiveMsg(CChangeActionTurnMsg msg);

    /*from client (CLI) to the ActionController */
    public void receiveMsg(CMoveResourceInfoMsg msg);

    /*from client (CLI) to the ActionController, to buy from the market */
    public void receiveMsg(CBuyFromMarketInfoMsg msg);
    /*if the player discard a resources from the market*/
    public void receiveMsg(CChooseDiscardResourceMsg msg);

    /*from client (CLI) to the PPController, to activate it*/
    public void receiveMsg(CActivateProductionPowerResponseMsg msg);
    public void receiveMsg(CStopPPMsg msg);

    /*another Action that a player can make in the game (see the info of himself or someone else)*/
    public void receiveMsg(CAskSeeSomeoneElseMsg msg);

    /*from Client Handler to controller*/
    public void receiveMsg(CClientDisconnectedMsg msg);
}
