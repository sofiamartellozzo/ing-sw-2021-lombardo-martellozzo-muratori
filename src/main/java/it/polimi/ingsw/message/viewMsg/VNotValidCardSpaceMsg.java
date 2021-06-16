package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.card.DevelopmentCard;

/**
 * Action Controller --> VV --> CLI/GUI
 * msg sent if the card space chosen isn't available or the card bought can't be put in that card space
 */
public class VNotValidCardSpaceMsg extends ViewGameMsg{
    private String username;
    private int rowTable;
    private int columnTable;

    public VNotValidCardSpaceMsg(String msgContent, String username, int rowTable, int columnTable) {
        super(msgContent);
        this.username = username;
        this.rowTable = rowTable;
        this.columnTable = columnTable;
    }

    public String getUsername() {
        return username;
    }

    public int getRowTable() {
        return rowTable;
    }

    public int getColumnTable() {
        return columnTable;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
