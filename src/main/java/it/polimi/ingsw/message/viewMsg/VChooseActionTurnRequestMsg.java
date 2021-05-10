package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.TurnAction;

import java.util.ArrayList;

/**
 * TurnController ---> VV ---> CLI
 *
 * msg send by the controller (Turn Controller) to the view
 * this will ask the client to choose an action he want to make
 * from the list of action available (so that the client can make)
 */
public class VChooseActionTurnRequestMsg extends ViewGameMsg{
    private String username;
    private ArrayList<TurnAction> availableActions;

    public VChooseActionTurnRequestMsg(String msgContent, String username, ArrayList<TurnAction> availableActions) {
        super(msgContent);
        this.username = username;
        this.availableActions = availableActions;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<TurnAction> getAvailableActions() {
        return availableActions;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
