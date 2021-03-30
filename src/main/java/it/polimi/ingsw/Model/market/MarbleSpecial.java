package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/*
 * SOFIA
 * */
public class MarbleSpecial extends Marble {
    private ArrayList<Ability> ability;

    /* constructor of the class */
    public MarbleSpecial(Color color) {
        super(color);
    }

    @Override
    public void choose(Player player) {
        //Resource resource = this.ability.choose();
        //if (resource != null)
        // player.putResources(resource);
        Resource resource = new Resource(Color.YELLOW);
        player.putResources(resource);
    }

}
