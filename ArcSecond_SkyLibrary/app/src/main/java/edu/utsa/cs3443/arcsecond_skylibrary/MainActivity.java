package edu.utsa.cs3443.arcsecond_skylibrary;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// ADINE
/**
 * Class for the main screen, the first screen that appears upon application launch.
 * This is the start point of the app and contains buttons for login, signup, continue as guest and
 * (possibly as a stretch goal) for the about Arcus screen.
 *
 * Net Scrap Development Corporation, LLC.
 * @author Adine Bahambana & Renae Manalastas
 *
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView continueAsGuest;

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
        setContentView(R.layout.activity_main);

        context = this;

        copyAssets("users.csv");
        copyAssets("constellations.csv");

        continueAsGuest = findViewById(R.id.continue_text);
        ImageButton signup = findViewById(R.id.signUp);
        ImageButton login = findViewById(R.id.logIn);

        continueAsGuest.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, DirectionsActivity.class);
            startActivity(i);
        });

        signup.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(i);
        });
        login.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

    /**
     * Copies files to local since assets folder is read only
     * @param filename, name of file
     */
    private void copyAssets(String filename) {
        try {
            File outFile = new File(context.getFilesDir(), filename);
            if (outFile.exists()) return; // Don't copy if it already exists

            InputStream in = context.getAssets().open(filename);
            OutputStream out = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
