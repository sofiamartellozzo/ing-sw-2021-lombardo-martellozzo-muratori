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

/**
 * this class is the part of the controller that manages the start of the game,
 * deals with next players' turns and checks if is the end of a game (creating EndGameController)
 * It sets the turn sequence of a game
 * Turn Controller is specific for a single room
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

    /*references to Action Controller if is on*/
    private ActionController actionController;

    /**
     * constructor of the class used in a Multi Player Mode
     * @param players of the game
     * @param boardManager
     * @param virtualView
     */
    public TurnController(HashMap<Integer, PlayerInterface> players, BoardManager boardManager, Map<String, VirtualView> virtualView) {

        this.turnSequence = new HashMap<>();
        setTurnSequence(players);
        setCurrentPlayer(players);
        lastTurns = false;
        numberOfLastTurn = numberOfPlayer - 1;

        commonSettings(boardManager, virtualView);

    }

    /**
     * constructor of the class, override the previous one, used in Single Player Mode
     * @param player of the game
     * @param boardManager
     * @param virtualView
     */
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
     * this method set the first player of the turn checking that another one is not disconnected
     * is handle yet while game playing but this is used if one (like the first player)
     * disconnected at the initialization
     * @param players
     */
    public void setCurrentPlayer(HashMap<Integer, PlayerInterface> players){
        currentPlayer = (Player) players.get(1);
        boolean setFirst = false;
        int index = 1;
        while (!setFirst && index<players.keySet().size()) {
            if (currentPlayer.isDisconnected()){
                System.out.println("SERVER SEE DISCONNECTION (TURN CONTROLLER)");
                index++;
                currentPlayer = (Player) players.get(index);
            }
            else {
                setFirst = true;
            }
        }
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

    /**
     * find a player basing on his username
     * @param username of the player
     * @return
     */
    private Player getPlayerByUsername(String username) {
        if (!isSoloMode) {
            for (PlayerInterface p : turnSequence.values()) {
                if (p.getUsername().equals(username)) {
                    return (Player) p;
                }
            }
        } else {
            return currentSoloTurnIstance.getCurrentPlayer();
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

    public void addVV(String username, VirtualView VV) {
        virtualView.put(username, VV);
    }

    /**
     * method to initialized the game, creating the spaces and setting everithing
     * except the Board Manager the rest is setted differently if the number of player
     * are one or more : so solo mode or multiple mode
     *
     * @throws InvalidActionException
     */
    public void gamePlay() throws InvalidActionException {
        //where the turn game starts
        printTurnCMesssage("A Turn can start...");
        if (isSoloMode) {
            startSoloPlayerTurn(this.singlePlayer);
            if (singlePlayer.isDisconnected()){
                detachObserver(ObserverType.VIEW, virtualView.get(singlePlayer.getUsername()));
                virtualView.remove(singlePlayer.getUsername());
            }
        } else {
            boolean go = true;
            while (go) {
                printTurnCMesssage(" is disconnected? " +currentPlayer.isDisconnected());
                if (currentPlayer.isDisconnected()) {
                    printTurnCMesssage("in game play");
                    printTurnCMesssage("the p disconnected " +currentPlayer.getUsername());
                    int index = currentPlayer.getNumber();
                    index++;
                    currentPlayer = (Player) turnSequence.get(index);
                    printTurnCMesssage("the p disconnected " +currentPlayer.getUsername());
                }
                else{
                    go = false;
                }
            }
            startPlayerTurn(this.currentPlayer);
            for (PlayerInterface player: turnSequence.values()) {
                if (player.isDisconnected()){
                    detachObserver(ObserverType.VIEW, virtualView.get(player.getUsername()));
                    virtualView.remove(player.getUsername());
                }

            }
        }
    }

    /**
     * start the game in solo mode, so create the Solo Player Turn that manage the different
     * action the player can do
     *
     * @param player of which the turn has to start
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
     * @param player of which the turn has to start
     */
    private void startPlayerTurn(Player player) {
        currentPlayer = player;
        printTurnCMesssage("New Turn for \"" + player.getUsername() + "\" is ready to start");
        player.setPlaying(true);
        PlayerTurn pt = new PlayerTurn(player, this.boardManager);
        currentTurnIstance = pt;
        //send the msg to the client, to choose the action he want to make
        VChooseActionTurnRequestMsg msg = new VChooseActionTurnRequestMsg("Make your move: \uD83D\uDE09", player.getUsername(), pt.getAvailableAction());
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
            if (nextPlayer.isDisconnected()) {
                currentPlayer = nextPlayer;
                //if a player is disconnected, pass directly to the next turn
                nextTurn();
            } else {
                //if is connected start the turn
                startPlayerTurn(nextPlayer);
            }
        } else {
            singlePlayer.endTurn();
            //now play Lorenzo
            ActionToken actionTokenActivated = null;
            try {

                actionTokenActivated = currentSoloTurnIstance.activateActionToken();
                VActionTokenActivateMsg msg1 = new VActionTokenActivateMsg("an Action Token has been activated", singlePlayer.getUsername(), actionTokenActivated);
                notifyAllObserver(ObserverType.VIEW, msg1);
                if (actionTokenActivated.getAbility().equals("Plus Two Black Cross Action Ability")) {

                    VLorenzoIncreasedMsg notify2 = new VLorenzoIncreasedMsg("Lorenzo increased of 2 his position because of a Action Token", singlePlayer.getUsername(), singlePlayer, 2);
                    notifyAllObserver(ObserverType.VIEW, notify2);

                } else if (actionTokenActivated.getAbility().equals("Plus One And Shuffle Action Ability")) {

                    VLorenzoIncreasedMsg notify1 = new VLorenzoIncreasedMsg("Lorenzo increased of 1 his position because of a Action Token", singlePlayer.getUsername(), singlePlayer, 1);
                    notifyAllObserver(ObserverType.VIEW, notify1);

                } else {
                    Map<Integer, PlayerInterface> allPlayers = new HashMap<>();
                    allPlayers.put(1, singlePlayer);
                    VUpdateDevTableMsg notify3 = new VUpdateDevTableMsg("The table has changed because of a Action Token", singlePlayer.getUsername(), singlePlayer.calculateVictoryPoints(), singlePlayer.getGameSpace().getCardSpaces(), boardManager.getDevelopmentCardTable(), allPlayers);
                    notifyAllObserver(ObserverType.VIEW, notify3);
                }
                checkEndGame();
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
        printTurnCMesssage("CHECK end Game in Turn Controller ❀");
        if (!isSoloMode) {

                        //MULTIPLAYER MODE
            currentPlayer.endTurn();
            boolean checkEnd = false;
            for (Integer key : turnSequence.keySet()) {
                //if one player is arrived to the end of the faithTrack the checkEnd is set to true
                if (turnSequence.get(key).checkEndGame()) {
                    checkEnd = true;
                }
            }
            if (checkEnd) {

                int keyCurrentP = currentPlayer.getNumber();
                numberOfLastTurn = numberOfPlayer - keyCurrentP;      //it contains the number representing the total of last turn games
                lastTurns = true;
            }
            callRightAction();
        } else {
                        //SINGLE PLAYER MODE
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
            else if(singlePlayer.isPlaying()){
                nextTurn();
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
        System.out.println("[Turn Controller]: " +messageToPrint);
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
                //System.out.println("HERE ACTION CONTROLLER: " + actionController);        //DEBUG

                //attach it as an observer
                attachObserver(ObserverType.CONTROLLER, controller);
                //send him the msg with the info
                notifyAllObserver(ObserverType.CONTROLLER, msg);

            } else {
                //                      END_TURN
                if (!lastTurns) {
                    checkEndGame();

                } else {
                    callRightAction();
                    /*
                    System.out.println("Number of last turns: " +numberOfLastTurn);

                    if (numberOfLastTurn > 0) {
                        numberOfLastTurn--;    //decrease the number of players that have to play
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
                    }*/
                }
            }
        }
    }

    private void callRightAction(){
        if (!lastTurns) {
            nextTurn();
        } else {
            System.out.println("Number of last turns: " +numberOfLastTurn);

            if (numberOfLastTurn > 0) {
                numberOfLastTurn--;    //decrease the number of players that have to play
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

    /**
     * received when a player has to change the action of the game and choose another one from the possible actions
     * @param msg
     */
    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        detachObserver(ObserverType.CONTROLLER, actionController);
        actionController = null;
        if (!isSoloMode) {
            VChooseActionTurnRequestMsg requestMsg = new VChooseActionTurnRequestMsg("Make your move: \uD83D\uDE09", currentPlayer.getUsername(), currentTurnIstance.getAvailableAction());
            notifyAllObserver(ObserverType.VIEW, requestMsg);
        } else {
            VChooseActionTurnRequestMsg requestMsg = new VChooseActionTurnRequestMsg("Make your move: \uD83D\uDE09", singlePlayer.getUsername(), currentSoloTurnIstance.getAvailableAction());
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
                    int section = turnSequence.get(key).increasePosition();
                    if(section!=0) {
                        for (int i : this.boardManager.getPlayers().keySet()) {
                            this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().doVaticanReport(section);
                        }
                    }
                    //actionController.decrementNumberResourcesFromM();
                    VNotifyPositionIncreasedByMsg notify = new VNotifyPositionIncreasedByMsg("\" " + turnSequence.get(key).getUsername() + "\" increased his position because \"" + msg.getUsername() + "\"  discard a resource from the market", turnSequence.get(key).getUsername(), turnSequence.get(key).calculateVictoryPoints(), 1);
                    //remember to set all the other players!!!!
                    notifyAllObserver(ObserverType.VIEW, notify);
                    VUpdateFaithTrackMsg update = new VUpdateFaithTrackMsg("This is the new situation of your faithtrack", turnSequence.get(key).getUsername(), turnSequence.get(key).getGameSpace().getFaithTrack());
                    notifyAllObserver(ObserverType.VIEW, update);
                }
            }
        } else {
            singlePlayer.getGameSpace().increaseLorenzoIlMagnifico();
            //actionController.decrementNumberResourcesFromM();
            VLorenzoIncreasedMsg notify = new VLorenzoIncreasedMsg("Lorenzo increased his position", singlePlayer.getUsername(), singlePlayer, 1);
            notifyAllObserver(ObserverType.VIEW, notify);

        }

        //check end game (because all player has increased their position of 1
        //checkEndGame();
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //to ACTIONCONTROLLER
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        //to ACTIONCONTROLLER (PPController)
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }


    @Override
    public void receiveMsg(CStopPPMsg msg) {
        //to PPController (ActionC)
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //to ActionController
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        PlayerInterface playerDisconnected = getPlayerByUsername(msg.getUsernameDisconnected());
        System.out.println("IN TURN CONTROLLER: received disconn Msg ");      //DEBUG
        if (playerDisconnected != null) {
            //check if the client disconnected is currently playing
            System.out.println("NAME disconnected: " + playerDisconnected.getUsername());
            playerDisconnected.setDisconnected(true);
            if (!isSoloMode) {
                //notify all players about one disconnected
                notifyAllObserver(ObserverType.VIEW, msg);
            }
            System.out.println(playerDisconnected.isDisconnected());
            System.out.println(playerDisconnected.isPlaying());
            System.out.println(actionController != null);
            if (playerDisconnected.isPlaying() ) {
                System.out.println("not here.......$$$$$");       //DEBUG

                if (actionController != null){
                    detachObserver(ObserverType.CONTROLLER, actionController);
                    actionController = null;
                }
                playerDisconnected.setPlaying(false);
                System.out.println(isSoloMode);
                if (isSoloMode) {
                    //solo mode, wait until a reconnection
                    System.out.println("Disconnect SOLO MODE");
                    VStartWaitReconnectionMsg wait = new VStartWaitReconnectionMsg("a client disconnected so wait until a reconnection", singlePlayer.getUsername());
                    notifyAllObserver(ObserverType.VIEW, wait);
                    detachObserver(ObserverType.VIEW, virtualView.get(singlePlayer.getUsername()));
                    virtualView.remove(singlePlayer.getUsername());
                } else if (!isSoloMode && allDisconnected()) {
                    System.out.println("ALL PLAYERS DISCONNECTED");
                    VStartWaitReconnectionMsg wait = new VStartWaitReconnectionMsg("all client disconnected so wait until one (or more) reconnection", playerDisconnected.getUsername());
                    notifyAllObserver(ObserverType.VIEW, wait);
                    detachObserver(ObserverType.VIEW, virtualView.get(playerDisconnected.getUsername()));
                    virtualView.remove(playerDisconnected.getUsername());
                } else {
                    System.out.println("Only one disconnected, game still ON");
                    detachObserver(ObserverType.VIEW, virtualView.get(playerDisconnected.getUsername()));
                    virtualView.remove(playerDisconnected.getUsername());
                    nextTurn();
                }
            } else {
                if (!isSoloMode && allDisconnected()) {
                    System.out.println("ALL PLAYERS DISCONNECTED");
                    VStartWaitReconnectionMsg wait = new VStartWaitReconnectionMsg("all client disconnected so wait until one (or more) reconnection", playerDisconnected.getUsername());
                    notifyAllObserver(ObserverType.VIEW, wait);
                }
                detachObserver(ObserverType.VIEW, virtualView.get(playerDisconnected.getUsername()));
                virtualView.remove(playerDisconnected.getUsername());
            }


            /* even if is out of the if, so he should not be playing
             * set his attribute to false, to be sure */
            //playerDisconnected.setPlaying(false);
            //playerDisconnected.setDisconnected(true);

            System.out.println(virtualView);
            System.out.println(playerDisconnected.isDisconnected());
        }
    }

    /**
     * method to check if in this room there is at least one player
     * not disconnected playing
     * if not the server have to start the timer counting 10 seconds
     * if it expire the room will be closed
     *
     * @return true= all disconnected, false= one still connected
     */
    private boolean allDisconnected() {
        for (PlayerInterface p : turnSequence.values()) {
            if (!p.isDisconnected()) {
                //one player in the room not disconnected is enough
                return false;
            }
        }
        return true;
    }


    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CNewStartMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Initialized Controller/ ActionC
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby (Room)
    }


    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        if (isSoloMode || !currentPlayer.isPlaying()) {
            nextTurn();
        }
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Virtual View
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //NOT IMPLEMENTED HERE, (VV) but in Lobby
    }


    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //to Initialize Controller/ActionC
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

    public SoloPlayerTurn getCurrentSoloTurnInstance() {
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
