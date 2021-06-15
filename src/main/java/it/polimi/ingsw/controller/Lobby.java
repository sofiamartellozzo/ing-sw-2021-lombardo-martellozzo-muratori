package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.exception.NotFreeRoomAvailableError;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.message.controllerMsg.CChooseResourceAndDepotMsg;
import it.polimi.ingsw.message.controllerMsg.CConnectionRequestMsg;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.view.VirtualView;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SINGLETON
 * this class handle the network, create the rooms and manage the organization of the rooms
 */
public class Lobby extends Observable implements ControllerObserver {

    private static Lobby lobby = null;

    /* constructor */
    private Lobby() {
        //create the objects inside this class
        canCreateRoom = new AtomicBoolean();
        canCreateRoom.set(true);
        this.notEmptyRoom = new ArrayList<>();
        this.usersAssigned = new ArrayList<>();
        this.idRoomToUse = new ArrayList<>();
    }

    /**
     * method called to instances this class, because is a Singleton
     * if is null is created a new Lobby object, otherwise is returned the class itself
     * because can be created only once
     *
     * @return
     */
    public static Lobby getInstance() {
        if (lobby == null) {
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
     * array list of integer containing
     * all numbers of room id eliminated
     * to not create a Room with the same id as another one
     */
    private List<Integer> idRoomToUse;

    /**
     * maximum number of rooms
     */
    private final int MAX_NUMBER_ROOM = 20;

    /**
     * add an attribute Atomic (so only one thead at the time can have access)
     * that store the information about being able to reate a new room or not
     */
    private final AtomicBoolean canCreateRoom;

    /**
     * Lock the creation of the Room and the access to canCreateNewRoom.
     */
    private final ReentrantLock creatingRoomLock = new ReentrantLock();


    /*-------------------------------------------------------------------------------------------*/
    //      Getter methods
    //       (for testing)

    public static Lobby getLobby() {
        return lobby;
    }

    public List<Room> getNotEmptyRoom() {
        return notEmptyRoom;
    }

    public List<String> getUsersAssigned() {
        return usersAssigned;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getMAX_NUMBER_ROOM() {
        return MAX_NUMBER_ROOM;
    }

    public AtomicBoolean getCanCreateRoom() {
        return canCreateRoom;
    }

    public ReentrantLock getCreatingRoomLock() {
        return creatingRoomLock;
    }


    /*-------------------------------------------------------------------------------------------*/

    //METHODS FOR MANAGE THE ROOMS

    /**
     * method to check if the room (given in input) is full or not
     *
     * @param room -> the class Room to be checked calling his method
     * @return
     */
    private boolean roomFull(Room room) {
        return room.isFull();
    }

    /**
     * scroll all the occupied room ad check if are all full
     * if not returned false so The Lobby know that can assign
     * the client to the first able room
     *
     * @return
     */
    private boolean allRoomsFull() {
        for (Room room : notEmptyRoom) {
            if (!roomFull(room)) {
                return false;
            }
        }
        return true;
    }

    /**
     * after checking if there is an available room this method returns the first room
     * available in this lobby
     */
    private Room firstFreeRoom() throws NotFreeRoomAvailableError {
        if (allRoomsFull()) {
            throw new NotFreeRoomAvailableError("Error, there are not an available room right now");
        } else {
            for (Room room : this.notEmptyRoom) {
                if (!roomFull(room)) {
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
    private void updateRoomCounter() {
        this.numberOfRooms = this.notEmptyRoom.size();
    }

    /**
     * private method to find a Room in the not empty List by its ID
     *
     * @param roomId
     * @return
     */
    private Room findRoomByID(String roomId) {
        for (Room room : this.notEmptyRoom) {
            if (room.getRoomID().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    /**
     * method used to create a new Room by one user
     * using lock to stop a possible player that wants to inizialized a new
     * room in the same moment---> he have to wait
     *
     * @param username
     * @param gameMode
     */
    private void createNewRoom(String username, String gameMode, VirtualView userVV) {
        creatingRoomLock.lock();
        try {
            usersAssigned.add(username);
            Room newRoom = null;
            if (idRoomToUse.isEmpty()) {
                newRoom = new Room("Room  #" + this.numberOfRooms);
            }
            else {
                newRoom = new Room("Room  #" + idRoomToUse.remove(0));
            }
            notEmptyRoom.add(newRoom);
            updateRoomCounter();  //update the actual number of the rooms occupied

            if (gameMode.equals("0")) {
                //set the attribute of the Room true because the Client asked to play in Solo Mode
                newRoom.setSoloMode(true);
                //and the size of the room to 1
                newRoom.setSIZE(1);
                //add the VV of the player yet in add user
                //newRoom.addVV(username, userVV);
            } else {
                //setting the room size asking that to the client
                canCreateRoom.set(false); //now this client is creating a new room, so I set this parameter to false and not letting anyone else to do the same now
                newRoom.setSoloMode(false);
                VRoomSizeRequestMsg requestMsg = new VRoomSizeRequestMsg("Ask the client the size of the room where he wants to play", username, newRoom.getRoomID());
                notifyAllObserver(ObserverType.VIEW, requestMsg);
            }

            newRoom.addUser(username, userVV);


        } catch (LimitExceededException e) {
            e.printStackTrace();
        } finally {
            creatingRoomLock.unlock();
        }

    }

    /*-------------------------------------------------------------------------------------------*/

    //METHODS FOR THE INITIALIZATION

    /**
     * check if this username can start the game, so is in a full room and the Controller
     * can start to inizialized the game
     *
     * @param username the player ask a network
     * @return true if the controller can start, false otherwise
     */
    public boolean canInitializeGameFor(String username) {
        System.out.println(usersAssigned.toString());
        if (usersAssigned.contains(username)) {
            //the user has been assigned to a Room
            System.out.println("PLEASEEEE");
            System.out.println(usersAssigned.toString());
            Room userRoom = null;
            try {
                //find his room
                userRoom = findUserRoom(username);
            } catch (NotFreeRoomAvailableError e) {
                e.printStackTrace();
            }
            //send to the client the number of players in his room and ask if it's ok to start
            //VRoomSizeRequestMsg request = new VRoomSizeRequestMsg()
            if (userRoom != null && userRoom.isFull() && !userRoom.isWaiting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * find the room where the user is assigned
     *
     * @param username
     * @return
     * @throws NotFreeRoomAvailableError
     */
    private Room findUserRoom(String username) throws NotFreeRoomAvailableError {
        for (Room r : this.notEmptyRoom) {
            if (r.getPlayersId().contains(username))
                return r;
        }
        throw new NotFreeRoomAvailableError("Username not found in the list of the Rooms in the Lobby!");
    }

    /**
     * called by Lobby after check whit this class method (can Initialized Game for..)
     *
     * @param username
     */
    public void startInitializationOfTheGame(String username) {
        System.out.println("START IN LOBBY");
        try {
            findUserRoom(username).initializedGame();
        } catch (NotFreeRoomAvailableError | InvalidActionException error) {
            error.printStackTrace();
        }
    }



    /*-----------------------------------------------------------------------------------------------------------------*/
    //HANDLE EVENTS


    /**
     * this method respond at a message from the client that ask for a network
     * here is needed a check of the username given:
     * it has to be unique
     * then assign to this client the room where to play
     * (the client before has to say if he want to play in Solo mode or Multi player)
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        String gameMode = msg.getGameSize();
        String convertedGameMode = convertStringForMode(gameMode);
        System.out.println("[Lobby] request of a connection from: " + msg.getIP() + " on @" + msg.getPort() + " given  \"" + msg.getUsername() + "\" as username " +
                "asking for playing in " + convertedGameMode + " Game!");

        //check if the username is not used yet
        if (usersAssigned.contains(msg.getUsername())) {
            //check if is a user disconnected that is trying to reconnect
            try {
                Room room = findUserRoom(msg.getUsername());
                if (room.getPlayerByUsername(msg.getUsername())!=null && room.getPlayerByUsername(msg.getUsername()).isDisconnected()) {
                    room.reconnectPlayer(msg.getUsername(), msg.getVV());
                    detachObserver(ObserverType.VIEW, room.getListOfVirtualView().get(msg.getUsername()));
                } else {
                    //username used yet
                    sendNackConnectionRequest(msg, "USER_NOT_VALID");
                    System.out.println("Error, username \"" + msg.getUsername() + "\" not valid because is taken already");
                }
            } catch (NotFreeRoomAvailableError error) {
                error.printStackTrace();
            }

        } else {
            //now check if there is a room available or if all are occupied
            //if the client wants to play in Solo mode check only if can create a new room
            if (!allRoomsFull() && !gameMode.equals("0")) {
                //there is an available room
                usersAssigned.add(msg.getUsername());
                Room room = null;
                try {
                    room = firstFreeRoom();
                    if (room != null && !roomFull(room)) {
                        room.addUser(msg.getUsername(), msg.getVV());
                        if (room.getNumberOfPlayer() > 1) {
                            VRoomInfoMsg requestMsg = new VRoomInfoMsg("You have been added to the room", room.getPlayersId(), room.getRoomID(), room.getNumberOfPlayer(), room.getSIZE());
                            notifyAllObserver(ObserverType.VIEW, requestMsg);
                        }
                        //System.out.println("user added: " +room.getPlayersId().get(1));
                    }
                } catch (NotFreeRoomAvailableError | LimitExceededException e) {
                    e.printStackTrace();
                }
            } else if (!creatingRoomLock.isLocked()) {
                //all occupied room are full but one can be created, or Solo Mode
                if (gameMode.equals("0") || canCreateRoom.get()) {
                    System.out.println("HHHHHHH");
                    System.out.println(usersAssigned.toString());
                    createNewRoom(msg.getUsername(), gameMode, msg.getVV());
                }
            } else if (this.numberOfRooms == MAX_NUMBER_ROOM) {
                //all rooms are full and is not possible to create a new room
                sendNackConnectionRequest(msg, "FULL_SIZE");
                System.out.println("Error: all rooms are Full, Sorry try later!");
            } else if (!canCreateRoom.get()) {
                //if you arrived here this means that all rooms are occupied but the server is not full (#room==16) so the reason is that someone else is creating a new room
                sendNackConnectionRequest(msg, "WAIT");
                System.out.println("Error: someone else is creating a room, please wait a few seconds!");
            }
        }
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    /**
     * msg from the client with the size of the room where he (the first) wants to play
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //System.out.println("setting size room in Lobby");         DEBUGGING
        String roomId = msg.getRoomID();
        Room room = findRoomByID(roomId);
        if (room != null) {
            room.setSIZE(msg.getRoomSize());
            VRoomInfoMsg requestMsg = new VRoomInfoMsg("The room has been created", room.getPlayersId(), room.getRoomID(), room.getNumberOfPlayer(), room.getSIZE());
            notifyAllObserver(ObserverType.VIEW, requestMsg);
        }

    }

    /**
     * notification from VV that a room is full so the initialization has started
     * so the attribute is setted to true because a new room now can be initialized
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        creatingRoomLock.lock();
        try {
            if (!findUserRoom(msg.getPlayers()).isSoloMode()) {
                canCreateRoom.set(true);
            }
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        } finally {
            creatingRoomLock.unlock();
        }

    }

    /**
     * creating the Error message to send to the client, after notify the view
     *
     * @param msg
     */
    private void sendNackConnectionRequest(CConnectionRequestMsg msg, String errorInformation) {
        VNackConnectionRequestMsg nackMsg = new VNackConnectionRequestMsg("Connection cannot be established ", msg.getPort(), msg.getIP(), msg.getUsername(), errorInformation);
        notifyAllObserver(ObserverType.VIEW, nackMsg);
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //send to Initialized Controller, so find the room
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //System.out.println(" try found room ");
        try {
            Room room = findUserRoom(msg.getPlayers().get(0));
            //System.out.println("found room " + room);
            room.startFirstTurn();
            room.detachInitializedC();
            //room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError | InvalidActionException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        //send to TurnController by Room
        try {
            Room room = findUserRoom(msg.getUsername());
            //room.detachInitializedC();
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //not implemented here (in Virtual View)
    }


    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //send to Initialized Controller or TurnController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }


    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //send to TurnController by Room and then to ActionController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        //send to TurnController by Room and then to ActionController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //send to TurnController by Room and then to ActionController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //send to TurnController by Room and then to ActionController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        //send to TurnController by Room and then to ActionController and PPController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }


    @Override
    public void receiveMsg(CStopPPMsg msg) {

        //send to TurnController by Room and then to ActionController and PPController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //send to TurnController by Room
        try {
            Room room = findUserRoom(msg.getUsernameAsking());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        System.out.println("DISCONNECT IN LOBBY");
        //send to TurnController by Room, to set him disconnected
        try {
            Room room = findUserRoom(msg.getUsernameDisconnected());
            room.disconnectPlayer(msg.getUsernameDisconnected());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }


    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        creatingRoomLock.lock();
        try {
            Room room = findUserRoom(msg.getUsername());
            for (String user: room.getPlayersId()) {
                usersAssigned.remove(user);
            }
            Map<String, VirtualView> roomVV = room.getListOfVirtualView();
            for (VirtualView vv: roomVV.values()) {
                detachObserver(ObserverType.VIEW, vv);
            }
            room.removeVV();
            //remove the room from the not empty ones
            idRoomToUse.add(room.getIntId());
            System.out.println("Remove this Room in LOBBY: " +room);
            System.out.println(idRoomToUse);
            System.out.println(numberOfRooms);
            notEmptyRoom.remove(room);
            updateRoomCounter();

            System.out.println(numberOfRooms);
            canCreateRoom.set(true);

        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        } finally {
            creatingRoomLock.unlock();
        }
    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        /* because of an end of a game, remove the players room and
         * the usernames from the list
        String winner = msg.getWinnerUsername();

        try {
            Room room = findUserRoom(msg.getWinnerUsername());
            notEmptyRoom.remove(room);
            usersAssigned.remove(msg.getWinnerUsername());
            for (Player player : msg.getLosersUsernames()) {
                usersAssigned.remove(player.getUsername());
            }
            updateRoomCounter();
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }*/
    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {

    }

    @Override
    public void receiveMsg(CNewStartMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //send to TurnController by Room and then to ActionController and PPController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //send to TurnController by Room and then to ActionController
        try {
            Room room = findUserRoom(msg.getUsername());
            room.notifyAllObserver(ObserverType.CONTROLLER, msg);
        } catch (NotFreeRoomAvailableError error) {
            error.printStackTrace();
        }
    }



    /*----------------------------------------------------------------------------------------------------------------*/

    //AUXILIARY PRIVATE METHODS

    /**
     * because the client type 0 or 1 to choose the game mode
     * convert the number to print out the right msg
     *
     * @param mode
     * @return
     */
    private String convertStringForMode(String mode) {
        switch (mode) {
            case "0":
                return "Solo Mode";
            case "1":
                return "Multiplayer Mode";
        }
        return null;
    }


}
