package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardTable;

import it.polimi.ingsw.message.ViewObserver;

/**
 * ActionController --> VV --> CLI
 *
 * this msg is send by the Turn controller when the client want to
 * buy a Development Card in his Turn
 * the controller pass a double array on integer that represents the position of the table where
 * he/she can buy
 */
public class VChooseDevelopCardRequestMsg extends ViewGameMsg {

    private String username;
    private DevelopmentCardTable developmentCardTable;
    private boolean[][] cardAvailable;

    public VChooseDevelopCardRequestMsg(String msgContent, String username, DevelopmentCardTable developmentCardTable,boolean[][] cardAvailable) {
        super(msgContent);
        this.username = username;
        this.developmentCardTable = developmentCardTable;
        this.cardAvailable = cardAvailable;
    }

    public String getUsername() {
        return username;
    }

    public DevelopmentCardTable getDevelopmentCardTable() {
        return developmentCardTable;
    }

    public boolean[][] getCardAvailable() {
        return cardAvailable;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
