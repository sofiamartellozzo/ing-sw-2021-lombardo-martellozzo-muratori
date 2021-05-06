package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cardAbility.SpecialAbility;
import it.polimi.ingsw.model.cardAbility.TransformWhiteMarble;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class LeaderCardTest extends TestCase {

    LeaderCard leaderCard = null;

    @Before
    public void setUp() throws Exception {

        ArrayList<Object> req = new ArrayList<>();
        req.add(new Resource(Color.PURPLE));
        leaderCard = new LeaderCard(2,2, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
    }

    @After
    public void tearDown() throws Exception {
        leaderCard = null;
    }

    @Test
    public void testGetSpecialAbility() {

        SpecialAbility special = leaderCard.getSpecialAbility();
        assertEquals(TypeAbility.SPECIAL_DEPOT, special.getTypeAbility());
    }

    @Test
    public void testGetRequirements() {

        ArrayList<Object> ob = new ArrayList<>();
        ob = leaderCard.getRequirements();
        Resource r = (Resource) ob.get(0);
        assertEquals(TypeResource.SERVANT,r.getType());
    }

    @Test
    public void testActiveCard() throws InvalidActionException {

        Player player = new Player("bob");
        leaderCard.activeCard(player);
        assertEquals(new Active().toString(), leaderCard.getState().toString());
    }

    @Test
    public void testTestActiveCard() throws InvalidActionException {

        Player player = new Player("bob");
        Resource res = new Resource(Color.PURPLE);

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<Resource> array = new ArrayList<>();
        array.add(new Resource(Color.BLUE));

        player.getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.PURPLE),1);
        player.getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.YELLOW),2);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);

        String where = "Warehouse";
        leaderCard.activeCard(res,player);

        assertEquals(leaderCard.getState().toString(),"Active");
    }

    @Test
    public void testCreateResource(){

        Resource resource = leaderCard.createResource(TypeResource.COIN);
        assertEquals(resource.getType(),TypeResource.COIN);
    }

    @Test
    public void testGetVictoryPoints() throws InvalidActionException {

        leaderCard.activeCard(new Player("gimmi"));
        assertEquals(leaderCard.getVictoryPoints(),2);
    }
}