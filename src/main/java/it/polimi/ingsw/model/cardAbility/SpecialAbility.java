package it.polimi.ingsw.model.cardAbility;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

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
    private final TypeAbility typeAbility;

    /**
     * Constructor: active is always "false"
     * then the setter method can set it in "true".
     * @param resource
     */
    public SpecialAbility(Resource resource, TypeAbility typeAbility) {
        this.active = false;
        this.resource = resource;
        this.typeAbility = typeAbility;
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

    public TypeAbility getTypeAbility() {
        return typeAbility;
    }

    /**
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "SpecialAbility";
    }
}
