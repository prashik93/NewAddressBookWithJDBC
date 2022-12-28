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
            System.out.print("1.Add Contact Into Database\n2.Retrieve Contact From Database\n3.Update Contact In Database" +
                    "\n4.Delete Contact\n5.Retrieve Contact By City Or State\n6.Contacts Count By City Or State" +
                    "\n7.Sort Contact By Persons Name\n8.Identify Each Address Book With Name And Type");
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
                case Constants.CONTACTS_COUNT_BY_CITY_OR_STATE:
                    addressBook.contactsCountByCityOrState();
                    break;
                case Constants.SORT_CONTACTS_BY_PERSONS_NAME:
                    addressBook.sortContactsByPersonsName();
                    break;
                case Constants.IDENTIFY_EACH_ADDRESSBOOK_BY_NAME_AND_CONTACT_TYPE:
                    addressBook.identifyEachAddressBookWithNameAndType();
                    break;
                case Constants.EXIT:
                    break;
                default : System.out.println("\nPlease give valid input...");
            }
        }
    }

}