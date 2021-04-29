package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.net.InetAddress;

/* a network fail because the username is taken or because the room is full */
public class CNackConnectionRequestMsg extends ControllerGameMsg{

    private final String username;
    private final String errorInformation;
    private final InetAddress userIp;
    private final int userPort;

    public CNackConnectionRequestMsg(String content, int userPort ,InetAddress userIp, String username, String errorInformation) {
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
