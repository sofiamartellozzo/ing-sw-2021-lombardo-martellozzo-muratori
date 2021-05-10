package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI/GUI ---> VV ---> Lobby(Room) ---> Action Controller
 *
 * msg from client to discard a resource from the market
 */
public class CChooseDiscardResourceMsg extends ControllerGameMsg{

    private final String username;

    public CChooseDiscardResourceMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
