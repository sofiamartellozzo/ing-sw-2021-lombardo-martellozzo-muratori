package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.controller.Lobby;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Virtual View class, it simulates the View in the Server side and each client has associated an unique instance of this class.
 * The class decides which events need to be send to the client and which to the server.
 */
public class VirtualView extends Observable implements ControllerObserver, ViewObserver {

    private String username;
    private String gameMode;    //0 for solo mode, 1 for multiplayer

    private Lobby lobby;        //the game Lobby on this Server

    private ClientHandler client;

    private boolean inLobby;           //if true this client is waiting in the lobby

    /**
     * {@link AtomicBoolean} for the first connection, is true if the connection has been accepted and, after the disconnection, Lobby has to be clean.
     */
    private final AtomicBoolean userConnected;
    /**
     * {@link ReentrantLock} lock for first connection
     */
    private final ReentrantLock connectionLock = new ReentrantLock();


    public VirtualView(ClientHandler client) {
        this.client = client;    //specific for this VV
        this.lobby = Lobby.getInstance();  //create a Lobby or get one created yet
        attachObserver(ObserverType.CONTROLLER, lobby);  //attach it as it is a controller observer
        lobby.attachObserver(ObserverType.VIEW, this); //listening each other, the VV is like a View observer too

        userConnected = new AtomicBoolean(false);
    }

    /**
     * send a message to the client throw his Client Handler
     */
    private void sendToClient(GameMsg msg) {
        client.sendMsg(msg);
    }



    /**
     * receiving the message of a Request of connection by the client
     * this has to be send to the Controller
     *
     * @param msg
     */
    public void receiveMsg(VVConnectionRequestMsg msg){

        //save the username of the client associated to this virtual view
        username = msg.getUsername();
        //save the mode the client choose
        gameMode = msg.getGameSize();
        //set true that the user is in the Lobby, if something will went wrong change it to false!
        inLobby = true;

        connectionLock.lock();
        userConnected.set(true);
        connectionLock.unlock();

        //create the message to send to the controller with the username the client send
        CConnectionRequestMsg requestToController = new CConnectionRequestMsg("Msg from [Virtual view] in respond of a request of connection by a client", client.getUserIP(), client.getUserPort(), username, gameMode);
        requestToController.setVirtualView(this);
        // "send" the message to the controller
        notifyAllObserver(ObserverType.CONTROLLER, requestToController);

        tryToStartGame();
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg){
        System.out.println("setting size room in VV");

        connectionLock.lock();
        userConnected.set(true);
        connectionLock.unlock();

        notifyAllObserver(ObserverType.CONTROLLER, msg);

    }

    /**
     * msg from himself to forward to the client (CLI or GUI)
     * @param msg
     */
    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        if (msg.getPlayers().equals(username)){
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //send to Initialized Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        //send to Turn Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //not implemented here (in Initialized Controller)
    }


    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //send to Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);

    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //send to Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //send to Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }



    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        //send to Production Power Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //send to Lobby(Room) and Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CStandardPPResponseMsg msg) {

    }




    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //not implemented here (in Lobby)
    }

    /**
     * this msg is received from the controller when the username is not valid
     * or there are not a room available
     * then forward the event to the client (as a notify)
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VNackConnectionRequestMsg msg) {

        /* send this message (notify) to the client */
        if (msg.getUsername().equals(this.username)) {
            //set the boolean that specify if the user is waiting in the lobby to false
            inLobby = false;

            sendToClient(msg);
        }
    }

    /**
     * msg recived from the controller (Lobby) asking to the client the size of the room
     * he want to play in
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            //the connection is not on yet so

            connectionLock.lock();
            userConnected.set(false);
            connectionLock.unlock();

            //and then send the request to the client
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {

    }

    /**
     * when the game and a turn started, the server need to ask a client which action
     * wants to make, so here the VV will forward the msg to the client
     * (CLI or GUI) will print out the request to make the client choose the Action
     * @param msg {VChooseActionTurnRequest}
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }


    /**
     * msg from the controller (Initialized) when the client has to chose 2 leader card
     * from a 4 cards deck
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {
        //check if the username is mine

        if (msg.getUsername().equals(this.username)) {
            /* send this message (notify) to the client */
            sendToClient(msg);
        }
    }

    /**
     * msg received when is asked to the client to chose a Resource
     * and the depots where to store it
     *
     * @param msg sended to client
     */
    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {
        //check if the username is mine
        if (msg.getUsername().equals(this.username)) {
            /* send this message (notify) to the client */
            System.out.println("choosing in VV");
            sendToClient(msg);
        }
    }



    /**
     * receive this msg from controller when the player chose an invalid depots
     * because he cannot store the resource he chose (Like at first when the resource are
     * given to the players in multiple Mode, not the first player!)
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {
        if (msg.getUsername().equals(this.username)) {

            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {
        //send to ALL players, in CLI check the username--> if not same "him increased"
        if (msg.getUsernameIncreased().equals(this.username) || msg.getAllPlayerToNotify().contains(this.username)) {
            sendToClient(msg);
        }
    }

    /**
     * send to the client this msg to ask which development card wants to buy
     *
     * @param msg-> contains all the available position of the table where to buy
     */
    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }

    }

    /**
     * in this msg (specific of one client) is a request of the player to
     * move the resources from one depots to another
     * @param msg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is a request of the player to
     * buy from the market in the turn
     * @param msg
     */
    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is after a request of the player to
     * buy from the market in the turn, so the server now need to know
     * in which depot store it
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is after a request of the player to
     * activate a Production Power, so ask his which one activate
     * @param msg
     */
    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * after the choice of the player to activate PP 0
     * @param msg
     */
    @Override
    public void receiveMsg(VStandardPPRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * after the choice of the player to activate special PP
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseSingleResourceToPutInStrongBoxRequestMsg msg) {
        if (msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }


    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }

    /**
     * this msg contains the info of the Action Token activated at the end of the turn
     * check if is Solo Mode in addiction of the right username
     * @param msg
     */
    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {
        if (msg.getUsername().equals(this.username) && gameMode.equals(0)){
            sendToClient(msg);
        }
    }




    /*---------------------------------------------------------------------------------------------------------------------*/

    //METHODS PRIVATE AUXILIARY

    /**
     * called by VV after recived a Msg of a Connection Request by the client
     */
    private void tryToStartGame() {
        connectionLock.lock();
        try{
            //check if the connection is ON and ask to the Lobby if the Game can start
            if (userConnected.get() && lobby.canInitializeGameFor(this.username)) {
                CVStartInitializationMsg msg = new CVStartInitializationMsg("A room is full so starting the initialization", username );
                notifyAllObserver(ObserverType.CONTROLLER, msg);
                lobby.startInitializationOfTheGame(username);
            }
        } finally {
            connectionLock.unlock();
        }

    }
}
