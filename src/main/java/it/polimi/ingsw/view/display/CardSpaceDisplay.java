package it.polimi.ingsw.view.display;

import it.polimi.ingsw.controller.factory.PersonalBoardFactory;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerInterface;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.CardSpace;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.utility.AnsiColors;

import java.util.ArrayList;

/**
 * this class contains the method to show to the player his Card's Spaces, in particular the last card of each Card Space
 * because it is the only one that he can activate
 */
public class CardSpaceDisplay {

    private ArrayList<CardSpace> cardSpaces;


    public CardSpaceDisplay(ArrayList<CardSpace> cardSpaces){
        this.cardSpaces = cardSpaces;

    }

    public void showCardSpaces(){
    /*public static void main(String[] args) {
        PersonalBoardFactory personalBoardFactory = new PersonalBoardFactory();
        Player player = new Player("bob");
        PersonalBoard personalBoard = personalBoardFactory.createGame();
        player.setGameSpace(personalBoard);

        ArrayList<Resource> array = new ArrayList<>();
        array.add(0,new Resource(Color.BLUE));
        array.add(1,new Resource(Color.PURPLE));

        ArrayList<Resource> proceeds = new ArrayList<>();
        proceeds.add(0,new Resource(Color.GREY));
        proceeds.add(1,new Resource(Color.YELLOW));

        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(0,new Resource(Color.BLUE));

        DevelopmentCard card1 = new DevelopmentCard(1,3,Color.GREEN,1,array,proceeds,cost);
        DevelopmentCard card2 = new DevelopmentCard(2,2,Color.YELLOW,1,array,proceeds,cost);

        player.getGameSpace().getCardSpace(1).addCard(card1);
        player.getGameSpace().getCardSpace(2).addCard(card2);*/

        System.out.println(AnsiColors.YELLOW_BOLD+"HERE ARE YOUR THREE CARD SPACES:\n"+AnsiColors.RESET);
        for(int i = 0; i < 3; i++) {
            if(cardSpaces.get(i).getCards().isEmpty()){
                System.out.print("Card Space"+(i+1)+": "+AnsiColors.RED_BOLD+"EMPTY CARD SPACE\n"+AnsiColors.RESET);
            }
            else {
                System.out.println("CARD Space"+(i+1)+": ");
                /*System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getColor()+"\n");
                System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getleveltostring()+"\n");
                System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getcosttostring()+"\n");
                System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getpaytostring()+"\n");
                System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getearntostring()+"\n");
                System.out.print(player.getGameSpace().getCardSpace(i).getUpperCard().getvictorytostring()+"\n");*/
                System.out.println(cardSpaces.get(i).getUpperCard().toString());

            }
            System.out.print("\n");
        }
    }
}
