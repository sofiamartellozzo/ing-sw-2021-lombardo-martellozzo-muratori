package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;
import java.util.Map;

/**
 * CLI ---> VV ---> Lobby(Room) ---> TurnController ---> ActionController ---> PPController
 *
 * response from the client with the number corresponding the space with the card to activate
 *
 *  availableCardSpace:
 *      - 0     ---> base PP
 *      - 1,2,3 ---> card Space
 *      - 4,5   ---> Special
 */
public class CActivateProductionPowerResponseMsg extends ControllerGameMsg{
    private String username;
    private String where;
    private int which;
    private ArrayList<TypeResource> resourcesToPay;
    private TypeResource resourceToGet;
    private TurnAction action;

    public CActivateProductionPowerResponseMsg(String msgContent, String username, String where, int which){
        super(msgContent);
        this.username=username;
        this.where = where;
        this.which = which;
        this.action = TurnAction.ACTIVE_PRODUCTION_POWER;
        this.resourcesToPay=null;
        this.resourceToGet=null;
    }

    public String getUsername() {
        return username;
    }

    public String getWhere() {
        return where;
    }

    public int getWhich() {
        return which;
    }

    public TurnAction getActionChose(){
        return action;
    }

    public ArrayList<TypeResource> getResourcesToPay() {
        return resourcesToPay;
    }

    public TypeResource getResourceToGet() {
        return resourceToGet;
    }

    public void setResourceToGet(TypeResource resourceToGet){this.resourceToGet=resourceToGet;}

    public void setResourcesToPay(ArrayList<TypeResource> resourcesToPay){this.resourcesToPay=resourcesToPay;}

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }

}
