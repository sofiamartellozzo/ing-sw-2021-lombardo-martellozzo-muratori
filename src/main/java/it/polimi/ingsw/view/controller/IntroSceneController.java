package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class IntroSceneController {
    private GUI gui;

    @FXML
    private TextField ip;
    @FXML
    private TextField username;
    @FXML
    private Button playButton;

    @FXML
    private RadioButton localhostButton;
    @FXML
    private RadioButton onlineServerButton;
    @FXML
    private RadioButton customIPButton;
    @FXML
    private RadioButton singlePlayerModeButton;
    @FXML
    private RadioButton multiPlayerModeButton;

    private String selectedIP;
    private boolean customIP;
    private boolean singleGameMode;

    public String getSelectedIP() {
        return selectedIP;
    }

    public boolean isCustomIP() {
        return customIP;
    }

    public boolean getSingleGameMode() {
        return singleGameMode;
    }

    public void start(){
        selectedIP ="127.0.0.1";

        localhostButton.selectedProperty().setValue(true);
        onlineServerButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);

        singlePlayerModeButton.selectedProperty().setValue(true);
        multiPlayerModeButton.selectedProperty().setValue(false);
    }

    public void setGui(GUI gui){ this.gui=gui;}

    public void clickPlayButton() throws IOException {
        if(customIP){
            selectedIP=ip.getText();
            System.out.println("New selected IP: "+selectedIP);
        }

        disableAllLoginFields();

        String getUsername = username.getText().toLowerCase();
        System.out.println("New username gotten: "+getUsername);

        //Check username valid and ip valid, if yes try to connect, else show errors and enable fields

        ClientSocket client = new ClientSocket(selectedIP,gui);
        client.beginConnection();
        System.out.println("Client connected");
        gui.setClient(client);
        System.out.println("Client add to gui");
        String gameMode = null;
        if(singlePlayerModeButton.selectedProperty().getValue()){
            gameMode="0";
        }else if(multiPlayerModeButton.selectedProperty().getValue()){
            gameMode="1";
        }
        VVConnectionRequestMsg requestMsg = new VVConnectionRequestMsg("Request connection",selectedIP,0,getUsername,gameMode);
        client.sendMsg(requestMsg);
        new Thread(client).start();
    }

    public void disableAllLoginFields(){
        playButton.setDisable(true);
        ip.setDisable(true);
        username.setDisable(true);
    }

    public void enableAllLoginFields(){
        playButton.setDisable(false);
        ip.setDisable(false);
        username.setDisable(false);
    }

    public void clickLocalHostButton(){
        selectedIP="127.0.0.1";
        customIP=false;

        onlineServerButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);
    }

    public void clickOnlineServerButton(){
        //GET IP ONLINE SERVER
        customIP=false;

        localhostButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);
    }

    public void clickCustomIPButton(){
        customIP=true;

        localhostButton.selectedProperty().setValue(false);
        onlineServerButton.selectedProperty().setValue(false);
    }

    public void clickSinglePlayerModeButton(){
        singleGameMode=true;

        multiPlayerModeButton.selectedProperty().setValue(false);
    }

    public void clickMultiPlayerModeButton(){
        singleGameMode=false;
        singlePlayerModeButton.selectedProperty().setValue(false);
    }

}
