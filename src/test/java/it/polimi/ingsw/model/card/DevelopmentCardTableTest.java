package it.polimi.ingsw.model.card;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DevelopmentCardTableTest extends TestCase {

    DevelopmentCardTable developmentCardTable = null;

    @Before
    public void setUp() throws Exception {


        DevelopmentCardDeck[][] squareCards = new DevelopmentCardDeck[3][4];

        //create the hash map for the cost of a Card
        ArrayList<Resource> array = new ArrayList<>();
        array.add(0,new Resource(Color.BLUE));
        array.add(1,new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0,new Resource(Color.GREY));
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

        DevelopmentCard card91 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card92 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card93 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card94 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        list12.add(card91);
        list12.add(card92);
        list12.add(card93);
        list12.add(card94);
        DevelopmentCardDeck smallDeck12 = new DevelopmentCardDeck(list12);

        DevelopmentCard card81 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card82 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card83 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card84 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list13 = new ArrayList<>();
        list13.add(card91);
        list13.add(card92);
        list13.add(card93);
        list13.add(card94);
        DevelopmentCardDeck smallDeck13 = new DevelopmentCardDeck(list13);



        DevelopmentCard card5 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card6 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card7 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card8 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card55 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card66 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card77 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card88 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card555 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card666 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card777 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card888 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list1 = new ArrayList<>();
        list1.add(card5);
        list1.add(card6);
        list1.add(card7);
        list1.add(card8);
        DevelopmentCardDeck smallDeck2 = new DevelopmentCardDeck(list1);

        ArrayList<DevelopmentCard> list11 = new ArrayList<>();
        list11.add(card55);
        list11.add(card66);
        list11.add(card77);
        list11.add(card88);
        DevelopmentCardDeck smallDeck22 = new DevelopmentCardDeck(list11);

        ArrayList<DevelopmentCard> list111 = new ArrayList<>();
        list111.add(card555);
        list111.add(card666);
        list111.add(card777);
        list111.add(card888);
        DevelopmentCardDeck smallDeck222 = new DevelopmentCardDeck(list111);

        DevelopmentCard card9 = new DevelopmentCard(3,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card10 = new DevelopmentCard(2,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card11 = new DevelopmentCard(6,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card12 = new DevelopmentCard(3,Color.YELLOW,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        list2.add(card9);
        list2.add(card10);
        list2.add(card11);
        list2.add(card12);
        DevelopmentCardDeck smallDeck3 = new DevelopmentCardDeck(list2);

        DevelopmentCard card13 = new DevelopmentCard(3,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card14 = new DevelopmentCard(2,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card15 = new DevelopmentCard(6,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card16 = new DevelopmentCard(3,Color.PURPLE,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list3 = new ArrayList<>();
        list3.add(card13);
        list3.add(card14);
        list3.add(card15);
        list3.add(card16);
        DevelopmentCardDeck smallDeck4 = new DevelopmentCardDeck(list3);



        squareCards[0][0] = smallDeck1;
        squareCards[1][0]= smallDeck12;
        squareCards[2][0]= smallDeck13;

        squareCards[0][1]= smallDeck2;
        squareCards[1][1]= smallDeck22;
        squareCards[2][1]= smallDeck222;

        squareCards[0][2]= smallDeck3;
        squareCards[1][2]= smallDeck3;
        squareCards[2][2]= smallDeck3;

        squareCards[0][3]= smallDeck4;
        squareCards[1][3]= smallDeck4;
        squareCards[2][3]= smallDeck4;


        developmentCardTable = DevelopmentCardTable.getInstance(squareCards);


    }

    @After
    public void tearDown() throws Exception {
        developmentCardTable = null;
    }

    @Test
    public void testTakeCard() {


        DevelopmentCard card  = developmentCardTable.takeCard(1,0);
        DevelopmentCard card1 = developmentCardTable.takeCard(1,0);
        DevelopmentCard card2 = developmentCardTable.takeCard(1,0);
        DevelopmentCard card3 = developmentCardTable.takeCard(1,0);

        assertEquals(developmentCardTable.getTable()[1][0].getDevelopDeck().size(),0);

        //DevelopmentCard card4 = developmentCardTable.takeCard(2,0);
        //assertEquals(developmentCardTable.getTable()[2][0].getDevelopDeck().size(),3);
    }

    @Test
    public void testGetSquare() throws InvalidActionException {

        boolean thrown = false;
           try {

               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);

               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);

           }catch (InvalidActionException e){
               thrown = true;
           }
           assertTrue(thrown);

    }

    @Test
    public void testCheckIfEmpty() throws InvalidActionException {

        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();

        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();

        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();

        assertTrue(developmentCardTable.checkIfEmpty());
    }
}