package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;

import java.util.ArrayList;

/**
 * CLI ---> VV ---> InitializedController or ActionController
 */
public class CChooseLeaderCardResponseMsg extends ControllerGameMsg {

    private ArrayList<Integer> leaderCards;
    private String username;
    private String action;

    /* constructor */
    public CChooseLeaderCardResponseMsg(String msgContent, ArrayList<Integer> chosenLeaderCard, String username, String action) {
        super(msgContent);
        this.leaderCards = chosenLeaderCard;
        this.username = username;
        this.action = action;
    }

    //override if the response is about the single card that have to be activated or removed
    public CChooseLeaderCardResponseMsg(String msgContent, Integer chosen, String username, String action){
        super(msgContent);
        this.leaderCards = new ArrayList<>();
        this.leaderCards.add(chosen);
        this.username = username;
        this.action = action;
    }


    public String getAction() {
        return action;
    }

    public ArrayList<Integer> getLeaderCards() { return leaderCards; }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
