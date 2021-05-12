package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

public class CStandardPPResponseMsg extends ControllerGameMsg{
    private String username;
    private ArrayList<TypeResource> resourcesToPay;
    private TypeResource resourceToGet;
    private String where;

    public CStandardPPResponseMsg(String msgContent, String username, ArrayList<TypeResource> resourcesToPay, TypeResource resourceToGet, String where){
        super(msgContent);
        this.username=username;
        this.resourcesToPay =resourcesToPay;
        this.resourceToGet = resourceToGet;
        this.where=where;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<TypeResource> getResourcesToPay() {
        return resourcesToPay;
    }

    public TypeResource getResourceToGet(){return resourceToGet;}

    public String getWhere() {
        return where;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
