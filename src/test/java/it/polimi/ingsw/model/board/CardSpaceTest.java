package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

//done

public class CardSpaceTest extends TestCase {

    CardSpace cardSpace= null;

    @Before
    public void setUp() throws Exception {
        cardSpace= new CardSpace(1);
    }

    @After
    public void tearDown() throws Exception {
        cardSpace=null;
    }

    @Test
    public void testGetNumberOfCards() {
        Resource res1 = new Resource(Color.GREY);
        ArrayList<Resource> resTot = new ArrayList<>();
        resTot.add(res1);
        DevelopmentCard newCard = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);
        DevelopmentCard newCard1 = new DevelopmentCard(2,Color.BLUE,2,resTot,resTot,resTot);
        cardSpace.addCard(newCard);
        cardSpace.addCard(newCard1);

        int num = cardSpace.getNumberOfCards();
        assertEquals(num,2);
    }

    @Test
    public void testGetCards() {

        Resource res1 = new Resource(Color.GREY);
        ArrayList<Resource> resTot = new ArrayList<>();
        resTot.add(res1);
        DevelopmentCard newCard = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);
        DevelopmentCard newCard1 = new DevelopmentCard(2,Color.BLUE,2,resTot,resTot,resTot);
        cardSpace.addCard(newCard);
        cardSpace.addCard(newCard1);

        ArrayList<DevelopmentCard> card = cardSpace.getCards();
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        cards.add(newCard);
        cards.add(newCard1);

        assertEquals(card,cards);
    }

    @Test
    public void testGetWhichSpace() {
        int space = cardSpace.getWhichSpace();
        assertEquals(space,1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetUpperCard() {

        //boolean thrown = false;
        Resource res1 = new Resource(Color.GREY);
        ArrayList<Resource> resTot = new ArrayList<>();
        resTot.add(res1);
        DevelopmentCard newCard = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);
        DevelopmentCard newCard1 = new DevelopmentCard(2,Color.BLUE,2,resTot,resTot,resTot);
        cardSpace.addCard(newCard);
        cardSpace.addCard(newCard1);

       /* try{
          cardSpace.getUpperCard();
      } catch (IllegalArgumentException e){
          thrown = true;
      }
        assertTrue(thrown);*/
        DevelopmentCard card = cardSpace.getUpperCard();
    }

    @Test
    public void testAddCard() {
        Resource res1 = new Resource(Color.GREY);
        ArrayList<Resource> resTot = new ArrayList<>();
        resTot.add(res1);
        DevelopmentCard newCard = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);
        cardSpace.addCard(newCard);
        DevelopmentCard newCard1 = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);

        /*boolean thrown = false;

        try{
            cardSpace.addCard(newCard1);
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);*/
    }


    @Test
    public void testGetTotVictoryPoints()
    {
        Resource res1 = new Resource(Color.GREY);
        ArrayList<Resource> resTot = new ArrayList<>();
        resTot.add(res1);
        DevelopmentCard newCard = new DevelopmentCard(2,Color.BLUE,1,resTot,resTot,resTot);
        cardSpace.addCard(newCard);
        DevelopmentCard newCard1 = new DevelopmentCard(2,Color.YELLOW,3,resTot,resTot,resTot);

        DevelopmentCard newCard2 = new DevelopmentCard(4,Color.BLUE,2,resTot,resTot,resTot);
        cardSpace.addCard(newCard2);


      int victoryP = cardSpace.getTotVictoryPoints();
      assertEquals(victoryP,6);
    }
}
