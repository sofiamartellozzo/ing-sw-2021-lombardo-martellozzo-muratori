package it.polimi.ingsw.exception;

/**
 * general exception that passes a message when an input from the Player isn't valid
 */
public class InputInvalidException extends Exception{
    public InputInvalidException(String message){
        super(message);
    }
}
