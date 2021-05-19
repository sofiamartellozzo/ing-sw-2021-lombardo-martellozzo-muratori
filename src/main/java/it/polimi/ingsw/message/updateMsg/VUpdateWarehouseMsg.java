package it.polimi.ingsw.message.updateMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;

/**
 * ActionController ---> VV ---> CLI / GUI
 *
 * send this msg from Server to notify the View (CLI or GUI) that the warehouse has changed
 */
public class VUpdateWarehouseMsg extends ViewGameMsg {
    private String username;
    private Warehouse warehouse;

    public VUpdateWarehouseMsg(String msgContent, String username, Warehouse warehouse) {
        super(msgContent);
        this.username = username;
        this.warehouse = warehouse;
    }

    public String getUsername() {
        return username;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
