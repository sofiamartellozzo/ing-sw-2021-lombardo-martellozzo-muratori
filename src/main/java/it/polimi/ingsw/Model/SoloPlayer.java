package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.SoloPersonalBoard;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.card.SpecialCard;
import it.polimi.ingsw.Model.market.MarbleSpecial;

import java.util.ArrayList;

public class SoloPlayer extends Player{


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

    public void setGameSpace(SoloPersonalBoard gameSpace) {
        this.gameSpace = gameSpace;
    }


    public String checkIfWin() {
        if (super.checkEndGame()){
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
}
