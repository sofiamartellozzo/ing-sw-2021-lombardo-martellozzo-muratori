package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.board.PersonalBoard;

import java.util.ArrayList;

public class Player {

    private String username;
    private int number;
    private boolean inkpot;
    private ArrayList<LeaderCard>  leaderCards;
    private int victoryPoints;
    private PersonalBoard gameSpace;

    /**
     *
     * @param username
     * constructor of the class, it set the attribute inkpot with default
     */
    public Player(String username) {
        this.username = username;
        this.victoryPoints = 0;
        this.inkpot = false;
    }

    public String getUsername() {
        return username;
    }

    public int getNumber(){
        return this.number;
    }

    public boolean hasInkpot() {
        return inkpot;
    }

    public int getVictoryPoints(){
        return this.victoryPoints;
    }

    public void setGameSpace(PersonalBoard personalBoard){
        this.gameSpace = personalBoard;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * private method that controls if the player is the first
     * if it is, then assign the inkpot to him (set it true)
     */
    private void setInkpot() {
        if (this.number == 1)
            this.inkpot = true;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public PersonalBoard getGameSpace() {
        return gameSpace;
    }


    /**
     *
     * @param cards
     * @param chose1
     * @param chose2
     * method when the player have to choose two leader card from the four that he
     * draw from the dek
     */
    public void chooseLeaderCards(ArrayList<LeaderCard> cards, LeaderCard chose1, LeaderCard chose2){

        int index1 = cards.indexOf(chose1);
        this.leaderCards.add(cards.remove(index1));
        int index2 = cards.indexOf(chose2);
        this.leaderCards.add(cards.remove(index2));


    }

    /**
     * private method invocated when the game end, to set the total
     * points that this player has made
     */
    private void calculateVictoryPoints(){
        //sum of all points.. then set the attribute to it
        this.victoryPoints = 0;
    }

    /**
     * the player decides to invoke the power of a Development Card
     * so he ask the Board to take that from his space and active it
     */
    public void invokesProductionPower(){
        //give input of the method the chosen card
        this.gameSpace.invokeProductionPower();
    }

    public void activeLeaderCardAbility(LeaderCard card){
            //LeaderCard card = chooseLeaderCardToActive();
        //method implemented by Ilaria in her local project
            //LeaderCard card =  this.leaderCards.get(wich);
        //card.activeCard();
    }

    public LeaderCard chooseLeaderCardToActive(int number) {
        return this.leaderCards.get(number);
    }

    public void activeLeaderCardAbility(LeaderCard card,Resource choice){
        //method implemented by Ilaria in her local project
        //card.activeCard(choice);
    }



    public void buyFromMarket(){

    }

    public void buyCard(){

    }

    /**
     * method invocated when the player wants to end the game
     * @return
     */
    public boolean endTurn(){
        return true;
    }


}