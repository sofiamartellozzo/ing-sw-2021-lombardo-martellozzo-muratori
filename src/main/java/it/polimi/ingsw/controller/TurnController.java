package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
* SOFI*/

/**
 * this class is the part of the controller that arraing the start of the game, and set the turn sequence
 * it is specific for a single room, to manage
 */

public class TurnController extends Observable implements ControllerObserver {
    /* all player in a Room */
    private HashMap<Integer, PlayerInterface> turnSequence;
    private int numberOfPlayer;

    private int currentTurnIndex;
    private PlayerTurn currentTurnIstance;
    private Player currentPlayer;

    private ArrayList<PopesFavorTileReview> checkPopesFavorTile;

    /* the solo player */
    private SoloPlayer singlePlayer;
    /* same as in Room class, to check immediately */
    private boolean isSoloMode;
    /*the attribute for the class that handle the turn*/
    private SoloPlayerTurn currentSoloTurnIstance;

    /* the parts of the game that all the player have in common*/
    private BoardManager boardManager;

    /*set true when is called the last turn*/
    private boolean lastTurns;

    /*at the end of the game there is a last turn for each player except the one that ended it, this keep the track of it*/
    private int numberOfLastTurn;

    /* Constructor of the class */
    public TurnController(HashMap<Integer, PlayerInterface> players, BoardManager boardManager) {

        this.turnSequence = new HashMap<>();
        setTurnSequence(players);
        checkIfSoloMode();
        this.boardManager = boardManager;
        this.currentTurnIndex = 1;
        this.currentPlayer = (Player) players.get(1);
        setVaticanSectionUnchecked();
        lastTurns = false;
        numberOfLastTurn = numberOfPlayer-1;
    }

    /* override of the constructor, for the Solo Mode */
    public TurnController(SoloPlayer player, BoardManager boardManager) {

        this.turnSequence = null;
        this.singlePlayer = player;
        this.numberOfPlayer = 1;
        checkIfSoloMode();
        this.boardManager = boardManager;
        this.currentTurnIndex = 1;
        setVaticanSectionUnchecked();
    }

    /**
     * setting the turn sequence, created in the room
     * @param players
     */
    private void setTurnSequence(HashMap<Integer, PlayerInterface> players){
        turnSequence = players;
        numberOfPlayer = players.keySet().size();
    }

    /**
     * at the initialization of the class set all vatican section to unchecked
     */
    private void setVaticanSectionUnchecked(){
        checkPopesFavorTile = new ArrayList<>();
        for (int i=0; i<3; i++){
            checkPopesFavorTile.add(PopesFavorTileReview.UNCHECKED);
        }
    }

    /**
     * settings the boolean parameter for check if is Solo Mode or not
     * */
    private void checkIfSoloMode(){
        if (numberOfPlayer == 1){
            isSoloMode = true;
        }
        else {
            isSoloMode = false;
        }
    }

    /**
     * method to initialized the game, creating the spaces and setting everithing
     * exept the Board Manager the rest is setted differently if the number of player
     * are one or more : so solo mode or multiple mode
     * @throws InvalidActionException
     */
    public void gamePlay() throws InvalidActionException{
        //where the turn game starts
        printTurnCMesssage("A Turn can start...");
        if (isSoloMode){
            startSoloPlayerTurn(this.singlePlayer);
        }
        else{
            startPlayerTurn(this.currentPlayer);
        }
    }

    /**
     * start the game in solo mode, so create the Solo Player Turn that manage the different
     * action the player can do
     * @param player
     */
    private void startSoloPlayerTurn(SoloPlayer player) throws InvalidActionException {
        printTurnCMesssage("You choose to play in Solo Mode, ready to fight against Lorenzo il Magnifico?");
        player.setPlaying(true);
        SoloPlayerTurn spt = new SoloPlayerTurn(player, this.boardManager);
        currentSoloTurnIstance = spt;
        if (currentTurnIndex > 1 && spt.checkEndGame()){
            startSoloPlayerTurn(player);
        }
    }

