package it.polimi.ingsw.model.card;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.TransformWhiteMarble;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class LeaderCardTest extends TestCase {

    LeaderCard leaderCard = null;

    public void setUp() throws Exception {

        ArrayList<Object> req = new ArrayList<>();
        req.add(0,new Resource(Color.PURPLE));
        leaderCard = new LeaderCard(2,2, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
    }

    @After
    public void tearDown() throws Exception {
        leaderCard = null;
    }

    @Test
    public void testGetSpecialAbility() {

        SpecialAbility special = leaderCard.getSpecialAbility();
    }

    @Test
    public void testGetRequirements() {

        ArrayList<Object> ob = new ArrayList<>();
        ob = leaderCard.getRequirements();
    }

    @Test
    public void testActiveCard() throws InvalidActionException {

        Player player = new Player("bob");
        //leaderCard.activeCard(player);
        leaderCard.getState();
    }

    @Test
    public void testTestActiveCard() throws InvalidActionException {

        Player player = new Player("bob");
        Resource res = new Resource(Color.PURPLE);

        //leaderCard.activeCard(res,player);
    }

    @Test
    public void testCreateSpecialAbility() {

        SpecialAbility special = leaderCard.createSpecialAbility(TypeAbility.SPECIAL_DEPOT);
    }

    @Test
    public void testCreateResource(){

        Resource resource = leaderCard.createResource(TypeResource.COIN);
    }

    @Test
    public void testGetVictoryPoints() throws InvalidActionException {

        leaderCard.activeCard(new Player("gimmi"));
        assertEquals(2,leaderCard.getVictoryPoints());
    }
}