package it.polimi.ingsw.view.display;

/**
 * class to write the message on the CLI
 */
public class WriteMessageDisplay {

    /**
     * display the first phrase of the game
     */
    public static void writeTitle(){

        System.out.println("                                                                            \n" +
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
                "                                                                                       ");
    }

    /**
     * if the connection is working print the phrase CONNECTED, YOU'RE ONLINE
     */
    public static void writeOnlineStatus(){

        System.out.println(
                "________  ________  ________   ________   _______   ________ _________  _______   ________                 \n" +
                "|\\   ____\\|\\   __  \\|\\   ___  \\|\\   ___  \\|\\  ___ \\ |\\   ____\\\\___   ___\\\\  ___ \\ |\\   ___ \\ \n" +
                "\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\   __/|\\ \\  \\___\\|___ \\  \\_\\ \\   __/|\\ \\  \\_|\\ \\  \n" +
                " \\ \\  \\    \\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\_|/_\\ \\  \\       \\ \\  \\ \\ \\  \\_|/_\\ \\  \\ \\\\ \\  ___  \n" +
                "  \\ \\  \\____\\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\_|\\ \\ \\  \\____   \\ \\  \\ \\ \\  \\_|\\ \\ \\  \\_\\\\ \\|\\  \\  \n" +
                "   \\ \\_______\\ \\_______\\ \\__\\\\ \\__\\ \\__\\\\ \\__\\ \\_______\\ \\_______\\  \\ \\__\\ \\ \\_______\\ \\_______\\ \\  \\                   \n" +
                "    \\|_______|\\|_______|\\|__| \\|__|\\|__| \\|__|\\|_______|\\|_______|   \\|__|  \\|_______|\\|_______|\\/  /|                                  \n" +
                "                                                                                              |\\___/ /                                  \n" +
                "                                                                                              \\|___|/                                   \n" +
                "                                                                                                                                        \n" +
                "  ___    ___ ________  ___  ___  ________  _______           ________  ________   ___       ___  ________   _______           ___       \n" +
                " |\\  \\  /  /|\\   __  \\|\\  \\|\\  \\|\\   __  \\|\\  ___ \\         |\\   __  \\|\\   ___  \\|\\  \\     |\\  \\|\\   ___  \\|\\  ___ \\         |\\  \\      \n" +
                " \\ \\  \\/  / | \\  \\|\\  \\ \\  \\\\\\  \\ \\  \\|\\  \\ \\   __/|        \\ \\  \\|\\  \\ \\  \\\\ \\  \\ \\  \\    \\ \\  \\ \\  \\\\ \\  \\ \\   __/|        \\ \\  \\     \n" +
                "  \\ \\    / / \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\_|/__       \\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\    \\ \\  \\ \\  \\\\ \\  \\ \\  \\_|/__       \\ \\  \\    \n" +
                "   \\/  /  /   \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\_|\\ \\       \\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\____\\ \\  \\ \\  \\\\ \\  \\ \\  \\_|\\ \\       \\ \\__\\   \n" +
                " __/  / /      \\ \\_______\\ \\_______\\ \\__\\\\ _\\\\ \\_______\\       \\ \\_______\\ \\__\\\\ \\__\\ \\_______\\ \\__\\ \\__\\\\ \\__\\ \\_______\\       \\|__|   \n" +
                "|\\___/ /        \\|_______|\\|_______|\\|__|\\|__|\\|_______|        \\|_______|\\|__| \\|__|\\|_______|\\|__|\\|__| \\|__|\\|_______|           ___ \n" +
                "\\|___|/                                                                                                                            |\\__\\\n" +
                "    " +
                "                                                                                                                               \\|__|                                                 \n");
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

        System.out.println(
                "      ___                       ___           ___           ___           ___     \n" +
                        "     /\\  \\                     /\\  \\         /\\  \\         /\\__\\         /\\  \\    \n" +
                        "    _\\:\\  \\       ___          \\:\\  \\        \\:\\  \\       /:/ _/_       /::\\  \\   \n" +
                        "   /\\ \\:\\  \\     /\\__\\          \\:\\  \\        \\:\\  \\     /:/ /\\__\\     /:/\\:\\__\\  \n" +
                        "  _\\:\\ \\:\\  \\   /:/__/      _____\\:\\  \\   _____\\:\\  \\   /:/ /:/ _/_   /:/ /:/  /  \n" +
                        " /\\ \\:\\ \\:\\__\\ /::\\  \\     /::::::::\\__\\ /::::::::\\__\\ /:/_/:/ /\\__\\ /:/_/:/__/___\n" +
                        " \\:\\ \\:\\/:/  / \\/\\:\\  \\__  \\:\\~~\\~~\\/__/ \\:\\~~\\~~\\/__/ \\:\\/:/ /:/  / \\:\\/:::::/  /\n" +
                        "  \\:\\ \\::/  /   ~~\\:\\/\\__\\  \\:\\  \\        \\:\\  \\        \\::/_/:/  /   \\::/~~/~~~~ \n" +
                        "   \\:\\/:/  /       \\::/  /   \\:\\  \\        \\:\\  \\        \\:\\/:/  /     \\:\\~~\\     \n" +
                        "    \\::/  /        /:/  /     \\:\\__\\        \\:\\__\\        \\::/  /       \\:\\__\\    \n" +
                        "     \\/__/         \\/__/       \\/__/         \\/__/         \\/__/         \\/__/    \n");
    }


    /**
     * method that shows the word "LOSER"
     */
    public static void declareLoser(){
        System.out.println(
                "      ___       ___           ___           ___           ___     \n" +
                "     /  /\\     /  /\\         /  /\\         /  /\\         /  /\\    \n" +
                "    /  /:/    /  /::\\       /  /::\\       /  /::\\       /  /::\\   \n" +
                "   /  /:/    /  /:/\\:\\     /__/:/\\:\\     /  /:/\\:\\     /  /:/\\:\\  \n" +
                "  /  /:/    /  /:/  \\:\\   _\\_ \\:\\ \\:\\   /  /::\\ \\:\\   /  /::\\ \\:\\ \n" +
                " /__/:/    /__/:/ \\__\\:\\ /__/\\ \\:\\ \\:\\ /__/:/\\:\\ \\:\\ /__/:/\\:\\_\\:\\\n" +
                " \\  \\:\\    \\  \\:\\ /  /:/ \\  \\:\\ \\:\\_\\/ \\  \\:\\ \\:\\_\\/ \\__\\/~|::\\/:/\n" +
                "  \\  \\:\\    \\  \\:\\  /:/   \\  \\:\\_\\:\\    \\  \\:\\ \\:\\      |  |:|::/ \n" +
                "   \\  \\:\\    \\  \\:\\/:/     \\  \\:\\/:/     \\  \\:\\_\\/      |  |:|\\/  \n" +
                "    \\  \\:\\    \\  \\::/       \\  \\::/       \\  \\:\\        |__|:|~   \n" +
                "     \\__\\/     \\__\\/         \\__\\/         \\__\\/         \\__\\|    ");
    }
}
