package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

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

    public VActivateProductionPowerRequestMsg(String msgContent, String username) {
        super(msgContent);
        this.username=username;
    }

    public String getUsername(){return username;}

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
