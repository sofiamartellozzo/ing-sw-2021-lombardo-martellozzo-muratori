package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import it.polimi.ingsw.connection.client.ClientSocket;
import it.polimi.ingsw.event.message.ViewConnectionRequestMsg;
import it.polimi.ingsw.view.display.WriteMessageDisplay;

/**
 * CLI version of the View
 */
public class CLI {

    private Scanner in;             //for input data from console
    private PrintStream out;        //for output data for console

    private ClientSocket client;    //client that view the cli
    private String username;        //store locally the client username

    private String[] args;           //the args recive from the ClientMain

    /* create a cache of the Leader Card choosen by this client */
    private List<Integer> myLeaderCards;
    /* local info about the client position of faith marker */
    private int positionOnFaithTrack;

    /* create a cache containing the LCards of the other players */
    private Map<String,Integer> antagonistLeaderCards;
    /* local info of opponent position on Fait Track */
    private Map<String,Integer> antagonistFaithMarkers;

    public CLI(String[] args){
        out = System.out;
        in = new Scanner(System.in);
        this.args = args.clone();
    }


    public void start(){

        boolean connectionOFF = true;

        /* start class and visualized the Game Title */
        WriteMessageDisplay.writeTitle();

        //remember to check the connection is actually working!!!!
        WriteMessageDisplay.writeOnlineStatus();

        /* ask the client to press enter button */
        System.out.println("Press enter to start...");
        in.nextLine();

        /* clear the screen to start a new game */
        clearScreen();

        /* set up the client info */
        //first ask the IP for the connection
        String IP;
        IP = askIPAddress();

        /* Initialize client socket */
        client = new ClientSocket(IP);

        /* repeat this cycle until the connection go ON and the client reaches the server */
        while (connectionOFF) {
            try {
                client.beginConnection();            //open connection with the client
                System.out.println("Client Connected");
                clearScreen();

                String user = askUsername();
                /* put inside the variable username the name that the client chose*/
                username = user;

                /* try to create the connection sending the username, port and ip */
                ViewConnectionRequestMsg request = new ViewConnectionRequestMsg(IP,0,username);

                // start client Thread ....

                connectionOFF = false;
            } catch (IOException e) {
                System.out.println(" Error, can't reach the server");
                connectionOFF = true;
            }
        }

    }


    public String askIPAddress(){
        System.out.println("Please insert the Ip address:\n");
        System.out.println("Press ENTER for local setup");
        String ip = in.nextLine();
        /* first check if localhost is asked */
        if (ip.equals("")){
            ip = "127.0.0.1"; //localhost, ip referred to server on client computer
        }
        /* check if is the right ip*/
        else{
            boolean checking = true;
            while (checking){
                if (ip==null||ip.length()<7||ip.length()>15){
                    System.out.println("⚠︎ Error: ip not valid!\n  Please insert another ip:");
                    ip = in.nextLine();
                }
                else{
                    checking = false;
                }
            }
        }
        return ip;
    }

    /**
     * method to ask the player its Username, and check if it is valid
     */
    public String askUsername(){
        /* preparing an Input scanner in order to take the Username from the client*/
        in = new Scanner(System.in);
        in.reset();

        String username = null;
        out.println("Please insert the Username that you want ");

        username = in.nextLine();

        return username;
    }

    /**
     * method to clear the screen and remove older prints
     */
    public static void clearScreen(){
        System.out.println("reset and clear the screen");
        System.out.println("\033[H\033[2J");  //H is for go back to the top and 2J is for clean the screen
        System.out.flush();
    }


    /**
     * method to print the the antagonist's Cards
     */
    public void showAntagonistCards()
    {
        for (Map.Entry<String,Integer> antagonists: antagonistLeaderCards.entrySet())
        {
            System.out.println("Card : " + antagonists.getValue());
        }

    }

    /**
     * method to print my Info
     */
    public void showMyInfo()
    {
        /* show every Leader card that the player has (max 2) */
        for (int card: myLeaderCards) {
            System.out.println(" My Card "+ card);
        }

    }
}
