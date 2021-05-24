package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MessageHandler;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Color;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.actionAbility.CardActionAbility;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.card.LeaderCardDeck;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.viewMsg.VChooseLeaderCardRequestMsg;
import it.polimi.ingsw.utility.AnsiColors;
import it.polimi.ingsw.view.display.*;
import it.polimi.ingsw.view.display.FaithTrackDisplay;
import it.polimi.ingsw.view.display.WriteMessageDisplay;

/**
 * CLI version of the View
 */
public class CLI extends Observable implements ViewObserver {

    private String[] args;           //the args receive from the ClientMain

    private Scanner in;             //for input data from console
    private PrintStream out;        //for output data for console

    private ClientSocket client;    //client that view the cli
    private String username;        //store locally the client username
    private String iP;              //store locally the client IP
    private boolean connectionOFF;

    private String gameSize;

    private boolean offline;        //set true if client ask to play in offline Mode (=> SOLO)
    private VirtualView offlineVirtualView;
    private MessageHandler messageHandler;  //the message queue for offline mode


    /*-------------------------------------------------------------------------------------*/

    //local variables used to save locally the dates about a player and his game space

    private Boolean soloMode;
    private BoardManager boardManager;
    private MarketStructure marketStructureData; //contains the data about the market
    private PlayerInterface player;
    private LeaderCardDeck leaderCards;
    private DevelopmentCardTable developmentCardTable;
    private StrongBox strongBox;
    private Warehouse warehouse;
    private FaithTrack faithTrack;
    private ArrayList<CardSpace> cardSpaces;
    private ArrayList<TurnAction> possibleActions;   //contains the updated actions that a player can make

    /*-------------------------------------------------------------------------------------*/

    /* create a cache of the Leader Card chosen by this client */
    private List<Integer> myLeaderCards;
    /* local info about the client position of faith marker */
    private int positionOnFaithTrack;

    /* create a cache containing the LCards of the other players */
    private Map<String, Integer> antagonistLeaderCards;
    /* local info of opponent position on Fait Track */
    private Map<String, Integer> antagonistFaithMarkers;

    public CLI(String[] args) {

        out = System.out;
        in = new Scanner(System.in);
        this.args = args.clone();
        connectionOFF = false;

    }


    public void start() {

        connectionOFF = true;

        /* start class and visualized the Game Title */
        WriteMessageDisplay.writeTitle();

        /* ask the client to press enter button */
        printCLIMessage("Press enter to start...");
        in.nextLine();

        /* clear the screen to start a new game */
        clearScreen();

        /* set up the client info */

        //ask if the client wants to play offline
        offline = askTypeConnection();

        if (!offline) {
            /*the client choose to play online*/

            //first ask the IP for the connection
            iP = askIPAddress();

            /* Initialize client socket */
            client = new ClientSocket(iP, this);


            /* repeat this cycle until the connection go ON and the client reaches the server */
            while (connectionOFF) {
                try {
                    client.beginConnection();               //open connection with the server

                    if (!connectionOFF) {
                        //the connection didn't worked
                        break;
                    }
                    WriteMessageDisplay.writeOnlineStatus();
                    clearScreen();

                    String user = null;
                    user = askUsername();
                    /* put inside the variable username the name that the client chose*/
                    username = user;


                    String gameMode = askGameMode();
                    gameSize = gameMode;

                    /* try to create the connection sending the username, port and ip */
                    VVConnectionRequestMsg request = new VVConnectionRequestMsg("Request Connection ", iP, 0, username, gameSize);
                    //client.sendMsg(request);
                    sendMsg(request);

                    // start client Thread ....
                    new Thread(client).start();
                    connectionOFF = false;

                } catch (IOException e) {
                    printCLIMessage("⚠️ Error, can't reach the server ");
                    connectionOFF = true;
                }
            }
        }
        else{
            //write offline status!!!!
            WriteMessageDisplay.writeOfflineStatus();
            /*ask to set the username*/
            String user = null;
            user = askUsername();
            /* put inside the variable username the name that the client chose*/
            username = user;
            /*create the message handler, he work as Client Socket but not throw the net*/
            messageHandler = new MessageHandler();
            /*generate a sort of Virtual View*/
            messageHandler.generateVV(username);
            /*
            offlineVirtualView = new VirtualView(username);
            attachObserver(ObserverType.VIEW, offlineVirtualView);
            attachObserver(ObserverType.CONTROLLER, offlineVirtualView);*/
            //this.offlineVirtualView.attachObserver(ObserverType.VIEW, this);
            messageHandler.attachObserver(ObserverType.VIEW, this);
            //printCLIMessage("before run");
            new Thread(messageHandler).start();
            //printCLIMessage("after run");
            /* try to create the connection sending the username, port and ip */
            VVConnectionRequestMsg request = new VVConnectionRequestMsg("OFFLINE", username);
            sendMsg(request);
        }

    }

    /**
     * ask the client if want to play offline or online
     *
     * @return client choice, [true == Offline]
     */
    private boolean askTypeConnection() {
        printCLIMessage("type [offline] or [online] to choose your game mode");
        printCLIMessage("🚨Remember: if you choose to play OFFLINE the game will be in Solo Mode!");

        in = new Scanner(System.in);
        in.reset();
        String typeConnection = in.nextLine().toUpperCase();

        boolean getInput = false;
        while (!getInput) {
            if (typeConnection.equals("OFFLINE")) {
                getInput = true;
                return true;
            } else if (typeConnection.equals("ONLINE")) {
                getInput = true;
                return false;
            } else {
                printCLIMessage("⚠️You type it wrong, please insert the right choice");
                typeConnection = in.nextLine().toUpperCase();
            }
        }

        //default
        return false;
    }

