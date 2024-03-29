package it.polimi.ingsw.message;

import it.polimi.ingsw.exception.InvalidActionException;

import java.io.Serializable;

/**
 * a generic GameEvent that notify an EventListener (View or Controller)
 * that are two Observer
 *
 * SERIALIZABLE: Interface that that allow an activity to save an object (or a graph of objects)
 *              in a byte stream, that can be saved persistently after on the network.
 *              (This object so can be serialized or deserialized)
 *              Serialization= it needs the class ObjectOutputStream (with method writeObject)
 *                              so then the object can be send on the net easily
 *              Deserialization= is the inverse process
 *                              it needs the class ObjectInputStream (with method readObject)
 *                              so then the object can be received on the other side of the net
 */
public abstract class GameMsg implements Serializable {

    private final String msgContent;

    public GameMsg(String msgContent){
        this.msgContent = msgContent;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void notifyHandler(ViewObserver viewObserver){

    }

    public void notifyHandler(ControllerObserver controllerObserver){

    }
}
