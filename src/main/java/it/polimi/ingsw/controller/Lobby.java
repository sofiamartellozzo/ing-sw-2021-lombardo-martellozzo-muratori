package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.exception.NotFreeRoomAvailableError;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.VConnectionRequestMsg;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SINGLETON
 * this class handle the connection, create the rooms and manage the organization of the rooms
 */
public class Lobby extends Observable implements ControllerObserver {

    private static Lobby lobby = null;

    /* constructor */
    private Lobby(){
        //create the objects inside this class
        canCreateRoom = new AtomicBoolean();
        canCreateRoom.set(true);
        this.notEmptyRoom = new ArrayList<>();
        this.usersAssigned = new ArrayList<>();
    }

    /**
     * method called to instances this class, because is a Singleton
     * if is null is created a new Lobby object, otherwise is returned the class itself
     * because can be created only once
     * @return
     */
    public static Lobby getInstance(){
        if (lobby == null){
            lobby = new Lobby();
        }
        return lobby;
    }

    /*------------------------------------------------------------------------------------------*/

    /**
     * list with the room occupied with some players
     */
    private List<Room> notEmptyRoom;

    /**
     * list with the player assigned yet in a room
     */
    private List<String> usersAssigned;

    /**
     * number of the occupied Rooms
     * using a private method (update Room Number) to update it
     * as the size of the notEmptyRoom List
     */
    private int numberOfRooms;

    /**
     * maximum number of rooms
     */
    private final int MAX_NUMBER_ROOM = 20;

    /**
     * add an attribute Atomic (so only one thead at the time can have access)
     * that store the information about being able to reate a new room or not
     */
    private final AtomicBoolean canCreateRoom;


    /*----------------------------------------------------------------------------------------------------------------*/

            //METHODS FOR MANAGE THE ROOMS

    /**
     * method to check if the room (given in input) is full or not
     * @param room -> the class Room to be checked calling his method
     * @return
     */
    private boolean roomFull(Room room){
        return room.isFull();
    }

    /**
     * scroll all the occupied room ad check if are all full
     * if not returned false so The Lobby know that can assign
     * the client to the first able room
     * @return
     */
    private boolean allRoomsFull(){
        for (Room room: notEmptyRoom) {
            if (!roomFull(room)){
                return false;
            }
        }
        return true;
    }

    /**
     * after checking if there is an available room this method returns the first room
     * available in this lobby
     */
    private Room firstFreeRoom() throws NotFreeRoomAvailableError{
        if (allRoomsFull()){
            throw new NotFreeRoomAvailableError("Error, there are not an available room right now");
        }
        else{
            for (Room room: this.notEmptyRoom) {
                if (!roomFull(room)){
                    return room;
                }
            }
        }
        return null;
    }

    /**
     * private method to update the number of room
     * called after adding or removing a room
     */
    private void updateRoomCounter(){
        this.numberOfRooms = this.notEmptyRoom.size();
    }

    /*-------------------------------------------------------------------------------------------*/

                //METHODS FOR THE INITIALIZATION

    /**
     * check if this username can start the game, so is in a full room and the Controller
     * can start to inizialized the game
     * @param username the player ask a connection
     * @return true if the controller can start, false otherwise
     */
    public boolean canInitializeGameFor(String username){
        if (this.usersAssigned.contains(username)){
            //the user has been assigned to a Room
            Room userRoom = null;
            try {
                //find his room
                userRoom = findUserRoom(username);
            } catch (NotFreeRoomAvailableError e) {
                e.printStackTrace();
            }
            //send to the client the number of players in his room and ask if it's ok to start
            //VRoomSizeRequestMsg request = new VRoomSizeRequestMsg()
            if (userRoom!=null && userRoom.isFull()){
                return true;
            }
        }
        return false;
    }

    /**
     * find the room where the user is assigned
     * @param username
     * @return
     * @throws NotFreeRoomAvailableError
     */
    private Room findUserRoom(String username) throws NotFreeRoomAvailableError{
        for (Room r:this.notEmptyRoom) {
            if (r.getPlayersId().contains(username))
                return r;
        }
        throw  new NotFreeRoomAvailableError("Username not found in the list of the Rooms in the Lobby!");
    }

