package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.controller.factory.ResourcesSupplyFactory;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.cardAbility.Discount;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import it.polimi.ingsw.model.market.ColoredMarble;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.model.market.RedMarble;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/* ILA */

/**
 * Pattern: FACTORY
 * this class is used to create the board manager at the initialization of the game
 */

public class BoardManagerFactory {

    /**
     * constructor of the class
     *
     * @param turnSequence the sequence of the players in the game
     * @return
     */
    public BoardManager createBoardManager(HashMap<Integer, PlayerInterface> turnSequence) {
        MarketStructure marketStructure = createStructure();
        DevelopmentCardTable developmentCardTable = new DevelopmentCardTable(createDevelopmentDeckTable());
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck(createLeaderCardDeck());
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        ResourcesSupply resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

        return new BoardManager(turnSequence, marketStructure, developmentCardTable, leaderCardDeck, resourcesSupply);
    }

    /**
     * this method creates the market of the game (3 rows x 4 columns) composed by 4 white marbles, 2 blue, 2 yellow, 2 purple and 1 red
     * @return
     */
    protected MarketStructure createStructure() {
        Marble[][] marbles = new Marble[3][4];
        ArrayList<Marble> possibleMarbles = new ArrayList<>();
        ColoredMarble purple = new ColoredMarble(Color.PURPLE);
        ColoredMarble yellow = new ColoredMarble(Color.YELLOW);
        ColoredMarble blue = new ColoredMarble(Color.BLUE);
        ColoredMarble white = new ColoredMarble(Color.WHITE);
        ColoredMarble grey = new ColoredMarble(Color.GREY);
        RedMarble red = new RedMarble();
        //2 PURPLE
        possibleMarbles.add(0, purple);
        possibleMarbles.add(1, purple);
        //2 YELLOW
        possibleMarbles.add(2, yellow);
        possibleMarbles.add(3, yellow);
        //2 BLUE
        possibleMarbles.add(4, blue);
        possibleMarbles.add(5, blue);
        //4 WHITE
        possibleMarbles.add(6, white);
        possibleMarbles.add(7, white);
        possibleMarbles.add(8, white);
        possibleMarbles.add(9, white);
        //2 GREY
        possibleMarbles.add(10, grey);
        possibleMarbles.add(11, grey);
        //1 RED
        possibleMarbles.add(12, red);
        Collections.shuffle(possibleMarbles);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marbles[i][j] = possibleMarbles.remove(0);
            }
        }

        return new MarketStructure(marbles, possibleMarbles.remove(0));
    }


    /**
     *  method implemented in the DevelopmentCardFactory that creates the card Table composed by decks of Development Cards (3 rows x 4 columns)
     * @return
     */

    private DevelopmentCardDeck[][] createDevelopmentDeckTable() {

        //method implemented in the DevelopmentCardFactory that creates the card Table composed by decks

        DevelopmentCardDeck[][] table = new DevelopmentCardDeck[3][4];
        DevelopmentCardFactory developmentCardFactory = new DevelopmentCardFactory();

        try {
            table = developmentCardFactory.createTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * this method creates the Leader Card Deck composed by 16 cards using the Leader Card Factory
     * @return
     */
        private ArrayList<LeaderCard> createLeaderCardDeck ()
        {
            ArrayList<LeaderCard> cards = new ArrayList<>();
            LeaderCardFactory leaderCardFactory = new LeaderCardFactory();
            try {
                cards = leaderCardFactory.createLeaderCardDeck();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return cards;
        }
}
