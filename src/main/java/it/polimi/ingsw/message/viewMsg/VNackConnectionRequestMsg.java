package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;

import java.net.InetAddress;

/**
 * Lobby ---> VV ---> CLI
 *
 * a network fail because the username is taken
 * or because the room is full
 * or because someone else is creating a room
 */
public class VNackConnectionRequestMsg extends ViewGameMsg {

    private final String username;
    private final String errorInformation;
    private final InetAddress userIp;
    private final int userPort;

    public VNackConnectionRequestMsg(String content, int userPort , InetAddress userIp, String username, String errorInformation) {
        super(content);
        this.userIp = userIp;
        this.userPort = userPort;
        this.username = username;
        this.errorInformation = errorInformation;
    }


    public String getErrorInformation() {
        return errorInformation;
    }

    public InetAddress getUserIp() {
        return userIp;
    }

    public int getUserPort() {
        return userPort;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
