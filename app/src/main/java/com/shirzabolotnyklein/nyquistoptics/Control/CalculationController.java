package com.shirzabolotnyklein.nyquistoptics.Control;

import com.shirzabolotnyklein.nyquistoptics.Model.ConstantsKt;
import com.shirzabolotnyklein.nyquistoptics.Model.DB;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;

public class CalculationController {
    private DB DbRefrence;
    public CalculationController(){

    }

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

        target = (focalLength / (line * sensorPitch / ConstantsKt.OneMillion)) * (Math.sqrt(targetSize.getWidth() * targetSize.getHeight()) / ConstantsKt.OneThousand);

        return target;
    }

    /**
     * Method that returns the calculated value of target detection, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target detection.
     */
    private void calcDetection(TargetSize targetSize) {

//        double detection;
//
//        // Calculate detection for object target
//        if (targetSize.getHeight() <= ConstantsKt.MinHeight) {
//            detection = calcTarget(DbRefrence.getSensorPitch().getSensorPitch(), DbRefrence.getFocalLength().getFocalLength(), targetSize, DbRefrence.getLinePair().getLpDetObj());
//        }
//
//        // Calculate detection for nato/human targets
//        else {
//            detection = calcTarget(DbRefrence.getSensorPitch().getSensorPitch(), DbRefrence.getFocalLength().getFocalLength(), targetSize, DbRefrence.getLinePair().getLpDet());
//        }
//
//        DbRefrence.getTargetDRI().setDetection(detection);
    }

    /**
     * Method that returns the calculated value of target recognition, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target recognition.
     */
    private void calcRecognition(TargetSize targetSize) {

//        double recognition;
//
//        recognition = calcTarget(DbRefrence.getSensorPitch().getSensorPitch(), DbRefrence.getFocalLength().getFocalLength(), targetSize,DbRefrence.getLinePair().getLpRec());
//
//        DbRefrence.getTargetDRI().setRecognition(recognition);

    }

    /**
     * Method that returns the calculated value of target identify, by using the helper method calcTarget.
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target identify.
     */
    private void calcIdentify(TargetSize targetSize) {

//        double identify;
//
//        // Calculate identify for object target
//        if (targetSize.getHeight() <= ConstantsKt.MinHeight) {
//            identify = 0;
//        }
//
//        // Calculate identify for nato/human targets
//        else {
//            identify = calcTarget(DbRefrence.getSensorPitch().getSensorPitch(),  DbRefrence.getFocalLength().getFocalLength(), targetSize, DbRefrence.getLinePair().getLpIdent());
//        }
//
//
//        DbRefrence.getTargetDRI().setIdentification(identify);
    }


    /**
     * The formula that calculates the value of instantaneous field of view (IFOV).
     * @return = The value of instantaneous field of view (IFOV).
     */
    public double calcIfov(double sensorPitch,double focalLength) {
        double ifov;

        ifov = sensorPitch / (focalLength * ConstantsKt.OneThousand);

        return ifov;
    }

    /**
     * The formula that calculates the value of horizontal field of view (HFOV).
     * @return = The value of horizontal field of view (HFOV).
     */
    public double calcHfov(double sensorPitch,double DimInPixelWidth,double FocalLength) {
        double hfov;

        hfov =  Math.atan((sensorPitch * DimInPixelWidth) / (ConstantsKt.OneThousand * FocalLength)) * 90 / Math.PI;

        return hfov;
    }

    /**
     * The formula that calculates the value of vertical field of view (VFOV).
     * @return = The value of vertical field of view (VFOV).
     */
    public  double calcVfov(double DimInPixelHeight,double DimInPixelWidth,double hfov) {
        double vfov;
        vfov = hfov * (DimInPixelHeight / DimInPixelWidth);
        return vfov;
    }
}
