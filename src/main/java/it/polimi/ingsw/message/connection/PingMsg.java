package it.polimi.ingsw.message.connection;

import it.polimi.ingsw.message.GameMsg;

public class PingMsg extends GameMsg {
    public PingMsg(String msgContent) {
        super(msgContent);
    }
}
