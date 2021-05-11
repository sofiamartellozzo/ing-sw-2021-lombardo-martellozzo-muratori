package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.TypeResource;

import javax.swing.text.View;

/**
 * PPController ---> VV ---> CLI/GUI
 *
 * msg send if the player choose the PP of the special card
 */
public class VChooseSingleResourceToPutInStrongBoxRequestMsg extends ViewGameMsg {
    private String username;

    public VChooseSingleResourceToPutInStrongBoxRequestMsg(String msgContent,String username){
        super(msgContent);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
