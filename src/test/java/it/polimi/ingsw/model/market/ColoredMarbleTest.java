package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
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
    public void testChoose() throws InvalidActionException{

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("sofia");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        TypeResource typeResource = null;
        try {
            typeResource = cm.choose(player);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        Resource r = new Resource(Color.YELLOW); //create the resource that this Marble shoult add to the Player Wharehouse
        assertEquals(r.getType(), typeResource); //check if the resource is in the Wharehouse



    }
}