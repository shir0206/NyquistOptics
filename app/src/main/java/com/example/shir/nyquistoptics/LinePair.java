package com.example.shir.nyquistoptics;

public class LinePair {




    private static double lpDet;
    private static double lpRec;
    private static double lpIdent;
    private static double lpDetObj;

    public LinePair(double lpDet, double lpRec, double lpIdent, double lpDetObj) {
        LinePair.lpDet = lpDet;
        LinePair.lpRec = lpRec;
        LinePair.lpIdent = lpIdent;
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

    public static double getLpDetObj() {
        return lpDetObj;
    }

    public static void setLpDetObj(double lpDetObj) {
        LinePair.lpDetObj = lpDetObj;
    }

}
