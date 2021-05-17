package it.polimi.ingsw.model.card;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import it.polimi.ingsw.model.board.Active;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.model.board.State;
import it.polimi.ingsw.model.cardAbility.*;
import it.polimi.ingsw.utility.AnsiColors;
import it.polimi.ingsw.utility.SmallDevelopCard;

import javax.print.DocFlavor;

/* ILA */

public class LeaderCard extends Card implements Serializable {

    private  SpecialAbility specialAbility;
    private  TypeResource specialResource;
    private  ArrayList<Object> requirements;
    private  State state;
    private int cardID;

    /**
     * constructor of the class
     * @param victoryPoints
     */

    public LeaderCard(int cardID, int victoryPoints, TypeAbility typeSpecialAbility, TypeResource specialResource, ArrayList<Object> requirements) {

        super(victoryPoints);
        this.cardID = cardID;
        this.requirements = requirements;
        this.specialResource = specialResource;
        createSpecialAbility(typeSpecialAbility);
        this.state = new Inactive();

    }

    // Getter methods

    public SpecialAbility getSpecialAbility(){return specialAbility;}
    public ArrayList<Object> getRequirements(){return requirements;}
    public State getState(){return state;}
    public TypeResource getSpecialResource(){return specialResource;}

    /**
     * method to take the ID of a Leader Card (an integer number that goes from 1 to 16)
     * @return
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * auxiliary method used to create the real special ability object starting from the TypeAbility
     * @param typeSpecialAbility
     * @return
     */

    private void createSpecialAbility(TypeAbility typeSpecialAbility){
        switch (typeSpecialAbility){
            case DISCOUNT:
                this.specialAbility = new Discount(createResource(specialResource));
                break;
            case SPECIAL_DEPOT:
                this.specialAbility = new SpecialDepot(createResource(specialResource));
                break;
            case TRANSFORM_WHITE:
                this.specialAbility = new TransformWhiteMarble(createResource(specialResource));
                break;
            case ADDITIONAL_POWER:
                this.specialAbility = new AdditionalPower(createResource(specialResource));
                break;
            default:
                throw new IllegalArgumentException(" Error, typeAbility not valid! ");
        }

    }


    /**
     * auxiliary method used to create the resource using the typeResource
     * @param typeResource
     * @return
     */
    public Resource createResource(TypeResource typeResource){
        switch (typeResource){
            case COIN:
                return new Resource(Color.YELLOW);
            case STONE:
                return new Resource(Color.GREY);
            case SHIELD:
                return new Resource(Color.BLUE);
            case SERVANT:
                return new Resource(Color.PURPLE);
        }
        throw new IllegalArgumentException(" Error, typeResource not valid! ");
    }


    /**
     * method used to active the special ability of a card Leader Card with a fixed resource as
     * earn production power
     * @param player
     * @throws InvalidActionException
     */
    public void activeCard(Player player) throws InvalidActionException {
        if(player == null) throw new InvalidActionException("Error, not valid!");
        state = new Active();
    }

    /**
     * method used when the special ability is activated and create the special card,
     * so the payer has to choose the resource he wants
     * @param resource
     * @param player
     * @throws InvalidActionException
     */
    public void activeCard(Resource resource,Player player) throws InvalidActionException {
        if(player == null) throw new InvalidActionException("Error, not valid!");
        state = new Active();
        this.specialAbility.activeAbility(player);
    }

    /**
     * this method gives you the victory points of the Leader Card only if the card is active,
     * otherwise it returns zero
     * @return
     */
    @Override
    public int getVictoryPoints() {
        return this.getState().returnPoints(victoryPoints);
    }


