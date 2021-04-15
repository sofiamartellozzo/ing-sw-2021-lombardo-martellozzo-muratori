package it.polimi.ingsw.model.card;
import java.util.ArrayList;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.State;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;

/* ILA */

public class LeaderCard extends Card{

    private  SpecialAbility specialAbility;
    private  ArrayList<Object> requirements;
    private  State state;

    /**
     * constructor of the class
     * @param victoryPoints
     */

    public LeaderCard(int victoryPoints, SpecialAbility specialAbility,ArrayList<Object> requirements) {

        super(victoryPoints);
        this.requirements=requirements;
        this.specialAbility=specialAbility;
        this.state = new Inactive();

    }

    // Getter methods

    public SpecialAbility getSpecialAbility(){return specialAbility;}
    public ArrayList<Object> getRequirements(){return requirements;}
    public State getState(){return state;}

    /**
     * method used to active the special ability of a card Leader Card with a fixed resource as
     * earn production power
     * @param player
     * @throws InvalidActionException
     */
    public void activeCard(Player player) throws InvalidActionException {
        if(player == null) throw new InvalidActionException("Error, not valid!");
        state = new Active();
        this.specialAbility.activeAbility(player);
    }

    /**
     * method used when the special ability is activated and create the special card,
     * so the payer has to choose the resource he wants
     * @param resource
     * @param player
     * @throws InvalidActionException
     */
    public void activeCard(Resource resource,Player player) throws InvalidActionException {
        if(player == null) throw new InvalidActionException("Error, not valid!");
        state = new Active();
        this.specialAbility.activeAbility(player);
    }

    /**
     * this method gives you the victory points of the Leader Card only if the card is active,
     * otherwise it returns zero
     * @return
     */
    @Override
    public int getVictoryPoints() {
        return this.state.returnPoints(victoryPoints);
    }
}
