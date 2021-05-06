package it.polimi.ingsw.controller.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.FaithMarker;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.LeaderCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * creating every development card and the table that contains them divided in 12 decks of 4 cards
 */
public class DevelopmentCardFactory {

    public DevelopmentCardDeck[][] createTable() throws FileNotFoundException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/developmentCard.json"));
        Type list = new TypeToken<ArrayList<DevelopmentCard>>() {
        }.getType();
        ArrayList<DevelopmentCard> cards = gson.fromJson(reader, list);

        DevelopmentCardDeck[][] table = new DevelopmentCardDeck[3][4];

        // creating the 12 different Development card deck composed each one by 4 cards

        ArrayList<DevelopmentCard> list1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {        //adding four cards every time to a deck
            list1.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck20 = new DevelopmentCardDeck(list1);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {
            list2.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck10 = new DevelopmentCardDeck(list2);

        ArrayList<DevelopmentCard> list3 = new ArrayList<>();
        for (int i = 8; i < 12; i++) {
            list3.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck00 = new DevelopmentCardDeck(list3);

        ArrayList<DevelopmentCard> list4 = new ArrayList<>();
        for (int i = 12; i < 16; i++) {
            list4.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck21 = new DevelopmentCardDeck(list4);

        ArrayList<DevelopmentCard> list5 = new ArrayList<>();
        for (int i = 16; i < 20; i++) {
            list5.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck11 = new DevelopmentCardDeck(list5);

        ArrayList<DevelopmentCard> list6 = new ArrayList<>();
        for (int i = 20; i < 24; i++) {
            list6.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck01 = new DevelopmentCardDeck(list6);

        ArrayList<DevelopmentCard> list7 = new ArrayList<>();
        for (int i = 24; i < 28; i++) {
            list7.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck22 = new DevelopmentCardDeck(list7);

        ArrayList<DevelopmentCard> list8 = new ArrayList<>();
        for (int i = 28; i < 32; i++) {
            list8.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck12 = new DevelopmentCardDeck(list8);

        ArrayList<DevelopmentCard> list9 = new ArrayList<>();
        for (int i = 32; i < 36; i++) {
            list9.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck02 = new DevelopmentCardDeck(list9);

        ArrayList<DevelopmentCard> list10 = new ArrayList<>();
        for (int i = 36; i < 40; i++) {
            list10.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck23 = new DevelopmentCardDeck(list10);

        ArrayList<DevelopmentCard> list11 = new ArrayList<>();
        for (int i = 40; i < 44; i++) {
            list11.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck13 = new DevelopmentCardDeck(list11);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        for (int i = 44; i < 48; i++) {
            list12.add(cards.get(i));
        }
        DevelopmentCardDeck cardDeck03 = new DevelopmentCardDeck(list12);

        //put each deck in the table, starting from the low-left side
        table[2][0] = cardDeck20;
        table[1][0] = cardDeck10;
        table[0][0] = cardDeck00;
        table[2][1] = cardDeck21;
        table[1][1] = cardDeck11;
        table[0][1] = cardDeck01;
        table[2][2] = cardDeck22;
        table[1][2] = cardDeck12;
        table[0][2] = cardDeck02;
        table[2][3] = cardDeck23;
        table[1][3] = cardDeck13;
        table[0][3] = cardDeck03;

        return table;
    }

}




