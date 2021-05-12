package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.SoloPlayer;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * this class is a part of the controller that count the victory points and declare the winner of the game
 */
public class EndGameController extends Observable implements ControllerObserver {

    private HashMap<Integer, PlayerInterface> turnSequence;
    private SoloPlayer soloPlayer;
    private final int numberOfPlayers;
    private TurnController turnController;

    public EndGameController(HashMap<Integer, PlayerInterface> turnSequence, TurnController turnController) {
        this.turnSequence = turnSequence;
        this.numberOfPlayers = turnSequence.keySet().size();
        this.turnController = turnController;
    }

    public EndGameController(SoloPlayer soloPlayer, TurnController turnController){
        this.soloPlayer = soloPlayer;
        this.numberOfPlayers = 1;
        this.turnController = turnController;
    }

    public void startCounting() throws InvalidActionException {

        //for each player in the turn sequence it has to calculate all the victory points obtained
        int maxVPoints = 0;
        for (Integer num : turnSequence.keySet()) {
            //every time we found a player with a sum of victory points that is bigger, this become the new max
            if (calculateVPoints((Player) turnSequence.get(num)) > maxVPoints) {
                maxVPoints = calculateVPoints((Player) turnSequence.get(num));
            }
        }
        Player winnerPlayer = findWinnerPlayer(maxVPoints);   //find the player that corresponds to that victory points

        //create a List of losers players
        ArrayList<Player> losersPlayers = new ArrayList<>();
        for(Integer num : turnSequence.keySet())
        {
            if(!winnerPlayer.getUsername().equals(turnSequence.get(num).getUsername())){

                losersPlayers.add((Player) turnSequence.get(num));
            }
        }

        //send msg containing the username of the winner, his points and the list of losers to CLI
        VShowEndGameResultsMsg msg = new VShowEndGameResultsMsg("The counting is finished ",winnerPlayer.getUsername(), winnerPlayer.getVictoryPoints(),losersPlayers);
        notifyAllObserver(ObserverType.VIEW, msg);
        System.out.println(winnerPlayer.getUsername()+" is the winner of the Game ");

    }

    private void printTurnLastTurnMessage(String messageToPrint){
        System.out.println(messageToPrint);
    }

    /**
     * auxiliary method used to calculate all the victory points of a specific player
     * @param player
     * @return
     */
    private int calculateVPoints(Player player){
        return player.calculateVictoryPoints();
    }

    /**
     * with this auxiliary method we find the player that corresponds tho the maximum victory points previously calculated
     * @param points
     * @return
     */
    private Player findWinnerPlayer(int points)
    {
        for (Integer num: turnSequence.keySet()) {
            Player player = (Player) turnSequence.get(num);
            if(player.getVictoryPoints() == points)
            {
                return player;
            }
        }
        throw new IllegalArgumentException(" Error, this victory points don't correspond to any player !");

    }

    /*------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //not here (Lobby)
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

        //NOT IMPLEMENTED HERE
    }

    @Override
    public void receiveMsg(CChooseDiscardResponseMsg msg) {

    }




}
