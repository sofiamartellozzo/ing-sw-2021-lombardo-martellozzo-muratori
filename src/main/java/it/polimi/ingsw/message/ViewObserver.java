package it.polimi.ingsw.message;

import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.CCloseRoomMsg;
import it.polimi.ingsw.message.controllerMsg.VStartWaitReconnectionMsg;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.viewMsg.VNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.*;

import java.io.IOException;

/**
 * OBSERVER
 * interface with the methods of the view
 */
public interface ViewObserver extends Observer{

    /* first msg, only multiple players*/
    public void receiveMsg(VRoomSizeRequestMsg msg);

    /* from Room after initialization, containing Player's data*/
    public void receiveMsg(VSendPlayerDataMsg msg);

    /*from Room containing the info about the Room*/
    public void receiveMsg(VRoomInfoMsg msg);

    /* multiple player, from initialized C...*/
    public void receiveMsg(VChooseResourceAndDepotMsg msg);
    /* if not error in chose depot */
    public void receiveMsg(VUpdateWarehouseMsg msg);
    /* if error in chose depot*/
    public void receiveMsg(VNotValidDepotMsg msg);

    /* send to all player to notify this action of one*/
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg);

    /* send from controller to client to update the points at each turn*/
    public void receiveMsg(VUpdateVictoryPointsMsg msg);

    /* send to the player after try to initialized the game*/
    public void receiveMsg(VNackConnectionRequestMsg msg);

    /* from InitC to each player to select the 2 L_Card*/
    public void receiveMsg(VChooseLeaderCardRequestMsg msg);

    /*from InitController to the player that has chosen the Leader Card yet, but the others not*/
    public void receiveMsg(VWaitOtherPlayerInitMsg msg);

    /* from Turn Controller to the client */
    public void receiveMsg(VChooseActionTurnRequestMsg msg);
    /*msg to all other players that are not actually playing (because is someone else turn)*/
    public void receiveMsg(VWaitYourTurnMsg msg);

    /* fist (only in code order) of action chosen in ActionController, so send this msg to client for asking which card to buy */
    public void receiveMsg(VChooseDevelopCardRequestMsg msg);
    /*notify the client that the card space chosen is not valid*/
    public void receiveMsg(VNotValidCardSpaceMsg msg);
    /*notify all player the new situation of the DevTable after someone bought a card*/
    public void receiveMsg(VUpdateDevTableMsg msg);

    /* from ActionC to the client */
    public void receiveMsg(VMoveResourceRequestMsg msg);

    /* from ActionC to the client, so send to the client asking which choice in market */
    public void receiveMsg(VBuyFromMarketRequestMsg msg);
    /*msg with the update market after someone bought from it*/
    public void receiveMsg(VUpdateMarketMsg msg);
    /*msg with the update faith track after something in it changed*/
    public void receiveMsg(VUpdateFaithTrackMsg msg);
    /* from ActionC to the client, so send to the client asking which depot to store a resource */
    public void receiveMsg(VChooseDepotMsg msg);
    /* notify the client that lorenzo has increased his position of..*/
    public void receiveMsg(VLorenzoIncreasedMsg msg);

    /*from PPController to client*/
    public void receiveMsg(VActivateProductionPowerRequestMsg msg);

    /*from PPController if the player doesn't have te resources to "pay" the PP*/
    public void receiveMsg(VResourcesNotFoundMsg msg);
    /*from controller to client to update the strongBox at every change*/
    public void receiveMsg(VUpdateStrongboxMsg msg);

    /* only SOLO mode, after the player end the turn (is Lorenzo playing) */
    public void receiveMsg(VActionTokenActivateMsg msg);

    /* from client Socket main to cli gui*/
    public void receiveMsg(VServerUnableMsg msg);
    /*used to start the initialization of the game*/
    public void receiveMsg(CVStartInitializationMsg msg);
    /*used when the initialization is finished */
    public void receiveMsg(CGameCanStartMsg msg);

    /*msg used to ask to see the board of another player*/
    public void receiveMsg(VAnotherPlayerInfoMsg msg);
    /*msg used to choose the name of which other player the client wants to see*/
    public void receiveMsg(VWhichPlayerRequestMsg msg);

    /*notify the View with the result of the game*/
    public void receiveMsg(VShowEndGameResultsMsg msg) throws IOException;
    /*ask the client if wants to play again or not*/
    public void receiveMsg(VAskNewGameMsg msg);
    /*close the old room*/
    public void receiveMsg(CCloseRoomMsg msg);


    /*notification to all players in a room about one disconnected*/
    public void receiveMsg(CClientDisconnectedMsg msg);
    /*ge game stops waiting the client to reconnect*/
    public void receiveMsg(VStartWaitReconnectionMsg msg);
    /*msg if a client reconnect to the room*/
    public void receiveMsg(VStopWaitReconnectionMsg msg);

    /*msg used to update the state of leader cards*/
    public void receiveMsg(VUpdateLeaderCards msg);
    /*msg used to update the situation of the card spaces*/
    public void receiveMsg(VUpdateCardSpaces msg);
    /*msg used if a player can't move two depots*/
    public void receiveMsg(VNotValidMoveMsg msg);
}
