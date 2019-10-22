package com.shir.nyquistoptics;

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


    protected static void calcFocalLength(double hfov) {
         focalLength = (sensorPitch * SensorSize.getWidth()) / (2 * 1000 * Math.tan((Math.PI * hfov) / 360));

    }

    //------------------------------------- Getters & Setters -------------------------------------

    public static double getSensorPitch() {
        return sensorPitch;
    }

    public static void setSensorPitch(double sensorPitch) {
        sensorPitch = sensorPitch;
    }

    public static double getFocalLength() {
        return focalLength;
    }

    public static void setFocalLength(double focalLength) {
        focalLength = focalLength;
    }


}
