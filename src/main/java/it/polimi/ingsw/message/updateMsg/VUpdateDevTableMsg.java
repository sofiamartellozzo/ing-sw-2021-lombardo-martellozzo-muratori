package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.market.MarketStructure;

import java.util.Map;

public class VUpdateDevTableMsg extends ViewGameMsg {
    private String username;
    private DevelopmentCardTable updateTable;
    private Map<Integer, PlayerInterface> allPlayers;

    public VUpdateDevTableMsg(String msgContent, String username, DevelopmentCardTable updateTable, Map<Integer, PlayerInterface> allPlayers) {
        super(msgContent);
        this.username = username;
        this.updateTable = updateTable;
        this.allPlayers = allPlayers;
    }

    public String getUsername() {
        return username;
    }

    public DevelopmentCardTable getUpdateTable() {
        return updateTable;
    }

    public Map<Integer, PlayerInterface> getAllPlayers() {
        return allPlayers;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
