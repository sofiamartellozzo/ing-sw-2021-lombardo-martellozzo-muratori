package it.polimi.ingsw.message.controllerMsg;

public class CRoomSizeResponseMsg extends ControllerGameMsg{

    private int roomSize;
    private String username;

    public CRoomSizeResponseMsg(String msgContent,int roomSize, String username) {
        super(msgContent);
        this.roomSize = roomSize;
        this.username = username;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public String getUsername() {
        return username;
    }

}
