package org.example;

import java.sql.*;
import java.util.ArrayList;
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
        insertContactDetailsToDatabase();
        System.out.println("Data Added In Database...");
    }

    public void insertContactDetailsToDatabase() throws SQLException {
        String INSERT_USERS_QUERY = "INSERT INTO addressbook" + "  (firstname, lastname, address, city, state, zip, phone, email) VALUES " +
                " (?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_QUERY); {
            connection.setAutoCommit(false);
            for (ContactDetails contactDetails : contactDetailsArrayList) {
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

    public void retrieveAllContactDetailsFromDatabase() throws SQLException {
        String RETRIEVAL_USERS_QUERY = "SELECT firstname, lastname, address, city, state, zip, phone, email FROM addressbook";
        Connection connection = conn.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(RETRIEVAL_USERS_QUERY);
        while (rs.next()) {
            System.out.println("{" + "FirstName" + " : " + rs.getString( "firstname") + ", " + "LastName" + " : " +rs.getString("lastname") + ", " +
                    "Address" + " : " + rs.getString("address") + ", " + "City" + " : " + rs.getString("city") + ", " +
                    "State" + " : " + rs.getString("state") + ", " + "Zip" + " : " + rs.getString("zip") + ", " +
                    "Phone" + " : " + rs.getString("phone") + ", " + "Email" + " : " + rs.getString("email") + "}");
        }
        connection.close();
    }

    public void updateContactDetailsFromDatabase() throws SQLException {
        Connection connection = conn.getConnection();
        String RETRIEVAL_USERS_QUERY = "SELECT firstname, lastname, address, city, state, zip, phone, email FROM addressbook WHERE firstname = ? AND lastname = ?";
        System.out.print("Enter FirstName to Update Record : ");
        String keyFirstName = scnr.next();
        System.out.print("Enter LastName to Update Record : ");
        String keyLastName = scnr.next();
        PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVAL_USERS_QUERY);
        connection.setAutoCommit(false);

        preparedStatement.setString(1, keyFirstName);
        preparedStatement.setString(2, keyLastName);
        ResultSet rs = preparedStatement.executeQuery();
        preparedStatement.executeBatch();

        while (rs.next()) {
            System.out.println("{" + "FirstName" + " : " + rs.getString( "firstname") + ", " + "LastName" + " : " +rs.getString("lastname") + ", " +
                    "Address" + " : " + rs.getString("address") + ", " + "City" + " : " + rs.getString("city") + ", " +
                    "State" + " : " + rs.getString("state") + ", " + "Zip" + " : " + rs.getString("zip") + ", " +
                    "Phone" + " : " + rs.getString("phone") + ", " + "Email" + " : " + rs.getString("email") + "}");
        }
        connection.commit();
        connection.setAutoCommit(true);
        updateContact(keyFirstName, keyLastName);
    }

    public void updateContact(String keyFirstName, String keyLastName) throws SQLException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("\nWhat do you want to edit? : ");
        System.out.println("\n1.Edit First Name\n2.Edit Last Name\n3.Edit Address\n4.Edit City\n5.Edit State\n6.Edit Zip Code\n7.Edit Phone\n8.Edit Email\n9.Exit");
        System.out.print("Enter your choice : ");
        int elementForEditing = scnr.nextInt();

        Connection connection = conn.getConnection();
        switch (elementForEditing) {
            case Constants.EDIT_FIRSTNAME : {
                System.out.print("Update First Name : ");
                String updtFirstName = scnr.next().toLowerCase();

                String UPDATE_FIRST_NAME = "UPDATE addressbook SET firstname = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIRST_NAME);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtFirstName);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
                break;
            }
            case Constants.EDIT_LASTNAME : {
                System.out.print("Update Last Name : ");
                String updtLastName = scnr.next().toLowerCase();

                String UPDATE_LAST_NAME = "UPDATE addressbook SET lastname = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LAST_NAME);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtLastName);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
                break;
           }
            case Constants.EDIT_ADDRESS : {
                System.out.print("Update Address : ");
                String updtAddress = scnr.next().toLowerCase();

                String UPDATE_ADDRESS = "UPDATE addressbook SET address = ? WHERE firstname= ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtAddress);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
                break;
            }
            case Constants.EDIT_CITY : {
                System.out.print("Update City Name : ");
                String updtCity = scnr.next().toLowerCase();

                String UPDATE_CITY = "UPDATE addressbook SET city = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtCity);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
                break;
           }
            case Constants.EDIT_STATE : {
                System.out.print("Update State Name : ");
                String updtState = scnr.next().toLowerCase();

                String UPDATE_STATE = "UPDATE addressbook SET state = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATE);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtState);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(true);
                break;
            }
            case Constants.EDIT_ZIP : {
                System.out.print("Update Zip Code : ");
                String updtZip = scnr.next().toLowerCase();

                String UPDATE_ZIP = "UPDATE addressbook SET zip = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ZIP);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtZip);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(false);
                break;
            }
            case Constants.EDIT_PHONE : {
                System.out.print("Update Phone : ");
                String updtPhone = scnr.next().toLowerCase();

                String UPDATE_PHONE = "UPDATE addressbook SET phone = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHONE);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtPhone);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(false);
                break;
            }
            case Constants.EDIT_EMAIL : {
                System.out.print("Update Email : ");
                String updtEmail = scnr.next().toLowerCase();

                String UPDATE_EMAIL = "UPDATE addressbook SET email = ? WHERE firstname = ? AND lastname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMAIL);
                connection.setAutoCommit(false);

                preparedStatement.setString(1, updtEmail);
                preparedStatement.setString(2, keyFirstName);
                preparedStatement.setString(3, keyLastName);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

                connection.commit();
                connection.setAutoCommit(false);
                break;
            }
            case Constants.EXIT : {
                break;
            }
            default : System.out.println("\nPlease give valid input!");
        }
    }

}
