package edu.utsa.cs3443.arcsecond_skylibrary;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import edu.utsa.cs3443.arcsecond_skylibrary.model.Constellation;
import edu.utsa.cs3443.arcsecond_skylibrary.model.Star;
import edu.utsa.cs3443.arcsecond_skylibrary.model.User;

// MARK

/**
 * Class for the info activity, which displays detailed constellation statistics
 * with constituent stars and a button to favorite them.
 *
 * Net Scrap Development Corporation, LLC.
 * @author Mark Finley & Renae Manalastas
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class InfoActivity extends AppCompatActivity {
    private Constellation selectedConstellation;
    private Button favoriteButton;
    private User loggedInUser;
    private Context context;

    /**
     * The main driver in this class. This onCreate saves the activity instance.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        favoriteButton = findViewById(R.id.favoriteButton);

        context = this;

        // **Retrieve Constellation from Intent**
        Intent intent = getIntent();
        selectedConstellation = (Constellation) intent.getSerializableExtra("CONSTELLATION_OBJECT");
        if (selectedConstellation == null) {
            Log.d("InfoActivity", "Failed to retrieve constellation.");
            Toast.makeText(this, "Constellation data not available.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("InfoActivity", "Picture filename: " + selectedConstellation.getPictureFilename());

        // **Retrieve Logged-In User from Intent**
        loggedInUser = (User) intent.getSerializableExtra("USER");
        if (loggedInUser == null) {
            Log.d("InfoActivity", "User not found in Intent.");
            //Toast.makeText(this, "User data unavailable.", Toast.LENGTH_SHORT).show();
        } else {
            User.refreshFavorites(context, loggedInUser);
        }

        // **Update UI Elements**
        TextView titleView = findViewById(R.id.constellationName);
        titleView.setText(selectedConstellation.getName());

        ImageView constellationImageHolder = findViewById(R.id.constellationImageHolder);
        int imageResId = getResources().getIdentifier(
                selectedConstellation.getPictureFilename(),
                "drawable",
                getPackageName()
        );
        if (imageResId != 0) {
            constellationImageHolder.setImageResource(imageResId);
        }

        populateStatsList(selectedConstellation.getStars());

        if (loggedInUser != null){
            // **Check Favorite Status**
            boolean isFavorited = loggedInUser.getFavorites().contains(selectedConstellation.getName());
            updateFavoriteButton(isFavorited);
        }

        // **Handle Favorite Button Click**
        favoriteButton.setOnClickListener(v -> {
            if (loggedInUser != null){
                toggleFavorite();
            } else {
                Toast.makeText(this, "Please log in or sign up to access favorites feature.", Toast.LENGTH_LONG).show();
            }

        });

    }

    /**
     * Refreshes the favorites list when the screen is switched back to by
     * the user.
     */
    @Override
    protected void onResume(){
        super.onResume();
        User.refreshFavorites(context, loggedInUser);
    }

