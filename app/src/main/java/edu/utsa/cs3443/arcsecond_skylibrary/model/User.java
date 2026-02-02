package edu.utsa.cs3443.arcsecond_skylibrary.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.util.*;
import java.io.*;

/**
 *
 * Class that represents a User
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 *
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private ArrayList<String> favorites;

    /**
     * Constructor that initializes a User with a pre-existing favorite constellations arraylist
     * @param username
     * @param password
     * @param favorites
     */
    public User(String username, String password, String email, ArrayList<String> favorites){
        this.username = username;
        this.password = password;
        this.email = email;
        this.favorites = favorites;
    }

    /**
     * Constructor that initializes a new User with an empty favorite constellations arraylist
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.favorites = new ArrayList<>();
    }

    /**
     * Constructor that initializes a new User w/o email with an empty favorite constellations arraylist
     * @param username
     * @param password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.email = null;
        this.favorites = new ArrayList<>();
    }

    /**
     * Returns the username of the User
     * @return the username of the User object
     */
    public String getUsername() { return username; }

    /**
     * Sets the username of the User
     * @param username, the username of the user
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Returns the password of the User
     * @return the password of the user
     */
    public String getPassword() { return password; }

    /**
     * Sets the password of the User
     * @param password, the password of the user
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Returns the email of the User
     * @return
     */
    public String getEmail() { return email; }

    /**
     * Sets the email of the User
     * @param email, the email of the user
     */
    public void setEmail(String email){ this.email = email;}

    /**
     * Returns the favorite constellations of the User as a string arraylist
     * @return the favorites of the user
     */
    public ArrayList<String> getFavorites() { return favorites; }

    /**
     * Sets the favorite constellations of the User
     * @param favorites, the favorites of the user to be set to
     */
    public void setFavorites(ArrayList<String> favorites) { this.favorites = favorites; }

    /**
     * Adds a constellation to favorited constellations
     * @param c, the constellation to be added
     */
    public void addConstellationToFavorites(String c) {
        if (!favorites.contains(c)) { favorites.add(c); }
    }

    /**
     * Removes a constellation from favorited constellations
     * @param c, the constellation to be removed
     */
    public void removeConstellationFromFavorites(String c) { favorites.remove(c); }

    /**
     * helper function to refreshing favorites list, reads the user.csv
     * file for the favorites of the user, and sets up an array list to
     * reset the user's favorites.
     */
    public static void refreshFavorites(Context context, User u) {
        AssetManager m = context.getAssets();
        try {
            File file = new File(context.getFilesDir(), "users.csv");
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line;

            r.readLine(); //skip header

            while ((line = r.readLine()) != null) {
                String[] tokens = line.split("/");
                if (u.getUsername().equals(tokens[1])){
                    ArrayList<String> favArrList = new ArrayList<>();
                    String[] favTokens = tokens[3].split(";");
                    for (String s : favTokens) {
                        favArrList.add(s.trim());
                    }
                    u.setFavorites(favArrList);
                    break;
                }
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
