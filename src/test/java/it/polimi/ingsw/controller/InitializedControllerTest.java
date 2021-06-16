package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.controllerMsg.CChooseLeaderCardResponseMsg;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InitializedControllerTest extends TestCase {

    InitializedController initializedController = null;
    InitializedController initializedController2 = null;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> players = new ArrayList<>();
        players.add(0,"pippo");
        players.add(1,"pluto");
        players.add(2,"paperino");
        Map<String, VirtualView> virtualView = new HashMap<>();
        virtualView.put("pippo", new VirtualView(new ClientHandler(new Socket(),"lalal")));
        initializedController = new InitializedController(players,virtualView);

        ArrayList<String> player = new ArrayList<>();
        player.add("philip");
        VirtualView vv = new VirtualView(new ClientHandler(new Socket(), "ppp"));
        Map<String, VirtualView> virtualView2 = new HashMap<>();
        virtualView2.put("philip", vv);
        initializedController2 = new InitializedController(player, virtualView2);
    }

    @After
    public void tearDown() throws Exception {
        initializedController = null;
        initializedController2 = null;
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

        assertEquals(true, initializedController.getTurnSequence().get(1).hasInkpot());
        assertEquals(false, initializedController.getTurnSequence().get(2).hasInkpot());
        assertEquals(false, initializedController.getTurnSequence().get(3).hasInkpot());


    }

    @Test
    public void testReceiveMsg() throws InvalidActionException {
        initializedController.createGame();
        ArrayList<Integer> card = new ArrayList<>();
        card.add(1);
        card.add(2);
        ArrayList<Integer> card2 = new ArrayList<>();
        card2.add(3);
        card2.add(4);
        //CChooseLeaderCardResponseMsg m = new CChooseLeaderCardResponseMsg("boo", card, card2, "pippo", "firstChoose");
        //initializedController.receiveMsg(m);

        //
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