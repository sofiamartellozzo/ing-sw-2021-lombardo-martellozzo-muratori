package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.actionAbility.PlusTwoBlackCrossActionAbility;
import it.polimi.ingsw.controller.factory.PersonalSoloBoardFactory;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;

public class ActionTokenTest extends TestCase {

    ActionToken actionToken = null;

    @Before
    public void setUp() throws Exception {
        actionToken = new ActionToken(new PlusTwoBlackCrossActionAbility());
    }

    @After
    public void tearDown() throws Exception {
        actionToken = null;
    }

    public void testActiveActionToken() throws InvalidActionException {
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        SoloPlayer player = new SoloPlayer("pippo");
        HashMap<Integer, PlayerInterface> p = new HashMap<>();
        p.put(1,player);
        BoardManager boardManager = boardManagerFactory.createBoardManager(p);
        PersonalSoloBoardFactory personalSoloBoardFactory = new PersonalSoloBoardFactory();
        SoloPersonalBoard soloPersonalBoard = personalSoloBoardFactory.createGame();
        player.setGameSpace(soloPersonalBoard);
        actionToken.activeActionToken(boardManager, player);

        assertEquals(2, player.getGameSpace().getLorenzoIlMagnifico().getPosition());
    }
}