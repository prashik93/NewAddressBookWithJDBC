package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        main.userInput();
    }

    public void userInput() throws SQLException {
        AddressBook addressBook = new AddressBook();
        System.out.print("\n1.Add Contact Into Database");
        System.out.print("\nEnter your choice : ");
        int userChoice = scnr.nextInt();
        switch (userChoice) {
            case Constants.ADD_CONTACT :
                addressBook.addContact();
                break;
        }
    }
}