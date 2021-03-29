package it.polimi.ingsw.Model.board;

/*
 * GIANLUCA
 * PATTERN
 * A component class of the State Pattern.
 * Return 0 points because the relative Pope's Favor Tile is "Inactive"
 * */
public class Inactive implements State{
    @Override
    public int returnPoints() {
        return 0;
    }
}
