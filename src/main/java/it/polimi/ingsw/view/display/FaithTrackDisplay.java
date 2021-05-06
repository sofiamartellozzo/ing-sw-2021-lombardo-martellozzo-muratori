package it.polimi.ingsw.view.display;

import it.polimi.ingsw.utility.AnsiColors;

import java.awt.*;

/**
 * this class show to the player his faith Track with the updated position of his FaithMarker on it
 */

public class FaithTrackDisplay {

    //public void showFaithTrack(){
    public static void main(String[] args) {
        int pop1= 2;
        int pop2= 3;
        int pop3= 4;

        System.out.println(AnsiColors.RED_BOLD+"HERE IS YOUR FAITHTRACK"+AnsiColors.RESET);
        System.out.println("LEGEND:");
        System.out.print(AnsiColors.YELLOW_BOLD + "[ ] " +AnsiColors.RESET+ "Gold Boxes with victory points\n");
        System.out.print(AnsiColors.ANSI_WHITE + "[ ] " +AnsiColors.RESET+ "Simple Boxes\n");
        System.out.print(AnsiColors.PURPLE_BACKGROUND +AnsiColors.BLACK_BOLD + "[ \u271D ]" + AnsiColors.RESET+" pope Boxes (end of a vatican Section) \n");
        System.out.print(AnsiColors.RED_BACKGROUND +AnsiColors.BLACK_BOLD+ "[ \u271D ]" + AnsiColors.RESET+" last pope Box-> end Game, 20 victory points \n");
        System.out.print(AnsiColors.RED_BOLD+"[ ]"+AnsiColors.RESET+" Vatican Report section\n\n");

        System.out.println(AnsiColors.RED_BOLD+"                       |--------------------|                 |-----------------------------|             |---------------------------------|"+AnsiColors.RESET);

        for (int i = 0; i < 25; i++) {
            if (i == 3) {
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:1] " + AnsiColors.RESET);
            }else if(i == 6){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:2] " +AnsiColors.RESET);
            }else if(i == 8) {
                System.out.print(AnsiColors.PURPLE_BACKGROUND +AnsiColors.BLACK_BOLD + "[ \u271D ]" + AnsiColors.RESET+AnsiColors.RED_BOLD+ "|"+AnsiColors.RESET);
            }else if(i == 9){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:4] " +AnsiColors.RESET);
            }else if(i == 12){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:6] " +AnsiColors.RESET);
            }else if(i == 15){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:9] " +AnsiColors.RESET);
            }else if(i == 16) {
                System.out.print(AnsiColors.PURPLE_BACKGROUND +AnsiColors.BLACK_BOLD + "[ \u271D ]" + AnsiColors.RESET+AnsiColors.RED_BOLD+ "|"+AnsiColors.RESET);
            }else if(i == 18){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:12] " +AnsiColors.RESET+AnsiColors.RED_BOLD+ "|"+AnsiColors.RESET);
            }else if(i == 21){
                System.out.print(AnsiColors.YELLOW_BOLD + "[VP:16] " +AnsiColors.RESET);
            }else if(i == 24){
                System.out.print(AnsiColors.RED_BACKGROUND +AnsiColors.BLACK_BOLD + "[ \u271D ]" +AnsiColors.RESET+AnsiColors.RED_BOLD+ "|"+AnsiColors.RESET);
            }
            else {
                if(i == 4 || i== 11) {
                    System.out.print("[" + i + "] " + AnsiColors.RESET+AnsiColors.RED_BOLD+ "|"+AnsiColors.RESET);
                }
                else{
                    System.out.print("[" + i + "] ");
                }
            }

        }
        System.out.println(AnsiColors.RED_BOLD+"\n                       |--------------------|                 |-----------------------------|             |---------------------------------|"+AnsiColors.RESET);
        System.out.print(AnsiColors.YELLOW_BOLD+  "                           [Pop'sFavor:"+pop1+"]"+AnsiColors.YELLOW_BRIGHT+"                              [Pop'sFavor:"+pop2+"]"+AnsiColors.RED_BOLD+ "                                [Pop'sFavor:"+pop3+"]"+ AnsiColors.RESET);

    }
}




