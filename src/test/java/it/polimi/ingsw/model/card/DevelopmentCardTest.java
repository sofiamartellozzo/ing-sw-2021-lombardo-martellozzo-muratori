package it.polimi.ingsw.model.card;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardFactory;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

//not done
public class DevelopmentCardTest extends TestCase {

    DevelopmentCard developmentCard = null;

    @Before
    public void setUp() throws Exception {

       Resource res = new Resource(Color.BLUE);
       Resource r1 = new Resource(Color.PURPLE);
       ArrayList<Resource> cost = new ArrayList<>();
       cost.add(res);
       cost.add(r1);

        Resource r2 = new Resource(Color.BLUE);
        ArrayList<Resource> earn = new ArrayList<>();
        earn.add(r2);

        Resource r3 = new Resource(Color.PURPLE);
        ArrayList<Resource> pay = new ArrayList<>();
        pay.add(r3);

       developmentCard = new DevelopmentCard(3, Color.BLUE,1,cost,earn,pay);
    }

    @After
    public void tearDown() throws Exception {
        developmentCard = null;
    }

    @Test
    public void testGetVictoryPoints() {
        int n = developmentCard.getVictoryPoints();
        assertEquals(n,3);
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
        player.getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.BLUE),2);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);

        String where = "Warehouse";
        developmentCard.useProductionPower(player,where);

    }
}