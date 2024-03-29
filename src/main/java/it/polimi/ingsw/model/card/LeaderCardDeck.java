package it.polimi.ingsw.model.card;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * deck composed by 16 leader cards divided in 4 types of special Ability
 */
public class LeaderCardDeck implements Serializable {

    private ArrayList<LeaderCard>cards;
    private static LeaderCardDeck leaderCardDeck = null;

    /**
     * constructor of the class
     * @param cards --> leader cards
     */

    public LeaderCardDeck(ArrayList<LeaderCard>cards){
        this.cards=cards;
    }

    public ArrayList<LeaderCard> getCards(){return cards;}


    /**
     * method used to have the actual number of the Cards in the Leader Deck,
     * so when you give to a player 4 of them, the number decreases
     * @return cards.size()
     */
    public int getNumberOfCards(){ return cards.size(); }

    /**
     * method used to remove the cards that a player chooses
     * from the deck composed by 16 leader Card
     */
    public void remove(ArrayList<LeaderCard> card){cards.removeAll(card);}


    /**
     * method used to return only the card from the Deck with a specific Id (if present),
     * otherwise it returns an exception
     * @param IdCard --> id of the card
     * @return --> corresponding Leader Card
     * @throws IllegalArgumentException
     */
    public LeaderCard getLeaderCardById (int IdCard) throws IllegalArgumentException {
        for (LeaderCard card: cards) {
            if (card.getCardID() == IdCard)
            {
                return card;
            }

        }
        throw new IllegalArgumentException(" Error, that Id card is not present!");
    }

}
