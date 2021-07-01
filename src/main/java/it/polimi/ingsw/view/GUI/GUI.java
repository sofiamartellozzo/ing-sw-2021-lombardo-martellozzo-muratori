package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.MessageHandler;
import it.polimi.ingsw.message.GameMsg;
import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.connection.CClientDisconnectedMsg;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.controllerMsg.CCloseRoomMsg;
import it.polimi.ingsw.message.controllerMsg.VStartWaitReconnectionMsg;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.controllerMsg.CRoomSizeResponseMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.LeaderCardDeck;
import it.polimi.ingsw.model.card.SpecialCard;
import it.polimi.ingsw.model.market.MarketStructure;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.GUI.controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.model.TurnAction.BUY_FROM_MARKET;

/**
 * GUI version of the View
 */
public class GUI extends Application implements ViewObserver {
    private Stage stage;

    private Scene startScene;
    private StartGameController startGameController;

    private Scene introScene;
    private IntroSceneController introSceneController;

    private Scene lobbyScene;
    private LobbySceneController lobbySceneController;

    private Scene roomScene;
    private RoomSceneController roomSceneController;

    private Scene initializeScene;
    private InitializeSceneController initializeSceneController;

    private Scene personalBoardScene;
    private PersonalBoardSceneController personalBoardSceneController;

    private Scene marketStructureScene;
    private MarketStructureSceneController marketStructureSceneController;

    private Scene devCardTableScene;
    private DevCardTableSceneController devCardTableSceneController;

    private Scene otherPersonalBoardScene;
    private OtherPersonalBoardSceneController otherPersonalBoardSceneController;

    private Scene endGameScene;
    private EndGameSceneController endGameSceneController;

    private ClientSocket client;
    private String username;

    private String iP;

    private String gameSize;
    private boolean offline;
    private boolean soloMode;

    private boolean serverAvailable=true;
    boolean receiveMsg;

    private PlayerInterface player;
    private BoardManager boardManager;
    private LeaderCardDeck leaderCardsDeck;



    private ArrayList<LeaderCard> leaderCards;
    private MarketStructure marketStructureData;
    private DevelopmentCardTable developmentCardTable;
    private Warehouse warehouse;
    private StrongBox strongBox;
    private FaithTrack faithTrack;
    private ArrayList<CardSpace> cardSpaces;
    private ArrayList<SpecialCard> specialCards;



    private MessageHandler messageHandler;


    public static void main(String[] args){ Application.launch(args);}

