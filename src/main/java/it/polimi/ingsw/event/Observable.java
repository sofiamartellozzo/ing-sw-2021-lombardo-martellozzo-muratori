package it.polimi.ingsw.event;

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

    public void notifyAllObserver(){

    }

    public void attachListener(){

    }

    public void detachListener(){

    }
}
