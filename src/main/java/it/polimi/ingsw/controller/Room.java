package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.message.Observable;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room extends Observable {

    /* the ID of the room */
    private final String roomID;

    /* list of the players in this room */
    private List<String> playersId;
    /* number of players */
    private int numberOfPlayer;

    /* inizialized a size for the room (set by the first player acceded)*/
    //private final int SIZE;

    /* boolean to notify if the player want to play in Solo Mode, so the room has to be of size 1!*/
    private boolean isSoloMode;

    /* board manager of this room (to simplify the connection between Model and Controller) */
    private BoardManager boardManager;

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
        //SIZE = size;
        isSoloMode = false;
        gameCanStart = false;
        turnSequence = new HashMap<>();
        cleanRoom = false;
        printRoomMesssage("Room created");
    }

    public boolean isSoloMode() {
        return isSoloMode;
    }

    public void setSoloMode(boolean soloMode) {
        isSoloMode = soloMode;
    }

    public List<String> getPlayersId() {
        return playersId;
    }


    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * adding all the users logged in, in the room to start the game
     * the maximum of the player for room (game) is 4
     * @param username
     * @throws LimitExceededException
     */
    public void addUser(String username) throws LimitExceededException{
        try{
            // in a room can be maximum 4 player
            if (numberOfPlayer<4){
                Player player = new Player(username);
                playersId.add(username);
                turnSequence.put(numberOfPlayer+1, player);
                numberOfPlayer = playersId.size();
            }
            else{
                throw new LimitExceededException();
            }

            printRoomMesssage("New player " +username+ " added in the room!");
        } catch (LimitExceededException e){
            e.printStackTrace();
        }
    }

    /**
     * method to check if the room is full (4 player maximum) or not
     * @param
     */
    public boolean isFull(){
        if (isSoloMode && numberOfPlayer==1){
            //this room is occupied by a player in Solo Mode
            return true;
        }
        else if (numberOfPlayer == 4){
            //this room is Full
            return true;
        }
        return false;
    }

    /**
     * initialized the game for these players
     */
    public void initializedGame(){
        //creating the controller for the initialization
        initializedController = new InitializedController((ArrayList<String>) playersId);
        initializedController.createGame();
    }






    private void printRoomMesssage(String messageToPrint){
        System.out.println("Room " +this.roomID + ": " + messageToPrint);
    }
}