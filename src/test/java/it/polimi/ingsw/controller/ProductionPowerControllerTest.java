package it.polimi.ingsw.controller;

import it.polimi.ingsw.message.controllerMsg.CActivateProductionPowerResponseMsg;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithMarker;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.resourceManagement.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import junit.framework.TestCase;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductionPowerControllerTest extends TestCase {
    ProductionPowerController productionPowerController;
    Player player;

    public void setUp() throws Exception {
        super.setUp();
        //Creating player
        player= new Player("username");
        //Creating card spaces
        ArrayList<CardSpace> cardSpaces= new ArrayList<>();
        cardSpaces.add(new CardSpace(1));
        cardSpaces.add(new CardSpace(2));
        cardSpaces.add(new CardSpace(3));
        //Creating game space for the player
        player.setGameSpace(new PersonalBoard(new FaithTrack(new ArrayList<>(),new ArrayList<>(),new FaithMarker(),new ArrayList<>()),new ResourceManager(new StrongBox(), new WarehouseStandard()),cardSpaces));
        //Inserting resource in the warehouse to test the payment of the production power
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.SHIELD),1);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.COIN),2);
        player.getGameSpace().getWarehouse().addResource(new Resource(TypeResource.COIN),2);

        /*
        * Warehouse situation
        * Depot 1 -> 1 SHIELD
        * Depot 2 -> 2 COIN
        * Depot 3 -> EMPTY
        * */

        //Creating development card
        ArrayList<Resource> earnProductionPower = new ArrayList<>();
        earnProductionPower.add(new Resource(TypeResource.SERVANT));
        earnProductionPower.add(new Resource(TypeResource.STONE));
        ArrayList<Resource> costProductionPower = new ArrayList<>();
        costProductionPower.add(new Resource(TypeResource.SHIELD));
        costProductionPower.add(new Resource(TypeResource.COIN));
        DevelopmentCard developmentCard = new DevelopmentCard(1,0, Color.GREEN,1,null,earnProductionPower,costProductionPower);
        //Adding in the first card space
        player.getGameSpace().getCardSpace(0).addCard(developmentCard);
        //Create the controller
        Map<String, VirtualView> virtualView = new HashMap<>();
        virtualView.put("pippo", new VirtualView(new ClientHandler(new Socket(),"lalal")));
        productionPowerController=new ProductionPowerController(player, virtualView);
    }

    public void tearDown() throws Exception {
        player=null;
        productionPowerController=null;
    }

    public void testReceiveMsg_CActivateProductionPowerResponseMsg_CardSpace1() {
        assertTrue(productionPowerController.getReceivedResources().isEmpty());
        CActivateProductionPowerResponseMsg msg = new CActivateProductionPowerResponseMsg("Activation Production Power",player.getUsername(),"Warehouse",1);
        productionPowerController.receiveMsg(msg);
        ArrayList<ArrayList<TypeResource>> expectedWarehouse = new ArrayList<>();
        for(int i=0;i<3;i++){
            expectedWarehouse.add(new ArrayList<>());
        }
        expectedWarehouse.get(1).add(TypeResource.COIN);
        Warehouse warehouse = player.getGameSpace().getWarehouse();
        assertSame(expectedWarehouse.size(),warehouse.getDepots().size());
        for(int i=0;i<warehouse.getDepots().size();i++){
            Depot depot = warehouse.getDepots().get(i);
            assertEquals(expectedWarehouse.get(i).size(),depot.getResources().size());
            assertEquals(expectedWarehouse.get(i).isEmpty(),depot.getResources().isEmpty());
            if(!depot.getResources().isEmpty()){
                for(int j=0;j<depot.getResources().size();j++){
                    assertEquals(expectedWarehouse.get(i).get(j),depot.getResources().get(j).getType());
                }
            }
        }
        ArrayList<Resource> expectedReceivedResources = new ArrayList<>();
        expectedReceivedResources.add(new Resource(TypeResource.SERVANT));
        expectedReceivedResources.add(new Resource(TypeResource.STONE));
        assertFalse(productionPowerController.getReceivedResources().isEmpty());
        assertSame(expectedReceivedResources.size(),productionPowerController.getReceivedResources().size());
        for(int i=0;i<expectedReceivedResources.size();i++){
            assertEquals(expectedReceivedResources.get(i).getType(),productionPowerController.getReceivedResources().get(i).getType());
        }
    }

    public void testReceiveMsg_CActivateProductionPowerResponseMsg_StandardPP() {
        assertTrue(productionPowerController.getReceivedResources().isEmpty());
        CActivateProductionPowerResponseMsg msg = new CActivateProductionPowerResponseMsg("Activation Production Power",player.getUsername(),"Warehouse",0);
        ArrayList<TypeResource> resourcesToPay = new ArrayList<>();
        resourcesToPay.add(TypeResource.COIN);
        resourcesToPay.add(TypeResource.SHIELD);
        msg.setResourcesToPay(resourcesToPay);
        msg.setResourceToGet(TypeResource.SERVANT);
        productionPowerController.receiveMsg(msg);
        ArrayList<ArrayList<TypeResource>> expectedWarehouse = new ArrayList<>();
        for(int i=0;i<3;i++){
            expectedWarehouse.add(new ArrayList<>());
        }
        expectedWarehouse.get(1).add(TypeResource.COIN);
        Warehouse warehouse = player.getGameSpace().getWarehouse();
        assertSame(expectedWarehouse.size(),warehouse.getDepots().size());
        for(int i=0;i<warehouse.getDepots().size();i++){
            Depot depot = warehouse.getDepots().get(i);
            System.out.println("Depot "+(i+1));
            assertEquals(expectedWarehouse.get(i).size(),depot.getResources().size());
            assertEquals(expectedWarehouse.get(i).isEmpty(),depot.getResources().isEmpty());
            if(!depot.getResources().isEmpty()){
                for(int j=0;j<depot.getResources().size();j++){
                    assertEquals(expectedWarehouse.get(i).get(j),depot.getResources().get(j).getType());
                }
            }
        }
        ArrayList<Resource> expectedReceivedResources = new ArrayList<>();
        expectedReceivedResources.add(new Resource(TypeResource.SERVANT));
        assertFalse(productionPowerController.getReceivedResources().isEmpty());
        assertSame(expectedReceivedResources.size(),productionPowerController.getReceivedResources().size());
        for(int i=0;i<expectedReceivedResources.size();i++){
            assertEquals(expectedReceivedResources.get(i).getType(),productionPowerController.getReceivedResources().get(i).getType());
        }
    }

    public void testReceiveResources(){
        StrongBox strongBox = player.getGameSpace().getStrongbox();
        ArrayList<TypeResource> expectedStrongBox = new ArrayList<>();
        assertTrue(strongBox.getContent().isEmpty());
        assertSame(expectedStrongBox.size(),strongBox.getContent().size());
        testReceiveMsg_CActivateProductionPowerResponseMsg_CardSpace1();
        productionPowerController.receiveResources();
        expectedStrongBox.add(TypeResource.SERVANT);
        expectedStrongBox.add(TypeResource.STONE);
        assertFalse(strongBox.getContent().isEmpty());
        assertSame(expectedStrongBox.size(),strongBox.getContent().size());
        for(int i=0;i<expectedStrongBox.size();i++){
            assertEquals(expectedStrongBox.get(i),strongBox.getContent().get(i).getType());
        }

    }

    public void testGetReceivedResources(){
        assertTrue(productionPowerController.getReceivedResources().isEmpty());
        ArrayList<TypeResource> expected = new ArrayList<>();
        assertSame(expected.size(),productionPowerController.getReceivedResources().size());
        productionPowerController.getReceivedResources().add(new Resource(TypeResource.SERVANT));
        productionPowerController.getReceivedResources().add(new Resource(TypeResource.COIN));
        expected.add(TypeResource.SERVANT);
        expected.add(TypeResource.COIN);
        assertSame(expected.size(),productionPowerController.getReceivedResources().size());
        for(int i=0;i<expected.size();i++){
            assertEquals(expected.get(i),productionPowerController.getReceivedResources().get(i).getType());
        }
    }
}