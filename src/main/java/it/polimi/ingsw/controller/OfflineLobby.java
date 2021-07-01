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
 * this class represents the Lobby in offline Mode
 * so do the same stuff as the original but not manages all the checks
 * about client's synchronization
 */
public class OfflineLobby extends Observable implements ControllerObserver {
    private Room offlineRoom;
    private String offlinePlayer;

    /**
     * constructor of the class
     * @param offlinePlayer player of the game
     */
    public OfflineLobby(String offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public Room getOfflineRoom() {
        return offlineRoom;
    }

    public String getOfflinePlayer() {
        return offlinePlayer;
    }

    /**
     * creates the room of the game setting the size = 1 because in OFFLINE mode the game can be only in single Player Mode
     */
    public void createRoom() {
        offlineRoom = new Room("#off1");
        offlineRoom.setSoloMode(true);
        offlineRoom.setSIZE(1);
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Virtual View
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
        //NOT HERE
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

    /**
     * starts the first turn for the single player in the offline Mode
     * @param msg
     */
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
        //NOT HERE
    }

    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //NOT HERE
    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CNewStartMsg msg) {
        //NOT HERE, implemented in Virtual View
    }


}
