package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TypeResource;

/**
 * this msg is created by the cli when the player chose the resource
 * and the depot where to store it, then send it back to the controller to change the view
 */
public class CChooseResourceAndDepotMsg extends ControllerGameMsg{

    private final Color resource;
    private int depot;
    private String username;

    public CChooseResourceAndDepotMsg(String msgContent, Color resource, int depot, String username) {
        super(msgContent);
        this.resource = resource;
        this.depot = depot;
        this.username = username;
    }

    public Color getResource() {
        return resource;
    }

    public int getDepot() {
        return depot;
    }

    public String getUsername() {
        return username;
    }
}
