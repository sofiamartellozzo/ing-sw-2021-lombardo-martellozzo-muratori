package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.PlayerInterface;

/**
 * TurnController ---> VV ---> CLI / GUI
 */
public class VAnotherPlayerInfoMsg extends ViewGameMsg{
    private PlayerInterface player;
    private BoardManager boardManager;
    private String usernameAsking;

    public VAnotherPlayerInfoMsg(String msgContent, PlayerInterface player, BoardManager boardManager, String usernameAsking) {
        super(msgContent);
        this.player = player;
        this.boardManager = boardManager;
        this.usernameAsking = usernameAsking;
    }

    public PlayerInterface getPlayer() {
        return player;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public String getUsernameAsking() {
        return usernameAsking;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
