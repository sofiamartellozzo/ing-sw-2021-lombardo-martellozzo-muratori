package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.utility.AnsiColors;

/**
 * this class shows to the player his Strongbox with Resources
 */
public class StrongboxDisplay {

    private StrongBox strongBox;

    public StrongboxDisplay(StrongBox strongBox){
        this.strongBox = strongBox;
    }

       public void displayStrongBox(){

        //take the count of every type of resource inside the strongbox
        int coinInStrong = 0;
        int shieldInStrong = 0;
        int servantInStrong = 0;
        int stoneInStrong = 0;

        for (Resource res:strongBox.getContent()) {
            if(res.getType() == TypeResource.COIN){
                coinInStrong++;

            }else if(res.getType() == TypeResource.STONE){
                stoneInStrong++;

            }else if(res.getType() == TypeResource.SERVANT){
                servantInStrong++;

            }else if(res.getType() == TypeResource.SHIELD){
                shieldInStrong++;
            }

        }
        System.out.print("\n");
        System.out.println(AnsiColors.BLUE_BOLD+"HERE IS YOUR STRONGBOX"+AnsiColors.RESET);
        System.out.print(AnsiColors.WHITE_BOLD +" ┌--------------------┐ ");
        System.out.print("\n");
        System.out.print(" │      ");
        System.out.print(AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET+":"+stoneInStrong+AnsiColors.RESET+" "+AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET+":"+servantInStrong+"     │"+"\n │      "+AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET+":"+coinInStrong+" "+AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET+":"+shieldInStrong+"     │");
        System.out.print(AnsiColors.WHITE_BOLD + "\n └--------------------┘ ");
        System.out.print("\n");

    }
}
