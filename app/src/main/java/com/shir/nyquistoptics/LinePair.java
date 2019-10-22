package com.shir.nyquistoptics;

/**
 * This class contains all the line pairs parameters (static class).
 */
public class LinePair {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The line-pair value that is used to calculate detection for nato target & human target.
     */
    private static double lpDet;

    /**
     * The line-pair value that is used to calculate detection for object target.
     */
    private static double lpRec;

    /**
     * The line-pair value that is used to calculate identify for nato, human & object targets.
     */
    private static double lpIdent;

    /**
     * The line-pair value that is used to calculate recognition for nato, human & object targets.
     */
    private static double lpDetObj;

    //------------------------------------- Constructors -------------------------------------

    /**
     * LinePair all parameters constructor
     * @param lpDet = The line-pair value that is used to calculate detection for nato target & human target.
     * @param lpRec = The line-pair value that is used to calculate recognition for nato, human & object targets.
     * @param lpIdent = The line-pair value that is used to calculate identify for nato, human & object targets.
     * @param lpDetObj = The line-pair value that is used to calculate detection for object target.
     */
    public LinePair(double lpDet, double lpRec, double lpIdent, double lpDetObj) {
        LinePair.lpDet = lpDet;
        LinePair.lpRec = lpRec;
        LinePair.lpIdent = lpIdent;
        LinePair.lpDetObj = lpDetObj;
    }

    //------------------------------------- Getters & Setters -------------------------------------

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
