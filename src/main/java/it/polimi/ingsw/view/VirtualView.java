package it.polimi.ingsw.view;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.controller.Lobby;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;

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


    public VirtualView(ClientHandler client) {
        this.client = client;    //specific for this VV
        this.lobby = Lobby.getInstance();  //create a Lobby or get one created yet
        attachObserver(ObserverType.CONTROLLER, lobby);  //attach it as it is a controller observer
        lobby.attachObserver(ObserverType.VIEW, this); //listening each other, the VV is like a View observer too
    }

    /**
     * send a message to the client throw his Client Handler
     */
    private void sendToClient(GameMsg msg){
        client.sendMsg(msg);
    }



    /**
     * receiving the message of a Request of connection by the client
     * this has to be send to the Controller
     * @param msg
     */
    public void receiveMsg(VConnectionRequestMsg msg){

        //save the username of the client associated to this virtual view
        username = msg.getUsername();
        //save the mode the client choose
        gameMode = msg.getGameSize();
        //create the message to send to the controller with the username the client send
        CConnectionRequestMsg requestToController = new CConnectionRequestMsg("Msg from [Virtual view] in respond of a request of connection by a client",client.getUserIP(), client.getUserPort(), username, gameMode);
        // "send" the message to the controller
        notifyAllObserver(ObserverType.CONTROLLER, requestToController);

        tryToStartGame();
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //not implemented here (in Initialized Controller)
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //not implemented here (in Initialized Controller)
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        //not here
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

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //not implemented here (in Lobby)
    }

    /**
     * this msg is received from the controller when the username is not valid
     * or there are not a room available
     * then forward the event to the client (as a notify)
     * @param msg
     */
    @Override
    public void receiveMsg(CNackConnectionRequestMsg msg) {
        //set the boolean that specify if the user is waiting in the lobby to false
        inLobby = false;

        /* send this message (notify) to the client */
        sendToClient(msg);
    }

    /**
     * msg recived from the controller (Lobby) asking to the client the size of the room
     * he want to play in
     * @param msg
     */
    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {
        sendToClient(msg);
    }

    /**
     * msg from the controller (Initialized) when the client has to chose 2 leader card
     * from a 4 cards deck
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg){
        //check if the username is mine
        if (msg.getUsername().equals(this.username)){
            /* send this message (notify) to the client */
            sendToClient(msg);
        }
    }

    /**
     * msg received when is asked to the client to chose a Resource
     * and the depots where to store it
     * @param msg
     */
    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {
        //check if the username is mine
        if (msg.getUsername().equals(this.username)){
            /* send this message (notify) to the client */
            sendToClient(msg);
        }
    }

    @Override
    public void receiveMsg(VNotifyAllIncreasePositionMsg msg) {
        //ce pensiamo doamni
    }

    /**
     * receive this msg from controller when the player chose an invalid depots
     * because he cannot store the resource he chose (Like at first when the resource are
     * given to the players in multiple Mode, not the first player!)
     * @param msg
     */
    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {
        sendToClient(msg);
    }

    /**
     * send to the client this msg to ask wich development card wants to buy
     * @param msg-> contains all the available position of the table where to buy
     */
    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {
        sendToClient(msg);

    }

    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }




    /*---------------------------------------------------------------------------------------------------------------------*/

     //METHODS PRIVATE AUXILIARY

    private void tryToStartGame(){
        //check if the connection is ON and ask to the Lobby if the Game can start
        if (lobby.canInitializeGameFor(this.username)){
            lobby.startInitializationOfTheGame(username);
        }
    }
}
