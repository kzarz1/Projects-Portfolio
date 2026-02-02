package edu.utsa.cs3443.arcsecond_skylibrary.model;

import java.util.*;

/**
 *
 * Class that manages the User class
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 *
 */
public class UserManager {
    private HashMap<String, User> users = new HashMap<>();

    /**
     * Adds a new User to the UserManager at signup
     * @param username, the username of the User
     * @param password, the password of the User
     * @param email, the email of the User
     * @param favorites, the favorites of the User
     */
    public void addUser(String username, String password, String email, ArrayList<String> favorites){
        User u = new User(username, password, email, favorites);
        users.put(username, u);
    }

    /**
     * Adds a new user to the UserManager (no email needed)
     * @param username
     * @param password
     */
    public void addUser(String username, String password){
        User u = new User(username, password);
        users.put(username, u);
    }

    /**
     * Returns the specified User from username
     * @param username, the username of the User
     * @return the User object from the username
     */
    public User getUser(String username) { return users.get(username); };

    /**
     * Checks if the username and password are valid / if the account exists
     * @param username, the username of the user
     * @param password, the password of the user
     * @return true if the account exists, false otherwise
     */
    public boolean isValidCredentials(String username, String password){
        User u = users.get(username);
        if (u != null && u.getPassword().equals(password)){ return true; }
        return false;
    }
}
