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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import edu.utsa.cs3443.arcsecond_skylibrary.model.Constellation;
import edu.utsa.cs3443.arcsecond_skylibrary.model.Star;
import edu.utsa.cs3443.arcsecond_skylibrary.model.User;
/**
 * Class for the favorite activity, which displays the user's
 * favorite constellations and a button to access them.
 *
 * Net Scrap Development Corporation, LLC.
 * @author Mark Finley & Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class FavoritesActivity extends AppCompatActivity {
    //FIXME currently favoriting a new star after another deletes the previous favorited star??
    private User loggedInUser;
    private LinearLayout favoritesList;
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
        setContentView(R.layout.activity_favorites);

        context = this;

        // **Retrieve User from Intent**
        Intent intent = getIntent();
        loggedInUser = (User) intent.getSerializableExtra("USER");

        // **Debugging Log**
        Log.d("FavoritesActivity", "Received user: " + (loggedInUser != null ? loggedInUser.getUsername() : "NULL"));

        if (loggedInUser == null) {
            Log.d("FavoritesActivity", "User data unavailable.");
            Toast.makeText(this, "User data unavailable.", Toast.LENGTH_SHORT).show();
            return;  // Stay on FavoritesActivity instead of redirecting to LoginActivity
        }

        // **Populate Favorites List**
        populateFavoritesList();
    }

    /**
     * Refreshes the favorites list when the screen is switched back to by
     * the user.
     */
    @Override
    protected void onResume(){
        super.onResume();
        favoritesList.removeAllViews();
        User.refreshFavorites(context, loggedInUser);
        populateFavoritesList();
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
//                if (loggedInUser.getUsername().equals(tokens[1])){
//                    ArrayList<String> favArrList = new ArrayList<>();
//                    String[] favTokens = tokens[3].split(";");
//                    for (String s : favTokens) {
//                        favArrList.add(s.trim());
//                    }
//                    loggedInUser.setFavorites(favArrList);
//                    break;
//                }
//            }
//            r.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Actually prepares the screen elements and displays the favorite constellation
     * name, along with button linked to the info page.
     */
    private void populateFavoritesList() {
        favoritesList = findViewById(R.id.favorites_list);
        ArrayList<String> favoritedConstellations = loggedInUser.getFavorites();

        favoritesList.removeAllViews();

        if (favoritedConstellations.isEmpty()) {
            TextView noFavoritesText = new TextView(this);
            noFavoritesText.setText("No favorited constellations found.");
            noFavoritesText.setTextSize(20);
            noFavoritesText.setTextColor(getResources().getColor(android.R.color.white));
            noFavoritesText.setPadding(16, 8, 16, 8);
            favoritesList.addView(noFavoritesText);
            return;
        }

        Log.d("FavoritesActivity", "Current favorites: " + favoritedConstellations.toString());

        for (String constellationName : favoritedConstellations) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setPadding(16, 8, 16, 8);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            // Create a TextView for the constellation name
            TextView favoriteItem = new TextView(this);
            favoriteItem.setText(constellationName);
            favoriteItem.setTextSize(20);
            favoriteItem.setTextColor(getResources().getColor(android.R.color.white));
            favoriteItem.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            // Create a Button to navigate to InfoActivity
            Button infoButton = new Button(this);
            infoButton.setText("View Info");
            infoButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            infoButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));

            infoButton.setOnClickListener(v -> launchInfoActivity(constellationName));

            // Add views to rowLayout
            rowLayout.addView(favoriteItem);
            rowLayout.addView(infoButton);

            // Add row to favoritesList
            favoritesList.addView(rowLayout);
        }
    }

    // **Launch InfoActivity with the selected constellation**

    /**
     * The driver function that transfers the data from the favorite constellation
     * to the info view so that when the user presses the more info button they
     * actually see the correct information.
     * @param constellationName the constellation name, key to retrieving the
     * constellation data.
     */
    private void launchInfoActivity(String constellationName) {
        // Retrieve the full Constellation object
        Constellation selectedConstellation = retrieveConstellationObject(constellationName);

        if (selectedConstellation == null) {
            Log.d("FavoritesActivity", "Error: Could not find constellation object for " + constellationName);
            Toast.makeText(this, "Error loading constellation data.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass the full Constellation object to InfoActivity
        Intent i = new Intent(this, InfoActivity.class);
        i.putExtra("CONSTELLATION_OBJECT", selectedConstellation);
        i.putExtra("USER", loggedInUser);
        startActivity(i);
    }

    /**
     * Helper function to launchInfoActivity that stores the constellations
     * in an array list after they have been loaded from the constellations.csv.
     * @param name The specific constellation name that is being searched for.
     * @return Returns a selected constellation after being compared across the .csv.
     */
    private Constellation retrieveConstellationObject(String name) {
        ArrayList<Constellation> constellations = loadConstellationsFromCSV();

        for (Constellation c : constellations) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        Log.d("FavoritesActivity", "Constellation not found: " + name);
        return null;
    }

    /**
     * Creates a locally avaliable arrayList with all the data of the constellations,
     * with parse logic applied to the .csv so that the retrieveConstellationObject
     * method can transfer the selected (favorited) constellations data to the launchActivity.
     * @return the arraylist of constellations with their assembled data.
     */
    private ArrayList<Constellation> loadConstellationsFromCSV() {
        ArrayList<Constellation> constellations = new ArrayList<>();

        try {
            InputStream is = getAssets().open("constellations.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            reader.readLine(); // Skip header

            HashMap<String, Constellation> constellationMap = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                String starName = tokens[0].trim();
                String rightAscension = tokens[1].trim();
                String declination = tokens[2].trim();
                double apparentMagnitude = Double.parseDouble(tokens[3].trim());
                String direction = tokens[4].trim();
                String constellationName = tokens[5].trim();

                Constellation constellation = constellationMap.get(constellationName);
                if (constellation == null) {
                    constellation = new Constellation(constellationName);
                    constellation.setDirection(direction);
                    constellationMap.put(constellationName, constellation);
                }

                Star newStar = new Star(starName, rightAscension, declination, apparentMagnitude, direction, constellationName);
                constellation.addStar(newStar);
            }

            reader.close();
            constellations.addAll(constellationMap.values());

        } catch (IOException e) {
            Log.e("FavoritesActivity", "Error reading constellations.csv", e);
        }

        return constellations;
    }
}