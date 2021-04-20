package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.factory.PersonalSoloBoardFactory;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class PlusTwoBlackCrossActionAbilityTest extends TestCase {

    PlusTwoBlackCrossActionAbility a = null;

    @Before
    public void setUp() throws Exception {
        a = new PlusTwoBlackCrossActionAbility();
    }

    @After
    public void tearDown() throws Exception {
        a = null;
    }

    @Test
    public void testActiveAbility() {
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        SoloPlayer player = new SoloPlayer("pippo");
        HashMap<Integer, PlayerInterface> p = new HashMap<>();
        p.put(1,player);
        BoardManager boardManager = boardManagerFactory.createBoardManager(p);
        PersonalSoloBoardFactory personalSoloBoardFactory = new PersonalSoloBoardFactory();
        SoloPersonalBoard soloPersonalBoard = personalSoloBoardFactory.createGame();
        player.setGameSpace(soloPersonalBoard);
        a.activeAbility(boardManager, player);

        assertEquals(2, player.getGameSpace().getLorenzoIlMagnifico().getPosition());
    }

}