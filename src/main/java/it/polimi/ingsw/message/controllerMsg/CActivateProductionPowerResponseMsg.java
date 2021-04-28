package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TurnAction;

import java.util.ArrayList;
import java.util.Map;

public class CActivateProductionPowerResponseMsg extends ControllerGameMsg{
    private final String username;
    private Map<String,Integer> choose;
    private final TurnAction action;

    public CActivateProductionPowerResponseMsg(String msgContent, String username, Map<String,Integer> choose){
        super(msgContent);
        this.username=username;
        this.choose=choose;
        this.action = TurnAction.ACTIVE_PRODUCTION_POWER;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getChoose() {
        return choose;
    }

    public TurnAction getActionChose() { return action; }
}
