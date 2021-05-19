package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardInterface;
import it.polimi.ingsw.model.card.LeaderCard;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Lobby <--- VV ---> CLI
 * <p>
 * msg form VV to notify the Lobby that a game is starting so it can set
 * the boolean (canCreateRoom) to true and say the player the information
 */
public class CVStartInitializationMsg extends GameMsg {
    private final String playerInvolved;


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
