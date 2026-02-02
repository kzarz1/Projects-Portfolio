package edu.utsa.cs3443.arcsecond_skylibrary.model;
import java.io.Serializable;

/**
 *
 * Class that represents and initializes a Star object
 *
 * Net Scrap Development Corporation, LLC.
 * @author Renae Manalastas
 *
 * UTSA CS 3443 001
 * Project
 * Spring 2025
 *
 */
public class Star implements Serializable {
    private String name;
    private String rightAscension;
    private String declination;
    private double apparentMagnitude;
    private String direction;
    private String constellation;

    /**
     * Constructor that initializes a new Star object
     * @param name, name of the star
     * @param rightAscension, right ascension of the star
     * @param declination, declination of the star
     * @param apparentMagnitude, apparent magnitude of the star
     * @param direction, direction of the star
     * @param constellation, the constellation that the star belongs to
     */
    public Star (String name, String rightAscension, String declination, double apparentMagnitude, String direction, String constellation) {
        this.name = name;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.apparentMagnitude = apparentMagnitude;
        this.direction = direction;
        this.constellation = constellation;
    }

    /**
     * Returns the name of the star
     * @return the name of the star
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the star given a name as a parameter
     * @param name, the name of the star
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the right ascension of the star
     * @return the right ascension of the star
     */
    public String getRightAscension() {
        return rightAscension;
    }

    /**
     * Sets the right ascension of the star, given a right ascension as a parameter
     * @param rightAscension, the right ascension of the star
     */
    public void setRightAscension(String rightAscension) {
        this.rightAscension = rightAscension;
    }

    /**
     * Returns the declination of the star
     * @return the declination of the star
     */
    public String getDeclination() {
        return declination;
    }

    /**
     * Sets the declination of the star, given the declination as a parameter
     * @param declination, the declination of the star
     */
    public void setDeclination(String declination) {
        this.declination = declination;
    }

    /**
     * Returns the apparent magnitude of the star
     * @return the apparent magnitude of the star
     */
    public double getApparentMagnitude() {
        return apparentMagnitude;
    }

    /**
     * Sets the apparent magnitude of the star, given the apparent magnitude as a parameter
     * @param apparentMagnitude, the apparent magnitude of the star
     */
    public void setApparentMagnitude(double apparentMagnitude) { this.apparentMagnitude = apparentMagnitude;}

    /**
     * Returns the direction of the star
     * @return the direction of the star
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the star, given the direction as a parameter
     * @param direction, the direction of the star
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Returns the constellation that the star belongs to
     * @return, the constellation that the star belongs to
     */
    public String getConstellation() {
        return constellation;
    }

    /**
     * Sets the constellation that the star belongs to, given a parameter
     * @param constellation, the constellation that the star belogns to, to be set
     */
    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
