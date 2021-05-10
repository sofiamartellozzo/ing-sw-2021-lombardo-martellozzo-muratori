package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.factory.DevelopmentCardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithMarker;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class DevelopmentCardTableTest extends TestCase {

    DevelopmentCardTable developmentCardTable = null;

    @Before
    public void setUp() throws Exception {


        DevelopmentCardDeck[][] squareCards = new DevelopmentCardDeck[3][4];

        //create the hash map for the cost of a Card
        ArrayList<Resource> array = new ArrayList<>();
        array.add(0,new Resource(Color.BLUE));
        array.add(1,new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0,new Resource(Color.GREY));
        proceeds.add(1,new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0,new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card2 = new DevelopmentCard(2,2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card3 = new DevelopmentCard(6,6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card4 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        DevelopmentCardDeck smallDeck1 = new DevelopmentCardDeck(list);

        DevelopmentCard card91 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card92 = new DevelopmentCard(2,2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card93 = new DevelopmentCard(6,6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card94 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        list12.add(card91);
        list12.add(card92);
        list12.add(card93);
        list12.add(card94);
        DevelopmentCardDeck smallDeck12 = new DevelopmentCardDeck(list12);

        DevelopmentCard card81 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card82 = new DevelopmentCard(2,2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card83 = new DevelopmentCard(6,6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card84 = new DevelopmentCard(3,3,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list13 = new ArrayList<>();
        list13.add(card91);
        list13.add(card92);
        list13.add(card93);
        list13.add(card94);
        DevelopmentCardDeck smallDeck13 = new DevelopmentCardDeck(list13);



        DevelopmentCard card5 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card6 = new DevelopmentCard(2,2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card7 = new DevelopmentCard(6,6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card8 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card55 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card66 = new DevelopmentCard(2,2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card77 = new DevelopmentCard(6,6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card88 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card555 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card666 = new DevelopmentCard(2,2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card777 = new DevelopmentCard(6,6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card888 = new DevelopmentCard(3,3,Color.BLUE,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list1 = new ArrayList<>();
        list1.add(card5);
        list1.add(card6);
        list1.add(card7);
        list1.add(card8);
        DevelopmentCardDeck smallDeck2 = new DevelopmentCardDeck(list1);

        ArrayList<DevelopmentCard> list11 = new ArrayList<>();
        list11.add(card55);
        list11.add(card66);
        list11.add(card77);
        list11.add(card88);
        DevelopmentCardDeck smallDeck22 = new DevelopmentCardDeck(list11);

        ArrayList<DevelopmentCard> list111 = new ArrayList<>();
        list111.add(card555);
        list111.add(card666);
        list111.add(card777);
        list111.add(card888);
        DevelopmentCardDeck smallDeck222 = new DevelopmentCardDeck(list111);

        DevelopmentCard card9 = new DevelopmentCard(3,3,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card10 = new DevelopmentCard(2,2,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card11 = new DevelopmentCard(6,6,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card12 = new DevelopmentCard(3,3,Color.YELLOW,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        list2.add(card9);
        list2.add(card10);
        list2.add(card11);
        list2.add(card12);
        DevelopmentCardDeck smallDeck3 = new DevelopmentCardDeck(list2);

        DevelopmentCard card13 = new DevelopmentCard(3,3,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card14 = new DevelopmentCard(2,2,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card15 = new DevelopmentCard(6,6,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card16 = new DevelopmentCard(3,3,Color.PURPLE,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list3 = new ArrayList<>();
        list3.add(card13);
        list3.add(card14);
        list3.add(card15);
        list3.add(card16);
        DevelopmentCardDeck smallDeck4 = new DevelopmentCardDeck(list3);



        squareCards[0][0] = smallDeck1;
        squareCards[1][0]= smallDeck12;
        squareCards[2][0]= smallDeck13;

        squareCards[0][1]= smallDeck2;
        squareCards[1][1]= smallDeck22;
        squareCards[2][1]= smallDeck222;

        squareCards[0][2]= smallDeck3;
        squareCards[1][2]= smallDeck3;
        squareCards[2][2]= smallDeck3;

        squareCards[0][3]= smallDeck4;
        squareCards[1][3]= smallDeck4;
        squareCards[2][3]= smallDeck4;


        developmentCardTable = DevelopmentCardTable.getInstance(squareCards);


    }

    @After
    public void tearDown() throws Exception {
        developmentCardTable = null;
    }

    @Test
    public void testTakeCard() {
       for(int i=0;i<4;i++){
           developmentCardTable.takeCard(1,0);
       }

        //DevelopmentCard card4 = developmentCardTable.takeCard(2,0);
        assertEquals(developmentCardTable.getTable()[1][0].getDevelopDeck().size(),0);
    }

    @Test
    public void testGetSquare() throws InvalidActionException {

        boolean thrown = false;
           try {

               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);
               developmentCardTable.getSquare(Color.GREEN);

               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);
               developmentCardTable.getSquare(Color.BLUE);

           }catch (InvalidActionException e){
               thrown = true;
           }
           assertTrue(thrown);

    }

    @Test
    public void testCheckIfEmpty() throws InvalidActionException {

        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();
        developmentCardTable.getTable()[0][0].takeCard();

        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();
        developmentCardTable.getTable()[1][0].takeCard();

        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();
        developmentCardTable.getTable()[2][0].takeCard();

        assertTrue(developmentCardTable.checkIfEmpty());
    }

    @Test
    public void testGetAvailable() throws FileNotFoundException, InvalidActionException {
        developmentCardTable.setSquare(new DevelopmentCardFactory().createTable());
        boolean[][] expected = new boolean[3][4];
        //Creating player
        Player player= new Player("username");
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
        ArrayList<Resource> strongbox = new ArrayList<>();
        for(int i=0;i<3;i++){
            strongbox.add(new Resource(TypeResource.SHIELD));
            strongbox.add(new Resource(TypeResource.COIN));
            if(i!=2){
                strongbox.add(new Resource(TypeResource.SERVANT));
            }
            strongbox.add(new Resource(TypeResource.STONE));
        }
        player.getGameSpace().getStrongbox().addResources(strongbox);
        /*
        * RESOURCES IN RESOURCE MANAGER
        * 4 SHIELD
        * 5 COIN
        * 2 SERVANT
        * 3 STONE
        * */
        for(int row=0;row<3;row++){
            for(int column=0;column<4;column++){
                if(row!=2){
                    expected[row][column]=false;
                }else{
                    expected[row][column]=true;
                }
                assertEquals(expected[row][column],developmentCardTable.getAvailable(player)[row][column]);
            }
        }

        player.getGameSpace().getCardSpace(0).addCard(new DevelopmentCard(1,1,Color.GREEN,1,null,null,null));
        player.getGameSpace().getCardSpace(1).addCard(new DevelopmentCard(1,1,Color.GREEN,1,null,null,null));
        player.getGameSpace().getCardSpace(1).addCard(new DevelopmentCard(1,1,Color.GREEN,2,null,null,null));

        for(int row=0;row<3;row++){
            for(int column=0;column<4;column++){
                if(row==0){
                    if(column==0){
                        expected[row][column]=true;
                    }else{
                        expected[row][column]=false;
                    }
                }else if(row==1){
                    if(column==0 || column==1){
                        expected[row][column]=true;
                    }else if(column==2 || column==3){
                        expected[row][column]=false;
                    }
                }else if(row==2){
                    expected[row][column]=true;
                }
                if(expected[row][column]!=developmentCardTable.getAvailable(player)[row][column]){
                    System.out.println((row+1)+" "+(column+1));
                    System.out.println(developmentCardTable.getTable()[row][column].getUpperCard().getId());
                    int coin=0;
                    int shield=0;
                    int servant=0;
                    int stone=0;
                    for(Resource resource: developmentCardTable.getTable()[row][column].getUpperCard().getCost()){
                        if(resource.getType().equals(TypeResource.COIN)){
                            coin++;
                        }else if(resource.getType().equals(TypeResource.SHIELD)){
                            shield++;
                        }else if(resource.getType().equals(TypeResource.SERVANT)){
                            servant++;
                        }else if(resource.getType().equals(TypeResource.STONE)){
                            stone++;
                        }
                    }
                    System.out.println("COIN: "+coin);
                    System.out.println("SHIELD: "+shield);
                    System.out.println("SERVANT: "+servant);
                    System.out.println("STONE: "+stone);
                    System.out.println("ENOUGH RESOURCES: "+player.getGameSpace().getResourceManager().checkEnoughResources(developmentCardTable.getTable()[row][column].getUpperCard().getCost()));
                }

                assertEquals(expected[row][column],developmentCardTable.getAvailable(player)[row][column]);

            }
        }
    }
}