package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

        singlePlayerModeButton.selectedProperty().setValue(false);
        multiPlayerModeButton.selectedProperty().setValue(true);
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

        if(getUsername!=null && !getUsername.equals("") && selectedIP.length()>=7 && selectedIP.length()<=15) {
            ClientSocket client = new ClientSocket(selectedIP, gui);
            client.beginConnection();
            System.out.println("Client connected");
            gui.setClient(client);
            System.out.println("Client add to gui");
            String gameMode = null;
            if (singlePlayerModeButton.selectedProperty().getValue()) {
                gameMode = "0";
            } else if (multiPlayerModeButton.selectedProperty().getValue()) {
                gameMode = "1";
            }
            VVConnectionRequestMsg requestMsg = new VVConnectionRequestMsg("Request connection", selectedIP, 0, getUsername, gameMode);
            client.sendMsg(requestMsg);
            new Thread(client).start();
            gui.setUsername(getUsername);
        }else{
            //ERROR MESSAGE Username or Ip not valid
            enableAllLoginFields();
        }
    }

    public void disableAllLoginFields(){
        playButton.setDisable(true);
        ip.setDisable(true);
        username.setDisable(true);

        customIPButton.setDisable(true);
        onlineServerButton.setDisable(true);
        multiPlayerModeButton.setDisable(true);
        singlePlayerModeButton.setDisable(true);
        localhostButton.setDisable(true);
    }

    public void enableAllLoginFields(){
        playButton.setDisable(false);
        ip.setDisable(false);
        username.setDisable(false);
        customIPButton.setDisable(false);
        onlineServerButton.setDisable(false);
        multiPlayerModeButton.setDisable(false);
        singlePlayerModeButton.setDisable(false);
        localhostButton.setDisable(false);
    }

    public void clickLocalHostButton(){
        playButton.setDisable(false);
        selectedIP="127.0.0.1";
        customIP=false;

        ip.setDisable(true);
        onlineServerButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);
    }

    public void clickOnlineServerButton(){
        playButton.setDisable(false);
        customIP=false;

        try{
            selectedIP= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Not possible to get IP Address Online Server");
            playButton.setDisable(true);
        }

        ip.setDisable(true);
        localhostButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);
    }

    public void clickCustomIPButton(){
        playButton.setDisable(false);
        customIP=true;

        ip.setDisable(false);
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
