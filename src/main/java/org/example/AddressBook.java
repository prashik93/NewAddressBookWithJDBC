package org.example;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
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

        switch (elementForEditing) {
            case Constants.EDIT_FIRSTNAME : {
                System.out.print("Update First Name : ");
                String updtFirstName = scnr.next().toLowerCase();
                String columnName = "firstname";
                updateSingleDetail(columnName, updtFirstName, keyFirstName, keyLastName);
                break;
            }
            case Constants.EDIT_LASTNAME : {
                System.out.print("Update Last Name : ");
                String updtLastName = scnr.next().toLowerCase();
                String columnName = "lastName";
                updateSingleDetail(columnName, updtLastName, keyFirstName, keyLastName);
                break;
           }
            case Constants.EDIT_ADDRESS : {
                System.out.print("Update Address : ");
                String updtAddress = scnr.next().toLowerCase();
                String columnName = "address";
                updateSingleDetail(columnName, updtAddress, keyFirstName, keyLastName);
                break;
            }
            case Constants.EDIT_CITY : {
                System.out.print("Update City Name : ");
                String updtCity = scnr.next().toLowerCase();
                String columnName = "city";
                updateSingleDetail(columnName, updtCity, keyFirstName, keyLastName);
                break;
           }
            case Constants.EDIT_STATE : {
                System.out.print("Update State Name : ");
                String updtState = scnr.next().toLowerCase();
                String columnName = "state";
                updateSingleDetail(columnName, updtState, keyFirstName, keyLastName);
                break;
            }
            case Constants.EDIT_ZIP : {
                System.out.print("Update Zip Code : ");
                String updtZip = scnr.next().toLowerCase();
                String columnName = "zip";
                updateSingleDetail(columnName, updtZip, keyFirstName, keyLastName);
                break;
            }
            case Constants.EDIT_PHONE : {
                System.out.print("Update Phone : ");
                String updtPhone = scnr.next().toLowerCase();
                String columnName = "phone";
                updateSingleDetail(columnName, updtPhone, keyFirstName, keyLastName);
                break;
            }
            case Constants.EDIT_EMAIL : {
                System.out.print("Update Email : ");
                String updtEmail = scnr.next().toLowerCase();
                String columnName = "email";
                updateSingleDetail(columnName, updtEmail, keyFirstName, keyLastName);
                break;
            }
            case Constants.EXIT : {
                break;
            }
            default : System.out.println("\nPlease give valid input!");
        }
    }

    public void updateSingleDetail(String columnName, String updateData, String keyFirstName, String keyLastName) throws SQLException {
        Connection connection = conn.getConnection();
        String UPDATE_FIRST_NAME = String.format("UPDATE addressbook SET %s = ? WHERE firstname = ? AND lastname = ?", columnName);
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIRST_NAME);
        connection.setAutoCommit(false);

        preparedStatement.setString(1, updateData);
        preparedStatement.setString(2, keyFirstName);
        preparedStatement.setString(3, keyLastName);
        preparedStatement.addBatch();
        preparedStatement.executeBatch();

        connection.commit();
        connection.setAutoCommit(true);
    }

    public void deleteContactDetailsFromDatabase() throws SQLException {
        Connection connection = conn.getConnection();
        String DELETE_USERS_QUERY = "DELETE FROM addressbook WHERE firstname = ? AND lastname = ?";
        System.out.print("Enter FirstName to Delete Record : ");
        String keyFirstName = scnr.next();
        System.out.print("Enter LastName to Delete Record : ");
        String keyLastName = scnr.next();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_QUERY);
        connection.setAutoCommit(false);

        preparedStatement.setString(1, keyFirstName);
        preparedStatement.setString(2, keyLastName);
        preparedStatement.execute();

        connection.commit();
        connection.setAutoCommit(true);
        System.out.println("Record Deleted Successfully...");
    }

    public void retrievingPersonBelongingToCityOrState() throws SQLException {
        System.out.print("\nEnter City/State : ");
        String cityOrState = scnr.next();
        System.out.print("Enter Name : ");
        String name = scnr.next();
        Connection connection = conn.getConnection();
        String RETRIEVE_PERSON_BELONGING_TO_CITY_OR_STATE_QUERY = String.format("SELECT * FROM addressbook WHERE %s = ?", cityOrState);
        PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_PERSON_BELONGING_TO_CITY_OR_STATE_QUERY);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            System.out.println("{" + "FirstName" + " : " + rs.getString("firstname") + ", " + "LastName" + " : " + rs.getString("lastname") + ", " +
                    "Address" + " : " + rs.getString("address") + ", " + "City" + " : " + rs.getString("city") + ", " +
                    "State" + " : " + rs.getString("state") + ", " + "Zip" + " : " + rs.getString("zip") + ", " +
                    "Phone" + " : " + rs.getString("phone") + ", " + "Email" + " : " + rs.getString("email") + "}");
        }
        connection.close();
    }

    public void contactsCountByCityOrState() throws SQLException {
        System.out.print("\nEnter City/State : ");
        String cityOrState = scnr.next().toLowerCase();
        System.out.print("Enter Name : ");
        String name = scnr.next().toLowerCase();
        Connection connection = conn.getConnection();
        String GET_COUNT_OF_CONTACTS_IN_CITY_OR_SATE = String.format("SELECT COUNT(?) AS countRow FROM addressbook WHERE %s = ?", cityOrState);
        PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNT_OF_CONTACTS_IN_CITY_OR_SATE);
        preparedStatement.setString(1, cityOrState);
        preparedStatement.setString(2, name);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            System.out.println("Count : " + (rs.getInt("countRow")));
        }
        connection.close();
    }

    public void sortContactsByPersonsName() throws SQLException {
        System.out.print("\nEnter City Name : ");
        String cityName = scnr.next().toLowerCase();
        Connection connection = conn.getConnection();
        String SORT_CONTACTS_BY_PERSONS_NAME_FOR_A_GIVEN_CITY = "SELECT * FROM addressbook WHERE city = ? ORDER BY firstname ASC";
        PreparedStatement preparedStatement = connection.prepareStatement(SORT_CONTACTS_BY_PERSONS_NAME_FOR_A_GIVEN_CITY);
        preparedStatement.setString(1, cityName);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            System.out.println("{" + "FirstName" + " : " + rs.getString("firstname") + ", " + "LastName" + " : " + rs.getString("lastname") + ", " +
                    "Address" + " : " + rs.getString("address") + ", " + "City" + " : " + rs.getString("city") + ", " +
                    "State" + " : " + rs.getString("state") + ", " + "Zip" + " : " + rs.getString("zip") + ", " +
                    "Phone" + " : " + rs.getString("phone") + ", " + "Email" + " : " + rs.getString("email") + "}");
        }
        connection.close();
    }
}
