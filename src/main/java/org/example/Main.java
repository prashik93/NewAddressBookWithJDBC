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
        while (true) {
            System.out.println("\nWhat do you want to do? ");
            System.out.print("1.Add Contact Into Database\n2.Retrieve Contact From Database");
            System.out.print("\nEnter your choice : ");
            int userChoice = scnr.nextInt();
            switch (userChoice) {
                case Constants.ADD_CONTACT:
                    addressBook.addContact();
                    break;
                case Constants.SHOW_CONTACT:
                    addressBook.retrieveContactDetailsFromDatabase();
                    break;
            }
        }
    }
}