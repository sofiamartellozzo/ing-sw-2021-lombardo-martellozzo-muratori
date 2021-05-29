package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;

/**
 * msg sent from CLI (MarketCLI) to the Action Controller to notify that a client stopped buying from the market
 */
public class CStopMarketMsg extends ControllerGameMsg {

    private String username;
    private TurnAction turnAction;

    public CStopMarketMsg(String msgContent,String username,TurnAction turnAction) {
        super(msgContent);
        this.username = username;
        this.turnAction = turnAction;
    }

    public String getUsername() {
        return username;
    }

    public TurnAction getTurnAction() {
        return turnAction;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
