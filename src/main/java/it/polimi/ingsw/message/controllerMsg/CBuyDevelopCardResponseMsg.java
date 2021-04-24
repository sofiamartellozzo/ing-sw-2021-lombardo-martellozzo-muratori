package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TurnAction;

/**
 * ths is the msg by the client with his choice
 * send to the turn controller, and actually make the move
 */
public class CBuyDevelopCardResponseMsg  extends ControllerGameMsg{
    private String username;
    private int row;
    private int column;
    private int cardSpaceToStoreIt;
    private TurnAction action;

    public CBuyDevelopCardResponseMsg(String msgContent, String username, int row, int column, int cardSpaceToStoreIt) {
        super(msgContent);
        this.username = username;
        this.row = row;
        this.column = column;
        this.cardSpaceToStoreIt = cardSpaceToStoreIt;
        this.action = TurnAction.BUY_CARD;
    }

    public TurnAction getActionChose(){
        return action;
    }

    public String getUsername() {
        return username;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getCardSpaceToStoreIt() {
        return cardSpaceToStoreIt;
    }
}
