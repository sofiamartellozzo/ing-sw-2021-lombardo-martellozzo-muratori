package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TurnAction;

import java.util.ArrayList;

/**
 * msg of response by the client with wich action he want to do in the turn
 */
public class CChooseActionTurnResponseMsg extends ControllerGameMsg{
    private String username;
    private TurnAction actionChose;

    public CChooseActionTurnResponseMsg(String msgContent, String username, TurnAction actionChose) {
        super(msgContent);
        this.username = username;
        this.actionChose = actionChose;
    }

    public String getUsername() {
        return username;
    }

    public TurnAction getActionChose() {
        return actionChose;
    }
}
