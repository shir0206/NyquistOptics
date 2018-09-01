package com.example.shir.nyquistoptics;

/**
 * This class contains all the field of view (FOV) parameters and their calculations (static class).
 */
public class Fov {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The calculated value of instantaneous field of view (IFOV).
     */
    private static double ifov;

    /**
     * The calculated value of horizontal field of view (HFOV).
     */
    private static double hfov;

    /**
     * The calculated value of vertical field of view (VFOV).
     */
    private static double vfov;

    //------------------------------------- Formulas -------------------------------------

    /**
     * The formula that calculates the value of instantaneous field of view (IFOV).
     * @return = The value of instantaneous field of view (IFOV).
     */
    private static double calcIfov() {
        double ifov;
        ifov = Properties.getSensorPitch() / (Properties.getFocalLength() * 1000);
        return ifov;
    }

    /**
     * The formula that calculates the value of horizontal field of view (HFOV).
     * @return = The value of horizontal field of view (HFOV).
     */
    private static double calcHfov() {
        double hfov;
        hfov = 2 * Math.atan((Properties.getSensorPitch() * SensorSize.getWidth()) / (2 * 1000 * Properties.getFocalLength())) * 180 / Math.PI;
        return hfov;
    }

    /**
     * The formula that calculates the value of vertical field of view (VFOV).
     * @return = The value of vertical field of view (VFOV).
     */
    private static double calcVfov() {
        double vfov;
        vfov = hfov * (SensorSize.getHeight() / SensorSize.getWidth());
        return vfov;
    }

    //------------------------------------- Getters & Setters -------------------------------------

    public static double getIfov() {
        return ifov;
    }

    public static void setIfov() {
        Fov.ifov = calcIfov();
    }

    public static double getHfov() {
        return hfov;
    }

    public static void setHfov() {
        Fov.hfov = calcHfov();
    }

    public static double getVfov() {
        return vfov;
    }

    public static void setVfov() {
        Fov.vfov = calcVfov();
    }

    /**
     * Setter for the parameters: ifov, hfov, vfov.
     */
    public static void setFov() {
        setIfov();
        setHfov();
        setVfov();
    }

}
