package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.card.*;
import it.polimi.ingsw.Model.cardAbility.Discount;
import it.polimi.ingsw.Model.cardAbility.SpecialAbility;
import it.polimi.ingsw.Model.cardAbility.SpecialDepot;
import it.polimi.ingsw.Model.market.ColoredMarble;
import it.polimi.ingsw.Model.market.Marble;
import it.polimi.ingsw.Model.market.MarketStructure;
import it.polimi.ingsw.Model.market.RedMarble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/* ILA */

public class BoardManagerFactory {

    /**
     * constructor of the class
     * @param turnSequence
     * @return
     */
    public BoardManager createBoardManager(HashMap<Integer,PlayerInterface> turnSequence)
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

    protected DevelopmentCardDeck[][] createDevelopmentDeckTable(){

        DevelopmentCardDeck[][] squareCards = new DevelopmentCardDeck[1][2];

        //create the hash map for the cost of a Card
        ArrayList<Resource> array = new ArrayList<>();
        array.add(0,new Resource(Color.BLUE));
        array.add(1,new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0,new Resource(Color.GREEN));
        proceeds.add(1,new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0,new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card2 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card3 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card4 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        DevelopmentCardDeck smallDeck1 = new DevelopmentCardDeck(list);


        DevelopmentCard card5 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card6 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card7 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card8 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list1 = new ArrayList<>();
        list1.add(card5);
        list1.add(card6);
        list1.add(card7);
        list1.add(card8);
        DevelopmentCardDeck smallDeck2 = new DevelopmentCardDeck(list1);

        squareCards[0][0] = smallDeck1;
        squareCards[0][1]= smallDeck2;

        return squareCards;
    }

    private ArrayList<LeaderCard> createLeaderCardDeck()
    {
        SpecialAbility special1 = new SpecialDepot(new Resource(Color.BLUE));
        SpecialAbility special2 = new Discount(new Resource(Color.YELLOW));

        ArrayList<Object> requirements = new ArrayList<Object>();
        requirements.add(0,new Resource(Color.BLUE));
        requirements.add(1,new Resource(Color.GREEN));

        LeaderCard card1 = new LeaderCard(3,special1,requirements);
        LeaderCard card2 = new LeaderCard(4,special2,requirements);

        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(0,card1);
        cards.add(1,card2);

        return cards;
    }


}
