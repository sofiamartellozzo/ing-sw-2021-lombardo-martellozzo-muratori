package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyDiscountTest extends TestCase {

    BuyDiscount buyDiscount = null;
    boolean thrown = false;

    @Before
    public void setUp() throws Exception {
        buyDiscount = new BuyDiscount();
    }

    @After
    public void tearDown() throws Exception {
        buyDiscount = null;
    }

    @Test
    public void testAddResourceWithDiscount() {
        Resource resource = new Resource(Color.BLUE);
        buyDiscount.addResourceWithDiscount(resource);

        assertEquals(Color.BLUE, buyDiscount.getResourceWithDiscount().get(0).getColor());
    }

    @Test
    public void testGetResourceWithDiscount() {
        Resource resource = new Resource(Color.BLUE);
        buyDiscount.addResourceWithDiscount(resource);
        ArrayList<Resource> discounted = new ArrayList<>();
        discounted = buyDiscount.getResourceWithDiscount();

        assertEquals(1, discounted.size());
        assertEquals(Color.BLUE, discounted.get(0).getColor());
    }

    @Test
    public void testBuyCard() {
        Resource resource = new Resource(Color.BLUE);
        buyDiscount.addResourceWithDiscount(resource);

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player1 = new Player("sofia");
        PersonalBoard personalBoard1 = personalBoardFactory.createGame();
        player1.setGameSpace(personalBoard1);
        Player player2 = new Player("ila");
        PersonalBoard personalBoard2 = personalBoardFactory.createGame();
        player2.setGameSpace(personalBoard2);
        HashMap<Integer, PlayerInterface> players = new HashMap<>();
        players.put(1,player1);
        players.put(2,player2);
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        BoardManager boardManager = boardManagerFactory.createBoardManager(players);
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.BLUE));
        cost.add(new Resource(Color.PURPLE));
        player1.getGameSpace().getResourceManager().addResourcesToStrongBox(cost);
        player1.setBuyCard(buyDiscount);
        try{
            buyDiscount.buyCard(1,1,boardManager,player1,1);
        }catch (InvalidActionException e){
            thrown = true;
        }
        assertTrue(!thrown);
        //check if the card has been bought
        assertEquals(1, player1.getGameSpace().getCardSpaces().get(1).getNumberOfCards());
        //check if this class applyed the discount so in his Wharehouse there will still be the blu resource but only that
        assertEquals(Color.BLUE, player1.getGameSpace().getResourceManager().getResources().get(0).getColor());
        assertEquals(1, player1.getGameSpace().getResourceManager().getResources().size()); //una sola perchè all'inizio ne aveva due
    }

}