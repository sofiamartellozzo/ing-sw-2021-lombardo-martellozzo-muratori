package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SpecialCardTest extends TestCase {

    SpecialCard specialCard = null;

    @Before
    public void setUp() throws Exception {

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.PURPLE));
        cost.add(new Resource(Color.YELLOW));

        specialCard = new SpecialCard(cost);
    }

    @After
    public void tearDown() throws Exception {
        specialCard = null;
    }

    @Test
    public void testGetCostProductionPower() {
        assertEquals(specialCard.getCostProductionPower().size(),2);
    }


    @Test
    public void testUseProductionPower() throws InvalidActionException {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("bob");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<Resource> array = new ArrayList<>();
        array.add(new Resource(Color.BLUE));

        player.getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.PURPLE),1);
        player.getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.YELLOW),2);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);

        String where = "Warehouse";
        Resource res = new Resource(Color.YELLOW);

        specialCard.useProductionPower(player,res,where);

        assertEquals(player.getGameSpace().getResourceManager().getStrongBox().getContent().get(0).getType(), TypeResource.SHIELD);
        assertEquals(player.getGameSpace().getResourceManager().getStrongBox().getContent().get(1).getType(), TypeResource.COIN);
        assertTrue( player.getGameSpace().getResourceManager().getWarehouse().getContent().isEmpty());
    }
}