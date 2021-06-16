package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.market.MarketStructure;

import java.util.Map;

/**
 * Action Controller --> VV --> CLI/GUI
 *
 * after something happen in the market structure and some changes are made
 */
public class VUpdateMarketMsg extends ViewGameMsg {
    private String username;
    private MarketStructure marketUpdate;
    private Map<Integer, PlayerInterface> allPlayers;

    public VUpdateMarketMsg(String msgContent, String username, MarketStructure marketUpdate, Map<Integer, PlayerInterface> allPlayers) {
        super(msgContent);
        this.username = username;
        this.marketUpdate = marketUpdate;
        this.allPlayers = allPlayers;
    }

    public String getUsername() {
        return username;
    }

    public MarketStructure getMarketUpdate() {
        return marketUpdate;
    }

    public Map<Integer, PlayerInterface> getAllPlayers() {
        return allPlayers;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
