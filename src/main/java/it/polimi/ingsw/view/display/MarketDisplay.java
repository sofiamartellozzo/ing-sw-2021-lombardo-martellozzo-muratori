package it.polimi.ingsw.view.display;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.market.ColoredMarble;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.model.market.RedMarble;
import it.polimi.ingsw.utility.AnsiColors;

/**
 * this class shows the market updated situation during the Game
 */
public class MarketDisplay {

    public static void displayMarket(Marble[][] marbles ,Marble slide) {

        System.out.println(AnsiColors.RED_BOLD+"HERE IS THE MARKET: \n"+AnsiColors.WHITE_BOLD);
        System.out.print(" Slide Marble: ");
        createMarbleForCli(slide);
        System.out.print("\n\n");

        System.out.println("  1   2   3   4");
        for(int i = 0; i < 3; i++){
            System.out.print(i+1);
          for(int j = 0; j < 4; j++)
          {
              createMarbleForCli(marbles[i][j]);
          }
          System.out.print("\n");


       }
    }

    private static void createMarbleForCli(Marble marble){
        switch (marble.getColor()){
            case PURPLE:
                System.out.print(AnsiColors.PURPLE_BOLD+" \uD83D\uDFE3 "+AnsiColors.RESET);
            break;
            case YELLOW:
                System.out.print(AnsiColors.YELLOW_BOLD+" \uD83D\uDFE1 "+AnsiColors.RESET);
            break;
            case BLUE:
                System.out.print(AnsiColors.BLUE_BOLD+" \uD83D\uDD35 "+AnsiColors.RESET);
            break;
            case RED:
                System.out.print(AnsiColors.RED_BOLD+" \uD83D\uDD34 "+AnsiColors.RESET);
            break;
            case WHITE:
                System.out.print(AnsiColors.WHITE_BOLD+" \u26AA "+AnsiColors.RESET);
            break;
            case GREY:
                System.out.print(AnsiColors.BLACK_BOLD+" \u26AB "+AnsiColors.RESET);
            break;
        }
    }

    public static void main(String[] args) {

        Marble m = new ColoredMarble(Color.YELLOW);
        Marble m1 = new RedMarble();
        Marble m2 = new ColoredMarble(Color.YELLOW);
        Marble m3 = new ColoredMarble(Color.WHITE);
        Marble m4 = new ColoredMarble(Color.PURPLE);
        Marble m5 = new RedMarble();
        Marble m6 = new ColoredMarble(Color.BLUE);
        Marble m7 = new ColoredMarble(Color.BLUE);
        Marble m8 = new RedMarble();
        Marble m9 = new RedMarble();
        Marble m10 = new ColoredMarble(Color.PURPLE);
        Marble m11 = new ColoredMarble(Color.YELLOW);
        Marble m12 = new ColoredMarble(Color.WHITE);
        Marble[][] structure = {{m,m1,m2,m3},{m4,m5,m6,m7},{m8,m9,m10,m11}};
        MarketStructure marketStructure = MarketStructure.getInstance(structure, m12);

        displayMarket(marketStructure.getStructure(), m12);


    }

}
