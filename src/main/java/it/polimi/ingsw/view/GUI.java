package it.polimi.ingsw.view;


import it.polimi.ingsw.message.ViewObserver;
import it.polimi.ingsw.message.controllerMsg.CNackConnectionRequestMsg;
import it.polimi.ingsw.message.viewMsg.*;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.controller.IntroSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
* INTROSCENE
* LOBBYSCENE
* ROOMSIZESCENE
* BOARDSCENE
* PREGAMESCENE
* OUTCOMESCENE*/

public class GUI extends Application implements ViewObserver {

    private Stage stage;

    private Scene introScene;
    private IntroSceneController introSceneController;

    private Scene gameScene;
    //gameSceneController;

    private ClientSocket client;
    private String username;
    private String iP;

    private String gameSize;
    private String[] args;

    public static void main(String[] args){ Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        /* Check Ping */

        this.stage = stage;
        stage.setResizable(false);

        setNewIntroScene();

        //LOBBY

        stage.setTitle("Masters of Renaissance");
        stage.setScene(introScene);
        stage.show();
        stage.setOnCloseRequest(e -> close());

    }

    private void setNewIntroScene() throws IOException {
        FXMLLoader loaderIntroScene = new FXMLLoader(getClass().getResource("/scenes/IntroScene.fxml"));
        introScene = new Scene(loaderIntroScene.load());
        introSceneController = loaderIntroScene.getController();
        //PROBLEMA controller == null
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

    public void changeScene(Scene scene){
        stage.setTitle("Masters of Renaissance");
        stage.setScene(scene);
        stage.show();
    }

    public void close(){
        stage.close();
        Platform.exit();
        if(true/*client is connected*/){
            client.closeConnection();
        }
        System.exit(0);
    }

    public void setClient(ClientSocket client){this.client=client;}
    @Override
    public void receiveMsg(CNackConnectionRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VRoomSizeRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseLeaderCardRequestMsg msg) {

    }

    @Override
    public void receiveMsg(VChooseResourceAndDepotMsg msg) {

    }

    @Override
    public void receiveMsg(VNotifyAllIncreasePositionMsg msg) {

    }

    @Override
    public void receiveMsg(VNotValidDepotMsg msg) {

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
    public void receiveMsg(VShowEndGameResultsMsg msg) {

    }
}
