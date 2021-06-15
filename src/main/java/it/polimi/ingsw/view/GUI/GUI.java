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
    private String[] args;
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

    private void setStartScene() throws IOException {
        FXMLLoader loaderStartScene = new FXMLLoader(getClass().getResource("/scenes/InitWindowScene.fxml"));
        startScene = new Scene(loaderStartScene.load());
        startGameController = loaderStartScene.getController();
        startGameController.setGui(this);
        //startGameController.start();
    }

    private void setIntroScene() throws IOException {
        FXMLLoader loaderIntroScene = new FXMLLoader(getClass().getResource("/scenes/IntroScene.fxml"));
        introScene = new Scene(loaderIntroScene.load());
        introSceneController = loaderIntroScene.getController();
        introSceneController.setGui(this);
        //introSceneController.start();
    }

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

    public void setLobbyScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/LobbyScene.fxml"));
        lobbyScene = new Scene(loader.load());
        lobbySceneController=loader.getController();
    }

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

    public void roomSizeRequest(String idRoom) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/RoomScene.fxml"));
        roomScene = new Scene(loader.load());
        roomSceneController=loader.getController();
        changeScene(roomScene);
        roomSceneController.setGui(this);
        roomSceneController.setIdRoom(idRoom);
    }

    public void roomSizeResponse(int choice,String idRoom){
        CRoomSizeResponseMsg responseMsg = new CRoomSizeResponseMsg("Room size chosen: "+choice,choice,getUsername(),idRoom);
        client.sendMsg(responseMsg);
    }


    @Override
    public void receiveMsg(VRoomInfoMsg msg) {
        System.out.println(msg.toString());
        Platform.runLater(()-> {
            lobbySceneController.updateLobby(msg);
            changeScene(lobbyScene);
        });
    }

    public void setInitializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/InitializeScene.fxml"));
        initializeScene = new Scene(loader.load());
        initializeSceneController =loader.getController();
        initializeSceneController.setGui(this);
    }

    public void setPersonalBoardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/PersonalBoardScene.fxml"));
        personalBoardScene = new Scene(loader.load());
        personalBoardSceneController=loader.getController();
        personalBoardSceneController.setGui(this);
    }


    public void setMarketStructureScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/MarketStructureScene.fxml"));
        marketStructureScene=new Scene(loader.load());
        marketStructureSceneController=loader.getController();
        marketStructureSceneController.setGui(this);
    }

    public void setDevCardTableScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/DevCardTableScene.fxml"));
        devCardTableScene=new Scene(loader.load());
        devCardTableSceneController = loader.getController();
        devCardTableSceneController.setGui(this);
    }
    private void setOtherPersonalBoardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/OtherPersonalBoardScene.fxml"));
        otherPersonalBoardScene = new Scene(loader.load());
        otherPersonalBoardSceneController=loader.getController();
        otherPersonalBoardSceneController.setGui(this);
    }

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
            otherPersonalBoardSceneController.start();
            Platform.runLater(() -> {
                changeScene(initializeScene);
            });
        }else{
            personalBoardSceneController.updateCardSpacesView(cardSpaces);
            personalBoardSceneController.updateLeaderCards(leaderCards);
            personalBoardSceneController.updateWarehouseView(warehouse);
            personalBoardSceneController.updateStrongBoxView(strongBox);
            personalBoardSceneController.updateVictoryPointsView(player.getVictoryPoints());
            personalBoardSceneController.updateAdditionalPPView(player.getSpecialCard());
        }
    }



    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            if(stage.getScene().equals(initializeScene)) {
                initializeSceneController.chooseResource();
            }else if(stage.getScene().equals(marketStructureScene)){
                marketStructureSceneController.chooseResource();
            }
        }
    }

    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {
        System.out.println(msg.toString());
            if(stage.getScene().equals(initializeScene)){
                initializeSceneController.chooseDepot(msg);
            }else if(stage.getScene().equals(marketStructureScene)||stage.getScene().equals(personalBoardScene)){
                marketStructureSceneController.chooseDepot(msg);
            }
    }


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

    @Override
    public void receiveMsg(VWaitOtherPlayerInitMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            initializeSceneController.showWaitPane();
        }
    }

    @Override
    public void receiveMsg(CGameCanStartMsg msg) {
        System.out.println(msg.toString());
        Platform.runLater(()->{
            changeScene(personalBoardScene);
        });
    }

    public void seeMarketBoard(){
        Platform.runLater(()->{
            changeScene(marketStructureScene);
        });
    }

    public void seeDevCardTable(){
        Platform.runLater(()->{
            changeScene(devCardTableScene);
        });
    }
    public void seePersonalBoard(){
        Platform.runLater(()->{
            changeScene(personalBoardScene);
        });
    }

    public void seeOtherPersonalBoard(){
        Platform.runLater(()->{
            changeScene(otherPersonalBoardScene);
        });
    }

    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.chooseAction(msg);
        }
    }

    @Override
    public void receiveMsg(VAnotherPlayerInfoMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsernameAsking().equals(username)){
            otherPersonalBoardSceneController.update(msg.getPlayer());
            seeOtherPersonalBoard();
        }
    }

    @Override
    public void receiveMsg(VWhichPlayerRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.chooseOtherPlayer(msg.getOtherPlayers());
        }
    }


    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeMarketBoard();
            marketStructureSceneController.chooseRowColumn(msg);
        }
    }

    @Override
    public void receiveMsg(VUpdateMarketMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            marketStructureData=msg.getMarketUpdate();
            marketStructureSceneController.update(msg.getMarketUpdate());
        }
    }

    @Override
    public void receiveMsg(VUpdateFaithTrackMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            faithTrack=msg.getFaithTrack();
            personalBoardSceneController.updateFaithTrackView(msg.getFaithTrack(),player.getGameSpace());
        }
    }

    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeDevCardTable();
            devCardTableSceneController.choose(msg);
        }
    }

    @Override
    public void receiveMsg(VNotValidCardSpaceMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            seeDevCardTable();
            devCardTableSceneController.chooseCardSpace(msg);
        }
    }

    @Override
    public void receiveMsg(VUpdateDevTableMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            developmentCardTable=msg.getUpdateTable();
            devCardTableSceneController.update(msg.getUpdateTable());
            personalBoardSceneController.updateCardSpacesView(msg.getUpdateCardSpace());
        }
    }

    public void setEndGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/EndGameScene.fxml"));
        endGameScene = new Scene(loader.load());
        endGameSceneController=loader.getController();
        endGameSceneController.setGui(this);
    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg){
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(getUsername())) {
            personalBoardSceneController.chooseDepots();
        }
    }

    @Override
    public void receiveMsg(VChooseDepotMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            marketStructureSceneController.setResourcesToStore(msg.getResourceToStore());
            marketStructureSceneController.setWhiteSpecial(msg.getWhiteSpecialResources());
            marketStructureSceneController.chooseDepot();
        }
    }

    @Override
    public void receiveMsg(VLorenzoIncreasedMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)) {
            personalBoardSceneController.updateFaithTrackView(msg.getPlayer().getGameSpace().getFaithTrack(),msg.getPlayer().getGameSpace());
        }
    }

    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.choosePP(msg);
        }
    }

    @Override
    public void receiveMsg(VResourcesNotFoundMsg msg) {
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VUpdateStrongboxMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            strongBox=msg.getStrongBox();
            personalBoardSceneController.updateStrongBoxView(msg.getStrongBox());
        }
    }

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
            endGameSceneController.showOutcome(true);
            Platform.runLater(()->{
                changeScene(endGameScene);
            });
        }else {
            for(Player loser:msg.getLosersUsernames()) {
                if(loser.getUsername().equals(username)) {
                    try {
                        setEndGameScene();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    endGameSceneController.start();
                    endGameSceneController.showOutcome(false);
                    Platform.runLater(()->{
                        changeScene(endGameScene);
                    });
                }
            }
        }
    }

    @Override
    public void receiveMsg(VAskNewGameMsg msg) {
        System.out.println(msg.toString());
        endGameSceneController.askNewGame();
    }

    @Override
    public void receiveMsg(CCloseRoomMsg msg) {
        //in server
    }

    @Override
    public void receiveMsg(CClientDisconnectedMsg msg) {
        System.out.println(msg.toString());
        personalBoardSceneController.setWarningPane(msg);
    }

    @Override
    public void receiveMsg(VStartWaitReconnectionMsg msg) {
        System.out.println(msg.toString());
        /*
        * NOT IMPLEMENTED*/
    }

    @Override
    public void receiveMsg(VStopWaitReconnectionMsg msg) {
        /*
         * NOT IMPLEMENTED
         */
    }

    @Override
    public void receiveMsg(VUpdateLeaderCards msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            leaderCards=msg.getLeaderCards();
            for(LeaderCard leaderCard:leaderCards) {
                System.out.println(leaderCard.getCardID());
            }
            personalBoardSceneController.updateLeaderCards(msg.getLeaderCards());
        }
    }

    @Override
    public void receiveMsg(VUpdateCardSpaces msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            cardSpaces=msg.getCardSpaces();
            personalBoardSceneController.updateCardSpacesView(msg.getCardSpaces());
        }
    }

    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.updateLastActionToken(msg);
        }
    }

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
        }
    }



    @Override
    public void receiveMsg(VWaitYourTurnMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.showWaitPane();
        }
    }

    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {
        System.out.println(msg.toString());
        if(msg.getUsername().equals(username)){
            warehouse=msg.getWarehouse();
            personalBoardSceneController.updateWarehouseView(msg.getWarehouse());
        }
    }

    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {
        System.out.println(msg.toString());
    }

    @Override
    public void receiveMsg(VUpdateVictoryPointsMsg msg) {
        if(msg.getUsername().equals(username)){
            personalBoardSceneController.updateVictoryPointsView(msg.getUpdateVictoryPoints());
        }
    }

    public void changeScene(Scene scene){
        stage.setTitle("Masters of Renaissance");
        stage.setScene(scene);
        stage.show();
    }

    public void close(){
        stage.close();
        Platform.exit();
        client.closeConnection();
        System.exit(0);
    }

    public void sendMsg(GameMsg msg){
        if(!offline)
            client.sendMsg(msg);
        else
            messageHandler.receiveMsgForVV(msg);
    }


    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void setClient(ClientSocket client){this.client=client;}

    public void setUsername(String username){this.username=username;}

    public Stage getStage() {
        return stage;
    }

    public StartGameController getStartGameController() {
        return startGameController;
    }

    public void setStartGameController(StartGameController startGameController) {
        this.startGameController = startGameController;
    }

    public Scene getIntroScene() {
        return introScene;
    }

    public IntroSceneController getIntroSceneController() {
        return introSceneController;
    }

    public Scene getLobbyScene() {
        return lobbyScene;
    }

    public LobbySceneController getLobbySceneController() {
        return lobbySceneController;
    }

    public Scene getRoomScene() {
        return roomScene;
    }

    public RoomSceneController getRoomSceneController() {
        return roomSceneController;
    }

    public Scene getInitializeScene() {
        return initializeScene;
    }

    public InitializeSceneController getInitializeSceneController() {
        return initializeSceneController;
    }

    public Scene getPersonalBoardScene() {
        return personalBoardScene;
    }

    public PersonalBoardSceneController getGameSceneController() {
        return personalBoardSceneController;
    }

    public Scene getEndGameScene() {
        return endGameScene;
    }

    public EndGameSceneController getEndGameSceneController() {
        return endGameSceneController;
    }

    public ClientSocket getClient() {
        return client;
    }

    public String getUsername() {
        return username;
    }

    public String getIP() {
        return iP;
    }

    public String getGameSize() {
        return gameSize;
    }

    public String[] getArgs() {
        return args;
    }

    public String getiP() {
        return iP;
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

    public Scene getMarketStructureScene() {
        return marketStructureScene;
    }

    public MarketStructureSceneController getMarketStructureSceneController() {
        return marketStructureSceneController;
    }

    public Scene getDevCardTableScene() {
        return devCardTableScene;
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

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

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

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ArrayList<SpecialCard> getSpecialCards(){return specialCards;}

    public void setSoloMode(boolean soloMode){this.soloMode=soloMode;}

    public boolean getSoloMode(){return soloMode;}

    public boolean isServerAvailable() {
        return serverAvailable;
    }

    public void setServerAvailable(boolean serverAvailable) {
        this.serverAvailable = serverAvailable;
    }
}

