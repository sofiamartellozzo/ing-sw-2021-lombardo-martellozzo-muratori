package it.polimi.ingsw.Model.card;
import java.util.ArrayList;

/* ILA */

/** SINGLETON: because I have to instance this class only once,
and the next time
he has to give me back the same class */

public class LeaderCardDeck {

    private ArrayList<LeaderCard>cards;
    private static LeaderCardDeck leaderCardDeck = null;

    /**
     * constructor of the class
     * @param cards
     */

    private LeaderCardDeck(ArrayList<LeaderCard>cards){
        this.cards=cards;
    }

    public static LeaderCardDeck getInstance(ArrayList<LeaderCard>cards)
    {
        if(leaderCardDeck == null)
        {
            leaderCardDeck = new LeaderCardDeck(cards);
        }
        return leaderCardDeck;
    }

    public ArrayList<LeaderCard> getCards(){return cards;}
    //public void setCards(ArrayList<LeaderCard>cards){this.cards=cards;}

    /**
     * method used to have the number of the Cards of a Deck,
     * so when you give to a player 4 of them, the number decrease
     * @return cards.size()
     */
    public int getNumberOfCards(){ return cards.size(); }

    /**
     * method used to remove the cards from the deck composed by 16 leader Card
     */
    public void remove(ArrayList<LeaderCard> card){cards.removeAll(card);}
}
