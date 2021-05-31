package it.polimi.ingsw.message.viewMsg;

import it.polimi.ingsw.message.ViewObserver;

/**
 * EndGameController ---> VV ---> CLI / GUI
 * msg send by the controller at the end of a game to ask the client if
 * wants to play a new game or not
 */
public class VAskNewGameMsg extends ViewGameMsg{

    public VAskNewGameMsg(String msgContent) {
        super(msgContent);
    }

    @Override
    public void notifyHandler(ViewObserver viewObserver) {
        viewObserver.receiveMsg(this);
    }
}
