package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

public class CStandardPPResponseMsg extends ControllerGameMsg{
    private String username;
    private ArrayList<TypeResource> resourcesToRemove;

    public TypeResource getResourceToAdd() {
        return resourceToAdd;
    }

    private TypeResource resourceToAdd;
    private String where;

    public CStandardPPResponseMsg(String msgContent, String username, ArrayList<TypeResource> resourcesToRemove, TypeResource resourceToAdd,String where){
        super(msgContent);
        this.username=username;
        this.resourcesToRemove = resourcesToRemove;
        this.resourceToAdd=resourceToAdd;
        this.where=where;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<TypeResource> getResourcesToRemove() {
        return resourcesToRemove;
    }

    public String getWhere() {
        return where;
    }
}
