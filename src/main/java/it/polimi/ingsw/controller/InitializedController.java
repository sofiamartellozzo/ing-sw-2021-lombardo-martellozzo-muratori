package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.controller.factory.ResourcesSupplyFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.CGameCanStartMsg;
import it.polimi.ingsw.message.updateMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.updateMsg.VUpdateWarehouseMsg;
import it.polimi.ingsw.message.updateMsg.VWaitOtherPlayerInitMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.message.ControllerObserver;
import it.polimi.ingsw.controller.factory.PersonalSoloBoardFactory;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.model.board.SoloPersonalBoard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;

/**
 * this class set the information of the game at the initialization
 */

public class InitializedController extends Observable implements ControllerObserver {
    /* the parts of the game that all the player have in common*/
    private BoardManager boardManager;

    /* the sequence of the player to the game */
    private HashMap<Integer, PlayerInterface> turnSequence;
    private int currentTurnIndex;
    private int numberOfPlayer;

    /* the solo player if is only one */
    private SoloPlayer singlePlayer;


    /* setting true if the game can start */
    private boolean canStart;

    private Map<String, VirtualView> virtualView;

    private int counterPlayerInitialized = 0;
    private int counterResourcesChosen = 0;

    /* Constructor of the class */
    public InitializedController(ArrayList<String> players, Map<String, VirtualView> virtualView) {

        this.canStart = false;
        this.numberOfPlayer = players.size();
        //System.out.println(players.size());       DEBUGGING
        this.turnSequence = new HashMap<>();
        this.virtualView = new HashMap<>();
        this.virtualView = virtualView;
        attachAllVV();
        /* shuffle the player to make the choise of the fist random */
        Collections.shuffle(players);
        /* creating a ordered sequence of the players */
        creatingPlayers(players);

        //contruct the resources supply for all the players
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        //this.resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

    }


    public BoardManager getBoardManager() {
        return boardManager;
    }

    public HashMap<Integer, PlayerInterface> getTurnSequence() {
        return turnSequence;
    }

    public SoloPlayer getSinglePlayer() {
        return singlePlayer;
    }

