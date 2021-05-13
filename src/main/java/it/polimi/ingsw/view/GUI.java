package it.polimi.ingsw.view;


import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.CVStartInitializationMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements ViewObserver {

    private Stage stage;

    private Scene introScene;
    private IntroSceneController introSceneController;

    private Scene lobbyScene;
    private LobbySceneController lobbySceneController;

    private Scene roomScene;
    private Stage roomStage;
    private RoomSceneController roomSceneController;

    private Scene preGameScene;
    private PreGameSceneController preGameSceneController;

    private Scene gameScene;
    private GameSceneController gameSceneController;

    private Scene endGameScene;
    private EndGameSceneController endGameSceneController;

    private ClientSocket client;
    private String username;
    private String iP;

    private String gameSize;
    private String[] args;

    public static void main(String[] args){ Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setResizable(false);

        setNewIntroScene();

        stage.setTitle("Masters of Renaissance");
        stage.setScene(introScene);
        stage.show();
        stage.setOnCloseRequest(e -> close());

    }

    private void setNewIntroScene() throws IOException {
        FXMLLoader loaderIntroScene = new FXMLLoader(getClass().getResource("/scenes/IntroScene.fxml"));
        introScene = new Scene(loaderIntroScene.load());
        introSceneController = loaderIntroScene.getController();
        introSceneController.setGui(this);
        introSceneController.start();
    }

    private void restartIntroScene() {
        try {
            setNewIntroScene();
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
        try {
            roomSizeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(VSendPlayerDataMsg msg) {

    }

    public void roomSizeRequest() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/RoomScene.fxml"));
        roomScene = new Scene(loader.load());
        roomStage = new Stage();
        roomStage.setScene(roomScene);
        roomSceneController=loader.getController();
        roomStage.show();
        roomSceneController.setGui(this);
    }

    public void setPreGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/PreGameScene.fxml"));
        preGameScene = new Scene(loader.load());
        preGameSceneController=loader.getController();
    }

    public void setGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/GameScene.fxml"));
        gameScene = new Scene(loader.load());
        gameSceneController=loader.getController();
    }

    public void setEndGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/EndGameScene.fxml"));
        endGameScene = new Scene(loader.load());
        endGameSceneController=loader.getController();
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

    public Scene getPreGameScene() {
        return preGameScene;
    }

    public PreGameSceneController getPreGameSceneController() {
        return preGameSceneController;
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

    @Override
    public void receiveMsg(VChooseActionTurnRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {

    }

    @Override
    public void receiveMsg(VUpdateWarehouseMsg msg) {

    }


    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {

    }

    @Override
    public void receiveMsg(VNotifyPositionIncreasedByMsg msg) {

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
    public void receiveMsg(VChooseDevelopCardRequestMsg msg) {

    }


    @Override
    public void receiveMsg(VMoveResourceRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VBuyFromMarketRequestMsg msg) {

    }


    @Override
    public void receiveMsg(VChooseDepotMsg msg) {

    }

    @Override
    public void receiveMsg(VActivateProductionPowerRequestMsg msg) {

    }


    @Override
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }

    @Override
    public void receiveMsg(VActionTokenActivateMsg msg) {

    }

    @Override
    public void receiveMsg(CVStartInitializationMsg msg) {

    }
}

