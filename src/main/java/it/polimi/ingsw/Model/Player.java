package it.polimi.ingsw.Model;

import it.polimi.ingsw.board.PersonalBoard;

public class Player {
    private int number;
    private boolean inkpot;
    private LeaderCard[] leaderCards;
    private int victoryPoints;
    private PersonalBoard gameSpace;
    private DevelopmentCard[] handCard;

    public void setNumber(int num){};
    public int getNumber(){};
    public void chooseLeaderCards(LeaderCard[] cards){};
    public void calculateVictoryPoints(){};
    public int getVictoryPoints(){};
    public PersonalBoard getPersonalBoard(){};
    public void invokesProductionPower(){};
    public void putResources(){};
    public void gameplay(){};
    public int getNumberHandCard(){};
    public void activeLeaderCardAbility(LeaderCard card){};
    public void activeLeaderCardAbility(LeaderCard card,Resource choice){};

}