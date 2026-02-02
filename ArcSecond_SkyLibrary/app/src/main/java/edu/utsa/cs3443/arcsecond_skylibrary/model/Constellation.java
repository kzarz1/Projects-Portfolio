package edu.utsa.cs3443.arcsecond_skylibrary.model;

import java.util.*;
import java.io.Serializable;

/**
 *
 * Class that represents and initializes a Constellation object
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 *
 */
public class Constellation implements Serializable {

    /*Mark here: We should probably handle favorites as a
boolean attribute of the constellation class. Initailized as false, and hitting favorite button
could simply flip it to true. Might handle as a separate favorites screen, which could list
out only favorited constellations. This screen would show the favorited constellations as
clickable buttons. We could handle unfavoriting either as a toggleable favorite button click,
or as its own separate button. Note I have not implemented any of this yet, just describing
how this could work. */

    private ArrayList<Star> stars;
    private String name;
    private String direction;
    private String pictureFilename;
    private boolean isFavorited;

    /**
     * Constructor that initializes a Constellation object with given arraylist of stars
     * that belong to the constellation
     * @param stars, an arraylist of star objects
     */
    public Constellation(ArrayList<Star> stars) {
        this.stars = stars;
        this.pictureFilename = initializePictureFilename(name.toLowerCase());
        isFavorited = false;
    }

    /**
     * Constructor that initializes a Constellation object with a name
     * and an empty stars arraylist
     * @param name
     */
    public Constellation(String name){
        stars = new ArrayList<>();
        this.name = name;
        this.pictureFilename = initializePictureFilename(name.toLowerCase());
        isFavorited = false;
    }

    /**
     * Constructor that initializes a Constellation without a name
     * and an empty stars arraylist
     */
    public Constellation() {
        stars = new ArrayList<>();
        isFavorited = false;
    }

    /**
     * Returns the name of the constellation
     * @return the name of the constellation
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the constellation
     * @param name, the name of the constellation object to be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the direction the constellation belongs to
     * @return the direction of the constellation
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the constellation
     * @param direction, the direction of the constellation to be set to
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Initializes the picture filename properly
     * Handles names with spaces to replace with underscores for grabbing
     * the picture filenames
     * @param name, the name of the constellation
     * @return
     */
    public String initializePictureFilename(String name) {
        if (name.contains(" ")) {
            pictureFilename = name.replace(' ', '_');
        } else if (name.contains("ö")){
            pictureFilename = name.replace("ö", "o");
        } else {
            pictureFilename = name;
        }

        return pictureFilename;
    }

    /**
     * Returns the picture filename of the constellation
     * @return the picture filename of the constellation
     */
    public String getPictureFilename() { return pictureFilename; }

    /**
     * Sets the picture filename of the constellation
     * @param pictureFilename, the picture filename of the constellation as a string
     */
    public void setPictureFilename(String pictureFilename) { this.pictureFilename = pictureFilename; }

    /**
     * Checks if the constellation is favorited
     * @return true if favorited, false otherwise
     */
    public boolean isStarFavorited() { return isFavorited; }

    /**
     * Sets favorited to true
     */
    public void setFavorited() { isFavorited = true; }

    /**
     * Returns the arraylist of stars of the constellation
     * @return the arraylist of star objects
     */
    public ArrayList<Star> getStars() { return stars; }

    /**
     * Sets the stars of the constellation given the stars as a parameter as an arraylist of stars
     * @param stars
     */
    public void setStars(ArrayList<Star> stars) { this.stars = stars; }

    /**
     * Adds a star to the constellation
     * @param s, the star to be added
     */
    public void addStar(Star s){ stars.add(s); }

}
