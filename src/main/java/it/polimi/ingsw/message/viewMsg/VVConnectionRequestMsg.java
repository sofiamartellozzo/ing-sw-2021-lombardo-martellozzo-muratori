package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 *  CLI ----> Virtual View
 *
 * message send by the client to the server
 * the Virtual view receive it with the info:
 * IP, Port, username and the type of the Game the client choose
 */

public class VVConnectionRequestMsg extends ViewGameMsg{

    private String IP;
    private int Port;
    private String username;
    private String gameSize;

    public VVConnectionRequestMsg(String content, String IP, int port, String username, String gameSize) {
        super(content);
        this.IP = IP;
        Port = port;
        this.username = username;
        this.gameSize = gameSize;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return Port;
    }

    public String getUsername() {
        return username;
    }

    public String getGameSize() {
        return gameSize;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        /* this class now redirect the message with the info to the controller (lobby) */
        controllerObserver.receiveMsg(this);
    }
}