    public boolean canStart() {
        return canStart;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * this method create a organized sequence of the players as a Map
     * (if only one initialized his as Solo Player class)
     *
     * @param players
     */
    private void creatingPlayers(ArrayList<String> players) {
        if (numberOfPlayer == 1) {
            SoloPlayer p = new SoloPlayer(players.get(0));
            singlePlayer = p;
        } else {
            for (int i = 0; i < numberOfPlayer; i++) {
                Player p = new Player(players.get(i));
                turnSequence.put(i + 1, p);
            }
        }
    }

    private void attachAllVV() {
        for (String username : virtualView.keySet()) {
            attachObserver(ObserverType.VIEW, virtualView.get(username));
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * method called to make this class start his work:
     * creating all the stuff to prepare the game
     */
    public void createGame() throws InvalidActionException {
        /* at first creating the common part of the game */

        //create the resources supply--->in the Board Manager so these can be removed
        ResourcesSupplyFactory resourcesSupplyFactory = new ResourcesSupplyFactory();
        ResourcesSupply resourcesSupply = resourcesSupplyFactory.createTheResourcesSupply();

        //create the board manager
        BoardManagerFactory boardManagerFactory = new BoardManagerFactory();
        boardManager = boardManagerFactory.createBoardManager(turnSequence);
        System.out.println("in INITIALIZEDC " +boardManager.toString());

        if (numberOfPlayer == 1) {
            /*initialized solo Mode*/
            //creating the Personal Board
            PersonalSoloBoardFactory soloBoardFactory = new PersonalSoloBoardFactory();
            SoloPersonalBoard soloPersonalBoard = soloBoardFactory.createGame();
            //setting the attribute of the player with his Personal Board
            singlePlayer.setGameSpace(soloPersonalBoard);

            VSendPlayerDataMsg msg = new VSendPlayerDataMsg("Here are the personal Data about your", singlePlayer, boardManager, true);
            notifyAllObserver(ObserverType.VIEW, msg);

            //call the msg to choose the leader card
            chooseLeaderCard(true);

            //now the came can start... Create the turn controller
           // canStart = true;
        } else {
            /*initialized multiplayer Mode*/
            //creating the Personal Board for each player
            for (Integer i : this.turnSequence.keySet()) {
                PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
                PersonalBoard personalBoard = personalBoardFactory.createGame();
                this.turnSequence.get(i).setGameSpace(personalBoard);
                this.turnSequence.get(i).setNumber(i);
                VSendPlayerDataMsg msg = new VSendPlayerDataMsg("Here are the personal Data about your", turnSequence.get(i), boardManager, false);
                notifyAllObserver(ObserverType.VIEW, msg);
            }

            //then giving the resources initial to the players
            giveStartResources();


        }
    }

    /**
     * at the start of the game all player (both single or multiple mode)
     * have to choose 2 Leader Card from 4 choosen random
     *
     * @param solo
     */
    private void chooseLeaderCard(boolean solo)  {
        //take the 4 leader card from all (stored in Board Manager)
        ArrayList<Integer> allLeaderCard = boardManager.getAllLeaderCard();  //all leader cards
        //select 4 randomly
        ArrayList<Integer> fourLeaderCard = new ArrayList<>();
        if (solo) {
            //there is only one player

            fourLeaderCard = selectOnlyFour(allLeaderCard);
            //now create the msg to the client
            VChooseLeaderCardRequestMsg msg = new VChooseLeaderCardRequestMsg("Ask the client to choose 2 from 4 Leader Cards", fourLeaderCard, singlePlayer.getUsername(), "initialization");
            notifyAllObserver(ObserverType.VIEW, msg); //send to the view that will send it to the client
        } else {
            //the players are at least two

            for (Integer j : this.turnSequence.keySet()) {
                fourLeaderCard = selectOnlyFour(allLeaderCard);
                /* remove the 4 card chosen for the player from all (cannot be send to another player) */
                allLeaderCard.removeAll(fourLeaderCard);
                //create the msg for the client (player number #j)
                VChooseLeaderCardRequestMsg msg = new VChooseLeaderCardRequestMsg("Ask the client to choose 2 from 4 Leader Cards", fourLeaderCard, turnSequence.get(j).getUsername(), "initialization");
                notifyAllObserver(ObserverType.VIEW, msg); //send to the view that will send it to the client
            }
        }
    }

    /**
     * auxiliary method to choose randomly 4 card from all in the game
     *
     * @param allLeaderCard
     * @return
     */
    private ArrayList<Integer> selectOnlyFour(List<Integer> allLeaderCard) {
        Random random = new Random();
        ArrayList<Integer> fourLeaderCard = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fourLeaderCard.add(allLeaderCard.remove(random.nextInt(allLeaderCard.size())));
        }
        return fourLeaderCard;
    }

    /**
     * this method is called only in multiple mode, in single one is not necessary
     * at the game start, after chose the Leader Card all player recive different stuff:
     * the first one only the inkpot
     * the second one recive one resource that he choose
     * the third one (if exist) recive one resource that he choose and increasing of one position the faith market
     * the fourth one (if exist) recive two resources that he choose and increasing of one position the paith market
     *
     * @throws InvalidActionException
     */
    private void giveStartResources() throws InvalidActionException {
        //System.out.println("giving the resources to the second player outside");      DEBUGGING
        if (this.turnSequence.get(2) != null) {
            //the second player receive one resources that he choose
            //System.out.println("giving the resources to the second player inside");       DEBUGGING
            VChooseResourceAndDepotMsg msg1 = new VChooseResourceAndDepotMsg("You are the second player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(2).getUsername());
            notifyAllObserver(ObserverType.VIEW, msg1);

            if (this.turnSequence.get(3) != null) {
                //System.out.println("giving the resources to the 3 player inside");        DEBUGGING
                //the third player receive one resource and a faith marker(so increase of one his position)
                VChooseResourceAndDepotMsg msg2 = new VChooseResourceAndDepotMsg("You are the third player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(3).getUsername());
                notifyAllObserver(ObserverType.VIEW, msg2);

                this.turnSequence.get(3).getGameSpace().getFaithTrack().increasePosition();
                /* notify all palyers that this one increase his position */
                VNotifyPositionIncreasedByMsg notify1 = new VNotifyPositionIncreasedByMsg("one user increased his position in FT", turnSequence.get(3).getUsername(), 1);
                notify1.setAllPlayerToNotify(getPlayersAsList());
                notifyAllObserver(ObserverType.VIEW, notify1);

                if (this.turnSequence.get(4) != null) {
                    //the fourth player receives two resources and a faith marker(so thi increase of one his position)
                    VChooseResourceAndDepotMsg msg3 = new VChooseResourceAndDepotMsg("You are the fourth player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(4).getUsername());
                    notifyAllObserver(ObserverType.VIEW, msg3);
                    VChooseResourceAndDepotMsg msg4 = new VChooseResourceAndDepotMsg("You are the fourth player, please select another resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(4).getUsername());
                    notifyAllObserver(ObserverType.VIEW, msg4);

                    this.turnSequence.get(4).getGameSpace().getFaithTrack().increasePosition();
                    /* notify all palyers that this one increase his position */
                    VNotifyPositionIncreasedByMsg notify2 = new VNotifyPositionIncreasedByMsg("one user increased his position in FT", turnSequence.get(4).getUsername(), 1);
                    notify2.setAllPlayerToNotify(getPlayersAsList());
                    notifyAllObserver(ObserverType.VIEW, notify2);
                }
            }
        }
    }

    /**
     * private method to help find the player in the sequence turn by his username
     * usually when receive a msg so find the player associated to apply
     * the event to the right one
     *
     * @param username
     * @return
     */
    private PlayerInterface findByUsername(String username) {
        for (PlayerInterface p : turnSequence.values()) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        throw new NoSuchElementException("Player not found in the sequence!");
    }

    /**
     * get an array list by the turn sequence
     */
    private List<String> getPlayersAsList() {
        List<String> players = new ArrayList<>();
        for (Integer i : turnSequence.keySet()) {
            players.add(turnSequence.get(i).getUsername());
        }
        return players;
    }

    /*--------------------------------------------------------------------------------------------------------------------*/

                //HANDLE EVENT OF THE GAME

    /**
     * this msg contains the list of Leader Cards the user choose and
     * the two discarded
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        //System.out.println("in receiving the 2 cards chosen ");       DEBUGGING
        counterPlayerInitialized++;
        System.out.println(counterPlayerInitialized);           //DEBUGGING
        if (msg.getAction().equals("firstChoose")) {
            //take all the Integer for Leader Card
            ArrayList<Integer> twoChosen = msg.getLeaderCards();
            //System.out.println("the 2 cards chosen " + twoChosen);
            ArrayList<LeaderCard> chosenCards = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                if (boardManager.getAllLeaderCard().contains(twoChosen.get(i))) {
                    /* the player choose this card so I have to put it in his and remove from the Deck */
                    LeaderCard chosen = boardManager.getLeaderCardDeck().getLeaderCardById(twoChosen.get(i));
                    chosenCards.add(chosen);
                }
            }
            //System.out.println("the 2 cards chosen " + chosenCards);
            //take the player that choose the cards
            if (this.getNumberOfPlayer() == 1) {
                singlePlayer.setLeaderCards(chosenCards);
                //System.out.println("the 2 cards chosen now in player " + singlePlayer.getLeaderCards());
            } else {
                PlayerInterface player = null;
                try {
                    player = findByUsername(msg.getUsername());
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
                player.setLeaderCards(chosenCards);
                //System.out.println("the 2 cards chosen now in player " + player.getLeaderCards());
            }

            //now remove the card from the deck
            boardManager.getLeaderCardDeck().remove(chosenCards);
            if (counterPlayerInitialized==numberOfPlayer){
                //System.out.println("enter in can start!!!!");
                //now the came can start... Create the turn controller
                canStart = true;
                CGameCanStartMsg startGame = new CGameCanStartMsg("",getPlayersAsList());
                notifyAllObserver(ObserverType.VIEW, startGame);
                //System.out.println(startGame);
            }
            else{
                VWaitOtherPlayerInitMsg wait = new VWaitOtherPlayerInitMsg("", msg.getUsername());
                notifyAllObserver(ObserverType.VIEW, wait);
            }
        }
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {

    }

    /**
     * msg from client with the resource he chose and the depots where he wants to store it
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseResourceAndDepotMsg msg) {
        //find the player by username
        counterResourcesChosen++;
        Resource r = new Resource(msg.getResource());
        if (singlePlayer == null) {
            Player player = null;
            try {
                player = (Player) findByUsername(msg.getUsername());
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

            try {
                player.getGameSpace().getResourceManager().addResourceToWarehouse(r, msg.getDepot());
                VUpdateWarehouseMsg notification = new VUpdateWarehouseMsg("The warehouse has changed..", player.getUsername(), player.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, notification);
            } catch (InvalidActionException e) {
                e.printStackTrace();
                //create msg to send to client that he made an invalid action, so change the depot
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
            if ((numberOfPlayer==4 && counterResourcesChosen==4)||( counterResourcesChosen==(numberOfPlayer-1)&&(numberOfPlayer!=4))){
                //call the msg to choose the leader card
                chooseLeaderCard(false);
            }
        }
        else{
            try {
                singlePlayer.getGameSpace().getResourceManager().addResourceToWarehouse(r,msg.getDepot());
                VUpdateWarehouseMsg notification = new VUpdateWarehouseMsg("The warehouse has changed..", singlePlayer.getUsername(), singlePlayer.getGameSpace().getWarehouse());
                notifyAllObserver(ObserverType.VIEW, notification);
            } catch (InvalidActionException e) {
                e.printStackTrace();
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //      not implemented here!!

    @Override
    public void receiveMsg(CChooseActionTurnResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {

    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {

    }


    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {

    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {

    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {

    }


    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {

    }



    @Override
    public void receiveMsg(CStopPPMsg msg) {

    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {

    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {

    }


    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //not here
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //not here (Lobby)
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        //not here (VV-->Lobby and CLI)
    }

}
