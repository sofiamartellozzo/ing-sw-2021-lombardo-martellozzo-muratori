package it.polimi.ingsw.Model.board;
import it.polimi.ingsw.Model.card.DevelopmentCard;
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

    /*now I have the size of the List and so the number of cards*/
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

    /* Method used to take the upper card of the Card space,
     * One player can use the production power only of the upper card.
     */

    public DevelopmentCard getUpperCard() throws IllegalArgumentException{
        /* take the card in the last position (array lenght - 1 because the first index is 0)*/
        if(cards.size()==0) throw new IllegalArgumentException("Error you don't have any card in this space!");
        else return cards.get(getNumberOfCards()-1);
    }

     public void addCard(DevelopmentCard newCard) throws IllegalArgumentException{
        if(newCard==null) throw new IllegalArgumentException("Error, card not valid!");

        /*if you don't have any card you can add it without controls*/
        else if(cards.size()==0){
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }

        /*you can add the card in the card space only if its level is one bigger than the prevoius one*/
        else if(newCard.getlevel()==(cards.get(getNumberOfCards()-1).getlevel())+1) {
            this.cards.add(newCard);
            setNumberOfCards(this.cards.size());
        }
        else throw new IllegalArgumentException("Error, You can't put the resource here!");
    }

    /*in this method I calculate the sum of Victory points in a CardSpace*/
    public int getTotVictoryPoints ()
    {
        int TotPoints = 0;
        for (DevelopmentCard c : cards) {
            TotPoints += c.getVictoryPoints();
        }
        return TotPoints;
    }
}
