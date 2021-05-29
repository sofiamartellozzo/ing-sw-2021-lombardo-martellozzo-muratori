package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * ActionController ---> VV ---> CLI
 *
 * ask the client to choose in which depot store the resource
 */
public class VChooseDepotMsg extends ViewGameMsg{

    private final String username;
    private ArrayList<TypeResource> resourceToStore;

    public VChooseDepotMsg(String msgContent, String username, ArrayList<TypeResource> resourceToStore) {
        super(msgContent);
        this.username = username;
        this.resourceToStore = resourceToStore;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<TypeResource> getResourceToStore() {
        return resourceToStore;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
