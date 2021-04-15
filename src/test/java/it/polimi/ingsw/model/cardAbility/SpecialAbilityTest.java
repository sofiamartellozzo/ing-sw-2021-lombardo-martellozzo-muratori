package it.polimi.ingsw.model.cardAbility;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecialAbilityTest {
    SpecialAbility additionalPower;
    SpecialAbility discount;
    SpecialAbility specialDepot;
    SpecialAbility transformWhiteMarble;
    @Before
    public void setUp() throws Exception {
        additionalPower=new AdditionalPower(new Resource(Color.BLUE));
        discount = new Discount(new Resource(Color.GREY));
        specialDepot = new SpecialDepot(new Resource(Color.PURPLE));
        transformWhiteMarble = new TransformWhiteMarble(new Resource(Color.YELLOW));
    }

    @After
    public void tearDown() throws Exception {
        additionalPower=null;
        discount=null;
        specialDepot=null;
        transformWhiteMarble=null;
    }

    @Test
    public void setActive() {
        assertFalse(additionalPower.isActive());
        assertFalse(discount.isActive());
        assertFalse(specialDepot.isActive());
        assertFalse(transformWhiteMarble.isActive());
        additionalPower.setActive(true);
        discount.setActive(true);
        specialDepot.setActive(true);
        transformWhiteMarble.setActive(true);
        assertTrue(additionalPower.isActive());
        assertTrue(discount.isActive());
        assertTrue(specialDepot.isActive());
        assertTrue(transformWhiteMarble.isActive());
    }

    @Test
    public void isActive() {
        setActive();
    }

    @Test
    public void getResource() {
        assertEquals((new Resource(Color.BLUE)).getType(),additionalPower.getResource().getType());
        assertEquals((new Resource(Color.GREY)).getType(),discount.getResource().getType());
        assertEquals((new Resource(Color.PURPLE)).getType(),specialDepot.getResource().getType());
        assertEquals((new Resource(Color.YELLOW)).getType(),transformWhiteMarble.getResource().getType());
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

        assertFalse(additionalPower.isActive());
        assertFalse(discount.isActive());
        assertFalse(specialDepot.isActive());
        assertFalse(transformWhiteMarble.isActive());
        additionalPower.activeAbility(player);
        discount.activeAbility(player);
        specialDepot.activeAbility(player);
        transformWhiteMarble.activeAbility(player);
        assertTrue(additionalPower.isActive());
        assertTrue(discount.isActive());
        assertTrue(specialDepot.isActive());
        assertTrue(transformWhiteMarble.isActive());
        //To see the specific effects of all ability go to the specific test
    }

    @Test
    public void testToString() {
        assertEquals("AdditionalPower",additionalPower.toString());
        assertEquals("Discount",discount.toString());
        assertEquals("SpecialDepot",specialDepot.toString());
        assertEquals("TransformWhiteMarble",transformWhiteMarble.toString());
    }
}