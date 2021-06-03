package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;

import javax.naming.LimitExceededException;

/**
 * this class rapresent the Lobby in offline Mode
 * so do the same stuff as the original but not all the check in
 * syncronizations the clients
 */
public class OfflineLobby extends Observable implements ControllerObserver {
    private Room offlineRoom;
    private String offlinePlayer;

    public OfflineLobby(String offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public Room getOfflineRoom() {
        return offlineRoom;
    }

    public String getOfflinePlayer() {
        return offlinePlayer;
    }

    public void createRoom() {
        offlineRoom = new Room("#off1");
        offlineRoom.setSoloMode(true);
        offlineRoom.setSIZE(1);
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //in VV
    }

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        createRoom();
        try {
            offlineRoom.addUser(offlinePlayer, msg.getVV());
        } catch (LimitExceededException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {

    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //offline only solo, so the client don't chose the Room SIZE
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        try {
            offlineRoom.initializedGame();
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        try {

            offlineRoom.startFirstTurn();
            offlineRoom.detachInitializedC();

        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CStopPPMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        offlineRoom.notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {

    }



    @Override
    public void receiveMsg(CCloseRoomMsg msg) {

    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {

    }

    private void printOffLobbyMessage(String message) {
        System.out.println(message);
    }

}
