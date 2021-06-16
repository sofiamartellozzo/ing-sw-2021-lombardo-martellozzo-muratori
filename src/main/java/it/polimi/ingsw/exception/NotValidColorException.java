package it.polimi.ingsw.exception;

/**
 * exception that sends a message when a color isn't valid so it doesn't belong to the Color list
 */
public class NotValidColorException extends Exception {
    public NotValidColorException(String message){
        super(message);
    }
}
