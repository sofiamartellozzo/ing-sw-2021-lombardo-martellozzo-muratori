package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.controller.MessageHandler;
import it.polimi.ingsw.message.ObserverType;
import it.polimi.ingsw.message.viewMsg.VVConnectionRequestMsg;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private RadioButton customIPButton;

    @FXML
    private RadioButton singlePlayerModeButton;
    @FXML
    private RadioButton multiPlayerModeButton;

    @FXML
    private Label errorMessage;

    @FXML
    private ProgressIndicator loadingIndicator;

    private String selectedIP;
    private boolean customIP;

    public void start(){
        selectedIP ="127.0.0.1";

        loadingIndicator.setVisible(false);
        if(gui.isOffline()){
            localhostButton.setVisible(false);
            customIPButton.setVisible(false);
            ip.setDisable(true);

            singlePlayerModeButton.setVisible(false);
            multiPlayerModeButton.setVisible(false);
            playButton.setDisable(false);

        }else {
            localhostButton.selectedProperty().setValue(false);
            customIPButton.selectedProperty().setValue(false);


            singlePlayerModeButton.selectedProperty().setValue(false);
            multiPlayerModeButton.selectedProperty().setValue(false);
            playButton.setDisable(true);
        }

        errorMessage.setVisible(false);
    }

    public void setGui(GUI gui){ this.gui=gui;}

    public void clickPlayButton() throws IOException {
            if (!gui.isOffline() && customIP) {
                selectedIP = ip.getText();
                System.out.println("New selected IP: " + selectedIP);
            }


        loadingIndicator.setVisible(true);

        disableAllLoginFields();


        String getUsername = username.getText().toLowerCase();
        System.out.println("New username gotten: "+getUsername);

        if(getUsername!=null && !getUsername.equals("") && selectedIP.length()>=7 && selectedIP.length()<=15 && !gui.isOffline()/*&& !offlineButton.isSelected()*/) {
            //gui.setOffline(false);
            ClientSocket client = new ClientSocket(selectedIP, gui);
            client.beginConnection();
            System.out.println("Client connected");
            gui.setClient(client);
            System.out.println("Client add to gui");
            String gameMode = null;
            if (singlePlayerModeButton.selectedProperty().getValue()) {
                gameMode = "0";
                gui.setSoloMode(true);
            } else if (multiPlayerModeButton.selectedProperty().getValue()) {
                gameMode = "1";
                gui.setSoloMode(false);
            }
            VVConnectionRequestMsg requestMsg = new VVConnectionRequestMsg("Request connection", selectedIP, 0, getUsername, gameMode);
            client.sendMsg(requestMsg);
            new Thread(client).start();
            gui.setUsername(getUsername);
        }else if(getUsername!=null && !getUsername.equals("") && selectedIP.length()>=7 && selectedIP.length()<=15 && gui.isOffline() /*&& offlineButton.isSelected()*/){
            /*create the message handler, he work as Client Socket but not throw the net*/
            //gui.setOffline(true);
            MessageHandler messageHandler = new MessageHandler();
            gui.setMessageHandler(messageHandler);
            /*generate a sort of Virtual View*/
            messageHandler.generateVV(getUsername);
            /*
            offlineVirtualView = new VirtualView(username);
            attachObserver(ObserverType.VIEW, offlineVirtualView);
            attachObserver(ObserverType.CONTROLLER, offlineVirtualView);*/
            //this.offlineVirtualView.attachObserver(ObserverType.VIEW, this);
            messageHandler.attachObserver(ObserverType.VIEW, gui);
            //printCLIMessage("before run");
            new Thread(messageHandler).start();
            //printCLIMessage("after run");
            /* try to create the connection sending the username, port and ip */
            VVConnectionRequestMsg request = new VVConnectionRequestMsg("OFFLINE", getUsername);
            gui.sendMsg(request);
            gui.setUsername(getUsername);
        }else{
            setLabelText(errorMessage,"Username already used or IP not valid!");
            errorMessage.setVisible(true);
            enableAllLoginFields();
            loadingIndicator.setVisible(false);
        }
    }

    public void disableAllLoginFields(){
        playButton.setDisable(true);
        ip.setDisable(true);
        username.setDisable(true);

        customIPButton.setDisable(true);
        multiPlayerModeButton.setDisable(true);
        singlePlayerModeButton.setDisable(true);
        localhostButton.setDisable(true);
    }

    public void enableAllLoginFields(){
        playButton.setDisable(false);
        ip.setDisable(false);
        username.setDisable(false);
        customIPButton.setDisable(false);

        multiPlayerModeButton.setDisable(false);
        singlePlayerModeButton.setDisable(false);
        localhostButton.setDisable(false);
    }

    public void clickLocalHostButton(){

        if(!gui.isOffline()) {
            selectedIP = "127.0.0.1";
            customIP = false;

            ip.setDisable(true);

            customIPButton.selectedProperty().setValue(false);

            if (singlePlayerModeButton.isDisable() || multiPlayerModeButton.isDisable()) {
                singlePlayerModeButton.setDisable(false);
                multiPlayerModeButton.setDisable(false);
            }
            playButton.setDisable(!singlePlayerModeButton.isSelected() && !multiPlayerModeButton.isSelected());
        }
    }

    /*public void clickOnlineServerButton(){
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
        offlineButton.selectedProperty().setValue(false);
        if(singlePlayerModeButton.isDisable()||multiPlayerModeButton.isDisable()) {
            singlePlayerModeButton.setDisable(false);
            multiPlayerModeButton.setDisable(false);
        }
        playButton.setDisable(!singlePlayerModeButton.isSelected() && !multiPlayerModeButton.isSelected());
    }*/

    public void clickCustomIPButton(){
        if(!gui.isOffline()) {
            customIP = true;

            ip.setDisable(false);
            localhostButton.selectedProperty().setValue(false);

            if (singlePlayerModeButton.isDisable() || multiPlayerModeButton.isDisable()) {
                singlePlayerModeButton.setDisable(false);
                multiPlayerModeButton.setDisable(false);
            }
            playButton.setDisable(!singlePlayerModeButton.isSelected() && !multiPlayerModeButton.isSelected());
        }
    }

    /*public void clickOfflineButton(){
        playButton.setDisable(false);
        customIP=false;

        ip.setDisable(true);
        localhostButton.selectedProperty().setValue(false);
        onlineServerButton.selectedProperty().setValue(false);
        customIPButton.selectedProperty().setValue(false);
        singlePlayerModeButton.setDisable(true);
        multiPlayerModeButton.setDisable(true);
    }*/

    public void clickSinglePlayerModeButton(){
        multiPlayerModeButton.selectedProperty().setValue(false);
        playButton.setDisable(!customIPButton.isSelected() && !localhostButton.isSelected());
    }

    public void clickMultiPlayerModeButton(){
        singlePlayerModeButton.selectedProperty().setValue(false);
        playButton.setDisable(!customIPButton.isSelected() && !localhostButton.isSelected());
    }

    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }

    public void serverUnavailable() {
        errorMessage.setVisible(true);
        setLabelText(errorMessage,"The server is unable!\n" +
                "You'll play in offline mode!");
        ip.setDisable(true);
        gui.setOffline(true);
        try {
            clickPlayButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userNotValid(){
        errorMessage.setVisible(true);
        setLabelText(errorMessage,"Username already taken!");
        enableAllLoginFields();
        loadingIndicator.setVisible(false);
    }

    public void serverIsFull(){
        errorMessage.setVisible(true);
        setLabelText(errorMessage,"The server is full!\n" +
                "You'll play in offline mode!");
        ip.setDisable(true);
        gui.setOffline(true);
        try {
            clickPlayButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
