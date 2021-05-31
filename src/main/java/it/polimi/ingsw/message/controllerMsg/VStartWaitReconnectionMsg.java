package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.view.VirtualView;

/**
 * TurnController ---> VV [ClientHandler]
 *
 * put the handler on a wait until a client reconnect to the game
 * if the time expire close the room, so send the CCloseRoomMsg
 * to the Lobby that will remove the room
 */
public class VStartWaitReconnectionMsg extends ViewGameMsg {
    private String username;

    public VStartWaitReconnectionMsg(String msgContent, String username) {
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
