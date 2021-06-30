package it.polimi.ingsw.controller;

import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.cardAbility.TypeAbility;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;

import java.net.Socket;
import java.util.*;

public class ActionControllerTest extends TestCase {
    ActionController actionController;
    public void setUp() throws Exception {
        super.setUp();
        Player player = new Player("user");
        ArrayList<Box> boxes = new ArrayList<>();
        for(int i=0;i<25;i++){
            boxes.add(new GoldBox(i+1,i+1));
        }
        ArrayList<CardSpace> cardSpaces=new ArrayList<>();
        for(int i=0;i<3;i++){
            cardSpaces.add(new CardSpace(1));
        }
        player.setGameSpace(new PersonalBoard(new FaithTrack(boxes,null,new FaithMarker(),null),new ResourceManager(new StrongBox(),new WarehouseStandard()),cardSpaces));
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        ArrayList<Object> requirements1= new ArrayList<>();
        LinkedTreeMap<Object,Object> r1_1 = new LinkedTreeMap<>();
        r1_1.put("color","BLUE");
        r1_1.put("typeResource","SHIELD");
        requirements1.add(r1_1);
        LinkedTreeMap<Object,Object> r1_2 = new LinkedTreeMap<>();
        r1_2.put("color","BLUE");
        r1_2.put("typeResource","SHIELD");
        requirements1.add(r1_2);
        leaderCards.add(new LeaderCard(1,1, TypeAbility.ADDITIONAL_POWER, TypeResource.COIN,requirements1));
        ArrayList<Object> requirements2 = new ArrayList<>();
        LinkedTreeMap<Object,Object> r2_1 = new LinkedTreeMap<>();
        r2_1.put("color","GREEN");
        r2_1.put("level",0);
        requirements2.add(r2_1);
        LinkedTreeMap<Object,Object> r2_2 = new LinkedTreeMap<>();
        r2_2.put("color","YELLOW");
        r2_2.put("level",2);
        requirements2.add(r2_2);
        leaderCards.add(new LeaderCard(2,1,TypeAbility.TRANSFORM_WHITE,TypeResource.SHIELD,requirements2));
        player.setLeaderCards(leaderCards);
        HashMap<Integer, PlayerInterface> players = new HashMap<>();
        players.put(1,player);
        BoardManager boardManager = new BoardManagerFactory().createBoardManager(players);
        Map<String, VirtualView> virtualView = new HashMap<>();
        virtualView.put(player.getUsername(), new VirtualView(new ClientHandler(new Socket(),"lalal")));
        actionController = new ActionController(player,new PlayerTurn(player,boardManager),boardManager,virtualView);
    }

    public void tearDown() throws Exception {
        actionController = null;
    }


    public void testEndAction(){
        assertSame(actionController.endAction(),false);
    }

    public void testGetPlayer(){
        PlayerInterface player = actionController.getPlayer();
        assertEquals(player.getUsername(),"user");
    }


    //TO PRIVATE METHOD
    public void testCardActivatableForPlayer() throws InvalidActionException, CardSpaceException {
        //Start the method should return an empty list
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        //First leader card
        ResourceManager resourceManager = actionController.getPlayer().getGameSpace().getResourceManager();

        //Adding one shield in warehouse
        resourceManager.addResourceToWarehouse(new Resource(TypeResource.SHIELD),2);

        //The method still should return an empty list
        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        //Adding one shield in strongbox
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(new Resource(TypeResource.SHIELD));
        resourceManager.addResourcesToStrongBox(resourcesToAdd);

        //Leader card 1 has satisfied the requirements, so adding to the "expected"
        expected.add(1);
        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        //Second leader card
        CardSpace cardSpace = actionController.getPlayer().getGameSpace().getCardSpace(0);

        //Adding one purple card level 1
        cardSpace.addCard(new DevelopmentCard(1,1,Color.PURPLE,1,null,null,null));

        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        //Adding one yellow card level 2 (one of the requirements of the second leader card)
        cardSpace.addCard(new DevelopmentCard(1,1,Color.YELLOW,2,null,null,null));

        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        cardSpace=actionController.getPlayer().getGameSpace().getCardSpace(1);

        //Adding one green card level 3 (one of the requirements of the first leader card)
        //Not possible in this conditions in the game play but in this way should go
        cardSpace.getCards().add(new DevelopmentCard(1,1,Color.GREEN,3,null,null,null));

        //Adding the new activatable leader card in the conditions
        expected.add(2);
        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());

        //Remove resources from the warehouse
        resourceManager.removeResourceFromWarehouse(2);

        //First leader card no more activatable
        expected.remove((Integer) 1);
        assertEquals(expected,actionController.cardActivatableForPlayer());
        assertSame(expected.size(),actionController.cardActivatableForPlayer().size());
    }
}