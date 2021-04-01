package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.TypeResource;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        int num = cardSpace.getNumberOfCards();
        assertEquals(num,0);
    }

    @Test
    public void testGetCards() {
        ArrayList<DevelopmentCard> card= cardSpace.getCards();
        assertEquals(card,null);
    }

    @Test
    public void testGetWhichSpace() {
        int space = cardSpace.getWhichSpace();
        assertEquals(space,1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetUpperCard() {
        cardSpace.getUpperCard();


        /*boolean thrown = false;

      try{
          cardSpace.getUpperCard();
      } catch (IllegalArgumentException e){
          thrown = true;
      }
      assertTrue(thrown);*/
    }

    /*public void testAddCard() {
        DevelopmentCard newCard = new DevelopmentCard(1,Color.RED,2,4, TypeResource.SHIELD);
    }*/
}
