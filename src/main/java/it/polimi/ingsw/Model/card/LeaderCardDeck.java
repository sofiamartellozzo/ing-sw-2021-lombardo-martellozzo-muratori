package it.polimi.ingsw.Model.card;
import java.util.ArrayList;

public class LeaderCardDeck {

    private ArrayList<LeaderCard>cards;

    //constructor of the class
    public LeaderCardDeck(ArrayList<LeaderCard>cards){
        this.cards=cards;
    }

    public ArrayList<LeaderCard> getCards(){return cards;}
    public void setCards(ArrayList<LeaderCard>cards){this.cards=cards;}

    // method used to remove a card from the deck composed by 16 leader Card
    public void remove(LeaderCard card){cards.remove(card);}
}
