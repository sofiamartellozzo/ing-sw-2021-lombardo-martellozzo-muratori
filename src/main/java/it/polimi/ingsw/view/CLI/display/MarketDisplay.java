package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.utility.AnsiColors;

/**
 * this class shows the market updated situation during the Game
 * so the structure of the market and the slide marble
 */
public class MarketDisplay {

    private MarketStructure marketStructure;

    public MarketDisplay(MarketStructure marketStructure){
        this.marketStructure = marketStructure;
    }

    public void displayMarket() {

        System.out.println(AnsiColors.BLUE_BOLD+"HERE IS THE MARKET: \n"+AnsiColors.WHITE_BOLD);
        System.out.print(" Slide Marble: ");
        createMarbleForCli(marketStructure.getSlide());
        System.out.print("\n\n");

        System.out.println("  1   2   3   4");
        for(int i = 0; i < 3; i++){
            System.out.print(i+1);
          for(int j = 0; j < 4; j++)
          {
              createMarbleForCli(marketStructure.getMarble(i,j));
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


}