    private String askIPAddress() {
        printCLIMessage("Please insert the Ip address:\n");
        printCLIMessage("Press ENTER for local setup");
        String ip = in.nextLine();
        /* first check if localhost is asked */
        if (ip.equals("")) {
            ip = "127.0.0.1"; //localhost, ip referred to server on client computer
        }
        /* check if is the right ip*/
        else {
            boolean checking = true;
            while (checking) {
                if (ip == null || ip.length() < 7 || ip.length() > 15) {
                    printCLIMessage("⚠️ Error: ip not valid!\n  Please insert another ip:");
                    ip = in.nextLine();
                } else {
                    checking = false;
                }
            }
        }
        return ip;
    }


    /**
     * method to ask the player its Username, and check if it is valid
     */
    private String askUsername() {

        boolean correct = false;
        /* preparing an Input scanner in order to take the Username from the client*/
        in = new Scanner(System.in);
        in.reset();

        String username = null;
        out.println("Please insert the Username that you want ");

        while (!correct) {

            username = in.nextLine();
            // if the player insert a Tab as username this can't be possible
            if (username.length() < 1) {
                System.out.println("!! Invalid Usernam, insert a new one ");

            }else{
                correct = true;
            }
        }

        return username;
    }

    /**
     * this method is used to specify the type of Game that the player want to play,
     * he Enter 0 if he wants to play the Solo game and 1 if he wants to play the Multiplayer Game
     *
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
            printCLIMessage("Error input not valid! Please insert 1 or 0 ");
            size = in.nextLine();
        }


        return size;
    }

    private void sendMsg(GameMsg msg){
        if (!offline){
            //send it throw the net
            client.sendMsg(msg);
        }
        else{
            //send to the VV
            //offlineVirtualView.notifyAllObserver(ObserverType.CONTROLLER,msg);
            //msg.notifyHandler((ControllerObserver) offlineVirtualView);
            //notifyAllObserver(ObserverType.CONTROLLER, msg);
            messageHandler.receiveMsgForVV(msg);
        }
    }

    /**
     * auxiliary method to check if the GameMode is 0 (for solo player) or 1
     *
     * @param size
     * @return
     */

    private boolean validGameMode(String size) {
        return size.equals("0") || size.equals("1");
    }


    /**
     * method to ask to the first Player in a Room the number of players he wants to play with,
     * in order to this number the lobby will create a room of that specific size
     *
     * @return
     */
    private int askRoomSize() {

        boolean correctInput = false;
        in = new Scanner(System.in);
        in.reset();

        int numberOfPlayer = -1;

        while (!correctInput) {

            try {
                numberOfPlayer = in.nextInt();
                correctInput = true;
            } catch (InputMismatchException eio) {
                printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                in.nextLine();
            }
        }


        while (!validRoomSize(numberOfPlayer)) {
            printCLIMessage("Invalid input, insert another one");
            numberOfPlayer = in.nextInt();
        }

        return numberOfPlayer;
    }

    /**
     * auxiliary method to check if the room size is 2,3 of 4
     *
     * @param size
     * @return
     */
    private boolean validRoomSize(int size) {
        return size == 2 || size == 3 || size == 4;
    }

    /**
     * auxiliary method used to check if the color inserted by the player is possible,
     * the color can be only YELLOW,PURPLE,BLUE and GREY
     *
     * @return
     */
    private boolean checkColor(String color) {

        return color.equals("YELLOW") || color.equals("PURPLE") || color.equals("BLUE") || color.equals("GREY");
    }

    /**
     * auxiliary method used to check if the typeResource inserted is valid
     *
     * @param typeResource
     * @return
     */
    private boolean checkType(String typeResource) {
        return typeResource.equals("SHIELD") || typeResource.equals("COIN") || typeResource.equals("STONE") || typeResource.equals("SERVANT");
    }

    /**
     * auxiliary method used to check if the integer insert by the client representing a depot is valid,
     * it can be 1,2 or 3
     *
     * @return
     */
    private boolean checkDepotValidity(int depot) {
        return depot == 1 || depot == 2 || depot == 3 || depot == 4 || depot == 5;
    }

    /**
     * because the player is asked to insert a string that represents the color of the resource he wants,
     * this method converts the color written by the player to a real Color
     *
     * @param resourceColor
     * @return
     */
    private Color getColorFromString(String resourceColor) {
        switch (resourceColor) {
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "GREY":
                return Color.GREY;
            case "PURPLE":
                return Color.PURPLE;
        }

        throw new IllegalArgumentException(" Error, color not valid ");
    }

    /**
     * auxiliary method that creates the color basing on the type
     *
     * @param typeRes
     * @return
     */
    private Color getColorFromType(String typeRes) {
        switch (typeRes) {
            case "SHIELD":
                return Color.BLUE;
            case "COIN":
                return Color.YELLOW;
            case "SERVANT":
                return Color.PURPLE;
            case "STONE":
                return Color.GREY;
        }
        throw new IllegalArgumentException("Error, type not valid!");
    }

    /**
     * auxiliary method used to create the type from the string
     *
     * @param type
     * @return
     */
    private TypeResource getTypeFromString(String type) {

        switch (type) {
            case "COIN":
                return TypeResource.COIN;
            case "SHIELD":
                return TypeResource.SHIELD;
            case "STONE":
                return TypeResource.STONE;
            case "SERVANT":
                return TypeResource.SERVANT;
        }
        throw new IllegalArgumentException("Error, type not valid!");
    }

    /**
     * auxiliary method that creates the resource basing on the color
     *
     * @param color
     * @return
     */
    private Resource getResourceFromColor(Color color) {
        switch (color) {
            case YELLOW:
                return new Resource(Color.YELLOW);
            case BLUE:
                return new Resource(Color.BLUE);
            case GREY:
                return new Resource(Color.GREY);
            case PURPLE:
                return new Resource(Color.PURPLE);
        }
        throw new IllegalArgumentException("Error, color not valid! ");
    }

    /**
     * private method used only as an auxiliary method to let the Client choose from four different cardId
     *
     * @return
     */
    private int chooseIdCard() {

        boolean validInput = false;
        int insert = -1;

        while (!validInput) {

            try {
                in = new Scanner(System.in);
                in.reset();

                insert = in.nextInt();
                validInput = true;
            } catch (InputMismatchException eio) {
                printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                in.nextLine();
            }

        }
        return insert;
    }

