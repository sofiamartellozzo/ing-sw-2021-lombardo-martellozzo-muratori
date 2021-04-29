package it.polimi.ingsw.message;

import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.viewMsg.VConnectionRequestMsg;

/**
 * OBSERVER
 * interface with the method of the controllers
 * not all yet
 *
 */
public interface ControllerObserver extends Observer{

    public void receiveMsg(CConnectionRequestMsg msg);
    public void receiveMsg(VConnectionRequestMsg msg);
    public void receiveMsg(CChooseLeaderCardResponseMsg msg);
    public void receiveMsg(CChooseResourceAndDepotMsg msg);
    public void receiveMsg(CChooseActionTurnResponseMsg msg);
    public void receiveMsg(CBuyDevelopCardResponseMsg msg);
    public void receiveMsg(CMoveResourceInfoMsg msg);
    public void receiveMsg(CBuyFromMarketInfoMsg msg);
    public void receiveMsg(CActivateProductionPowerResponseMsg msg);
    public void receiveMsg(CChooseDiscardResourceMsg msg);
    public void receiveMsg(CChooseResourceResponseMsg msg);
    public void receiveMsg(CChooseSingleResourceToPutInStrongBoxResponseMsg msg);


}
