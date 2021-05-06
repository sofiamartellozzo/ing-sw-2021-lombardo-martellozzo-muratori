package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.beans.beancontext.BeanContextMembershipEvent;
import java.util.ArrayList;

public class PersonalBoardTest extends TestCase {

    PersonalBoard personalBoard = null;

    @Before
    public void setUp() throws Exception {
       PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
       personalBoard = personalBoardFactory.createGame();
    }

    @After
    public void tearDown() throws Exception {
        personalBoard = null;
    }

    @Test
    public void testGetFaithTrack() {
        FaithTrack faithTrack = personalBoard.getFaithTrack();
        assertEquals(0, faithTrack.getPositionFaithMarker());
        assertEquals(3, faithTrack.getVaticanSections().size());
        assertEquals(3, faithTrack.getPopesFavorTiles().size());
        assertEquals(3, faithTrack.getPopeBoxes().size());
        assertEquals(25, faithTrack.getPathBox().size());
    }

    @Test
    public void testGetWarehouse() {
    }

    @Test
    public void testGetStrongbox() {
    }

    @Test
    public void testGetResourceManager() {
    }

    @Test
    public void testGetCardSpaces() {
    }

    @Test
    public void testGetCardSpace() {
    }

    @Test
    public void testGetAllCards() {
    }

    @Test
    public void testGetAllCardOfOneSpace() {
    }

    @Test
    public void testInvokeProductionPowerFromStrongBox() {
    }

    @Test
    public void testGetVictoryPointsFromCardSpaces() {
    }

    @Test
    public void testGetActivatableCardSpace() throws InvalidActionException {
        Player player = new Player("player");
        Warehouse warehouse= personalBoard.getWarehouse();
        StrongBox strongBox= personalBoard.getStrongbox();
        warehouse.addResource(new Resource(TypeResource.SHIELD),2);
        warehouse.addResource(new Resource(TypeResource.SHIELD),2);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected,personalBoard.getActivatableCardSpace(player));
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource((TypeResource.SHIELD)));
        cost.add(new Resource(TypeResource.SHIELD));
        DevelopmentCard developmentCard = new DevelopmentCard(0, Color.GREEN,1,null,null,cost);
        personalBoard.getCardSpace(0).addCard(developmentCard);
        expected.add(1);
        assertEquals(expected,personalBoard.getActivatableCardSpace(player));
        personalBoard.getCardSpace(1).addCard(developmentCard);
        expected.add(2);
        assertEquals(expected,personalBoard.getActivatableCardSpace(player));
        personalBoard.getCardSpace(2).addCard(developmentCard);
        expected.add(3);
        assertEquals(expected,personalBoard.getActivatableCardSpace(player));
        /*
        DA RIVEDERE
        LeaderCard leaderCard = new LeaderCard(0,0, TypeAbility.ADDITIONAL_POWER,TypeResource.SHIELD,null);
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(leaderCard);

        player.setLeaderCards(leaderCards);
        SpecialCard specialCard = new SpecialCard(cost);
        player.addSpecialCard(specialCard);
        expected.add(4);
        assertEquals(expected,personalBoard.getActivatableCardSpace(player));

         */
    }
}