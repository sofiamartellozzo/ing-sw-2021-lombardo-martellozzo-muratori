package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.util.List;

/**
 * End Game Controller --> VV --> CLI/GUI
 * this message sent to the View contains the name of the Winner and a list of Losers
 * and it will be sent at the end of the game
 */
public class VShowEndGameResultsMsg extends GameMsg {

    private String winnerUsername;
    private List<Player> losers;
    private int victoryPoints;
    private boolean soloWin;

    public VShowEndGameResultsMsg(String msgContent, String winnerUsername, int victoryPoints, List<Player> losers) {
        super(msgContent);
        this.winnerUsername = winnerUsername;
        this.victoryPoints = victoryPoints;
        this.losers = losers;
    }

    public VShowEndGameResultsMsg(String msgContent, String winnerUsername, int victoryPoints, boolean win){
        super(msgContent);
        this.winnerUsername=winnerUsername;
        this.victoryPoints=victoryPoints;
        this.soloWin=win;
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

    public boolean isSoloWin(){
        return soloWin;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        try {
            viewObserver.receiveMsg(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
