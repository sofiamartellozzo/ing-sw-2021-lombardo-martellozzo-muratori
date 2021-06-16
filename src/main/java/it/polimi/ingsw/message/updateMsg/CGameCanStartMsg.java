package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

import java.util.List;

/**
 * Initialized Controller --> VV --> CLI/GUI
 *
 * initialization msg that contains all the players (their username)
 */
public class CGameCanStartMsg extends GameMsg {

    private List<String> players;

    public CGameCanStartMsg(String msgContent, List<String> players) {
        super(msgContent);
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
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
