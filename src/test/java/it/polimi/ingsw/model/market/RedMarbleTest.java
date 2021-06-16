package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
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
        TypeResource fromMarket = null;
        try {
            fromMarket= rm.choose(player);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
       assertEquals(TypeResource.FAITHMARKER, fromMarket);
    }
}