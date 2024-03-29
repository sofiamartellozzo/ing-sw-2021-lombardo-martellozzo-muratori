package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;

/**
 * CLI ---> VV ---> Lobby(Room) ---> TurnController ---> ActionController
 *
 * msg send by the client with his selection in the market
 * row/column and which row/column
 */
public class CBuyFromMarketInfoMsg extends ControllerGameMsg{
    private final String username;
    private String rowOrColumn;
    private int whichRorC;
    private final TurnAction action;

    public CBuyFromMarketInfoMsg(String msgContent, String username, String rowOrColumn, int whichRorC) {
        super(msgContent);
        this.username = username;
        this.rowOrColumn = rowOrColumn;
        this.whichRorC = whichRorC;
        this.action = TurnAction.BUY_FROM_MARKET;
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


    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
