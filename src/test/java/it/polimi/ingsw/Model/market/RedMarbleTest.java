package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedMarbleTest extends TestCase {

    RedMarble rm = null;

    @Before
    public void setUp() throws Exception {
        rm = new RedMarble();
    }

    @After
    public void tearDown() throws Exception {
        rm = null;
    }

    @Test
    public void testGetColor(){
        Color color = rm.getColor();
        assertEquals(color, Color.RED);
    }

    @Test
    public void testChoose() throws InvalidActionException{
        //the faith market is inizialized at 0
        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("sofia");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        try {
            rm.choose(player);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        //afret active this method his position should be one
        assertEquals(1, player.getGameSpace().getFaithTrack().getPositionFaithMarker());
    }
}