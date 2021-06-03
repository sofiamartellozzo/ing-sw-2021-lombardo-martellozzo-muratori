package it.polimi.ingsw.network.server;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.connection.PingMsg;
import it.polimi.ingsw.message.connection.PongMsg;
import it.polimi.ingsw.message.controllerMsg.CCloseRoomMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.view.VirtualView;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * class in the SERVER that rapresent the server, it manage the comunication
 * with a specifc client so it is istanziated for each
 * Client that ask a connection in the specific Socket assigned
 * Whith this I can manage multiple Client contemporally
 */
public class ClientHandler extends Observable implements Runnable {

    private Socket clientSocket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private VirtualView virtualView; //the virtual view created for this client (this specific client handler indeed)
    private Thread ping;  //to keep alive the connection
    private String threadId; //the id for this thread that handle one specific client

    /* create a queue to contains all the msg received from the server */
    private ArrayList<GameMsg> queue;

    /* timer of 10 seconds waiting a reconnection */
    private Timer notifyDisconnectionTimer;
    private static final int TIMER = 50000;

    public ClientHandler(Socket client, String threadId) {
        clientSocket = client;
        this.threadId = threadId;
        /* when create the cHandler, create his virtual view too */
        virtualView = new VirtualView(this);
        //attach it to the Observable, because is an Observer
        attachObserver(ObserverType.CONTROLLER, virtualView);
        queue = new ArrayList<>();
        notifyDisconnectionTimer = new Timer();
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException /*| ClassNotFoundException*/ e) {
            /* disconnect */
            e.getStackTrace();
        }
    }

    private void handleConnection() throws IOException {
        /* open an in/out to comunicate */
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* start the ping process */
        startPing();

        /* now wait listening for a message (Event) */
        try {

            while (true) {
                handleMsg();
                GameMsg msgReceived;
                //TimeUnit.SECONDS.sleep(10);
                Object received = in.readObject(); //deserialized
                /* control that the msg received is not a ping msg, that one is to keep the connection cannot be send to the controller*/
                if (received != null) {
                    if ((received instanceof PongMsg)) {
                        System.out.println(((PongMsg) received).getMsgContent() + " from: " + threadId);
                    } else {
                        msgReceived = (GameMsg) received;
                        //add msg in the queue
                        addMsgInQueue(msgReceived);
                    }
                }
            }

        } catch (ClassNotFoundException | IOException | InvalidActionException /*| InterruptedException */ e) {
            System.err.println("Error from input client message");

            //because of losing the connection
            disconnect();
        } finally {
            clientSocket.close();
            //disconnect();
        }
    }



    /**
     * adding the new message in the queue to handle one msg at time
     *
     * @param msgReceived
     */
    private void addMsgInQueue(GameMsg msgReceived) {

        queue.add(msgReceived);
    }

    /**
     * notify the view with the messages received from the net
     */
    private void handleMsg() throws InvalidActionException {
        if (!queue.isEmpty()) {
            GameMsg msg = queue.remove(0);
            //notify controller with the received message
            notifyAllObserver(ObserverType.CONTROLLER, msg);
            //System.out.println("send msg to controller : " +msg.getMsgContent());     DEBUGGING
        }
    }

    /**
     * private method to start the Ping process, so keep the connection alive
     * from the server to the client every 5000 mills
     */
    private void startPing() {

        ping = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(10000);
                    sendMsg(new PingMsg("Ping!"));
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("Ping disable");
            } finally {
                Thread.currentThread().interrupt();
            }
        });
        ping.start();

    }

    private void stopPing() {
        ping.interrupt();
    }

    /**
     * because a reconnection of the client stop the timer and only close this thread
     */
    public void resetTimer(){
        notifyDisconnectionTimer.cancel();
        Thread.currentThread().interrupt();
        //notifyDisconnectionTimer = new Timer();
        //notifyDisconnectionTimer.schedule(new DisconnectHandler(this), TIMER);

    }

    public void startWaitReconnection(){
        notifyDisconnectionTimer.schedule(new DisconnectHandler(this), TIMER);
    }

    public void stopWaitReconnection(){
        //waitReconnection.interrupt();
        notifyDisconnectionTimer.cancel();
        CCloseRoomMsg msg1 = new CCloseRoomMsg("close the room with..", virtualView.getUsername());
        notifyAllObserver(ObserverType.CONTROLLER, msg1);
        detachObserver(ObserverType.CONTROLLER, virtualView);
        Thread.currentThread().interrupt();
    }

    /**
     * method called to send a msg from the server to the client
     *
     * @param msg
     */
    public void sendMsg(GameMsg msg) {
        try {
            out.reset();
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("unable to send msg to client");
        }
    }

    //at the end always disconnect so close the socket
    public void disconnect() {

        try {
            if (ping.isAlive()){
                stopPing();
            }
            CClientDisconnectedMsg notification = new CClientDisconnectedMsg("the client is not reachable anymore", virtualView.getUsername());
            notifyAllObserver(ObserverType.CONTROLLER, notification);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing socket");
        }
    }

    public InetAddress getUserIP() {
        return clientSocket.getInetAddress();
    }

    public int getUserPort() {
        return clientSocket.getPort();
    }
}
