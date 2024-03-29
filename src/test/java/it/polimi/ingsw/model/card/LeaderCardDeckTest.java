package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cardAbility.TransformWhiteMarble;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderCardDeckTest extends TestCase {

    LeaderCardDeck leaderCardDeck = null;
    Player player = new Player("bob");
    HashMap<Integer, PlayerInterface> players = new HashMap<>();

    BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
    BoardManager boardManager = boardManagerFactory.createBoardManager(players);

    @Before
    public void setUp() throws Exception {

        /*
        ArrayList<Object> req = new ArrayList<>();
        req.add(0,new Resource(Color.PURPLE));
        LeaderCard card1= new LeaderCard(2,2, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
        LeaderCard card2= new LeaderCard(3,3, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
        LeaderCard card3= new LeaderCard(4,4, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
        LeaderCard card4= new LeaderCard(5,5, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        leaderCardDeck = new LeaderCardDeck(cards);*/
        players.put(1,player);
        leaderCardDeck = boardManager.getLeaderCardDeck();
    }

    @After
    public void tearDown() throws Exception {
        leaderCardDeck = null;
    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testGetCards() throws InvalidActionException {


        leaderCardDeck.getCards().get(3).activeCard(player);

        assertEquals(leaderCardDeck.getCards().get(3).getVictoryPoints(),4);

    }

    @Test
    public void testGetNumberOfCards() {

        assertEquals(16,leaderCardDeck.getNumberOfCards());
    }

    @Test
    public void testRemove() {

        ArrayList<LeaderCard> card = new ArrayList<>();
        card.add(leaderCardDeck.getCards().get(1));
        leaderCardDeck.remove(card);
        assertEquals(leaderCardDeck.getCards().size(),15);

    }

    @Test
    public void testGetLeaderCardById() throws InvalidActionException {


        leaderCardDeck.getCards().get(3).activeCard(player);

        assertEquals(leaderCardDeck.getLeaderCardById(4).getVictoryPoints(),4);
    }
}