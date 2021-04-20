package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BoardManagerTest extends TestCase {

    BoardManager boardManager = null;

    @Before
    public void setUp() throws Exception {
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        Player player1 = new Player("sofia");
        Player player2 = new Player("ila");
        HashMap<Integer, PlayerInterface> players = new HashMap<>();
        players.put(1,player1);
        players.put(2,player2);
        boardManager = boardManagerFactory.createBoardManager(players);
    }

    @After
    public void tearDown() throws Exception {
        boardManager = null;
    }

    @Test
    public void testGetPlayers() {
        Map<Integer, PlayerInterface> players = new HashMap<>();
        players = boardManager.getPlayers();
    }

    public void testGetMarketStructure() {
    }

    public void testGetDevelopmentCardTable() {
    }

    public void testGetLeaderCardDeck() {
    }

    public void testGetResourcesSupply() {
    }
}