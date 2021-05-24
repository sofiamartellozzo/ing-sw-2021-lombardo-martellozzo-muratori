package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.util.ArrayList;

/**
 * ActionController ---> VV ---> CLI / GUI
 *
 * after client ask to see the info of another player the server
 * ask him of which is asking about
 */
public class VWhichPlayerRequestMsg extends ViewGameMsg{
    private String username;
    private ArrayList<String> otherPlayers;

    public VWhichPlayerRequestMsg(String msgContent, String username, ArrayList<String> otherPlayers) {
        super(msgContent);
        this.username = username;
        this.otherPlayers = otherPlayers;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getOtherPlayers() {
        return otherPlayers;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
