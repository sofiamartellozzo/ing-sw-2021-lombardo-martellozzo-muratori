package it.polimi.ingsw.message.controllerMsg;

import it.polimi.ingsw.message.ControllerObserver;

/**
 * CLI/GUI ---> VV ---> Lobby(Room) ---> TurnController
 *
 * msg send if the client ask to see the info of another player
 */
public class CAskSeeSomeoneElseMsg extends ControllerGameMsg {
    private String usernameAsking;  //ho ask to see
    private String usernameAsked;

    public CAskSeeSomeoneElseMsg(String msgContent, String usernameAsking, String usernameAsked) {
        super(msgContent);
        this.usernameAsking = usernameAsking;
        this.usernameAsked = usernameAsked;
    }

    public String getUsernameAsking() {
        return usernameAsking;
    }

    public String getUsernameAsked() {
        return usernameAsked;
    }

    @Override
    public void notifyHandler(ControllerObserver controllerObserver) {
        controllerObserver.receiveMsg(this);
    }
}