    /**
     * To launch the GUI.
     * Set the stage and prepare the three main scene.
     * @param stage where to show the various scenes
     * @throws Exception in case something went wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setResizable(false);

        setStartScene();
        setIntroScene();
        setLobbyScene();

        stage.setTitle("Masters of Renaissance");
        //stage.setScene(introScene);
        stage.setScene(startScene);
        stage.show();
        stage.setOnCloseRequest(e -> close());

    }

    /**
     * To set the StartScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    private void setStartScene() throws IOException {
        FXMLLoader loaderStartScene = new FXMLLoader(getClass().getResource("/scenes/InitWindowScene.fxml"));
        startScene = new Scene(loaderStartScene.load());
        startGameController = loaderStartScene.getController();
        startGameController.setGui(this);
        //startGameController.start();
    }

    /**
     * To set the IntroScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    private void setIntroScene() throws IOException {
        FXMLLoader loaderIntroScene = new FXMLLoader(getClass().getResource("/scenes/IntroScene.fxml"));
        introScene = new Scene(loaderIntroScene.load());
        introSceneController = loaderIntroScene.getController();
        introSceneController.setGui(this);
        //introSceneController.start();
    }

    /**
     * When the game ends and the player wants to play another match, this method is used to prepare again
     * the main scenes and to change it in the stage
     */
    public void restartIntroScene() {
        try {
            setIntroScene();
            setStartScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //changeScene(introScene);
        changeScene(startScene);
    }

    /**
     * To set the LobbyScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setLobbyScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/LobbyScene.fxml"));
        lobbyScene = new Scene(loader.load());
        lobbySceneController=loader.getController();
        lobbySceneController.setGui(this);
    }

    /**
     * When this message is received, the GUI prepare the RoomScene
     * to allow to the player to choose how many players wants to play with
     * @param msg VRoomSizeRequestMsg
     */
    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {
        System.out.println(msg.toString());
        Platform.runLater(()->{
            try {
                roomSizeRequest(msg.getRoomID());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * To set the RoomScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void roomSizeRequest(String idRoom) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/RoomScene.fxml"));
        roomScene = new Scene(loader.load());
        roomSceneController=loader.getController();
        changeScene(roomScene);
        roomSceneController.setGui(this);
        roomSceneController.setIdRoom(idRoom);
    }

    /**
     * To send the response of room size to the Server/MessageHandler
     * @param choice the size
     * @param idRoom the id of the room
     */
    public void roomSizeResponse(int choice,String idRoom){
        CRoomSizeResponseMsg responseMsg = new CRoomSizeResponseMsg("Room size chosen: "+choice,choice,getUsername(),idRoom);
        client.sendMsg(responseMsg);
    }

    /**
     * When this message is received, set the information of the LobbyScene and change the scene of the stage
     * @param msg VRoomInfoMsg
     */
    @Override
    public void receiveMsg(VRoomInfoMsg msg) {
        System.out.println(msg.toString());
        Platform.runLater(()-> {
            lobbySceneController.updateLobby(msg);
            changeScene(lobbyScene);
        });
    }

    /**
     * To set the InitializeScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setInitializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/InitializeScene.fxml"));
        initializeScene = new Scene(loader.load());
        initializeSceneController =loader.getController();
        initializeSceneController.setGui(this);
    }

    /**
     * To set the PersonalBoardScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setPersonalBoardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/PersonalBoardScene.fxml"));
        personalBoardScene = new Scene(loader.load());
        personalBoardSceneController=loader.getController();
        personalBoardSceneController.setGui(this);
    }

    /**
     * To set the MarketStructureScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setMarketStructureScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/MarketStructureScene.fxml"));
        marketStructureScene=new Scene(loader.load());
        marketStructureSceneController=loader.getController();
        marketStructureSceneController.setGui(this);
    }

    /**
     * To set the DevCardTableScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setDevCardTableScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/DevCardTableScene.fxml"));
        devCardTableScene=new Scene(loader.load());
        devCardTableSceneController = loader.getController();
        devCardTableSceneController.setGui(this);
    }

    /**
     * To set the OtherPersonalBoardScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    private void setOtherPersonalBoardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/OtherPersonalBoardScene.fxml"));
        otherPersonalBoardScene = new Scene(loader.load());
        otherPersonalBoardSceneController=loader.getController();
        otherPersonalBoardSceneController.setGui(this);
    }

    /**
     * To update during the game, the info of the player and the view of the PersonalBoardScene.
     * If the stage's scene is not the PersonalBoardScene does mean the scene is the InitializeScene,
     * so the game can start and the scene can be changed with the first one.
     * Long story short, it is used to alert the GUI to change in the PersonalBoardScene.
     * @param msg VSendPlayerDataMsg
     */
    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {
        System.out.println(msg.toString());
        player = msg.getPlayer();
        boardManager = msg.getBoardManager();
        marketStructureData = msg.getBoardManager().getMarketStructure();
        leaderCardsDeck = msg.getBoardManager().getLeaderCardDeck();
        developmentCardTable = msg.getBoardManager().getDevelopmentCardTable();
        warehouse = msg.getPlayer().getGameSpace().getResourceManager().getWarehouse();
        strongBox = msg.getPlayer().getGameSpace().getResourceManager().getStrongBox();
        faithTrack = msg.getPlayer().getGameSpace().getFaithTrack();
        cardSpaces = msg.getPlayer().getGameSpace().getCardSpaces();
        leaderCards = msg.getPlayer().getLeaderCards();
        specialCards=msg.getPlayer().getSpecialCard();
        if(!stage.getScene().equals(personalBoardScene)) {
            try {
                setInitializeScene();
                setPersonalBoardScene();
                setMarketStructureScene();
                setDevCardTableScene();
                setOtherPersonalBoardScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setResizable(false);
            initializeSceneController.start();
            personalBoardSceneController.start();
            marketStructureSceneController.start();
            devCardTableSceneController.start();
            Platform.runLater(() -> changeScene(initializeScene));
        }
            personalBoardSceneController.updateCardSpacesView(cardSpaces);
            personalBoardSceneController.updateLeaderCards(leaderCards);
            personalBoardSceneController.updateWarehouseView(warehouse);
            personalBoardSceneController.updateStrongBoxView(strongBox);
            personalBoardSceneController.updateVictoryPointsView(player.getVictoryPoints());
            personalBoardSceneController.updateAdditionalPPView(player.getSpecialCard());
            personalBoardSceneController.updateFaithTrackView(faithTrack);
            if (soloMode){
                personalBoardSceneController.updateBlackFaithMarkerView(player.getGameSpace());
            }

    }

    /**
     * Alert the relative scene's controller to prepare a pop-up to allow the player to choose the
     * resource and, then, the depot.
     * @param msg VChooseResourceAndDepotMsg
     */
    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            if(stage.getScene().equals(initializeScene)) {
                initializeSceneController.setResourceToReceive(msg.getNumberOfResources());
                initializeSceneController.chooseResource();
            }else if(stage.getScene().equals(marketStructureScene)){
                marketStructureSceneController.chooseResource();
            }
        }
    }

    /**
     * In case the player choose a not valid depot, notify the relative scene's controller to prepare a pop-up
     * to choose the depot where to store the resource contained in the message.
     * @param msg VNotValidDepotMsg
     */
    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {
        System.out.println(msg.toString());
            if(stage.getScene().equals(initializeScene)){
                initializeSceneController.chooseDepot(msg);
            }else if(stage.getScene().equals(marketStructureScene)||stage.getScene().equals(personalBoardScene)){
                marketStructureSceneController.chooseDepot(msg);
            }
    }

    /**
     * To alert the relative scene's controller to allow to choose one or two leader cards based on the action.
     * @param msg VChooseLeaderCardRequestMsg
     */
    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            if (msg.getWhatFor().equals("initialization")) {
                initializeSceneController.chooseLeaderCard(msg);
            }else if(msg.getWhatFor().equals("active")||msg.getWhatFor().equals("remove")){
                System.out.println(msg.getWhatFor());
                personalBoardSceneController.chooseLeaderCard(msg);
            }
        }

    }

    /**
     * To show a message in the GUI of the clients saying that they have to wait the other players
     * @param msg VWaitOtherPlayerInitMsg
     */
    @Override
    public void receiveMsg(VWaitOtherPlayerInitMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            initializeSceneController.showWaitPane();
        }
    }

