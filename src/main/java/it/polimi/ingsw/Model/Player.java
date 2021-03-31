package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.cardAbility.Discount;

import java.util.ArrayList;

public class Player {

    private String username;
    private int number;
    private boolean inkpot;
    private ArrayList<LeaderCard>  leaderCards;
    private int victoryPoints;
    private PersonalBoard gameSpace;
    private boolean whiteSpecialAbility; //remove this

    /**
     *
     * @param username
     * constructor of the class, it set the attribute inkpot with default
     */
    public Player(String username) {
        this.username = username;
        this.victoryPoints = 0;
        this.inkpot = false;
        this.whiteSpecialAbility = false;
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

    public boolean hasWhiteSpecialAbility() {
        return whiteSpecialAbility;
    }

    public void setWhiteSpecialAbility(boolean whiteSpecialAbility) {
        this.whiteSpecialAbility = whiteSpecialAbility;
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
    public void invokesProductionPowerFromStronBox(DevelopmentCard card){
        //the production powers are activated all toghether, after one gli cannot active another one
        //give input of the method the chosen card
        this.gameSpace.invokeProductionPowerFromStrongBox(card);
    }

    public void activeLeaderCardAbility(LeaderCard card){
            //LeaderCard card = chooseLeaderCardToActive();
        //method implemented by Ilaria in her local project
            //LeaderCard card =  this.leaderCards.get(wich);
        //card.activeCard(this);
    }

    public LeaderCard chooseLeaderCardToActive(int number) {
        return this.leaderCards.get(number);
    }

    public void activeLeaderCardAbility(LeaderCard card,Resource choice){
        //method implemented by Ilaria in her local project
        card.activeCard(choice);
    }



    public void buyFromMarket(){

    }

    public void buyCard(){
        if (checkDiscountAbility() != 0){
            this.leaderCards.get(checkDiscountAbility()).getSpecialAbility().activeAbility();
            //not active ability but ask the class buy card with discount!!!
        }
        else{
            //normal buy
        }
        
        /*check the player's Leader card:
        * if there is one with the ability of discount I have to take this ability*/

    }

    public int checkDiscountAbility(){
        if (this.leaderCards.get(1).getSpecialAbility().equals(Discount)){
            return 1;
        }
        else if (this.leaderCards.get(2).getSpecialAbility().equals(Discount)){
            return 2;
        }
        else
            return 0;

    }

    /**
     * method invocated when the player wants to end the game
     * @return
     */
    public boolean endTurn(){
        return true;
    }

    public void putResources(Resource resources){
        //i ask to put this resource in this player warehouse or strongBox
    }


}