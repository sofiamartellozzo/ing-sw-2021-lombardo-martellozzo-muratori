package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ViewObserver;

/* a connection fail because the username is taken or because the room is full */
public class CNackConnectionRequestMsg extends ControllerGameMsg{

    private final String username;
    private final String errorInformation;
    private final String userIp;
    private final int userPort;

    public CNackConnectionRequestMsg(String content, int userPort ,String userIp, String username, String errorInformation) {
        super(content);
        this.userIp = userIp;
        this.userPort = userPort;
        this.username = username;
        this.errorInformation = errorInformation;
    }


    public String getErrorInformation() {
        return errorInformation;
    }

    public String getUserIp() {
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
