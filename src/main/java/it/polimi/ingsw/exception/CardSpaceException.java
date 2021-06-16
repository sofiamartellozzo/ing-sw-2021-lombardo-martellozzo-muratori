package it.polimi.ingsw.exception;

/**
 * Exception that passes a message when a Card Space selected is not valid
 */
public class CardSpaceException extends Exception{
    public CardSpaceException(String message) {
        super(message);
    }
}
