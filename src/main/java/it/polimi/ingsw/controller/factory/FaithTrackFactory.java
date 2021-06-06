package it.polimi.ingsw.controller.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.board.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Pattern: FACTORY
 * Creates the Faith Track of the game, combining 7 Gold Boxes, 3 Pope Boxes, 15 Simple Boxes
 * The Track is composed by 3 Popes Favor Tiles, 3 Vatican Sections and a Faith Marker
 */
public class FaithTrackFactory {

    public FaithTrack createFaithTrack() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ArrayList<Box> boxes = new ArrayList<>();
        //15 normal box
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/simpleBox.json"));
        Type list = new TypeToken<ArrayList<SimpleBox>>() {
        }.getType();
        ArrayList<SimpleBox> simpleBoxes = gson.fromJson(reader, list);
        boxes.addAll(simpleBoxes);

        //7 gold box
        BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/goldBox.json"));
        Type list1 = new TypeToken<ArrayList<GoldBox>>() {
        }.getType();
        ArrayList<GoldBox> goldBoxes = gson.fromJson(reader1, list1);
        boxes.addAll(goldBoxes);

        //3 pope box
        BufferedReader reader2 = new BufferedReader(new FileReader("src/main/resources/popeBox.json"));
        Type list2 = new TypeToken<ArrayList<PopeBox>>() {
        }.getType();
        ArrayList<PopeBox> popeBoxes = gson.fromJson(reader2, list2);
        boxes.addAll(popeBoxes);

        //create and add the 3 Popes Favor Tile
        ArrayList<PopesFavorTile> array3 = new ArrayList<>();

        PopesFavorTile popesFavorTile1 = new PopesFavorTile(1, 2);
        PopesFavorTile popesFavorTile2 = new PopesFavorTile(2, 3);
        PopesFavorTile popesFavorTile3 = new PopesFavorTile(3, 4);

        array3.add(popesFavorTile1);
        array3.add(popesFavorTile2);
        array3.add(popesFavorTile3);

        //devided the boxes for each section
        ArrayList<Box> boxes1 = new ArrayList<>();
        ArrayList<Box> boxes2 = new ArrayList<>();
        ArrayList<Box> boxes3 = new ArrayList<>();

        for (Box box : boxes) {
            if (box.getWhichSection() == 1) {
                boxes1.add(box);
            } else if (box.getWhichSection() == 2) {
                boxes2.add(box);
            } else if (box.getWhichSection() == 3) {
                boxes3.add(box);
            }
        }

        //create the 3 vatican Section
        ArrayList<VaticanSection> vaticanSections = new ArrayList<>();

        VaticanSection vaticanSection1 = new VaticanSection(1, boxes1, popesFavorTile1);
        VaticanSection vaticanSection2 = new VaticanSection(2, boxes2, popesFavorTile2);
        VaticanSection vaticanSection3 = new VaticanSection(3, boxes3, popesFavorTile2);

        vaticanSections.add(vaticanSection1);
        vaticanSections.add(vaticanSection2);
        vaticanSections.add(vaticanSection3);

        //create faith marker
        FaithMarker faithMarker = new FaithMarker();

        FaithTrack faithTrack = new FaithTrack(boxes, array3, faithMarker, vaticanSections);
        return faithTrack;
    }
}
