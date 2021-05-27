package it.polimi.ingsw.model;
import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.market.MarbleSpecial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * SOFI*/

public class Player implements PlayerInterface, Serializable {

    protected String username;
    protected int number;
    private boolean inkpot;

    private boolean playing;
    private boolean disconnected;

    protected ArrayList<LeaderCard>  leaderCards;
    protected int victoryPoints;
    private PersonalBoard gameSpace;

    protected MarbleSpecial whiteSpecialMarble;

    protected BuyCard buyCard;//this class specify how the player can buy the card
    protected ArrayList<SpecialCard> specialCard;//addictional card created from an Leader Card ability



    /**
     *
     * @param username
     * constructor of the class, it set the attribute inkpot with default
     */
    public Player(String username) {
        this.username = username;
        this.victoryPoints = 0;
        this.inkpot = false;
        this.buyCard = new Buy();
        this.playing = false;
        this.disconnected = false;
        this.leaderCards = new ArrayList<>();
    }



    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int getNumber(){
        return this.number;
    }

    /**
     * method for take the attribute containing the class that implement the way the player buy
     * a card, it can be Buy if is a normal operation or BuyDiscount if this player have a discount
     * to apply at the sale
     * @return
     */
    @Override
    public BuyCard getBuyCard() {
        return buyCard;
    }

    /**
     * setting the way the player buy a card
     * @param buyCard
     */
    @Override
    public void setBuyCard(BuyCard buyCard) {
        this.buyCard = buyCard;
    }

    /**
     * method to get the card (or cards) special of this player if he had activated
     * the special ability of a particular Leader Card
     * so this attribute can be null!!
     * @return
     */
    @Override
    public ArrayList<SpecialCard> getSpecialCard() {
        return specialCard;
    }

    /**
     * invocathed this method if the player active the Leader Card that create a Special Card
     * @param specialCard
     */
    @Override
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
    @Override
    public boolean hasInkpot() {
        return inkpot;
    }

    @Override
    public int getVictoryPoints(){
        return this.victoryPoints;
    }

    /**
     * when the game is setted a Personal Board for each player has to be created
     * then associated to each one their own
     * @param personalBoard
     */
    @Override
    public void setGameSpace(PersonalBoard personalBoard){
        this.gameSpace = personalBoard;
    }

    /**
     * this attribute rapresent the position in the sequence of turn of the player this Player has
     * @param number
     */
    @Override
    public void setNumber(int number) {
        this.number = number;
        setInkpot();
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
    @Override
    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    /**
     * method used in the initialization of the game afret the player
     * chose the two leader card, then store it in his attribute
     * @param card the Leader Card chosen
     */
    @Override
    public void setLeaderCards(ArrayList<LeaderCard> card){
        for (LeaderCard c: card){
            this.leaderCards.add(c);
        }
    }

    /**
     *
     * @param cards
     * @param chose1
     * @param chose2
     * method when the player have to choose two leader card from the four that he
     * draw from the dek
     */
    @Override
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
     * @return Personal Board
     */
    @Override
    public PersonalBoard getGameSpace() {
        return gameSpace;
    }

    /**
     * return the special White Marble that make different for the player
     * buy from the market
     * @return
     */
    @Override
    public MarbleSpecial getWhiteSpecialMarble() {
        return whiteSpecialMarble;
    }

    /**
     * setting this White marble when created from the special ability of a Leader Card
     * @param whiteSpecialMarble
     */
    @Override
    public void setWhiteSpecialMarble(MarbleSpecial whiteSpecialMarble) {
        this.whiteSpecialMarble = whiteSpecialMarble;
    }

    @Override
    public int chooseSpecialWhiteMarble(){
        //we decided he choose the second one, this method is called only if there are 2 special marble
        return 1;
    }

    /**
     * method for when is asked to the player to choose a resource
     * @return the Resource choose
     */
    @Override
    public Resource chooseResource(){
        return new Resource(Color.YELLOW);
    }

    /**
     * method invocated when the game end, to set the total
     * points that this player has made
     * in order to decide the winner
     */
    @Override
    public int calculateVictoryPoints(){
        //sum of all points.. then set the attribute to it
        int points = 0;
        //get points from Development Card in Card Space
        points += gameSpace.getVictoryPointsFromCardSpaces();

        //get points from Leader Card
        for (LeaderCard leaderCard: this.leaderCards){
            points += leaderCard.getVictoryPoints();
        }

        //get points from PopesFavorTile and last Gold Box
        points += gameSpace.getFaithTrack().getAllVictoryPoints();

        //get points from the count of the Resources owned by the player
        points += gameSpace.getResourceManager().getVictoryPoints();

        victoryPoints = points;
        return points;
    }

    /**
     * invocated the method to use the productipon Power of only Developmemnt card
     * @param developmentCards
     * @throws InvalidActionException
     */
    @Override
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards) throws InvalidActionException{
        //the production powers are activated all toghether, after one gli cannot active another one
        //give input of the method the chosen card
        /* create a private method that verify if the production Power can be invocated*/
        String where = verifyProductionPower(developmentCards);
        if (!where.equals("None")){
            for (DevelopmentCard card: developmentCards) {
                card.useProductionPower(this, where);
            }
        }
        else
            throw new InvalidActionException("Production Power can't be invocated! Look at the Requirements!");
    }

