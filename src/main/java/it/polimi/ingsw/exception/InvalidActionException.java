package it.polimi.ingsw.exception;

/**
 * Exception that passes a message when an action isn't valid.
 */

public class InvalidActionException extends Exception{
    public InvalidActionException(String message){
        super(message);
    }
}
