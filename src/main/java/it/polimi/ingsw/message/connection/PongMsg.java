package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.GameMsg;

/**
 * pong msg to keep alive the connection, send by the client as a response (clientSocket)
 */
public class PongMsg extends GameMsg {

    public PongMsg(String msgContent) {
        super(msgContent);
    }
}
