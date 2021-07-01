package it.polimi.ingsw.model.board;
import it.polimi.ingsw.exception.CardSpaceException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Refers to the card space in the personal board.
 * The "whichSpace" attribute indicates if it is the 1st, the 2nd or the 3rd.
 * The "numberOfCards" attribute indicates how many cards are in.
 * The "cards" attribute contains the reference to the DevelopmentCard contained by the card space.
 */
public class CardSpace implements Serializable{

    private final int whichSpace;
    private int numberOfCards;
    private ArrayList<DevelopmentCard>cards;

    /**
     * Constructor
     * Initialize "cards", set "numberOfCards" to 0
     * @param whichSpace -> If it is the 1st, the 2nd or the 3rd card space.
     */
    public CardSpace(int whichSpace)
    {
        this.whichSpace = whichSpace;
        this.cards= new ArrayList<>();
    }

    /**
     * Getter Method
     * @return -> How many cards are in the card space
     */
    public int getNumberOfCards() {
        return cards.size();
    }

    /**
     * Getter Method
     * @return -> The cards as an ArrayList<DevelopmentCard>
     */
    public ArrayList<DevelopmentCard> getCards() {
        return cards;
    }

    /**
     * Getter method
     * @return -> "whichSpace"
     */
    public int getWhichSpace(){
        return whichSpace;
    }

    /**
     * Setter Method
     * @param numberOfCards -> The new value of how many cards are in
     */
    private void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    /**
     * Setter Method
     * @param cards -> The new cards that the card space must contain
     */
    public void setCards(ArrayList<DevelopmentCard> cards) { this.cards = cards; }

    /**
     * After checking:
     * - The card space contains at least one card.
     * @return -> The card on the top of the card space
     * @throws IllegalArgumentException -> If one of the conditions is not respected
     */
    public DevelopmentCard getUpperCard() throws IllegalArgumentException{
        /* take the card in the last position (array length - 1 because the first index is 0)*/
        if(cards.size()==0) throw new IllegalArgumentException("Error you don't have any card in this space!");
        else return cards.get(getNumberOfCards()-1);
    }

    /**
     * Add a DevelopmentCard in the card space, increasing the "numberOfCards".
     * After checking:
     * - The "newCard" is not null
     * - The "newCard" must be of the next level if there is already one in the card space
     * - If the card space is empty, the "newCard" level must 1.
     * @param newCard -> The new card which you want to insert
     * @throws IllegalArgumentException -> If one of the conditions is not respected
     */
    public void addCard(DevelopmentCard newCard) throws CardSpaceException {
        if(newCard==null) throw new IllegalArgumentException("Error, card not valid!");

        /*if you don't have any card you can add it without controls, but it has to be level 1 !! */
        else if((cards.size()==0)&&(newCard.getLevel()==1)){
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }
        /*you can add the card in the card space only if its level is one bigger than the previous one*/
        else if(cards.size()>0 && newCard.getLevel()==(cards.get(getNumberOfCards()-1).getLevel())+1) {
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }
        else throw new CardSpaceException("Error, You can't put the Card here!");
    }

    /**
     * @return -> All the victory points of each card contained by the card space.
     */
    public int getTotVictoryPoints ()
    {
        int TotPoints = 0;

        if (cards.size() == 0)
        {
           return 0;
        }
        else for (DevelopmentCard c : cards) {
             TotPoints += c.getVictoryPoints();
        }
        return TotPoints;
    }

    /**
     * Which type of resource needs to pay without repeating it.
     * Example: COST -> SHIELD, SHIELD, COIN, SERVANT, SERVANT
     *          RETURN -> SHIELD, COIN, SERVANT
     * @return -> The result is returned as an ArrayList<TypeResource>
     */
    public ArrayList<TypeResource> getCostPPTypeUpperCard(){
        ArrayList<TypeResource> contentType = new ArrayList<>();
        for(Resource resource: getUpperCard().showCostProductionPower()){
            if(!contentType.contains(resource.getType())){
                contentType.add(resource.getType());
            }
        }
        return contentType;
    }

    /**
     * @param type -> Which resource to count
     * @return -> How many resource of "type" the player needs to pay
     */
    public int getNumberCostPP(TypeResource type){
        return (int) getUpperCard().showCostProductionPower().stream().filter(r -> r.getType().equals(type)).count();
    }
}
