package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.connection.PingMsg;
import it.polimi.ingsw.message.connection.PongMsg;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * this class is where is readed the input from the CLIENT
 * and send the output for the server
 */
public class ClientSocket extends Observable implements Runnable {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String IP;
    private Socket serverSocket;

    public final static int SERVER_PORT = 2323;

    private boolean connectionOpen = false;

    private Thread ping;  //to keep alive the connection

    private ViewObserver clientView;

    /* create a queue to contains all the msg received from the server */
    private ArrayList<GameMsg> queue;

    public ClientSocket(String IPAddress, ViewObserver view) {
        this.IP = IPAddress;
        queue = new ArrayList<>();
        this.clientView = view;
        attachObserver(ObserverType.VIEW, clientView);
    }

    /**
     * try to open the connection
     */
    public void beginConnection() throws IOException {
        serverSocket = null;
        //open a connection with the server
        try {
            serverSocket = new Socket(IP, SERVER_PORT);
            connectionOpen = true;
        } catch (IOException e) {
            //System.err.println("Client unable to open the connection");
            connectionOpen = false;
            VServerUnableMsg error = new VServerUnableMsg("");
            notifyAllObserver(ObserverType.VIEW, error);
            //e.printStackTrace();

        }


        /* open the in/out Stream to communicate */
        if (connectionOpen) {
            try {
                out = new ObjectOutputStream(serverSocket.getOutputStream());
                in = new ObjectInputStream(new BufferedInputStream(serverSocket.getInputStream()));
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("The Socket cannot open the connection!");
            }

            /* start the ping process */
            startPing();
        }
    }


    /**
     * private method to start the Ping process, so keep the connection alive
     */
    private void startPing() {
        try {
            /* In a Socket an IP Address is represented by the class InetAddress, this class is used to
             * manage an IP Address for routing information throw the Net*/
            InetAddress serverAddress = InetAddress.getByName(this.IP);

            ping = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(10000);
                        out.writeObject(new PongMsg("Pong!"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Ping disable");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Unable to send pong msg to server");
                } finally {
                    Thread.currentThread().interrupt();
                }
            });
            ping.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Unable to convert IP address to InetAddress");
        }
    }

    /**
     * interrupting the sending of Pinging msg
     */
    private void stopPing() {
        ping.interrupt();
    }

    /**
     * send message to the server
     */
    public void sendMsg(GameMsg msg) {
        if (connectionOpen) {
            try {
                out.reset();
                out.writeObject(msg);    //serialized the msg (event)
                out.flush();             //create a buffer of the object and send in the net
            } catch (IOException e) {
                System.out.println("unable to send msg to server");
            }
        }
    }

    /**
     * adding the new message in the queue to handle one msg at time
     *
     * @param msgReceived
     */
    private void addMsgInQueue(GameMsg msgReceived) {
        //System.out.println("adding in the queue");        DEBUGGING
        queue.add(msgReceived);
    }

    /**
     * notify the view with the messages received from the net
     */
    private void handleMsg() {
        if (!queue.isEmpty()) {
            GameMsg msg = queue.remove(0);
            //then notify all the observer (viewer) about the new message arrived from the Server
            notifyAllObserver(ObserverType.VIEW, msg);
            //System.out.println("send msg to view : " +msg.getMsgContent()+ "for ");       DEBUGGING
        }
    }

    @Override
    public void run() {
        while (connectionOpen) {

            try {
                handleMsg();
                //TimeUnit.SECONDS.sleep(20);
                Object received = in.readObject(); //deserialized the msg from the server
                /* control that the msg received is not a ping msg, that one is to keep the connection cannot be send to the view*/
                if ((received instanceof PingMsg)) {
                    System.out.println(((PingMsg) received).getMsgContent() + " from the server: ");
                    //response with a Pong msg
                    sendMsg(new PongMsg("Pong!"));
                } else {
                    //casting the msg because it is a ViewMsg type for sure
                    GameMsg msg = (GameMsg) received;
                    //adding the msg in the queue
                    addMsgInQueue(msg);
                }

            } catch (IOException | ClassNotFoundException /*| InterruptedException*/ e) {
                System.out.println("this client disconnected from the server");
                /* setting the attribute to false because the connection shut down */
                connectionOpen = false;
                closeConnection();
            }

        }

    }


    public void closeConnection() {
        if (ping.isAlive()) {
            stopPing();
        }
        try {
            serverSocket.close();
            //if GUI is on handle the scene for the disconnection
        } catch (IOException e) {
            System.err.println("error closing the connection");
        }
    }
}
