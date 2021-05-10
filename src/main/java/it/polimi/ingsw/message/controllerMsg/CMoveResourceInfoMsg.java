package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TurnAction;

/**
 * CLI ---> VV ---> Lobby(Room) ---> TurnController ---> ActionController
 *
 * this is the response from the client with the depot from where the resource will be moved to where will be stored
 * them can be 1,2,3 or 4,5 if is active the special depot
 */
public class CMoveResourceInfoMsg extends ControllerGameMsg{
    private String username;
    private int fromDepot;
    private int toDepot;
    private TurnAction action;

    public CMoveResourceInfoMsg(String msgContent, String username, int fromDepot, int toDepot) {
        super(msgContent);
        this.username = username;
        this.fromDepot = fromDepot;
        this.toDepot = toDepot;
        this.action = TurnAction.MOVE_RESOURCE;
    }

    public TurnAction getActionChose(){
        return action;
    }

    public String getUsername() {
        return username;
    }

    public int getFromDepot() {
        return fromDepot;
    }

    public int getToDepot() {
        return toDepot;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
