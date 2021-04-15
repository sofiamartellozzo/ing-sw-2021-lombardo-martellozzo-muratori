package it.polimi.ingsw.model.actionAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class CardActionAbilityTest extends TestCase {

    CardActionAbility cardActionAbility = null;

    boolean thrown = false;

    @Before
    public void setUp() throws Exception {
        cardActionAbility = new CardActionAbility(Color.GREEN);
    }

    @After
    public void tearDown() throws Exception {
        cardActionAbility = null;
    }

    @Test
    public void testActiveAbility() throws InvalidActionException {
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        SoloPlayer player = new SoloPlayer("pippo");
        HashMap<Integer,PlayerInterface> p = new HashMap<>();
        p.put(1,player);
        BoardManager boardManager = boardManagerFactory.createBoardManager(p);

        cardActionAbility.activeAbility(boardManager,player);

        assertEquals(2, boardManager.getDevelopmentCardTable().getTable()[2][0].getDevelopDeck().size());
    }


    @Test (expected = IndexOutOfBoundsException.class)
    public void testActiveAbility_EmptyDek() throws InvalidActionException, IndexOutOfBoundsException{
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        SoloPlayer player = new SoloPlayer("pippo");
        HashMap<Integer,PlayerInterface> p = new HashMap<>();
        p.put(1,player);
        BoardManager boardManager = boardManagerFactory.createBoardManager(p);

        boardManager.getDevelopmentCardTable().getTable()[0][0].takeCard();
        boardManager.getDevelopmentCardTable().getTable()[0][0].getDevelopDeck().remove(2);
        boardManager.getDevelopmentCardTable().getTable()[0][0].getDevelopDeck().remove(1);
        boardManager.getDevelopmentCardTable().getTable()[0][0].getDevelopDeck().remove(0);

        boardManager.getDevelopmentCardTable().getTable()[1][0].getDevelopDeck().remove(3);
        boardManager.getDevelopmentCardTable().getTable()[1][0].getDevelopDeck().remove(2);
        boardManager.getDevelopmentCardTable().getTable()[1][0].getDevelopDeck().remove(1);
        boardManager.getDevelopmentCardTable().getTable()[1][0].getDevelopDeck().remove(0);

        boardManager.getDevelopmentCardTable().getTable()[2][0].getDevelopDeck().remove(3);
        boardManager.getDevelopmentCardTable().getTable()[2][0].getDevelopDeck().remove(2);
        boardManager.getDevelopmentCardTable().getTable()[2][0].getDevelopDeck().remove(1);
        boardManager.getDevelopmentCardTable().getTable()[2][0].getDevelopDeck().remove(0);
        try{
            cardActionAbility.activeAbility(boardManager,player);
        } catch (InvalidActionException e){
            thrown = true;
        }
        assertTrue(thrown);
    }
}