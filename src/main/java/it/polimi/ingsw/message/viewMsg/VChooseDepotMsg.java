package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.model.TypeResource;

/**
 * ask the client to choose in which depot store the resource
 */
public class VChooseDepotMsg extends ViewGameMsg{

    private final String username;
    private TypeResource resourceToStore;

    public VChooseDepotMsg(String msgContent, String username, TypeResource resourceToStore) {
        super(msgContent);
        this.username = username;
        this.resourceToStore = resourceToStore;
    }

    public String getUsername() {
        return username;
    }

    public TypeResource getResourceToStore() {
        return resourceToStore;
    }
}
