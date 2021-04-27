package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public interface PersonalBoardInterface {
    public FaithTrack getFaithTrack();
    public Warehouse getWarehouse();
    public StrongBox getStrongbox();
    public ResourceManager getResourceManager();
    public int getVictoryPointsFromCardSpaces();
    public CardSpace getCardSpace(int i) throws IndexOutOfBoundsException;
    public ArrayList<DevelopmentCard> getAllCards();
    public ArrayList<DevelopmentCard> getAllCardOfOneSpace(int i);
    public ArrayList<CardSpace> getCardSpaces();
    public void invokeProductionPowerFromStrongBox(DevelopmentCard card);
    public ArrayList<Integer> getActivatableCardSpace(Player player);
}
