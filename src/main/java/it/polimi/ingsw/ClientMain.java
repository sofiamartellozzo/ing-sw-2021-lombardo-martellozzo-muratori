package it.polimi.ingsw;


import it.polimi.ingsw.exception.InputInvalidException;

import java.util.Scanner;

/**
 * this class rapresents the client
 * here he will choose the type of view (CLI or GUI)
 * this info will be in the args in input
 */
public class ClientMain {

    public static void main(String[] args) throws InputInvalidException {

        int viewModeChoice = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi! Please insert the interface you would like to play with");
        System.out.println("1) CLI");
        System.out.println("2) GUI");

        boolean setViewMode = false;
        while (!setViewMode) {

                viewModeChoice = scanner.nextInt();
                if (viewModeChoice != 1 && viewModeChoice != 2) {
                    throw new InputInvalidException("Error! Plese insert a valid umber for the view mode..");
                } else {
                    setViewMode = true;
                }
        }
    }
}
