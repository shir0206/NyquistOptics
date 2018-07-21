package com.example.shir.nyquistoptics;

public class Properties {

    private	static double sensorPitch;
    private	static double focalLength;
    //private	double	fNumber; //never used


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
