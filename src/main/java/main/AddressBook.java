package main;

import constants.Constants;
import jdbcconnection.JDBCConnection;

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
            boolean result = checkContactExistOrNot(contactDetails.getFirstName());
            if(!result) {
                contactDetailsArrayList.add(contactDetails);
                insertContactDetailsToDatabase();
                System.out.println("Data Added In Database...");
                return;
            }
            System.out.println("Contact already exist...");
        }
    }

    public boolean checkContactExistOrNot(String newFirstName) throws SQLException {
        String RETRIEVE_FIRSTNAME_FROM_DATABASE = "SELECT * FROM addressbook1 WHERE firstname = ?";
        Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_FIRSTNAME_FROM_DATABASE);
        preparedStatement.setString(1, newFirstName);
        ResultSet resultSet = preparedStatement.executeQuery();
        String firstNameFromDatabase = null;
        String lastNameFromDatabase = null;
        while(resultSet.next()) {
            firstNameFromDatabase = resultSet.getString("firstname");
            lastNameFromDatabase = resultSet.getString("lastname");
        }

        if((firstNameFromDatabase != null) && (lastNameFromDatabase != null))
            return true;
        return false;
    }

    public void insertContactDetailsToDatabase() throws SQLException {
        String INSERT_USERS_QUERY = "INSERT INTO addressbook1 (firstname, lastname, address, city, state, zip, phone, email) VALUES " +
                " (?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_QUERY); {
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
            connection.close();
        }
    }

    public void retrieveAllContactsFromDatabase() throws SQLException {
        String RETRIEVAL_USERS_QUERY = "SELECT * FROM addressbook1";
        Connection connection = conn.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(RETRIEVAL_USERS_QUERY);
        retrieveContactDetailsFromDatabase(rs);
    }

    public void retrieveContactDetailsFromDatabase(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("{" + "FirstName" + " : " + rs.getString( "firstname") + ", " + "LastName" + " : " +rs.getString("lastname") + ", " +
                    "Address" + " : " + rs.getString("address") + ", " + "City" + " : " + rs.getString("city") + ", " +
                    "State" + " : " + rs.getString("state") + ", " + "Zip" + " : " + rs.getString("zip") + ", " +
                    "Phone" + " : " + rs.getString("phone") + ", " + "Email" + " : " + rs.getString("email") + "}");
        }
    }

    public void updateContactDetailsFromDatabase() throws SQLException {
        Connection connection = conn.getConnection();
        System.out.print("Enter FirstName to Update Record : ");
        String keyFirstName = scnr.next();
        System.out.print("Enter LastName to Update Record : ");
        String keyLastName = scnr.next();
        String RETRIEVAL_USERS_QUERY = "SELECT * FROM addressbook1 WHERE firstname = ? AND lastname = ?";

        boolean result = checkContactExistOrNot(keyFirstName);
        if(result) {
            PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVAL_USERS_QUERY);
            preparedStatement.setString(1, keyFirstName);
            preparedStatement.setString(2, keyLastName);
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.executeBatch();
            retrieveContactDetailsFromDatabase(rs);
            connection.close();
            updateContact(keyFirstName, keyLastName);
            return;
        }
        System.out.println("No Such Record Found");
    }

    public void updateContact(String keyFirstName, String keyLastName) throws SQLException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("\nWhat do you want to edit? : ");
        System.out.println("\n1.Edit First Name\n2.Edit Last Name\n3.Edit Address\n4.Edit City\n5.Edit State" +
                "\n6.Edit Zip Code\n7.Edit Phone\n8.Edit Email\n9.Exit");
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
        String UPDATE_FIRST_NAME = String.format("UPDATE addressbook1 SET %s = ? WHERE firstname = ? AND lastname = ?", columnName);
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIRST_NAME);

        preparedStatement.setString(1, updateData);
        preparedStatement.setString(2, keyFirstName);
        preparedStatement.setString(3, keyLastName);
        preparedStatement.addBatch();
        preparedStatement.executeBatch();

        connection.close();
    }

    public void deleteContactDetailsFromDatabase() throws SQLException {
        String DELETE_USERS_QUERY = "DELETE FROM addressbook1 WHERE firstname = ? AND lastname = ?";
        System.out.print("Enter FirstName to Delete Record : ");
        String keyFirstName = scnr.next();
        System.out.print("Enter LastName to Delete Record : ");
        String keyLastName = scnr.next();

        boolean result = checkContactExistOrNot(keyFirstName);
        if(result) {
            Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_QUERY);
            preparedStatement.setString(1, keyFirstName);
            preparedStatement.setString(2, keyLastName);
            preparedStatement.execute();
            connection.close();
            System.out.println("Record Deleted Successfully...");
            return;
        }
        System.out.println("No Such Record Found...");
    }

    public void retrievingPersonBelongingToCityOrState() throws SQLException {
        System.out.print("\nEnter City/State : ");
        String cityOrState = scnr.next();
        System.out.print("Enter Name : ");
        String name = scnr.next();
        Connection connection = conn.getConnection();
        String RETRIEVE_PERSON_BELONGING_TO_CITY_OR_STATE_QUERY = String.format("SELECT * FROM addressbook1 WHERE %s = ?", cityOrState);
        PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_PERSON_BELONGING_TO_CITY_OR_STATE_QUERY);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        retrieveContactDetailsFromDatabase(rs);
        connection.close();
    }

    public void contactsCountByCityOrState() throws SQLException {
        System.out.print("\nEnter City/State : ");
        String cityOrState = scnr.next().toLowerCase();
        System.out.print("Enter Name : ");
        String name = scnr.next().toLowerCase();
        Connection connection = conn.getConnection();
        String GET_COUNT_OF_CONTACTS_IN_CITY_OR_SATE = String.format("SELECT COUNT(?) AS countRow FROM addressbook1 WHERE %s = ?", cityOrState);
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
        String SORT_CONTACTS_BY_PERSONS_NAME_FOR_A_GIVEN_CITY = "SELECT * FROM addressbook1 WHERE city = ? ORDER BY firstname ASC";
        PreparedStatement preparedStatement = connection.prepareStatement(SORT_CONTACTS_BY_PERSONS_NAME_FOR_A_GIVEN_CITY);
        preparedStatement.setString(1, cityName);
        ResultSet rs = preparedStatement.executeQuery();
        retrieveContactDetailsFromDatabase(rs);
        connection.close();
    }

    public void identifyEachAddressBookWithNameAndType() throws SQLException {
        Connection connection = conn.getConnection();
        String ADD_CONTACT_TYPE_IN_DATABASE_USING_ALTER_QUERY = "ALTER TABLE addressbook ADD contact_type VARCHAR(40) not null default 'Family' ";
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_CONTACT_TYPE_IN_DATABASE_USING_ALTER_QUERY);
        preparedStatement.execute();
        connection.close();
    }

    public void countContactsByType() throws SQLException {
        System.out.print("\nEnter Contact-Type : ");
        String contactType = scnr.next().toLowerCase();
        Connection connection = conn.getConnection();
        String COUNT_CONTACTS_BY_TYPE = "SELECT COUNT(*) AS countType FROM contact_type ct JOIN addressbookcontacttype abct " +
                                        "ON ct.id = abct.contacttypeid JOIN addressbook1 ab1 " +
                                        "ON abct.addressbookid = ab1.addbookid  " +
                                        "where ct.type = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CONTACTS_BY_TYPE);
        preparedStatement.setString(1, contactType);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            System.out.println("Count : " + (rs.getInt("countType")));
        }
        connection.close();
    }

    public void addContactToBothFriendAndFamily() throws SQLException {
        String ADD_CONTACT_TO_BOTH_FRIEND_AND_FAMILY = "INSERT INTO addressbook VALUES('Prashik', 'Kamble', 'Sanglud', 'Akola', 'Mah', '444102', '8806187589', 'p@gmail.com', 'Friend')," +
                                                                                     "('Prashik', 'Kamble', 'Sanglud', 'Akola', 'Mah', '444102', '8806187589', 'p@gmail.com', 'Family');";
        Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_CONTACT_TO_BOTH_FRIEND_AND_FAMILY);
        ResultSet rs = preparedStatement.executeQuery();
        retrieveContactDetailsFromDatabase(rs);
    }


}
