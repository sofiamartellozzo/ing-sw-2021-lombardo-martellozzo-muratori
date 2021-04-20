package it.polimi.ingsw.view;

import java.awt.font.NumericShaper;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import it.polimi.ingsw.connection.client.ClientSocket;
import it.polimi.ingsw.event.Observable;
import it.polimi.ingsw.event.ViewObserver;
import it.polimi.ingsw.event.message.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.event.message.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.event.message.VConnectionRequestMsg;
import it.polimi.ingsw.event.message.ViewGameMsg;
import it.polimi.ingsw.view.display.WriteMessageDisplay;

/**
 * CLI version of the View
 */
public class CLI extends Observable implements ViewObserver {

    private Scanner in;             //for input data from console
    private PrintStream out;        //for output data for console

    private ClientSocket client;    //client that view the cli
    private String username;        //store locally the client username

    private String gameSize;

    private String[] args;           //the args receive from the ClientMain

    /* create a cache of the Leader Card chosen by this client */
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


    public void start() {

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

                String gameMode = askGameMode();
                gameSize = gameMode;

                /* try to create the connection sending the username, port and ip */
                VConnectionRequestMsg request = new VConnectionRequestMsg("Connected",IP, 0, username,gameSize);
                client.sendMsg(request);

                // start client Thread ....

                connectionOFF = false;
            } catch (IOException e) {
                System.out.println(" Error, can't reach the server");
                connectionOFF = true;
            }
        }

    }

    private String askIPAddress(){
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
    private String askUsername(){

        /* preparing an Input scanner in order to take the Username from the client*/
        in = new Scanner(System.in);
        in.reset();

        String username = null;
        out.println("Please insert the Username that you want ");

        username = in.nextLine();

        return username;
    }

    /**
     * this method is used to specify the type of Game that the player want to play,
     * he Enter 0 if he wants to play the Solo game and 1 if he wants to play the Multiplayer Game
     * @return
     */
    private String askGameMode() {
        in = new Scanner(System.in);
        in.reset();

        String size = null;
        out.println(" Please enter which version of the Game you want to play : \n  ");
        out.println(" [Enter 0 for Solo mode, 1 for Multiplayer mode] \n  ");
        size = in.nextLine();

        while (!validGameMode(size)) {
            System.out.println("Error input not valid! Please insert 1 or 0 ");
            size = in.nextLine();
        }


        return size;
    }

    /**
     * auxiliary method to check if the GameMode is 0 (for solo player) or 1
     * @param size
     * @return
     */

    private boolean validGameMode(String size)
    {
        return size.equals("0") || size.equals("1");
    }


    /**
     * method to ask to the first Player in a Room the number of players he wants to play with,
     * in order to this number the lobby will create a room of that specific size
     * @return
     */
   private String askRoomSize(){

       in = new Scanner(System.in);
       in.reset();

       String numberOfPlayer = null;

       System.out.println(" Please insert the number of players you want to play with [2,3 or 4]");

       numberOfPlayer = in.nextLine();

       while (!validRoomSize(numberOfPlayer)) {
           System.out.println(" Invalid input, insert another one");
           numberOfPlayer = in.nextLine();
       }

       return numberOfPlayer;
       }

    /**
     * auxiliary method to check if the room size is 2,3 of 4
     * @param size
     * @return
     */
    private boolean validRoomSize(String size)
    {
        return size.equals("2") || size.equals("3") || size.equals("4");
    }


    /**
     * the Client has to choose two cards from the card list composed by four cards, so there will be two Arrays,
     * one composed by the two chosen Cards, and the other composed by the two that the player denied
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {

        // the two card Id chosen by the player
        Integer cardId1 = -1;
        Integer cardId2 = -1;

        boolean valid = false;  //boolean variable used to check if two card Id insert by the player are the same

        ArrayList<Integer> chosenCards = new ArrayList<>();
        ArrayList<Integer> deniedCards = new ArrayList<>();

        clearScreen();

        // show the four cards from which the player has to choose
        System.out.println(" There are the four Leader cards from which you have to choose : \n");
        for (Integer id: msg.getMiniDeckLeaderCardFour())
        {
            System.out.println(" Card number : " + id);
        }

        System.out.println(" Please, Choose the two Cards \n");
        System.out.println(" Press the Id numbers ");

        cardId1 = chooseIdCard();
        cardId2 = chooseIdCard();

        /* before checking if the Id are valid numbers (from 1 to 16), we have to be sure that
           the two cardId are different and represent two different cards ! Otherwise the player has to
           insert again the second Card Id
         */
        while(!valid) {

            if (cardId1.equals(cardId2)) {
                System.out.println(" Error, you can't choose two cards with the same Id number");
                System.out.println(" Please insert again the second card Id ! ");
                in = new Scanner(System.in);
                cardId2 = in.nextInt();
            }
            else valid = true;
        }

        while (!checkValidity(cardId1,cardId2,msg.getMiniDeckLeaderCardFour())){

            System.out.println(" Error, Id card not valid !! ");
            in = new Scanner(System.in);
            cardId1 = in.nextInt();
            cardId2 = in.nextInt();
        }

        System.out.println( "Good, you chose your cards ! ");

        chosenCards.add(cardId1);
        chosenCards.add(cardId2);

        /* put the remaining cards not chosen by the player in another ArrayList*/
        for (int card: msg.getMiniDeckLeaderCardFour()) {
            if (card != cardId1 && card != cardId2)
            {
                deniedCards.add(card);
            }
        }

        CChooseLeaderCardResponseMsg response = new CChooseLeaderCardResponseMsg(" chosen cards ",chosenCards,deniedCards,msg.getUsername());
        this.client.sendMsg(response);
    }

    /**
     * private method used only as an auxiliary method to let the Client choose from four different cardId
     * @return
     */
    private int chooseIdCard() {

        boolean validInput = false;
        int insert = -1;

        while (!validInput) {
            in = new Scanner(System.in);
            in.reset();

            try {
                insert = in.nextInt();
                validInput = true;

            } catch (IllegalArgumentException e) {
                System.out.println(" Error, input not valid, insert a valid One ");
            }

        }
        return insert;
    }

    /**
     * auxiliary method used only to check if the card Id selected by the Player
     * is contained in the four card
     * @param card1
     * @param card2
     * @param cardsId
     * @return
     */
    private boolean checkValidity (Integer card1, Integer card2, ArrayList<Integer> cardsId)
    {
        boolean firstCheck = false;
        boolean secondCheck = false;

        for (Integer cardID : cardsId){

            if(cardID.equals(card1))
            {
                firstCheck = true;     //boolean used to check if the first card is contained in the group
            }
            else if (cardID.equals(card2))
            {
                secondCheck = true;    //boolean used to check if the second card is contained in the group
            }

        }
        return firstCheck && secondCheck;     /*this returns true only if the two cards are contained inside the Array,
                                               but the two Id can't be the same number !! */
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
        for (Map.Entry<String,Integer> antagonistsCards: antagonistLeaderCards.entrySet())
        {
            System.out.println("Player : " + antagonistsCards.getKey() + " has card : " + antagonistsCards.getValue());
        }
    }

    /**
     * method that shows the position in their FaithTrack of all the players to others
     */
    public void showAntagonistPositions()
    {
        for (Map.Entry<String,Integer> positions : antagonistFaithMarkers.entrySet()) {
            System.out.println("Player : " + positions.getKey() + " is in position : " + positions.getValue());
        }
    }

    /**
     * method to print the client Leader Cards
     */
    public void showMyCards()
    {
        /* show every Leader card that the player has (max 2), every Leader Card is represented by a number
        from 1 to 16 that identifies the card */
        for (int card: myLeaderCards) {
            System.out.println("Your Card : " + card);
        }

    }

    /**
     * method that shows to the client / player his position in the faithTrack
     */
    public void showMyPosition(){
        System.out.println("Your position oh the track is : " + positionOnFaithTrack);
    }

}
