package it.polimi.ingsw.model;

/*
* SOFI*/

public enum TurnAction {

    BUY_CARD, BUY_FROM_MARKET, ACTIVE_PRODUCTION_POWER, ACTIVE_LEADER_CARD, END_TURN, GET_ACTION_TOKEN, MOVE_RESOURCE, REMOVE_LEADER_CARD, ERROR, SEE_OTHER_PLAYER;

    @Override
    public String toString() {
        return super.toString();
    }
}
