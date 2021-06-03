package it.polimi.ingsw.network.client;

import it.polimi.ingsw.exception.InputInvalidException;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Application;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * this class rapresents the client
 * here he will choose the type of view (CLI or GUI)
 * this info will be in the args in input
 */
public class ClientMain {

    public static void main(String[] args) throws InputInvalidException {

        //REMINDER: launch the server with the type of view with args,
        //          if null use the actual command that able the client to choose manually

        int viewModeChoice = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi! Please insert the interface you would like to play with");
        System.out.println("1) CLI");
        System.out.println("2) GUI");

        boolean setViewMode = false;
        while (!setViewMode) {

            try {
                viewModeChoice = scanner.nextInt();
                if (viewModeChoice != 1 && viewModeChoice != 2) {
                    //throw new InputInvalidException("Error! Plese insert a valid umber for the view mode..");
                    System.out.println("Error! Plese insert a valid umber for the view mode..");
                } else {
                    setViewMode = true;
                }
            } catch (InputMismatchException eio) {
                System.out.println("⚠️ERROR: You can only insert numbers, type again");
                scanner.nextLine();
            }


        }

        System.out.println("You choose: "+viewModeChoice);

        switch (viewModeChoice){
            case 1:
                CLI cli = new CLI(args);
                cli.start();
                break;
            case 2:
                Application.launch(GUI.class,args);
                break;
            default:
                throw new Error("The number of the view don't mach..");
        }

    }
}
