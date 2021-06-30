package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EndGameControllerTest extends TestCase {

    EndGameController endGameController;

    @Before
    public void setUp() throws InvalidActionException, CardSpaceException {
        Player player = new Player("user");
        Player player1 = new Player("user1");
        Player player2 = new Player("user2");

        HashMap<Integer,PlayerInterface> players = new HashMap<>();
        players.put(1,player);
        players.put(2,player1);
        players.put(3,player2);
        BoardManager boardManager = new BoardManagerFactory().createBoardManager(players);

        Map<String, VirtualView> virtualView = new HashMap<>();
        virtualView.put(player.getUsername(), new VirtualView(new ClientHandler(new Socket(),"lalal")));
        virtualView.put(player.getUsername(),new VirtualView(new ClientHandler(new Socket(),"ciao")));
        virtualView.put(player.getUsername(),new VirtualView(new ClientHandler(new Socket(),"ciao1")));

        TurnController turnController = new TurnController(players,boardManager,virtualView);


        ArrayList<Box> boxes = new ArrayList<>();
        for(int i=0;i<25;i++){
            boxes.add(new GoldBox(i+1,i+1));
        }

        ArrayList<PopesFavorTile> popes = new ArrayList<>();
        for(int i=0; i<3;i++){
            popes.add(new PopesFavorTile(i+1,2));
        }

        ArrayList<CardSpace> cardSpaces=new ArrayList<>();
        for(int i=0;i<3;i++){
            cardSpaces.add(new CardSpace(1));
        }
        player.setGameSpace(new PersonalBoard(new FaithTrack(boxes,popes,new FaithMarker(),null),new ResourceManager(new StrongBox(),new WarehouseStandard()),cardSpaces));
        player1.setGameSpace(new PersonalBoard(new FaithTrack(boxes,popes,new FaithMarker(),null),new ResourceManager(new StrongBox(),new WarehouseStandard()),cardSpaces));
        player2.setGameSpace(new PersonalBoard(new FaithTrack(boxes,popes,new FaithMarker(),null),new ResourceManager(new StrongBox(),new WarehouseStandard()),cardSpaces));

        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.SHIELD),1);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.COIN),2);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.COIN),2);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.STONE),3);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.STONE),3);

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(new Resource(Color.GREY));
        cost.add(new Resource(Color.BLUE));
        cost.add(new Resource(Color.YELLOW));

        ArrayList<Resource> cost1 = new ArrayList<>();
        cost.add(new Resource(Color.GREY));
        cost.add(new Resource(Color.BLUE));
        cost.add(new Resource(Color.YELLOW));

        ArrayList<Resource> cost2 = new ArrayList<>();
        cost.add(new Resource(Color.GREY));
        cost.add(new Resource(Color.BLUE));
        cost.add(new Resource(Color.YELLOW));

        DevelopmentCard card = new DevelopmentCard(1,12,Color.PURPLE,1,cost,cost1,cost2);
        DevelopmentCard card1 = new DevelopmentCard(1,12,Color.PURPLE,1,cost,cost1,cost2);

        player.getGameSpace().getCardSpaces().get(0).addCard(card);
        player1.getGameSpace().getCardSpaces().get(1).addCard(card1);

        endGameController = new EndGameController(players,turnController,virtualView);

    }

    @After
    public void tearDown(){

        endGameController = null;
    }


    public void testGetTurnSequence()
    {
      assertEquals(endGameController.getTurnSequence().values().size(),3);
    }


    public void testStartCounting() throws InvalidActionException {

        endGameController.startCounting();

        assertEquals(endGameController.getTurnSequence().get(1).getVictoryPoints(),25);
        assertEquals(endGameController.getTurnSequence().get(2).getVictoryPoints(),24);
        assertEquals(endGameController.getTurnSequence().get(3).getVictoryPoints(),24);


    }
}