
package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.actionAbility.PlusUneAndShuffleActionAbility;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.card.LeaderCardDeck;
import junit.framework.TestCase;

import java.util.HashMap;

public class SoloPlayerTurnTest extends TestCase {

    SoloPlayer player = new SoloPlayer("bob");
    PersonalBoardFactory f = new PersonalBoardFactory();
    PersonalBoard board = f.createGame();
    HashMap<Integer,PlayerInterface> players = new HashMap<>();
    BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
    BoardManager boardManager = boardManagerFactory.createBoardManager(players);
    SoloPlayerTurn soloPlayerTurn = new SoloPlayerTurn(player,boardManager);

    public void setUp() throws Exception {
       player.setGameSpace(board);
    }

    public void tearDown() throws Exception {
        soloPlayerTurn = null;
    }

    public void testGetCurrentPlayer() {
        assertSame(soloPlayerTurn.getCurrentPlayer(),player);
    }

    public void testActiveLeaderCard() throws InvalidActionException {

    }

    public void testDiscardLeaderCard() {
    }

    public void testCheckEndTurn() {

    }

    public void testActivateActionToken() throws InvalidActionException {

    }
}