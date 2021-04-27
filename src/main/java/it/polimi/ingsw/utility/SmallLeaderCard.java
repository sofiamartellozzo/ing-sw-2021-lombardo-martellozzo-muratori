package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.cardAbility.TypeAbility;

import java.util.ArrayList;

public class SmallLeaderCard {

    private TypeResource specialResource;
    private ArrayList<Object> requirements;
    private int cardID;
    private TypeAbility typeAbility;
    private int victoryPoints;

    public SmallLeaderCard(TypeResource specialResource, ArrayList<Object> requirements, int cardID, TypeAbility typeSpecialAbility, int victoryPoints) {
        this.specialResource = specialResource;
        this.requirements = requirements;
        this.cardID = cardID;
        this.typeAbility = typeSpecialAbility;
        this.victoryPoints = victoryPoints;
    }

    public TypeAbility getTypeAbility() {
        return typeAbility;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public TypeResource getSpecialResource() {
        return specialResource;
    }

    public ArrayList<Object> getRequirements() {
        return requirements;
    }

    public int getCardID() {
        return cardID;
    }

}
