package it.polimi.ingsw.message.viewMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent the msg send by the controller to the view in the initialization of the game
 * so is the Initialized Controller that create it and notify the view
 * it will send it to the client, waiting for the respond
 */
public class VChooseLeaderCardRequestMsg extends ViewGameMsg {

    private ArrayList<Integer> miniDeckLeaderCardFour; //the four card throw the client has to chose
    private String username;


    public VChooseLeaderCardRequestMsg(String msgContent, ArrayList<Integer> cardToChoose, String username) {
        super(msgContent);
        miniDeckLeaderCardFour = cardToChoose;
        this.username = username;
    }

    public ArrayList<Integer> getMiniDeckLeaderCardFour() {
        return miniDeckLeaderCardFour;
    }

    public String getUsername() {
        return username;
    }
}
