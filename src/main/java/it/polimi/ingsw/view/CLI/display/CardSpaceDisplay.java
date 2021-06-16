package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/**
 * this class contains the method to show to the player his Card's Spaces, in particular the last card of each Card Space
 * because it is the only one that he can activate
 */
public class CardSpaceDisplay {

    private ArrayList<CardSpace> cardSpaces;


    public CardSpaceDisplay(ArrayList<CardSpace> cardSpaces){
        this.cardSpaces = cardSpaces;

    }

    public void showCardSpaces(){

        System.out.println(AnsiColors.BLUE_BOLD+"HERE ARE YOUR THREE CARD SPACES:\n"+AnsiColors.RESET);
        for(int i = 0; i < 3; i++) {
            if(cardSpaces.get(i).getCards().isEmpty()){
                System.out.print("Card Space"+(i+1)+": "+AnsiColors.RED_BOLD+"EMPTY CARD SPACE\n"+AnsiColors.RESET);
            }
            else {
                System.out.println("CARD Space"+(i+1)+": ");
                System.out.println(cardSpaces.get(i).getUpperCard().toString());

            }
            System.out.print("\n");
        }
    }
}
