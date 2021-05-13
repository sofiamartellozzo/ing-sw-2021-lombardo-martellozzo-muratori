package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

import it.polimi.ingsw.model.ActionToken;

/**
 * TurnController ---> VV ---> CLI/GUI
 *
 * msg send by the server at the end of a Solo Player turn
 * after activated one Action Token randomly
 * the purpose of this msg is to notify the client which AT has been activated
 */
public class VActionTokenActivateMsg extends ViewGameMsg{
    private final String username;
    private ActionToken actionToken;


    public VActionTokenActivateMsg(String msgContent, String username, ActionToken actionToken) {
        super(msgContent);
        this.username = username;
        this.actionToken = actionToken;
    }

    public String getUsername() {
        return username;
    }

    public ActionToken getActionToken() {
        return actionToken;
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
