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

    public DevelopmentCardTableDisplay(DevelopmentCardTable developmentCardTable, boolean[][] getAvailable){

        this.tableCard = developmentCardTable;
        this.getAvailable = getAvailable;
    }

    public void displayCardTable() {
    //public static void main(String[] args) {

        //DevelopmentCardDeck[][] squareCards = new DevelopmentCardDeck[3][4];

        //create the hash map for the cost of a Card
        /*ArrayList<Resource> array = new ArrayList<>();
        array.add(0, new Resource(Color.BLUE));
        array.add(1, new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0, new Resource(Color.GREY));
        proceeds.add(1, new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0, new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(3, Color.GREEN, 1, array, proceeds, cost);
        DevelopmentCard card2 = new DevelopmentCard(2, Color.GREEN, 1, array, proceeds, cost);
        DevelopmentCard card3 = new DevelopmentCard(6, Color.GREEN, 1, array, proceeds, cost);
        DevelopmentCard card4 = new DevelopmentCard(3, Color.GREEN, 1, array, proceeds, cost);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        DevelopmentCardDeck smallDeck1 = new DevelopmentCardDeck(list);

        DevelopmentCard card91 = new DevelopmentCard(3, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card92 = new DevelopmentCard(2, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card93 = new DevelopmentCard(6, Color.GREEN, 2, array, proceeds, cost);
        DevelopmentCard card94 = new DevelopmentCard(3, Color.GREEN, 2, array, proceeds, cost);

        ArrayList<DevelopmentCard> list12 = new ArrayList<>();
        list12.add(card91);
        list12.add(card92);
        list12.add(card93);
        list12.add(card94);
        DevelopmentCardDeck smallDeck12 = new DevelopmentCardDeck(list12);

        DevelopmentCard card81 = new DevelopmentCard(3, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card82 = new DevelopmentCard(2, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card83 = new DevelopmentCard(6, Color.GREEN, 3, array, proceeds, cost);
        DevelopmentCard card84 = new DevelopmentCard(3, Color.GREEN, 3, array, proceeds, cost);

        ArrayList<DevelopmentCard> list13 = new ArrayList<>();
        list13.add(card81);
        list13.add(card82);
        list13.add(card83);
        list13.add(card84);
        DevelopmentCardDeck smallDeck13 = new DevelopmentCardDeck(list13);


        DevelopmentCard card5 = new DevelopmentCard(3, Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card6 = new DevelopmentCard(2, Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card7 = new DevelopmentCard(6, Color.BLUE, 1, array, proceeds, cost);
        DevelopmentCard card8 = new DevelopmentCard(3, Color.BLUE, 1, array, proceeds, cost);

        DevelopmentCard card55 = new DevelopmentCard(3, Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card66 = new DevelopmentCard(2, Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card77 = new DevelopmentCard(6, Color.BLUE, 2, array, proceeds, cost);
        DevelopmentCard card88 = new DevelopmentCard(3, Color.BLUE, 2, array, proceeds, cost);

        DevelopmentCard card555 = new DevelopmentCard(3, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card666 = new DevelopmentCard(2, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card777 = new DevelopmentCard(6, Color.BLUE, 3, array, proceeds, cost);
        DevelopmentCard card888 = new DevelopmentCard(3, Color.BLUE, 3, array, proceeds, cost);

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

        DevelopmentCard card9 = new DevelopmentCard(3, Color.YELLOW, 1, array, proceeds, cost);
        DevelopmentCard card10 = new DevelopmentCard(2, Color.YELLOW, 1, array, proceeds, cost);
        DevelopmentCard card11 = new DevelopmentCard(6, Color.YELLOW, 1, array, proceeds, cost);
        DevelopmentCard card12 = new DevelopmentCard(3, Color.YELLOW, 1, array, proceeds, cost);

        ArrayList<DevelopmentCard> list2 = new ArrayList<>();
        list2.add(card9);
        list2.add(card10);
        list2.add(card11);
        list2.add(card12);
        DevelopmentCardDeck smallDeck3 = new DevelopmentCardDeck(list2);

        DevelopmentCard card13 = new DevelopmentCard(3, Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card14 = new DevelopmentCard(2, Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card15 = new DevelopmentCard(6, Color.PURPLE, 1, array, proceeds, cost);
        DevelopmentCard card16 = new DevelopmentCard(3, Color.PURPLE, 1, array, proceeds, cost);

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
        squareCards[2][3] = smallDeck4;*/


        //DevelopmentCardTable developmentCardTable = DevelopmentCardTable.getInstance(squareCards);

        System.out.println(AnsiColors.RED_BOLD + "HERE IS THE DEVELOPMENT CARD TABLE\n" + AnsiColors.RESET);
        

            /*row6 += tableCardCopy.getDeckTable(2,j).takeCard().getvictorytostring();
            row5 += tableCardCopy.getDeckTable(1,j).takeCard().getEarnForCli();
            row4 += tableCardCopy.getDeckTable(0,j).takeCard().getpaytostring();
            row3 += tableCardCopy.getDeckTable(2,j).takeCard().getcosttostring();
            row2 += tableCardCopy.getDeckTable(1,j).takeCard().getleveltostring();
            row1 += tableCardCopy.getDeckTable(0,j).takeCard().getColortoString();*/

            /*row18 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getvictorytostring();
            row17 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getearntostring();
            row16 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getpaytostring();
            row15 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getcosttostring();
            row14 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getleveltostring();
            row13 += developmentCardTable.getTable()[0][j].getDevelopDeck().get(3).getColortoString();
            
            row12 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getvictorytostring();
            row11 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getearntostring();
            row10 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getpaytostring();
            row9 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getcosttostring();
            row8 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getleveltostring();
            row7 += developmentCardTable.getTable()[1][j].getDevelopDeck().get(3).getColortoString();

            row6 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getvictorytostring();
            row5 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getearntostring();
            row4 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getpaytostring();
            row3 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getcosttostring();
            row2 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getleveltostring();
            row1 += developmentCardTable.getTable()[2][j].getDevelopDeck().get(3).getColortoString();*/

        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if(getAvailable[i][j]) {
                    System.out.println("CARD" + "(" + i + "," + j + ")");
                    System.out.print(tableCard.getTable()[i][j].getDevelopDeck().get(tableCard.getTable()[i][j].getDevelopDeck().size()-1).toString());
                }else{
                    //if this deck is unavailable ( size = 0) this will be printed with another color
                    System.out.println("CARD" + "(" + i + "," + j + ")");
                    System.out.print(AnsiColors.ANSI_RED+tableCard.getTable()[i][j].getDevelopDeck().get(tableCard.getTable()[i][j].getDevelopDeck().size()-1).toString()+AnsiColors.RESET);
                }
            }


        }
    }
}