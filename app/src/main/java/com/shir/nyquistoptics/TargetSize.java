package com.shir.nyquistoptics;

/**
 * This class contains the target size (height & width) for a certain target type.
 */
public class TargetSize  {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The value of target size - width.
     */
    private double width;

    /**
     * The value of target size - height.
     */
    private double height;

    //------------------------------------- Constructor -------------------------------------

    public TargetSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    //------------------------------------- Getters & Setters -------------------------------------

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
