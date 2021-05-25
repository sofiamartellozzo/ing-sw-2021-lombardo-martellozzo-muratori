package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MessageHandler;
import it.polimi.ingsw.controller.OfflineLobby;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.model.PlayerInterface;
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
    private OfflineLobby offLobby; //in offline mode

    private ClientHandler client;

    private boolean inLobby;           //if true this client is waiting in the lobby

    private MessageHandler messageHandler;      //for offline mode

    //private ViewObserver viewMode;

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

    /* the constructor for the offline mode*/
    public VirtualView(String username, MessageHandler messageHandler) {
        this.username = username;
        this.gameMode = "0";

        this.messageHandler = messageHandler;

        this.offLobby = new OfflineLobby(username);
        attachObserver(ObserverType.CONTROLLER, offLobby);
        offLobby.attachObserver(ObserverType.VIEW, this);

        userConnected = new AtomicBoolean(false);
    }


    /**
     * send a message to the client throw his Client Handler
     */
    private void sendToClient(GameMsg msg) {
        if (offLobby == null) {
            client.sendMsg(msg);
        }
        else{
            //notifyAllObserver(ObserverType.VIEW,msg);
            messageHandler.receivedMsgForView(msg);
        }
    }


    /**
     * receiving the message of a Request of connection by the client
     * this has to be send to the Controller
     *
     * @param msg
     */
    public void receiveMsg(VVConnectionRequestMsg msg) {

        //save the username of the client associated to this virtual view
        username = msg.getUsername();
        //save the mode the client choose
        gameMode = msg.getGameSize();
        //set true that the user is in the Lobby, if something will went wrong change it to false!
        inLobby = true;

        if (!msg.getMsgContent().equals("OFFLINE")) {

            connectionLock.lock();
            userConnected.set(true);
            connectionLock.unlock();

            //create the message to send to the controller with the username the client send
            CConnectionRequestMsg requestToController = new CConnectionRequestMsg("Msg from [Virtual view] in respond of a request of connection by a client", client.getUserIP(), client.getUserPort(), username, gameMode);
            requestToController.setVirtualView(this);
            // "send" the message to the controller
            notifyAllObserver(ObserverType.CONTROLLER, requestToController);

        } else {
            //offline Mode
            //create the message to send to the controller with the username the client send
            CConnectionRequestMsg requestToController = new CConnectionRequestMsg("Msg from [Virtual view] in respond of a request of connection by a client in offline mode", username, gameMode);
            requestToController.setVirtualView(this);
            // "send" the message to the controller
            notifyAllObserver(ObserverType.CONTROLLER, requestToController);
        }

        tryToStartGame();
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        System.out.println("setting size room in VV");

        connectionLock.lock();
        userConnected.set(true);
        connectionLock.unlock();

        notifyAllObserver(ObserverType.CONTROLLER, msg);

    }

    /**
     * msg from himself to forward to the client (CLI or GUI)
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        if (msg.getPlayers().equals(username)) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VServerUnableMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //send to Initialized Controller
        //System.out.println("PASSING THROW VV " + msg.getUsername());
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CGameCanStratMsg msg) {
        //send to room to start the game
        if (msg.getOnePlayer().equals(username)) {
            //System.out.println("PASSING THROW VV for initialization");
            notifyAllObserver(ObserverType.CONTROLLER, msg);
        }
    }

    @Override
    public void receiveMsg(VAnotherPlayerInfoMsg msg) {
        if (username.equals(msg.getUsernameAsking())) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VWhichPlayerRequestMsg msg) {
        if (username.equals(msg.getUsername())) {
            sendToClient(msg);
        }
    }


    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        //send to Turn Controller
        if (msg.getUsername().equals(username)) {
            notifyAllObserver(ObserverType.CONTROLLER, msg);
        }
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //send to Initialized Controller or TurnController
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //send to Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);

    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        //send to Turn Controller
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
    public void receiveMsg(CStopPPMsg msg) {
        if (this.username.equals(msg.getUsername())) {
            notifyAllObserver(ObserverType.CONTROLLER, msg);
        }
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //to Turn Controller
        if (username.equals(msg.getUsernameAsking())) {
            notifyAllObserver(ObserverType.CONTROLLER, msg);
        }
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //send to Lobby(Room) and Action Controller
        notifyAllObserver(ObserverType.CONTROLLER, msg);
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
        if (msg.getUserIp().equals(client.getUserIP()) && msg.getUserPort() == client.getUserPort() && msg.getUsername().equals(this.username)) {
            //set the boolean that specify if the user is waiting in the lobby to false
            inLobby = false;

            connectionLock.lock();
            userConnected.set(false);
            connectionLock.lock();

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
        if (msg.getPlayer().getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VRoomInfoMsg msg) {
        for (String player : msg.getPlayersId()) {
            if (player.equals(this.username)) {
                sendToClient(msg);
            }
        }
    }


    /**
     * when the game and a turn started, the server need to ask a client which action
     * wants to make, so here the VV will forward the msg to the client
     * (CLI or GUI) will print out the request to make the client choose the Action
     *
     * @param msg {VChooseActionTurnRequest}
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VWaitYourTurnMsg msg) {
        if (msg.getUsername().equals(this.username)) {
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
     * at the initialization of the game send this msg to all player
     * until all have decided the 2 Leader card and resources (if had to)
     *
     * @param msg-> from Initialized Controller
     */
    @Override
    public void receiveMsg(VWaitOtherPlayerInitMsg msg) {
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
            //System.out.println("choosing in VV");
            sendToClient(msg);
        }
    }

    /**
     * update to View a change in warehouse
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            /* send this message (notify) to the client */
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

    @Override
    public void receiveMsg(VUpdateDevTableMsg msg) {
        //sent it to all player
        sendToClient(msg);

    }


    /**
     * in this msg (specific of one client) is a request of the player to
     * move the resources from one depots to another
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is a request of the player to
     * buy from the market in the turn
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VUpdateMarketMsg msg) {
        for (PlayerInterface player : msg.getAllPlayers().values()) {
            if (player.getUsername().equals(this.username)) {
                sendToClient(msg);
            }
        }
    }

    @Override
    public void receiveMsg(VUpdateFaithTrackMsg msg) {
        if(msg.getUsername().equals(this.username)){
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is after a request of the player to
     * buy from the market in the turn, so the server now need to know
     * in which depot store it
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    /**
     * nitification to the client after Lorenzo increased his position
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VLorenzoIncreasedMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    /**
     * in this msg (specific of one client) is after a request of the player to
     * activate a Production Power, so ask his which one activate
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VUpdateStrongboxMsg msg) {
        if (msg.getUsername().equals(this.username)) {
            sendToClient(msg);
        }
    }


    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        sendToClient(msg);
        /*
        if (msg.getPlayerUsername().equals(this.username)) {
            sendToClient(msg);
        }*/
    }

    /**
     * this msg contains the info of the Action Token activated at the end of the turn
     * check if is Solo Mode in addiction of the right username
     *
     * @param msg
     */
    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {

        if (msg.getUsername().equals(this.username) && gameMode.equals("0")) {

            sendToClient(msg);
        }
    }




    /*---------------------------------------------------------------------------------------------------------------------*/

    //METHODS PRIVATE AUXILIARY

    /**
     * called by VV after received a Msg of a Connection Request by the client
     */
    private void tryToStartGame() {
        if (offLobby == null) {
            connectionLock.lock();
            try {
                //check if the connection is ON and ask to the Lobby if the Game can start
                if (userConnected.get() && lobby.canInitializeGameFor(this.username)) {
                    CVStartInitializationMsg msg = new CVStartInitializationMsg("A room is full so starting the initialization", username);
                    notifyAllObserver(ObserverType.CONTROLLER, msg);
                    lobby.startInitializationOfTheGame(username);
                }
            } finally {
                connectionLock.unlock();
            }
        } else {
            CVStartInitializationMsg msg = new CVStartInitializationMsg("A room is full so starting the initialization", username);
            notifyAllObserver(ObserverType.CONTROLLER, msg);
            //lobby.startInitializationOfTheGame(username);
        }

    }
}
