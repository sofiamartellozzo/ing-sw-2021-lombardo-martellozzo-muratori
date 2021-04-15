package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.card.DevelopmentCard;
import java.util.ArrayList;

/* ILA */

public class CardSpace {

    private final int whichSpace;
    private int numberOfCards;
    private ArrayList<DevelopmentCard>cards;

    /**
     * constructor
     * @param whichSpace
     */

    public CardSpace(int whichSpace)
    {
        this.whichSpace = whichSpace;
        this.numberOfCards = 0;
        this.cards= new ArrayList<>();
    }

    // Getter methods
    /**
     * now I have the size of the List and so the number of cards
     * @return
     */
    public int getNumberOfCards() {
        return cards.size();
    }
    public ArrayList<DevelopmentCard> getCards() {
        return cards;
    }
    public int getWhichSpace(){
        return whichSpace;
    }


    // Setter methods
    private void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
    public void setCards(ArrayList<DevelopmentCard> cards) { this.cards = cards; }

    /**
     * Method used to take the upper card of the Card space,
     *  One player can use the production power only of the upper card.
     * @return
     * @throws IllegalArgumentException
     */

    public DevelopmentCard getUpperCard() throws IllegalArgumentException{
        /* take the card in the last position (array lenght - 1 because the first index is 0)*/
        if(cards.size()==0) throw new IllegalArgumentException("Error you don't have any card in this space!");
        else return cards.get(getNumberOfCards()-1);
    }

    /**
     * method used to add a development card to a card space and after increase the size of it,
     * but if you already have a card on it, you have to check that the level is 1 higher than the previous one
     * @param newCard
     * @throws IllegalArgumentException
     */
    public void addCard(DevelopmentCard newCard) throws IllegalArgumentException {
        if(newCard==null) throw new IllegalArgumentException("Error, card not valid!");

        /*if you don't have any card you can add it without controls, but it has to be level 1 !! */
        else if((cards.size()==0)&&(newCard.getlevel()==1)){
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }
        /*you can add the card in the card space only if its level is one bigger than the previous one*/
        else if(newCard.getlevel()==(cards.get(getNumberOfCards()-1).getlevel())+1) {
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }
        else throw new IllegalArgumentException("Error, You can't put the Card here!");
    }

    /**
     * in this method I calculate the sum of Victory points in a CardSpace
     * @return
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
}
