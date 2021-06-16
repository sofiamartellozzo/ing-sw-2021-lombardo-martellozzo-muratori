package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.resourceManagement.Depot;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.cardAbility.SpecialDepot;
import it.polimi.ingsw.utility.AnsiColors;

/**
 * this class shows to the player his warehouse, so the three depots
 * and if one of them is empty it displays the message "IS EMPTY"
 */
public class WarehouseDisplay {

    private Warehouse warehouse;
    private TypeResource specialResource;

    public WarehouseDisplay(Warehouse warehouse, TypeResource specialResource){
        this.warehouse = warehouse;
        this.specialResource = specialResource;
    }

        public void displayWarehouse() {

        System.out.print(AnsiColors.BLUE_BOLD+"\nHERE IS YOUR WAREHOUSE:\n"+AnsiColors.RESET);
        for (int i = 0; i < warehouse.getDepots().size(); i++) {
            Depot depot = warehouse.getDepots().get(i);
            if (depot.isEmpty()) {
                if (i < 3 ) {
                    System.out.print("DEPOT" + (i + 1) + ":" + AnsiColors.RED_BOLD + " IS EMPTY\n" + AnsiColors.RESET);
                } else {

                    System.out.print("SPECIAL DEPOT" + (i + 1) + ":(" + depot.getType().toString() + ")" + AnsiColors.RED_BOLD + " IS EMPTY\n" + AnsiColors.RESET);
                }
            } else {
                if (i < 3) {
                    System.out.print("DEPOT" + (i + 1) + ":");
                    for (Resource res : depot.getResources()) {
                        System.out.print(createResForCli(res));
                    }
                }else{
                    System.out.print("SPECIAL DEPOT" + (i + 1) + ":(" + depot.getType().toString() + ")");
                    for (Resource res : depot.getResources()) {
                        System.out.print(createResForCli(res));
                    }
                }
                System.out.print("\n");
            }
        }

    }

    public static String createResForCli(Resource resource) {
        String cost = "";

        switch (resource.getType()){
            case SHIELD:
                cost += "🥏";
                break;
            case STONE:
                cost += "🗿";
                break;
            case COIN:
                cost += "💰";
                break;
            case SERVANT:
                cost += "👾";
                break;

        }
        return cost;
    }
}