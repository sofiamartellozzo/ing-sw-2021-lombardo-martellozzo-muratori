package it.polimi.ingsw.message;

import java.util.ArrayList;
import java.util.List;


/**
 * this class has to notify all the Listener attached
 */
public abstract class Observable {

    private List<ViewObserver> viewObserver;
    private List<ControllerObserver> controllerObserver;

    public Observable() {
        this.viewObserver = new ArrayList<>();
        this.controllerObserver = new ArrayList<>();
    }

    public synchronized void notifyAllObserver(ObserverType observerType, GameMsg msg){
        switch (observerType){
            case VIEW:
                msg.notifyHandler(viewObserver.get(1));
                break;
            case CONTROLLER:
                msg.notifyHandler(controllerObserver.get(1));
                break;
        }

    }

    public void attachListener(){

    }

    public void detachListener(){

    }
}
