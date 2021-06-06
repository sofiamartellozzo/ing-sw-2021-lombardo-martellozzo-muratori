package it.polimi.ingsw.controller.factory;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.WarehouseStandard;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/* SOFI
*/

/**
 * Pattern: FACTORY
 * this class is made to create the Personal Board of each Player
 * So the 3 Card spaces, the Resource manager containing Warehouse and Strongbox and the Faith track
 */
public class PersonalBoardFactory {

    public PersonalBoard createGame(){

        //create the 3 spaces where stored the Development Card for each player
        ArrayList<CardSpace> allCardSpace = createCardSpaces();

        //create the spaces where stored the Player's resources
        StrongBox strongBox = new StrongBox();
        WarehouseStandard warehouseStandard = new WarehouseStandard();
        //create the class that manage both strongbox and warehouse, to make easier the access in "read" or "write" mode to them
        ResourceManager resourceManager = new ResourceManager(strongBox, warehouseStandard);

        //create the faith trak whit all his components
        FaithTrack faithTrack = createFaithTrack();

        //FOR debug add a lot resources to the player
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.BLUE));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.YELLOW));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.PURPLE));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        resources.add(new Resource(Color.GREY));
        try {
            strongBox.addResources(resources);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        return new PersonalBoard(faithTrack, resourceManager, allCardSpace);
    }

    public ArrayList<CardSpace> createCardSpaces(){
        CardSpace cardSpace1 = new CardSpace(1);
        CardSpace cardSpace2 = new CardSpace(2);
        CardSpace cardSpace3 = new CardSpace(3);
        ArrayList<CardSpace> allCardSpace = new ArrayList<>();
        allCardSpace.add(cardSpace1);
        allCardSpace.add(cardSpace2);
        allCardSpace.add(cardSpace3);
        return allCardSpace;
    }

    public FaithTrack createFaithTrack(){
        FaithTrackFactory faithTrackFactory = new FaithTrackFactory();
        FaithTrack faithTrack = null;
        try {
            faithTrack = faithTrackFactory.createFaithTrack();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return faithTrack;

    }

}
