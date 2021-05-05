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
import java.util.HashMap;
import java.util.Random;

/* ILA */

/**
 * FACTORY
 * this class is used to create the board manager at the initialization of the game
 */

public class BoardManagerFactory {

    /**
     * constructor of the class
     * @param turnSequence the sequence of the players in the game
     * @return
     */
    public BoardManager createBoardManager(HashMap<Integer, PlayerInterface> turnSequence)
    {
        MarketStructure marketStructure = MarketStructure.getInstance(createStructure(),new ColoredMarble(Color.PURPLE));
        DevelopmentCardTable developmentCardTable = DevelopmentCardTable.getInstance(createDevelopmentDeckTable());
        LeaderCardDeck leaderCardDeck = LeaderCardDeck.getInstance(createLeaderCardDeck());
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        ResourcesSupply resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

        return new BoardManager(turnSequence,marketStructure,developmentCardTable,leaderCardDeck, resourcesSupply);
    }

    protected Marble[][] createStructure(){

        Marble[][] marbles = new Marble[3][4];
        ArrayList<Marble> possibleMarbles = new ArrayList<>();

        ColoredMarble marble1 = new ColoredMarble(Color.PURPLE);
        ColoredMarble marble2 = new ColoredMarble(Color.YELLOW);
        ColoredMarble marble3 = new ColoredMarble(Color.BLUE);
        ColoredMarble marble4 = new ColoredMarble(Color.WHITE);
        ColoredMarble marble5 = new ColoredMarble(Color.GREY);
        RedMarble marble6 = new RedMarble();

        possibleMarbles.add(0,marble1);
        possibleMarbles.add(1,marble2);
        possibleMarbles.add(2,marble3);
        possibleMarbles.add(3,marble4);
        possibleMarbles.add(4,marble5);
        possibleMarbles.add(5,marble6);

        Random random = new Random();

        for(int i = 0; i<3; i++){
            for (int j = 0; j<4; j++)
            {
                marbles[i][j] = possibleMarbles.get(random.nextInt(possibleMarbles.size()));
            }
        }
        return marbles;
    }

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


        private ArrayList<LeaderCard> createLeaderCardDeck()
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
