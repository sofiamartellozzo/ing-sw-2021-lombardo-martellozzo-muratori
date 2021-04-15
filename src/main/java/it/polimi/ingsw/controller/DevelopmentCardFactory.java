package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.FaithMarker;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DevelopmentCardFactory {
    public static void main(String[] args) {
        Gson gson1 = new Gson();

        /* from java class to Json */

        ArrayList<Resource> requirements = new ArrayList<>();
        requirements.add(new Resource(Color.BLUE));
        requirements.add(new Resource(Color.BLUE));

        ArrayList<Resource> earnProductionPower = new ArrayList<>();
        earnProductionPower.add(new FaithMarker());

        ArrayList<Resource> costProductionPower = new ArrayList<>();
        costProductionPower.add(new Resource(Color.BLUE));

        DevelopmentCard card = new DevelopmentCard(1,Color.GREEN,1,requirements,earnProductionPower,costProductionPower);

        String json = gson1.toJson(card);
        System.out.println(json);
    }
}
