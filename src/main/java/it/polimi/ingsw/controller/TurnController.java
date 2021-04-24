package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.controller.factory.ResourcesSupplyFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.controller.factory.PersonalSoloBoardFactory;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        for (int i=0; i<3; i++){
            checkPopesFavorTile.add(PopesFavorTileReview.UNCHECKED);
        }
    }

    /**
     * settings the boolean parameter for check if is Solo Mode or not
     * */
    private void isSoloMode(){
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
        if (spt.checkEndGame()){
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
        VChooseActionTurnMsg msg = new VChooseActionTurnMsg("A new turn is started, make your move:", player.getUsername(), pt.getAvailableAction());
        notifyAllObserver(ObserverType.VIEW, msg);
        if (pt.checkEndTurn()){
            nextTurn();
        }
    }

    /**
     * when a player end his turn, this method is called to set the next player
     * and create a new turn for him
     */
    private void nextTurn(){
        if (this.currentTurnIndex == this.numberOfPlayer){
            this.currentTurnIndex = 1;
        }
        else {
            this.currentTurnIndex++;
        }
        Player nextPlayer = (Player) this.turnSequence.get(currentTurnIndex);
        startPlayerTurn(nextPlayer);
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

        printTurnCMesssage("New action of the game");
        ActionController controller = new ActionController(currentPlayer, currentTurnIstance, boardManager);
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
