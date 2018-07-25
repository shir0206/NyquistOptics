package com.example.shir.nyquistoptics;

public class LinePair {


    private static double lpDetObj = 1.2;
    private static double lpDet = 2;
    private static double lpRec = 6;
    private static double lpIdent = 10;


    public static double getLpDetObj() {
        return lpDetObj;
    }

    public static void setLpDetObj(double lpDetObj) {
        LinePair.lpDetObj = lpDetObj;
    }

    public static double getLpDet() {
        return lpDet;
    }

    public static void setLpDet(double lpDet) {
        LinePair.lpDet = lpDet;
    }

    public static double getLpRec() {
        return lpRec;
    }

    public static void setLpRec(double lpRec) {
        LinePair.lpRec = lpRec;
    }

    public static double getLpIdent() {
        return lpIdent;
    }

    public static void setLpIdent(double lpIdent) {
        LinePair.lpIdent = lpIdent;
    }


}
