package it.polimi.ingsw.network.server;

import java.util.TimerTask;

/**
 * Timer for disconnect a client waited to much
 */
public class DisconnectHandler extends TimerTask {

    private ClientHandler clientHandler;

    public DisconnectHandler(ClientHandler clientHandler) {
        super();
        this.clientHandler = clientHandler;
    }

    /**
     * called when the timeout expire and say to the
     * client handler to close the game
     */
    @Override
    public void run() {
        System.out.println("[TIMER] timeout expire");
        clientHandler.stopWaitReconnection();
    }
}
