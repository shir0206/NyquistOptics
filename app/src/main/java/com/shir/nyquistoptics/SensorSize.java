package com.shir.nyquistoptics;

/**
 * This class contains the user input sensor size (static class).
 */
public class SensorSize {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The user input value of sensor size - width.
     */
    private static double width;

    /**
     * The user input value of sensor size - height.
     */
    private static double height;


    //------------------------------------- Getters & Setters -------------------------------------

    /**
     * Setter for width.
     * @return = The user input value of sensor size - width.
     */
    public static double getWidth() {
        return width;
    }

    public static void setWidth(double width) {
        SensorSize.width = width;
    }

    public static double getHeight() {
        return height;
    }

    public static void setHeight(double height) {
        SensorSize.height = height;
    }


}
