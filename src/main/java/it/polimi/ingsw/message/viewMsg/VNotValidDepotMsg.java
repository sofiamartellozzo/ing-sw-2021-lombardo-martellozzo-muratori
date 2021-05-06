package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TypeResource;

/**
 * InitializedC ---> VV ---> CLI
 * this msg is send by the controller to the view to notify the client that
 * the depot he choose cannot store the
 * resource so he have to change his choice
 */
public class VNotValidDepotMsg extends ViewGameMsg{

    private final String username;
    private int unableDepot;  //the one he chose before, not able
    private Color resourceChooseBefore;

    public VNotValidDepotMsg(String msgContent, String username, int unableDepot, Color resourceChooseBefore ) {
        super(msgContent);
        this.username = username;
        this.unableDepot = unableDepot;
        this.resourceChooseBefore = resourceChooseBefore;
    }

    public String getUsername() {
        return username;
    }

    public int getUnableDepot() {
        return unableDepot;
    }

    public Color getResourceChooseBefore() {
        return resourceChooseBefore;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
