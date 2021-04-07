package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MarketStructureTest extends TestCase {

    MarketStructure marketStructure = null;

    @Before
    public void setUp() throws Exception {
        Marble m = new ColoredMarble(Color.YELLOW);
        Marble m1 = new RedMarble();
        Marble m2 = new ColoredMarble(Color.YELLOW);
        Marble m3 = new ColoredMarble(Color.WHITE);
        Marble m4 = new ColoredMarble(Color.PURPLE);
        Marble m5 = new RedMarble();
        Marble m6 = new ColoredMarble(Color.BLUE);
        Marble m7 = new ColoredMarble(Color.BLUE);
        Marble m8 = new RedMarble();
        Marble m9 = new RedMarble();
        Marble m10 = new ColoredMarble(Color.PURPLE);
        Marble m11 = new ColoredMarble(Color.YELLOW);
        Marble m12 = new ColoredMarble(Color.WHITE);
        Marble[][] structure = {{m,m1,m2,m3},{m4,m5,m6,m7},{m8,m9,m10,m11}};
        marketStructure = marketStructure.getInstance(structure, m12);
    }

    @After
    public void tearDown() throws Exception {
        marketStructure = null;
    }

    public void testGetInstance() {
    }

    @Test
    public void testRowMoveMarble() {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("pippo");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        marketStructure.rowMoveMarble(0, player);
        /* check if the resources the marble shoud have generate in the Wharehouse of the player */
        assertEquals(Color.RED, marketStructure.getStructure()[0][0].getColor());
        assertEquals(Color.YELLOW, marketStructure.getStructure()[0][0].getColor());
        assertEquals(Color.WHITE, marketStructure.getStructure()[0][0].getColor());
        assertEquals(Color.WHITE, marketStructure.getStructure()[0][0].getColor());
        /* check now the color*/
        assertEquals(Color.YELLOW, marketStructure.getSlide().getColor());

    }

    @Test
    public void testColumnMoveMarble() {
    }
}