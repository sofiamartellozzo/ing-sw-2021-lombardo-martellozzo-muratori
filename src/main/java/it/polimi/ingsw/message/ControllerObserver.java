package it.polimi.ingsw.message;

import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;

/**
 * OBSERVER
 * interface with the methods of the controllers
 */
public interface ControllerObserver extends Observer{

    /* received from VV, the first msg*/
    public void receiveMsg(VVConnectionRequestMsg msg);

    /* form VV to Lobby*/
    public void receiveMsg(CConnectionRequestMsg msg);

    /*after a reconnection say it to the Turn Controller*/
    public void receiveMsg(CResumeGameMsg msg);

    /* from cli, then VV and finally to Lobby---> set room size*/
    public void receiveMsg(CRoomSizeResponseMsg msg);

    /* notification by VV of a starting initialization*/
    public void receiveMsg(CVStartInitializationMsg msg);

    /* from CLI to InitializedC to choose the resource and the depot*/
    public void receiveMsg(CChooseResourceAndDepotMsg msg);     //also in Action Controller
    /*from client to controller with the answer of the player (the two leader cards that he wants) */
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);   //this also to ActionController

    /*send after initialization of the game*/
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
    /*msg used to stop the buying from the market*/
    public void receiveMsg(CStopMarketMsg msg);

    /*from client (CLI) to the PPController, to activate it*/
    public void receiveMsg(CActivateProductionPowerResponseMsg msg);
    /*send to PPController when the client finished the actions of activate a PP*/
    public void receiveMsg(CStopPPMsg msg);

    /*another Action that a player can make in the game (see the info of himself or someone else)*/
    public void receiveMsg(CAskSeeSomeoneElseMsg msg);

    /*from Client Handler to controller*/
    public void receiveMsg(CClientDisconnectedMsg msg);
    /*msg send if a single player reconnected on time*/
    public void receiveMsg(CCloseRoomMsg msg);

    /*end of a game situation*/
    public void receiveMsg(VShowEndGameResultsMsg msg);
    /*send from the client if he doesn't want to play another game*/
    public void receiveMsg(CNotStartAgainMsg msg);
    /*msg used if the player wants to play another match*/
    public void receiveMsg(CNewStartMsg msg);
}