    /**
     * verify only Development card request, at first if the cost can be payed from the
     * resources stored in the wherouse if not verify if in the Strong Box there are enought
     * @param developmentCards
     * @return where to take the resources for active the ability as a String
     */
    private String verifyProductionPower(ArrayList<DevelopmentCard> developmentCards){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        for (DevelopmentCard card: developmentCards) {
            requirements.addAll(card.showCostProductionPower());
        }
        resourcesOwned = this.gameSpace.getResourceManager().getWarehouse().getContent();
        if (resourcesOwned.containsAll(requirements))
            return "Whatehouse";
        else{
            resourcesOwned = this.gameSpace.getResourceManager().getStrongBox().getContent();
            if (resourcesOwned.containsAll(requirements))
                return "StrongBox";
            else
                return "None";
        }
    }

    /**
     * the player decides to invoke the power of a Development Card and the special card
     * so he ask the Board to take that from his space and active it
     */
    @Override
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards, ArrayList<Resource> resources) throws InvalidActionException{
        //the production powers are activated all toghether, after one gli cannot active another one
        //give input of the method the chosen card
        /* create a private method that verify if the production Power can be invocated*/
        String where = verifyProductionPower(specialCard, developmentCards);
        if (!where.equals("None")){
            int i = 0;
            for (SpecialCard card: specialCard) {
                card.useProductionPower(this, resources.get(i), where);
                i++;
            }
            for (DevelopmentCard card: developmentCards) {
                card.useProductionPower(this, where);
            }
        }
        else
            throw new InvalidActionException("Production Power can't be invocated! Look at the Requirements!");
    }

    /**
     * this method verify if the player have in his strong box and warehouse all the resources
     * requied for the production power, at first if the cost can be payed from the
     *      * resources stored in the wherouse if not verify if in the Strong Box there are enought
     * @param developmentCards
     * @param specialCard
     * @return where to take the resources for active the ability as a String
     */
    private String verifyProductionPower(ArrayList<SpecialCard> specialCard, ArrayList<DevelopmentCard> developmentCards){
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> resourcesOwned = new ArrayList<>();
        for (SpecialCard card: specialCard) {
            //cost is a single resource
            requirements.addAll(card.getCostProductionPower());
        }
        for (DevelopmentCard card: developmentCards) {
            requirements.addAll(card.showCostProductionPower());
        }
        resourcesOwned = this.gameSpace.getResourceManager().getWarehouse().getContent();
        if (resourcesOwned.containsAll(requirements))
            return "Wharehouse";
        else{
            resourcesOwned = this.gameSpace.getResourceManager().getStrongBox().getContent();
            if (resourcesOwned.containsAll(requirements))
                return "StrongBox";
            else
                return "None";
        }
    }


    /**
     * select the leader card to active
     * @param number
     * @return  the choosen Leader Card owned from the player himself
     */
    @Override
    public LeaderCard selectLeaderCard(int number) {
        if (leaderCards.get(0).getCardID() == number){
            return leaderCards.get(0);
        }
        else{
            return leaderCards.get(1);
        }
    }

    /**
     * activated the Leader card, handle to produce his ability
     * @param card
     * @throws InvalidActionException
     */
    @Override
    public void activeLeaderCardAbility(LeaderCard card) throws InvalidActionException {
        ArrayList<DevelopmentCard> allDevelopmentCards = this.getGameSpace().getAllCards();
        //verify the cost
        if(card.getState() instanceof Inactive){
            //If true, checking the requirements
            ArrayList<Object> requirements = card.getRequirements();
            ArrayList<Resource> requiredResources = new ArrayList<>();
            //If at the end the counter will be 0, all requirements are satisfied
            int counter = requirements.size();
            for (int i = 0; i < requirements.size(); i++) {
                Object[] keys = ((LinkedTreeMap) requirements.get(i)).keySet().toArray();
                Object[] values = ((LinkedTreeMap) requirements.get(i)).values().toArray();
                Color color = null;
                for (int j = 0; j < keys.length; j++) {
                    String key = (String) keys[j];
                    if (key.equals("color")) {
                        String value = (String) values[j];
                        switch (value) {
                            case "GREEN":
                                color = Color.GREEN;
                                break; //Color -> Card
                            case "BLUE":
                                color = Color.BLUE;
                                break; //Color -> Card/Resource
                            case "YELLOW":
                                color = Color.YELLOW;
                                break;//Color -> Card/Resource
                            case "PURPLE":
                                color = Color.PURPLE;
                                break;//Color -> Card/Resource
                            case "GREY":
                                color = Color.GREY;
                                break;//Color -> Resource
                        }
                    } else if (key.equals("typeResource")) {
                        String value = (String) values[j];
                        //Adding the resource to count at the end
                        switch (value) {
                            case "SHIELD":
                                requiredResources.add(new Resource(TypeResource.SHIELD));
                                break;
                            case "COIN":
                                requiredResources.add(new Resource(TypeResource.COIN));
                                break;
                            case "STONE":
                                requiredResources.add(new Resource(TypeResource.STONE));
                                break;
                            case "SERVANT":
                                requiredResources.add(new Resource(TypeResource.SERVANT));
                                break;
                        }
                    } else if (key.equals("level")) {
                        //The requirements it's a card
                        Double value = (Double) values[j];
                        for (int k = 0; k < allDevelopmentCards.size(); k++) {
                            DevelopmentCard developmentCard = allDevelopmentCards.get(k);
                            if ((developmentCard.getColor().equals(color) && ((value == 0) || (value != 0 && developmentCard.getlevel() == value)))) {
                                allDevelopmentCards.remove(k);
                                counter--;
                            }
                        }
                    }
                }
            }
            if (requiredResources.size() > 0 && this.getGameSpace().getResourceManager().checkEnoughResources(requiredResources)) {
                counter = 0;
            }
            if(counter==0){
                card.activeCard(this);
            }else{
                throw new InvalidActionException("This leader card can't be activated!");
            }
        }else{
            throw new InvalidActionException("This leader card is already activated");
        }

    }

    /**
     * same as above but in this case the special Ability requires the resource that the player want
     * to recive with this ability
     * @param card
     * @param choice
     * @throws InvalidActionException
     */
    @Override
    public void activeLeaderCardAbility(LeaderCard card,Resource choice) throws InvalidActionException {
        //card.activeCard(choice, this);
    }


    /**
     * method invocated from the player when he want to buy from the market, so he have to choose
     * a row or a column to take all the Marble ad so the Resources that them provide
     * @param position  in the Market Structure
     * @param wich  if he choose a row or a column
     * @param boardManager   to reach the Market, that is common for all players
     * @throws IllegalArgumentException
     * @return
     */
    @Override
    public ArrayList<TypeResource> buyFromMarket(int position, String wich, BoardManager boardManager) throws IllegalArgumentException, InvalidActionException{
        ArrayList<TypeResource> resourcesFromMarket = null;
        if (wich.equals("row")){
            resourcesFromMarket = boardManager.getMarketStructure().rowMoveMarble(position, this);
        }
        else if (wich.equals("column")){
            resourcesFromMarket = boardManager.getMarketStructure().columnMoveMarble(position, this);
        }
        else
            throw new IllegalArgumentException("invalid action of buy from market!");

        return resourcesFromMarket;
    }

    /**
     * method called if the player has 2 white special marble, so when invocated
     * by the market he have to choose one of them
     * @return
     */
    @Override
    public ArrayList<TypeResource> getWhiteSpecialResources() {
        ArrayList<TypeResource> possibleResources = new ArrayList<>();
        possibleResources.add(whiteSpecialMarble.getAbility().get(0).getResource().getType());
        possibleResources.add(whiteSpecialMarble.getAbility().get(1).getResource().getType());
        return possibleResources;
    }

    /**
     * method invocated whenever the player whant to buy a Development Card
     * so the input he have to provide are
     * @param row   of the position of the card to buy in the table
     * @param column   of the position of the card to buy in the table
     * @param boardManager   the board manager to reach the Development Card Table (common for all players)
     * @param selectedCardSpace   wich Card Space in the Personal Board the Player choose to put the new card
     * @throws InvalidActionException
     */
    @Override
    public void buyCard(int row, int column, BoardManager boardManager, int selectedCardSpace) throws InvalidActionException{
       buyCard.buyCard(row, column, boardManager, this,  selectedCardSpace);
    }


    /**
     * method to check if the player is actually playing or not,
     * he has an attribute boolean that is set true when his turn starts
     * when he finish his turn this attribute turned false
     * @return --> true if the player is actually playing
     */
    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * set the boolean paramether to see if the player is actually playing
     * or not
     * @param playing-> true at the start of a Turn
     */
    @Override
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    @Override
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    /**
     * method invocated when the player wants to end the game
     * @return
     */
    @Override
    public void endTurn(){
        setPlaying(false);
    }

    @Override
    public void putResources(Resource resource) throws InvalidActionException{
        //i ask to put this resource in this player warehouse

        /*we supposed that if the player is able to put the resource only moving two (or more) resources
        * he just did it, so the check is to verify if at the end he can store this resource
        * or not; in second case the resource is throw away and the other player move +1 their faith marker */
        if (this.getGameSpace().getResourceManager().getWarehouse().checkAvailableDepot(resource)){
            this.getGameSpace().getResourceManager().getWarehouse().addResource(resource, chooseDepot());
        }
        else{
            //the other players move +1 their faith market

        }
    }

    @Override
    public void moveResource(int depot1, int depot2) throws InvalidActionException{
        this.getGameSpace().getResourceManager().getWarehouse().moveResource(depot1,depot2
        );
    }

    @Override
    public int chooseDepot(){
        return 1;
    }

    /**
     * discard a Leader Card implies that the faith track incrise of 1 his position
     * @param card
     */
    @Override
    public void removeLeaderCard(LeaderCard card) throws InvalidActionException{
        if (card.getState() instanceof Inactive){
            this.leaderCards.remove(card);
            this.getGameSpace().getFaithTrack().increasePosition();
        }
        else
            throw new InvalidActionException("You can't remove a Leader Card altready active!");
    }

    /**
     * increase the position of this player
     */
    @Override
    public void increasePosition() {
        this.gameSpace.getFaithTrack().increasePosition();
        System.out.println(gameSpace.getFaithTrack().getPositionFaithMarker());
    }


    @Override
    public boolean checkEndGame(){
        if ((gameSpace.getFaithTrack().getPositionFaithMarker()==24)||(gameSpace.getAllCards().size()==7)){
            return true;
        }
        else
            return false;
    }

    /**
     * @return -> HashMap with which and what LeaderCard is activatable
     */
    public HashMap<Integer, Boolean> getActivableLeaderCard(){
        HashMap<Integer,Boolean> activatableLeaderCard= new HashMap<Integer,Boolean>();
        for(int i=0;i<leaderCards.size();i++){
            if(leaderCards.get(i).getState() instanceof Inactive) {
                activatableLeaderCard.put(i, false);
            }else{
                activatableLeaderCard.put(i,true);
            }
        }
        return activatableLeaderCard;
    }
}