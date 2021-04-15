package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.event.ControllerListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class set the information of the game at the initialization
 */

public class InitializedController implements ControllerListener {
    /* the parts of the game that all the player have in common*/
    private BoardManager boardManager;

    /* the sequence of the player to the game */
    private HashMap<Integer, PlayerInterface> turnSequence;
    private int currenyTurnIndex;
    private int numberOfPlayer;

    /* the solo player */
    private SoloPlayer singlePlayer;

    /* Constructor of the class */
    public InitializedController(ArrayList<Player> players) {

        this.numberOfPlayer = players.size();
        this.turnSequence = new HashMap<>();
        //whe have to choose random the first player
        Player first= choiceRandomFirstPlayer(players);
        this.turnSequence.put(1, first);
        this.currentPlayer = first;
        this.currenyTurnIndex = 1;
        fillSequence(players, first);

        //contruct the resources supply for all the players
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        //this.resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

    }


}
