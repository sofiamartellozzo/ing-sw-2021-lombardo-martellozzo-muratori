package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.model.TypeResource;

public class CChooseSingleResourceToPutInStrongBoxResourceMsg extends ControllerGameMsg {
    private String username;
    private TypeResource resource;

    public CChooseSingleResourceToPutInStrongBoxResourceMsg(String msgContent, String username, TypeResource resource) {
        super(msgContent);
        this.username = username;
        this.resource = resource;
    }

    public String getUsername() {
        return username;
    }

    public TypeResource getResource() {
        return resource;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
