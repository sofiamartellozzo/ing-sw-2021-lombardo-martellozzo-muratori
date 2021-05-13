package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;

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
        marketStructure = new MarketStructure(structure,m12);
    }

    @After
    public void tearDown() throws Exception {
        marketStructure = null;
    }

    public void testGetInstance() {
    }

    @Test
    public void testRowMoveMarble() throws InvalidActionException {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("pippo");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<TypeResource> resources = marketStructure.rowMoveMarble(0, player);
        /* check if the resources the marble shoud have generate in the Wharehouse of the player */
        assertEquals(Color.RED, marketStructure.getStructure()[0][0].getColor());
        assertEquals(Color.YELLOW, marketStructure.getStructure()[0][1].getColor());
        assertEquals(Color.WHITE, marketStructure.getStructure()[0][2].getColor());
        assertEquals(Color.WHITE, marketStructure.getStructure()[0][3].getColor());
        /* check now the color*/
        assertEquals(Color.YELLOW, marketStructure.getSlide().getColor());

        //check the returned resources
        assertEquals(TypeResource.COIN, resources.get(0));
        assertEquals(TypeResource.FAITHMARKER, resources.get(1));
        assertEquals(TypeResource.COIN, resources.get(2));

    }

    @Test
    public void testColumnMoveMarble() throws InvalidActionException {
        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("pippo");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<TypeResource> resources = marketStructure.columnMoveMarble(0, player);
        assertEquals(Color.PURPLE, marketStructure.getStructure()[0][0].getColor());
        assertEquals(Color.RED, marketStructure.getStructure()[1][0].getColor());
        assertEquals(Color.WHITE, marketStructure.getStructure()[2][0].getColor());
        assertEquals(Color.YELLOW, marketStructure.getSlide().getColor());

        //check the returned resources
        assertEquals(TypeResource.COIN, resources.get(0));
        assertEquals(TypeResource.SERVANT, resources.get(1));
        assertEquals(TypeResource.FAITHMARKER, resources.get(2));
    }
}