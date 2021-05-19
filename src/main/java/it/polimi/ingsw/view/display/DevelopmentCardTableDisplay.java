package it.polimi.ingsw.view.display;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.utility.AnsiColors;
import it.polimi.ingsw.utility.TableCardCopy;

import java.util.ArrayList;

/**
 * this class show to the player the development card table, in particular every last card of a Development Card Deck
 * available in the table using data contained in
 */

public class DevelopmentCardTableDisplay {

    private DevelopmentCardTable tableCard;
    private boolean[][] getAvailable;

    public DevelopmentCardTableDisplay(DevelopmentCardTable developmentCardTable, boolean[][] getAvailable) {

        this.tableCard = developmentCardTable;
        this.getAvailable = getAvailable;
    }

    public void displayCardTable() {

        //public static void main(String[] args) {

       /* DevelopmentCardDeck[][] squareCards = new DevelopmentCardDeck[3][4];

        //create the hash map for the cost of a Card
        ArrayList<Resource> array = new ArrayList<>();
        array.add(0, new Resource(Color.BLUE));
        array.add(1, new Resource(Color.YELLOW));
        array.add(2, new Resource(Color.YELLOW));
        array.add(3, new Resource(Color.YELLOW));
        array.add(4, new Resource(Color.YELLOW));
        array.add(5, new Resource(Color.YELLOW));
        array.add(6, new Resource(Color.YELLOW));
        array.add(7, new Resource(Color.YELLOW));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0, new Resource(Color.GREY));
        proceeds.add(1, new Resource(Color.GREY));
        proceeds.add(2, new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0, new Resource(Color.BLUE));
        cost.add(1, new Resource(Color.BLUE));
        cost.add(2, new Resource(Color.BLUE));
        cost.add(3, new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(3,1, Color.GREEN, 1, array, proceeds, cost);
        DevelopmentCard card2 = new DevelopmentCard(2,2, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card3 = new DevelopmentCard(6,3, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card4 = new DevelopmentCard(3,1, Color.GREEN, 4, array, proceeds, cost);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        DevelopmentCardDeck smallDeck1 = new DevelopmentCardDeck(list);

        DevelopmentCard card91 = new DevelopmentCard(3, 2,Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card92 = new DevelopmentCard(2,1, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card93 = new DevelopmentCard(6,4, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card94 = new DevelopmentCard(3,1, Color.GREEN, 2, array, proceeds, cost);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        list12.add(card91);
        list12.add(card92);
        list12.add(card93);
        list12.add(card94);
        DevelopmentCardDeck smallDeck12 = new DevelopmentCardDeck(list12);

        DevelopmentCard card81 = new DevelopmentCard(3,2, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card82 = new DevelopmentCard(2,2, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card83 = new DevelopmentCard(6,2, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card84 = new DevelopmentCard(3,2, Color.GREEN, 3, array, proceeds, cost);

        ArrayList<DevelopmentCard> list13 = new ArrayList<>();
        list13.add(card81);
        list13.add(card82);
        list13.add(card83);
        list13.add(card84);
        DevelopmentCardDeck smallDeck13 = new DevelopmentCardDeck(list13);


        DevelopmentCard card5 = new DevelopmentCard(3,2, Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card6 = new DevelopmentCard(2,2, Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card7 = new DevelopmentCard(6, 2,Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card8 = new DevelopmentCard(3,2, Color.BLUE, 1, array, proceeds, cost);

        DevelopmentCard card55 = new DevelopmentCard(3,2,Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card66 = new DevelopmentCard(2,2, Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card77 = new DevelopmentCard(6,2, Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card88 = new DevelopmentCard(3,2, Color.BLUE, 2, array, proceeds, cost);

        DevelopmentCard card555 = new DevelopmentCard(3,2, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card666 = new DevelopmentCard(2,2, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card777 = new DevelopmentCard(6,2, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card888 = new DevelopmentCard(3,1, Color.BLUE, 3, array, proceeds, cost);

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

        DevelopmentCard card9 = new DevelopmentCard(3, 1,Color.YELLOW, 1, array, proceeds, cost);
        DevelopmentCard card10 = new DevelopmentCard(2, 1,Color.YELLOW, 2, array, proceeds, cost);
        DevelopmentCard card11 = new DevelopmentCard(6, 1,Color.YELLOW, 3, array, proceeds, cost);
        DevelopmentCard card12 = new DevelopmentCard(3, 1,Color.YELLOW, 4, array, proceeds, cost);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        list2.add(card9);
        list2.add(card10);
        list2.add(card11);
        list2.add(card12);
        DevelopmentCardDeck smallDeck3 = new DevelopmentCardDeck(list2);

        DevelopmentCard card13 = new DevelopmentCard(3,2, Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card14 = new DevelopmentCard(2,2, Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card15 = new DevelopmentCard(6, 2,Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card16 = new DevelopmentCard(3,2, Color.PURPLE, 1, array, proceeds, cost);

        ArrayList<DevelopmentCard> list3 = new ArrayList<>();
        list3.add(card13);
        list3.add(card14);
        list3.add(card15);
        list3.add(card16);
        DevelopmentCardDeck smallDeck4 = new DevelopmentCardDeck(list3);


        squareCards[0][0] = smallDeck1;
        squareCards[1][0] = smallDeck12;
        squareCards[2][0] = smallDeck13;

        squareCards[0][1] = smallDeck2;
        squareCards[1][1] = smallDeck22;
        squareCards[2][1] = smallDeck222;

        squareCards[0][2] = smallDeck3;
        squareCards[1][2] = smallDeck3;
        squareCards[2][2] = smallDeck3;

        squareCards[0][3] = smallDeck4;
        squareCards[1][3] = smallDeck4;
        squareCards[2][3] = smallDeck4;

        boolean[][] available = new boolean[3][4];
        available[1][0] = true;
        available[2][0] = false;
        available[0][0] = true;
        available[2][1] = true;
        available[1][1] = true;
        available[0][1] = false;
        available[2][2] = true;
        available[1][2] = true;
        available[0][2] = true;
        available[0][3] = true;
        available[1][3] = true;
        available[2][3] = true;


        DevelopmentCardTable developmentCardTable = new DevelopmentCardTable(squareCards);*/

        System.out.println(AnsiColors.RED_BOLD + "HERE IS THE DEVELOPMENT CARD TABLE:\n" + AnsiColors.RESET);


        String row0 = "";
        String row01 = "";
        String row02 = "";
        String row1 = "";
        String row2 = "";
        String row3 = "";
        String row4 = "";
        String row5 = "";
        String row6 = "";
        String row7 = "";
        String row8 = "";
        String row9 = "";
        String row10 = "";
        String row11 = "";
        String row12 = "";
        String row13 = "";
        String row14 = "";
        String row15 = "";
        String row16 = "";
        String row17 = "";
        String row18 = "";

            /*row6 += tableCardCopy.getDeckTable(2,j).takeCard().getvictorytostring();
            row5 += tableCardCopy.getDeckTable(1,j).takeCard().getEarnForCli();
            row4 += tableCardCopy.getDeckTable(0,j).takeCard().getpaytostring();
            row3 += tableCardCopy.getDeckTable(2,j).takeCard().getcosttostring();
            row2 += tableCardCopy.getDeckTable(1,j).takeCard().getleveltostring();
            row1 += tableCardCopy.getDeckTable(0,j).takeCard().getColortoString();*/

        for (int j = 0; j < 4; j++) {

            if (tableCard.getTable()[0][j].getDevelopDeck().isEmpty()) {
                row18 += "                           ";
                row17 += "                           ";
                row16 += "                           ";
                row15 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row14 += "                           ";
                row13 += "                           ";
                row02 += "                           ";
            } else {
                int size1 = tableCard.getTable()[0][j].getDevelopDeck().size() - 1;   //actual Dim of the Deck in the table

                row18 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getvictorytostring();
                row17 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getearntostring();
                row16 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getpaytostring();
                row15 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getcosttostring();
                row14 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getleveltostring();
                row13 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getColortoString();
                row02 += tableCard.getTable()[0][j].getDevelopDeck().get(size1).getIDtoString();
            }

            if (tableCard.getTable()[1][j].getDevelopDeck().isEmpty()) {   // If that card deck is empty!
                row12 += "                           ";
                row11 += "                           ";
                row10 += "                           ";
                row9 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row8 += "                            ";
                row7 += "                            ";
                row01 += "                           ";
            } else {
                int size2 = tableCard.getTable()[1][j].getDevelopDeck().size() - 1;

                row12 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getvictorytostring();
                row11 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getearntostring();
                row10 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getpaytostring();
                row9 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getcosttostring();
                row8 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getleveltostring();
                row7 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getColortoString();
                row01 += tableCard.getTable()[1][j].getDevelopDeck().get(size2).getIDtoString();
            }


            if (tableCard.getTable()[2][j].getDevelopDeck().isEmpty()) {
                row6 += "                           ";
                row5 += "                           ";
                row4 += "                           ";
                row3 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row2 += "                           ";
                row1 += "                           ";
                row0 += "                           ";
            } else {
                int size3 = tableCard.getTable()[2][j].getDevelopDeck().size() - 1;

                row6 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getvictorytostring();
                row5 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getearntostring();
                row4 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getpaytostring();
                row3 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getcosttostring();
                row2 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getleveltostring();
                row1 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getColortoString();
                row0 += tableCard.getTable()[2][j].getDevelopDeck().get(size3).getIDtoString();
            }
        }

        /*for(int j = 0; j < 4 ;j++) {

            if (developmentCardTable.getTable()[0][j].getDevelopDeck().isEmpty()) {
                row18 += "                           ";
                row17 += "                           ";
                row16 += "                           ";
                row15 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row14 += "                           ";
                row13 += "                           ";
                row02 += "                           ";
            } else {
                int size1 = developmentCardTable.getTable()[0][j].getDevelopDeck().size() - 1;   //actual Dim of the Deck in the table

                row18 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getvictorytostring();
                row17 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getearntostring();
                row16 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getpaytostring();
                row15 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getcosttostring();
                row14 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getleveltostring();
                row13 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getColortoString();
                row02 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(size1).getIDtoString();
            }

            if (developmentCardTable.getTable()[1][j].getDevelopDeck().isEmpty()) {   // If that card deck is empty!
                row12 += "                           ";
                row11 += "                           ";
                row10 += "                           ";
                row9 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row8 += "                            ";
                row7 += "                            ";
                row01 += "                           ";
            } else {
                int size2 = developmentCardTable.getTable()[1][j].getDevelopDeck().size() - 1;

                row12 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getvictorytostring();
                row11 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getearntostring();
                row10 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getpaytostring();
                row9 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getcosttostring();
                row8 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getleveltostring();
                row7 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getColortoString();
                row01 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(size2).getIDtoString();
            }


            if (developmentCardTable.getTable()[2][j].getDevelopDeck().isEmpty()) {
                row6 += "                           ";
                row5 += "                           ";
                row4 += "                           ";
                row3 += AnsiColors.RED_BOLD + "      EMPTY DECK           " + AnsiColors.RESET;
                row2 += "                           ";
                row1 += "                           ";
                row0 += "                           ";
            } else {
                int size3 = developmentCardTable.getTable()[2][j].getDevelopDeck().size() - 1;

                row6 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getvictorytostring();
                row5 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getearntostring();
                row4 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getpaytostring();
                row3 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getcosttostring();
                row2 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getleveltostring();
                row1 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getColortoString();
                row0 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(size3).getIDtoString();
            }
        }*/

        for(int i = 0; i < 4 ; i++){
            if(!getAvailable[0][i]){
            //if(!available[0][i]){
                System.out.print(AnsiColors.RED_BOLD+"CARD ("+0+","+i+")                   "+AnsiColors.RESET);
            }else {
                System.out.print("CARD ("+0+","+i+")                    ");
            }
        }
        System.out.print("\n");
        System.out.println("┌---------------------┐    ┌----------------------┐    ┌------------------------┐    ┌-----------------------┐   ");
        System.out.println(row02);
        System.out.println(row13);
        System.out.println(row14);
        System.out.println(row15);
        System.out.println(row16);
        System.out.println(row17);
        System.out.println(row18);
        System.out.println("└---------------------┘     └---------------------┘    └-----------------------┘    └----------------------┘   ");
        System.out.println("\n");

        for(int i = 0; i <4  ; i++){
            if(!getAvailable[1][i]){
            //if(!available[1][i]){
                System.out.print(AnsiColors.RED_BOLD+"CARD ("+1+","+i+")                   "+AnsiColors.RESET);
            }else {
                System.out.print("CARD ("+1+","+i+")                    ");
            }
        }

        System.out.println("\n");
        System.out.println("┌--------------------┐    ┌---------------------┐   ┌---------------------┐     ┌----------------------┐   ");
        System.out.println(row01);
        System.out.println(row7);
        System.out.println(row8);
        System.out.println(row9);
        System.out.println(row10);
        System.out.println(row11);
        System.out.println(row12);
        System.out.println("└--------------------┘    └---------------------┘   └---------------------┘     └-----------------------┘   ");
        System.out.println("\n");

        for(int i = 0; i < 4 ; i++){
            if(!getAvailable[2][i]){
            //if(!available[2][i]){
                System.out.print(AnsiColors.RED_BOLD+"CARD ("+2+","+i+")                   "+AnsiColors.RESET);
            }else {
                System.out.print("CARD ("+2+","+i+")                    ");
            }
        }
        System.out.println("\n");
        System.out.println("┌--------------------┐    ┌----------------------┐    ┌----------------------┐    ┌----------------------┐   ");
        System.out.println(row0);
        System.out.println(row1);
        System.out.println(row2);
        System.out.println(row3);
        System.out.println(row4);
        System.out.println(row5);
        System.out.println(row6);
        System.out.println("└--------------------┘    └----------------------┘    └------------------------┘   └------------------------┘   ");
    }
}
