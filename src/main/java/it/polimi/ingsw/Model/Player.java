package it.polimi.ingsw.Model;
import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.card.Card;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import it.polimi.ingsw.Model.card.LeaderCard;
import it.polimi.ingsw.Model.board.PersonalBoard;
import it.polimi.ingsw.Model.card.SpecialCard;
import it.polimi.ingsw.Model.cardAbility.Discount;

import java.util.ArrayList;

/*
 * SOFI*/

public class Player {

    private String username;
    private int number;
    private boolean inkpot;

    private boolean playing;

    private ArrayList<LeaderCard>  leaderCards;
    private int victoryPoints;
    private PersonalBoard gameSpace;

    private boolean whiteSpecialAbility; //remove this

    private BuyCard buyCard;//this class pecify how the player can buy the card
    private ArrayList<SpecialCard> specialCard;//addictional card created from an Leader Card ability


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
        this.buyCard = new Buy();
        this.playing = false;
    }

    public String getUsername() {
        return username;
    }

    public int getNumber(){
        return this.number;
    }

    /**
     * method for take the attribute containing the class that implement the way the player buy
     * a card, it can be Buy if is a normal operation or BuyDiscount if this player have a discount
     * to apply at the sale
     * @return
     */
    public BuyCard getBuyCard() {
        return buyCard;
    }

    /**
     * setting the way the player buy a card
     * @param buyCard
     */
    public void setBuyCard(BuyCard buyCard) {
        this.buyCard = buyCard;
    }

    /**
     * method to get the card (or cards) special of this player if he had activated
     * the special ability of a particular Leader Card
     * so this attribute can be null!!
     * @return
     */
    public ArrayList<SpecialCard> getSpecialCard() {
        return specialCard;
    }

    /**
     * invocathed this method if the player active the Leader Card that create a Special Card
     * @param specialCard
     */
    public void addSpecialCard(SpecialCard specialCard) {
        if (this.specialCard == null)
            this.specialCard = new ArrayList<>();
        this.specialCard.add(specialCard);
    }

    /**
     *
     * @return true
     * if the player is the first, else false
     */
    public boolean hasInkpot() {
        return inkpot;
    }

    public int getVictoryPoints(){
        return this.victoryPoints;
    }

    /**
     * when the game is setted a Personal Board for each player has to be created
     * then associated to each one their own
     * @param personalBoard
     */
    public void setGameSpace(PersonalBoard personalBoard){
        this.gameSpace = personalBoard;
    }

    /**
     * this attribute rapresent the position in the sequence of turn of the player this Player has
     * @param number
     */
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

    /**
     * this method return the Leader cards (2) owned by the players
     * @return
     */
    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    /**
     *
     * @param cards
     * @param chose1
     * @param chose2
     * method when the player have to choose two leader card from the four that he
     * draw from the dek
     */
    public void chooseLeaderCards(ArrayList<LeaderCard> cards, int chose1, int chose2){

        //int index1 = cards.indexOf(chose1);
        //this.leaderCards.add(cards.remove(index1));
        this.leaderCards.add(cards.remove(chose1));

        //int index2 = cards.indexOf(chose2);
        //this.leaderCards.add(cards.remove(index2));
        this.leaderCards.add(cards.remove(chose2));

    }

    /**
     * method to take the personal Board of this player
     * @return
     */
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
     * private method invocated when the game end, to set the total
     * points that this player has made
     */
    private void calculateVictoryPoints(){
        //sum of all points.. then set the attribute to it
        int points = 0;
        //get points from Development Card in Card Space
        points += this.gameSpace.getVictoryPointsFromCardSpaces();

        //get points from Leader Card
        points += this.leaderCards.get(0).getVictoryPoints();
        points += this.leaderCards.get(1).getVictoryPoints();

        //get points from PopesFavorTile and last Gold Box
        points += this.gameSpace.getFaithTrack().getAllVictoryPoints();

        this.victoryPoints = points;
    }

    /**
     * invocated the method to use the productipon Power of only Developmemnt card
     * @param developmentCards
     * @throws InvalidActionException
     */
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards) throws InvalidActionException{
        //the production powers are activated all toghether, after one gli cannot active another one
        //give input of the method the chosen card
        /* create a private method that verify if the production Power can be invocated*/
        if (verifyProductionPower(developmentCards)){
            for (DevelopmentCard card: developmentCards) {
                card.UseProductionPower(this);
            }
        }
        else
            throw new InvalidActionException("Production Power can't be invocated! Look at the Requirements!");
    }

    /**
     * verify only Development card request
     * @param developmentCards
     * @return
     */
    private boolean verifyProductionPower(ArrayList<DevelopmentCard> developmentCards){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        for (DevelopmentCard card: developmentCards) {
            requirements.addAll(card.showCostProductionPower());
        }
        resourcesOwned = this.gameSpace.getResourceManager().getResources();
        if (resourcesOwned.containsAll(requirements))
            return true;
        else
            return false;

    }

    /**
     * the player decides to invoke the power of a Development Card and the special card
     * so he ask the Board to take that from his space and active it
     */
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards, ArrayList<Resource> resources) throws InvalidActionException{
        //the production powers are activated all toghether, after one gli cannot active another one
        //give input of the method the chosen card
        /* create a private method that verify if the production Power can be invocated*/
        if (verifyProductionPower(specialCard, developmentCards)){
            int i = 0;
            for (SpecialCard card: specialCard) {
                card.useProductionPower(this, resources.get(i));
                i++;
            }
            for (DevelopmentCard card: developmentCards) {
                card.UseProductionPower(this);
            }
        }
        else
            throw new InvalidActionException("Production Power can't be invocated! Look at the Requirements!");
    }

    /**
     * this method verify if the player have in his strong box and warehouse all the resources
     * requied for the production power
     * @param developmentCards
     * @param specialCard
     * @return
     */
    private boolean verifyProductionPower(ArrayList<SpecialCard> specialCard, ArrayList<DevelopmentCard> developmentCards){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        for (SpecialCard card: specialCard) {
            //cost is a single resource
            requirements.addAll(card.getCostProductionPower());
        }
        for (DevelopmentCard card: developmentCards) {
            requirements.addAll(card.showCostProductionPower());
        }
        resourcesOwned = this.gameSpace.getResourceManager().getResources();
        if (resourcesOwned.containsAll(requirements))
            return true;
        else
            return false;

    }


    public LeaderCard chooseLeaderCardToActive(int number) {
        return this.leaderCards.get(number);
    }

    public void activeLeaderCardAbility(LeaderCard card) throws InvalidActionException {
            //LeaderCard card = chooseLeaderCardToActive();
        //method implemented by Ilaria in her local project
            //LeaderCard card =  this.leaderCards.get(wich);
        card.activeCard(this);
    }


    public void activeLeaderCardAbility(LeaderCard card,Resource choice) throws InvalidActionException {
        //method implemented by Ilaria in her local project
        card.activeCard(choice, this);
    }



    public void buyFromMarket(int position, String wich, BoardManager boardManager) throws IllegalArgumentException{
        if (wich.equals("row")){
            boardManager.getMarketStructure().rowMoveMarble(position, this);
        }
        else if (wich.equals("column")){
            boardManager.getMarketStructure().columnMoveMarble(position, this);
        }
        else
            throw new IllegalArgumentException("invalid action of buy from market!");

    }

    public void buyCard(int row, int column, BoardManager boardManager, int selectedCardSpace) throws InvalidActionException{
       this.buyCard.buyCard(row, column, boardManager, this,  selectedCardSpace);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * method invocated when the player wants to end the game
     * @return
     */
    public void endTurn(){
        setPlaying(false);
    }

    public void putResources(Resource resources){
        //i ask to put this resource in this player warehouse or strongBox
    }


}