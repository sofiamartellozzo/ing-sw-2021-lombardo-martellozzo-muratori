package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;

/**
 * Initialized Controller, Action Controller --> VV -->  CLI/GUI
 *
 * update the state of the leader cards after the player chooses them and
 * also if a player active one of them (its state will become ACTIVE) or discard
 */
public class VUpdateLeaderCards extends ViewGameMsg {
    public String username;
    public ArrayList<LeaderCard> leaderCards;

    public VUpdateLeaderCards(String content,String username,ArrayList<LeaderCard> leaderCards){
        super(content);
        this.username=username;
        this.leaderCards=leaderCards;
    }

    public String getUsername(){return username;}
    public ArrayList<LeaderCard> getLeaderCards(){return leaderCards;}

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
