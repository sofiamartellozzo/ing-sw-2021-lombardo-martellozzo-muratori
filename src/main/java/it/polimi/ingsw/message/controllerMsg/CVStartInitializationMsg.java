package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * Lobby <--- VV ---> CLI
 *
 * msg form VV to notify the Lobby that a game is starting so it can set
 * the boolean (canCreateRoom) to true and say the player the information
 */
public class CVStartInitializationMsg extends GameMsg {
    private String playerInvolved;

    public CVStartInitializationMsg(String msgContent, String player) {
        super(msgContent);
        this.playerInvolved = player;
    }

    public String getPlayers() {
        return playerInvolved;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
