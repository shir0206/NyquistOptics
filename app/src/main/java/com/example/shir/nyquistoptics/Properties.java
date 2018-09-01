package com.example.shir.nyquistoptics;

/**
 * This class contains the user input properties (static class).
 */
public class Properties {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The user input value of sensor pitch.
     */
    private static double sensorPitch;

    /**
     * The user input value of focal length.
     */
    private static double focalLength;

    //------------------------------------- Getters & Setters -------------------------------------

    public static double getSensorPitch() {
        return sensorPitch;
    }

    public static void setSensorPitch(double sensorPitch) {
        Properties.sensorPitch = sensorPitch;
    }

    public static double getFocalLength() {
        return focalLength;
    }

    public static void setFocalLength(double focalLength) {
        Properties.focalLength = focalLength;
    }


}
