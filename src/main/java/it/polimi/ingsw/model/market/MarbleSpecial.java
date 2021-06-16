package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class is made by a special ability of the Leader Card that changes the white marble to the marble special
 * if the player actives this ability whenever he chooses to buy from the market
 * every white marble will be converted to Specific resource instead of nothing
 */
public class MarbleSpecial extends Marble implements Serializable {
    private ArrayList<Ability> ability = new ArrayList<>();

    /**
     * constructor of the class
     */
    public MarbleSpecial() {
        this.color = Color.WHITE;
    }

    public void setAbility(Ability ability1){
        ability.add(ability1);
    }

    public ArrayList<Ability> getAbility() {
        return ability;
    }

    @Override
    public TypeResource choose(Player player) throws InvalidActionException {

        if (ability.size()==2){
            return TypeResource.BLANK;   //blank because the player have to decide which one

        }
        else{
            Resource resource1 = ability.get(0).getResource();
            return resource1.getType();
        }

    }

}
