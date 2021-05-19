package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import java.util.ArrayList;

/**
 * PPController ---> VV ---> CLI
 *
 * msg from the Production Power controller to the client after a request of activating
 * this ability so after found which one/ones can be activated ask it to the client
 *
 *  availableCardSpace:
 *      - 0     ---> base PP
 *      - 1,2,3 ---> card Space
 *      - 4,5   ---> Special
 */
public class VActivateProductionPowerRequestMsg extends ViewGameMsg{
    private String username;
    private ArrayList<Integer> activatablePP;

    public VActivateProductionPowerRequestMsg(String msgContent, String username, ArrayList<Integer> activatablePP) {
        super(msgContent);
        this.username=username;
        this.activatablePP=activatablePP;
    }

    public String getUsername(){return username;}

    public ArrayList<Integer> getActivatablePP() {
        return activatablePP;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
