package it.polimi.ingsw.view.display;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/**
 * this class shows to the player his warehouse, so the three depots
 * and if one of them is empty it display the message "IS EMPTY"
 */
public class WarehouseDisplay {

    public static void main(String[] args) throws InvalidActionException {

        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("bob");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);
        ArrayList<Resource> array = new ArrayList<>();
        array.add(new Resource(Color.BLUE));
        array.add(new Resource(Color.PURPLE));

        player.getGameSpace().getResourceManager().addResourceToWarehouse(new Resource(TypeResource.SHIELD), 1);
        player.getGameSpace().getResourceManager().addResourceToWarehouse(new Resource(TypeResource.SERVANT), 2);
        player.getGameSpace().getResourceManager().addResourceToWarehouse(new Resource(TypeResource.SERVANT), 2);

        System.out.print(AnsiColors.YELLOW_BOLD+"\nHERE IS YOUR WAREHOUSE:\n"+AnsiColors.RESET);
        for (int i = 0; i < 3; i++) {
            if (player.getGameSpace().getResourceManager().getWarehouse().getDepots().get(i).getResources().isEmpty()) {
                System.out.print("DEPOT" + (i+1) + ":" + AnsiColors.RED_BOLD + " IS EMPTY\n" + AnsiColors.RESET);
            } else {
                System.out.print("DEPOT" + (i+1) + ":");
                for (Resource res : player.getGameSpace().getResourceManager().getWarehouse().getDepots().get(i).getResources()) {
                    System.out.print(createResForCli(res));

                }
                System.out.print("\n");
            }
        }

    }

    public static String createResForCli(Resource resource) {
        String cost = "";

        switch (resource.getType()){
            case SHIELD:
                cost += AnsiColors.BLUE_BOLD+"\uD83D\uDD35"+AnsiColors.RESET;
                break;
            case STONE:
                cost += AnsiColors.BLACK_BOLD+"\u26AB"+AnsiColors.RESET;
                break;
            case COIN:
                cost += AnsiColors.YELLOW_BOLD+"\uD83D\uDFE1"+AnsiColors.RESET;
                break;
            case SERVANT:
                cost += AnsiColors.PURPLE_BOLD+"\uD83D\uDFE3"+AnsiColors.RESET;
                break;

        }
        return cost;
    }
}