package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class AddressBook {
    Scanner scnr = new Scanner(System.in);
    JDBCConnection conn = new JDBCConnection();

    ArrayList<ContactDetails> contactDetailsArrayList = new ArrayList<>();
    public ContactDetails contactDetailsForm() {
        System.out.print("\nEnter First Name : ");
        String firstName = scnr.next().toLowerCase();
        System.out.print("Enter Last Name : ");
        String lastName = scnr.next().toLowerCase();
        System.out.print("Enter Address : ");
        String address = scnr.next().toLowerCase();
        System.out.print("Enter City : ");
        String city = scnr.next().toLowerCase();
        System.out.print("Enter State : ");
        String state = scnr.next().toLowerCase();
        System.out.print("Enter Zip Code : ");
        String zip = scnr.next().toLowerCase();
        System.out.print("Enter Phone Number : ");
        String phone = scnr.next().toLowerCase();
        System.out.print("Enter Email : ");
        String email = scnr.next().toLowerCase();
        return new ContactDetails(firstName, lastName, address, city, state, zip, phone, email);
    }

    public void addContact() throws SQLException {
        System.out.print("How many contacts do you want? : ");
        int num = scnr.nextInt();
        for (int i = 0; i < num; i++) {
            ContactDetails contactDetails = contactDetailsForm();
            contactDetailsArrayList.add(contactDetails);
        }
        addContactDetailsToDatabase();
        System.out.println("Data Added In Database...");
    }

    public void addContactDetailsToDatabase() throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO addressbook" + "  (firstname, lastname, address, city, state, zip, phone, email) VALUES " +
                " (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = conn.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL); {
            connection.setAutoCommit(false);
            for (Iterator< ContactDetails > iterator = contactDetailsArrayList.iterator(); iterator.hasNext();) {
                ContactDetails contactDetails = iterator.next();
                preparedStatement.setString(1, contactDetails.getFirstName());
                preparedStatement.setString(2, contactDetails.getLastName());
                preparedStatement.setString(3, contactDetails.getAddress());
                preparedStatement.setString(4, contactDetails.getCity());
                preparedStatement.setString(5, contactDetails.getState());
                preparedStatement.setString(6, contactDetails.getZip());
                preparedStatement.setString(7, contactDetails.getPhone());
                preparedStatement.setString(8, contactDetails.getEmail());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
}
