package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyTest extends TestCase {

    Buy buy = null;

    boolean thrown = false;

    @Before
    public void setUp() throws Exception {
        buy = new Buy();
    }

    @After
    public void tearDown() throws Exception {
        buy = null;
    }

    @Test
    public void testBuyCard_failNotEnought_toPay() {
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
        try{
            buy.buyCard(1,1,boardManager,player1,1);
        }catch (InvalidActionException e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testBuyCard_Success(){
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
        try{
            buy.buyCard(1,1,boardManager,player1,1);
        }catch (InvalidActionException e){
            thrown = true;
        }
        assertTrue(!thrown);
        assertEquals(1, player1.getGameSpace().getCardSpaces().get(1).getNumberOfCards());
    }
}