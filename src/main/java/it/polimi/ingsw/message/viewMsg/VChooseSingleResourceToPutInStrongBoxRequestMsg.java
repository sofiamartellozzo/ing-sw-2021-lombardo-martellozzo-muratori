package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.model.TypeResource;

import javax.swing.text.View;

public class VChooseSingleResourceToPutInStrongBoxRequestMsg extends ViewGameMsg {
    private String username;

    public VChooseSingleResourceToPutInStrongBoxRequestMsg(String msgContent,String username){
        super(msgContent);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