    /**
     * start a new Turn of a Player in multiple mode, called any time a new turn start
     * first after setting all game, then at the end of the others player turn
     * @param player
     */
    private void startPlayerTurn(Player player){
        printTurnCMesssage("New Turn is ready to start, and you?...");
        player.setPlaying(true);
        PlayerTurn pt = new PlayerTurn(player, this.boardManager);
        currentTurnIstance = pt;
        //send the msg to the client, to choose the action he want to make
        VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), pt.getAvailableAction());
        notifyAllObserver(ObserverType.VIEW, msg);
        if (currentTurnIndex > 1 && pt.checkEndTurn()){
            nextTurn();
        }
    }

    /**
     * when a player end his turn, this method is called to set the next player
     * and create a new turn for him
     */
    protected void nextTurn(){
        if (this.currentTurnIndex == this.numberOfPlayer){
            this.currentTurnIndex = 1;
        }
        else {
            this.currentTurnIndex++;
        }
        Player nextPlayer = (Player) this.turnSequence.get(currentTurnIndex);
        startPlayerTurn(nextPlayer);
    }

    /**
     * check if one or more players are arrived at the end of the game, in that case create a special controller
     * to manage the last turn of the game
     */
    private void checkEndGame(){
        //check the conditions fot the last turn of the game
        currentPlayer.endTurn();
        boolean checkEnd = false;
        for (Integer key: turnSequence.keySet()) {
            //if one player is arrived to the end of the faithTrack the checkEnd is set to true
            if(turnSequence.get(key).checkEndGame()) {
                checkEnd = true;
            }
        }
        if (checkEnd){
            int keyCurrentP = 0;
            for (Integer key: turnSequence.keySet()) {
                if(turnSequence.get(key).getUsername().equals(currentPlayer.getUsername()))
                {
                   keyCurrentP = key;
                }

            }
            numberOfLastTurn = numberOfPlayer - keyCurrentP;      //it contains the number representing the total of last turn games
            lastTurns = true;
        }
    }

    /*check if someone is in the pop's favor tile...*/

    private void printTurnCMesssage(String messageToPrint){
        System.out.println(messageToPrint);
    }

    /*------------------------------------------------------------------------------------------------------------------*/

            //WAIT THE MSG FROM THE CLIENT


    /**
     * receive the msg from the client with the Action he choose
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        if(msg.getUsername().equals(currentPlayer.getUsername())) {
            if (!msg.getActionChose().equals(TurnAction.END_TURN)) {
                printTurnCMesssage("New action of the game");
                ActionController controller = new ActionController(currentPlayer, currentTurnIstance, boardManager);
            } else {
                if(!lastTurns) {
                    checkEndGame();
                    nextTurn();
                }else{
                    this.numberOfLastTurn--;    //decrease the number of players that have to play
                    if(numberOfLastTurn > 0) {
                        nextTurn();
                    }else {                    // if the game is over EndGameController will be instantiated to count all the victory points
                        EndGameController endGameController = new EndGameController(turnSequence,this);
                        try {
                            endGameController.startCounting();
                        } catch (InvalidActionException e) {
                            e.printStackTrace();
                        }
                        attachObserver(ObserverType.CONTROLLER, endGameController);
                    }

                }
            }
        }
    }

    /**
     * handle the event in which one player decided to discard a resource from the market
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        for (Integer key: turnSequence.keySet()) {
            if (!turnSequence.get(key).getUsername().equals(msg.getUsername())){
                //not the player that discarded the resource
                turnSequence.get(key).increasePosition();
                VNotifyAllIncreasePositionMsg notify = new VNotifyAllIncreasePositionMsg("this player increased his position because of another player", turnSequence.get(key).getUsername(), 1);
                notifyAllObserver(ObserverType.VIEW, notify);
            }
        }

        //check end turn (because all player has increased their position of 1
    }

    @Override
    public void receiveMsg(CChooseResourceResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg) {

    }

    /*------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //IN ACTIONCONTROLLER
    }


    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //IN ACTIONCONTROLLER
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //IN ACTIONCONTROLLER
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

    }


    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(VConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //not here
    }


}
