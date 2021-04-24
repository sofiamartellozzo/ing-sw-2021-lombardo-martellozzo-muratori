package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * request from the server (turn controller) to the client to choose which resource and where to move it
 * in this msg is stored the actual situation
 * in the map the integer (key) represents the depots and the TypeResource (value) what inside
 */
public class VMoveResourceRequestMsg extends ViewGameMsg{
    private String username;
    private HashMap<Integer, ArrayList<TypeResource>> depotsActualSituation;

    public VMoveResourceRequestMsg(String msgContent, String username, HashMap<Integer, ArrayList<TypeResource>> depotsActualSituation) {
        super(msgContent);
        this.username = username;
        this.depotsActualSituation = depotsActualSituation;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<Integer, ArrayList<TypeResource>> getDepotsActualSituation() {
        return depotsActualSituation;
    }
}
