package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.utility.MarketStructureCopy;

/**
 * ActionController --> VV --> CLI
 * msg send by the server to the client after he asked to buy from the market
 * to ask the row or column and which one he choose
 */
public class VBuyFromMarketRequestMsg extends ViewGameMsg{
    private String username;
    private MarketStructure market;

    public VBuyFromMarketRequestMsg(String msgContent, String username, MarketStructure marketStructure) {
        super(msgContent);
        this.username = username;
        market = marketStructure;
    }

    public String getUsername() {
        return username;
    }

    public MarketStructure getMarket() { return market; }

    @Override
    public void notifyHandler(ViewObserver viewObserver) throws InvalidActionException {
        viewObserver.receiveMsg(this);
    }
}
