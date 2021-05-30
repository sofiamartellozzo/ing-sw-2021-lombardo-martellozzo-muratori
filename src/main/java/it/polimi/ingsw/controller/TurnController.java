package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    /* list of VV of the players*/
    private Map<String, VirtualView> virtualView;

    /*refereces to Action Controller if is on*/
    private ActionController actionController;

    /* Constructor of the class */
    public TurnController(HashMap<Integer, PlayerInterface> players, BoardManager boardManager, Map<String, VirtualView> virtualView) {

        this.turnSequence = new HashMap<>();
        setTurnSequence(players);
        this.currentPlayer = (Player) players.get(1);
        lastTurns = false;
        numberOfLastTurn = numberOfPlayer - 1;

        commonSettings(boardManager, virtualView);

    }

    /* override of the constructor, for the Solo Mode */
    public TurnController(SoloPlayer player, BoardManager boardManager, Map<String, VirtualView> virtualView) {

        this.turnSequence = null;
        this.singlePlayer = player;
        this.numberOfPlayer = 1;

        commonSettings(boardManager, virtualView);

    }

    /* settings for both constructor */
    private void commonSettings(BoardManager boardManager, Map<String, VirtualView> virtualView) {
        checkIfSoloMode();
        this.boardManager = boardManager;
        this.currentTurnIndex = 1;
        setVaticanSectionUnchecked();
        this.virtualView = virtualView;
        attachAllVV();
    }

    /**
     * setting the turn sequence, created in the room
     *
     * @param players
     */
    private void setTurnSequence(HashMap<Integer, PlayerInterface> players) {
        turnSequence = players;
        numberOfPlayer = players.keySet().size();
        System.out.println("number of players " + numberOfPlayer + " index " + players.keySet());
    }

    private Player getPlayerByUsername(String username) {
        for (PlayerInterface p : turnSequence.values()) {
            if (p.getUsername().equals(username)) {
                return (Player) p;
            }
        }
        return null;
    }

    /**
     * at the initialization of the class set all vatican section to unchecked
     */
    private void setVaticanSectionUnchecked() {
        checkPopesFavorTile = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            checkPopesFavorTile.add(PopesFavorTileReview.UNCHECKED);
        }
    }

    /**
     * settings the boolean parameter for check if is Solo Mode or not
     */
    private void checkIfSoloMode() {
        if (numberOfPlayer == 1) {
            isSoloMode = true;
        } else {
            isSoloMode = false;
        }
    }

    /**
     * attach all VV of the players so this class can notify them
     */
    private void attachAllVV() {
        for (String username : virtualView.keySet()) {
            attachObserver(ObserverType.VIEW, virtualView.get(username));
        }
    }

    /**
     * method to initialized the game, creating the spaces and setting everithing
     * exept the Board Manager the rest is setted differently if the number of player
     * are one or more : so solo mode or multiple mode
     *
     * @throws InvalidActionException
     */
    public void gamePlay() throws InvalidActionException {
        //where the turn game starts
        printTurnCMesssage("A Turn can start...");
        if (isSoloMode) {
            startSoloPlayerTurn(this.singlePlayer);
        } else {
            startPlayerTurn(this.currentPlayer);
        }
    }

    /**
     * start the game in solo mode, so create the Solo Player Turn that manage the different
     * action the player can do
     *
     * @param player
     */
    private void startSoloPlayerTurn(SoloPlayer player) throws InvalidActionException {
        printTurnCMesssage("The client \"" + player.getUsername() + "\" choose to play in Solo Mode, starting his turn");
        player.setPlaying(true);
        SoloPlayerTurn spt = new SoloPlayerTurn(player, this.boardManager);
        spt.removeAction(TurnAction.SEE_OTHER_PLAYER);
        currentSoloTurnIstance = spt;
        VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("Start a new turn", player.getUsername(), spt.getAvailableAction());
        notifyAllObserver(ObserverType.VIEW, msg);
    }

    /**
     * start a new Turn of a Player in multiple mode, called any time a new turn start
     * first after setting all game, then at the end of the others player turn
     *
     * @param player
     */
    private void startPlayerTurn(Player player) {
        currentPlayer = player;
        printTurnCMesssage("New Turn for \"" + player.getUsername() + "\" is ready to start");
        player.setPlaying(true);
        PlayerTurn pt = new PlayerTurn(player, this.boardManager);
        currentTurnIstance = pt;
        //send the msg to the client, to choose the action he want to make
        VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", player.getUsername(), pt.getAvailableAction());
        notifyAllObserver(ObserverType.VIEW, msg);
        for (PlayerInterface p : turnSequence.values()) {
            if (!p.getUsername().equals(player.getUsername())) {
                VWaitYourTurnMsg wait = new VWaitYourTurnMsg("", p.getUsername());
                notifyAllObserver(ObserverType.VIEW, wait);
            }
        }

    }

    /**
     * when a player end his turn, this method is called to set the next player
     * and create a new turn for him
     */
    protected void nextTurn() {
        this.currentTurnIndex++;
        if (!isSoloMode) {
            int playerIndex = currentPlayer.getNumber();
             if (playerIndex == this.numberOfPlayer) {
                playerIndex = 1;
            } else {
                playerIndex++;
            }
            Player nextPlayer = (Player) this.turnSequence.get(playerIndex);
             if (nextPlayer.isDisconnected()){
                currentPlayer = nextPlayer;
                //if a player is disconnected, pass directly to the next turn
                nextTurn();
            }
            else {
                //if is connected start the turn
                startPlayerTurn(nextPlayer);
            }
        } else {
            ActionToken actionTokenActivated = null;
            try {

                actionTokenActivated = currentSoloTurnIstance.activateActionToken();
                VActionTokenActivateMsg msg1 = new VActionTokenActivateMsg("an Action Token has been activated", singlePlayer.getUsername(), actionTokenActivated);
                notifyAllObserver(ObserverType.VIEW, msg1);
                if (actionTokenActivated.getAbility().equals("Plus Two Black Cross Action Ability")) {

                    VLorenzoIncreasedMsg notify2 = new VLorenzoIncreasedMsg("Lorenzo increased of 2 his position because of a Action Token", singlePlayer.getUsername(), singlePlayer, 2);
                    notifyAllObserver(ObserverType.VIEW, notify2);
                }
                checkEndGame();
                if (actionTokenActivated.getAbility().equals("Plus One And Shuffle Action Ability")) {

                    VLorenzoIncreasedMsg notify1 = new VLorenzoIncreasedMsg("Lorenzo increased of 1 his position because of a Action Token", singlePlayer.getUsername(), singlePlayer, 1);
                    notifyAllObserver(ObserverType.VIEW, notify1);
                    checkEndGame();
                }
                startSoloPlayerTurn(singlePlayer);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * check if one or more players are arrived at the end of the game, in that case create a special controller
     * to manage the last turn of the game
     */
    private void checkEndGame() {
        //check the conditions fot the last turn of the game
        if (!isSoloMode) {
            currentPlayer.endTurn();
            boolean checkEnd = false;
            for (Integer key : turnSequence.keySet()) {
                //if one player is arrived to the end of the faithTrack the checkEnd is set to true
                if (turnSequence.get(key).checkEndGame()) {
                    checkEnd = true;
                }
            }
            if (checkEnd) {
                int keyCurrentP = 0;
                for (Integer key : turnSequence.keySet()) {
                    if (turnSequence.get(key).getUsername().equals(currentPlayer.getUsername())) {
                        keyCurrentP = key;
                    }

                }
                numberOfLastTurn = numberOfPlayer - keyCurrentP;      //it contains the number representing the total of last turn games
                lastTurns = true;
            }
        } else {
            if (singlePlayer.checkEndGame()) {
                //single mode the game has ended ...
                EndGameController endGameController = new EndGameController(singlePlayer, this, virtualView);
                attachObserver(ObserverType.CONTROLLER, endGameController);
                try {
                    endGameController.startCounting();
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkIfFirstAction() {

         if (actionController != null && actionController.endAction()) {
            detachObserver(ObserverType.CONTROLLER, actionController);
            actionController = null;
        }
    }

    private void printTurnCMesssage(String messageToPrint) {
        System.out.println(messageToPrint);
    }

    /*------------------------------------------------------------------------------------------------------------------*/

    //WAIT THE MSG FROM THE CLIENT


    /**
     * receive the msg from the client with the Action he choose
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {
        if ((currentPlayer != null && msg.getUsername().equals(currentPlayer.getUsername()) || (singlePlayer != null && msg.getUsername().equals(singlePlayer.getUsername())))) {

            //check if is not the first one and detach Action Turn if there is
            checkIfFirstAction();

            if (!msg.getActionChose().equals(TurnAction.END_TURN)) {
                printTurnCMesssage("New action of the game");

                /* create the controller to handle the specific action of the turn */
                ActionController controller = null;
                if (!isSoloMode) {
                    controller = new ActionController(currentPlayer, currentTurnIstance, boardManager, virtualView);
                } else {
                    controller = new ActionController(singlePlayer, currentSoloTurnIstance, boardManager, virtualView);
                }
                this.actionController = controller;
                System.out.println("HERE ACTION CONTROLLER: " + actionController);

                //attach it as an observer
                attachObserver(ObserverType.CONTROLLER, controller);
                //send him the msg with the info
                notifyAllObserver(ObserverType.CONTROLLER, msg);

            } else {
                if (!lastTurns) {
                    checkEndGame();
                    nextTurn();
                } else {
                    this.numberOfLastTurn--;    //decrease the number of players that have to play
                    if (numberOfLastTurn > 0) {
                        nextTurn();
                    } else {                    // if the game is over EndGameController will be instantiated to count all the victory points
                        EndGameController endGameController = null;
                        if (!isSoloMode) {
                            endGameController = new EndGameController(turnSequence, this, virtualView);
                        } else {
                            endGameController = new EndGameController(singlePlayer, this, virtualView);
                        }

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

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        detachObserver(ObserverType.CONTROLLER, actionController);
        actionController = null;
        if (!isSoloMode) {
            VChooseActionTurnRequestMsg requestMsg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", currentPlayer.getUsername(), currentTurnIstance.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, requestMsg);
        } else {
            VChooseActionTurnRequestMsg requestMsg = new VChooseActionTurnRequestMsg("A new turn is started, make your move:", singlePlayer.getUsername(), currentSoloTurnIstance.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, requestMsg);
        }

    }





    /*------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //to ACTION_CONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    /**
     * handle the event in which one player decided to discard a resource from the market
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        if (!isSoloMode) {
            for (Integer key : turnSequence.keySet()) {
                if (!turnSequence.get(key).getUsername().equals(msg.getUsername())) {
                    //not the player that discarded the resource
                    turnSequence.get(key).increasePosition();
                    //actionController.decrementNumberResourcesFromM();
                    VNotifyPositionIncreasedByMsg notify = new VNotifyPositionIncreasedByMsg("\" " + turnSequence.get(key).getUsername() + "\" increased his position because \"" + msg.getUsername() + "\"  discard a resource from the market", turnSequence.get(key).getUsername(), 1);
                    //remember to set all the other players!!!!
                    notifyAllObserver(ObserverType.VIEW, notify);
                    VUpdateFaithTrackMsg update = new VUpdateFaithTrackMsg("This is the new situation of your faithtrack",turnSequence.get(key).getUsername(),turnSequence.get(key).getGameSpace().getFaithTrack());
                    notifyAllObserver(ObserverType.VIEW,update);
                }
            }
        } else {
            singlePlayer.getGameSpace().increaseLorenzoIlMagnifico();
            //actionController.decrementNumberResourcesFromM();
            VLorenzoIncreasedMsg notify = new VLorenzoIncreasedMsg("Lorenzo incresed his position", singlePlayer.getUsername(), singlePlayer, 1);
            notifyAllObserver(ObserverType.VIEW, notify);

        }

        //check end game (because all player has increased their position of 1
        checkEndGame();
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CStopPPMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //to ActionController
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        Player playerDisconnected = getPlayerByUsername(msg.getUsername());
        if (playerDisconnected != null) {
            //check if the client disconnected is currently playing
            if (playerDisconnected.isPlaying() && actionController != null) {
                detachObserver(ObserverType.CONTROLLER, actionController);
                actionController = null;
                nextTurn();
            }
            /* even if is out of the if, so he should not be playing
            * set his attribute to false, to be sure */
            playerDisconnected.setPlaying(false);
            playerDisconnected.setDisconnected(true);
        }
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {

    }


    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //not here (Lobby)
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {

    }


    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //here after buyFromMarket
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    /*------------------------------------------------------*/
    //GETTER FOR TESTING


    public HashMap<Integer, PlayerInterface> getTurnSequence() {
        return turnSequence;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public PlayerTurn getCurrentTurnIstance() {
        return currentTurnIstance;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<PopesFavorTileReview> getCheckPopesFavorTile() {
        return checkPopesFavorTile;
    }

    public SoloPlayer getSinglePlayer() {
        return singlePlayer;
    }

    public boolean isSoloMode() {
        return isSoloMode;
    }

    public SoloPlayerTurn getCurrentSoloTurnIstance() {
        return currentSoloTurnIstance;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public boolean isLastTurns() {
        return lastTurns;
    }

    public int getNumberOfLastTurn() {
        return numberOfLastTurn;
    }
}
