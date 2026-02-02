package edu.utsa.cs3443.arcsecond_skylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.*;

import edu.utsa.cs3443.arcsecond_skylibrary.model.User;
import edu.utsa.cs3443.arcsecond_skylibrary.model.UserManager;

// ANGELA
/**
 * Class for the login activity, the logic of a user has already signed up
 * as tracked by the user.csv, logging into to the app and leads into the directions activity.
 * Net Scrap Development Corporation, LLC.
 *
 * @author Angela Oommen & Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private UserManager manager;

    /**
     * This onCreate saves the activity instance.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new UserManager();
        loadUsersFromCSV();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonLogin.setOnClickListener(v -> attemptLogin());
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * This loads users from the user.csv for the purposes of comparing to the known user.csv group.
     */
    private void loadUsersFromCSV() {
        try {
            File file = new File(getFilesDir(), "users.csv");
            if (!file.exists()) { return; }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine(); //skip header

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("/");

                if (tokens.length >= 3) {
                    String email = tokens[0].trim();
                    String username = tokens[1].trim();
                    String password = tokens[2].trim();
                    ArrayList<String> favorites = new ArrayList<>();

                    if (tokens.length > 3) {
                        String[] favTokens = tokens[3].split(";");
                        for (String s : favTokens) {
                            favorites.add(s.trim());
                        }
                    }

                    manager.addUser(username, password, email, favorites);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This checks that the user who is logging in is entering their correct password and username,
     * and is checked against the stored information in user.csv. Also enforced are character length
     * and type requirements.
     */
    private void attemptLogin() {
        String username = getUsernameInput();
        String password = getPasswordInput();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter your username/email and password.",Toast.LENGTH_LONG).show();
            return;
        }

        if (username.length() < 3){
            Toast.makeText(this, "Username too short. Please enter a username more than three characters.",Toast.LENGTH_LONG).show();
            return;
        } else if (username.length() > 16){
            Toast.makeText(this, "Username too long. Please enter a username less than 16 characters.",Toast.LENGTH_LONG).show();
            return;
        }

        User existingUser = manager.getUser(username);

        if (existingUser != null && manager.isValidCredentials(username, password)) {
            launchActivity(existingUser);
        } else {
            Toast.makeText(this, "Incorrect username/email or password. Please try again.",Toast.LENGTH_LONG).show();
        }
    }

    // Getters and Setters

    /**
     * This gets the user input in the username field.
     * @return This returns the string that the user input.
     */
    public String getUsernameInput() {
        return editTextUsername.getText().toString().trim();
    }

    /**
     * This gets the user input in the password field.
     * @return This returns the String that the user input.
     */
    public String getPasswordInput() {
        return editTextPassword.getText().toString();
    }

    /**
     * This launches the directions activity with the information corresponding to the user
     * that successfully logged in on this screen, passed through intent.
     * @param u Takes the full user object for passing into the intent.
     */
    private void launchActivity(User u){
        Intent intent = new Intent(LoginActivity.this, DirectionsActivity.class);
        intent.putExtra("USER", u);
        startActivity(intent);
    }

}