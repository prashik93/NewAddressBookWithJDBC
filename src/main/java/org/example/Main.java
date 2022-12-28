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
            System.out.print("1.Add Contact Into Database\n2.Retrieve Contact From Database\n3.Update Contact In Database\n4.Delete Contact\n5.Retrieve Contact By City Or State");
            System.out.print("\nEnter your choice : ");
            int userChoice = scnr.nextInt();
            switch (userChoice) {
                case Constants.ADD_CONTACT:
                    addressBook.addContact();
                    break;
                case Constants.SHOW_CONTACT:
                    addressBook.retrieveAllContactDetailsFromDatabase();
                    break;
                case Constants.EDIT_CONTACT:
                    addressBook.updateContactDetailsFromDatabase();
                    break;
                case Constants.DELETE_CONTACT:
                    addressBook.deleteContactDetailsFromDatabase();
                    break;
                case Constants.RETRIEVE_CONTACT_BY_CITY_OR_STATE:
                    addressBook.retrievingPersonBelongingToCityOrState();
                    break;
                case Constants.EXIT:
                    break;
                default : System.out.println("\nPlease give valid input...");
            }
        }
    }

}