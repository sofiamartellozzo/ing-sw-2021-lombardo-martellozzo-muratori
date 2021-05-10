package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.model.TypeResource;

public class CChooseSingleResourceToPutInStrongBoxResponseMsg extends ControllerGameMsg {
    private String username;
    private TypeResource resource;

    public CChooseSingleResourceToPutInStrongBoxResponseMsg(String content,String username,TypeResource resource){
        super(content);
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
