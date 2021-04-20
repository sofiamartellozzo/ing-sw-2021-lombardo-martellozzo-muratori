package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MarbleSpecialTest extends TestCase {

    MarbleSpecial ms = null;

    @Before
    public void setUp() throws Exception {
        ms = new MarbleSpecial();
    }

    @After
    public void tearDown() throws Exception {
        ms = null;
    }

    @Test
    public void testGetColor(){
        Color color = ms.getColor();
        assertEquals(color, Color.WHITE);
    }

    @Test
    public void testChoose() throws InvalidActionException{
        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("sofia");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        try {
            ms.choose(player); //The method called by player that he have to choose the type of resource to give is the YELLOW
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        Resource r = new Resource(Color.YELLOW); //create the resource that this Marble shoult add to the Player Wharehouse
        assertEquals(r.getColor(), player.getGameSpace().getResourceManager().getResources().get(0).getColor()); //check if the resource is in the Wharehouse
    }
}