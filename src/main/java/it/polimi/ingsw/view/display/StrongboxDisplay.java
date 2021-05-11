package it.polimi.ingsw.view.display;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/**
 * this class shows to the player his Strongbox with Resources
 */
public class StrongboxDisplay {

    private StrongBox strongBox;
    private PlayerInterface player;

    public StrongboxDisplay(StrongBox strongBox, PlayerInterface player){
        this.strongBox = strongBox;
        this.player = player;
    }

    /*public static void main(String[] args) throws InvalidActionException {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("bob");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<Resource> array = new ArrayList<>();
        array.add(new Resource(Color.BLUE));
        array.add(new Resource(Color.PURPLE));

        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);
        player.getGameSpace().getResourceManager().addResourcesToStrongBox(array);*/

       public void displayStrongBox(){
       //take the count of every type of resource inside the strongbox
        int coinInStrong = 0;
        int shieldInStrong = 0;
        int servantInStrong = 0;
        int stoneInStrong = 0;

        for (Resource res:player.getGameSpace().getResourceManager().getStrongBox().getContent()) {
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
        System.out.println(AnsiColors.RED_BOLD+"HERE IS YOUR STRONGBOX"+AnsiColors.RESET);
        System.out.print(AnsiColors.WHITE_BOLD +" |--------------------| ");
        System.out.print("\n");
        System.out.print("        ");
        System.out.print(AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET+":"+stoneInStrong+AnsiColors.RESET+" "+AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET+":"+servantInStrong+" "+"\n        "+AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET+":"+coinInStrong+" "+AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET+":"+shieldInStrong+" ");
        System.out.print(AnsiColors.WHITE_BOLD + "\n |--------------------| ");

    }
}
