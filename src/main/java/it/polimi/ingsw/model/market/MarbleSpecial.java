package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/*
 * SOFIA
 * */

/**
 * this class is made by a special ability of the Leader Card that made the white marble
 * in the market special, because if the player active this ability whenever he choose to
 * buy from the market an white marble this will return one Resource (choosen by himself)
 */
public class MarbleSpecial extends Marble {
    private ArrayList<Ability> ability = new ArrayList<>();

    /* constructor of the class */
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
            //int marble = player.chooseSpecialWhiteMarble();
            //Resource resource2 = ability.get(marble).getResource();
            return TypeResource.BLANK;   //blank because the player have to decide which one
            //player.putResources(resource2);
        }
        else{
            Resource resource1 = ability.get(0).getResource();
            return resource1.getType();
            //player.putResources(resource1);
        }

    }

}
