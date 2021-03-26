package it.polimi.ingsw.Model.Card;
import java.util.HashMap;
import it.polimi.ingsw.Model.SpecialAbility;

public class LeaderCard extends Card {

    private boolean hidden;
    private SpecialAbility SpecialAbility;
    private HashMap<Integer,Object> requirements;

    public boolean getHiddenValue(){return hidden;}
    public void setHiddenValue(boolean hidden){this.hidden=hidden;}

    public SpecialAbility getSpecialAbility(){return SpecialAbility;}
    public void setSpecialAbility(SpecialAbility SpecialAbility){this.SpecialAbility=SpecialAbility;}

    public HashMap<Integer,Object> getRequirements(){return requirements;}
    public void setRequirements(HashMap<Integer,Object> requirements){this.requirements=requirements;}

    @Override
    public int getVicroyPoints() {
        return super.VictoryPoints;
    }

    @Override
    public void setVictoryPoints(int VictoryPoints) {
     super.VictoryPoints=VictoryPoints;
    }
}
