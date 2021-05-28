package it.polimi.ingsw.view.GUI.utils;

import it.polimi.ingsw.message.viewMsg.ViewGameMsg;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueGUI {
    private Queue<ViewGameMsg> msgs;

    public QueueGUI(){
        msgs= new ArrayDeque<>();
    }

    public void addMsg(ViewGameMsg msg){msgs.add(msg);}

    public void removeMsg(){ msgs.remove();}
}
