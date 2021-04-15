package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.board.PersonalBoardFactory;
import it.polimi.ingsw.model.board.PersonalSoloBoardFactory;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
* SOFI*/

/**
 * this class is the part of the controller that arraing the start of the game, and set the turn sequence
 */

public class TurnController {
    private HashMap<Integer, PlayerInterface> turnSequence;
    private int currenyTurnIndex;
    private PlayerTurn currentTurnIstance;
    private Player currentPlayer;
    private int numberOfPlayer;
    private ArrayList<PopesFavorTileReview> checkPopesFavorTile;

    /* the solo player */
    private SoloPlayer singlePlayer;

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

    /**
     * method to inizialized the game, creating the spaces and setting everithing
     * exept the Board Manager the rest is setted differently if the number of player
     * are one or more : so solo mode or multiple mode
     * @throws InvalidActionException
     */
    public void gamePlay() throws InvalidActionException{
        //where the game starts



        if (this.numberOfPlayer == 1){
            HashMap<Integer, PlayerInterface> player = new HashMap<>();
            PersonalSoloBoardFactory soloBoardFactory = new PersonalSoloBoardFactory();
            SoloPersonalBoard soloPersonalBoard = soloBoardFactory.createGame();
            this.singlePlayer = new SoloPlayer(this.turnSequence.get(1).getUsername());
            this.singlePlayer.setGameSpace(soloPersonalBoard);
            player.put(1,this.singlePlayer);
            BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
            this.boardManager = boardManagerFactory.createBoardManager(player);
            chooseLeaderCard(true);
            startSoloPlayerTurn(this.singlePlayer);
        }
        else{

            BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
            this.boardManager = boardManagerFactory.createBoardManager(this.turnSequence);
            for (Integer i: this.turnSequence.keySet()) {
                PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
                PersonalBoard personalBoard = personalBoardFactory.createGame();
                this.turnSequence.get(i).setGameSpace(personalBoard);
                chooseLeaderCard(false);
            }
            giveStartResources();
            startPlayerTurn(this.currentPlayer);
        }
    }

    /**
     * at the start of the game all player (both single or multiple mode)
     * have to choose 2 Leader Card from 4 choosen random
     * @param solo
     */
    private void chooseLeaderCard(boolean solo){
        Random random = new Random();
        ArrayList<LeaderCard> fourLeaderCard = new ArrayList<>();
        for (Integer j: this.turnSequence.keySet()) {
            for (int i=0; i<4; i++){
                fourLeaderCard.add(this.boardManager.getLeaderCardDeck().getCards().get(random.nextInt(this.boardManager.getLeaderCardDeck().getNumberOfCards())));
            }
            if (solo){
                this.singlePlayer.chooseLeaderCards(fourLeaderCard, 1, 2);
                this.boardManager.getLeaderCardDeck().remove(this.singlePlayer.getLeaderCards());
            }
            else{
                this.turnSequence.get(j).chooseLeaderCards(fourLeaderCard, 1,2);
                this.boardManager.getLeaderCardDeck().remove(this.turnSequence.get(j).getLeaderCards());
            }
        }
    }

    /**
     * this method is called only in multiple mode, in single one is not necessary
     * at the game start, after chose the Leader Card all player recive different stuff:
     * the first one only the inkpot
     * the second one recive one resource that he choose
     * the third one (if exist) recive one resource that he choose and increasing of one position the faith market
     * the fourth one (if exist) recive two resources that he choose and increasing of one position the paith market
     * @throws InvalidActionException
     */
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

    /**
     * start the game in solo mode, so create the Solo Player Turn that manage the different
     * action the player can do
     * @param player
     */
    private void startSoloPlayerTurn(SoloPlayer player){
        SoloPlayerTurn spt = new SoloPlayerTurn(player, this.boardManager);

    }

    /**
     * start a new Turn of a Player in multiple mode, called any time a new turn start
     * first after setting all game, then at the end of the others player turn
     * @param player
     */
    private void startPlayerTurn(Player player){
        this.currentPlayer = player;
        player.setPlaying(true);
        PlayerTurn pt = new PlayerTurn(player, this.boardManager);
        if (pt.checkEndTurn()){
            nextTurn();
        }
    }

    /**
     * when a player end his turn, this method is called to set the next player
     * and create a new turn for him
     */
    private void nextTurn(){
        if (this.currenyTurnIndex == this.numberOfPlayer){
            this.currenyTurnIndex = 1;
        }
        else {
            this.currenyTurnIndex++;
        }
        Player nextPlayer = (Player) this.turnSequence.get(currenyTurnIndex);
        startPlayerTurn(nextPlayer);
    }

    /*check if someone is in the pop's favor tile...*/


}
