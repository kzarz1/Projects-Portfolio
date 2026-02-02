package edu.utsa.cs3443.arcsecond_skylibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;

import edu.utsa.cs3443.arcsecond_skylibrary.model.User;

// ANGELA
/**
 * Class for the signup activity, which will receive a new user's email, username, and password.
 * A new user must signup in order to use the favorites function.
 *
 * Net Scrap Development Corporation, LLC.
 * @author Angela Oommen & Renae Manalastas
 *
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class SignupActivity extends AppCompatActivity {

    private String username;
    private String password1;
    private String password2;
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignup, buttonBack;
    private Context context;

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
        setContentView(R.layout.activity_signup);

        context = this;

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonSignup = findViewById(R.id.buttonSignup);
        buttonBack = findViewById(R.id.buttonBack);

        buttonSignup.setOnClickListener(v -> attemptSignup());
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * This function receives the user's input for the fields and validates whether the input
     * meets specified character criteria (i.e. username must be longer than 3 characters).
     */
    private void attemptSignup() {
        String email = editTextEmail.getText().toString().trim();
        username = editTextUsername.getText().toString().trim();
        password1 = editTextPassword.getText().toString();
        password2 = editTextConfirmPassword.getText().toString();

        boolean valid = true;
        boolean matches = true;

        if (username.length() < 3){
            Toast.makeText(this, "Username too short. Please enter a username more than three characters.",Toast.LENGTH_LONG).show();
            return;
        } else if (username.length() > 16){
            Toast.makeText(this, "Username too long. Please enter a username less than 16 characters.",Toast.LENGTH_LONG).show();
            return;
        }

        if (!email.contains("@")) {
            Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a valid username.", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (!isValidPassword(password1)) {
            Toast.makeText(this, "Password must be 8+ chars with upper, lower, and special character.", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (!password1.equals(password2)){
            Toast.makeText(this, "Passwords must match.", Toast.LENGTH_LONG).show();
            matches = false;
        }

        if (valid && matches) {
            User newUser = new User(username, password1, email);
            saveToFile(newUser);
            Intent intent = new Intent(SignupActivity.this, DirectionsActivity.class);
            intent.putExtra("USER", newUser);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This checks if the password is valid (contains upper, lower case, and special characters,
     * of length 8 or more).
     * @param password Takes the user's input in password field as a parameter.
     * @return returns boolean whether the password is valid (true means is valid).
     */
    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    /**
     * This vital function saves the user's credentials into the user.csv file, which is necessary
     * both for logins and for accessing favorites.
     * @param u This takes the user object that is established from the provided credentials and
     *          places those credentials in the csv.
     */
    private void saveToFile(User u) {
        File f = new File(context.getFilesDir(), "users.csv");
        try {
            FileInputStream inFile = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inFile));

            StringBuilder userData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                userData.append(line).append("\n");
            }
            reader.close();

            StringBuilder newUserData = new StringBuilder();
            newUserData.append(u.getEmail()).append("/")
                    .append(u.getUsername()).append("/")
                    .append(u.getPassword()).append("/");

            if (u.getFavorites() != null && !u.getFavorites().isEmpty()) {
                for (String favorite : u.getFavorites()) {
                    newUserData.append(favorite).append(",");
                }
                newUserData.setLength(newUserData.length() - 1);
            }

            newUserData.append("\n");

            userData.append(newUserData.toString());

            FileOutputStream outFile = new FileOutputStream(f);
            outFile.write(userData.toString().getBytes());
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}