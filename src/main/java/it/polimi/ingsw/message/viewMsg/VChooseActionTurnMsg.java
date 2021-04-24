package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.model.TurnAction;

import java.util.ArrayList;

/**
 * msg send by the controller (Turn Controller) to the view
 * this will ask the client to choose an action he want to make
 */
public class VChooseActionTurnMsg extends ViewGameMsg{
    private String username;
    private ArrayList<TurnAction> availableActions;

    public VChooseActionTurnMsg(String msgContent, String username, ArrayList<TurnAction> availableActions) {
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
}
