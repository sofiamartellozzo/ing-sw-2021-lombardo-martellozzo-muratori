package it.polimi.ingsw.message.viewMsg;

/**
 * this msg is send by the Turn controller when the client want to
 * buy a Development Card in his Turn
 * the controller pass a double array on integer that represents the position of the table where
 * he/she can buy
 */
public class VChooseDevelopCardRequestMsg extends ViewGameMsg {

    private String username;
    private boolean[][] cardAvailable;

    public VChooseDevelopCardRequestMsg(String msgContent, String username, boolean[][] cardAvailable) {
        super(msgContent);
        this.username = username;
        this.cardAvailable = cardAvailable;
    }

    public String getUsername() {
        return username;
    }

    public boolean[][] getCardAvailable() {
        return cardAvailable;
    }
}
