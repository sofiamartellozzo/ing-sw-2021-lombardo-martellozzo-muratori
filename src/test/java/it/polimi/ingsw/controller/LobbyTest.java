package it.polimi.ingsw.controller;

import it.polimi.ingsw.message.controllerMsg.CConnectionRequestMsg;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyTest extends TestCase {

    Lobby lobby = null;


    @Before
    public void setUp() throws Exception {
        lobby = lobby.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        lobby = null;
    }

    @Test
    public void testCanInitializeGameFor() {

    }

    public void testStartInitializationOfTheGame() {
    }

    @Test
    public void testReceiveMsg() throws UnknownHostException {
        // CConnectionRequestMsg
        byte[] ipAddr = new byte[]{127, 0, 0, 1};
        InetAddress addr = InetAddress.getByAddress(ipAddr);
        CConnectionRequestMsg msg = new CConnectionRequestMsg("first connection request", addr, 4444, "patrizio", "1" );
        msg.setVirtualView(new VirtualView(new ClientHandler(new Socket(),"lalal")));
        lobby.receiveMsg(msg);
        assertEquals(1, lobby.getUsersAssigned().size());
        assertEquals("patrizio", lobby.getUsersAssigned().get(0));
        //assertEquals(false, lobby.getCanCreateRoom());
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
}