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
    private ArrayList<TypeResource> whiteSpecialResources;
    private int numberWhiteMarbleSpecial;

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

    public ArrayList<TypeResource> getWhiteSpecialResources() {
        return whiteSpecialResources;
    }

    public void setWhiteSpecialResources(ArrayList<TypeResource> whiteSpecialResources) {
        this.whiteSpecialResources = whiteSpecialResources;
    }

    public int getNumberWhiteMarbleSpecial() {
        return numberWhiteMarbleSpecial;
    }

    public void setNumberWhiteMarbleSpecial(int numberWhiteMarbleSpecial) {
        this.numberWhiteMarbleSpecial = numberWhiteMarbleSpecial;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
