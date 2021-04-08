package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.ActionToken;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;

import java.util.ArrayList;

/* SOFI
* PATTERN
* */
public class PersonalBoardFactory {
    /*
    * In order to create the Personal Board of each Player
    */
    public PersonalBoard createGame(){
        ArrayList<CardSpace> allCardSpace = createCardSpaces();
        StrongBox strongBox = new StrongBox();
        WarehouseStandard warehouseStandard = new WarehouseStandard();
        ResourceManager resourceManager = new ResourceManager(strongBox, warehouseStandard);
        FaithTrack faithTrack = createFaithTrack();
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
        ArrayList<Box> boxes = new ArrayList<>();
        //15 normal box
        SimpleBox normalBox = new SimpleBox(0, 1);
        boxes.add(normalBox);
        SimpleBox normalBox2 = new SimpleBox(1, 2);
        boxes.add(normalBox2);
        //7 gold box
        GoldBox goldBox = new GoldBox(2,3,1);
        boxes.add(goldBox);
        //3 pope box
        PopeBox popeBox = new PopeBox(2,4);
        boxes.add(popeBox);


        //create and add the 3 Popes Favor Tile
        PopesFavorTile popesFavorTile1 = new PopesFavorTile(1, 3);
        PopesFavorTile popesFavorTile2 = new PopesFavorTile(2, 3);
        PopesFavorTile popesFavorTile3 = new PopesFavorTile(3, 3);
        ArrayList<PopesFavorTile> array3 = new ArrayList<>();
        array3.add(popesFavorTile1);
        array3.add(popesFavorTile2);
        array3.add(popesFavorTile3);

        //create the 3 vatican Section
        VaticanSection vaticanSection1 = new VaticanSection(1, boxes, popesFavorTile1);
        VaticanSection vaticanSection2 = new VaticanSection(2, boxes, popesFavorTile2);
        VaticanSection vaticanSection3 = new VaticanSection(3, boxes, popesFavorTile2);

        ArrayList<VaticanSection> vaticanSections = new ArrayList<>();
        vaticanSections.add(vaticanSection1);
        vaticanSections.add(vaticanSection2);
        vaticanSections.add(vaticanSection3);

        //create faith market
        FaithMarker faithMarker = new FaithMarker();

        return new FaithTrack(boxes, array3, faithMarker, vaticanSections);
    }
}
