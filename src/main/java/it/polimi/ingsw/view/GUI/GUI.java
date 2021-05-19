package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.connection.VServerUnableMsg;
import it.polimi.ingsw.message.updateMsg.*;
import it.polimi.ingsw.message.controllerMsg.CRoomSizeResponseMsg;
import it.polimi.ingsw.message.controllerMsg.ControllerGameMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.model.BoardManager;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.resourceManagement.StrongBox;
import it.polimi.ingsw.model.board.resourceManagement.Warehouse;
import it.polimi.ingsw.model.card.DevelopmentCardTable;
import it.polimi.ingsw.model.card.LeaderCardDeck;
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

    private Scene introScene;
    private IntroSceneController introSceneController;

    private Scene lobbyScene;
    private LobbySceneController lobbySceneController;

    private Scene roomScene;
    private Stage roomStage;
    private RoomSceneController roomSceneController;

    private Scene initializeScene;
    private InitializeSceneController initializeSceneController;

    private Scene gameScene;
    private GameSceneController gameSceneController;

    private Scene endGameScene;
    private EndGameSceneController endGameSceneController;

    private ClientSocket client;
    private String username;
    private String iP;

    private String gameSize;
    private String[] args;

    private PlayerInterface player;
    private BoardManager boardManager;
    private LeaderCardDeck leaderCards;
    private MarketStructure marketStructureData;
    private DevelopmentCardTable developmentCardTable;
    private Warehouse warehouse;
    private StrongBox strongBox;
    private FaithTrack faithTrack;
    private ArrayList<CardSpace> cardSpaces;

    public static void main(String[] args){ Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setResizable(false);

        setIntroScene();
        setLobbyScene();

        stage.setTitle("Masters of Renaissance");
        stage.setScene(introScene);
        stage.show();
        stage.setOnCloseRequest(e -> close());

    }

    private void setIntroScene() throws IOException {
        FXMLLoader loaderIntroScene = new FXMLLoader(getClass().getResource("/scenes/IntroScene.fxml"));
        introScene = new Scene(loaderIntroScene.load());
        introSceneController = loaderIntroScene.getController();
        introSceneController.setGui(this);
        introSceneController.start();
    }

    private void restartIntroScene() {
        try {
            setIntroScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(introScene);
    }

    public void setLobbyScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/LobbyScene.fxml"));
        lobbyScene = new Scene(loader.load());
        lobbySceneController=loader.getController();
    }

    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {
            Platform.runLater(() -> {
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
        roomStage = new Stage();
        roomStage.setResizable(false);
        roomStage.setScene(roomScene);
        roomStage.show();
        roomSceneController.setGui(this);
        roomSceneController.setIdRoom(idRoom);
    }

    public void roomSizeResponse(int choice,String idRoom){
        roomStage.close();
        CRoomSizeResponseMsg responseMsg = new CRoomSizeResponseMsg("Room size chosen: "+choice,choice,getUsername(),idRoom);
        client.sendMsg(responseMsg);
    }


    @Override
    public void receiveMsg(VRoomInfoMsg vRoomInfoMsg) {
        Platform.runLater(() -> {
            lobbySceneController.updateLobby(vRoomInfoMsg);
            changeScene(lobbyScene);
        });

    }

    public void setInitializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/InitializeScene.fxml"));
        initializeScene = new Scene(loader.load());
        initializeSceneController =loader.getController();
        initializeSceneController.setGui(this);
        initializeSceneController.setPlayer(player);
    }



    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {
        player = msg.getPlayer();
        boardManager = msg.getBoardManager();
        marketStructureData = msg.getBoardManager().getMarketStructure();
        leaderCards = msg.getBoardManager().getLeaderCardDeck();
        developmentCardTable = msg.getBoardManager().getDevelopmentCardTable();
        warehouse = msg.getPlayer().getGameSpace().getResourceManager().getWarehouse();
        strongBox = msg.getPlayer().getGameSpace().getResourceManager().getStrongBox();
        faithTrack = msg.getPlayer().getGameSpace().getFaithTrack();
        cardSpaces = msg.getPlayer().getGameSpace().getCardSpaces();
    }

    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {
        try {
            setInitializeScene();
        }catch (IOException e){
            e.printStackTrace();
        }
        initializeSceneController.start();
        Platform.runLater(() -> {
            stage.setResizable(true);
            changeScene(initializeScene);
            if(msg.getUsername().equals(username)) {
                initializeSceneController.chooseResource(msg);
            }
        });

    }

    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {
        Platform.runLater(() -> {
            if(stage.getScene().equals(initializeScene)){
                //Ask again depot initializeController

            }else if(stage.getScene().equals(gameScene)){
                //Ask again depot from gameController
            }
        });
    }

    @Override
    public void receiveMsg(CGameCanStratMsg msg) {

    }

    public void setGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/GameScene.fxml"));
        gameScene = new Scene(loader.load());
        gameSceneController=loader.getController();
        gameSceneController.setGui(this);
    }

    public void setEndGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/EndGameScene.fxml"));
        endGameScene = new Scene(loader.load());
        endGameSceneController=loader.getController();
        endGameSceneController.setGui(this);
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

    public void sendMsg(ControllerGameMsg msg){
        client.sendMsg(msg);
    }

    public void setClient(ClientSocket client){this.client=client;}

    public void setUsername(String username){this.username=username;}

    public Stage getStage() {
        return stage;
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

    public Stage getRoomStage() {
        return roomStage;
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

    public Scene getGameScene() {
        return gameScene;
    }

    public GameSceneController getGameSceneController() {
        return gameSceneController;
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

    public LeaderCardDeck getLeaderCards() {
        return leaderCards;
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

    @Override
    public void receiveMsg(VNackConnectionRequestMsg msg) {
        switch(msg.getErrorInformation()){
            case "USER_NOT_VALID":  // if the username is already taken, the player has to insert a new one

                //Error(" Error, this username is not valid because it is already taken",stage);
                getIntroSceneController().enableAllLoginFields();
            case "FULL_SIZE":  //all the rooms in the server are full, so the client can't be connected to the game

                //Error(" Error, server is full ",stage);
                break;
            case "WAIT":      //in this case the server is not full so there are new rooms available, and the client has to wait because someone is creating a new room
                //Error(" Someone is now creating a new room! Please wait a moment ",stage);
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
    public void receiveMsg(CVStartInitializationMsg msg){

    }

    @Override
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VUpdateMarketMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseDepotMsg msg) {

    }

    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VUpdateStrongboxMsg msg) {

    }


    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }

    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {

    }

    @Override
    public void receiveMsg(VServerUnableMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VWaitYourTurnMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VWaitOtherPlayerInitMsg msg) {

    }

    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {

    }

    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {

    }
}

