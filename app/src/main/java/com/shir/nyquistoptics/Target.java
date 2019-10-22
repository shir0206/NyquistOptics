package com.shir.nyquistoptics;

/**
 * This class contains all the detection, recognition & identify (DRI) parameters and their calculations. (static class).
 */
public class Target {

    //------------------------------------- Parameters -------------------------------------

    /**
     * The calculated value of target detection
     */
    private double detection;

    /**
     * The calculated value of target recognition
     */
    private double recognition;

    /**
     * The calculated value of target identify
     */
    private double identify;

    //------------------------------------- Constructors -------------------------------------

    /**
     * Target empty constructor
     */
    public Target() {
    }

    //------------------------------------- Formulas -------------------------------------

    /**
     * Helper method that calculates the value of target detection/recognition/identify.
     * @param sensorPitch = The user input value of sensor pitch.
     * @param focalLength = The user input value of focal length.
     * @param targetSize = The value of the target size (height & width).
     * @param line = The value of the line pair.
     * @return = the calculated value for target detection/recognition/identify (DRI)
     */
    private double calcTarget(double sensorPitch, double focalLength, TargetSize targetSize, double line) {

        double target;

        target = (focalLength / (line * sensorPitch / 1000000)) * (Math.sqrt(targetSize.getHeight() * targetSize.getWidth()) / 1000);

        return target;
    }

    /**
     * Method that returns the calculated value of target detection, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target detection.
     */
    private double calcDetection(TargetSize targetSize) {

        double detection;

        // Calculate detection for object target
        if (targetSize.getHeight() <= 0.5) {
            detection = calcTarget(Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpDetObj());
        }

        // Calculate detection for nato/human targets
        else {
            detection = calcTarget(Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpDet());
        }

        return detection;
    }

    /**
     * Method that returns the calculated value of target recognition, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target recognition.
     */
    private double calcRecognition(TargetSize targetSize) {

        double recognition;

        recognition = calcTarget(Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpRec());

        return recognition;
    }

    /**
     * Method that returns the calculated value of target identify, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target identify.
     */
    private double calcIdentify(TargetSize targetSize) {

        double identify;

        // Calculate identify for object target
        if (targetSize.getHeight() <= 0.5) {
            identify = 0;
        }

        // Calculate identify for nato/human targets
        else {
            identify = calcTarget(Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpIdent());
        }

        return identify;
    }

    //------------------------------------- Getters & Setters -------------------------------------

    public double getDetection() {
        return detection;
    }

    public void setDetection(TargetSize targetSize) {
        this.detection = calcDetection(targetSize);
    }

    public double getRecognition() {
        return recognition;
    }

    public void setRecognition(TargetSize targetSize) {
        this.recognition = calcRecognition(targetSize);
    }

    public double getIdentify() {
        return identify;
    }

    public void setIdentify(TargetSize targetSize) {
        this.identify = calcIdentify(targetSize);
    }

    /**
     * Setter for the parameters: detection, recognition & identify (DRI).
     * @param targetSize = The value of the target size (height & width).
     */
    public void setTarget(TargetSize targetSize) {
        setDetection(targetSize);
        setRecognition(targetSize);
        setIdentify(targetSize);
    }

}
