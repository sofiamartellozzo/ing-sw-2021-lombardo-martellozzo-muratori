package it.polimi.ingsw.event.message;

import it.polimi.ingsw.event.ControllerObserver;

public class ViewConnectionRequestMsg extends ViewGameMsg {
    private String IP;
    private int Port;
    private String username;

    public ViewConnectionRequestMsg(String IP, int port, String username)
    {
        this.IP=IP;
        Port = port;
        this.username=username;
    }

    public String getIP()
    {
        return IP;
    }
    public int getPort()
    {
        return Port;
    }
    public String getUsername()
    {
        return username;
    }

    @Override
    public void notifyHandler (ControllerObserver controllerObserver)
    {
        controllerObserver.handleMsg();
    }

}
