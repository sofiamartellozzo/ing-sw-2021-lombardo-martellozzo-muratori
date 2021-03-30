package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.board.PersonalSoloBoardFactory;
import it.polimi.ingsw.Model.board.SoloPersonalBoard;
import it.polimi.ingsw.Model.market.MarketStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TurnController {
    private Map<Integer, Player> turnSequence;
    private int currenyTurnIndex;
    private PlayerTurn currentTurnIstance;
    private Player currentPlayer;
    private int numberOfPlayer;
    private ArrayList<PopesFavorTileReview> checkPopesFavorTile;

    /* the parts of the game that all the player havi in common*/
    private ResourcesSupply resourcesSupply;
    private MarketStructure marketStructure;

    /* Constructor of the class */
    public TurnController(ArrayList<Player> players) {

        this.numberOfPlayer = players.size();
        this.turnSequence = new HashMap<>();
        //whe have to choose random the first player
        Player first= choiceRandomFirstPlayer(players);
        this.turnSequence.put(1, first);
        this.currenyTurnIndex = 1;
        fillSequence(players, first);

        //contruct the resources supply for all the players
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        //this.resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

    }

    /**
     *
     * @param players
     * @return the first player of the game, chosen random
     */
    private Player choiceRandomFirstPlayer(ArrayList<Player> players){
        Random random = new Random();
        Player first = players.get(random.nextInt(players.size()));
        first.setNumber(1);
        return first;
    }

    /**
     *
     * @param players
     * @param first
     * fill the map attribute to create a sequence of the turn, with the other player except the first
     * that is altready in the sequence
     */
    private void fillSequence(ArrayList<Player> players, Player first){
        players.remove(first);
        for (int j = 2; (j< 5)/*&&(players.size() > 0)*/;j++){
            players.get(j-2).setNumber(j);
            this.turnSequence.put(j, players.get(j-2));

        }
    }

    public void gamePlay(){
        //where the game starts

        if (this.numberOfPlayer == 1){
            PersonalSoloBoardFactory soloBoardFactory = new PersonalSoloBoardFactory();
            SoloPersonalBoard soloPersonalBoard = soloBoardFactory.createGame();
            this.turnSequence.get(1).setGameSpace(soloPersonalBoard);
        }
    }

    private void sratrSoloPlayerTurn(Player player){
        SoloPlayerTurn spt = new SoloPlayerTurn(player);
    }

    private void startPlayerTurn(Player player){
        PlayerTurn pt = new PlayerTurn(player);
        if (pt.checkEndTurn())
            nextTurn();
    }

    private void nextTurn(){
        this.currenyTurnIndex++;
        Player nextPlayer = this.turnSequence.get(currenyTurnIndex);
        startPlayerTurn(nextPlayer);
    }


}
