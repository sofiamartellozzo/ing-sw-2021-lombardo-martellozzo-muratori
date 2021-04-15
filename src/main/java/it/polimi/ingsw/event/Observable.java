package it.polimi.ingsw.event;

import java.util.ArrayList;
import java.util.List;


/**
 * this class has to notify all the Listener attached
 */
public abstract class Observable {

    private List<ViewListener> viewListener;
    private List<ControllerListener> controllerListener;

    public Observable() {
        this.viewListener = new ArrayList<>();
        this.controllerListener = new ArrayList<>();
    }

    public void notifyAllObserver(){

    }

    public void attachListener(){

    }

    public void detachListener(){

    }
}
