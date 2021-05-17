package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.viewMsg.VRoomInfoMsg;
import it.polimi.ingsw.message.viewMsg.VSendPlayerDataMsg;
import it.polimi.ingsw.message.viewMsg.VNotifyPositionIncreasedByMsg;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.model.SoloPlayer;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardInterface;
import it.polimi.ingsw.view.VirtualView;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room extends Observable {

    /* the ID of the room */
    private final String roomID;

    /* list of the players in this room */
    private ArrayList<String> playersId;
    /* number of players */
    private int numberOfPlayer;

    /* the solo player if is only one */
    private SoloPlayer singlePlayer;

    /* initialized a size for the room (set by the first player acceded)*/
    private int SIZE;

    /* boolean to notify if the player want to play in Solo Mode, so the room has to be of size 1!*/
    private boolean isSoloMode;

    /* board manager of this room (to simplify the network between Model and Controller) */
    private BoardManager boardManager;

    /* map to connect every player to his virtual view */
    private Map<String, VirtualView> listOfVirtualView;

    /* the controller for setting the game */
    private InitializedController initializedController;

    /* the controller that manage the Turns */
    private TurnController turnController;

    /* the turn sequence in the math */
    private HashMap<Integer, PlayerInterface> turnSequence;

    /* boolean that notify if the game is all set and so can start */
    private boolean gameCanStart;

    /* boolean set true when a game end and the room must be cleaned */
    private boolean cleanRoom;

    public Room(String roomID) {
        this.roomID = roomID;
        playersId = new ArrayList<>();
        numberOfPlayer = 0;
        listOfVirtualView = new HashMap<>();
        isSoloMode = false;
        gameCanStart = false;
        turnSequence = new HashMap<>();
        cleanRoom = false;
        printRoomMessage("Room created");
    }

    public boolean isSoloMode() {
        return isSoloMode;
    }

    public void setSoloMode(boolean soloMode) {
        isSoloMode = soloMode;
    }

    public ArrayList<String> getPlayersId() {
        return playersId;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public int getSIZE() {
        return SIZE;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public HashMap<Integer, PlayerInterface> getTurnSequence() {
        return turnSequence;
    }

    public boolean isGameCanStart() {
        return gameCanStart;
    }

    public void addVV(String userVV, VirtualView vV){
        listOfVirtualView.put(userVV, vV);
        attachObserver(ObserverType.VIEW,vV);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * adding all the users logged in, in the room to start the game
     * the maximum of the player for room (game) is 4
     *
     * @param username
     * @throws LimitExceededException
     */
    public void addUser(String username, VirtualView VV) throws LimitExceededException {
        try {
            // in a room can be maximum 4 player
            if (numberOfPlayer < 4) {

                //add the new username to the list
                playersId.add(username);
                //update the number of players based on the number of usernames
                numberOfPlayer = playersId.size();

                if (!isSoloMode){
                    /* multiple game*/
                    Player player = new Player(username);
                    turnSequence.put(numberOfPlayer, player);
                }
                else{
                    SoloPlayer playerS = new SoloPlayer(username);
                    singlePlayer = playerS;
                }

                addVV(username, VV);
                //System.out.println("in add user " +turnSequence);     DEBUGGING

            } else {
                throw new LimitExceededException();
            }

            printRoomMessage("New player \"" + username + "\" added in the room!");
        } catch (LimitExceededException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to check if the room is full (4 player maximum) or not
     *
     * @param
     */
    public boolean isFull() {
        if ((isSoloMode && numberOfPlayer == 1) || (numberOfPlayer == SIZE)) {
            //this room is occupied by a player in Solo Mode
            //or is full, so the size equals yhe number of players inside
            return true;
        } else {
            return false;
        }
    }

    /**
     * initialized the game for these players
     */
    public void initializedGame() throws InvalidActionException {
        //creating the controller for the initialization
        //System.out.println(playersId.size());     DEBUGGING
        //attachAllVV();
        initializedController = new InitializedController(playersId, listOfVirtualView);
        attachObserver(ObserverType.CONTROLLER, initializedController);
        initializedController.createGame();
        boardManager = initializedController.getBoardManager();
        if (!isSoloMode){
            //fill the sequence with the players created
            turnSequence = initializedController.getTurnSequence();
        }
        else{
            //fill the attribute of the single player with the created one
            singlePlayer = initializedController.getSinglePlayer();
        }

        printRoomMessage("the game has been initialized, starting...");

        /*
        for (PlayerInterface player:turnSequence.values()) {
            VSendPlayerDataMsg msg = new VSendPlayerDataMsg("Here are the personal Data about your",player,boardManager);
            notifyAllObserver(ObserverType.VIEW, msg);
        }*/
        startFirstTurn();
        boolean wait = true;
        while  (wait){
            //wait until the initialization has finished
            if (initializedController.canStart()){
                //startFirstTurn();
                wait = false;
            }
        }


    }

    public void startFirstTurn() throws InvalidActionException {
        //creating the controller for the turn
        //System.out.println("is in solo mode? " +isSoloMode);      DEBUGGING
        if (!isSoloMode) {
            //System.out.println("is in solo mode? " +isSoloMode);      DEBUGGING
            //System.out.println(turnSequence);
            turnController = new TurnController(turnSequence, boardManager, listOfVirtualView);
            attachObserver(ObserverType.CONTROLLER, turnController);
        } else {
            //System.out.println("is in solo mode? " +isSoloMode);      DEBUGGING
            //singlePlayer = initializedController.getSinglePlayer();
            turnController = new TurnController(singlePlayer, boardManager, listOfVirtualView);
            attachObserver(ObserverType.CONTROLLER, turnController);
        }

        /* the initialization has finished so detach the observer*/
        //detachObserver(ObserverType.CONTROLLER, initializedController);
        turnController.gamePlay();
    }

    /**
     *
     * @param username
     * @return
     */
    public PersonalBoardInterface getPlayerBoard(String username){
        for (PlayerInterface player: turnSequence.values()) {
            if(player.getUsername().equals(username)) {
                return player.getGameSpace();
            }
        }
        throw new IllegalArgumentException(" Error, not found any player with that username!");
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    private void attachAllVV(){
        for (String username: listOfVirtualView.keySet()) {
            attachObserver(ObserverType.VIEW, listOfVirtualView.get(username));
        }
    }

    public void detachInitializedC(){
        if (initializedController!=null){
            detachObserver(ObserverType.CONTROLLER, initializedController);
            initializedController = null;
        }
    }


    private void printRoomMessage(String messageToPrint) {
        System.out.println("[Room] " + this.roomID + " : " + messageToPrint);
    }


    /**
     * method used to manage the disconnection of a single player from a game, and delete his network
     * with the virtual view
     *
     * @param username
     */
    private void disconnectPlayer(String username) {

        playersId.remove(username);       //remove the name of the player disconnected from the list

        VirtualView playerVirtualView = listOfVirtualView.get(username);     //get the virtual view of the player disconnected
        playerVirtualView.detachObserver(ObserverType.CONTROLLER, turnController);
        turnController.detachObserver(ObserverType.VIEW, playerVirtualView);
        listOfVirtualView.remove(username); //remove the username from the map in which every player is associated to his virtual view
    }
}
