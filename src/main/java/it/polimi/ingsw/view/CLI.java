package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Color;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithTrack;
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
import it.polimi.ingsw.view.display.*;
import it.polimi.ingsw.view.display.FaithTrackDisplay;
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

    //local variables used to save locally the dates about a player and his game space

    private MarketStructure marketStructureData; //contains the data about the market
    private PlayerInterface player;
    private LeaderCardDeck leaderCards;
    private DevelopmentCardTable developmentCardTable;
    private StrongBox strongBox;
    private Warehouse warehouse;
    private FaithTrack faithTrack;
    private ArrayList<CardSpace> cardSpaces;

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
        client = new ClientSocket(iP, this);

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
                VVConnectionRequestMsg request = new VVConnectionRequestMsg("Request Connection ", iP, 0, username, gameSize);
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

    private String askIPAddress() {
        System.out.println("Please insert the Ip address:\n");
        System.out.println("Press ENTER for local setup");
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
                    System.out.println("⚠︎ Error: ip not valid!\n  Please insert another ip:");
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
            System.out.println("Error input not valid! Please insert 1 or 0 ");
            size = in.nextLine();
        }


        return size;
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
        return depot == 1 || depot == 2 || depot == 3;
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
     * auxiliary method that creates the resource basing on the color
     *
     * @param color
     * @return
     */
    private Resource getResourceFromString(Color color) {
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
            default:
                return TurnAction.ERROR;
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
                this.client.sendMsg(request);
                break;

            case "FULL_SIZE":  //all the rooms in the server are full, so the client can't be connected to the game

                System.out.println(" Error, server is full ");
                break;

            case "WAIT":      //in this case the server is not full so there are new rooms available, and the client has to wait because someone is creating a new room
                System.out.println(" Someone is now creating a new room! Please wait a moment ");
                try {
                    Thread.sleep(5000);
                    /* the login process has to restart, so the client try again sending another request */
                    VVConnectionRequestMsg request2 = new VVConnectionRequestMsg("Trying to connect", iP, 0, username, gameSize);
                    this.client.sendMsg(request2);
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

        System.out.println("setting size room in CLI");
        int roomSize = -1;

        System.out.println(" Please insert the number of players you want to play with [2,3 or 4]");

        roomSize = askRoomSize();

        /* send the msg to the controller with the size room he chose */
        CRoomSizeResponseMsg response = new CRoomSizeResponseMsg(" asking the room size ", roomSize, msg.getUsername(), msg.getRoomID());
        client.sendMsg(response);
    }

    /**
     * this msg is sent from the Room and contains all the data about the player and the board Manager
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {

        player = msg.getPlayer();
        marketStructureData = msg.getBoardManager().getMarketStructure();
        leaderCards = msg.getBoardManager().getLeaderCardDeck();
        developmentCardTable = msg.getBoardManager().getDevelopmentCardTable();
        warehouse = msg.getPlayer().getGameSpace().getResourceManager().getWarehouse();
        strongBox = msg.getPlayer().getGameSpace().getResourceManager().getStrongBox();
        faithTrack = msg.getPlayer().getGameSpace().getFaithTrack();
        cardSpaces = msg.getPlayer().getGameSpace().getCardSpaces();
    }

    /**
     * this msg ask to the client which action he wants to play from the actions that he can do!!
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {

        if (msg.getUsername().equals(username)) {
            System.out.println(msg.getMsgContent());
            System.out.println(" The actions that you are allowed to do are: " + msg.getAvailableActions());
            System.out.println(" Write the action you chose with _ between every word!! ");

            in = new Scanner(System.in);
            in.reset();

            String action = in.nextLine();
            TurnAction turnAction = returnActionFromString(action.toLowerCase());

            if (msg.getAvailableActions().contains(turnAction) && turnAction != TurnAction.ERROR) {

                CChooseActionTurnResponseMsg response = new CChooseActionTurnResponseMsg(" I made my choice, I decided the action I want to do", username, turnAction);
                client.sendMsg(response);

            } else {
                VChooseActionTurnRequestMsg askAgain = new VChooseActionTurnRequestMsg("choose again an action", msg.getUsername(), msg.getAvailableActions());
                this.receiveMsg(askAgain);
            }

        }
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
            System.out.println(" There are the four Leader cards from which you have to choose : \n");
            for (Integer id : msg.getMiniDeckLeaderCardFour()) {
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
            while (!valid) {

                if (cardId1.equals(cardId2)) {
                    System.out.println(" Error, you can't choose two cards with the same Id number");
                    System.out.println(" Please insert again the second card Id ! ");

                    cardId2 = in.nextInt();
                } else
                    valid = true;
            }

            while (!checkValidity(cardId1, cardId2, msg.getMiniDeckLeaderCardFour())) {

                System.out.println(" Error, Id card not valid !! ");

                cardId1 = in.nextInt();
                cardId2 = in.nextInt();
            }

            System.out.println("Good, you chose your cards ! ");

            chosenCards.add(cardId1);
            chosenCards.add(cardId2);

            /* put the remaining cards not chosen by the player in another ArrayList*/
            for (Integer card : msg.getMiniDeckLeaderCardFour()) {
                if (!card.equals(cardId1) && card != cardId2) {
                    deniedCards.add(card);
                }
            }

            CChooseLeaderCardResponseMsg response = new CChooseLeaderCardResponseMsg(" chosen cards ", chosenCards, deniedCards, msg.getUsername(), "firstChoose");
            this.client.sendMsg(response);
        } else {
            //discard or activate
            if (!msg.getMiniDeckLeaderCardFour().isEmpty()) {
                System.out.println("Choose which card you want to \"" + msg.getWhatFor() + "\"  from:");
                for (Integer i : msg.getMiniDeckLeaderCardFour()) {
                    System.out.println(i);
                }
                int cardToRemoveOrActivate = in.nextInt();
                CChooseLeaderCardResponseMsg response2 = new CChooseLeaderCardResponseMsg(" chosen cards ", cardToRemoveOrActivate, msg.getUsername(), msg.getWhatFor());
                this.client.sendMsg(response2);
            } else {
                System.out.println("Sorry you cannot discard any Leader Card!");
            }
        }
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
        String resourceColor = null;
        String resourceType = null;
        Color typeColor = null;
        Color resColor = null;

        if (msg.getUsername().equals(username)) {

            in = new Scanner(System.in);
            in.reset();

            System.out.print(msg.getMsgContent());
            System.out.println(" Here is your current Warehouse's situation ");
            showWarehouse(warehouse, player);

            if (msg.getChoices() == null) {
                System.out.println(" Please enter the color of the resource you want : ");
                System.out.println("YELLOW --> COIN,\n" +
                        "PURPLE --> SERVANT,\n" +
                        "BLUE --> SHIELD,\n" +
                        "GREY --> STONE\n");

                resourceColor = in.nextLine();

                // check if the color exist
                while (!checkColor(resourceColor.toUpperCase())) {

                    System.out.println(" Error, please insert a valid color! ");
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

                        System.out.println(" Error, please insert a valid Type! ");
                        resourceType = in.nextLine();
                    }

                }

                /*creates the color of the resource basing on the Type of it, written by the player*/
                typeColor = getColorFromType(resourceType.toUpperCase());

            }

            System.out.println(" Please enter the depot where you want to put the resource : ");
            System.out.println(" 1 --> DEPOT1,\n" +
                    "2 --> DEPOT2,\n" +
                    "3 --> DEPOT3\n");

            depot = in.nextInt();

            // check if the depot exist
            while (!checkDepotValidity(depot)) {
                in.reset();

                System.out.println(" Error depot int not valid, insert a new one (1,2 or 3 ");
                depot = in.nextInt();
            }

            //send one of this two types of responses depending on the type of request
            CChooseResourceAndDepotMsg response;
            if (msg.getChoices() == null) {
                try {
                    warehouse.addResource(getResourceFromString(resColor), depot);
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
                System.out.println(" Here is your Warehouse updated ");
                showWarehouse(warehouse, player);
                response = new CChooseResourceAndDepotMsg(" resource and depot chosen ", resColor, depot, msg.getUsername());
            } else {
                try {
                    warehouse.addResource(getResourceFromString(typeColor), depot);
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
                System.out.println(" Here is your Warehouse updated ");
                showWarehouse(warehouse, player);
                response = new CChooseResourceAndDepotMsg(" resource and depot chosen ", typeColor, depot, msg.getUsername());
            }
            client.sendMsg(response);

        }
    }

    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {
        warehouse = msg.getWarehouse();
        System.out.println(" Here is your Warehouse updated ");
        showWarehouse(warehouse, player);
    }


    /**
     * msg used to notify to all the game Player that a player increased his position of NumberOfPositionIncreased positions
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {

        System.out.println(msg.getMsgContent());
        if (msg.getUsernameIncreased().equals(username)) {
            System.out.println("Congratulations, your faith Marker increased his position " + msg.getNumberOfPositionIncreased());
            // locally increasing the position of the faith Marker
            faithTrack.getFaithMarker().increasePosition();
            showFaithTrack(faithTrack, faithTrack.getPositionFaithMarker());
        } else if (msg.getAllPlayerToNotify().contains(username)) {
            System.out.println("\"msg.getUsernameIncreased()\"+ increased his position of: " + msg.getNumberOfPositionIncreased());
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
            System.out.println(msg.getMsgContent());
            System.out.println("You can't put in Depot " + msg.getUnableDepot() + " the resource " + "\"msg.getResourceChooseBefore()\"" + "that you choose before! ");

            System.out.print("Here is your actual situation! ");
            showWarehouse(warehouse, player);

            System.out.print("\n");
            System.out.println(" Please insert a new depot for this resource [number from 1 to 5] ");
            in = new Scanner(System.in);
            in.reset();

            int newDepot = in.nextInt();
            CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg("I made my choice!", msg.getResourceChooseBefore(), newDepot, username);
            client.sendMsg(response);
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

        boolean correct = false;
        if (msg.getUsername().equals(username)) {

            developmentCardTable = msg.getTableCard();
            System.out.println(msg.getMsgContent());

            in = new Scanner(System.in);
            in.reset();
            showStrongBox(strongBox, player);
            showDevelopmentCardTable(developmentCardTable, msg.getCardAvailable());
            showCardSpaces(cardSpaces, player);

            System.out.println(" Insert a row [from 0 to 2] and a column [from 0 to 3] in the table from where you want to take the card ");
            int row = in.nextInt();
            int column = in.nextInt();

            System.out.println(" Insert in which card Space you want to insert it [1,2 or 3] ");
            int cardSpace = in.nextInt();

            while (!correct) {

                //check if the deck selected is not empty (and has at least one card) and if the client inserted a valid card space
                if (!(msg.getTableCard().getTable()[row][column].getDevelopDeck().isEmpty()) && (cardSpace == 1 || cardSpace == 2 || cardSpace == 3)) {

                    //to update the local version of the variable of the client
                    //developmentCardTable.takeCard(row,column);
                    cardSpaces.get(cardSpace).addCard(developmentCardTable.takeCard(row, column));
                    try {
                        strongBox.removeResources(developmentCardTable.getTable()[row][column].getDevelopDeck().get(developmentCardTable.getTable()[row][column].getDevelopDeck().size()).getCost());
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }

                    if (developmentCardTable.getTable()[row][column].getDevelopDeck().isEmpty()) {
                        msg.getCardAvailable()[row][column] = false;
                    }
                    System.out.println("Here is the card Table Updated! ");
                    showDevelopmentCardTable(developmentCardTable, msg.getCardAvailable());

                    System.out.println("Here is your StrongBox Updated! ");
                    showStrongBox(strongBox, player);

                    System.out.println("Here are your card spaces updated");
                    showCardSpaces(cardSpaces, player);

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
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

        boolean correct = false;
        while (!correct) {
            if (msg.getUsername().equals(username)) {

                System.out.println(msg.getMsgContent());
                in = new Scanner(System.in);
                in.reset();

                showWarehouse(warehouse, player);
                System.out.println(" Write the origin depot from where you want to move: ");
                int fromDepot = in.nextInt();

                System.out.println(" Write the depot in which you want to move it ");
                int toDepot = in.nextInt();

                if (msg.getDepotsActualSituation().containsKey(fromDepot) && msg.getDepotsActualSituation().containsKey(toDepot)) {
                    try {
                        warehouse.moveResource(fromDepot, toDepot);
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Here is the updated situation of your Warehouse! ");
                    showWarehouse(warehouse, player);

                    CMoveResourceInfoMsg response = new CMoveResourceInfoMsg(" I choose from where and to where I want to put my resource ", username, fromDepot, toDepot);
                    client.sendMsg(response);
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

        if (msg.getUsername().equals(username)) {
            marketStructureData = msg.getMarket();

            System.out.println(msg.getMsgContent());

            in = new Scanner(System.in);
            in.reset();

            // show the player the current market situation
            showMarketStructure(marketStructureData);

            boolean correct = false;

            System.out.println(" Please insert if you want to choose a row or a column : ");
            String choice = in.nextLine();

            if (choice.toLowerCase().equals("row")) {
                System.out.println(" Please insert the number of the row that you want (0,1,2) ");
            } else {
                System.out.println(" Please insert the number of the column that you want (0,1,2,3) ");
            }
            int number = in.nextInt();


            while (!correct) {

                if ((choice.toLowerCase().equals("row") && number < 4) || (choice.toLowerCase().equals("column") && number < 5)) {

                    if (choice.toLowerCase().equals("row")) {
                        try {
                            marketStructureData.rowMoveMarble(number, (Player) player);  //update the market situation (changing the row)
                        } catch (InvalidActionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            marketStructureData.columnMoveMarble(number, (Player) this.player);  //update the market situation (changing the column)
                        } catch (InvalidActionException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(" That's the updated situation of your market! ");
                    showMarketStructure(marketStructureData);

                    CBuyFromMarketInfoMsg response = new CBuyFromMarketInfoMsg(" I chose the row/column that I want to take from the market ", username, choice, number);
                    client.sendMsg(response);
                    correct = true;
                }
            }
        }
    }

    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        //...
        System.out.println("Choose where to store this resource: " + msg.getResourceToStore() + " if you want to discard it send 0");
        showWarehouse(this.warehouse, this.player);
        int depot = in.nextInt();
        Color rC = msg.getResourceToStore().getThisColor();
        if (depot != 0) {
            CChooseResourceAndDepotMsg response = new CChooseResourceAndDepotMsg("the choice of the player for a resources recived", rC, depot, msg.getUsername());
            client.sendMsg(response);
        } else {
            CChooseDiscardResourceMsg discard = new CChooseDiscardResourceMsg("the player choose to discard the resources", msg.getUsername());
            client.sendMsg(discard);
        }

    }

    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {

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
            System.out.println(" You totalize " + msg.getVictoryPoints() + " points");
        } else {
            WriteMessageDisplay.endGame();
            WriteMessageDisplay.declareLoser();
        }

        in.reset();
        out.flush();
    }

    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {
        //this have to be implemented
    }

    /**
     * notification of starting the initialization
     *
     * @param msg from VV
     */
    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        System.out.println("The initialization has started...");

    }


    /**
     * method to clear the screen and remove older prints
     */
    private void clearScreen() {
        System.out.println("reset and clear the screen");
        System.out.println("\033[H\033[2J");  //H is for go back to the top and 2J is for clean the screen
        System.out.flush();
    }

    /*--------------------------------------------------------------------------------------------------------------------------------*/
    // METHODS USED TO SHOW TO THE CLIENT

    /**
     * this method shows the FaithTrack to the player with his FaithMarker inside
     *
     * @param faithTrack
     */
    private void showFaithTrack(FaithTrack faithTrack, int positionOnFaithTrack) {
        FaithTrackDisplay faithT = new FaithTrackDisplay(faithTrack, faithTrack.getPositionFaithMarker());
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
     * @param player
     */
    private void showStrongBox(StrongBox strongBox, PlayerInterface player) {
        StrongboxDisplay strongboxDisplay = new StrongboxDisplay(strongBox, player);
        strongboxDisplay.displayStrongBox();
    }

    /**
     * method used to show the warehouse content to the player
     *
     * @param warehouse
     * @param player
     */
    private void showWarehouse(Warehouse warehouse, PlayerInterface player) {
        WarehouseDisplay warehouseDisplay = new WarehouseDisplay(warehouse, player);
        warehouseDisplay.displayWarehouse();
    }

    /**
     * method used to show the last card in every card Space of the player
     *
     * @param cardSpaces
     * @param player
     */
    private void showCardSpaces(ArrayList<CardSpace> cardSpaces, PlayerInterface player) {
        CardSpaceDisplay cardSpaceDisplay = new CardSpaceDisplay(cardSpaces, player);
        cardSpaceDisplay.showCardSpaces();
    }

}


