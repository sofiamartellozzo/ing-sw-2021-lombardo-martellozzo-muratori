package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.SoloPlayer;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;

import java.util.*;

/*
* SOFI*/
/**
 * this class is the same as the Personal Board but for the Solo Game
 */
    public class SoloPersonalBoard extends PersonalBoard{

    private List<ActionToken> actionTokens;
    private LorenzoFaithMarker lorenzoIlMagnifico;

    public SoloPersonalBoard(SoloFaithTrack soloFaithTrack, ResourceManager resourceManager, ArrayList<CardSpace> cardSpaces, ArrayList<ActionToken> actionTokens) {
        super(soloFaithTrack, resourceManager, cardSpaces);
        this.actionTokens = new ArrayList<>();
        this.actionTokens = actionTokens;
        lorenzoIlMagnifico = new LorenzoFaithMarker();
    }

    /**
     * this method get one Action Token random, at the end of the player turn
     * these cards are all stored in a Specific Deck
     */
    public ActionToken getActionTokenDeck(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        Random random = new Random();
        ActionToken actionToken = this.actionTokens.get(random.nextInt(this.actionTokens.size()));
        actionToken.activeActionToken(boardManager, player);
        return actionToken;
    }

    public List<ActionToken> getActionTokens() {
        return actionTokens;
    }

    public LorenzoFaithMarker getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public void increaseLorenzoIlMagnifico(){
        this.lorenzoIlMagnifico.increasePosition();
    }

    public void shuffleActionToken(){
        Collections.shuffle(this.actionTokens);
    }

    public int checkInvokeVaticanReport(){
        for (PopeBox popeBox: getFaithTrack().getPopeBoxes()){
            if ((popeBox.isActivated())&&(getFaithTrack().getFaithMarker().getPosition()== popeBox.getNumberBox())||(lorenzoIlMagnifico.getPosition()==popeBox.getNumberBox())){
                return popeBox.getWhichSection();
            }
        }
        return 0;
    }
}
