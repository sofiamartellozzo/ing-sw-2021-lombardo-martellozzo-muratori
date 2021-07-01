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

/**
 * After choosing if ONLINE/OFFLINE game, the stage shows this scene, where the player can choose if the wants
 * to play in Single/MultiPlayer, with which IP and username.
 */
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

    /**
     * To prepare the scene when it is set
     */
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

    /**
     * To set the GUI
     * @param gui The GUI of the Player
     */
    public void setGui(GUI gui){ this.gui=gui;}

    /**
     * Whene the PlayButton is clicked, this method disables every field and checks what the player
     * has just inserted through the scene fields.
     * If something is incorrect the scene enables again every field and alert the client.
     * If not, try to connect to the server/message handler.
     * @throws IOException if something went wrong when the connection message is send.
     */
    public void clickPlayButton() throws IOException {
            if (!gui.isOffline() && customIP) {
                selectedIP = ip.getText();
            }


        loadingIndicator.setVisible(true);

        disableAllLoginFields();


        String getUsername = username.getText().toLowerCase();

        if(getUsername!=null && !getUsername.equals("") && selectedIP.length()>=7 && selectedIP.length()<=15 && !gui.isOffline()/*&& !offlineButton.isSelected()*/) {
            //gui.setOffline(false);
            ClientSocket client = new ClientSocket(selectedIP, gui);
            client.beginConnection();
            gui.setClient(client);
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
            gui.setIP(selectedIP);
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
            gui.setSoloMode(true);
        }else{
            setLabelText(errorMessage,"Username already used or IP not valid!");
            errorMessage.setVisible(true);
            enableAllLoginFields();
            loadingIndicator.setVisible(false);
        }
    }

    /**
     * To disable all scene's fields
     */
    public void disableAllLoginFields(){
        playButton.setDisable(true);
        ip.setDisable(true);
        username.setDisable(true);

        customIPButton.setDisable(true);
        multiPlayerModeButton.setDisable(true);
        singlePlayerModeButton.setDisable(true);
        localhostButton.setDisable(true);
    }

    /**
     * To enable all scene's fields
     */
    public void enableAllLoginFields(){
        playButton.setDisable(false);
        ip.setDisable(false);
        username.setDisable(false);
        customIPButton.setDisable(false);

        multiPlayerModeButton.setDisable(false);
        singlePlayerModeButton.setDisable(false);
        localhostButton.setDisable(false);
    }

    /**
     * When local host button is clicked, the IP field is disabled and the other button are set to false.
     */
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

    /**
     * When custom IP button is clicked, the IP field is enabled and the other button are set to false.
     */
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

    /**
     * When the single player mode button is clicked, the multi player mode is disabled
     */
    public void clickSinglePlayerModeButton(){
        multiPlayerModeButton.selectedProperty().setValue(false);
        playButton.setDisable(!customIPButton.isSelected() && !localhostButton.isSelected());
    }

    /**
     * When the multi player mode button is clicked, the single player mode is disabled
     */
    public void clickMultiPlayerModeButton(){
        singlePlayerModeButton.selectedProperty().setValue(false);
        playButton.setDisable(!customIPButton.isSelected() && !localhostButton.isSelected());
    }

    /**
     * To set a label's text
     * @param label the label to modify
     * @param content the text to add
     */
    private void setLabelText(Label label,String content){
        Platform.runLater(()->{
            label.setText(content);
        });
    }

    /**
     * When the server is unable and the player click the play button,
     * an error message is shown and the game is started automatically in OFFLINE mode.
     */
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

    /**
     * If username is not valid, an error message is shown and all fields are enabled to modify some choices or
     * credentials.
     */
    public void userNotValid(){
        errorMessage.setVisible(true);
        setLabelText(errorMessage,"Username already taken!");
        enableAllLoginFields();
        loadingIndicator.setVisible(false);
    }

    /**
     * If the server is full, an error message is shown and the game is set to OFFLINE mode automatically.
     */
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
