package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.GameMsg;

/**
 * ping msg to keep alive the connection, send by the server (clientHandler)
 */
public class PingMsg extends GameMsg {
    public PingMsg(String msgContent) {
        super(msgContent);
    }
}
