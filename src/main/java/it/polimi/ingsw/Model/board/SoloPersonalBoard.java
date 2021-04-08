package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.BoardManager;
import it.polimi.ingsw.Model.SoloPlayer;
import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

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
    public void getActionTokenDeck(BoardManager boardManager, SoloPlayer player) throws InvalidActionException {
        Random random = new Random();
        ActionToken actionToken = this.actionTokens.get(random.nextInt(this.actionTokens.size()));
        actionToken.activeActionToken(boardManager, player);
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
            if ((!popeBox.isActivated())&&(getFaithTrack().getFaithMarker().getPosition()== popeBox.getNumberBox())||(lorenzoIlMagnifico.getPosition()==popeBox.getNumberBox())){
                return popeBox.getWhichSection();
            }
        }
        return 0;
    }
}
