package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DevelopmentCardFactoryTest extends TestCase {
    DevelopmentCardDeck[][] expected;

    public void setUp() throws Exception {
        super.setUp();
        ArrayList<DevelopmentCard> cards = new ArrayList<>();

        //Card 1
        ArrayList<Resource> costToBuy1 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower1 = new ArrayList<>();
        ArrayList<Resource> costProductionPower1 = new ArrayList<>();

        costToBuy1.add(new Resource(Color.BLUE));
        costToBuy1.add(new Resource(Color.BLUE));

        earnProductionPower1.add(new Resource(Color.RED));

        costProductionPower1.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(1,1,Color.GREEN,1,costToBuy1,earnProductionPower1,costProductionPower1));

        //Card 2
        ArrayList<Resource> costToBuy2 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower2 = new ArrayList<>();
        ArrayList<Resource> costProductionPower2 = new ArrayList<>();

        costToBuy2.add(new Resource(Color.BLUE));
        costToBuy2.add(new Resource(Color.GREY));
        costToBuy2.add(new Resource(Color.PURPLE));

        earnProductionPower2.add(new Resource(Color.PURPLE));

        costProductionPower2.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(2,2,Color.GREEN,1,costToBuy2,earnProductionPower2,costProductionPower2));

        //Card 3
        ArrayList<Resource> costToBuy3 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower3 = new ArrayList<>();
        ArrayList<Resource> costProductionPower3 = new ArrayList<>();

        costToBuy3.add(new Resource(Color.BLUE));
        costToBuy3.add(new Resource(Color.BLUE));
        costToBuy3.add(new Resource(Color.BLUE));

        earnProductionPower3.add(new Resource(Color.YELLOW));
        earnProductionPower3.add(new Resource(Color.BLUE));
        earnProductionPower3.add(new Resource(Color.GREY));

        costProductionPower3.add(new Resource(Color.PURPLE));
        costProductionPower3.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(3,3,Color.GREEN,1,costToBuy3,earnProductionPower3,costProductionPower3));

        //Card 4
        ArrayList<Resource> costToBuy4 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower4 = new ArrayList<>();
        ArrayList<Resource> costProductionPower4 = new ArrayList<>();

        costToBuy4.add(new Resource(Color.BLUE));
        costToBuy4.add(new Resource(Color.BLUE));
        costToBuy4.add(new Resource(Color.YELLOW));
        costToBuy4.add(new Resource(Color.YELLOW));

        earnProductionPower4.add(new Resource(Color.YELLOW));
        earnProductionPower4.add(new Resource(Color.YELLOW));
        earnProductionPower4.add(new Resource(Color.RED));

        costProductionPower4.add(new Resource(Color.GREY));
        costProductionPower4.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(4,4,Color.GREEN,1,costToBuy4,earnProductionPower4,costProductionPower4));

        //Card 5
        ArrayList<Resource> costToBuy5 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower5 = new ArrayList<>();
        ArrayList<Resource> costProductionPower5 = new ArrayList<>();

        costToBuy5.add(new Resource(Color.BLUE));
        costToBuy5.add(new Resource(Color.BLUE));
        costToBuy5.add(new Resource(Color.BLUE));
        costToBuy5.add(new Resource(Color.BLUE));

        earnProductionPower5.add(new Resource(Color.RED));
        earnProductionPower5.add(new Resource(Color.RED));

        costProductionPower5.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(5,5,Color.GREEN,2,costToBuy5,earnProductionPower5,costProductionPower5));

        //Card 6
        ArrayList<Resource> costToBuy6 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower6 = new ArrayList<>();
        ArrayList<Resource> costProductionPower6 = new ArrayList<>();

        costToBuy6.add(new Resource(Color.BLUE));
        costToBuy6.add(new Resource(Color.BLUE));
        costToBuy6.add(new Resource(Color.BLUE));
        costToBuy6.add(new Resource(Color.PURPLE));
        costToBuy6.add(new Resource(Color.PURPLE));

        earnProductionPower6.add(new Resource(Color.GREY));
        earnProductionPower6.add(new Resource(Color.GREY));
        earnProductionPower6.add(new Resource(Color.GREY));

        costProductionPower6.add(new Resource(Color.BLUE));
        costProductionPower6.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(6,6,Color.GREEN,2,costToBuy6,earnProductionPower6,costProductionPower6));

        //Card 7
        ArrayList<Resource> costToBuy7 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower7 = new ArrayList<>();
        ArrayList<Resource> costProductionPower7 = new ArrayList<>();

        costToBuy7.add(new Resource(Color.BLUE));
        costToBuy7.add(new Resource(Color.BLUE));
        costToBuy7.add(new Resource(Color.BLUE));
        costToBuy7.add(new Resource(Color.BLUE));
        costToBuy7.add(new Resource(Color.BLUE));

        earnProductionPower7.add(new Resource(Color.GREY));
        earnProductionPower7.add(new Resource(Color.GREY));
        earnProductionPower7.add(new Resource(Color.RED));
        earnProductionPower7.add(new Resource(Color.RED));

        costProductionPower7.add(new Resource(Color.YELLOW));
        costProductionPower7.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(7,7,Color.GREEN,2,costToBuy7,earnProductionPower7,costProductionPower7));

        //Card 8
        ArrayList<Resource> costToBuy8 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower8 = new ArrayList<>();
        ArrayList<Resource> costProductionPower8 = new ArrayList<>();

        costToBuy8.add(new Resource(Color.BLUE));
        costToBuy8.add(new Resource(Color.BLUE));
        costToBuy8.add(new Resource(Color.BLUE));
        costToBuy8.add(new Resource(Color.YELLOW));
        costToBuy8.add(new Resource(Color.YELLOW));
        costToBuy8.add(new Resource(Color.YELLOW));

        earnProductionPower8.add(new Resource(Color.BLUE));
        earnProductionPower8.add(new Resource(Color.BLUE));
        earnProductionPower8.add(new Resource(Color.RED));

        costProductionPower8.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(8,8,Color.GREEN,2,costToBuy8,earnProductionPower8,costProductionPower8));

        //Card 9
        ArrayList<Resource> costToBuy9 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower9 = new ArrayList<>();
        ArrayList<Resource> costProductionPower9 = new ArrayList<>();

        costToBuy9.add(new Resource(Color.BLUE));
        costToBuy9.add(new Resource(Color.BLUE));
        costToBuy9.add(new Resource(Color.BLUE));
        costToBuy9.add(new Resource(Color.BLUE));
        costToBuy9.add(new Resource(Color.BLUE));
        costToBuy9.add(new Resource(Color.BLUE));

        earnProductionPower9.add(new Resource(Color.GREY));
        earnProductionPower9.add(new Resource(Color.GREY));
        earnProductionPower9.add(new Resource(Color.GREY));
        earnProductionPower9.add(new Resource(Color.RED));
        earnProductionPower9.add(new Resource(Color.RED));

        costProductionPower9.add(new Resource(Color.YELLOW));
        costProductionPower9.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(9,9,Color.GREEN,3,costToBuy9,earnProductionPower9,costProductionPower9));

        //Card 10
        ArrayList<Resource> costToBuy10 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower10 = new ArrayList<>();
        ArrayList<Resource> costProductionPower10 = new ArrayList<>();

        costToBuy10.add(new Resource(Color.BLUE));
        costToBuy10.add(new Resource(Color.BLUE));
        costToBuy10.add(new Resource(Color.BLUE));
        costToBuy10.add(new Resource(Color.BLUE));
        costToBuy10.add(new Resource(Color.BLUE));
        costToBuy10.add(new Resource(Color.PURPLE));
        costToBuy10.add(new Resource(Color.PURPLE));

        earnProductionPower10.add(new Resource(Color.BLUE));
        earnProductionPower10.add(new Resource(Color.BLUE));
        earnProductionPower10.add(new Resource(Color.GREY));
        earnProductionPower10.add(new Resource(Color.GREY));
        earnProductionPower10.add(new Resource(Color.RED));

        costProductionPower10.add(new Resource(Color.YELLOW));
        costProductionPower10.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(10,10,Color.GREEN,3,costToBuy10,earnProductionPower10,costProductionPower10));

        //Card 11
        ArrayList<Resource> costToBuy11 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower11 = new ArrayList<>();
        ArrayList<Resource> costProductionPower11 = new ArrayList<>();

        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));
        costToBuy11.add(new Resource(Color.BLUE));

        earnProductionPower11.add(new Resource(Color.YELLOW));
        earnProductionPower11.add(new Resource(Color.RED));
        earnProductionPower11.add(new Resource(Color.RED));
        earnProductionPower11.add(new Resource(Color.RED));

        costProductionPower11.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(11,11,Color.GREEN,3,costToBuy11,earnProductionPower11,costProductionPower11));

        //Card 12
        ArrayList<Resource> costToBuy12 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower12 = new ArrayList<>();
        ArrayList<Resource> costProductionPower12 = new ArrayList<>();

        costToBuy12.add(new Resource(Color.BLUE));
        costToBuy12.add(new Resource(Color.BLUE));
        costToBuy12.add(new Resource(Color.BLUE));
        costToBuy12.add(new Resource(Color.BLUE));
        costToBuy12.add(new Resource(Color.YELLOW));
        costToBuy12.add(new Resource(Color.YELLOW));
        costToBuy12.add(new Resource(Color.YELLOW));
        costToBuy12.add(new Resource(Color.YELLOW));

        earnProductionPower12.add(new Resource(Color.YELLOW));
        earnProductionPower12.add(new Resource(Color.YELLOW));
        earnProductionPower12.add(new Resource(Color.YELLOW));
        earnProductionPower12.add(new Resource(Color.BLUE));

        costProductionPower12.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(12,12,Color.GREEN,3,costToBuy12,earnProductionPower12,costProductionPower12));

        //Card 13
        ArrayList<Resource> costToBuy13 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower13 = new ArrayList<>();
        ArrayList<Resource> costProductionPower13 = new ArrayList<>();

        costToBuy13.add(new Resource(Color.YELLOW));
        costToBuy13.add(new Resource(Color.YELLOW));

        earnProductionPower13.add(new Resource(Color.RED));

        costProductionPower13.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(13,1,Color.BLUE,1,costToBuy13,earnProductionPower13,costProductionPower13));

        //Card 14
        ArrayList<Resource> costToBuy14 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower14 = new ArrayList<>();
        ArrayList<Resource> costProductionPower14 = new ArrayList<>();

        costToBuy14.add(new Resource(Color.YELLOW));
        costToBuy14.add(new Resource(Color.PURPLE));
        costToBuy14.add(new Resource(Color.GREY));

        earnProductionPower14.add(new Resource(Color.GREY));

        costProductionPower14.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(14,2,Color.BLUE,1,costToBuy14,earnProductionPower14,costProductionPower14));

        //Card 15
        ArrayList<Resource> costToBuy15 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower15 = new ArrayList<>();
        ArrayList<Resource> costProductionPower15 = new ArrayList<>();

        costToBuy15.add(new Resource(Color.YELLOW));
        costToBuy15.add(new Resource(Color.YELLOW));
        costToBuy15.add(new Resource(Color.YELLOW));

        earnProductionPower15.add(new Resource(Color.YELLOW));
        earnProductionPower15.add(new Resource(Color.PURPLE));
        earnProductionPower15.add(new Resource(Color.BLUE));

        costProductionPower15.add(new Resource(Color.GREY));
        costProductionPower15.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(15,3,Color.BLUE,1,costToBuy15,earnProductionPower15,costProductionPower15));

        //Card 16
        ArrayList<Resource> costToBuy16 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower16 = new ArrayList<>();
        ArrayList<Resource> costProductionPower16 = new ArrayList<>();

        costToBuy16.add(new Resource(Color.YELLOW));
        costToBuy16.add(new Resource(Color.YELLOW));
        costToBuy16.add(new Resource(Color.PURPLE));
        costToBuy16.add(new Resource(Color.PURPLE));

        earnProductionPower16.add(new Resource(Color.PURPLE));
        earnProductionPower16.add(new Resource(Color.PURPLE));
        earnProductionPower16.add(new Resource(Color.RED));

        costProductionPower16.add(new Resource(Color.BLUE));
        costProductionPower16.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(16,4,Color.BLUE,1,costToBuy16,earnProductionPower16,costProductionPower16));

        //Card 17
        ArrayList<Resource> costToBuy17 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower17 = new ArrayList<>();
        ArrayList<Resource> costProductionPower17 = new ArrayList<>();

        costToBuy17.add(new Resource(Color.YELLOW));
        costToBuy17.add(new Resource(Color.YELLOW));
        costToBuy17.add(new Resource(Color.YELLOW));
        costToBuy17.add(new Resource(Color.YELLOW));

        earnProductionPower17.add(new Resource(Color.RED));
        earnProductionPower17.add(new Resource(Color.RED));

        costProductionPower17.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(17,5,Color.BLUE,2,costToBuy17,earnProductionPower17,costProductionPower17));

        //Card 18
        ArrayList<Resource> costToBuy18 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower18 = new ArrayList<>();
        ArrayList<Resource> costProductionPower18 = new ArrayList<>();

        costToBuy18.add(new Resource(Color.YELLOW));
        costToBuy18.add(new Resource(Color.YELLOW));
        costToBuy18.add(new Resource(Color.GREY));

        earnProductionPower18.add(new Resource(Color.PURPLE));
        earnProductionPower18.add(new Resource(Color.PURPLE));
        earnProductionPower18.add(new Resource(Color.PURPLE));

        costProductionPower18.add(new Resource(Color.YELLOW));
        costProductionPower18.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(18,6,Color.BLUE,2,costToBuy18,earnProductionPower18,costProductionPower18));

        //Card 19
        ArrayList<Resource> costToBuy19 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower19 = new ArrayList<>();
        ArrayList<Resource> costProductionPower19 = new ArrayList<>();

        costToBuy19.add(new Resource(Color.YELLOW));
        costToBuy19.add(new Resource(Color.YELLOW));
        costToBuy19.add(new Resource(Color.YELLOW));
        costToBuy19.add(new Resource(Color.YELLOW));
        costToBuy19.add(new Resource(Color.YELLOW));

        earnProductionPower19.add(new Resource(Color.BLUE));
        earnProductionPower19.add(new Resource(Color.BLUE));
        earnProductionPower19.add(new Resource(Color.RED));
        earnProductionPower19.add(new Resource(Color.RED));

        costProductionPower19.add(new Resource(Color.PURPLE));
        costProductionPower19.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(19,7,Color.BLUE,2,costToBuy19,earnProductionPower19,costProductionPower19));

        //Card 20
        ArrayList<Resource> costToBuy20 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower20 = new ArrayList<>();
        ArrayList<Resource> costProductionPower20 = new ArrayList<>();

        costToBuy20.add(new Resource(Color.YELLOW));
        costToBuy20.add(new Resource(Color.YELLOW));
        costToBuy20.add(new Resource(Color.YELLOW));
        costToBuy20.add(new Resource(Color.GREY));
        costToBuy20.add(new Resource(Color.GREY));
        costToBuy20.add(new Resource(Color.GREY));

        earnProductionPower20.add(new Resource(Color.GREY));
        earnProductionPower20.add(new Resource(Color.GREY));
        earnProductionPower20.add(new Resource(Color.RED));

        costProductionPower20.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(20,8,Color.BLUE,2,costToBuy20,earnProductionPower20,costProductionPower20));

        //Card 21
        ArrayList<Resource> costToBuy21 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower21 = new ArrayList<>();
        ArrayList<Resource> costProductionPower21 = new ArrayList<>();

        costToBuy21.add(new Resource(Color.YELLOW));
        costToBuy21.add(new Resource(Color.YELLOW));
        costToBuy21.add(new Resource(Color.YELLOW));
        costToBuy21.add(new Resource(Color.YELLOW));
        costToBuy21.add(new Resource(Color.YELLOW));
        costToBuy21.add(new Resource(Color.YELLOW));

        earnProductionPower21.add(new Resource(Color.BLUE));
        earnProductionPower21.add(new Resource(Color.BLUE));
        earnProductionPower21.add(new Resource(Color.BLUE));
        earnProductionPower21.add(new Resource(Color.RED));
        earnProductionPower21.add(new Resource(Color.RED));

        costProductionPower21.add(new Resource(Color.PURPLE));
        costProductionPower21.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(21,9,Color.BLUE,3,costToBuy21,earnProductionPower21,costProductionPower21));

        //Card 22
        ArrayList<Resource> costToBuy22 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower22 = new ArrayList<>();
        ArrayList<Resource> costProductionPower22 = new ArrayList<>();

        costToBuy22.add(new Resource(Color.YELLOW));
        costToBuy22.add(new Resource(Color.YELLOW));
        costToBuy22.add(new Resource(Color.YELLOW));
        costToBuy22.add(new Resource(Color.YELLOW));
        costToBuy22.add(new Resource(Color.YELLOW));
        costToBuy22.add(new Resource(Color.GREY));
        costToBuy22.add(new Resource(Color.GREY));

        earnProductionPower22.add(new Resource(Color.PURPLE));
        earnProductionPower22.add(new Resource(Color.PURPLE));
        earnProductionPower22.add(new Resource(Color.GREY));
        earnProductionPower22.add(new Resource(Color.GREY));
        earnProductionPower22.add(new Resource(Color.RED));

        costProductionPower22.add(new Resource(Color.YELLOW));
        costProductionPower22.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(22,10,Color.BLUE,3,costToBuy22,earnProductionPower22,costProductionPower22));

        //Card 23
        ArrayList<Resource> costToBuy23 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower23 = new ArrayList<>();
        ArrayList<Resource> costProductionPower23 = new ArrayList<>();

        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));
        costToBuy23.add(new Resource(Color.YELLOW));

        earnProductionPower23.add(new Resource(Color.BLUE));
        earnProductionPower23.add(new Resource(Color.RED));
        earnProductionPower23.add(new Resource(Color.RED));
        earnProductionPower23.add(new Resource(Color.RED));

        costProductionPower23.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(23,11,Color.BLUE,3,costToBuy23,earnProductionPower23,costProductionPower23));

        //Card 24
        ArrayList<Resource> costToBuy24 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower24 = new ArrayList<>();
        ArrayList<Resource> costProductionPower24 = new ArrayList<>();

        costToBuy24.add(new Resource(Color.YELLOW));
        costToBuy24.add(new Resource(Color.YELLOW));
        costToBuy24.add(new Resource(Color.YELLOW));
        costToBuy24.add(new Resource(Color.YELLOW));
        costToBuy24.add(new Resource(Color.GREY));
        costToBuy24.add(new Resource(Color.GREY));
        costToBuy24.add(new Resource(Color.GREY));
        costToBuy24.add(new Resource(Color.GREY));

        earnProductionPower24.add(new Resource(Color.YELLOW));
        earnProductionPower24.add(new Resource(Color.BLUE));
        earnProductionPower24.add(new Resource(Color.BLUE));
        earnProductionPower24.add(new Resource(Color.BLUE));

        costProductionPower24.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(24,12,Color.BLUE,3,costToBuy24,earnProductionPower24,costProductionPower24));

        //Card 25
        ArrayList<Resource> costToBuy25 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower25 = new ArrayList<>();
        ArrayList<Resource> costProductionPower25 = new ArrayList<>();

        costToBuy25.add(new Resource(Color.GREY));
        costToBuy25.add(new Resource(Color.GREY));

        earnProductionPower25.add(new Resource(Color.RED));

        costProductionPower25.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(25,1,Color.YELLOW,1,costToBuy25,earnProductionPower25,costProductionPower25));

        //Card 26
        ArrayList<Resource> costToBuy26 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower26 = new ArrayList<>();
        ArrayList<Resource> costProductionPower26 = new ArrayList<>();

        costToBuy26.add(new Resource(Color.BLUE));
        costToBuy26.add(new Resource(Color.YELLOW));
        costToBuy26.add(new Resource(Color.GREY));

        earnProductionPower26.add(new Resource(Color.YELLOW));

        costProductionPower26.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(26,2,Color.YELLOW,1,costToBuy26,earnProductionPower26,costProductionPower26));

        //Card 27
        ArrayList<Resource> costToBuy27 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower27 = new ArrayList<>();
        ArrayList<Resource> costProductionPower27 = new ArrayList<>();

        costToBuy27.add(new Resource(Color.GREY));
        costToBuy27.add(new Resource(Color.GREY));
        costToBuy27.add(new Resource(Color.GREY));

        earnProductionPower27.add(new Resource(Color.YELLOW));
        earnProductionPower27.add(new Resource(Color.PURPLE));
        earnProductionPower27.add(new Resource(Color.GREY));

        costProductionPower27.add(new Resource(Color.BLUE));
        costProductionPower27.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(27,3,Color.YELLOW,1,costToBuy27,earnProductionPower27,costProductionPower27));

        //Card 28
        ArrayList<Resource> costToBuy28 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower28 = new ArrayList<>();
        ArrayList<Resource> costProductionPower28 = new ArrayList<>();

        costToBuy28.add(new Resource(Color.GREY));
        costToBuy28.add(new Resource(Color.GREY));
        costToBuy28.add(new Resource(Color.BLUE));
        costToBuy28.add(new Resource(Color.BLUE));

        earnProductionPower28.add(new Resource(Color.BLUE));
        earnProductionPower28.add(new Resource(Color.BLUE));
        earnProductionPower28.add(new Resource(Color.RED));

        costProductionPower28.add(new Resource(Color.YELLOW));
        costProductionPower28.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(28,4,Color.YELLOW,1,costToBuy28,earnProductionPower28,costProductionPower28));

        //Card 29
        ArrayList<Resource> costToBuy29 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower29 = new ArrayList<>();
        ArrayList<Resource> costProductionPower29 = new ArrayList<>();

        costToBuy29.add(new Resource(Color.GREY));
        costToBuy29.add(new Resource(Color.GREY));
        costToBuy29.add(new Resource(Color.GREY));
        costToBuy29.add(new Resource(Color.GREY));

        earnProductionPower29.add(new Resource(Color.RED));
        earnProductionPower29.add(new Resource(Color.RED));

        costProductionPower29.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(29,5,Color.YELLOW,2,costToBuy29,earnProductionPower29,costProductionPower29));

        //Card 30
        ArrayList<Resource> costToBuy30 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower30 = new ArrayList<>();
        ArrayList<Resource> costProductionPower30 = new ArrayList<>();

        costToBuy30.add(new Resource(Color.GREY));
        costToBuy30.add(new Resource(Color.GREY));
        costToBuy30.add(new Resource(Color.GREY));
        costToBuy30.add(new Resource(Color.BLUE));
        costToBuy30.add(new Resource(Color.BLUE));

        earnProductionPower30.add(new Resource(Color.YELLOW));
        earnProductionPower30.add(new Resource(Color.YELLOW));
        earnProductionPower30.add(new Resource(Color.YELLOW));

        costProductionPower30.add(new Resource(Color.GREY));
        costProductionPower30.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(30,6,Color.YELLOW,2,costToBuy30,earnProductionPower30,costProductionPower30));

        //Card 31
        ArrayList<Resource> costToBuy31 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower31 = new ArrayList<>();
        ArrayList<Resource> costProductionPower31 = new ArrayList<>();

        costToBuy31.add(new Resource(Color.GREY));
        costToBuy31.add(new Resource(Color.GREY));
        costToBuy31.add(new Resource(Color.GREY));
        costToBuy31.add(new Resource(Color.GREY));
        costToBuy31.add(new Resource(Color.GREY));

        earnProductionPower31.add(new Resource(Color.PURPLE));
        earnProductionPower31.add(new Resource(Color.PURPLE));
        earnProductionPower31.add(new Resource(Color.RED));
        earnProductionPower31.add(new Resource(Color.RED));

        costProductionPower31.add(new Resource(Color.BLUE));
        costProductionPower31.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(31,7,Color.YELLOW,2,costToBuy31,earnProductionPower31,costProductionPower31));

        //Card 32
        ArrayList<Resource> costToBuy32 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower32 = new ArrayList<>();
        ArrayList<Resource> costProductionPower32 = new ArrayList<>();

        costToBuy32.add(new Resource(Color.GREY));
        costToBuy32.add(new Resource(Color.GREY));
        costToBuy32.add(new Resource(Color.GREY));
        costToBuy32.add(new Resource(Color.PURPLE));
        costToBuy32.add(new Resource(Color.PURPLE));
        costToBuy32.add(new Resource(Color.PURPLE));

        earnProductionPower32.add(new Resource(Color.YELLOW));
        earnProductionPower32.add(new Resource(Color.YELLOW));
        earnProductionPower32.add(new Resource(Color.RED));

        costProductionPower32.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(32,8,Color.YELLOW,2,costToBuy32,earnProductionPower32,costProductionPower32));

        //Card 33
        ArrayList<Resource> costToBuy33 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower33 = new ArrayList<>();
        ArrayList<Resource> costProductionPower33 = new ArrayList<>();

        costToBuy33.add(new Resource(Color.GREY));
        costToBuy33.add(new Resource(Color.GREY));
        costToBuy33.add(new Resource(Color.GREY));
        costToBuy33.add(new Resource(Color.GREY));
        costToBuy33.add(new Resource(Color.GREY));
        costToBuy33.add(new Resource(Color.GREY));

        earnProductionPower33.add(new Resource(Color.PURPLE));
        earnProductionPower33.add(new Resource(Color.PURPLE));
        earnProductionPower33.add(new Resource(Color.PURPLE));
        earnProductionPower33.add(new Resource(Color.RED));
        earnProductionPower33.add(new Resource(Color.RED));

        costProductionPower33.add(new Resource(Color.BLUE));
        costProductionPower33.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(33,9,Color.YELLOW,3,costToBuy33,earnProductionPower33,costProductionPower33));

        //Card 34
        ArrayList<Resource> costToBuy34 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower34 = new ArrayList<>();
        ArrayList<Resource> costProductionPower34 = new ArrayList<>();

        costToBuy34.add(new Resource(Color.GREY));
        costToBuy34.add(new Resource(Color.GREY));
        costToBuy34.add(new Resource(Color.GREY));
        costToBuy34.add(new Resource(Color.GREY));
        costToBuy34.add(new Resource(Color.GREY));
        costToBuy34.add(new Resource(Color.PURPLE));
        costToBuy34.add(new Resource(Color.PURPLE));

        earnProductionPower34.add(new Resource(Color.YELLOW));
        earnProductionPower34.add(new Resource(Color.YELLOW));
        earnProductionPower34.add(new Resource(Color.BLUE));
        earnProductionPower34.add(new Resource(Color.BLUE));
        earnProductionPower34.add(new Resource(Color.RED));

        costProductionPower34.add(new Resource(Color.GREY));
        costProductionPower34.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(34,10,Color.YELLOW,3,costToBuy34,earnProductionPower34,costProductionPower34));

        //Card 35
        ArrayList<Resource> costToBuy35 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower35 = new ArrayList<>();
        ArrayList<Resource> costProductionPower35 = new ArrayList<>();

        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));
        costToBuy35.add(new Resource(Color.GREY));

        earnProductionPower35.add(new Resource(Color.PURPLE));
        earnProductionPower35.add(new Resource(Color.RED));
        earnProductionPower35.add(new Resource(Color.RED));
        earnProductionPower35.add(new Resource(Color.RED));

        costProductionPower35.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(35,11,Color.YELLOW,3,costToBuy35,earnProductionPower35,costProductionPower35));

        //Card 36
        ArrayList<Resource> costToBuy36 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower36 = new ArrayList<>();
        ArrayList<Resource> costProductionPower36 = new ArrayList<>();

        costToBuy36.add(new Resource(Color.GREY));
        costToBuy36.add(new Resource(Color.GREY));
        costToBuy36.add(new Resource(Color.GREY));
        costToBuy36.add(new Resource(Color.GREY));
        costToBuy36.add(new Resource(Color.PURPLE));
        costToBuy36.add(new Resource(Color.PURPLE));
        costToBuy36.add(new Resource(Color.PURPLE));
        costToBuy36.add(new Resource(Color.PURPLE));

        earnProductionPower36.add(new Resource(Color.GREY));
        earnProductionPower36.add(new Resource(Color.PURPLE));
        earnProductionPower36.add(new Resource(Color.PURPLE));
        earnProductionPower36.add(new Resource(Color.PURPLE));

        costProductionPower36.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(36,12,Color.YELLOW,3,costToBuy36,earnProductionPower36,costProductionPower36));

        //Card 37
        ArrayList<Resource> costToBuy37 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower37 = new ArrayList<>();
        ArrayList<Resource> costProductionPower37 = new ArrayList<>();

        costToBuy37.add(new Resource(Color.PURPLE));
        costToBuy37.add(new Resource(Color.PURPLE));

        earnProductionPower37.add(new Resource(Color.RED));

        costProductionPower37.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(37,1,Color.PURPLE,1,costToBuy37,earnProductionPower37,costProductionPower37));

        //Card 38
        ArrayList<Resource> costToBuy38 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower38 = new ArrayList<>();
        ArrayList<Resource> costProductionPower38 = new ArrayList<>();

        costToBuy38.add(new Resource(Color.BLUE));
        costToBuy38.add(new Resource(Color.YELLOW));
        costToBuy38.add(new Resource(Color.PURPLE));

        earnProductionPower38.add(new Resource(Color.BLUE));

        costProductionPower38.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(38,2,Color.PURPLE,1,costToBuy38,earnProductionPower38,costProductionPower38));

        //Card 39
        ArrayList<Resource> costToBuy39 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower39 = new ArrayList<>();
        ArrayList<Resource> costProductionPower39 = new ArrayList<>();

        costToBuy39.add(new Resource(Color.PURPLE));
        costToBuy39.add(new Resource(Color.PURPLE));
        costToBuy39.add(new Resource(Color.PURPLE));

        earnProductionPower39.add(new Resource(Color.PURPLE));
        earnProductionPower39.add(new Resource(Color.BLUE));
        earnProductionPower39.add(new Resource(Color.GREY));

        costProductionPower39.add(new Resource(Color.YELLOW));
        costProductionPower39.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(39,3,Color.PURPLE,1,costToBuy39,earnProductionPower39,costProductionPower39));

        //Card 40
        ArrayList<Resource> costToBuy40 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower40 = new ArrayList<>();
        ArrayList<Resource> costProductionPower40 = new ArrayList<>();

        costToBuy40.add(new Resource(Color.PURPLE));
        costToBuy40.add(new Resource(Color.PURPLE));
        costToBuy40.add(new Resource(Color.GREY));
        costToBuy40.add(new Resource(Color.GREY));

        earnProductionPower40.add(new Resource(Color.GREY));
        earnProductionPower40.add(new Resource(Color.GREY));
        earnProductionPower40.add(new Resource(Color.RED));

        costProductionPower40.add(new Resource(Color.YELLOW));
        costProductionPower40.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(40,4,Color.PURPLE,1,costToBuy40,earnProductionPower40,costProductionPower40));

        //Card 41
        ArrayList<Resource> costToBuy41 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower41 = new ArrayList<>();
        ArrayList<Resource> costProductionPower41 = new ArrayList<>();

        costToBuy41.add(new Resource(Color.PURPLE));
        costToBuy41.add(new Resource(Color.PURPLE));
        costToBuy41.add(new Resource(Color.PURPLE));
        costToBuy41.add(new Resource(Color.PURPLE));

        earnProductionPower41.add(new Resource(Color.RED));
        earnProductionPower41.add(new Resource(Color.RED));

        costProductionPower41.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(41,5,Color.PURPLE,2,costToBuy41,earnProductionPower41,costProductionPower41));

        //Card 42
        ArrayList<Resource> costToBuy42 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower42 = new ArrayList<>();
        ArrayList<Resource> costProductionPower42 = new ArrayList<>();

        costToBuy42.add(new Resource(Color.PURPLE));
        costToBuy42.add(new Resource(Color.PURPLE));
        costToBuy42.add(new Resource(Color.PURPLE));
        costToBuy42.add(new Resource(Color.YELLOW));
        costToBuy42.add(new Resource(Color.YELLOW));

        earnProductionPower42.add(new Resource(Color.BLUE));
        earnProductionPower42.add(new Resource(Color.BLUE));
        earnProductionPower42.add(new Resource(Color.BLUE));

        costProductionPower42.add(new Resource(Color.YELLOW));
        costProductionPower42.add(new Resource(Color.PURPLE));

        cards.add(new DevelopmentCard(42,6,Color.PURPLE,2,costToBuy42,earnProductionPower42,costProductionPower42));

        //Card 43
        ArrayList<Resource> costToBuy43 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower43 = new ArrayList<>();
        ArrayList<Resource> costProductionPower43 = new ArrayList<>();

        costToBuy43.add(new Resource(Color.PURPLE));
        costToBuy43.add(new Resource(Color.PURPLE));
        costToBuy43.add(new Resource(Color.PURPLE));
        costToBuy43.add(new Resource(Color.PURPLE));
        costToBuy43.add(new Resource(Color.PURPLE));

        earnProductionPower43.add(new Resource(Color.YELLOW));
        earnProductionPower43.add(new Resource(Color.YELLOW));
        earnProductionPower43.add(new Resource(Color.RED));
        earnProductionPower43.add(new Resource(Color.RED));

        costProductionPower43.add(new Resource(Color.GREY));
        costProductionPower43.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(43,7,Color.PURPLE,2,costToBuy43,earnProductionPower43,costProductionPower43));

        //Card 44
        ArrayList<Resource> costToBuy44 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower44 = new ArrayList<>();
        ArrayList<Resource> costProductionPower44 = new ArrayList<>();

        costToBuy44.add(new Resource(Color.PURPLE));
        costToBuy44.add(new Resource(Color.PURPLE));
        costToBuy44.add(new Resource(Color.PURPLE));
        costToBuy44.add(new Resource(Color.BLUE));
        costToBuy44.add(new Resource(Color.BLUE));
        costToBuy44.add(new Resource(Color.BLUE));

        earnProductionPower44.add(new Resource(Color.PURPLE));
        earnProductionPower44.add(new Resource(Color.PURPLE));
        earnProductionPower44.add(new Resource(Color.RED));

        costProductionPower44.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(44,8,Color.PURPLE,2,costToBuy44,earnProductionPower44,costProductionPower44));

        //Card 45
        ArrayList<Resource> costToBuy45 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower45 = new ArrayList<>();
        ArrayList<Resource> costProductionPower45 = new ArrayList<>();

        costToBuy45.add(new Resource(Color.PURPLE));
        costToBuy45.add(new Resource(Color.PURPLE));
        costToBuy45.add(new Resource(Color.PURPLE));
        costToBuy45.add(new Resource(Color.PURPLE));
        costToBuy45.add(new Resource(Color.PURPLE));
        costToBuy45.add(new Resource(Color.PURPLE));

        earnProductionPower45.add(new Resource(Color.YELLOW));
        earnProductionPower45.add(new Resource(Color.YELLOW));
        earnProductionPower45.add(new Resource(Color.YELLOW));
        earnProductionPower45.add(new Resource(Color.RED));
        earnProductionPower45.add(new Resource(Color.RED));

        costProductionPower45.add(new Resource(Color.GREY));
        costProductionPower45.add(new Resource(Color.GREY));

        cards.add(new DevelopmentCard(45,9,Color.PURPLE,3,costToBuy45,earnProductionPower45,costProductionPower45));

        //Card 46
        ArrayList<Resource> costToBuy46 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower46 = new ArrayList<>();
        ArrayList<Resource> costProductionPower46 = new ArrayList<>();


        costToBuy46.add(new Resource(Color.PURPLE));
        costToBuy46.add(new Resource(Color.PURPLE));
        costToBuy46.add(new Resource(Color.PURPLE));
        costToBuy46.add(new Resource(Color.PURPLE));
        costToBuy46.add(new Resource(Color.PURPLE));
        costToBuy46.add(new Resource(Color.YELLOW));
        costToBuy46.add(new Resource(Color.YELLOW));

        earnProductionPower46.add(new Resource(Color.YELLOW));
        earnProductionPower46.add(new Resource(Color.YELLOW));
        earnProductionPower46.add(new Resource(Color.PURPLE));
        earnProductionPower46.add(new Resource(Color.PURPLE));
        earnProductionPower46.add(new Resource(Color.RED));

        costProductionPower46.add(new Resource(Color.GREY));
        costProductionPower46.add(new Resource(Color.BLUE));

        cards.add(new DevelopmentCard(46,10,Color.PURPLE,3,costToBuy46,earnProductionPower46,costProductionPower46));

        //Card 47
        ArrayList<Resource> costToBuy47 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower47 = new ArrayList<>();
        ArrayList<Resource> costProductionPower47 = new ArrayList<>();

        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));
        costToBuy47.add(new Resource(Color.PURPLE));

        earnProductionPower47.add(new Resource(Color.GREY));
        earnProductionPower47.add(new Resource(Color.RED));
        earnProductionPower47.add(new Resource(Color.RED));
        earnProductionPower47.add(new Resource(Color.RED));

        costProductionPower47.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(47,11,Color.PURPLE,3, costToBuy47,earnProductionPower47,costProductionPower47));

        //Card 48
        ArrayList<Resource> costToBuy48 = new ArrayList<>();
        ArrayList<Resource> earnProductionPower48 = new ArrayList<>();
        ArrayList<Resource> costProductionPower48 = new ArrayList<>();

        costToBuy48.add(new Resource(Color.PURPLE));
        costToBuy48.add(new Resource(Color.PURPLE));
        costToBuy48.add(new Resource(Color.PURPLE));
        costToBuy48.add(new Resource(Color.PURPLE));
        costToBuy48.add(new Resource(Color.BLUE));
        costToBuy48.add(new Resource(Color.BLUE));
        costToBuy48.add(new Resource(Color.BLUE));
        costToBuy48.add(new Resource(Color.BLUE));

        earnProductionPower48.add(new Resource(Color.GREY));
        earnProductionPower48.add(new Resource(Color.GREY));
        earnProductionPower48.add(new Resource(Color.GREY));
        earnProductionPower48.add(new Resource(Color.PURPLE));

        costProductionPower48.add(new Resource(Color.YELLOW));

        cards.add(new DevelopmentCard(48,12,Color.PURPLE,3,costToBuy48,earnProductionPower48,costProductionPower48));

        //insert in the expected
        expected=new DevelopmentCardDeck[3][4];
        int card=0;
        for(int column=0;column<4;column++){
            for(int row=2;row>=0;row--){
                ArrayList<DevelopmentCard> deck = new ArrayList<>();
                for(int i=card+3;i>=card;i--) {
                    deck.add(cards.get(i));
                    expected[row][column]=new DevelopmentCardDeck(deck);
                }
                card+=4;
            }
        }

    }

    public void tearDown() throws Exception {
    }

    public void testCreateTable() throws FileNotFoundException {
        DevelopmentCardDeck[][] table = new DevelopmentCardFactory().createTable();
        for(int column=0;column<4;column++){
            for(int row=2;row>=0;row--){
                System.out.println("Row: "+row+", Column: "+column);
                ArrayList<DevelopmentCard> deck = table[row][column].getDevelopDeck();
                ArrayList<DevelopmentCard> expectedDeck = expected[row][column].getDevelopDeck();
                //Check is not empty
                assertFalse(deck.isEmpty());
                //Check is not empty for both
                assertEquals(expectedDeck.isEmpty(),deck.isEmpty());
                //Check if there are 4 cards
                assertSame(expectedDeck.size(),deck.size());
                //Check if all cards in the deck have the same color and level
                int level=deck.get(0).getlevel();
                Color color = deck.get(0).getColor();
                for(DevelopmentCard card: deck){
                    assertSame(level,card.getlevel());
                    assertEquals(color,card.getColor());
                }
                for(int i=0;i<4;i++){
                    //Based on the expected
                    System.out.println("Card n° "+ (i+1));
                    System.out.println("ID: "+deck.get(i).getId());
                    //Check id
                    assertSame(expectedDeck.get(i).getId(),deck.get(i).getId());
                    //Check the color
                    assertEquals(expectedDeck.get(i).getColor(),deck.get(i).getColor());
                    //Check the level
                    assertSame(expectedDeck.get(i).getlevel(),deck.get(i).getlevel());
                    //Check the victory points
                    assertSame(expectedDeck.get(i).getVictoryPoints(),deck.get(i).getVictoryPoints());
                    //Check if has the same size of costToBuy
                    assertSame(expectedDeck.get(i).getCost().size(),deck.get(i).getCost().size());

                    //Check the single resources of the costToBuy
                    for(int j=0;j<deck.get(i).getCost().size();j++){
                        System.out.println("CostToBuy: "+ (j+1));
                        assertEquals(expectedDeck.get(i).getCost().get(j).getColor(),deck.get(i).getCost().get(j).getColor());
                        assertEquals(expectedDeck.get(i).getCost().get(j).getType(),deck.get(i).getCost().get(j).getType());
                    }
                    //Check if has the same size of earnPP
                    assertSame(expectedDeck.get(i).showProceedsProductionPower().size(),deck.get(i).showProceedsProductionPower().size());
                    //Check the single resources of the earnPP
                    for(int j=0;j<deck.get(i).showProceedsProductionPower().size();j++){
                        System.out.println("EarnPP: "+ (j+1));
                        assertEquals(expectedDeck.get(i).showProceedsProductionPower().get(j).getColor(),deck.get(i).showProceedsProductionPower().get(j).getColor());
                        assertEquals(expectedDeck.get(i).showProceedsProductionPower().get(j).getType(),deck.get(i).showProceedsProductionPower().get(j).getType());
                    }
                    //Check if has the same size of costPP
                    assertSame(expectedDeck.get(i).showCostProductionPower().size(),deck.get(i).showCostProductionPower().size());
                    //Check the single resources of the costPP
                    for(int j=0;j<deck.get(i).showCostProductionPower().size();j++){
                        System.out.println("CostPP: "+ (j+1));
                        assertEquals(expectedDeck.get(i).showCostProductionPower().get(j).getColor(),deck.get(i).showCostProductionPower().get(j).getColor());
                        assertEquals(expectedDeck.get(i).showCostProductionPower().get(j).getType(),deck.get(i).showCostProductionPower().get(j).getType());
                    }
                }
            }
        }
        //Verify all the cards of a column have the same color
        for(int column=0;column<4;column++){
            Color color = table[0][column].getDevelopDeck().get(0).getColor();
            for(int row=0;row<3;row++){
                for(DevelopmentCard card:table[row][column].getDevelopDeck()){
                    assertEquals(color,card.getColor());
                }
            }
        }
        //Verify all the cards of a row have the same level
        for(int row=0;row<3;row++){
            int level = table[row][0].getDevelopDeck().get(0).getlevel();
            for(int column=0;column<4;column++){
                for(DevelopmentCard card:table[row][column].getDevelopDeck()){
                    assertSame(level,card.getlevel());
                }
            }
        }
    }
}