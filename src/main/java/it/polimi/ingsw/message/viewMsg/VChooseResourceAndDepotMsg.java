package it.polimi.ingsw.message.viewMsg;

/**
 * this class represent the msg send by the controller to the view, that will send
 * directly to the client for make him chose a resources and in witch depot store it
 */
public class VChooseResourceAndDepotMsg extends ViewGameMsg {

    private String username;

    public VChooseResourceAndDepotMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
