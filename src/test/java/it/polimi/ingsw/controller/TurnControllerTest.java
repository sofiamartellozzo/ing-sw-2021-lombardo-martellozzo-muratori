package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class TurnControllerTest extends TestCase {

    TurnController turnController = null;

    @Before
    public void setUp() throws Exception {
        HashMap<Integer, PlayerInterface> players = new HashMap<>();
        players.put(1, new Player("A"));
        players.put(2, new Player("B"));
        players.put(3, new Player("C"));
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        //turnController = new TurnController(players, boardManagerFactory.createBoardManager(players));
    }

    @After
    public void tearDown() throws Exception {
        turnController = null;
    }

    @Test
    public void testGamePlay() throws InvalidActionException {
        turnController.gamePlay();
        assertEquals(true, turnController.getCurrentPlayer().isPlaying());
    }

    public void testNextTurn() {
    }

    public void testReceiveMsg() {
    }

    public void testTestReceiveMsg() {
    }

    public void testTestReceiveMsg1() {
    }

    public void testTestReceiveMsg2() {
    }

    public void testTestReceiveMsg3() {
    }

    public void testTestReceiveMsg4() {
    }

    public void testTestReceiveMsg5() {
    }

    public void testTestReceiveMsg6() {
    }

    public void testTestReceiveMsg7() {
    }

    public void testTestReceiveMsg8() {
    }

    public void testTestReceiveMsg9() {
    }

    public void testTestReceiveMsg10() {
    }

    public void testTestReceiveMsg11() {
    }

    public void testTestReceiveMsg12() {
    }
}