package it.polimi.ingsw.Model.market;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColoredMarbleTest extends TestCase {

    ColoredMarble cm = null;

    //Before is used for the method called whenever the class is instanziated
    @Before
    public void setUp() throws Exception {
        cm = new ColoredMarble(Color.YELLOW);
    }

    //every time I finish the test use @After to create the method that make null our object, to clean the space
    @After
    public void tearDown() throws Exception {
        cm = null;
    }

    //a generic test to see if a method worked
    @Test
    public void testGetColor(){
       Color color = cm.getColor();
       assertEquals(color, Color.YELLOW);
    }

    @Test
    public void testChoose() {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("sofia");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        cm.choose(player);
        Resource r = new Resource(Color.YELLOW); //create the resource that this Marble shoult add to the Player Wharehouse
        assertEquals(r, player.getGameSpace().getResourceManager().getResources().get(0)); //check if the resource is in the Wharehouse



    }
}