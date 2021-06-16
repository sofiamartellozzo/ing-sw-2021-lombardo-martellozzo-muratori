package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;

/**
 * CLI --> VV --> TurnController
 *
 * msg from the client to choose another action turn (for ex because he can't buy anything from the development card)
 */
public class CChangeActionTurnMsg extends ControllerGameMsg {
    private String username;
    private TurnAction actionChosen;

    public CChangeActionTurnMsg(String msgContent, String username, TurnAction actionChosen) {
        super(msgContent);
        this.actionChosen = actionChosen;
        this.username =username;
    }

    public String getUsername() {
        return username;
    }

    public TurnAction getActionChosen() {
        return actionChosen;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
