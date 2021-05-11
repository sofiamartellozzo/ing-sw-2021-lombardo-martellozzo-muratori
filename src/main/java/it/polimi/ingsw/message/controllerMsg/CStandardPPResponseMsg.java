package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

public class CStandardPPResponseMsg extends ControllerGameMsg{
    private String username;
    private ArrayList<TypeResource> resources;
    private String where;

    public CStandardPPResponseMsg(String msgContent, String username, ArrayList<TypeResource> resources, String where){
        super(msgContent);
        this.username=username;
        this.resources=resources;
        this.where=where;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<TypeResource> getResources() {
        return resources;
    }

    public String getWhere() {
        return where;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
