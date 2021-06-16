package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCardTable;

/**
 * CLI/ GUI ---> ActionController
 * this msg contains the response of the client with the position of the Card in the Dev Table
 * and the number of the card space where he wants to store it
 * send to the action controller (by Turn controller), and actually make the move
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


    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
