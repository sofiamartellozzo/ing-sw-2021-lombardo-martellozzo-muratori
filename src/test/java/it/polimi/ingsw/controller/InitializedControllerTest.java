package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InitializedControllerTest extends TestCase {

    InitializedController initializedController = null;
    InitializedController initializedController2 = null;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> players = new ArrayList<>();
        players.add(0,"pippo");
        players.add(1,"pluto");
        players.add(2,"paperino");
        //initializedController = new InitializedController(players);

        ArrayList<String> player = new ArrayList<>();
        players.add("philip");
        //initializedController2 = new InitializedController(player);
    }

    @After
    public void tearDown() throws Exception {
        initializedController = null;
    }

    public void testTurnCanStart() {
    }

    public void testGetBoardManager() {
    }

    @Test
    public void testGetTurnSequence() {
        //for testing the private method creatingPlayers
        assertEquals(3, initializedController.getTurnSequence().keySet().size());
         }

    public void testGetSinglePlayer() {
        //for testing the private method creatingPlayers
        assertEquals("philip", initializedController2.getSinglePlayer().getUsername());

    }

    @Test
    public void testCreateGame() {
        try {
            initializedController.createGame();
            initializedController2.createGame();
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(true,initializedController.canStart());
        assertEquals(true, initializedController2.canStart());

        assertEquals(true, initializedController.getTurnSequence().get(1).hasInkpot());
        assertEquals(false, initializedController.getTurnSequence().get(2).hasInkpot());
        assertEquals(false, initializedController.getTurnSequence().get(3).hasInkpot());


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