    public void startInitializationOfTheGame(String username){
        try {
            findUserRoom(username).initializedGame();
        } catch (NotFreeRoomAvailableError | InvalidActionException error) {
            error.printStackTrace();
        }
    }

    /*-----------------------------------------------------------------------------------------------------------------*/
            //HANDLE EVENTS


    /**
     * this method respond at a message from the client that ask for a connection
     * here is needed a check of the username given:
     * it has to be unique
     * then assign to this client the room where to play
     * (the client before has to say if he want to play in Solo mode or Multi player)
     * @param msg
     */
    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        String gameMode = msg.getGameSize();
        String convertedGameMode = convertStringForMode(gameMode);
        System.out.println("[Lobby] request of a connection from: " +msg.getIP()+ " on @" +msg.getPort()+ " given " +msg.getUsername()+ " as username " +
                "asking for playing in " +convertedGameMode+ " Game!");

        //check if the username is not used yet
        if (usersAssigned.contains(msg.getUsername())){
            //username used yet
            sendNackConnectionRequest(msg);
            System.out.println("Error, username " +msg.getUsername()+ "not valid because is taken already");
        }
        else{
            //now check if there is a room available or if all are occupied
            //if the client wants to play in Solo mode check only if can create a new room
            if (!allRoomsFull()&&!gameMode.equals("0")){
                //there is an available room
                usersAssigned.add(msg.getUsername());
                Room room = null;
                try{
                    room = firstFreeRoom();
                    if (room!=null && !roomFull(room)) {
                        room.addUser(msg.getUsername());
                    }
                }catch (NotFreeRoomAvailableError | LimitExceededException e){
                    e.printStackTrace();
                }
            }
            else if (canCreateRoom.get()){
                //all occupied room are full but one can be created, or Solo Mode
                usersAssigned.add(msg.getUsername());
                canCreateRoom.set(false); //now this client is creating a new room, so I set this parameter to false and not letting anyone else to do the same now
                Room newRoom = new Room("Room:  #" +this.numberOfRooms);
                if (gameMode.equals("0")){
                    //set the attribute of the Room true because the Client asked to play in Solo Mode
                    newRoom.setSoloMode(true);
                }
                this.notEmptyRoom.add(newRoom);
                updateRoomCounter();  //update the actual number of the rooms occupied
            }
            else if (this.numberOfRooms == MAX_NUMBER_ROOM){
                //all rooms are full and is not possible to create a new room
                sendNackConnectionRequest(msg);
                System.out.println("Error: all rooms are Full, Sorry try later!");
            }
            else if (!canCreateRoom.get()){
                //if you arrived here this means that all rooms are occupied but the server is not full (#room==16) so the reason is that someone else is creating a new room
                sendNackConnectionRequest(msg);
                System.out.println("Error: someone else is creating a room, please wait a fiew seconds!");
            }
        }
    }

    /**
     * creating the Error message to send to the client, after notify the view
     * @param msg
     */
    private void sendNackConnectionRequest(CConnectionRequestMsg msg){
        CNackConnectionRequestMsg nackMsg = new CNackConnectionRequestMsg("Connection cannot be established ",msg.getUsername());
        notifyAllObserver(ObserverType.VIEW, nackMsg);
    }

    @Override
    public void receiveMsg(VConnectionRequestMsg msg) {
        //not implemented here (in Virtual View)
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //not implemented here (Initialized Controller)
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //not implemented here (Initialized Controller)
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

    }


    /*----------------------------------------------------------------------------------------------------------------*/

                    //AUXILIARY PRIVATE METHODS

    /**
     * because the client type 0 or 1 to choose the game mode
     * convert the number to print out the right msg
     * @param mode
     * @return
     */
    private String convertStringForMode(String mode){
        switch (mode){
            case "0":
                return "Solo Mode";
            case "1":
                return "Multiplayer Mode";
        }
        return null;
    }


}
