package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.model.TypeResource;

import java.util.ArrayList;

/**
 * 1) InitializedController---> VV ----> CLI
 *
 *
 * this class represent the msg send by the controller to the view, that will send
 * directly to the client for make him chose a resources and in witch depot store it
 */
public class VChooseResourceAndDepotMsg extends ViewGameMsg {

    private final String username;
    private ArrayList<TypeResource> choices;


    public VChooseResourceAndDepotMsg(String msgContent, String username) {
        super(msgContent);
        this.username = username;
        choices = null;
    }

    public VChooseResourceAndDepotMsg(String msgContent, String username,ArrayList<TypeResource> choice ){
        super(msgContent);
        this.username = username;
        this.choices = choice;
    }

    public ArrayList<TypeResource> getChoices() {
        return choices;
    }

    public String getUsername() {
        return username;
    }


    public void notifyHandler(ViewObserver viewObserver){
        /* redirect the msg to the VV*/
        viewObserver.receiveMsg(this);
    }
}
