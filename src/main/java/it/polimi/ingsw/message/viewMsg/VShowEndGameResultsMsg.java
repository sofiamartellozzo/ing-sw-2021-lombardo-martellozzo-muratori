package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.model.Player;

import java.util.List;

/**
 * this message sent to the View contains the name of the Winner and a list of Losers
 */
public class VShowEndGameResultsMsg extends GameMsg {

    private final String winnerUsername;
    private final List<Player> losers;
    private int victoryPoints;

    public VShowEndGameResultsMsg(String msgContent, String winnerUsername, int victoryPoints, List<Player> losers) {
        super(msgContent);
        this.winnerUsername = winnerUsername;
        this.victoryPoints = victoryPoints;
        this.losers = losers;
    }

    public String getPlayerUsername() {
        return winnerUsername;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public List<Player> getLosersUsernames() {
        return losers;
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
