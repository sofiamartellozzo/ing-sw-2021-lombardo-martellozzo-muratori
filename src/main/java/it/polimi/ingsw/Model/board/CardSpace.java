package it.polimi.ingsw.Model.board;
import it.polimi.ingsw.Model.card.DevelopmentCard;
import java.util.ArrayList;

public class CardSpace {

    private int whichSpace;
    private int numberOfCards;
    private ArrayList<DevelopmentCard>cards;
    private DevelopmentCard selectedCard;

    //constructor of the class
    public CardSpace(int whichSpace,int numberOfCards,ArrayList<DevelopmentCard>cards)
    {
        this.whichSpace=whichSpace;
        this.numberOfCards=numberOfCards;
        this.cards=cards;
    }

    public DevelopmentCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(DevelopmentCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public int getWhichSpace(){
        return whichSpace;
    }

    public void setWhichSpace(int whichSpace) {
        this.whichSpace = whichSpace;
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
}
