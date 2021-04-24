package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.PingMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * this class is where is readed the input from the CLIENT
 * and send the output for the server
 */
public class ClientSocket extends Observable implements Runnable{

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String IP;
    private Socket serverSocket;

    public final static int SERVER_PORT = 4444;

    private boolean connectionOpen = false;

    private Thread ping;  //to keep alive the connection

    public ClientSocket(String IPAddress){
        this.IP = IPAddress;
    }

    /**
     * try to open the connection
     */
    public void beginConnection() throws IOException{
        serverSocket = null;
        //open a connection with the server
        try{
            serverSocket = new Socket(IP, SERVER_PORT);
            connectionOpen = true;
        } catch (IOException e){
            System.err.println("Client unable to open the connection");
            e.printStackTrace();
        }


        /* open the in/out Stream to communicate */
        try {
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
        } catch (IOException e1){
            e1.printStackTrace();
            System.out.println("The Socket cannot open the connection!");
        }

        /* start the ping process */
        startPing();
    }


    /**
     * private method to start the Ping process, so keep the connection alive
     */
    private void startPing(){
        try{
            /* In a Socket an IP Address is represented by the class InetAddress, this class is used to
            * manage an IP Address for routing information throw the Net*/
            InetAddress serverAddress = InetAddress.getByName(this.IP);

            ping = new Thread(() -> {
                try{
                    while (true){
                        Thread.sleep(5000);
                        out.writeObject(new PingMsg("Ping!"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Ping disable");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Unable to send ping msg to server");
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

    private void stopPing(){
        ping.interrupt();
    }

    /**
     * send message to the server
     */
    public void sendMsg(GameMsg msg){
        if (connectionOpen){
            try{
                out.writeObject(msg);    //serialized the msg (event)
                out.flush();             //create a buffer of the object and send in the net
            } catch (IOException e){
                System.out.println("unable to send msg to server");
            }
        }
    }

    @Override
    public void run(){
        while (true){
            try{
                Object received = in.readObject(); //deserialized the msg from the server
                /* control that the msg received is not a ping msg, that one is to keep the connection cannot be send to the view*/
                if (!(received instanceof PingMsg)){
                    //casting the msg because it is a ViewMsg type for sure
                    ViewGameMsg msg = (ViewGameMsg) received;
                    //then notify all the observer (viewer) about the new message arrived from the Server
                    notifyAllObserver(ObserverType.VIEW, msg);
                }

            } catch (IOException | ClassNotFoundException e){
                System.out.println("this client disconnected from the server");
                /* setting the attribute to false because the connection shut down */
                connectionOpen = false;
                closeConnection();
            }
        }
    }

    public void closeConnection(){
        try{
            serverSocket.close();
        } catch (IOException e){
            System.err.println("error closing the connection");
        }
    }
}
