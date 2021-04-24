package it.polimi.ingsw.message.viewMsg;

/**
 * this msg is send by the controller to the view to notify the client that
 * the depot he choose cannot store the
 * resource so he have to change his choice
 */
public class VNotValidDepotMsg extends ViewGameMsg{

    private String username;
    private int unableDepot;  //the one he chose before, not able

    public VNotValidDepotMsg(String msgContent, String username, int unableDepot) {
        super(msgContent);
        this.username = username;
        this.unableDepot = unableDepot;
    }

    public String getUsername() {
        return username;
    }

    public int getUnableDepot() {
        return unableDepot;
    }
}
