package it.polimi.ingsw.model.board;


import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.cardAbility.AdditionalPower;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents player's personal board.
 * It is composed by the "faithTrack" and the Strongbox and the Warehouse in the "resourceManager".
 * The "cardSpaces" attribute refers to the 3 card spaces of the board.
 */
public class PersonalBoard implements PersonalBoardInterface,Serializable {
    private final FaithTrack faithTrack;
    private final ResourceManager resourceManager;
    private final ArrayList<CardSpace> cardSpaces;
    /**
     * Constructor
     * @param faithTrack -> The reference to the FaithTrack
     * @param resourceManager -> The reference to the StrongBox and the Warehouse
     * @param cardSpaces -> The reference to the 3 card spaces
     */
    public PersonalBoard(FaithTrack faithTrack, ResourceManager resourceManager, ArrayList<CardSpace> cardSpaces) {
        this.faithTrack = faithTrack;
        this.resourceManager = resourceManager;
        this.cardSpaces = cardSpaces;
    }

    /**
     * Getter Method
     * @return -> The "faithTrack"
     */
    @Override
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /**
     * Getter Method
     * @return -> The "Warehouse" of the "resourceManger"
     */
    @Override
    public Warehouse getWarehouse() {
        return resourceManager.getWarehouse();
    }

    /**
     * Getter Method
     * @return -> The "StrongBox" of the "resourceManager"
     */
    @Override
    public StrongBox getStrongbox() {
        return resourceManager.getStrongBox();
    }

    /**
     * Getter Method
     * @return -> The "resourceManager"
     */
    @Override
    public ResourceManager getResourceManager() {
        return resourceManager;
    }


    /**
     * Getter Method
     * @return -> The 3 "cardSpaces"
     */
    @Override
    public ArrayList<CardSpace> getCardSpaces() {
        return cardSpaces;
    }


    /**
     * @param i -> Which card space
     * @return -> The card space you want
     * @throws IndexOutOfBoundsException -> If the player requests a not-existing card space
     */
    @Override
    public CardSpace getCardSpace(int i) throws IndexOutOfBoundsException{
        if ((i>2)||(i<0)){
            throw new IndexOutOfBoundsException("There are maximum 3 card spaces! Not more");
        }
        else{
            return this.cardSpaces.get(i);
        }
    }

    /**
     * @return -> All the development card of the 3 card spaces as an ArrayList<DevelopmentCard>
     */
    @Override
    public ArrayList<DevelopmentCard> getAllCards(){
        ArrayList<DevelopmentCard> allCards = new ArrayList<>();
        for (CardSpace space: getCardSpaces()){
            allCards.addAll(space.getCards());
        }
        return allCards;
    }

    /**
     * @param i -> Which space
     * @return -> All the DevelopmentCards of the space given.
     */
    @Override
    public ArrayList<DevelopmentCard> getAllCardOfOneSpace(int i){
        return new ArrayList<>(getCardSpace(i).getCards());
    }


    // This method refers to the Basic Production Power which is
    // equipped to every Personal Board
    public void invokeProductionPowerFromStrongBox(DevelopmentCard card){
        //Dev Card Input needed
        //card.useProductionPower();
    }

    /**
     * @return -> All the victory points of the 3 card spaces.
     */
    @Override
    public int getVictoryPointsFromCardSpaces(){
        int points = 0;
        for (CardSpace cardSpace: getCardSpaces()){
            points += cardSpace.getTotVictoryPoints();
        }
        return points;
    }

    /**
     * @param player -> The reference to the player
     * @return -> All activatable card spaces based on the resources of the resource manager as an ArrayList<Integer>
     */
    @Override
    public ArrayList<Integer> getActivatableCardSpace(PlayerInterface player) {
        ArrayList<Integer> activatableCardSpace = new ArrayList<>();
        Warehouse warehouse = resourceManager.getWarehouse();
        StrongBox strongBox = resourceManager.getStrongBox();
        //Basic Production Power
        if(warehouse.getContent().size()>=2||strongBox.getContent().size()>=2){
            activatableCardSpace.add(0);
        }
        //Normal Production Power
        for(CardSpace cardSpace:cardSpaces){
            if(!cardSpace.getCards().isEmpty()) {
                boolean activatable = true;
                for (TypeResource type : cardSpace.getCostPPTypeUpperCard()) {
                    if ((resourceManager.countResource(warehouse.getContent(), new Resource(type)) < cardSpace.getNumberCostPP(type)) && (resourceManager.countResource(strongBox.getContent(), new Resource(type)) < cardSpace.getNumberCostPP(type))) {
                        activatable = false;
                    }
                }
                if (activatable) {
                    activatableCardSpace.add(cardSpace.getWhichSpace());
                }
            }
        }
        //Ability Production Power
        int countLeaderCard=0;

        if(player.getLeaderCards()!=null) {
            for (LeaderCard leaderCard : player.getLeaderCards()) {
                if (leaderCard.getState() instanceof Active && leaderCard.getSpecialAbility() instanceof AdditionalPower) {
                    countLeaderCard++;
                }
            }

            if (player.getSpecialCard()!=null && countLeaderCard == player.getSpecialCard().size()) {
                for (int i = 0; i < player.getSpecialCard().size(); i++) {
                    SpecialCard specialCard = player.getSpecialCard().get(i);
                    if (resourceManager.countResource(warehouse.getContent(), specialCard.getCostProductionPower().get(0)) >= 1 || resourceManager.countResource(strongBox.getContent(), specialCard.getCostProductionPower().get(0)) >= 1) {
                        activatableCardSpace.add(4 + i);
                    }
                }
            }
        }
        return activatableCardSpace;
    }


}
