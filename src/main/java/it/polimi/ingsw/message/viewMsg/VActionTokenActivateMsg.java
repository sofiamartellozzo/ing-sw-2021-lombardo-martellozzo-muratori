package it.polimi.ingsw.message.viewMsg;

/**
 * msg send by the server at the end of a Solo Player turn
 * after activated one Action Token randomly
 * the purpose of this msg is to notify the client which AT has been activated
 */
public class VActionTokenActivateMsg extends ViewGameMsg{
    private final String username;
    private final int actionToken;

    public VActionTokenActivateMsg(String msgContent, String username, int actionToken) {
        super(msgContent);
        this.username = username;
        this.actionToken = actionToken;
    }

    public String getUsername() {
        return username;
    }

    public int getActionToken() {
        return actionToken;
    }
}
