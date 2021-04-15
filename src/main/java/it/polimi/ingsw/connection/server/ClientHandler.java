package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.event.GameEvent;

import java.io.*;
import java.net.Socket;

/**
 * class in the server that rapresent the server, it manage the comunication
 * with a specifc client so it is istanziated for each
 * Client that ask a connection in the specific Socket assigned
 * Whith this I can manage multiple Client contemporally
 */
public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String IP;

    public ClientHandler(Socket client){
        clientSocket = client;
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
            in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }


        /* now waith listening for a message (Event) */
        GameEvent recived;
        try {
            recived = (GameEvent) in.readObject(); //deserialized
            //notify observer
        } catch (ClassNotFoundException | /*EOFException |*/ IOException  e){
            System.err.println("Error from input client message");
        } finally {
            //clientSocket.close();
            disconnect();
        }
    }

    public void sendMsg (/*event*/){
        try {
            // out.writeObject(event);
            out.flush();
        } catch (IOException e){
            System.err.println("unable to send msg to client");
        }
    }

    public void disconnect(){
        try {
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error closing socket");
        }
    }
}
