package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.board.PersonalBoardFactory;
import it.polimi.ingsw.Model.board.PersonalSoloBoardFactory;
import it.polimi.ingsw.Model.board.SoloPersonalBoard;
import it.polimi.ingsw.Model.card.DevelopmentCardTable;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.card.LeaderCardDeck;
import it.polimi.ingsw.Model.market.MarketStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/*
* SOFI*/

public class TurnController {
    private HashMap<Integer, Player> turnSequence;
    private int currenyTurnIndex;
    private PlayerTurn currentTurnIstance;
    private Player currentPlayer;
    private int numberOfPlayer;
    private ArrayList<PopesFavorTileReview> checkPopesFavorTile;

    /* the parts of the game that all the player have in common*/
    private BoardManager boardManager;

    /* Constructor of the class */
    public TurnController(ArrayList<Player> players) {

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

    public void gamePlay() throws InvalidActionException{
        //where the game starts

        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        this.boardManager = boardManagerFactory.createBoardManager(this.turnSequence);

        if (this.numberOfPlayer == 1){
            PersonalSoloBoardFactory soloBoardFactory = new PersonalSoloBoardFactory();
            SoloPersonalBoard soloPersonalBoard = soloBoardFactory.createGame();
            this.turnSequence.get(1).setGameSpace(soloPersonalBoard);
            chooseLeaderCard();
            startSoloPlayerTurn(this.currentPlayer);
        }
        else{

            for (Integer i: this.turnSequence.keySet()) {
                PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
                PersonalBoard personalBoard = personalBoardFactory.createGame();
                this.turnSequence.get(i).setGameSpace(personalBoard);
                chooseLeaderCard();
            }
            giveStartResources();
            startPlayerTurn(this.currentPlayer);
        }
    }

    private void chooseLeaderCard(){
        Random random = new Random();
        ArrayList<LeaderCard> fourLeaderCard = new ArrayList<>();
        for (Integer j: this.turnSequence.keySet()) {
            for (int i=0; i<4; i++){
                fourLeaderCard.add(this.boardManager.getLeaderCardDeck().getCards().get(random.nextInt(this.boardManager.getLeaderCardDeck().getNumberOfCards())));
            }
            this.turnSequence.get(j).chooseLeaderCards(fourLeaderCard, 1,2);
            this.boardManager.getLeaderCardDeck().remove(this.turnSequence.get(j).getLeaderCards());
        }
    }

    private void giveStartResources() throws InvalidActionException {
        if (this.turnSequence.get(1)!= null){
            //the second player recive one resources that he choose
            this.turnSequence.get(1).getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.YELLOW), 1);
            if (this.turnSequence.get(2)!= null){
                //the third player recive one resource and a faith marker(so thi increase of one his position)
                this.turnSequence.get(2).getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.BLUE), 1);
                this.turnSequence.get(2).getGameSpace().getFaithTrack().increasePosition();
                if (this.turnSequence.get(3)!= null){
                    //the fourth player recives two resources and a faith marker(so thi increase of one his position)
                    this.turnSequence.get(3).getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.PURPLE), 2);
                    this.turnSequence.get(3).getGameSpace().getResourceManager().getWarehouse().addResource(new Resource(Color.PURPLE), 2);
                    this.turnSequence.get(3).getGameSpace().getFaithTrack().increasePosition();
                }
            }
        }


    }


    private void startSoloPlayerTurn(Player player){
        SoloPlayerTurn spt = new SoloPlayerTurn(player, this.boardManager);

    }

    private void startPlayerTurn(Player player){
        this.currentPlayer = player;
        player.setPlaying(true);
        PlayerTurn pt = new PlayerTurn(player, this.boardManager);
        if (pt.checkEndTurn()){
            nextTurn();
        }
    }

    private void nextTurn(){
        if (this.currenyTurnIndex == this.numberOfPlayer){
            this.currenyTurnIndex = 1;
        }
        else {
            this.currenyTurnIndex++;
        }
        Player nextPlayer = this.turnSequence.get(currenyTurnIndex);
        startPlayerTurn(nextPlayer);
    }


}
