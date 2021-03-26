package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Model.board.PersonalBoard;

public class Player {
    private int number;
    private boolean inkpot;
    private LeaderCard[] leaderCards;
    private int victoryPoints;
    private PersonalBoard gameSpace;
    private DevelopmentCard[] handCard;

    public void setNumber(int num){};
    public int getNumber(){return 0;}
    public void chooseLeaderCards(LeaderCard[] cards){};
    public void calculateVictoryPoints(){};
    public int getVictoryPoints(){return 0;}
    public PersonalBoard getPersonalBoard(){return gameSpace;}
    public void invokesProductionPower(){};
    public void putResources(){};
    public void gameplay(){};
    public int getNumberHandCard(){return 0;}
    public void activeLeaderCardAbility(LeaderCard card){};
    public void activeLeaderCardAbility(LeaderCard card,Resource choice){};

}