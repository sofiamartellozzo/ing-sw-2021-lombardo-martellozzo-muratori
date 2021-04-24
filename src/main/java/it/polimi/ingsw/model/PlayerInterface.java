package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.market.MarbleSpecial;

import java.util.ArrayList;

public interface PlayerInterface {
    public String getUsername();
    public int getNumber();
    public BuyCard getBuyCard();
    public void setBuyCard(BuyCard buyCard);
    public ArrayList<SpecialCard> getSpecialCard();
    public void addSpecialCard(SpecialCard specialCard);
    public boolean hasInkpot();
    public int getVictoryPoints();
    public void setGameSpace(PersonalBoard personalBoard);
    public void setNumber(int number);
    public ArrayList<LeaderCard> getLeaderCards();
    public void setLeaderCards(ArrayList<LeaderCard> card);
    public void chooseLeaderCards(ArrayList<LeaderCard> cards, int chose1, int chose2);
    public PersonalBoard getGameSpace();
    public MarbleSpecial getWhiteSpecialMarble();
    public void setWhiteSpecialMarble(MarbleSpecial whiteSpecialMarble);


    public int chooseSpecialWhiteMarble();//INUTILE


    public Resource chooseResource();
    public int calculateVictoryPoints();
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards) throws InvalidActionException;
    public void invokesProductionPower(ArrayList<DevelopmentCard> developmentCards, ArrayList<Resource> resources) throws InvalidActionException;

    public LeaderCard selectLeaderCard(int number);
    public void activeLeaderCardAbility(LeaderCard card) throws InvalidActionException;
    public void activeLeaderCardAbility(LeaderCard card,Resource choice) throws InvalidActionException;

    public ArrayList<TypeResource> buyFromMarket(int position, String wich, BoardManager boardManager) throws IllegalArgumentException, InvalidActionException;
    public ArrayList<TypeResource> getWhiteSpecialResources();

    public void buyCard(int row, int column, BoardManager boardManager, int selectedCardSpace) throws InvalidActionException;
    public boolean isPlaying();
    public void setPlaying(boolean playing);
    public void endTurn();
    public void putResources(Resource resource) throws InvalidActionException;
    public void moveResource(int depot1, int depot2) throws InvalidActionException;
    public int chooseDepot();

    public void removeLeaderCard(int card) throws InvalidActionException;
    public void increasePosition();

    public boolean checkEndGame();
}
