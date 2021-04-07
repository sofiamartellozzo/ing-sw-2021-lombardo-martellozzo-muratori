package it.polimi.ingsw.Model.cardAbility;
import it.polimi.ingsw.Exception.InvalidActionException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;

/**
 * GIANLUCA
 * It refers to the Special Ability supplied by Leader Card
 * Attributes:
 * active -> a boolean to indicate if it is "active" or "inactive"
 * type -> it refers to the resource on which the ability works
 */
public abstract class SpecialAbility {
    private boolean active;
    private final Resource resource;

    /**
     * Constructor: active is always "false"ù
     * then the setter method can set it in "true".
     * @param resource
     */
    public SpecialAbility(Resource resource) {
        this.active = false;
        this.resource = resource;
    }

    /**
     * Setter Method to activate the special ability
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter Method
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Getter Method
     * @return
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * It actives the Ability and in the subclasses executes the function
     */
    public void activeAbility(Player player) throws InvalidActionException {
        this.setActive(true);
    };

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
