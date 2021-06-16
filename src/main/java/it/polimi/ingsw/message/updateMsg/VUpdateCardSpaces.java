package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.card.Card;

import java.util.ArrayList;

/**
 * Action Controller --> VV --> CLI/GUI
 *
 * msg used to update the situation of the three card spaces of the player's personal board
 */
public class VUpdateCardSpaces extends ViewGameMsg {

    private String username;
    private ArrayList<CardSpace> cardSpaces;

    public VUpdateCardSpaces(String content,String username,ArrayList<CardSpace> cardSpaces){
        super(content);
        this.username=username;
        this.cardSpaces=cardSpaces;
    }

    public String getUsername(){return username;}
    public ArrayList<CardSpace> getCardSpaces(){return cardSpaces;}

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
