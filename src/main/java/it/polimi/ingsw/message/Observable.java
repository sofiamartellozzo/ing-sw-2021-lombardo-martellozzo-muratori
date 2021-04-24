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

    /**
     *
     * @param observerType
     * @param msg
     */
    public synchronized void notifyAllObserver(ObserverType observerType, GameMsg msg){
        switch (observerType){
            case VIEW:
                for (ViewObserver observerV :viewObserver) {
                    msg.notifyHandler(observerV);
                }
                break;
            case CONTROLLER:
                for (ControllerObserver observerC :controllerObserver) {
                    msg.notifyHandler(observerC);
                }
                break;
        }

    }

    /**
     * method used to attach the observer
     */
    public void attachObserver(ObserverType observerType,Observer observer){
        switch (observerType){

            case VIEW:
                viewObserver.add((ViewObserver) observer);
                break;
            case CONTROLLER:
                controllerObserver.add((ControllerObserver) observer);
                break;
        }
    }

    /**
     * method used to detach an observer
     */
    public void detachObserver(ObserverType observerType,Observer observer){
        switch (observerType){

            case VIEW:
                viewObserver.remove((ViewObserver) observer);
                break;
            case CONTROLLER:
                controllerObserver.remove((ControllerObserver) observer);
                break;
        }
    }
}
