package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server class that accept each client
 * create a ClientHandler and a Thread for each request
 */
public class GameServer {

    /**
     * Socket Port to listen.
     */
    public final static int SOCKET_PORT = 4444;
    /**
     * Version of the server.
     */
    public static final String SERVER_VERSION = "2.1.0";

    public static void main(String[] args) {

        ServerSocket socket;

        try {
            /* create a socket on the server */
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open the server socket");
            System.exit(1);
            return;
        }

        System.out.println("Server is running - version:" + SERVER_VERSION + "\naccepting...");
        Socket clientSocket = null;

        while (true) {
            try {
                /* now the server accept the connections
                 * for each connection create a new Thread
                 * that execute a Client Handler*/
                clientSocket = socket.accept(); //get the client socket
                //clientSocket.setSoTimeout(20*10000); //if not ping
                String clientIpAddress = clientSocket.getInetAddress().toString().substring(1);
                System.out.println("New client accepted:\tIPAddress: " + clientIpAddress + "\tPort: " + clientSocket.getPort());

                //create a Client Handler to handle the trasmission of msg between client e server
                String threadID = clientIpAddress + "@" + clientSocket.getPort();
                ClientHandler clientHandler = new ClientHandler(clientSocket, threadID);

                //create a new thread to handle this client
                Thread thread = new Thread(clientHandler, threadID);
                thread.start();
                System.out.println("Client " + clientIpAddress + " in thread: " + threadID);

            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}

