package it.polimi.ingsw.Model.actionAbility;

import it.polimi.ingsw.Model.BoardManager;
import it.polimi.ingsw.Model.BoardManagerFactory;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloPlayer;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        Map<Integer, Player> player = new HashMap<>();
        SoloPlayer player1 = new SoloPlayer("gigi");
        BoardManagerFactory bMF = new BoardManagerFactory();
        //BoardManager boardManager = bMF.createBoardManager();
        //a.activeAbility();
    }
}