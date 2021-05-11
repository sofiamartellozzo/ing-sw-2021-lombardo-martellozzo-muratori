package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * InitializedC ---> VV ---> CLI
 *
 *                  "initialization"
 * this class represent the msg send by the controller to the view in the initialization of the game
 * so is the Initialized Controller that create it and notify the view
 * it will send it to the client, waiting for the respond
 *
 *                      "remove"
 * it is used even in the choice of REMOVE a Leader card not activated yet
 *
 *                      "active"
 * it is used even in the choice of ACTIVATE a Leader card not activated yet
 */
public class VChooseLeaderCardRequestMsg extends ViewGameMsg {

    private ArrayList<Integer> miniDeckLeaderCardFour; //the four card throw the client has to chose
    private String username;
    private String whatFor;

    public VChooseLeaderCardRequestMsg(String msgContent, ArrayList<Integer> cardToChoose, String username, String whatFor) {
        super(msgContent);
        miniDeckLeaderCardFour = cardToChoose;
        this.username = username;
        this.whatFor = whatFor;
    }

    public ArrayList<Integer> getMiniDeckLeaderCardFour() {
        return miniDeckLeaderCardFour;
    }

    public String getUsername() {
        return username;
    }

    public String getWhatFor() {
        return whatFor;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
