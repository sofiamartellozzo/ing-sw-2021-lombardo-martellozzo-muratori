package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.factory.BoardManagerFactory;
import it.polimi.ingsw.controller.factory.ResourcesSupplyFactory;
import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.message.Observable;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.controllerMsg.*;
import it.polimi.ingsw.message.updateMsg.*;
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
 * this class is a part of the Controller
 * It sets the information of the game at the initialization
 * It creates the players, the Board Manager (that manages all the players and their objects), the Personal Board of each player
 * (Solo Personal Board if is in solo game)
 * This class manages the players' Leader Cards choice (two cards) and assigns the beginning resources to the players
 * 1 player -> 0 resources
 * 2 player -> 1 resources
 * 3 player -> 1 resources
 * 4 player -> 2 resources
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

    /**
     * constructor of the class
     * @param players
     * @param virtualView
     */
    public InitializedController(ArrayList<String> players, Map<String, VirtualView> virtualView) {

        this.canStart = false;
        this.numberOfPlayer = players.size();
        this.turnSequence = new HashMap<>();
        this.virtualView = new HashMap<>();
        this.virtualView = virtualView;
        attachAllVV();
        /* shuffle the player to make the choise of the fist random */
        //Collections.shuffle(players);
        /* creating a ordered sequence of the players */
        //creatingPlayers(players);

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
     * this method create an organized sequence of the players as a Map
     * (if there is only one player it initialize a Solo Player class)
     *
     * @param players
     */
    public void creatingPlayersSequence(ArrayList<String> players, ArrayList<Player> realPlayers) {
        Collections.shuffle(players);
        for (int i = 0; i < numberOfPlayer; i++) {
            for (Player p: realPlayers) {
                if (p.getUsername().equals(players.get(i))){
                    turnSequence.put(i+1, p);
                }
            }
        }
    }

    public void fillSinglePlayer(SoloPlayer realPlayer){
        singlePlayer = realPlayer;
    }

    /**
     * attach all VV of the players so this class can notify them
     */
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
     * have to choose 2 Leader Card from 4 chosen random (from the 16 Cards in the deck)
     *
     * @param solo
     */
    private void chooseLeaderCard(boolean solo) {
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
     * auxiliary method used to choose randomly 4 card from the deck of 16 and after removes them
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
     * at the game start, after chose the Leader Card all player receive different stuff:
     * the first one only the inkpot
     * the second one receive one resource that he chooses
     * the third one (if exist) receive one resource that he chooses and increasing of one position the faith marker
     * the fourth one (if exist) receive two resources that he chooses and increasing of one position the faith marker
     *
     * @throws InvalidActionException
     */
    private void giveStartResources() throws InvalidActionException {
        if (this.turnSequence.get(2) != null) {
            //the second player receive one resources that he choose
                //send wait mag to the first player
            VWaitOtherPlayerInitMsg firstWait = new VWaitOtherPlayerInitMsg("", turnSequence.get(1).getUsername());
            notifyAllObserver(ObserverType.VIEW, firstWait);
                //to the second send the msg to choose the resource
            VChooseResourceAndDepotMsg msg1 = new VChooseResourceAndDepotMsg("You are the second player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(2).getUsername(), 1);
            notifyAllObserver(ObserverType.VIEW, msg1);

            if (this.turnSequence.get(3) != null) {
                //the third player receive one resource and a faith marker(so increase of one his position)
                VChooseResourceAndDepotMsg msg2 = new VChooseResourceAndDepotMsg("You are the third player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(3).getUsername(),1);
                notifyAllObserver(ObserverType.VIEW, msg2);

                int section=this.turnSequence.get(3).getGameSpace().getFaithTrack().increasePosition();
                if(section!=0) {
                    for (int i : this.boardManager.getPlayers().keySet()) {
                        this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().doVaticanReport(section);
                    }
                }
                /* notify all palyers that this one increase his position */
                VNotifyPositionIncreasedByMsg notify1 = new VNotifyPositionIncreasedByMsg("one user increased his position in FT", turnSequence.get(3).getUsername(), turnSequence.get(3).calculateVictoryPoints(), 1);
                notify1.setAllPlayerToNotify(getPlayersAsList());
                notifyAllObserver(ObserverType.VIEW, notify1);

                if (this.turnSequence.get(4) != null) {
                    //the fourth player receives two resources and a faith marker(so thi increase of one his position)
                    VChooseResourceAndDepotMsg msg3 = new VChooseResourceAndDepotMsg("You are the fourth player, please select a resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(4).getUsername(), 2);
                    notifyAllObserver(ObserverType.VIEW, msg3);
                    //VChooseResourceAndDepotMsg msg4 = new VChooseResourceAndDepotMsg("You are the fourth player, please select another resource and the depot where you want to store it (1, 2 or 3) !", this.turnSequence.get(4).getUsername());
                    //notifyAllObserver(ObserverType.VIEW, msg4);

                    section=this.turnSequence.get(3).getGameSpace().getFaithTrack().increasePosition();
                    if(section!=0) {
                        for (int i : this.boardManager.getPlayers().keySet()) {
                            this.boardManager.getPlayers().get(i).getGameSpace().getFaithTrack().doVaticanReport(section);
                        }
                    }
                    /* notify all palyers that this one increase his position */
                    VNotifyPositionIncreasedByMsg notify2 = new VNotifyPositionIncreasedByMsg("one user increased his position in FT", turnSequence.get(4).getUsername(), turnSequence.get(4).calculateVictoryPoints(), 1);
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
        if (numberOfPlayer > 1) {
            for (Integer i : turnSequence.keySet()) {
                players.add(turnSequence.get(i).getUsername());
            }
        }
        else{
            players.add(singlePlayer.getUsername());
        }
        return players;
    }

    /*--------------------------------------------------------------------------------------------------------------------*/

    //HANDLE EVENT OF THE GAME

    /**
     * this msg contains the list of Leader Cards the user chose and
     * the two discarded
     *
     * @param msg
     */
    @Override
    public void receiveMsg(CChooseLeaderCardResponseMsg msg) {
        counterPlayerInitialized++;
        if (msg.getAction().equals("firstChoose")) {
            //take all the Integer for Leader Card
            ArrayList<Integer> twoChosen = msg.getLeaderCards();
            ArrayList<LeaderCard> chosenCards = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                if (boardManager.getAllLeaderCard().contains(twoChosen.get(i))) {
                    /* the player choose this card so I have to put it in his and remove from the Deck */
                    LeaderCard chosen = boardManager.getLeaderCardDeck().getLeaderCardById(twoChosen.get(i));
                    chosenCards.add(chosen);
                }
            }
            //take the player that choose the cards
            if (numberOfPlayer == 1) {
                singlePlayer.setLeaderCards(chosenCards);
                VUpdateLeaderCards request = new VUpdateLeaderCards("Update leader cards",singlePlayer.getUsername(), singlePlayer.getLeaderCards());
                notifyAllObserver(ObserverType.VIEW,request);
            } else {
                PlayerInterface player = null;
                try {
                    player = findByUsername(msg.getUsername());
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
                player.setLeaderCards(chosenCards);
                VUpdateLeaderCards request = new VUpdateLeaderCards("Update leader cards",player.getUsername(), player.getLeaderCards());
                notifyAllObserver(ObserverType.VIEW,request);
            }

            //now remove the card from the deck
            boardManager.getLeaderCardDeck().remove(chosenCards);
            if (counterPlayerInitialized == numberOfPlayer) {
                //now the came can start... Create the turn controller
                canStart = true;
                CGameCanStartMsg startGame = new CGameCanStartMsg("", getPlayersAsList());
                notifyAllObserver(ObserverType.VIEW, startGame);
            } else {
                VWaitOtherPlayerInitMsg wait = new VWaitOtherPlayerInitMsg("", msg.getUsername());
                notifyAllObserver(ObserverType.VIEW, wait);
            }

        }
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby (Room)
    }

    /**
     * msg from client with the resource he chose and the depot where he wants to store it
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
                //e.printStackTrace();
                //create msg to send to client that he made an invalid action, so change the depot
                VNotValidDepotMsg msg1 = new VNotValidDepotMsg("You chose a depot that cannot store your resource, please chose another one!", msg.getUsername(), msg.getDepot(), msg.getResource());
                notifyAllObserver(ObserverType.VIEW, msg1);
            }
            if ((numberOfPlayer == 4 && counterResourcesChosen == 4) || (counterResourcesChosen == (numberOfPlayer - 1) && (numberOfPlayer != 4))) {
                //call the msg to choose the leader card
                chooseLeaderCard(false);
            }
            else{
                VWaitOtherPlayerInitMsg wait = new VWaitOtherPlayerInitMsg("", msg.getUsername());
                notifyAllObserver(ObserverType.VIEW, wait);
            }
        } else {
            try {
                singlePlayer.getGameSpace().getResourceManager().addResourceToWarehouse(r, msg.getDepot());
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
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyDevelopCardResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChangeActionTurnMsg msg) {
        //NOT HERE, implemented in Turn Controller
    }

    @Override
    public void receiveMsg(CMoveResourceInfoMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CBuyFromMarketInfoMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CChooseDiscardResourceMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }

    @Override
    public void receiveMsg(CStopMarketMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CActivateProductionPowerResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in PPController
    }

    @Override
    public void receiveMsg(CStopPPMsg msg) {
        //NOT IMPLEMENTED HERE, but in PPController (ActionC)
    }

    @Override
    public void receiveMsg(CAskSeeSomeoneElseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Action Controller
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
        if (numberOfPlayer == 1){
            //choose random leader card
            chooseLeaderCard(true);
        }
    }

    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //NOT IMPLEMENTED HERE, (VV) but in Lobby
    }

    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CNotStartAgainMsg msg) {
        //NOT HERE, implemented in Virtual View
    }

    @Override
    public void receiveMsg(CNewStartMsg msg) {
        //NOT HERE, implemented in Virtual View
    }


    @Override
    public void receiveMsg(CConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CResumeGameMsg msg) {
        //NOT IMPLEMENTED HERE, but in Turn Controller
    }

    @Override
    public void receiveMsg(VVConnectionRequestMsg msg) {
        //NOT IMPLEMENTED HERE, but in Virtual View
    }

    @Override
    public void receiveMsg(CRoomSizeResponseMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {
        //NOT IMPLEMENTED HERE, but in Lobby
    }

}
