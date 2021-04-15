package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecialDepotTest {
    SpecialDepot specialDepot;

    @Before
    public void setUp() throws Exception {
        specialDepot = new SpecialDepot(new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        specialDepot = null;
    }

    @Test
    public void activeAbility() throws InvalidActionException {
        Player player = new Player("username");
        ArrayList<Box> boxes = new ArrayList<>();
        ArrayList<PopesFavorTile> popesFavorTiles=new ArrayList<>();
        FaithMarker faithMarker = new FaithMarker();
        ArrayList<VaticanSection> vaticanSections=new ArrayList<>();
        FaithTrack faithTrack = new FaithTrack(boxes,popesFavorTiles,faithMarker,vaticanSections);
        StrongBox strongBox = new StrongBox();
        Warehouse warehouse = new WarehouseStandard();
        ResourceManager resourceManager = new ResourceManager(strongBox, (WarehouseStandard) warehouse);
        ArrayList <CardSpace> cardSpaces = new ArrayList<>();
        PersonalBoard personalBoard = new PersonalBoard(faithTrack,resourceManager,cardSpaces);
        player.setGameSpace(personalBoard);
        assertSame(3,player.getGameSpace().getResourceManager().getWarehouse().getDepots().size());
        assertFalse(specialDepot.isActive());
        specialDepot.activeAbility(player);
        assertTrue(specialDepot.isActive());
        assertSame(4,player.getGameSpace().getResourceManager().getWarehouse().getDepots().size());
        specialDepot = new SpecialDepot(new Resource(Color.PURPLE));
        assertFalse(specialDepot.isActive());
        specialDepot.activeAbility(player);
        assertTrue(specialDepot.isActive());
        assertSame(5,player.getGameSpace().getResourceManager().getWarehouse().getDepots().size());
    }

    @Test
    public void testToString() {
        assertEquals("SpecialDepot",specialDepot.toString());
    }
}