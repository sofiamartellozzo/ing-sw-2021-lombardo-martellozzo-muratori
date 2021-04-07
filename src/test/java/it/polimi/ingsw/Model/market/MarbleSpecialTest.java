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
    public void testChoose() {
        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("sofia");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ms.choose(player); //The method called by player that he have to choose the type of resource to give is the YELLOW
        Resource r = new Resource(Color.YELLOW); //create the resource that this Marble shoult add to the Player Wharehouse
        assertEquals(r, player.getGameSpace().getResourceManager().getResources().get(0)); //check if the resource is in the Wharehouse
    }
}