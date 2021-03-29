package it.polimi.ingsw.Model.board;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import java.util.ArrayList;

public class CardSpace {

    private final int whichSpace;
    private int numberOfCards;
    private ArrayList<DevelopmentCard>cards;

    //constructor of the class
    public CardSpace(int whichSpace)
    {
        this.whichSpace = whichSpace;
        this.numberOfCards = 0;
        this.cards= new ArrayList<>();
    }


    public int getWhichSpace(){
        return whichSpace;
    }

    /*now I have the size of the List and so the number of cards*/
    public int getNumberOfCards() {
        return cards.size();
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public ArrayList<DevelopmentCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<DevelopmentCard> cards) {
        this.cards = cards;
    }

    /* Method used to take the upper card of the Card space,
     * One player can use the production power only of the upper card.
     */
    public DevelopmentCard getUpperCard(){

        /* take the card in the last position (array lenght - 1 because the first index is 0)*/
        return cards.get(getNumberOfCards()-1);
    }

    public void addCard(DevelopmentCard newCard){
        this.cards.add(newCard);
        setNumberOfCards(this.cards.size());
    }
}
