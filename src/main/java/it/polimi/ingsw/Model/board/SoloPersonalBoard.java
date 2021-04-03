package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void getActionTokenDeck() {
        Random random = new Random();
        ActionToken actionToken = this.actionTokens.get(random.nextInt(this.actionTokens.size()));
        actionToken.activeActionToken();
    }
}
