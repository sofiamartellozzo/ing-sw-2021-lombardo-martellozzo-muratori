package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TurnAction;

import java.util.Map;

public class CActivateProductionPowerResponseMsg extends ControllerGameMsg{
    private String username;
    private String where;
    private Integer which;
    private TurnAction action;

    public CActivateProductionPowerResponseMsg(String msgContent, String username, String where, Integer which){
        super(msgContent);
        this.username=username;
        this.where = where;
        this.which = which;
        this.action = TurnAction.ACTIVE_PRODUCTION_POWER;
    }

    public String getUsername() {
        return username;
    }

    public String getWhere() {
        return where;
    }

    public Integer getWhich() {
        return which;
    }

    public TurnAction getActionChose() {
        return action;
    }
}