    /**
     * The game can start after the initialization, so the scene can be changed to the PersonalBoardScene
     * @param msg CGameCanStartMsg
     */
    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        System.out.println(msg.toString());
        Platform.runLater(()-> changeScene(personalBoardScene));
    }

    /**
     * To change the scene with the MarketStructureScene
     */
    public void seeMarketBoard(){
        Platform.runLater(()-> changeScene(marketStructureScene));
    }

    /**
     * To change the scene with the DevCardTableScene
     */
    public void seeDevCardTable(){
        Platform.runLater(()-> changeScene(devCardTableScene));
    }

    /**
     * To change the scene with the PersonalBoardScene
     */
    public void seePersonalBoard(){
        Platform.runLater(()-> changeScene(personalBoardScene));
    }

    /**
     * To change the scene with the OtherPersonalBoardScene
     */
    public void seeOtherPersonalBoard(){
        Platform.runLater(()-> changeScene(otherPersonalBoardScene));
    }

    /**
     * Alert the PersonalBoardSceneController to show a pop-up with the possible action the player can do.
     * @param msg VChooseActionTurnRequestMsg
     */
    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            if(stage.getScene().equals(initializeScene)){
                Platform.runLater(()->changeScene(personalBoardScene));
            }
            personalBoardSceneController.setLabelText(personalBoardSceneController.getActionLabel(),"");
            personalBoardSceneController.chooseAction(msg);
        }
    }

    /**
     * When the player choose to see another player personal board and which player,
     * this message contains all the request player info and are send to
     * the OtherPersonalBoardSceneController to update the view.
     * @param msg VAnotherPlayerInfoMsg
     */
    @Override
    public void receiveMsg(VAnotherPlayerInfoMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsernameAsking().equals(username)){
            otherPersonalBoardSceneController.update(msg.getPlayer());
            seeOtherPersonalBoard();
        }
    }

    /**
     * When the player choose to see another player personal board, this message
     * alerts the personal board to prepare a pop-up with a list of the player to choose.
     * @param msg VWhichPlayerRequestMsg
     */
    @Override
    public void receiveMsg(VWhichPlayerRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.chooseOtherPlayer(msg.getOtherPlayers());
        }
    }

    /**
     * When the player choose to buy from the market, the stage's scene change to the MarketStructureScene
     * and alerts its controller to enable the buttons to choose the row/column to choose.
     * @param msg VBuyFromMarketRequestMsg
     */
    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeMarketBoard();
            marketStructureSceneController.chooseRowColumn(msg);
        }
    }

    /**
     * To update the market view in the MarketStructureScene.
     * @param msg VUpdateMarketMsg
     */
    @Override
    public void receiveMsg(VUpdateMarketMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            marketStructureData=msg.getMarketUpdate();
            marketStructureSceneController.update(msg.getMarketUpdate());
        }
    }

    /**
     * To update the FaithTrack view in the PersonalBoardScene
     * @param msg VUpdateFaithTrackMsg
     */
    @Override
    public void receiveMsg(VUpdateFaithTrackMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            faithTrack=msg.getFaithTrack();
            personalBoardSceneController.updateFaithTrackView(msg.getFaithTrack());
        }
    }

    /**
     * When the player chooses to buy a card, the stage's scene change to the DevCardTableScene and
     * alerts its controller to prepare the table view with the possible choices or alerts the player that
     * he can't buy anything.
     * @param msg VChooseDevelopCardRequestMsg
     */
    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeDevCardTable();
            devCardTableSceneController.choose(msg);
        }
    }

    /**
     * When the player buy a card and puts it in a wrong card space, the scene is changed with the DevCardTableScene
     * and alerts its controller to prepare a pop-up to choose another card space.
     * @param msg VNotValidCardSpaceMsg
     */
    @Override
    public void receiveMsg(VNotValidCardSpaceMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeDevCardTable();
            devCardTableSceneController.chooseCardSpace(msg);
        }
    }

    /**
     * To update the table view of the DevCardTableScene
     * @param msg VUpdateDevTableMsg
     */
    @Override
    public void receiveMsg(VUpdateDevTableMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            developmentCardTable=msg.getUpdateTable();
            devCardTableSceneController.update(msg.getUpdateTable());
            personalBoardSceneController.updateCardSpacesView(msg.getUpdateCardSpace());
        }
    }

    /**
     * To set the EndGameScene and its controller from the FXML element
     * @throws IOException in case the FXML doesn't exist or is incorrect
     */
    public void setEndGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/EndGameScene.fxml"));
        endGameScene = new Scene(loader.load());
        endGameSceneController=loader.getController();
        endGameSceneController.setGui(this);
    }

    /**
     * When the player choose to move the resource in the Warehouse, this message alerts the PersonalBoardSceneController
     * to prepare the scene to allow to the player to choose where to move from and to.
     * @param msg VMoveResourceRequestMsg
     */
    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(getUsername())) {
            personalBoardSceneController.chooseDepots();
        }
    }

    /**
     * When the player receive some resources from the market, this message
     * sets in the MarketSceneController the resources received and the possible choices for the white marbles
     * and alerts the controller to prepare a pop-up to choose in which depot the player wants to store them
     * @param msg VChooseDepotMsg
     */
    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            marketStructureSceneController.setResourcesToStore(msg.getResourceToStore());
            marketStructureSceneController.setWhiteSpecial(msg.getWhiteSpecialResources());
            marketStructureSceneController.setResourceStored(false);
            marketStructureSceneController.chooseDepot();
        }
    }

    /**
     * In the SoloGame, update the position of the BlackFaithMarker of Lorenzo in the PersonalBoardScene.
     * @param msg VLorenzoIncreasedMsg
     */
    @Override
    public void receiveMsg(VLorenzoIncreasedMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            personalBoardSceneController.setLabelText(personalBoardSceneController.getActionLabel(),"Lorenzo's faithmarker has increased");
            personalBoardSceneController.updateBlackFaithMarkerView(msg.getPlayer().getGameSpace());
        }
    }

    /**
     * When the player choose to activate some production powers, this message alerts the PersonalBoardSceneController
     * to prepare the scene to allow to the player to choose where he wants to pay from and which
     * Production Power wants to activate.
     * @param msg VActivateProductionPowerRequestMsg
     */
    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.choosePP(msg);
        }
    }

    /**
     * To update the strongbox view in the PersonalBoardScene.
     * @param msg VUpdateStrongboxMsg
     */
    @Override
    public void receiveMsg(VUpdateStrongboxMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            strongBox=msg.getStrongBox();
            personalBoardSceneController.updateStrongBoxView(msg.getStrongBox());
        }
    }

    /**
     * When the game ends, the EndGameScene is set, prepared with the correct outcome
     * and, then, the stage's scene changes to it
     * @param msg VShowEndGameResultsMsg
     */
    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {
        System.out.println(msg.toString());
        if(msg.getWinnerUsername().equals(username)) {
            try {
                setEndGameScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
            endGameSceneController.start();
            endGameSceneController.setVictoryPoints(msg.getWinnerUsername(),msg.getVictoryPoints(),msg.getLosersUsernames(),soloMode);
            if(soloMode){
                endGameSceneController.showOutcome(msg.isSoloWin());
            }else {
                endGameSceneController.showOutcome(true);
            }
            Platform.runLater(()-> changeScene(endGameScene));
        }else {
            for(Player loser:msg.getLosersUsernames()) {
                if(loser.getUsername().equals(username)) {
                    try {
                        setEndGameScene();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    endGameSceneController.start();
                    endGameSceneController.setVictoryPoints(msg.getWinnerUsername(),msg.getVictoryPoints(),msg.getLosersUsernames(),soloMode);
                    endGameSceneController.showOutcome(false);
                    Platform.runLater(()-> changeScene(endGameScene));
                }
            }
        }
    }

    /**
     * To ask to the player if he wants to play another match in the EndGameScene.
     * @param msg VAskNewGameMsg
     */
    @Override
    public void receiveMsg(VAskNewGameMsg msg) {
        System.out.println(msg.toString());
        endGameSceneController.askNewGame();
    }

    /**
     * To alert the players that one player has just been disconnected.
     * @param msg CClientDisconnectedMsg
     */
    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        System.out.println(msg.toString());
        personalBoardSceneController.setWarningPane(msg);
    }

    /**
     * To update the leader cards view in the PersonalBoardScene.
     * @param msg VUpdateLeaderCards
     */
    @Override
    public void receiveMsg(VUpdateLeaderCards msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            leaderCards=msg.getLeaderCards();
            personalBoardSceneController.updateLeaderCards(msg.getLeaderCards());
        }
    }

    /**
     * To update the card spaces view in the PersonalBoardScene
     * @param msg VUpdateCardSpaces
     */
    @Override
    public void receiveMsg(VUpdateCardSpaces msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            cardSpaces=msg.getCardSpaces();
            personalBoardSceneController.updateCardSpacesView(msg.getCardSpaces());
        }
    }

    @Override
    public void receiveMsg(VNotValidMoveMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            if(stage.getScene().equals(marketStructureScene)){
                marketStructureSceneController.setWaitMove(false);
                marketStructureSceneController.moveNotValid();
            }else if(stage.getScene().equals(personalBoardScene)){
                personalBoardSceneController.setErrorMessage("Move not valid!");
            }
        }
    }

    /**
     * In the SoloGame, shows which action token was drawn in the PersonalBoardScene.
     * @param msg VActionTokenActivateMsg
     */
    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.updateLastActionToken(msg);
        }
    }

    /**
     * To alert the players that the server is no more available and prepare a message in every possible scene.
     * @param msg VServerUnableMsg
     */
    @Override
    public void receiveMsg(VServerUnableMsg msg) {
        System.out.println(msg.toString());
        serverAvailable=false;
        if(stage.getScene().equals(personalBoardScene)){
            personalBoardSceneController.setWarningPane(msg);
        }else if(stage.getScene().equals(introScene)){
            introSceneController.serverUnavailable();
        }else if(stage.getScene().equals(startScene)){
            startGameController.serverUnavailable();
        }else if(stage.getScene().equals(initializeScene)){
            initializeSceneController.setWarningPane(msg);
        }else if(stage.getScene().equals(marketStructureScene)){
            marketStructureSceneController.setWarningPane(msg);
        }else if(stage.getScene().equals(devCardTableScene)){
            devCardTableSceneController.setWarningPane(msg);
        }else if(stage.getScene().equals(lobbyScene)){
            lobbySceneController.serverUnable(msg);
        }
    }

    /**
     * To show to the players a message of waiting in the PersonalBoardScene.
     * @param msg VWaitYourTurnMsg
     */
    @Override
    public void receiveMsg(VWaitYourTurnMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.showWaitPane();
            if(!stage.getScene().equals(personalBoardScene)){
                personalBoardSceneController.updateCardSpacesView(cardSpaces);
                personalBoardSceneController.updateLeaderCards(leaderCards);
                personalBoardSceneController.updateWarehouseView(warehouse);
                personalBoardSceneController.updateStrongBoxView(strongBox);
                personalBoardSceneController.updateVictoryPointsView(player.getVictoryPoints());
                personalBoardSceneController.updateAdditionalPPView(player.getSpecialCard());
                personalBoardSceneController.updateFaithTrackView(player.getGameSpace().getFaithTrack());
                Platform.runLater(()->changeScene(personalBoardScene));
            }
        }
    }

    /**
     * To update the warehouse view in the PersonalBoardScene
     * @param msg VUpdateWarehouseMsg
     */
    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            warehouse=msg.getWarehouse();
            personalBoardSceneController.updateWarehouseView(msg.getWarehouse());
            if(personalBoardSceneController.getAction()!=null && personalBoardSceneController.getAction().equals(BUY_FROM_MARKET)){
                if(!marketStructureSceneController.isWaitMove()){
                    marketStructureSceneController.setResourceStored(true);
                }else{
                    marketStructureSceneController.setWaitMove(false);
                }
                marketStructureSceneController.chooseDepot();
            }
        }
    }

    /**
     * To update the count of victory points in the PersonalBoardScene
     * @param msg VUpdateVictoryPointsMsg
     */
    @Override
    public void receiveMsg(VUpdateVictoryPointsMsg msg) {
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.updateVictoryPointsView(msg.getUpdateVictoryPoints());
        }
    }

    /**
     * To change the scene in the stage
     * @param scene the new scene to show
     */
    public void changeScene(Scene scene){
        stage.setTitle("Masters of Renaissance");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * To close the GUI stage.
     */
    public void close(){
        stage.close();
        Platform.exit();
        client.closeConnection();
        System.exit(0);
    }

    /**
     * To send the message to the Server/MessageHandler
     * @param msg the message to send
     */
    public void sendMsg(GameMsg msg){
        if(!offline)
            client.sendMsg(msg);
        else
            messageHandler.receiveMsgForVV(msg);
    }

    /**
     * When the player try to connect and fails, set a message to show in the IntroScene
     * @param msg VNackConnectionRequestMsg
     */
    @Override
    public void receiveMsg(VNackConnectionRequestMsg msg) {
        System.out.println(msg.toString());
        switch(msg.getErrorInformation()){
            case "USER_NOT_VALID":  // if the username is already taken, the player has to insert a new one
                getIntroSceneController().userNotValid();
                break;
            case "FULL_SIZE":  //all the rooms in the server are full, so the client can't be connected to the game

                //Error(" Error, server is full ",stage);
                getIntroSceneController().serverIsFull();
                break;
            case "WAIT":      //in this case the server is not full so there are new rooms available, and the client has to wait because someone is creating a new room
                try {
                    Thread.sleep(5000);
                    /* the login process has to restart, so the client try again sending another request */
                    VVConnectionRequestMsg request2 = new VVConnectionRequestMsg("Trying to connect", iP, 0, username, gameSize);
                    this.client.sendMsg(request2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsernameIncreased().equals(username)){
            personalBoardSceneController.setLabelText(personalBoardSceneController.getActionLabel(),"Your faithmarker has increased");
        }else {
            personalBoardSceneController.setLabelText(personalBoardSceneController.getActionLabel(), msg.getUsernameIncreased() + "'s faithmarker has increased");
        }
    }

    //GETTER AND SETTER METHOD

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void setClient(ClientSocket client){this.client=client;}

    public void setUsername(String username){this.username=username;}

    public Scene getIntroScene() {
        return introScene;
    }

    public IntroSceneController getIntroSceneController() {
        return introSceneController;
    }

    public ClientSocket getClient() {
        return client;
    }

    public String getUsername() {
        return username;
    }

    public PlayerInterface getPlayer() {
        return player;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public LeaderCardDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    public MarketStructure getMarketStructureData() {
        return marketStructureData;
    }

    public DevelopmentCardTable getDevelopmentCardTable() {
        return developmentCardTable;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public ArrayList<CardSpace> getCardSpaces() {
        return cardSpaces;
    }

    public void setOffline(boolean offline){this.offline=offline;}

    public PersonalBoardSceneController getPersonalBoardSceneController() {
        return personalBoardSceneController;
    }

    public MarketStructureSceneController getMarketStructureSceneController() {
        return marketStructureSceneController;
    }

    public DevCardTableSceneController getDevCardTableSceneController() {
        return devCardTableSceneController;
    }

    public boolean isOffline() {
        return offline;
    }

    public boolean isReceiveMsg() {
        return receiveMsg;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setSoloMode(boolean soloMode){this.soloMode=soloMode;}

    public boolean getSoloMode(){return soloMode;}

    public boolean isServerAvailable() {
        return serverAvailable;
    }

    public void setIP(String iP) { this.iP = iP; }

    //Messages not used here

    @Override
    public void receiveMsg(CVStartInitializationMsg msg){
        //Message not used here
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VResourcesNotFoundMsg msg) {
        //Message not used here
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //Message not used here
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VStartWaitReconnectionMsg msg) {
        //Message not used here
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VStopWaitReconnectionMsg msg) {
       // Message not used here
        System.out.println(msg.toString());
    }
}

