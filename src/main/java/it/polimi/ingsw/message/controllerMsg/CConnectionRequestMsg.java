package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.view.VirtualView;

import java.net.InetAddress;

/**
 *
 *  Virtual View -----> Lobby
 *
 *  msg send by the VV to the lobby after a request of a connection
 *  so the lobby will handle the request checking the username and
 *  assigning the client to a new room
 */
public class CConnectionRequestMsg extends ControllerGameMsg {

    private InetAddress IP;
    private int Port;
    private String username;
    private String gameSize;
    private VirtualView VV;

    public CConnectionRequestMsg(String content, InetAddress IP, int port, String username, String gameSize) {
        super(content);
        this.IP = IP;
        Port = port;
        this.username = username;
        this.gameSize = gameSize;
    }

    public CConnectionRequestMsg(String content, String username, String gameSize ){
        super(content);
        this.username = username;
        this.gameSize = gameSize;
    }

    public InetAddress getIP() {
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

    public void setVirtualView(VirtualView virtualView){
        VV = virtualView;
    }

    public VirtualView getVV() {
        return VV;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver){
        controllerObserver.receiveMsg(this);
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {

    }
}
