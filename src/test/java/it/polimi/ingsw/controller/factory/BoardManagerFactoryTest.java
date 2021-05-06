package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BoardManagerFactoryTest extends TestCase {

    BoardManagerFactory boardManagerFactory = null;

    @Before
    public void setUp() throws Exception {
        boardManagerFactory = new BoardManagerFactory();
    }

    @After
    public void tearDown() throws Exception {
        boardManagerFactory = null;
    }

    @Test
    public void testCreateBoardManager() {
        BoardManager boardManager = null;
        Map<Integer, PlayerInterface> players = new HashMap<>();
        players.put(1, new Player("pizzocchero"));
        players.put(2, new Player("gianny"));
        boardManager = boardManagerFactory.createBoardManager((HashMap<Integer, PlayerInterface>) players);
    }

    public void testCreateStructure() {
    }

    public void testCreateDevelopmentDeckTable() {
    }
}