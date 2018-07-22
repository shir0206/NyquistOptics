package com.example.shir.nyquistoptics;

public class Fov {

    private	static double ifov;
    private	static double hfov;
    private	static double vfov;


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


    public static void setFov() {
        setIfov();
        setHfov();
        setVfov();
    }

    private static double calcIfov () {
        double ifov;
        ifov = Properties.getSensorPitch() / (Properties.getFocalLength() * 1000);
        return ifov;
    }

    private static double calcHfov () {
        double hfov;
        hfov = 2 * Math.atan((Properties.getSensorPitch()*SensorSize.getWidth()) / (2 * 1000 * Properties.getFocalLength())) * 180 / Math.PI;
        return hfov;
    }

    private static double calcVfov () {
        double vfov;
        vfov = hfov * 0.75;
        return vfov;
    }









}
