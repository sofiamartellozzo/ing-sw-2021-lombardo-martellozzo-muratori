package it.polimi.ingsw.controller.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DevelopmentCardFactory {
    public ArrayList<DevelopmentCard> createDevelopCards() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        /* from json to create Java class*/

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/developmentCard.json"));
        Type list = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        ArrayList<DevelopmentCard> cards = gson.fromJson(reader,list);
        return cards;
    }
}