//    /**
//     * helper function to refreshing favorites list, reads the user.csv
//     * file for the favorites of the user, and sets up an array list to
//     * reset the user's favorites.
//     */
//    private void refreshFavorites() {
//        AssetManager m = context.getAssets();
//        try {
//            File file = new File(context.getFilesDir(), "users.csv");
//            BufferedReader r = new BufferedReader(new FileReader(file));
//            String line;
//
//            r.readLine(); //skip header
//
//            while ((line = r.readLine()) != null) {
//                String[] tokens = line.split("/");
//                if (loggedInUser != null){
//                    if (loggedInUser.getUsername().equals(tokens[1])){
//                        ArrayList<String> favArrList = new ArrayList<>();
//                        String[] favTokens = tokens[3].split(";");
//                        for (String s : favTokens) {
//                            favArrList.add(s.trim());
//                        }
//                        loggedInUser.setFavorites(favArrList);
//                        break;
//                    }
//                } else {
//                    break;
//                }
//            }
//            r.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This creates the visual field for each star name of the constellation
     * and displays their associated stats.
     * @param stars
     */
    private void populateStatsList(ArrayList<Star> stars) {
        LinearLayout statsList = findViewById(R.id.stats_list);

        for (Star star : stars) {
            // Create a vertical layout for each star's details
            LinearLayout starItem = new LinearLayout(this);
            starItem.setOrientation(LinearLayout.VERTICAL);
            starItem.setPadding(16, 16, 16, 16);

            // Add star name
            TextView starName = new TextView(this);
            starName.setText("Name: " + star.getName());
            starName.setTextSize(16);
            starName.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            starItem.addView(starName);

            // Add star apparent magnitude
            TextView starMagnitude = new TextView(this);
            starMagnitude.setTextColor(getResources().getColor(android.R.color.white));
            starMagnitude.setText("Apparent Magnitude: " + star.getApparentMagnitude());
            starName.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            starItem.addView(starMagnitude);

            // Add star coordinates
            TextView starCoordinates = new TextView(this);
            starCoordinates.setTextColor(getResources().getColor(android.R.color.white));
            starCoordinates.setText("Coordinates: RA " + star.getRightAscension() +
                    ", Dec " + star.getDeclination());
            starName.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            starItem.addView(starCoordinates);

            // Add the star item layout to the stats list
            statsList.addView(starItem);
        }
    }

    /**
     * The implementation of the favorite button, whose state is saved according to user
     * and connected with the user's favorites detected in the .csv.
     */
    private void toggleFavorite() {
        // In toggleFavorite() method - before saving
        Log.d("InfoActivity", "Before toggle - User favorites: " + loggedInUser.getFavorites().toString());
        boolean isFavorited = loggedInUser.getFavorites().contains(selectedConstellation.getName());

        if (isFavorited) {
            loggedInUser.removeConstellationFromFavorites(selectedConstellation.getName());
        } else {
            loggedInUser.addConstellationToFavorites(selectedConstellation.getName());
        }

        // Save updates to user.csv
        saveFavoritesToCSV(loggedInUser);

        updateFavoriteButton(!isFavorited);

//        Intent i = new Intent(InfoActivity.this, DirectionsActivity.class);
//        i.putExtra("USER", loggedInUser);
//        startActivity(i);
//        finish();

        Log.d("InfoActivity", "After toggle - User favorites: " + loggedInUser.getFavorites().toString());
    }

    /**
     * Represents the state of the favorite button accurately by displaying
     * "favorite" when the constellation is not favorited, and "Unfavorite" when
     * the constellation is favorite.
     * @param isFavorited
     */
    private void updateFavoriteButton(boolean isFavorited) {
        if (isFavorited) {
            favoriteButton.setText("Unfavorite");
            favoriteButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_light));
        } else {
            favoriteButton.setText("Favorite");
            favoriteButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_purple));
        }
    }

    /**
     * This saves the new favorite constellation to the user.csv, in the favorite
     * constellation segment after the last delimiter.
     * @param user This is the full user object and is necessary to access the .csv
     *    in order to write to it. Received from intent.
     */
    private void saveFavoritesToCSV(User user) {
        try {
            File file = new File(context.getFilesDir(), "users.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    updatedLines.add(line); // Preserve header
                    firstLineSkipped = true;
                    continue;
                }

                String[] userData = line.split("/");
                if (userData.length < 4) continue;

                String username = userData[1].trim();

                if (username.equals(user.getUsername())) {
                    ArrayList<String> sorted = user.getFavorites();
                    Collections.sort(sorted, (s1, s2) -> s1.compareTo(s2));
                    String updatedFavorites = String.join(";", sorted);
                    updatedLines.add(userData[0] + "/" + userData[1] + "/" + userData[2] + "/" + updatedFavorites);
                } else {
                    updatedLines.add(line);
                }
            }

            reader.close();

            FileOutputStream fos = new FileOutputStream(file);
            for (String updatedLine : updatedLines) {
                fos.write((updatedLine + "\n").getBytes());
            }
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}