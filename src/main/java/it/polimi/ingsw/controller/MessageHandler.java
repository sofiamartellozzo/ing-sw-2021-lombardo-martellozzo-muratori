package it.polimi.ingsw.controller;

import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.message.viewMsg.ViewGameMsg;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

/**
 * this class is used in OFFLINE mode
 * it contains a queue for the messages
 */
public class MessageHandler extends Observable implements Runnable{

    private ArrayList<GameMsg> messageQueueForVV;
    private ViewObserver offlineVirtualView;

    private ArrayList<GameMsg> messageQueueForView;
    private ViewObserver cliOrGui;

    private Thread thread;

    private boolean gameOn;

    /**
     * constructor of the class
     */
    public MessageHandler() {
        this.messageQueueForVV = new ArrayList<>();
        this.messageQueueForView = new ArrayList<>();
        gameOn = true;
    }

    /**
     * this method creates the virtual view for the player given in input
     * @param username of the player
     */
    public void generateVV(String username){
        offlineVirtualView = new VirtualView(username, this);
        //attachObserver(ObserverType.VIEW, offlineVirtualView);
        attachObserver(ObserverType.CONTROLLER, offlineVirtualView);
    }

    private void sendMsgToVV(){
        GameMsg msg = messageQueueForVV.remove(0);
        notifyAllObserver(ObserverType.CONTROLLER, msg);
    }

    private void sendMsgToView(){
        GameMsg msg = messageQueueForView.remove(0);
        notifyAllObserver(ObserverType.VIEW, msg);
    }

    public void receiveMsgForVV(GameMsg msg){
        printDebug("in receive msg");
        messageQueueForVV.add(msg);
    }

    public void receivedMsgForView(GameMsg msg){
        messageQueueForView.add(msg);
    }

    @Override
    public void run() {
        printDebug("inside run");
        thread = new Thread (() -> {
            while (gameOn) {
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                printDebug("inside run while");
                if (messageQueueForVV.size() > 0) {
                    //send Msg as FIFO logic
                    printDebug("in run");
                    sendMsgToVV();
                } else if (messageQueueForView.size() > 0) {
                    sendMsgToView();
                }
              }
            });
        thread.start();

    }

    public void stopMessageHandler(){
        gameOn = false;
        thread.interrupt();
    }

    private void printDebug(String message) {
        System.out.println(message);
    }
}
