package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.model.board.FaithTrack;
import it.polimi.ingsw.model.board.Inactive;
import it.polimi.ingsw.utility.AnsiColors;

/**
 * this class show to the player his faith Track with the updated position of his FaithMarker on it
 * (if is in Solo Game it shows the position of Lorenzo's faith marker too)
 */

public class FaithTrackDisplay {

    private FaithTrack faithTrack;
    private int position;
    int lorenzoPosition;
    boolean soloMode;

    public FaithTrackDisplay(FaithTrack faithTrack, int position, boolean soloMode, int lorenzoPosition) {
        this.faithTrack = faithTrack;
        this.position = position;
        this.soloMode = soloMode;
        this.lorenzoPosition = lorenzoPosition;
    }

    public void showFaithTrack() {

        System.out.println(AnsiColors.BLUE_BOLD + "HERE IS YOUR FAITHTRACK: " + AnsiColors.RESET);
        System.out.println("LEGEND:");
        System.out.print(AnsiColors.YELLOW_BOLD + "[ ] " + AnsiColors.RESET + "Gold Boxes with victory points\n");
        System.out.print(AnsiColors.ANSI_WHITE + "[ ] " + AnsiColors.RESET + "Simple Boxes\n");
        System.out.print(AnsiColors.PURPLE_BACKGROUND + AnsiColors.BLACK_BOLD + "[   ]" + AnsiColors.RESET + " pope Boxes (end of a vatican Section) \n");
        System.out.print(AnsiColors.GREEN_BACKGROUND + AnsiColors.BLACK_BOLD + "[   ]" + AnsiColors.RESET + " last pope Box-> end Game + 20 victory points \n");
        System.out.print(AnsiColors.RED_BOLD + "[ ]" + AnsiColors.RESET + " Vatican Report section\n");
        System.out.print(AnsiColors.RED_BOLD + "✞" + AnsiColors.RESET + " Your Faith Marker in the Faith Track\n");
        System.out.print(AnsiColors.BLACK_BOLD + "✞" + AnsiColors.RESET + " (ONLY IF YOU'RE IN SOLO MODE) Lorenzo's Faith Marker in the Faith Track\n\n");

        System.out.println(AnsiColors.RED_BOLD + "                       |--------------------|                  |---------------------------|              |---------------------------------|" + AnsiColors.RESET);

        for (int i = 0; i < 25; i++) {
            if (i == 3) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:1] " + AnsiColors.RESET);
            } else if (i == 6) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:2] " + AnsiColors.RESET);
            } else if (i == 8 || i == 16) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.PURPLE_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.RED_BOLD + "✞" + AnsiColors.BLACK_BOLD + "✞" + AnsiColors.BLACK_BOLD + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.PURPLE_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.RED_BOLD + "✞" + AnsiColors.BLACK_BOLD + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.PURPLE_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.BLACK_BOLD + "✞" + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else
                    System.out.print(AnsiColors.PURPLE_BACKGROUND + AnsiColors.BLACK_BOLD + "[  ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
            } else if (i == 9) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:4] " + AnsiColors.RESET);
            } else if (i == 12) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:6] " + AnsiColors.RESET);
            } else if (i == 15) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:9] " + AnsiColors.RESET);
            } else if (i == 18) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET + AnsiColors.RESET + AnsiColors.RED_BOLD + "|" + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "|" + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "|" + AnsiColors.RESET);
                } else
                    System.out.print(AnsiColors.YELLOW_BOLD + "[VP:12] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
            } else if (i == 21) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.YELLOW_BOLD + "[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.YELLOW_BOLD + "] " + AnsiColors.RESET);
                } else System.out.print(AnsiColors.YELLOW_BOLD + "[VP:16] " + AnsiColors.RESET);
            } else if (i == 24) {
                if (soloMode && position == i && lorenzoPosition == i) {
                    System.out.print(AnsiColors.GREEN_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.RED_BOLD + "✞" + AnsiColors.BLACK_BOLD + "✞" + AnsiColors.BLACK_BOLD + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else if (position == i) {
                    System.out.print(AnsiColors.GREEN_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.RED_BOLD + "✞" + AnsiColors.BLACK_BOLD + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else if (soloMode && lorenzoPosition == i) {
                    System.out.print(AnsiColors.GREEN_BACKGROUND + AnsiColors.BLACK_BOLD + "[ " + AnsiColors.BLACK_BOLD + "✞" + " ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                } else
                    System.out.print(AnsiColors.GREEN_BACKGROUND + AnsiColors.BLACK_BOLD + "[  ]" + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
            } else {
                if (i == 4 || i == 11) {
                    if (soloMode && position == i && lorenzoPosition == i) {
                        System.out.print("[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.BLACK_BOLD + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                    } else if (position == i) {
                        System.out.print("[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                    } else if (soloMode && lorenzoPosition == i) {
                        System.out.print("[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "| " + AnsiColors.RESET);
                    } else
                        System.out.print("[" + i + "] " + AnsiColors.RESET + AnsiColors.RED_BOLD + "|" + AnsiColors.RESET);
                } else {
                    if (soloMode && position == i && lorenzoPosition == i) {
                        System.out.print("[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.BLACK_BOLD + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET);
                    } else if (position == i) {
                        System.out.print("[" + AnsiColors.ANSI_RED + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET);
                    } else if (soloMode && lorenzoPosition == i) {
                        System.out.print("[" + AnsiColors.ANSI_BLACK + "✞" + AnsiColors.WHITE_BOLD + "] " + AnsiColors.RESET);
                    } else System.out.print("[" + i + "] ");
                }
            }

        }
        System.out.println(AnsiColors.RED_BOLD + "\n                       |--------------------|                  |---------------------------|              |---------------------------------|" + AnsiColors.RESET);
        for (int i = 0; i < 3; i++) {
            if (faithTrack.getPopesFavorTiles().get(i).getState() instanceof Inactive) {
                //System.out.println(AnsiColors.YELLOW_BOLD + "                          [Pop'sFavor:" + pop1 + "❌]" + AnsiColors.YELLOW_BRIGHT + "                              [Pop'sFavor:" + pop2 + "❌]" + AnsiColors.RED_BOLD + "                                [Pop'sFavor:" + pop3 + "❌]" + AnsiColors.RESET);
                System.out.print("                          [Pop'sFavor" + (i + 1) + ":"+AnsiColors.RED_BOLD + "❌"+AnsiColors.RESET+"]  ");
            }else{
                System.out.print("                          [Pop'sFavor" + (i + 1) + ":"+AnsiColors.GREEN_BOLD + "✔"+AnsiColors.RESET+"]  ");
            }
        }
        System.out.print("\n");

    }
}




