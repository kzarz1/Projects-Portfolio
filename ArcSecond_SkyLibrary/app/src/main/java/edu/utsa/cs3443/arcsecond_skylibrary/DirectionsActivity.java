package edu.utsa.cs3443.arcsecond_skylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.arcsecond_skylibrary.model.User;

/*constellation image names should just be the name of the constellation. Match csv string all lower case
i.e. Leo's image file should should be named " leo.png ". Also please crop images to fit well.*/

// RENAE

/**
 * Class for the directions activity, which displays the directional buttons
 * containing the list of constellations and
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas & Mark Finley
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class DirectionsActivity extends AppCompatActivity {

    private User user;

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
        setContentView(R.layout.activity_directions);

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);

        Intent i = getIntent();
        if (i != null) {
            user = (User) i.getSerializableExtra("USER");
        }
        if (user == null){
            welcomeTextView.setText("Welcome, Guest!");
        } else {
            welcomeTextView.setText("Welcome, " + user.getUsername() + "!");
        }

        Button northButton = findViewById(R.id.north_button);
        northButton.setOnClickListener( (v) -> { launchActivity("North"); });

        Button southButton = findViewById(R.id.south_button);
        southButton.setOnClickListener((v) -> { launchActivity("South"); });

        Button viewFavoritesButton = findViewById(R.id.viewFavorites_button);
        viewFavoritesButton.setOnClickListener( (v) -> {
            if (user != null) { launchFavorites(); } else {
                Toast.makeText(this, "Favorites unavailable to guest users. Please log in or signup.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This launch activity provides intents to the stars activity.
     * @param direction The celestial direction of a particular constellation. Each
     *  constellation has either North or South associated with it, listed on a per-star
     *  basis in the .csv. Passes both direction and full user data to the stars activity.
     */
    private void launchActivity(String direction){
        try {
            Log.d("DirectionsActivity", "About to create intent for StarsActivity");
            Intent i = new Intent(DirectionsActivity.this, StarsActivity.class);
            Log.d("DirectionsActivity", "Intent created");
            i.putExtra("DIRECTION", direction);
            i.putExtra("USER", user);
            Log.d("DirectionsActivity", "About to start StarsActivity");
            startActivity(i);
            Log.d("DirectionsActivity", "After startActivity call");
        } catch (Exception e){
            Log.e("DirectionsActivity", "Error launching StarsActivity: " + e.getMessage());
            Toast.makeText(this, "Error launching activity", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Creates the intent that will be passed to the FavoritesActivity.
     * This intent passes the full user object to get user favorite information.
     */
    private void launchFavorites(){
        Intent i = new Intent(DirectionsActivity.this, FavoritesActivity.class);
        //i.putExtra("FAVORITE_CONSTELLATIONS", user.getFavorites());
        i.putExtra("USER", user); // Pass full user object
        startActivity(i);
    }

}
