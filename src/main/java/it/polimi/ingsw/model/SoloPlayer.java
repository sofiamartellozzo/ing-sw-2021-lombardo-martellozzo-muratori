package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.SoloPersonalBoard;
import it.polimi.ingsw.model.card.LeaderCard;

import java.io.Serializable;

/**
 * generic player of a solo game
 * (a game composed by only one player, he himself)
 */
public class SoloPlayer extends Player implements Serializable {


    private SoloPersonalBoard gameSpace;

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * @param username --> of the player
     * constructor of the class, it set the attribute inkpot with default
     */
    public SoloPlayer(String username) {
        super(username);
        this.number=1;
    }

    @Override
    public SoloPersonalBoard getGameSpace() {
        return gameSpace;
    }

    /**
     * increases the position of the player's faith marker
     */
    @Override
    public int increasePosition() {
        return gameSpace.getFaithTrack().increasePosition();
    }

    public void setGameSpace(SoloPersonalBoard gameSpace) {
        this.gameSpace = gameSpace;
    }

    /**
     * calculates all the victory points obtained by the player in the game
     * @return --> points
     */
    @Override
    public int calculateVictoryPoints() {
        //sum of all points.. then set the attribute to it
        int points = 0;
        //get points from Development Card in Card Space
        points += gameSpace.getVictoryPointsFromCardSpaces();

        //get points from Leader Card
        for (LeaderCard leaderCard: this.leaderCards){
            points += leaderCard.getVictoryPoints();
        }

        //get points from PopesFavorTile and last Gold Box
        points += gameSpace.getFaithTrack().getAllVictoryPoints();

        //get points from the count of the Resources owned by the player
        points += gameSpace.getResourceManager().getVictoryPoints();

        victoryPoints = points;
        return points;
    }

    public String checkIfWin() {
        if ((gameSpace.getFaithTrack().getPositionFaithMarker()==24)||(gameSpace.getAllCards().size()==7)){
            result= Result.WINNER;
            return "Winner";
        }
        else if (gameSpace.getLorenzoIlMagnifico().getPosition()==24){
            result = Result.LOOSER;
            return "Looser";
        }
        else
            return "Check DevelopTable!";

    }

    @Override
    public boolean checkEndGame() {
        if ((gameSpace.getFaithTrack().getPositionFaithMarker()==24)||(gameSpace.getAllCards().size()==7)||(gameSpace.getLorenzoIlMagnifico().getPosition()==24)){
            return true;
        }
        else
            return false;
    }
}
