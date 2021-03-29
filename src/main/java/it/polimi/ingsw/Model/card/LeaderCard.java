package it.polimi.ingsw.Model.card;
import java.util.HashMap;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;

public class LeaderCard extends Card {

    private boolean hidden;
    private SpecialAbility specialAbility;
    private HashMap<Integer,Object> requirements;


    /**
     * constructor of the class, he needs the points that it has, like the Abstract class Card
     * and set the other parameter to default
     * @param victoryPoints
     */
    public LeaderCard(int victoryPoints) {
        super(victoryPoints);
        this.requirements = new HashMap<>();
    }

    public boolean getHiddenValue(){return hidden;}
    public void setHiddenValue(boolean Hidden){this.hidden=Hidden;}

    public SpecialAbility getSpecialAbility(){return specialAbility;}
    public void setSpecialAbility(SpecialAbility specialAbility){this.specialAbility=specialAbility;}

    public HashMap<Integer,Object> getRequirements(){return requirements;}
    public void setRequirements(HashMap<Integer,Object> requirements){this.requirements=requirements;}

    //method used to active the special ability of a card Leader Card

    public void activeCard(){
        this.specialAbility.activeAbility();
    }

    // method used when the special ability is activated and the payer chose the resource he wants
    public void activeCard(Resource resource){
        this.specialAbility.activeAbility();
    }

    @Override
    public int getVictoryPoints() {
        return super.victoryPoints;
    }


}
