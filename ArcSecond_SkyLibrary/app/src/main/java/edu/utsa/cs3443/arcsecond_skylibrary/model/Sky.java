package edu.utsa.cs3443.arcsecond_skylibrary.model;

import java.io.Serializable;
import java.util.*;

/**
 *
 * Class that represents and initializes a Sky object
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 *
 */
public class Sky implements Serializable{
    private ArrayList<Constellation> constellations;
    public Sky(ArrayList<Constellation> constellations) {
        this.constellations = constellations;
    }

    /**
     * Constructor that initializes a Sky object with an empty constellations arraylist
     */
    public Sky() {
        constellations = new ArrayList<>();
    }

    /**
     * Returns an arraylist of Constellation objects
     * @return
     */
    public ArrayList<Constellation> getConstellations() {
        return constellations;
    }

    /**
     * Sets the sky's constellations arraylist, given the arraylist of constellation objects as a parameter
     * @param constellations, the arraylist of constellation objects
     */
    public void setConstellations(ArrayList<Constellation> constellations) { this.constellations = constellations; }

    /**
     * Adds a constellation object to the arraylist of constellations in the sky
     * @param c, the constellation object to be added
     */
    public void addConstellation(Constellation c){ constellations.add(c); }

    /**
     * Adds a star to a constellation in the arraylist
     * @param c, the target constellation object
     * @param s, the star to be added
     */
    public void addStarToConstellation(Constellation c, Star s){
        ArrayList<Star> constStars = c.getStars();
        constStars.add(s);
    }

    /**
     * Returns a specified constellation given its name as a parameter
     * @param name, the name of the constellation as a string
     * @return the Constellation object
     */
    public Constellation getConstellation(String name) {
        for (Constellation c : constellations) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns the size of the constellation arraylist
     * @return the size of the constellation arraylist
     */
    public int getSizeOfConstellationList() { return constellations.size(); }
}