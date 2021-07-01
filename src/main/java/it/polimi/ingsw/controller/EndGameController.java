package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.VAskNewGameMsg;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.SoloPlayer;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * this class is a part of the controller that is created at the end of the game
 * It counts the victory points and declare the winner of the game and the list of losers
 * victory points can be obtained from the faith Track, the number of resources in the Resource Manager and Victory Points of Dev
 * (the ones that are in the card spaces) and Leader Cards (only if active)
 *
 */
public class EndGameController extends Observable implements ControllerObserver {

    private HashMap<Integer, PlayerInterface> turnSequence;
    private SoloPlayer soloPlayer;
    private final int numberOfPlayers;
    private TurnController turnController;
    /* list of VV of the players*/
    private Map<String, VirtualView> virtualView;


    /**
     * constructor of the class used when is in Multi Player Game
     * @param turnSequence sequence players' turns
     * @param turnController
     * @param virtualView of the players
     */
    public EndGameController(HashMap<Integer, PlayerInterface> turnSequence, TurnController turnController, Map<String, VirtualView> virtualView) {
        this.turnSequence = turnSequence;
        this.numberOfPlayers = turnSequence.keySet().size();
        this.turnController = turnController;
        this.virtualView = virtualView;
        attachAllVV();
    }

    /**
     * constructor of the class, override the previous one, used in Single Player Mode
     * @param soloPlayer of the game
     * @param turnController
     * @param virtualView of the player
     */
    public EndGameController(SoloPlayer soloPlayer, TurnController turnController, Map<String, VirtualView> virtualView) {
        this.soloPlayer = soloPlayer;
        this.numberOfPlayers = 1;
        this.turnController = turnController;
        this.virtualView = virtualView;
        attachAllVV();
    }

    /**
     * getter method
     * @return --> HashMap<Integer, PlayerInterface>
     */
    public HashMap<Integer, PlayerInterface> getTurnSequence() {
        return turnSequence;
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
     * this method counts all the points of the players of the game, declare the winner and the list of losers basing on them victory points
     * @throws InvalidActionException
     */
    public void startCounting() throws InvalidActionException {

        //for each player in the turn sequence it has to calculate all the victory points obtained
        int maxVPoints = 0;
        if (numberOfPlayers > 1) {
            for (Integer num : turnSequence.keySet()) {
                //every time we found a player with a sum of victory points that is bigger, this become the new max
                if (calculateVPoints((Player) turnSequence.get(num)) > maxVPoints) {
                    maxVPoints = calculateVPoints((Player) turnSequence.get(num));
                }
            }
            Player winnerPlayer = findWinnerPlayer(maxVPoints);   //find the player that corresponds to that victory points

            //create a List of losers players
            ArrayList<Player> losersPlayers = new ArrayList<>();
            for (Integer num : turnSequence.keySet()) {
                if (!winnerPlayer.getUsername().equals(turnSequence.get(num).getUsername())) {

                    losersPlayers.add((Player) turnSequence.get(num));
                }
            }

            //send msg containing the username of the winner, his points and the list of losers to CLI
            VShowEndGameResultsMsg msg = new VShowEndGameResultsMsg("The counting is finished ", winnerPlayer.getUsername(), winnerPlayer.getVictoryPoints(), losersPlayers);
            notifyAllObserver(ObserverType.VIEW, msg);
        } else {
            String result = soloPlayer.checkIfWin();
            if (result.equals("Winner")) {
                VShowEndGameResultsMsg msg = new VShowEndGameResultsMsg("The counting is finished ", soloPlayer.getUsername(), soloPlayer.getVictoryPoints(), true);
                notifyAllObserver(ObserverType.VIEW, msg);
            } else if (result.equals("Looser")) {
                VShowEndGameResultsMsg msg = new VShowEndGameResultsMsg("The counting is finished ", soloPlayer.getUsername(), soloPlayer.getVictoryPoints(), false);
                notifyAllObserver(ObserverType.VIEW, msg);
            }
        }
        VAskNewGameMsg askNewGameMsg = new VAskNewGameMsg("Do you want to start a new game?");
        notifyAllObserver(ObserverType.VIEW, askNewGameMsg);
        if (numberOfPlayers > 1) {
            CCloseRoomMsg closeOldRoom = new CCloseRoomMsg("close the room because the game ended", turnSequence.get(1).getUsername());
            notifyAllObserver(ObserverType.VIEW, closeOldRoom);
        } else {
            CCloseRoomMsg closeRoomMsg = new CCloseRoomMsg("close the room because the game ended", soloPlayer.getUsername());
            notifyAllObserver(ObserverType.VIEW, closeRoomMsg);
        }
    }
    /**
     * auxiliary method used to calculate all the victory points of a specific player given in input
     *
     * @param player
     * @return
     */
    private int calculateVPoints(Player player) {
        return player.calculateVictoryPoints();
    }

    /**
     * with this auxiliary method we find the player that corresponds to the maximum victory points previously calculated
     *
     * @param points of the winner
     * @return
     */
    private Player findWinnerPlayer(int points) {
        Player possibleWinner = null;
        for (Integer num : turnSequence.keySet()) {
            Player player = (Player) turnSequence.get(num);
            if (player.getVictoryPoints() == points) {
                if (possibleWinner != null) {
                    if (possibleWinner.getGameSpace().getResourceManager().numberAllResources() < player.getGameSpace().getResourceManager().numberAllResources()) {
                        // if two player has the same Victory Points the one with most resource win
                        possibleWinner = player;
                    }
                } else {
                    possibleWinner = player;
                }
            }
        }
        return possibleWinner;

    }

    /*------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {

        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
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
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {

        //NOT IMPLEMENTED HERE, but in Initialized Controller/ActionC
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby (Room)
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {

        //NOT IMPLEMENTED HERE, but in Initialize Controller/ActionC
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        //NOT HERE, implemented in Turn Controller
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        //NOT IMPLEMENTED HERE, but in PPController
    }

    @Override
    public void receiveMsg(CStopPPMsg msg) {
        //NOT IMPLEMENTED HERE, but in PPController (ActionC)
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }


    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //NOT IMPLEMENTED HERE, (VV) but in Lobby
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


}
