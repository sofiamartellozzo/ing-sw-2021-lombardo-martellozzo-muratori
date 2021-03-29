package it.polimi.ingsw.Model.cardability;
import it.polimi.ingsw.Model.Resource;

public abstract class SpecialAbility {
    // Indicates if the ability is active or not
    private boolean active;
    // The function of this attribute is based
    // on the type of the Special Ability
    // (For example: if I have a Discount ability, it indicates the resource
    // on which to apply the discount)
    private Resource type;

    // Constructor:
    // Initially, the "active" attribute is set to false
    public SpecialAbility(boolean active, Resource type) {
        this.active = active;
        this.type = type;
    }

    // Setter:
    // To set "active" to true
    public void setActive(boolean active) {
        this.active = active;
    }

    public void activeAbility(){};
}
