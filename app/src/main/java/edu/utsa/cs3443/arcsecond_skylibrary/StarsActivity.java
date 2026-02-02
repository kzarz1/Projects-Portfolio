package edu.utsa.cs3443.arcsecond_skylibrary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.*;

import edu.utsa.cs3443.arcsecond_skylibrary.model.Constellation;
import edu.utsa.cs3443.arcsecond_skylibrary.model.Sky;
import edu.utsa.cs3443.arcsecond_skylibrary.model.Star;

// RENAE
/**
 * Class for the Stars activity, which displays the list of all constellations in either the
 * North or South celestial hemisphere, depending on which direction button the user pressed
 * on the previous directions screen.
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 */
public class StarsActivity extends AppCompatActivity {
    private Sky sky;
    private Sky filteredSky;
    private String direction;

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
        setContentView(R.layout.activity_stars);

        direction = getIntent().getStringExtra("DIRECTION");
        Log.d("StarsActivity", "Direction received: " + direction);

        sky = loadSky();
        Log.d("StarsActivity", "Sky loaded with " + sky.getSizeOfConstellationList() + " constellations");

        filteredSky = filterConstellationsByDirection(sky, direction);
        Log.d("StarsActivity", "Filtered sky has " + filteredSky.getSizeOfConstellationList() + " constellations");

        dynamicSetup(filteredSky);
        Log.d("StarsActivity", "Dynamic Setup Loaded");
    }

    /**
     * Initializes the Sky object, which acts as a container or aggregation of the constellations.
     * Also provides for the Star object composition of name, stats, direction, etc.
     * @return the sky object.
     */
    private Sky loadSky(){
        sky = new Sky();
        try {
            InputStream f = getAssets().open("constellations.csv");
            Scanner s = new Scanner(f);

            s.nextLine(); //skip header

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] tokens = line.split(",");
                Star newStar = new Star(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]), tokens[4], tokens[5]);
                // Name,Right Ascension,Declination,Apparent Magnitude,Direction,Constellation
                String direction = tokens[4];
                String constellationName = tokens[5];
                Constellation c = sky.getConstellation(constellationName);
                if (c == null){
                    c = new Constellation(constellationName);
                    c.setDirection(direction);
                    sky.addConstellation(c);
                }
                c.addStar(newStar);

            }
            s.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return sky;
    }

    /**
     * The method for sorting which constellations belong to which view, North or South.
     * @param sky Needed creating arraylist of constellations to be shown.
     * @param direction Needed for making sure correct direction constellations are shown.
     * @return The Sky object with the sorted constellations.
     */
    private Sky filterConstellationsByDirection(Sky sky, String direction) {
        filteredSky = new Sky();
        ArrayList<Constellation> constellations = sky.getConstellations();

        for (Constellation c : constellations){
            if (c.getDirection().equals(direction)){
                filteredSky.addConstellation(c);
            }
        }

        return filteredSky;
    }

    /**
     * Sets up the ui element layout for each constellation button in the list with images.
     * @param filteredSky This Sky object contains the filtered by direction constellations.
     */
    private void dynamicSetup(Sky filteredSky){
        ArrayList<Constellation> constellations = filteredSky.getConstellations();

        LinearLayout verticalLayout = findViewById(R.id.constellationHolder);

        LinearLayout rowLayout = null;

        for (int i = 0; i < constellations.size(); i++) {
            if (i % 2 == 0) {
                rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                verticalLayout.addView(rowLayout);
            }

            Constellation c = constellations.get(i);

            LinearLayout cLayout = new LinearLayout(this);
            cLayout.setOrientation(LinearLayout.VERTICAL);
            cLayout.setPadding(10,10,10,10);
            cLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f)); //divide so 2 in each row

            cLayout.setClickable(true);
//            cLayout.setBackgroundResource(android.R.drawable.btn_default);

            ImageView img = new ImageView(this);
//            String filename;
//            if (c.getPictureFilename().contains("ö")){ //for boötes
//                filename = c.getPictureFilename().replace("ö", "o");
//            } else {
//                filename = c.getPictureFilename();
//            }
//            Log.d("StarsActivity", "Loading " + filename);
            int imgRes = getResources().getIdentifier(c.getPictureFilename(), "drawable", getPackageName());
            ColorMatrix colorMatrixInverted = new ColorMatrix(new float[] {
                    -1.0f,  0,      0,      0,  255, // Red
                    0,    -1.0f,   0,      0,  255, // Green
                    0,     0,    -1.0f,    0,  255, // Blue
                    0,     0,     0,     1.0f,  0   // Alpha
            });
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrixInverted);
            img.setColorFilter(filter);

            img.setImageResource(imgRes);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    300));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView text = new TextView(this);
            text.setText(c.getName());
            text.setTextColor(Color.WHITE);
            text.setGravity(Gravity.CENTER);

            cLayout.addView(img);
            cLayout.addView(text);

            cLayout.setOnClickListener(v -> launchActivity(c));

            rowLayout.addView(cLayout);
        }
    }

    /**
     * This launch activity passes the constellation and user objects to infoActivity through intent.
     * @param constellation Passes the constellation object to its respective Info screen.
     */
    private void launchActivity(Constellation constellation){
        Intent i = new Intent(this, InfoActivity.class);
        i.putExtra("CONSTELLATION_OBJECT", constellation);
        i.putExtra("USER", getIntent().getSerializableExtra("USER"));
        startActivity(i);
    }
}
