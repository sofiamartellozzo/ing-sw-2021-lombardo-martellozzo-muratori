package it.polimi.ingsw.model.card;
import java.util.ArrayList;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.State;
import it.polimi.ingsw.model.cardAbility.*;

/* ILA */

public class LeaderCard extends Card{

    private  SpecialAbility specialAbility;
    private  TypeResource specialResource;
    private  ArrayList<Object> requirements;
    private  State state;
    private int cardID;

    /**
     * constructor of the class
     * @param victoryPoints
     */

    public LeaderCard(int cardID, int victoryPoints, TypeAbility typeSpecialAbility, TypeResource specialResource, ArrayList<Object> requirements) {

        super(victoryPoints);
        this.cardID = cardID;
        this.requirements = requirements;
        this.specialResource = specialResource;
        createSpecialAbility(typeSpecialAbility);
        this.state = new Inactive();

    }

    // Getter methods

    public SpecialAbility getSpecialAbility(){return specialAbility;}
    public ArrayList<Object> getRequirements(){return requirements;}
    public State getState(){return state;}
    //public void setSpecialResource(TypeResource type){this.specialResource = type;}

    /**
     * method to take the ID of a Leader Card (an integer number that goes from 1 to 16)
     * @return
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * auxiliary method used to create the real special ability object starting from the TypeAbility
     * @param typeSpecialAbility
     * @return
     */

    private void createSpecialAbility(TypeAbility typeSpecialAbility){
        switch (typeSpecialAbility){
            case DISCOUNT:
                this.specialAbility = new Discount(createResource(specialResource));
                //return new Discount(createResource(specialResource));
                break;
            case SPECIAL_DEPOT:
                this.specialAbility = new SpecialDepot(createResource(specialResource));
                //return new SpecialDepot(createResource(specialResource));
                break;
            case TRANSFORM_WHITE:
                this.specialAbility = new TransformWhiteMarble(createResource(specialResource));
                break;
            case ADDITIONAL_POWER:
                this.specialAbility = new AdditionalPower(createResource(specialResource));
                //return new AdditionalPower(createResource(specialResource));
                break;
            default:
                throw new IllegalArgumentException(" Error, typeAbility not valid! ");
        }

    }

    /**
     * auxiliary method used to create the resource using the typeResource
     * @param typeResource
     * @return
     */
    public Resource createResource(TypeResource typeResource){
        switch (typeResource){
            case COIN:
                return new Resource(Color.YELLOW);
            case STONE:
                return new Resource(Color.GREY);
            case SHIELD:
                return new Resource(Color.BLUE);
            case SERVANT:
                return new Resource(Color.PURPLE);
        }
        throw new IllegalArgumentException(" Error, typeResource not valid! ");
    }


    /**
     * method used to active the special ability of a card Leader Card with a fixed resource as
     * earn production power
     * @param player
     * @throws InvalidActionException
     */
    public void activeCard(Player player) throws InvalidActionException {
        if(player == null) throw new InvalidActionException("Error, not valid!");
        state = new Active();
        //this.specialAbility.activeAbility(player);
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
