package it.polimi.ingsw.Model.board;

/*
* GIANLUCA
* PATTERN
* A component class of the State Pattern
* Return points because the relative Pope's Favor Tile is "Active"
* */

public class Active implements State {
    @Override
    public int returnPoints() {
        return 0;
    }
}
