package it.polimi.ingsw.view.CLI.display;

import it.polimi.ingsw.utility.AnsiColors;

/**
 * class to write the message on the CLI
 */
public class WriteMessageDisplay {

    /**
     * display the first phrase of the game
     */
    public static void writeTitle(){

        System.out.println(AnsiColors.BLUE_BRIGHT+"                                                                            \n" +
                "███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███████╗     ██████╗ ███████╗      \n" +
                "████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝    ██╔═══██╗██╔════╝      \n" +
                "██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝███████╗    ██║   ██║█████╗        \n" +
                "██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗╚════██║    ██║   ██║██╔══╝        \n" +
                "██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║███████║    ╚██████╔╝██║           \n" +
                "╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═╝           \n" +
                "                                                                                       \n" +
                "██████╗ ███████╗███╗   ██╗ █████╗ ██╗███████╗███████╗ █████╗ ███╗   ██╗ ██████╗███████╗\n" +
                "██╔══██╗██╔════╝████╗  ██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗  ██║██╔════╝██╔════╝\n" +
                "██████╔╝█████╗  ██╔██╗ ██║███████║██║███████╗███████╗███████║██╔██╗ ██║██║     █████╗  \n" +
                "██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║╚════██║╚════██║██╔══██║██║╚██╗██║██║     ██╔══╝  \n" +
                "██║  ██║███████╗██║ ╚████║██║  ██║██║███████║███████║██║  ██║██║ ╚████║╚██████╗███████╗\n" +
                "╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚══════╝\n" +
                "" +AnsiColors.RESET+
                "                       ©Created By: Gianluca Lombardo, Sofia Martellozzo, Ilaria Muratori\n" +
                "                                                               Politecnico di Milano 2021\n" +
                "                                                                                       ");
    }

    /**
     * if the network is working print the phrase CONNECTED
     */
    public static void writeOnlineStatus(){

        System.out.println(AnsiColors.BLUE_BOLD+"  " +
                "                               _           _ \n" +
                "  ___ ___  _ __  _ __   ___  ___| |_ ___  __| |\n" +
                " / __/ _ \\| '_ \\| '_ \\ / _ \\/ __| __/ _ \\/ _` |\n" +
                "| (_| (_) | | | | | | |  __/ (__| ||  __/ (_| |\n" +
                " \\___\\___/|_| |_|_| |_|\\___|\\___|\\__\\___|\\__,_|\n" +
                "                                               "+AnsiColors.RESET);

    }

    /**
     * if the player chooses to play offline OFFLINE
     */
    public static void writeOfflineStatus() {
        System.out.println(AnsiColors.BLUE_BOLD+" " +
                "        ___    ___ _  _             \n" +
                "        / __)  / __) |(_)            \n" +
                "  ___ _| |__ _| |__| | _ ____  _____ \n" +
                " / _ (_   __|_   __) || |  _ \\| ___ |\n" +
                "| |_| || |    | |  | || | | | | ____|\n" +
                " \\___/ |_|    |_|   \\_)_|_| |_|_____)\n" +
                "                                     "+AnsiColors.RESET);
    }

    /**
     * if another player is playing, this message indicates to the player to wait
     */
    public static void writeWaitingStatus(){
        System.out.println("" +AnsiColors.BLUE_BOLD+
                "                     _   _                                        _                                        \n" +
                " __      __   __ _  (_) | |_     _   _    ___    _   _   _ __    | |_   _   _   _ __   _ __                \n" +
                " \\ \\ /\\ / /  / _` | | | | __|   | | | |  / _ \\  | | | | | '__|   | __| | | | | | '__| | '_ \\               \n" +
                "  \\ V  V /  | (_| | | | | |_    | |_| | | (_) | | |_| | | |      | |_  | |_| | | |    | | | |    _   _   _ \n" +
                "   \\_/\\_/    \\__,_| |_|  \\__|    \\__, |  \\___/   \\__,_| |_|       \\__|  \\__,_| |_|    |_| |_|   (_) (_) (_)\n" +
                "                                 |___/                                                                   "+AnsiColors.RESET);
    }

    /**
     * waiting msg when the player has to wait that other player
     */
    public static void writeWaitOtherPlayers(){
        System.out.println(" " +AnsiColors.BLUE_BOLD+
                "              _ _           \n" +
                "__      ____ _(_) |_         \n" +
                "\\ \\ /\\ / / _` | | __|        \n" +
                " \\ V  V / (_| | | |_   _ _ _ \n" +
                "  \\_/\\_/ \\__,_|_|\\__| (_)_)_)\n" +
                "                             "+AnsiColors.RESET);
    }
    /**
     * method that shows " END GAME" to say that the play is over!
     */
    public static void endGame(){

        System.out.println(
                "███████ ███    ██ ██████       ██████   █████  ███    ███ ███████ \n" +
                "██      ████   ██ ██   ██     ██       ██   ██ ████  ████ ██      \n" +
                "█████   ██ ██  ██ ██   ██     ██   ███ ███████ ██ ████ ██ █████   \n" +
                "██      ██  ██ ██ ██   ██     ██    ██ ██   ██ ██  ██  ██ ██      \n" +
                "███████ ██   ████ ██████       ██████  ██   ██ ██      ██ ███████ \n" +
                "                                                                  ");
    }

    /**
     * method that shows the word "WINNER"
     */
    public static void declareWinner(){

        System.out.println("YOU'RE THE  " +AnsiColors.BLUE_BOLD+
                "         _                       \n" +
                "__      ___)_ __  _ __   ___ _ __ \n" +
                "\\ \\ /\\ / / | '_ \\| '_ \\ / _ \\ '__|\n" +
                " \\ V  V /| | | | | | | |  __/ |   \n" +
                "  \\_/\\_/ |_|_| |_|_| |_|\\___|_|   \n" +
                "                                 "+AnsiColors.RESET);
    }


    /**
     * method that shows the word "LOSER"
     */
    public static void declareLoser(){
        System.out.println("I'M SORRY, YOU'RE A " +AnsiColors.RED_BOLD+
                " _                     \n" +
                "| | ___  ___  ___ _ __ \n" +
                "| |/ _ \\/ __|/ _ \\ '__|\n" +
                "| | (_) \\__ \\  __/ |   \n" +
                "|_|\\___/|___/\\___|_|   \n" +
                "                      "+AnsiColors.RED_BOLD);
    }

    public static void showRanking(){
        System.out.println(" HERE IS THE FINAL RANKING OF THE GAME: ");
        System.out.println(" ►►►►►►►►►►►►►►►►");
    }
}
