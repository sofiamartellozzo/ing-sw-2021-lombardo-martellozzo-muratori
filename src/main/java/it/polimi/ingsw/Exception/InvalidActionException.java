package it.polimi.ingsw.Exception;

/**
 * Exception that passes a message when an action isn't valid.
 */

public class InvalidActionException extends Exception{
    public InvalidActionException(String message){
        super(message);
    }
}
