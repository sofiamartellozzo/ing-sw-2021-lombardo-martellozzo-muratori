package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.SoloPersonalBoard;

import java.io.Serializable;

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
     * @param username constructor of the class, it set the attribute inkpot with default
     */
    public SoloPlayer(String username) {
        super(username);
    }

    @Override
    public SoloPersonalBoard getGameSpace() {
        return gameSpace;
    }

    @Override
    public void increasePosition() {
        gameSpace.getFaithTrack().increasePosition();
    }

    public void setGameSpace(SoloPersonalBoard gameSpace) {
        this.gameSpace = gameSpace;
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
