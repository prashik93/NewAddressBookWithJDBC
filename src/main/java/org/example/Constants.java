package org.example;

public class Constants {

    // DB_CONNECTION
    public static final String DB_URL = "jdbc:mysql://localhost:3306/bridgelabz";
    public static final String USER = "root";
    public static final String PASS = "root";

    // CRUD_OPERATION
    public static final int EXIT = 0;
    public static final int ADD_CONTACT = 1;
    public static final int SHOW_CONTACT = 2;
    public static final int EDIT_CONTACT = 3;
    public static final int DELETE_CONTACT = 4;
    public static final int RETRIEVE_CONTACT_BY_CITY_OR_STATE = 5;
    public static final int CONTACTS_COUNT_BY_CITY_OR_STATE = 6;
    public static final int SORT_CONTACTS_BY_PERSONS_NAME = 7;

    // EDIT_CONTACT_DETAILS
    public static final int EDIT_FIRSTNAME = 1;
    public static final int EDIT_LASTNAME = 2;
    public static final int EDIT_ADDRESS = 3;
    public static final int EDIT_CITY = 4;
    public static final int EDIT_STATE = 5;
    public static final int EDIT_ZIP = 6;
    public static final int EDIT_PHONE = 7;
    public static final int EDIT_EMAIL = 8;
}
