package it.polimi.ingsw.Model.board;

import it.polimi.ingsw.Model.board.resourceManagement.ResourceManager;
import it.polimi.ingsw.Model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.Model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.Model.board.resourceManagement.WarehouseStandard;
import it.polimi.ingsw.Model.card.DevelopmentCard;

import java.util.ArrayList;

public interface PersoonalBoardInterface {
    public FaithTrack getFaithTrack();
    public Warehouse getWarehouse();
    public StrongBox getStrongbox();
    public ResourceManager getResourceManager();
    public int getVictoryPointsFromCardSpaces();
    public CardSpace getCardSpace(int i) throws IndexOutOfBoundsException;
    public ArrayList<CardSpace> getCardSpaces();
    public void invokeProductionPowerFromStrongBox(DevelopmentCard card);
}
