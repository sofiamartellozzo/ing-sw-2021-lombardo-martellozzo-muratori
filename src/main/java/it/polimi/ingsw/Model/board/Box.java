package it.polimi.ingsw.Model.board;

/*
* GIANLUCA
* It's composing the FaithTrack with its extensions:
* SimpleBox, GoldBox and PopeBox.
* Attribute:
* whichSection -> If it is, indicates in which Vatican Section it's situated
* to facilitate the control of the Players, if they're in or out;
* numberBox -> the numeration of the boxes.
* */

public abstract class Box {

    private final int whichSection;
    private final int numberBox;

    /*
    * Constructor
    * */
    public Box(int whichSection, int numberBox) {
        this.whichSection = whichSection;
        this.numberBox = numberBox;
    }

    /*
    * Getter Methods
    * */
    public int getWhichSection() {
        return whichSection;
    }

    public int getNumberBox() {
        return numberBox;
    }
}
