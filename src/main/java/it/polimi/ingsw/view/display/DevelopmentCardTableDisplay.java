package it.polimi.ingsw.view.display;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/**
 * this class show to the player the development card table, in particular every last card of a Development Card Deck
 * available in the table
 */

public class DevelopmentCardTableDisplay {

    public static void displayCard1() {
        

        /*System.out.println("" +
                " ______________ \n" +
                "|   level:1    |\n" +
                "|   cost:2     |\n" +
                "|              |\n" +
                "|              |\n" +
                "|    pay:1     |\n" +
                "|    earn:1    |\n" +
                "|   vPoints:1  |\n" +
                "|______________|");*/


    }
    public static void main(String[] args){

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

        DevelopmentCard card1 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card2 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card3 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card4 = new DevelopmentCard(4,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        DevelopmentCardDeck smallDeck1 = new DevelopmentCardDeck(list);

        DevelopmentCard card91 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card92 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card93 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card94 = new DevelopmentCard(5,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        list12.add(card91);
        list12.add(card92);
        list12.add(card93);
        list12.add(card94);
        DevelopmentCardDeck smallDeck12 = new DevelopmentCardDeck(list12);

        DevelopmentCard card81 = new DevelopmentCard(3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card82 = new DevelopmentCard(2,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card83 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card84 = new DevelopmentCard(6,Color.GREEN,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list13 = new ArrayList<>();
        list13.add(card91);
        list13.add(card92);
        list13.add(card93);
        list13.add(card94);
        DevelopmentCardDeck smallDeck13 = new DevelopmentCardDeck(list13);



        DevelopmentCard card5 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card6 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card7 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card8 = new DevelopmentCard(7,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card55 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card66 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card77 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card88 = new DevelopmentCard(8,Color.BLUE,1,array,proceeds,cost);

        DevelopmentCard card555 = new DevelopmentCard(3,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card666 = new DevelopmentCard(2,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card777 = new DevelopmentCard(6,Color.BLUE,1,array,proceeds,cost);
        DevelopmentCard card888 = new DevelopmentCard(9,Color.BLUE,1,array,proceeds,cost);

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

        DevelopmentCard card9 = new DevelopmentCard(3,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card10 = new DevelopmentCard(2,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card11 = new DevelopmentCard(6,Color.YELLOW,1,array,proceeds,cost);
        DevelopmentCard card12 = new DevelopmentCard(10,Color.YELLOW,1,array,proceeds,cost);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        list2.add(card9);
        list2.add(card10);
        list2.add(card11);
        list2.add(card12);
        DevelopmentCardDeck smallDeck3 = new DevelopmentCardDeck(list2);

        DevelopmentCard card13 = new DevelopmentCard(3,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card14 = new DevelopmentCard(2,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card15 = new DevelopmentCard(6,Color.PURPLE,1,array,proceeds,cost);
        DevelopmentCard card16 = new DevelopmentCard(11,Color.PURPLE,1,array,proceeds,cost);

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


        DevelopmentCardTable developmentCardTable = DevelopmentCardTable.getInstance(squareCards);

        System.out.println(AnsiColors.RED_BOLD+"HERE IS THE DEVELOPMENT CARD TABLE"+AnsiColors.RESET);
        String row1 = "";
        String row2 = "";
        String row3 = "";
        //for(int i = 2; i >= 0; i--){

            for(int j = 0; j < 4; j++){

                row3+=developmentCardTable.getTable()[2][j].takeCard().toString();
                row2+=developmentCardTable.getTable()[1][j].takeCard().toString();
                row1+=developmentCardTable.getTable()[0][j].takeCard().toString();
                //System.out.print(developmentCardTable.getTable()[i][j].takeCard().toString());
            }
            System.out.println(row1);
            System.out.println(row2);
            System.out.println(row3);
            System.out.print("\n");
        //}
    }

}
