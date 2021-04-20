package it.polimi.ingsw.message.controllerMsg;

import java.util.ArrayList;

public class CChooseLeaderCardResponseMsg extends ControllerGameMsg {

    private ArrayList<Integer> chosenLeaderCard;
    private ArrayList<Integer> discardedLeaderCard;
    private String username;

    /* constructor */
    public CChooseLeaderCardResponseMsg(String msgContent, ArrayList<Integer> chosenLeaderCard, ArrayList<Integer> discardedLeaderCard, String username) {
        super(msgContent);
        this.chosenLeaderCard = chosenLeaderCard;
        this.discardedLeaderCard = discardedLeaderCard;
        this.username = username;
    }

    public ArrayList<Integer> getChosenLeaderCard() {
        return chosenLeaderCard;
    }

    public ArrayList<Integer> getDiscardedLeaderCard() {
        return discardedLeaderCard;
    }

    public String getUsername() {
        return username;
    }
}
