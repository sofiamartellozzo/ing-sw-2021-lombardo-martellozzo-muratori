package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;

public class CConnectionRequestMsg extends ControllerGameMsg {

    private String IP;
    private int Port;
    private String username;
    private String gameSize;

    public CConnectionRequestMsg(String content, String IP, int port, String username, String gameSize) {
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
        controllerObserver.receiveMsg(this);
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {

    }
}
