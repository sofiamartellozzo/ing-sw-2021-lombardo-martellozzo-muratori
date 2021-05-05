package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.view.display.CardSpaceDisplay;
import it.polimi.ingsw.view.display.FaithTrackDisplay;
import it.polimi.ingsw.view.display.MarketDisplay;
import it.polimi.ingsw.view.display.WriteMessageDisplay;

/**
 * CLI version of the View
 */
public class CLI extends Observable implements ViewObserver {

    private Scanner in;             //for input data from console
    private PrintStream out;        //for output data for console

    private ClientSocket client;    //client that view the cli
    private String username;        //store locally the client username
    private String iP;              //store locally the client IP

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
        iP = askIPAddress();

        /* Initialize client socket */
        client = new ClientSocket(iP);

        /* repeat this cycle until the connection go ON and the client reaches the server */
        while (connectionOFF) {
            try {
                client.beginConnection();               //open connection with the server
                System.out.println("Client Connected");
                clearScreen();

                 String user = null;
                 user = askUsername();
                 /* put inside the variable username the name that the client chose*/
                  username = user;


                String gameMode = askGameMode();
                gameSize = gameMode;

                /* try to create the connection sending the username, port and ip */
                VConnectionRequestMsg request = new VConnectionRequestMsg("Request Connection ",iP, 0, username,gameSize);
                client.sendMsg(request);

                // start client Thread ....
                new Thread(client).start();
                connectionOFF = false;

            } catch (IOException e) {
                System.out.println(" Error, can't reach the server ");
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
   private int askRoomSize(){

       in = new Scanner(System.in);
       in.reset();

       int numberOfPlayer = -1;


       numberOfPlayer = in.nextInt();

       while (!validRoomSize(numberOfPlayer)) {
           System.out.println(" Invalid input, insert another one");
           numberOfPlayer = in.nextInt();
       }

       return numberOfPlayer;
       }

    /**
     * auxiliary method to check if the room size is 2,3 of 4
     * @param size
     * @return
     */
    private boolean validRoomSize(int size)
    {
        return size == 2 || size == 3  || size == 4 ;
    }

    /**
     * auxiliary method used to check if the color inserted by the player is possible,
     * the color can be only YELLOW,PURPLE,BLUE and GREY
     * @return
     */
    private boolean checkColor(String color){

        return color.equals("YELLOW") || color.equals("PURPLE") || color.equals("BLUE")|| color.equals("GREY");
    }

    /**
     * auxiliary method used to check if the integer insert by the client representing a depot is valid,
     * it can be 1,2 or 3
     * @return
     */
    private boolean checkDepotValidity(int depot){
        return depot == 1 || depot == 2 || depot == 3;
    }

    /**
     * because the player is asked to insert a string that represents the color of the resource he wants,
     * this method converts the color written by the player to a real Color
     * @param resourceColor
     * @return
     */
    private Color getColorFromString(String resourceColor){
        switch (resourceColor){
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "GREY":
                return Color.GREY;
            case "PURPLE":
                return Color.PURPLE;
        }

        throw new IllegalArgumentException(" Error color not valid ");
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
     * auxiliary method that creates the TurnAction from the string inserted by the client
     * @param action
     */
    private TurnAction returnActionFromString(String action){

        switch (action) {
            case "end_turn": {
                return TurnAction.END_TURN;
            }
            case "buy_card": {
                return TurnAction.BUY_CARD;
            }
            case "buy_from_market": {
                return TurnAction.BUY_FROM_MARKET;
            }
            case "active_production_power": {
                return TurnAction.ACTIVE_PRODUCTION_POWER;
            }
            case "active_leader_cards": {
                return TurnAction.ACTIVE_LEADER_CARD;
            }
            case "get_action_token": {
                return TurnAction.GET_ACTION_TOKEN;
            }
            case "move_resource": {
                return TurnAction.MOVE_RESOURCE;
            }
            case "remove_leader_card": {
                return TurnAction.REMOVE_LEADER_CARD;
            }
            default:
                throw new IllegalArgumentException(" Error this action is not valid! ");
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(CNackConnectionRequestMsg msg) {

        String newUsername = null;
        switch (msg.getErrorInformation()){
            case "USER_NOT_VALID":  // if the username is already taken, the player has to insert a new one

                System.out.println( " Error, this username is not valid because it is already taken");
                newUsername = askUsername();
                break;

            case "FULL_SIZE":  //all the rooms in the server are full, so the client can't be connected to the game

                System.out.println(" Error, server is full ");
                break;

            case "WAIT":      //in this case the server is not full so there are new rooms available, and the client has to wait because someone is creating a new room
                System.out.println( " Someone is now creating a new room! Please wait a moment ");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

        username = newUsername;
        /* the login process has to restart, so the client try again sending another request */

        VConnectionRequestMsg request = new VConnectionRequestMsg("Trying to connect",iP,0,username,gameSize);
        this.client.sendMsg(request);
    }

    /**
     * msg received by the client to ask him the room size in which he wants to play,
     * than we send the answer to the controller
     * @param msg
     */
    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {

        int roomSize = -1;

        System.out.println(" Please insert the number of players you want to play with [2,3 or 4]");

        roomSize = askRoomSize();

        /* send the msg to the controller with the size room he chose */
        CRoomSizeResponseMsg response = new CRoomSizeResponseMsg(" asking the room size ",roomSize,msg.getUsername());
        client.sendMsg(response);
    }

    /**
     * this msg ask to the client which action he wants to play from the actions that he can do!!
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {

        System.out.println(msg.getMsgContent());
        System.out.println(" The action that you are allowed to do are: " +msg.getAvailableActions());
        boolean correct = false;

        in = new Scanner(System.in);
        in.reset();

        String action = in.nextLine();
        TurnAction turnAction = returnActionFromString(action);

        while (!correct) {
            if (msg.getAvailableActions().contains(turnAction)) {

                CChooseActionTurnResponseMsg response = new CChooseActionTurnResponseMsg(" I made my choice, I decided the action I want to do",username,turnAction);
                client.sendMsg(response);
                correct = true;
            }
        }
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
        for (Integer card: msg.getMiniDeckLeaderCardFour()) {
            if (!card.equals(cardId1) && card != cardId2)
            {
                deniedCards.add(card);
            }
        }

        CChooseLeaderCardResponseMsg response = new CChooseLeaderCardResponseMsg(" chosen cards ",chosenCards,deniedCards,msg.getUsername(), "firstChoose");
        this.client.sendMsg(response);
    }

    /**
     * this method is used to ask to the player to choose a specific type of resource and the depot where he wants to put it
     * @param msg
     */

    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {

        int depot = -1;
        String resourceColor = null;

        in = new Scanner(System.in);
        in.reset();


        System.out.println(" Please enter the color of resource you want : ");
        System.out.println(  "YELLOW --> COIN," +
                "PURPLE --> SERVANT," +
                "BLUE --> SHIELD," +
                "GREY --> STONE " );

        resourceColor = in.nextLine();

        // check if the color exist
        while(!checkColor(resourceColor)) {

            in = new Scanner(System.in);
            in.reset();

            System.out.println(" Error, please insert a valid color! ");
            resourceColor = in.nextLine();
        }

        System.out.println(" Please enter the depot where you want to put the resource : ");

        depot = in.nextInt();

        // check if the depot exist
        while (!checkDepotValidity(depot)) {

            in = new Scanner(System.in);
            in.reset();

            System.out.println( " Error depot int not valid, insert a new one (1,2 or 3 " );
            depot = in.nextInt();
        }

        /* create the color starting from the string written by the player,
           with the function toUpperCase we are sure that the input of the player will be in an upperCase mode */
        Color resColor = getColorFromString(resourceColor.toUpperCase());

        CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg(" resource and depot chosen ",resColor,depot,msg.getUsername());
        client.sendMsg(response);

    }


    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {

        System.out.println(msg.getMsgContent());
        if(msg.getUsernameIncreased().equals(username)){
            System.out.println("Congratulations, your faith Marker increased his position "+ msg.getNumberOfPositionIncreased());
        }
        else if(msg.getAllPlayerToNotify().contains(username)){
            System.out.println(msg.getUsernameIncreased()+"Increased his position of: "+msg.getNumberOfPositionIncreased());
        }

    }

    /**
     * message received from the client when the controller has to warn him that before he had inserted a wrong
     * or unavailable depot, so the client has to insert a new one and send it to the controller
     * @param msg
     */
    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {

        System.out.println(msg.getMsgContent());
        System.out.println("You can't put in Depot "+msg.getUnableDepot()+" the resource: ");
        //System.out.print(new Resource(msg.getResource()).toString());
        System.out.println(" Please insert a new depot for this resource [number from 1 to 5] ");
        in = new Scanner(System.in);
        in.reset();

        int newDepot = in.nextInt();
        CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg("I made my choice!",msg.getReource(),newDepot,username);
        client.sendMsg(response);

    }

    /**
     * msg used to ask to the client which development card he wants to chose from the table and in
     * which space he wants to put the card,the table indicates the available cards that he can buy
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {

        boolean correct = false;
        if(msg.getUsername().equals(username)) {

            System.out.println(msg.getMsgContent());

            in = new Scanner(System.in);
            in.reset();

            System.out.println(" Insert a row [from 0 to 2] and a column [from 0 to 3] in the table from where you want to take the card ");
            int row = in.nextInt();
            int column = in.nextInt();

            System.out.println(" Insert in which card Space you want to insert it ");
            int cardSpace = in.nextInt();

            while(!correct) {

                if (msg.getCardAvailable()[row][column] && (cardSpace== 1 || cardSpace == 2 || cardSpace == 3)) {

                    CBuyDevelopCardResponseMsg response = new CBuyDevelopCardResponseMsg(" I made my choice, I want this development card ", username, row, column, cardSpace);
                    client.sendMsg(response);
                    correct = true;
                }
            }

        }
    }

    /**
     * msg sent from the client to indicate the depot from where he wants to take the resource
     * and the depot in which he wants to put it
     * @param msg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

        boolean correct = false;
        while(!correct) {
            if (msg.getUsername().equals(username)) {

                System.out.println(msg.getMsgContent());
                in = new Scanner(System.in);
                in.reset();

                int fromDepot = in.nextInt();
                int toDepot = in.nextInt();

                if (msg.getDepotsActualSituation().containsKey(fromDepot) && msg.getDepotsActualSituation().containsKey(toDepot)) {
                    CMoveResourceInfoMsg response = new CMoveResourceInfoMsg(" I choose from where and to where I want to put my resource ", username, fromDepot, toDepot);
                    client.sendMsg(response);
                    correct = true;

                }

            }
        }
    }

    /**
     * with that message the player has to choose if he wants to take from a row or a column (choice) and from which one (number)
     * @param msg
     */

    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {

        System.out.println(msg.getMsgContent());

        in = new Scanner(System.in);
        in.reset();

        boolean correct = false;

        String choice = in.nextLine();
        int number = in.nextInt();

        while (!correct) {

            if ((choice.equals("row") && number < 4) || (choice.equals("column") && number < 5)) {

                CBuyFromMarketInfoMsg response = new CBuyFromMarketInfoMsg(" I chose the row/column that I want to take from the market ",username,choice,number);
                client.sendMsg(response);
                correct = true;
            }
        }
    }

    /**
     * this method displays to the players if they won or not
     * @param msg
     */
    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        clearScreen();

        if(msg.getWinnerUsername().contains(username)){
            WriteMessageDisplay.declareWinner();
            System.out.println(" You totalize "+msg.getVictoryPoints()+ " points" );
        }
        else{
            WriteMessageDisplay.endGame();
            WriteMessageDisplay.declareLoser();
        }

        in.reset();
        out.flush();
    }


    /**
     * method to clear the screen and remove older prints
     */
    public static void clearScreen(){
        System.out.println("reset and clear the screen");
        System.out.println("\033[H\033[2J");  //H is for go back to the top and 2J is for clean the screen
        System.out.flush();
    }

    /*--------------------------------------------------------------------------------------------------------------------------------*/

    private void showFaithTrack(FaithTrack faithTrack){
        FaithTrackDisplay faithT = new FaithTrackDisplay();
        //faithT.showFaithTrack();
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
