package it.polimi.ingsw.model.card;

import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.State;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.TransformWhiteMarble;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderCardTest extends TestCase {
    LeaderCard leaderCard = null;
    Player player = new Player("bob");
    PersonalBoardFactory f = new PersonalBoardFactory();
    PersonalBoard board = f.createGame();
    HashMap<Integer, PlayerInterface> players = new HashMap<>();

    BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
    BoardManager boardManager = boardManagerFactory.createBoardManager(players);

    @Before
    public void setUp() throws Exception {


        player.setGameSpace(board);
        leaderCard = boardManager.getLeaderCardDeck().getLeaderCardById(2);
    }

    @After
    public void tearDown() throws Exception {
        leaderCard = null;
    }

    @Test
    public void testGetSpecialAbility() {

        SpecialAbility special = leaderCard.getSpecialAbility();
        assertEquals(TypeAbility.ADDITIONAL_POWER, special.getTypeAbility());
    }

    @Test
    public void TestGetCardID(){
        assertEquals(leaderCard.getCardID(),2);
    }
    @Test
    public void testGetRequirements() {

        ArrayList<Object> ob = new ArrayList<>();
        ob = leaderCard.getRequirements();
        Object[] keys = ((LinkedTreeMap) ob.get(0)).keySet().toArray();
        Object[] values = ((LinkedTreeMap) ob.get(0)).values().toArray();
        //assertEquals(TypeResource.SERVANT,r.getType());
        assertEquals(Color.PURPLE.toString(), values[0].toString());
        assertEquals(2.0, values[1]);
    }

    @Test
    public void TestGetSpecialResource(){
        assertEquals(leaderCard.getSpecialResource(),TypeResource.STONE);
    }

    @Test
    public void testActiveCard() throws InvalidActionException, CardSpaceException {

        player.getGameSpace().getCardSpace(0).addCard(boardManager.getDevelopmentCardTable().takeCard(2,3));
        player.getGameSpace().getCardSpace(0).addCard(boardManager.getDevelopmentCardTable().takeCard(1,3));
        leaderCard.activeCard(player);
        assertEquals(new Active().toString(), leaderCard.getState().toString());
    }

    @Test
    public void testTestActiveCard()  {

        leaderCard = boardManager.getLeaderCardDeck().getLeaderCardById(1);
        boolean thrown = false;
        Player p = null;
        try {
            leaderCard.activeCard(p);
        } catch (InvalidActionException e) {
            thrown = true;
        }
        assertEquals(true, thrown);
    }

    @Test
    public void testCreateResource(){

        Resource resource = leaderCard.createResource(TypeResource.COIN);
        assertEquals(resource.getType(),TypeResource.COIN);
    }

    @Test
    public void testGetVictoryPoints() throws InvalidActionException, CardSpaceException {

        player.getGameSpace().getCardSpace(0).addCard(boardManager.getDevelopmentCardTable().takeCard(2,3));
        player.getGameSpace().getCardSpace(0).addCard(boardManager.getDevelopmentCardTable().takeCard(1,3));
        leaderCard.activeCard(player);
        assertEquals(leaderCard.getVictoryPoints(),4);
    }
}