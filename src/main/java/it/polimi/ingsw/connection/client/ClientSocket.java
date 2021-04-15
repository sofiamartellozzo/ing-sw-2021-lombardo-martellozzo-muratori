package it.polimi.ingsw.connection.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * this class is where is readed the input from the client
 * and send the output for the server
 */
public class ClientSocket implements Runnable{

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String IP;
    private Socket serverSocket;

    public final static int SERVER_PORT = 4444;

    private boolean connectionOpen = false;

    public ClientSocket(String IPAddress){
        this.IP = IPAddress;
    }

    /**
     * try to open the connection
     */
    public void beguin() throws IOException{
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
            in = new ObjectInputStream(new BufferedInputStream(serverSocket.getInputStream()));
            out = new ObjectOutputStream(serverSocket.getOutputStream());
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

    /**
     * send message to the server
     */
    public void sendMsg( /*event*/ ){
        if (connectionOpen){
            try{
               // out.writeObject(event); //serialized the msg (event)
                out.flush();    //create a buffer of the object and send in the net
            } catch (IOException e){
                System.out.println("unable to send msg to server");
            }
        }
    }

    @Override
    public void run(){
        while (true){
            try{
                Object recived = in.readObject(); //deserialized the msg from the server
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
