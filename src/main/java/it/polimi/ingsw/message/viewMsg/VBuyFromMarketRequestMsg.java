package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

/**
 * ActionController ---> VV ---> CLI
 *
 * msg send by the server to the client after he asked to buy from the market
 * to ask the row or column and which one he choose
 */
public class VBuyFromMarketRequestMsg extends ViewGameMsg{
    private final String username;

    public VBuyFromMarketRequestMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
