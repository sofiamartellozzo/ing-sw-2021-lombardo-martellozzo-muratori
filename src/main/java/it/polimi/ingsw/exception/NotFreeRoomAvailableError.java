package it.polimi.ingsw.exception;

/**
 * exception that passes a message when any room of the game is available
 */
public class NotFreeRoomAvailableError extends Exception {
    public NotFreeRoomAvailableError(String message){
        super(message);
    }
}
