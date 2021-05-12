package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardInterface;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;

/**
 * Lobby (Room) --> VV --> CLI
 * msg sent from the room to give to every player the information about his GameSpace and the array of leader cards
 */
public class VSendPlayerDataMsg extends ViewGameMsg{

    private PlayerInterface player;
    private BoardManager boardManager;
    private boolean soloMode;

    public VSendPlayerDataMsg(String msgContent, PlayerInterface player, BoardManager boardManager, boolean soloMode) {
        super(msgContent);
        this.player = player;
        this.boardManager = boardManager;
        this.soloMode = soloMode;
    }

    public PlayerInterface getPlayer() {
        return player;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public boolean isSoloMode() {
        return soloMode;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