    /**
     * auxiliary method used only to check if the card Id selected by the Player
     * is contained in the four card
     *
     * @param card1
     * @param card2
     * @param cardsId
     * @return
     */
    private boolean checkValidity(Integer card1, Integer card2, ArrayList<Integer> cardsId) {
        boolean firstCheck = false;
        boolean secondCheck = false;

        for (Integer cardID : cardsId) {

            if (cardID.equals(card1)) {
                firstCheck = true;     //boolean used to check if the first card is contained in the group
            } else if (cardID.equals(card2)) {
                secondCheck = true;    //boolean used to check if the second card is contained in the group
            }

        }
        return firstCheck && secondCheck;     /*this returns true only if the two cards are contained inside the Array,
                                               but the two Id can't be the same number !! */
    }

    /**
     * auxiliary method that creates the TurnAction from the string inserted by the client
     *
     * @param action
     */
    private TurnAction returnActionFromString(String action) {

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
            case "active_leader_card": {
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
            case "see_other_player": {
                return TurnAction.SEE_OTHER_PLAYER;
            }
            default:
                return TurnAction.ERROR;
        }
    }

    /**
     * method used to show all the name inside a Room
     *
     * @param players
     */
    private void showPlayersInRoom(ArrayList<String> players, int actualNumberPlayers, int roomsize) {
        printCLIMessage("Room Size: " + roomsize);
        printCLIMessage("Actually: " + actualNumberPlayers + "/" + roomsize);

        for (String player : players) {
            printCLIMessage(AnsiColors.RED_BOLD + "Player " + AnsiColors.RESET + player.toUpperCase());
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(VNackConnectionRequestMsg msg) {

        String newUsername = null;
        switch (msg.getErrorInformation()) {
            case "USER_NOT_VALID":  // if the username is already taken, the player has to insert a new one

                System.out.println(" Error, this username is not valid because it is already taken");
                newUsername = askUsername();
                username = newUsername;
                /* the login process has to restart, so the client try again sending another request */
                VVConnectionRequestMsg request = new VVConnectionRequestMsg("Trying to connect", iP, 0, username, gameSize);
                //client.sendMsg(request);
                sendMsg(request);
                break;

            case "FULL_SIZE":  //all the rooms in the server are full, so the client can't be connected to the game

                printCLIMessage(" Error, server is full ");
                break;

            case "WAIT":
                //in this case the server is not full so there are new rooms available, and the client has to wait because someone is creating a new room
                printCLIMessage(" Someone is now creating a new room! Please wait a moment ");
                try {
                    Thread.sleep(5000);
                    /* the login process has to restart, so the client try again sending another request */
                    VVConnectionRequestMsg request2 = new VVConnectionRequestMsg("Trying to connect", iP, 0, username, gameSize);
                    //client.sendMsg(request2);
                    sendMsg(request2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    /**
     * msg received by the client to ask him the room size in which he wants to play,
     * than we send the answer to the controller
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {

        printCLIMessage("setting size room in CLI");
        int roomSize = -1;

        printCLIMessage(" Please insert the number of players you want to play with [2,3 or 4]");

        roomSize = askRoomSize();

        /* send the msg to the controller with the size room he chose */
        CRoomSizeResponseMsg response = new CRoomSizeResponseMsg(" asking the room size ", roomSize, msg.getUsername(), msg.getRoomID());
        //client.sendMsg(response);
        sendMsg(response);
    }

    /**
     * this msg is sent from the Room and contains all the data about the player and the board Manager
     *
     * @param msg contains all the info about tha player just initialized
     */
    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {

        soloMode = msg.isSoloMode();
        player = msg.getPlayer();
        boardManager = msg.getBoardManager();
        marketStructureData = msg.getBoardManager().getMarketStructure();
        leaderCards = msg.getBoardManager().getLeaderCardDeck();
        developmentCardTable = msg.getBoardManager().getDevelopmentCardTable();
        warehouse = msg.getPlayer().getGameSpace().getResourceManager().getWarehouse();
        strongBox = msg.getPlayer().getGameSpace().getResourceManager().getStrongBox();
        faithTrack = msg.getPlayer().getGameSpace().getFaithTrack();
        cardSpaces = msg.getPlayer().getGameSpace().getCardSpaces();
    }


    /**
     * msg used to tell to the client if he has to wait because the room isn't completed
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VRoomInfoMsg msg) {
        clearScreen();
        printCLIMessage(AnsiColors.ANSI_CYAN + "THE ROOM HAS BEEN CREATED! " + AnsiColors.RESET);
        printCLIMessage("you have been added in it!");
        ArrayList<String> playersInside = msg.getPlayersId();
        showPlayersInRoom(playersInside, msg.getNumberOfPlayers(), msg.getSize());

        if (msg.getSize() == msg.getNumberOfPlayers()) {
            printCLIMessage("The Game can start! ");
        } else {

            printCLIMessage("⌛⌛⌛ WAITING FOR PARTICIPANTS ⌛⌛⌛");
            System.out.print("\n");
        }
    }

    /**
     * this msg ask to the client which action he wants to play from the actions that he can do!!
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {

        if (msg.getUsername().equals(username)) {
            possibleActions = msg.getAvailableActions();

            printCLIMessage(msg.getMsgContent());
            printCLIMessage(" The actions that you are allowed to do are: " + msg.getAvailableActions());
            printCLIMessage(" Write the action you chose with _ between every word!! ");
            boolean correct = false;

            in = new Scanner(System.in);
            in.reset();

            String action = in.nextLine();
            TurnAction turnAction = returnActionFromString(action.toLowerCase());

            if (msg.getAvailableActions().contains(turnAction) && turnAction != TurnAction.ERROR) {

                CChooseActionTurnResponseMsg response = new CChooseActionTurnResponseMsg(" I made my choice, I decided the action I want to do", username, turnAction);
                //client.sendMsg(response);
                sendMsg(response);

            } else {
                VChooseActionTurnRequestMsg askAgain = new VChooseActionTurnRequestMsg("choose again an action", msg.getUsername(), msg.getAvailableActions());
                this.receiveMsg(askAgain);
            }
        }
    }

    @Override
    public void receiveMsg(VWaitYourTurnMsg msg) {
        WriteMessageDisplay.writeWaitingStatus();
    }

    /**
     * the Client has to choose two cards from the card list composed by four cards, so there will be two Arrays,
     * one composed by the two chosen Cards, and the other composed by the two that the player denied
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {


        // the two card Id chosen by the player
        Integer cardId1 = -1;
        Integer cardId2 = -1;
        in = new Scanner(System.in);


        if (msg.getWhatFor().equals("initialization")) {


            boolean valid = false;  //boolean variable used to check if two card Id insert by the player are the same

            ArrayList<Integer> chosenCards = new ArrayList<>();
            ArrayList<Integer> deniedCards = new ArrayList<>();

            clearScreen();

            // show the four cards from which the player has to choose
            printCLIMessage(" There are the four Leader cards from which you have to choose : \n");
            for (Integer id : msg.getMiniDeckLeaderCardFour()) {

                System.out.print(leaderCards.getLeaderCardById(id).toString());

            }

            printCLIMessage(" Please, Choose the two Cards \n");
            printCLIMessage(" Press the Id numbers ");

            in = new Scanner(System.in);
            in.reset();

            cardId1 = chooseIdCard();
            cardId2 = chooseIdCard();

                /* before checking if the Id are valid numbers (from 1 to 16), we have to be sure that
                the two cardId are different and represent two different cards ! Otherwise the player has to
                insert again the second Card Id
                */
            while (!valid) {


                if (cardId1.equals(cardId2)) {
                    printCLIMessage(" Error, you can't choose two cards with the same Id number");
                    printCLIMessage(" Please insert again the second card Id ! ");

                    try {
                        cardId2 = in.nextInt();
                    } catch (InputMismatchException eio) {
                        printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                        in.nextLine();
                    }

                } else
                    valid = true;
            }

            while (!checkValidity(cardId1, cardId2, msg.getMiniDeckLeaderCardFour())) {

                printCLIMessage(" Error, Id card not valid !! ");
                in = new Scanner(System.in);
                in.reset();

                try {
                    cardId1 = in.nextInt();
                    cardId2 = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }

            }

            printCLIMessage("Good, you chose your cards ! ");

            chosenCards.add(cardId1);
            chosenCards.add(cardId2);

            /* put the remaining cards not chosen by the player in another ArrayList*/
            for (Integer card : msg.getMiniDeckLeaderCardFour()) {
                if (!card.equals(cardId1) && card != cardId2) {
                    deniedCards.add(card);
                }
            }

            CChooseLeaderCardResponseMsg response = new CChooseLeaderCardResponseMsg(" chosen cards ", chosenCards, msg.getUsername(), "firstChoose");
            //client.sendMsg(response);
            sendMsg(response);
        } else {
            //discard or activate
            if (!msg.getMiniDeckLeaderCardFour().isEmpty()) {
                printCLIMessage("Choose which card you want to \"" + msg.getWhatFor() + "\"  from:");
                for (Integer i : msg.getMiniDeckLeaderCardFour()) {
                    System.out.println(i);
                }
                in = new Scanner(System.in);
                in.reset();

                int cardToRemoveOrActivate = in.nextInt();
                CChooseLeaderCardResponseMsg response2 = new CChooseLeaderCardResponseMsg(" chosen cards ", cardToRemoveOrActivate, msg.getUsername(), msg.getWhatFor());
                sendMsg(response2);

            } else {
                printCLIMessage("Sorry you cannot " + msg.getWhatFor() + " any Leader Card!");
                //send a msg to change the action
                CChangeActionTurnMsg change = new CChangeActionTurnMsg("you have to change the Action of this turn", msg.getUsername(), TurnAction.ACTIVE_LEADER_CARD);
                sendMsg(change);
            }
        }
    }

    @Override
    public void receiveMsg(VWaitOtherPlayerInitMsg msg) {
        WriteMessageDisplay.writeWaitOtherPlayers();
    }

    /**
     * this method is used to ask to the player to choose a specific type of resource and the depot where he wants to put it
     * or if the White Marble Ability is activated, he has to chose from the two resources type available
     *
     * @param msg
     */

    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {

        int depot = -1;
        int choice = -1;
        String resourceColor = null;
        String resourceType = null;
        Color typeColor = null;
        Color resColor = null;
        boolean correctInput = false;     //boolean used to check if the exception is thrown and so the client can't store the resources in a specific depot chosen

        in = new Scanner(System.in);
        in.reset();

        if (msg.getUsername().equals(username)) {

            printCLIMessage(msg.getMsgContent());
            //System.out.println(" Here is your current Warehouse's situation ");
            //showWarehouse(warehouse, player);

            printCLIMessage(" If you want to discard the resource digit 0, otherwise if you want to keep it digit 1! \uD83D\uDE00" + AnsiColors.RESET);
            in = new Scanner(System.in);
            in.reset();

            while (!correctInput) {

                try {
                    choice = in.nextInt();
                    correctInput = true;
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }
            }

            if (choice == 1) {    //if he chooses to keep the resource he will be asked info about which one he wants and where putting it
                if (msg.getChoices() == null) {
                    printCLIMessage("Please enter the color of the resource you want : ");
                    System.out.println("YELLOW --> COIN 💰\n" +
                            "PURPLE --> SERVANT 👾\n" +
                            "BLUE --> SHIELD 🥏\n" +
                            "GREY --> STONE 🗿\n");

                    in = new Scanner(System.in);
                    in.reset();
                    resourceColor = in.nextLine().toUpperCase();

                    // check if the color exist
                    while (!checkColor(resourceColor)) {

                        printCLIMessage(" Error, please insert a valid color! ");
                        resourceColor = in.nextLine();
                    }

                /* create the color starting from the string written by the player,
                with the function toUpperCase we are sure that the input of the player will be in an upperCase mode */
                    resColor = getColorFromString(resourceColor.toUpperCase());

                } else {
                    // if he has to chose from specific type because the White Marble ability is activated
                    System.out.println(" You can choose from one of these: ");
                    for (TypeResource typeResource : msg.getChoices()) {
                        System.out.print(typeResource.toString() + "\n");
                    }

                    in = new Scanner(System.in);
                    in.reset();
                    resourceType = in.nextLine();
                    boolean correct = false;

                    while (!correct) {
                        for (TypeResource type : msg.getChoices()) {
                            if (type.toString().equals(resourceType.toUpperCase())) {
                                correct = true;
                            }
                        }
                        if (!correct) {
                            in.reset();

                            in = new Scanner(System.in);
                            in.reset();
                            printCLIMessage(" Error, please insert a valid Type! ");
                            resourceType = in.nextLine();
                        }

                    }

                    /*creates the color of the resource basing on the Type of it, written by the player*/
                    typeColor = getColorFromType(resourceType.toUpperCase());

                }

                //check if the exception is thrown and has to insert a new depot

                printCLIMessage("Please enter the depot where you want to put the resource : ");
                System.out.println("1 --> DEPOT1\n" +
                        "2 --> DEPOT2\n" +
                        "3 --> DEPOT3\n");

                in = new Scanner(System.in);
                in.reset();

                try {
                    depot = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }


                // check if the depot exist
                while (!checkDepotValidity(depot)) {
                    in.reset();

                    System.out.println(" ⚠️ Error depot int not valid, insert a new one (1,2,3,4,5) ");
                    depot = in.nextInt();
                }

                //send one of this two types of responses depending on the type of request
                CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg(" resource and depot chosen ", resColor, depot, msg.getUsername());
                sendMsg(response);

            }


        } else {
            CChooseDiscardResourceMsg response = new CChooseDiscardResourceMsg("I chose to discard this resource", username);
            sendMsg(response);
        }
    }

    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {
        warehouse = msg.getWarehouse();
        //System.out.println(" Here is your Warehouse updated ");
        //showWarehouse(warehouse, player);
    }


    /**
     * msg used to notify to all the game Player that a player increased his position of NumberOfPositionIncreased positions
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {

        printCLIMessage(msg.getMsgContent());

        if (msg.getUsernameIncreased().equals(username)) {
            printCLIMessage("Congratulations, your faith Marker increased his position of " + msg.getNumberOfPositionIncreased() + " position!");
            // locally increasing the position of the faith Marker
            faithTrack.getFaithMarker().increasePosition();
            showFaithTrack(faithTrack);

        } else if (msg.getAllPlayerToNotify().contains(username)) {
            System.out.println(AnsiColors.YELLOW_BOLD + msg.getUsernameIncreased() + AnsiColors.RESET + " increased his position of: " + msg.getNumberOfPositionIncreased() + " position");
        }

    }

    /**
     * message received from the client when the controller has to warn him that before he had inserted a wrong
     * or unavailable depot, so the client has to insert a new one and send it to the controller
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {

        if (msg.getUsername().equals(username)) {
            int newDepot = 0;

            printCLIMessage(msg.getMsgContent());
            printCLIMessage("You can't put in Depot " + msg.getUnableDepot() + "the resource (identified by color) --> " + msg.getResourceChooseBefore() + " that you choose before! ");

            printCLIMessage("Here is your actual situation! ");
            showWarehouse(warehouse);

            System.out.print("\n");
            printCLIMessage(" Please insert a new depot for this resource [number from 1 to 5] ");
            in = new Scanner(System.in);
            in.reset();

            try {
                newDepot = in.nextInt();
            } catch (InputMismatchException eio) {
                printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                in.nextLine();
            }

            CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg("I made my choice!", msg.getResourceChooseBefore(), newDepot, username);
            sendMsg(response);
        }

    }

    /**
     * msg used to ask to the client which development card he wants to chose from the table and in
     * which space he wants to put the card,the table indicates the available cards that he can buy
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {

        int row = 0;
        int column = 0;
        int cardSpace = 0;
        int cardSpace1 = 0;
        boolean correct = false;
        int countNotAvailable = 0;
        if (msg.getUsername().equals(username)) {

            developmentCardTable = msg.getTableCard();
            printCLIMessage(msg.getMsgContent());

            in = new Scanner(System.in);
            in.reset();

            showStrongBox(strongBox);
            System.out.println("\n");
            showDevelopmentCardTable(developmentCardTable, msg.getCardAvailable());
            System.out.println("\n");
            showCardSpaces(cardSpaces);
            System.out.println("\n");

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (!msg.getCardAvailable()[i][j]) {
                        countNotAvailable++;
                    }
                }
            }

            if (countNotAvailable < 12) {      // if is < 12 it means that there is at least 1 card that a player can buy
                printCLIMessage(" If a card position is RED it means that you haven't got enough resources to pay it!" +
                        " So, you can't buy it!" + AnsiColors.YELLOW_BOLD + "\t⚠");
                printCLIMessage(" Insert a row [from 0 to 2] and a column [from 0 to 3] in the table from where you want to take the card ");

                in = new Scanner(System.in);
                in.reset();

                try {
                    row = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }
                try {
                    column = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }

                printCLIMessage(" Insert in which card Space you want to insert it [1,2 or 3] ");
                try {
                    cardSpace1 = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }

                cardSpace = cardSpace1-1;

                while (!correct) {

                    //check if the deck selected is not empty (and has at least one card) and if the client inserted a valid card space
                    if (!(msg.getTableCard().getTable()[row][column].getDevelopDeck().isEmpty()) && (cardSpace == 0 || cardSpace == 1 || cardSpace == 2)) {
                        if (msg.getCardAvailable()[row][column]) { //if the player can buy the card in that position of the table

                            //to update the local version of the variable of the client
                            //developmentCardTable.takeCard(row,column);
                            cardSpaces.get(cardSpace).addCard(developmentCardTable.takeCard(row, column));
                            /*try {
                                strongBox.removeResources(developmentCardTable.getTable()[row][column].getDevelopDeck().get(developmentCardTable.getTable()[row][column].getDevelopDeck().size()).getCost());
                            } catch (InvalidActionException e) {
                                e.printStackTrace();
                            }*/

                            if (developmentCardTable.getTable()[row][column].getDevelopDeck().isEmpty()) {
                                msg.getCardAvailable()[row][column] = false;
                            }
                            //System.out.println("Here is the card Table Updated! ");
                            //showDevelopmentCardTable(developmentCardTable, msg.getCardAvailable());

                            //System.out.println("Here is your StrongBox Updated! ");
                            //showStrongBox(strongBox, player);

                            printCLIMessage("Here are your card spaces updated");
                            showCardSpaces(cardSpaces);

                            CBuyDevelopCardResponseMsg response = new CBuyDevelopCardResponseMsg(" I made my choice, I want this development card ", username, row, column, cardSpace);
                            sendMsg(response);
                            correct = true;
                        } else {
                            //if the player hasn't enough resources to buy the card
                            printCLIMessage(AnsiColors.RED_BOLD + "Error, you can't buy this card because you don't have enough resources! " + AnsiColors.RESET);
                        }
                    }
                }
            } else  //if all cards are not available he has to change the action he wants to play and send it to the controller
            {
                printCLIMessage(AnsiColors.RED_BOLD + " Any Development Card is available, so you have to change the action you want to play! " + AnsiColors.RESET);
                //System.out.println(" The actions you can choose from are: " + possibleActions);
                //System.out.println(" Write the action you chose with _ between every word! ");
                //in.reset();
                CChangeActionTurnMsg change = new CChangeActionTurnMsg("you have to change the Action of this turn", msg.getUsername(), TurnAction.BUY_CARD);
                sendMsg(change);
                //String action = in.nextLine();
                //TurnAction turnAction = returnActionFromString(action.toLowerCase());
                //send a msg to change the action
                //CChooseActionTurnResponseMsg request = new CChooseActionTurnResponseMsg("I chose another action ", username, turnAction);
                //this.client.sendMsg(request);
            }

        }
    }

    @Override
    public void receiveMsg(VUpdateDevTableMsg msg) {
        developmentCardTable = msg.getUpdateTable();
        if (msg.getUsername().equals(username)) {
            printCLIMessage(" That's the updated situation of the table of Develop cards ");
            //showDevelopmentCardTable();
        }
    }


    /**
     * msg sent from the client to indicate the depot from where he wants to take the resource
     * and the depot in which he wants to put it
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

        boolean correct = false;
        int fromDepot = 0;
        int toDepot = 0;

        while (!correct) {
            if (msg.getUsername().equals(username)) {

                printCLIMessage(msg.getMsgContent());
                in = new Scanner(System.in);
                in.reset();

                showWarehouse(warehouse);
                System.out.println(" Write the origin depot from where you want to move: ");

                try {
                    fromDepot = in.nextInt();
                } catch (InputMismatchException eio) {
                    System.out.println("ERRORE Puoi inserire solo numeri");
                    in.nextLine();
                }
                System.out.println(" Write the depot in which you want to move it ");

                try {
                    toDepot = in.nextInt();
                } catch (InputMismatchException eio) {
                    System.out.println("ERRORE Puoi inserire solo numeri");
                    in.nextLine();
                }

                if (msg.getDepotsActualSituation().containsKey(fromDepot) && msg.getDepotsActualSituation().containsKey(toDepot)) {

                    CMoveResourceInfoMsg response = new CMoveResourceInfoMsg(" I choose from where and to where I want to put my resource ", username, fromDepot, toDepot);
                    sendMsg(response);
                    correct = true;

                }

            }
        }
    }

    /**
     * with that message the player has to choose if he wants to take from a row or a column (choice) and from which one (number)
     *
     * @param msg
     */

    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {

        int number = 0;

        if (msg.getUsername().equals(username)) {
            printCLIMessage("enter in ask info for market");
            marketStructureData = msg.getMarket();

            printCLIMessage(msg.getMsgContent());

            in = new Scanner(System.in);
            in.reset();

            // show the player the current market situation
            showMarketStructure(marketStructureData);

            boolean valid = false;

            printCLIMessage(" Please insert if you want to choose a row or a column : ");
            String choice = in.nextLine();

            while (!valid) {   //check if the player inserted ROW or COLUMN
                if (choice.toLowerCase().equals("row") || choice.toLowerCase().equals("column")) {
                    valid = true;
                } else {
                    printCLIMessage(" Error invalid place, write another one");
                    in = new Scanner(System.in);
                    in.reset();
                    choice = in.nextLine();
                }
            }

            if (choice.toLowerCase().equals("row")) {
                printCLIMessage(" Please insert the number of the row that you want (1,2,3) ");
            } else {
                printCLIMessage(" Please insert the number of the column that you want (1,2,3,4) ");
            }
            try {
                number = in.nextInt();
            } catch (InputMismatchException eio) {
                printCLIMessage(" ⚠️ ERRORE : Puoi inserire solo numeri");
                in.nextLine();
            }

            boolean correct = false;
            while (!correct) {

                if ((choice.toLowerCase().equals("row") && number < 4 && number > 0) || (choice.toLowerCase().equals("column") && number < 5 && number > 0)) {

                    CBuyFromMarketInfoMsg response = new CBuyFromMarketInfoMsg(" I chose the row/column that I want to take from the market ", username, choice, number - 1);
                    sendMsg(response);
                    correct = true;
                } else {
                    printCLIMessage("Error, insert a valid number! ");
                    try {
                        number = in.nextInt();
                    } catch (InputMismatchException eio) {
                        printCLIMessage("⚠️ ERRORE : Puoi inserire solo numeri");
                        in.nextLine();
                    }
                }
            }
        }

    }

    @Override
    public void receiveMsg(VUpdateMarketMsg msg) {
        marketStructureData = msg.getMarketUpdate();
        if (msg.getUsername().equals(username)) {
            printCLIMessage(" That's the updated situation of your market! ");
            showMarketStructure(marketStructureData);
            showWarehouse(warehouse);
        }
    }

    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        //...
        int depot = 0;
        printCLIMessage("Choose where to store this resource: " + msg.getResourceToStore() + " if you want to discard it send 0");
        //showWarehouse(this.warehouse, this.player);
        in = new Scanner(System.in);
        in.reset();
        try {
            depot = in.nextInt();
        } catch (InputMismatchException eio) {
            printCLIMessage("⚠️ERRORE: Puoi inserire solo numeri");
            in.nextLine();
        }

        Color rC = msg.getResourceToStore().getThisColor();

        if (depot != 0) {
            CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg("the choice of the player for a resources recived", rC, depot, msg.getUsername());
            sendMsg(response);

        } else {
            CChooseDiscardResourceMsg discard = new CChooseDiscardResourceMsg("the player choose to discard the resources", msg.getUsername());
            sendMsg(discard);
        }

    }

    @Override
    public void receiveMsg(VLorenzoIncreasedMsg msg) {
        if (msg.getUsername().equals(username)) {
            player = msg.getPlayer();
            faithTrack = msg.getPlayer().getGameSpace().getFaithTrack();
            printCLIMessage(" Lorenzo increased his position in FT of " + msg.getNumberStep() + " step");
            showFaithTrack(faithTrack);
        }
    }

    /**
     * this msg is used to let the player choose between the production power that he wants to activate
     * <p>
     * availableCardSpace:
     * - 0     ---> base PP
     * - 1,2,3 ---> card Space's cards
     * - 4,5   ---> Special cards
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {

        ArrayList<TypeResource> resources = new ArrayList<>();
        boolean correct = false;
        boolean correctResource = false;
        int choice = 0;
        String choose1 = "";
        String choose2 = "";
        String where = null;
        String resourceToGet = null;
        in = new Scanner(System.in);
        in.reset();

        if (msg.getUsername().equals(username)) {

            if (!msg.getActivatablePP().isEmpty() && msg.getActivatablePP() != null) {   // if the player can activate at least 1 PP
                System.out.println(msg.getMsgContent());
                System.out.println("Available card Spaces from which you can choose:  " + msg.getActivatablePP() + "\n" +
                        "If you don't want to use others Production Powers please insert 9 !! ");

                in = new Scanner(System.in);
                in.reset();

                try {
                    choice = in.nextInt();
                } catch (InputMismatchException eio) {
                    printCLIMessage("⚠️ERROR: You can only insert numbers, type again");
                    in.nextLine();
                }

                if (msg.getActivatablePP().contains(choice)) {

                    in = new Scanner(System.in);
                    in.reset();
                    printCLIMessage("Insert from where you want to take the resources to pay the production (strongBox or wareHouse) ");
                    showStrongBox(strongBox);
                    showWarehouse(warehouse);
                    correct = false;

                    while (!correct) {
                        in = new Scanner(System.in);
                        in.reset();
                        where = in.nextLine();

                        if (where.toLowerCase().equals("warehouse") || where.toLowerCase().equals("strongbox")) {
                            correct = true;

                        } else {
                            printCLIMessage("Error this place is not valid! Write another one ");
                        }

                    }
                    //show to the client his situation of the warehouse/strongbox

                    if(where.toLowerCase().equals("warehouse")){
                        System.out.println("HERE IS YOUR WAREHOUSE! ");
                        showWarehouse(warehouse);
                    }else{
                        System.out.println("HERE IS YOUR STRONGBOX! ");
                        showStrongBox(strongBox);
                    }
                    if (choice == 0) {   //if he decided to activate the base production power

                        printCLIMessage("Good, you activated the base Production Power! ");
                        printCLIMessage("Choose the two resources that you want to pay to activate the PP! ");

                        in = new Scanner(System.in);
                        in.reset();

                        while(!correctResource) {

                            choose1 = in.nextLine();
                            choose2 = in.nextLine();

                            if(checkType(choose1) && checkType(choose2)){    // checking if the resources' Types are valid
                                correctResource = true;
                            }
                            else{
                                System.out.println("Error,Invalid Resources, write other Types!! ");
                            }
                        }

                        resources.add(getTypeFromString(choose1.toUpperCase()));
                        resources.add(getTypeFromString(choose2.toUpperCase()));

                        printCLIMessage("Insert the resource that you want, it will be put automatically in the StrongBox! ");
                        resourceToGet = in.nextLine();

                        CActivateProductionPowerResponseMsg response = new CActivateProductionPowerResponseMsg("I chose the base production power to activate", username, where, choice);
                        response.setResourcesToPay(resources);
                        response.setResourceToGet(getTypeFromString(resourceToGet.toUpperCase()));
                        sendMsg(response);

                    } else if (choice == 1 || choice == 2 || choice == 3) {

                        //if he chooses a normal card space
                        printCLIMessage("Good, you activated the production power of card space " + choice);
                        CActivateProductionPowerResponseMsg response = new CActivateProductionPowerResponseMsg("I chose the base production power to activate", username, where, choice);
                        sendMsg(response);

                    } else {

                        // if he chooses a production power of a special card (Ability of a leader card)
                        printCLIMessage("Good, you activated the production power of a Special card ");
                        printCLIMessage("Insert the type of the resource that you want, it will be put automatically in the StrongBox! ");

                        while (!correct) {
                            in = new Scanner(System.in);
                            in.reset();

                            resourceToGet = in.nextLine();
                            if (checkType(resourceToGet.toUpperCase())) {
                                correct = true;
                            } else {

                                printCLIMessage("Error, type not valid, insert a new one! ");
                            }
                        }
                        CActivateProductionPowerResponseMsg response = new CActivateProductionPowerResponseMsg("I chose the base production power to activate", username, where, choice);
                        response.setResourceToGet(getTypeFromString(resourceToGet.toUpperCase()));
                        sendMsg(response);
                    }


                } else {
                    //if the player doesn't want to activate others PP
                    CStopPPMsg msg1 = new CStopPPMsg(" I don't want to activate any other Production Power", username);
                    sendMsg(msg1);

                }
            } else {
                //if the player can't activate any production power
                CStopPPMsg msg1 = new CStopPPMsg(" I don't want to activate any other Production Power", username);
                sendMsg(msg1);

            }

        }
    }

    @Override
    public void receiveMsg(VUpdateStrongboxMsg msg) {
        strongBox = msg.getStrongBox();
        showStrongBox(strongBox);
    }

    /**
     * this method displays to the players if they won or not
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        clearScreen();

        if (msg.getWinnerUsername().contains(username)) {
            WriteMessageDisplay.declareWinner();
            printCLIMessage(" You totalize " + msg.getVictoryPoints() + "points");
        } else {
            WriteMessageDisplay.endGame();
            WriteMessageDisplay.declareLoser();
        }

        in.reset();
        out.flush();
    }

    /**
     * msg used to show to the player which action token is been activated at the end of the turn
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {

            if (msg.getUsername().equals(username)) {

                System.out.println(msg.getMsgContent());
                System.out.println("Action Token used: ");
                System.out.println("┌---------------------┐");
                System.out.print("\n");
                System.out.println("   CardId: " +AnsiColors.CYAN_BOLD+msg.getActionToken().getCardID()+AnsiColors.RESET);
                if(msg.getActionToken().getAbility().equals("Card Action Ability")) {
                    System.out.println(((CardActionAbility)msg.getActionToken().getActionAbility()).toString());
                }
                else{
                    System.out.println("   Ability: " + msg.getActionToken().getAbility());
                }
                System.out.print("\n");
                System.out.println("└----------------------┘");
                try {
                    msg.getActionToken().activeActionToken(boardManager, (SoloPlayer) player);
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }

        }
    }

    /**
     * notification of starting the initialization
     *
     * @param msg from VV
     */
    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        printCLIMessage("The initialization has started...");

    }

    @Override
    public void receiveMsg(CGameCanStratMsg msg) {

    }

    @Override
    public void receiveMsg(VAnotherPlayerInfoMsg msg) {
        PlayerInterface otherPlayer = msg.getPlayer();
        printCLIMessage("you asked to see the " + otherPlayer.getUsername() + " stuff: ");
        showFaithTrack(otherPlayer.getGameSpace().getFaithTrack());
        showWarehouse(otherPlayer.getGameSpace().getWarehouse());
        showStrongBox(otherPlayer.getGameSpace().getStrongbox());
        showCardSpaces(otherPlayer.getGameSpace().getCardSpaces());
    }

    @Override
    public void receiveMsg(VWhichPlayerRequestMsg msg) {
        if (username.equals(msg.getUsername())) {
            printCLIMessage("You ask to see the information of another player, please insert the username of which one:");
            for (String username : msg.getOtherPlayers()) {
                printCLIMessage(username);
            }
            in = new Scanner(System.in);
            String userChosen = in.nextLine();

            CAskSeeSomeoneElseMsg asking = new CAskSeeSomeoneElseMsg("", this.username, userChosen);
            printCLIMessage(asking.toString());
            sendMsg(asking);
        }
    }

    @Override
    public void receiveMsg(VServerUnableMsg msg) {
        printCLIMessage("⚠️ Sorry, now the server is not available\nTry later :)");
        connectionOFF = false;
    }


    /**
     * method to clear the screen and remove older prints
     */
    private void clearScreen() {
        //System.out.println("reset and clear the screen");
        System.out.println("\033[H\033[2J");  //H is for go back to the top and 2J is for clean the screen
        System.out.flush();
    }

    /*--------------------------------------------------------------------------------------------------------------------------------*/
    // METHODS USED TO SHOW TO THE CLIENT

    /**
     * this method shows the FaithTrack to the player with his FaithMarker inside, if its a solo mode Game it will send the Lorenzo's Position
     *
     * @param faithTrack
     */
    private void showFaithTrack(FaithTrack faithTrack) {
        int lorenzoPosition;
        if (soloMode) {
            SoloPersonalBoard soloP = (SoloPersonalBoard) player.getGameSpace();
            lorenzoPosition = soloP.getLorenzoIlMagnifico().getPosition();
        } else {
            lorenzoPosition = 0;
        }
        FaithTrackDisplay faithT = new FaithTrackDisplay(faithTrack, faithTrack.getPositionFaithMarker(), soloMode, lorenzoPosition);
        faithT.showFaithTrack();
    }


    /**
     * this method shows the updated Development card table
     *
     * @param developmentCardTable
     */
    private void showDevelopmentCardTable(DevelopmentCardTable developmentCardTable, boolean[][] availableCards) {
        DevelopmentCardTableDisplay table = new DevelopmentCardTableDisplay(developmentCardTable, availableCards);
        table.displayCardTable();
    }

    /**
     * method used to show the updated market to the player
     *
     * @param marketStructure
     */
    private void showMarketStructure(MarketStructure marketStructure) {
        MarketDisplay market = new MarketDisplay(marketStructure);
        market.displayMarket();
    }

    /**
     * method used to show to the player his StrongBox
     *
     * @param strongBox
     */
    private void showStrongBox(StrongBox strongBox) {
        StrongboxDisplay strongboxDisplay = new StrongboxDisplay(strongBox);
        strongboxDisplay.displayStrongBox();
    }

    /**
     * method used to show the warehouse content to the player
     *
     * @param warehouse
     */
    private void showWarehouse(Warehouse warehouse) {
        WarehouseDisplay warehouseDisplay = new WarehouseDisplay(warehouse, player);
        warehouseDisplay.displayWarehouse();
    }

    /**
     * method used to show the last card in every card Space of the player
     *
     * @param cardSpaces
     */
    private void showCardSpaces(ArrayList<CardSpace> cardSpaces) {
        CardSpaceDisplay cardSpaceDisplay = new CardSpaceDisplay(cardSpaces, player);
        cardSpaceDisplay.showCardSpaces();
    }


    private void printCLIMessage(String message) {
        System.out.println(message);
    }

}




