package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

/**
 * sent from PPController to view if the player wants to activate the base PP using resources that he hasn't
 */
public class VResourcesNotFoundMsg extends ViewGameMsg{

    private String username;
    public VResourcesNotFoundMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
