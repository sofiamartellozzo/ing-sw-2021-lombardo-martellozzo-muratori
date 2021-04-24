package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TurnAction;

/**
 * msg send by the client with his selection in the market
 */
public class CBuyFromMarketInfoMsg extends ControllerGameMsg{
    private String username;
    private String rowOrColumn;
    private int whichRorC;
    private TurnAction action;

    public CBuyFromMarketInfoMsg(String msgContent, String username, String rowOrColumn, int whichRorC) {
        super(msgContent);
        this.username = username;
        this.rowOrColumn = rowOrColumn;
        this.whichRorC = whichRorC;
        action = TurnAction.BUY_FROM_MARKET;
    }

    public TurnAction getActionChose(){
        return action;
    }

    public String getUsername() {
        return username;
    }

    public String getRowOrColumn() {
        return rowOrColumn;
    }

    public int getWhichRorC() {
        return whichRorC;
    }
}
