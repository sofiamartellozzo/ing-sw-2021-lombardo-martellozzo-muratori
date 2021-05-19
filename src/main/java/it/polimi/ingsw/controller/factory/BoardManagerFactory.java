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
        MarketStructure marketStructure = new MarketStructure(createStructure(),new ColoredMarble(Color.PURPLE));
        DevelopmentCardTable developmentCardTable = new DevelopmentCardTable(createDevelopmentDeckTable());
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck(createLeaderCardDeck());
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        ResourcesSupply resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

        return new BoardManager(turnSequence,marketStructure,developmentCardTable,leaderCardDeck, resourcesSupply);
    }

    protected Marble[][] createStructure(){
        Marble[][] marbles = new Marble[3][4];
        ArrayList<Marble> possibleMarbles = new ArrayList<>();
        ColoredMarble purple = new ColoredMarble(Color.PURPLE);
        ColoredMarble yellow = new ColoredMarble(Color.YELLOW);
        ColoredMarble blue = new ColoredMarble(Color.BLUE);
        ColoredMarble white = new ColoredMarble(Color.WHITE);
        ColoredMarble grey = new ColoredMarble(Color.GREY);
        RedMarble red = new RedMarble();
        //2 PURPLE
        possibleMarbles.add(0,purple);
        possibleMarbles.add(1,purple);
        //2 YELLOW
        possibleMarbles.add(2,yellow);
        possibleMarbles.add(3,yellow);
        //2 BLUE
        possibleMarbles.add(4,blue);
        possibleMarbles.add(5,blue);
        //4 WHITE
        possibleMarbles.add(6,white);
        possibleMarbles.add(7,white);
        possibleMarbles.add(8,white);
        possibleMarbles.add(9,white);
        //2 GREY
        possibleMarbles.add(10,grey);
        possibleMarbles.add(11,grey);
        //1 RED
        possibleMarbles.add(5,red);
        Random random = new Random();
        for(int i = 0; i<3; i++){
            for (int j = 0; j<4; j++)
            {
                int choose=random.nextInt(possibleMarbles.size());
                marbles[i][j] = possibleMarbles.get(choose);
                possibleMarbles.remove(choose);
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
