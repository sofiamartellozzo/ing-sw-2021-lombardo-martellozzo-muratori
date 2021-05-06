package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.card.DevelopmentCard;
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

    @Test
    public void testGetCostPPTypeUpperCard(){
        ArrayList<Resource> cost1 = new ArrayList<>();
        ArrayList<TypeResource> expected1 = new ArrayList<>();
        cost1.add(new Resource(TypeResource.SHIELD));
        expected1.add(TypeResource.SHIELD);
        cost1.add(new Resource(TypeResource.STONE));
        expected1.add(TypeResource.STONE);
        DevelopmentCard developmentCard1 = new DevelopmentCard(0,Color.BLUE,1,null,null,cost1);
        ArrayList<Resource> cost2 = new ArrayList<>();
        ArrayList<TypeResource> expected2 = new ArrayList<>();
        cost2.add(new Resource(TypeResource.COIN));
        expected2.add(TypeResource.COIN);
        cost2.add(new Resource(TypeResource.SERVANT));
        expected2.add(TypeResource.SERVANT);
        DevelopmentCard developmentCard2 = new DevelopmentCard(0,Color.BLUE,2,null,null,cost2);
        ArrayList<Resource> cost3 = new ArrayList<>();
        ArrayList<TypeResource> expected3 = new ArrayList<>();
        cost3.add(new Resource(TypeResource.SHIELD));
        expected3.add(TypeResource.SHIELD);
        cost3.add(new Resource(TypeResource.SHIELD));
        DevelopmentCard developmentCard3 = new DevelopmentCard(0,Color.BLUE,3,null,null,cost3);
        cardSpace.addCard(developmentCard1);
        assertEquals(expected1,cardSpace.getCostPPTypeUpperCard());
        cardSpace.addCard(developmentCard2);
        assertEquals(expected2,cardSpace.getCostPPTypeUpperCard());
        cardSpace.addCard(developmentCard3);
        assertEquals(expected3,cardSpace.getCostPPTypeUpperCard());
    }

    @Test
    public void testGetNumberCostPP(){
        ArrayList<Resource> cost1 = new ArrayList<>();
        cost1.add(new Resource(TypeResource.SHIELD));
        cost1.add(new Resource(TypeResource.STONE));
        DevelopmentCard developmentCard1 = new DevelopmentCard(0,Color.BLUE,1,null,null,cost1);
        ArrayList<Resource> cost2 = new ArrayList<>();
        cost2.add(new Resource(TypeResource.COIN));
        cost2.add(new Resource(TypeResource.SERVANT));
        DevelopmentCard developmentCard2 = new DevelopmentCard(0,Color.BLUE,2,null,null,cost2);
        ArrayList<Resource> cost3 = new ArrayList<>();
        cost3.add(new Resource(TypeResource.SHIELD));
        cost3.add(new Resource(TypeResource.SHIELD));
        DevelopmentCard developmentCard3 = new DevelopmentCard(0,Color.BLUE,3,null,null,cost3);
        cardSpace.addCard(developmentCard1);
        assertSame(1, cardSpace.getNumberCostPP(TypeResource.SHIELD));
        assertSame(1,cardSpace.getNumberCostPP(TypeResource.STONE));
        cardSpace.addCard(developmentCard2);
        assertSame(1,cardSpace.getNumberCostPP(TypeResource.COIN));
        assertSame(1,cardSpace.getNumberCostPP(TypeResource.SERVANT));
        cardSpace.addCard(developmentCard3);
        assertSame(2,cardSpace.getNumberCostPP(TypeResource.SHIELD));
        assertSame(0,cardSpace.getNumberCostPP(TypeResource.STONE));
        assertSame(0,cardSpace.getNumberCostPP(TypeResource.SERVANT));
        assertSame(0,cardSpace.getNumberCostPP(TypeResource.COIN));
    }
}
