package it.polimi.ingsw.message.controllerMsg;

import java.util.ArrayList;

public class CChooseLeaderCardResponseMsg extends ControllerGameMsg {

    private ArrayList<Integer> chosenLeaderCard;
    private ArrayList<Integer> discardedLeaderCard;
    private String username;
    private String action;

    /* constructor */
    public CChooseLeaderCardResponseMsg(String msgContent, ArrayList<Integer> chosenLeaderCard, ArrayList<Integer> discardedLeaderCard, String username, String action) {
        super(msgContent);
        this.chosenLeaderCard = chosenLeaderCard;
        this.discardedLeaderCard = discardedLeaderCard;
        this.username = username;
        this.action = action;
    }

    //override if the response is about the single card that have to be activated or removed
    public CChooseLeaderCardResponseMsg(String msgContent, ArrayList<Integer> chosenLeaderCard, String username, String action){
        super(msgContent);
        this.chosenLeaderCard = chosenLeaderCard;
        this.discardedLeaderCard = null;
        this.username = username;
        this.action = action;
    }

    public String getAction() {
        return action;
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
