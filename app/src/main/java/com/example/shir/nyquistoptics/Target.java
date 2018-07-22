package com.example.shir.nyquistoptics;

public class Target {



    private	double detection;
    private	double recognition;
    private	double identify;


    public Target(){//double detection, double recognition, double identify) {
 //       this.detection = detection;
  //      this.recognition = recognition;
   //     this.identify = identify;
    }

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


    public void setTarget (TargetSize targetSize) {
        setDetection(targetSize);
        setRecognition(targetSize);
        setIdentify(targetSize);
    }


    private double calcTarget (double sensorPitch, double focalLength, TargetSize targetSize, double line) {

        double target;

        target = (focalLength / (line * sensorPitch / 1000000)) * (Math.sqrt(targetSize.getHeight() * targetSize.getWidth()) / 1000);

        return target;


    }


    private double calcDetection (TargetSize targetSize) {


        double detection;

        if (targetSize.getHeight() <= 0.5) {
            detection = calcTarget (Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpDetObj());
        }

        else {
            detection = calcTarget(Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpDet());
        }

        return detection;

    }

    private double calcRecognition (TargetSize	targetSize) {

        double recognition;
        recognition = calcTarget (Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpRec());
        return recognition;

    }

    private double calcIdentify (TargetSize targetSize) {

        double identify;

        if (targetSize.getHeight() <= 0.5) {
            identify = 404;
        }

        else {
            identify = calcTarget (Properties.getSensorPitch(), Properties.getFocalLength(), targetSize, LinePair.getLpIdent());
        }

        return identify;

    }













}
