package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.updateMsg.VWaitYourTurnMsg;
import it.polimi.ingsw.message.viewMsg.VSendPlayerDataMsg;
import it.polimi.ingsw.message.viewMsg.VStopWaitReconnectionMsg;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.model.SoloPlayer;
import it.polimi.ingsw.model.board.PersonalBoardInterface;
import it.polimi.ingsw.view.VirtualView;

import javax.naming.LimitExceededException;
import java.util.*;

/**
 * this class contains the players of the game,
 * and handles the different phases of the game
 * A room can be composed of 1-2-3 or 4 players
 */
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
    private ArrayList<Player> players;

    /* boolean that notify if the game is all set and so can start */
    private boolean gameCanStart;

    /* boolean set true when a game end and the room must be cleaned */
    private boolean cleanRoom;

    private boolean waiting;

    /**
     * constructor of the class
     *
     * @param roomID Id of the room that will be created
     */
    public Room(String roomID) {
        this.roomID = roomID;
        playersId = new ArrayList<>();
        numberOfPlayer = 0;
        listOfVirtualView = new HashMap<>();
        isSoloMode = false;
        gameCanStart = false;
        turnSequence = new HashMap<>();
        players = new ArrayList<>();
        cleanRoom = false;
        waiting = false;
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

    public int getNumberOfPlayer() {
        return numberOfPlayer;
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
        return initializedController.canStart();
    }

    public Map<String, VirtualView> getListOfVirtualView() {
        return listOfVirtualView;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public int getIntId() {
        int number = 0;
        //number = roomID.charAt(roomID.length()-2) + roomID.charAt(roomID.length()-1);
        //Pattern p = Pattern.compile("\\d+");
        //Matcher m = p.matcher(roomID);
        String str = roomID;
        str = str.replaceAll("[^-?0-9]+", "");
        number = Integer.parseInt(str);
        return number;
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    public void addVV(String userVV, VirtualView vV) {
        listOfVirtualView.put(userVV, vV);
        attachObserver(ObserverType.VIEW, vV);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * adding all the users logged in, in the room to start the game
     * the maximum of the player for room (game) is 4
     *
     * @param username of the player that has to be added
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

                if (!isSoloMode) {
                    /* multiple game*/
                    Player player = new Player(username);
                    turnSequence.put(numberOfPlayer, player);
                    players.add(player);
                } else {
                    SoloPlayer playerS = new SoloPlayer(username);
                    singlePlayer = playerS;
                }

                addVV(username, VV);
                //VRoomInfoMsg requestMsg = new VRoomInfoMsg("Sending the info about the room", this.playersId,this.roomID,this.numberOfPlayer,this.SIZE);
                //notifyAllObserver(ObserverType.VIEW,requestMsg);

            } else {
                throw new LimitExceededException();
            }

        } catch (LimitExceededException e) {
            e.printStackTrace();
        }
    }

    public void disconnectPlayer(String username) {
        getPlayerByUsername(username).setDisconnected(true);

    }

    /**
     * method to check if the room is full (4 player maximum) or not
     *
     * @return -> true if the room is full
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
     * initialize the game for these players
     *
     * @throws InvalidActionException
     */
    public void initializedGame() throws InvalidActionException {
        //creating the controller for the initialization
        //attachAllVV();
        initializedController = new InitializedController(playersId, listOfVirtualView);
        attachObserver(ObserverType.CONTROLLER, initializedController);
        //initializedController.createGame();
        //boardManager = initializedController.getBoardManager();
        //printRoomMessage("in ROOM " + boardManager.toString());
        if (!isSoloMode) {
            //fill the sequence with the players created
            initializedController.creatingPlayersSequence(playersId, players);
            turnSequence = initializedController.getTurnSequence();
        } else {
            //fill the attribute of the single player with the created one
            //singlePlayer = initializedController.getSinglePlayer();
            initializedController.fillSinglePlayer(singlePlayer);
        }
        initializedController.createGame();
        boardManager = initializedController.getBoardManager();


        gameCanStart = true;

        waiting = true;

        //startFirstTurn();
    }

    /**
     * creates the turn controller to manage the turn
     *
     * @throws InvalidActionException
     */
    public void startFirstTurn() throws InvalidActionException {

        if (!isSoloMode) {
            turnController = new TurnController(turnSequence, boardManager, listOfVirtualView);
            attachObserver(ObserverType.CONTROLLER, turnController);
        } else {
            //singlePlayer = initializedController.getSinglePlayer();
            turnController = new TurnController(singlePlayer, boardManager, listOfVirtualView);
            attachObserver(ObserverType.CONTROLLER, turnController);
        }

        /* the initialization has finished so detach the observer*/
        //detachObserver(ObserverType.CONTROLLER, initializedController);
        //gameCanStart = true;
        turnController.gamePlay();

    }

    /**
     * returns the board of the player found by his username given in input
     *
     * @param username
     * @return
     */
    public PersonalBoardInterface getPlayerBoard(String username) {
        for (PlayerInterface player : turnSequence.values()) {
            if (player.getUsername().equals(username)) {
                return player.getGameSpace();
            }
        }
        throw new IllegalArgumentException(" Error, not found any player with that username!");
    }

    /**
     * returns the Player from his username given in input
     *
     * @param username of the player
     * @return
     */
    public PlayerInterface getPlayerByUsername(String username) {
        if (!isSoloMode) {
            //multiplayer mode
            for (PlayerInterface player : turnSequence.values()) {
                if (player.getUsername().equals(username)) {
                    return player;
                }
            }
        } else {
            //single player
            return this.singlePlayer;
        }
        return null;
    }


    public void detachInitializedC() {
        if (initializedController != null && isGameCanStart()) {
            detachObserver(ObserverType.CONTROLLER, initializedController);
            initializedController = null;
        }
    }

    /**
     * removes the virtual view of a specific player (username) from the list
     */
    public void removeVV() {
        Set<String> usernames = listOfVirtualView.keySet();
        for (String user : usernames) {
            detachObserver(ObserverType.VIEW, listOfVirtualView.get(user));
        }
        listOfVirtualView = null;
    }

    public boolean checkGameModeReconnection(String gameMode){
        if ((isSoloMode && gameMode.equals("0"))||(!isSoloMode && gameMode.equals("1"))){
            return true;
        }
        else
            return false;
    }


    public void reconnectPlayer(String username, VirtualView VV) {
        VStopWaitReconnectionMsg stop = new VStopWaitReconnectionMsg("The client reconnected so stop the timer");
        notifyAllObserver(ObserverType.VIEW, stop);

        if (listOfVirtualView.get(username) != null) {
            //detach the old one
            detachObserver(ObserverType.VIEW, listOfVirtualView.get(username));
            //and then eliminate it
            listOfVirtualView.remove(username);
        }
        //now set the "new" player
        getPlayerByUsername(username).setDisconnected(false);
        addVV(username, VV);
        //send to the player his Data
        VSendPlayerDataMsg msg = new VSendPlayerDataMsg("these is the actual condiction of your game after reconnected", getPlayerByUsername(username), boardManager, false);
        notifyAllObserver(ObserverType.VIEW, msg);
        if (!isSoloMode) {
            VWaitYourTurnMsg secondMsg = new VWaitYourTurnMsg("you just reconnected, so now wait your turn", username);
            notifyAllObserver(ObserverType.VIEW, secondMsg);
        }
        //VV.attachObserver(ObserverType.CONTROLLER, turnController);
        if (turnController != null) {
            turnController.attachObserver(ObserverType.VIEW, VV);
            turnController.addVV(username, VV);
        }
    }

    private boolean inWaiting() {
        for (PlayerInterface p : turnSequence.values()) {
            if (!p.isDisconnected()) {
                return false;
            }
        }
        return true;
    }




}
