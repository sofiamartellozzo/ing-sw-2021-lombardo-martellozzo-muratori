package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.market.MarketStructure;

/**
 * ActionController --> VV --> CLI
 *
 * msg send by the server to the client after he asked to buy from the market
 * to ask the row or column and which one he choose
 */
public class VBuyFromMarketRequestMsg extends ViewGameMsg{
    private MarketStructure market;
    private final String username;

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
    public void notifyHandler(ViewObserver viewObserver){
        viewObserver.receiveMsg(this);
    }
}
