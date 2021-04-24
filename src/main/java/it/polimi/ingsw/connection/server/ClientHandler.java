package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.PingMsg;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.view.VirtualView;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * class in the SERVER that rapresent the server, it manage the comunication
 * with a specifc client so it is istanziated for each
 * Client that ask a connection in the specific Socket assigned
 * Whith this I can manage multiple Client contemporally
 */
public class ClientHandler extends Observable implements Runnable{

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private InetAddress IP;
    private VirtualView virtualView; //the virtual view created for this client (this specific client handler indeed)
    private Thread ping;  //to keep alive the connection

    public ClientHandler(Socket client){
        clientSocket = client;
        /* when create the cHandler, create his virtual view too */
        virtualView = new VirtualView(this);
        //attach it to the Observable, because is an Observer
        attachObserver(ObserverType.VIEW, virtualView);
    }

    @Override
    public void run() {
        try{
            handleConnection();
        } catch (IOException /*| ClassNotFoundException*/ e){
            /* disconnect */
            e.getStackTrace();
        }
    }

    private void handleConnection() throws IOException{
        /* open an in/out to comunicate */
        try{
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }

        /* start the ping process, like in ClientSocket*/
        startPing();


        /* now wait listening for a message (Event) */
        GameMsg msgReceived;
        try {
            Object received =  in.readObject(); //deserialized
            /* control that the msg received is not a ping msg, that one is to keep the connection cannot be send to the controller*/
            if (received!=null){
                if (!(received instanceof PingMsg)){
                    msgReceived = (GameMsg) received;
                    //notify controller with the received message
                    notifyAllObserver(ObserverType.CONTROLLER, msgReceived);
                }
                else{
                    System.out.println(((PingMsg) received).getMsgContent()+ "from: " +IP);
                }
            }

        } catch (ClassNotFoundException | /*EOFException |*/ IOException  e){
            System.err.println("Error from input client message");
        } finally {
            //clientSocket.close();
            disconnect();
        }
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

    /**
     * method called to send a msg from the server to the client
     * @param msg
     */
    public void sendMsg(GameMsg msg){
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e){
            System.err.println("unable to send msg to client");
        }
    }

    //at the end always disconnect so close the socket
    public void disconnect(){
        try {
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error closing socket");
        }
    }

    public InetAddress getUserIP() {
        return clientSocket.getInetAddress();
    }
    public int getUserPort(){
        return clientSocket.getPort();
    }
}
