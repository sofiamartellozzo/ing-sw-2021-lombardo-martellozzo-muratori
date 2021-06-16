package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.LimitExceededException;
import java.net.Socket;

public class RoomTest extends TestCase {

    Room room = null;

    @Before
    protected void setUp() throws Exception {
        room = new Room("Room  #0");
    }

    @After
    protected void tearDown() throws Exception {
        room = null;
    }

    @Test
    public void testIsSoloMode() {
        assertEquals(false,room.isSoloMode());
    }

    @Test
    public void testSetSoloMode() {

        room.setSoloMode(true);
        assertEquals(true,room.isSoloMode());

        room.setSoloMode(false);
        assertEquals(false,room.isSoloMode());
    }

    @Test
    public void testGetPlayersId() {
        assertEquals(0, room.getPlayersId().size());
    }

    @Test
    public void testGetRoomID() {
        assertEquals("Room  #0", room.getRoomID());
    }

    @Test
    public void testSetSIZE() {
        room.setSIZE(2);
        assertEquals(2, room.getSIZE() );
    }

    @Test
    public void testAddUser() throws LimitExceededException {
        room.addUser("pippo", new VirtualView(new ClientHandler(new Socket(),"lalal")));
        assertEquals(1, room.getPlayersId().size());
        assertEquals("pippo", room.getPlayersId().get(0));
    }

    @Test
    public void testIsFull() throws LimitExceededException {
        room.setSIZE(2);
        room.addUser("pluto", new VirtualView(new ClientHandler(new Socket(),"lalal")));
        room.addUser("paperino", new VirtualView(new ClientHandler(new Socket(),"lal")));
        //room.addUser("pluto");
        //room.addUser("paperino");
        assertEquals(true, room.isFull());
    }

    @Test
    public void testInitializedGame() throws LimitExceededException, InvalidActionException {
        room.setSIZE(2);
        room.addUser("pluto", new VirtualView(new ClientHandler(new Socket(),"lalal")));
        room.addUser("paperino", new VirtualView(new ClientHandler(new Socket(),"lal")));
        //room.addUser("pluto");
        //room.addUser("paperino");
        room.initializedGame();
        assertEquals(2, room.getPlayersId().size());
        assertEquals(false, room.isSoloMode());
        assertEquals(2, room.getTurnSequence().keySet().size());


    }

    public void testStartFirstTurn() {
    }
}