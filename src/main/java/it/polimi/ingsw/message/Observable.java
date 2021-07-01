package it.polimi.ingsw.message;

import it.polimi.ingsw.exception.InvalidActionException;

import java.util.ArrayList;
import java.util.List;


/**
 * this class is used to attachObservers (VIEW, CONTROLLER), detachObservers (VIEW, CONTROLLER) and notify them
 */
public abstract class Observable {

    private List<ViewObserver> viewObserver;
    private List<ControllerObserver> controllerObserver;

    public Observable() {
        this.viewObserver = new ArrayList<>();
        this.controllerObserver = new ArrayList<>();
    }

    /**
     * @param observerType -> type of observer that has to be notified
     * @param msg
     */
    public synchronized void notifyAllObserver(ObserverType observerType, GameMsg msg) {
        switch (observerType) {
            case VIEW:
                for (ViewObserver observerV : viewObserver) {
                    msg.notifyHandler(observerV);
                }
                break;
            case CONTROLLER:
                for (ControllerObserver observerC : controllerObserver) {
                    msg.notifyHandler(observerC);
                }
                break;
        }

    }

    /**
     * method used to attach the observer
     */
    public void attachObserver(ObserverType observerType, Observer observer) {
        switch (observerType) {

            case VIEW:
                if (!viewObserver.contains((ViewObserver) observer)) {
                    viewObserver.add((ViewObserver) observer);
                    //debugging
                    break;

                }
            case CONTROLLER:
                if (!controllerObserver.contains((ControllerObserver) observer)) {
                    controllerObserver.add((ControllerObserver) observer);
                    //debugging
                    break;
                }
        }
    }


    /**
     * method used to detach an observer
     */
    public void detachObserver(ObserverType observerType, Observer observer) {
        switch (observerType) {

            case VIEW:
                viewObserver.remove((ViewObserver) observer);
                //debugging
                break;
            case CONTROLLER:
                controllerObserver.remove((ControllerObserver) observer);
                //debugging
                break;
        }
    }
}