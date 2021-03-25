package it.polimi.ingsw.Model.board;

public class Inactive implements State{

    @Override
    public int returnPoints() {
        return 0;
    }
}