    public String getRequirementsForCli(){

        String req = "" ;

        /*for(int i = 0; i < requirements.size(); i++) {
            if (requirements.get(i) instanceof Resource) {
                if (((Resource) requirements.get(i)).getType().equals(TypeResource.COIN)) {
                    req += AnsiColors.YELLOW_BOLD + "\uD83D\uDFE1" + AnsiColors.RESET;
                } else if (((Resource) requirements.get(i)).getType().equals(TypeResource.SERVANT)) {
                    req += AnsiColors.PURPLE_BOLD + "\uD83D\uDFE1" + AnsiColors.RESET;
                } else if (((Resource) requirements.get(i)).getType().equals(TypeResource.SHIELD)) {
                    req += AnsiColors.BLUE_BOLD + "\uD83D\uDFE1" + AnsiColors.RESET;
                } else if (((Resource) requirements.get(i)).getType().equals(TypeResource.STONE)) {
                    req += AnsiColors.BLACK_BOLD + "\uD83D\uDFE1" + AnsiColors.RESET;
                }
            } else if (requirements.get(i) instanceof SmallDevelopCard) {
                if (((SmallDevelopCard) requirements.get(i)).getColor().equals(Color.BLUE)) {
                    if (((SmallDevelopCard) requirements.get(i)).getLevel() == 0) {
                        req += AnsiColors.BLUE_BOLD + " 🟦 level: 0" + AnsiColors.RESET;
                    } else {
                        req += AnsiColors.BLUE_BOLD + " 🟦 level: "+((SmallDevelopCard) requirements.get(i)).getLevel() + AnsiColors.RESET;
                    }
                }
                if (((SmallDevelopCard) requirements.get(i)).getColor().equals(Color.YELLOW)) {
                    if (((SmallDevelopCard) requirements.get(i)).getLevel() == 0) {
                        req += AnsiColors.YELLOW_BOLD + " 🟦 level: 0" + AnsiColors.RESET;
                    } else {
                        req += AnsiColors.YELLOW_BOLD + " 🟦 level: " + ((SmallDevelopCard) requirements.get(i)).getLevel() + AnsiColors.RESET;
                    }
                }
                if (((SmallDevelopCard) requirements.get(i)).getColor().equals(Color.PURPLE)) {
                    if (((SmallDevelopCard) requirements.get(i)).getLevel() == 0) {
                        req += AnsiColors.PURPLE_BOLD + " 🟦 level: 0" + AnsiColors.RESET;
                    } else {
                        req += AnsiColors.PURPLE_BOLD + " 🟦 level: "+((SmallDevelopCard) requirements.get(i)).getLevel() + AnsiColors.RESET;
                    }
                }
                if (((SmallDevelopCard) requirements.get(i)).getColor().equals(Color.GREY)) {
                    if (((SmallDevelopCard) requirements.get(i)).getLevel() == 0) {
                        req += AnsiColors.BLACK_BOLD + " 🟦 level: 0" + AnsiColors.RESET;
                    } else {
                        req += AnsiColors.BLACK_BOLD + " 🟦 level: " + ((SmallDevelopCard) requirements.get(i)).getLevel() + AnsiColors.RESET;
                    }
                }
            }*/

        return req;
    }


    @Override
    public String toString() {
        return
                " cardID: " + cardID + "\n"+
                " specialAbility: " + specialAbility + "\n"+
                " specialResource: " + specialResource + "\n"+
                " requirements: " + getRequirementsForCli() + "\n"+
                " state: " + AnsiColors.RED_BOLD+state + AnsiColors.RESET+"\n"+
                " victoryPoints: " + AnsiColors.YELLOW_BOLD+victoryPoints+AnsiColors.RESET +"\n\n";
    }

    //METHODS USED TO SHOW THE CARD

    public String getIDtoString(){ return " ID: "+cardID+"\t\t\t\t\t\t";}
    public String getSpecialAbilitytoString(){return " specialAbility: " + specialAbility+"\t\t\t\t\t";}
    public String getSpecialResourceToString(){return " specialResource: " + specialResource +"\t\t\t\t";}
    public String getRequirementsToString(){return " requirements: " + getRequirementsForCli();}
    public String getStatetoString(){return " state: " + AnsiColors.RED_BOLD+state + AnsiColors.RESET+"\t\t\t\t";}
    public String getVPointsToString(){return " victoryPoints: " + AnsiColors.YELLOW_BOLD+victoryPoints+AnsiColors.RESET+"\t\t\t";}

    public static void main(String[] args) {

        ArrayList<Object> req = new ArrayList<>();
        req.add(new SmallDevelopCard(Color.PURPLE,0));
        req.add(new SmallDevelopCard(Color.BLUE,1));
        req.add(new SmallDevelopCard(Color.YELLOW,1));

        LeaderCard leaderCard = new LeaderCard(2,2, TypeAbility.SPECIAL_DEPOT, TypeResource.COIN,req);
        System.out.println(leaderCard.toString());
    }
}
