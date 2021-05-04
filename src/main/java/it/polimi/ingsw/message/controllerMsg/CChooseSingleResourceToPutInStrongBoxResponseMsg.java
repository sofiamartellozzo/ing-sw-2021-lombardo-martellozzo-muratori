package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TypeResource;

import java.lang.reflect.Type;

public class CChooseSingleResourceToPutInStrongBoxResponseMsg extends ControllerGameMsg{
    private String username;
    private TypeResource resource;

    public CChooseSingleResourceToPutInStrongBoxResponseMsg(String msgContent, String username, TypeResource resource){
        super(msgContent);
        this.username=username;
        this.resource=resource;
    }

    public String getUsername() {
        return username;
    }

    public TypeResource getResource() {
        return resource;
    }
}
