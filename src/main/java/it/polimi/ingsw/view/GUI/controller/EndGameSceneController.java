package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.message.controllerMsg.CNewStartMsg;
import it.polimi.ingsw.message.controllerMsg.CNotStartAgainMsg;
import it.polimi.ingsw.message.viewMsg.VShowEndGameResultsMsg;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * When the game ends, the scene changes to the EndGameScene which shows the outcome (winner/loser),
 * this controller handles it.
 */
public class EndGameSceneController {
    private GUI gui;

    @FXML
    private ImageView loserImage,winnerImage;
    @FXML
    private Label message,player1VictoryPoints,player2VictoryPoints,player3VictoryPoints,player4VictoryPoints;
    @FXML
    private Pane askNewGamePane;
    @FXML
    private VBox victoryPointsVBox;
    @FXML
    private Button yesButton,noButton;

    /**
     * To set the GUI which refers
     * @param gui the GUI of the player
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * To prepare the scene after it's set
     */
    public void start(){
        loserImage.setVisible(false);
        winnerImage.setVisible(false);
        askNewGamePane.setVisible(false);
        yesButton.setDisable(true);
        noButton.setDisable(true);
        victoryPointsVBox.setVisible(false);
        for(Label label:getLabelPlayersView()){
            label.setVisible(false);
        }
    }

    /**
     * To show the outcome
     * @param isTheWinner true=winner, false=loser
     */
    public void showOutcome(boolean isTheWinner){
        if(isTheWinner){
            setLabelText(message,"... CONGRATULATIONS, YOU'RE THE");
            winnerImage.setVisible(true);
        }else{
            setLabelText(message,"... OH NO, YOU'RE ONE OF THE");
            loserImage.setVisible(true);
        }
    }

    /**
     * Show a message to ask if the player wants to play again
     */
    public void askNewGame(){
        askNewGamePane.setVisible(true);
        yesButton.setDisable(false);
        noButton.setDisable(false);
    }

    /**
     * If the player click on YES the scene is restart to the StartScene
     */
    public void clickYesButton(){
        if(!yesButton.isDisable()){
            if(!gui.isOffline()) {
                gui.sendMsg(new CNewStartMsg("I want to play again", gui.getUsername()));
                gui.getClient().setClientFinish(true);
                gui.getClient().closeConnection();
            }else{
                gui.getMessageHandler().stopMessageHandler();
                gui.setMessageHandler(null);
            }
            gui.restartIntroScene();
        }
    }

    /**
     * If the player click on NO a message of end game is send and then the GUI closes.
     */
    public void clickNoButton(){
        if(!noButton.isDisable()){
            if(!gui.isOffline()) {
                gui.sendMsg(new CNotStartAgainMsg("I don't want to play again"));
            }
            gui.close();
        }
    }

    /**
     * To set the text of a label
     * @param label the label to modify
     * @param content the text to insert
     */
    private void setLabelText(Label label,String content){
        Platform.runLater(()->label.setText(content));
    }

    private ArrayList<Label> getLabelPlayersView(){
        ArrayList<Label> labelPlayersView=new ArrayList<>();
        labelPlayersView.add(player1VictoryPoints);
        labelPlayersView.add(player2VictoryPoints);
        labelPlayersView.add(player3VictoryPoints);
        labelPlayersView.add(player4VictoryPoints);
        return labelPlayersView;
    }

    public void setVictoryPoints(String winnerUsername,int winnerVictoryPoints, List<Player> losers, boolean soloMode) {
        if(!soloMode){
            victoryPointsVBox.setVisible(true);
            setLabelText(getLabelPlayersView().get(0),winnerUsername+": "+winnerVictoryPoints);
            getLabelPlayersView().get(0).setVisible(true);
            int i=1;
            for(Player loser:losers){
                setLabelText(getLabelPlayersView().get(i),loser.getUsername()+": "+loser.getVictoryPoints());
                getLabelPlayersView().get(i).setVisible(true);
                i++;
            }
        }
    }
}
