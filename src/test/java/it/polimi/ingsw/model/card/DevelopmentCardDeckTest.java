package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DevelopmentCardDeckTest extends TestCase {

    //done

    DevelopmentCardDeck developmentCardDeck = null;

    @Before
    public void setUp() throws Exception {

        Resource res = new Resource(Color.GREEN);
        Resource r1 = new Resource(Color.PURPLE);
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(res);
        cost.add(r1);

        Resource r2 = new Resource(Color.GREEN);
        ArrayList<Resource> earn = new ArrayList<>();
        earn.add(r2);

        Resource r3 = new Resource(Color.PURPLE);
        ArrayList<Resource> pay = new ArrayList<>();
        pay.add(r3);

        DevelopmentCard developmentCard = new DevelopmentCard(4,Color.GREEN,1,cost,earn,pay);
        DevelopmentCard developmentCard1 = new DevelopmentCard(2,Color.GREEN,2,cost,earn,pay);
        DevelopmentCard developmentCard2 = new DevelopmentCard(5,Color.GREEN,3,cost,earn,pay);

        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        cards.add(developmentCard);
        cards.add(developmentCard1);
        cards.add(developmentCard2);

        developmentCardDeck = new DevelopmentCardDeck(cards);

        cards.remove(developmentCard);
        cards.remove(developmentCard1);
        cards.remove(developmentCard2);

    }

    @After
    public void tearDown() throws Exception {
        developmentCardDeck = null;
    }

    @Test
    public void testGetDevelopDeck() {

        //ArrayList<DevelopmentCard> dev = new ArrayList<>();
        //dev = developmentCardDeck.getDevelopDeck();
        assertTrue(developmentCardDeck.getDevelopDeck().isEmpty());
    }

    @Test
    public void testTakeCard() {

        boolean thrown = false;

        //DevelopmentCard card = developmentCardDeck.takeCard();
        //assertEquals(developmentCardDeck.getDevelopDeck().size(),2);

        try {
            developmentCardDeck.takeCard();
        }catch (IndexOutOfBoundsException e){
            thrown = true;
        }
        assertTrue(thrown);
    }
}