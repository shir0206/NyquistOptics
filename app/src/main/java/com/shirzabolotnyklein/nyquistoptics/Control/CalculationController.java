package com.shirzabolotnyklein.nyquistoptics.Control;

import com.shirzabolotnyklein.nyquistoptics.Model.ConstantsKt;
import com.shirzabolotnyklein.nyquistoptics.Model.DB;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetDRIType;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetType;

import java.text.DecimalFormat;
import java.util.HashMap;

public class CalculationController {
    private DB DbRefrence;
    DecimalFormat formatOneDig = new DecimalFormat("0.0"); // Initialize decimal format for outputs
    DecimalFormat formatSixDig = new DecimalFormat("0.000000"); // Initialize decimal format for outputs

    public CalculationController() {

    }


    //=================================== Calculate Dimension ===================================


    private double calcDimensionWidth(double targetSizeW,
                                      double focalLength,
                                      double sensorPitch,
                                      double targetRange) {

        double dimensionWidth = calcDimension(targetSizeW, focalLength, sensorPitch, targetRange);

        return dimensionWidth;
    }


    private double calcDimensionHeight(double targetSizeH,
                                       double focalLength,
                                       double sensorPitch,
                                       double targetRange) {

        double dimensionHeight = calcDimension(targetSizeH, focalLength, sensorPitch, targetRange);

        return dimensionHeight;
    }


    private double calcDimension(double targetSize,
                                 double focalLength,
                                 double sensorPitch,
                                 double targetRange) {

        double dimension;

        double ifov = calcIfov(sensorPitch, focalLength);

        dimension = (targetSize) / (ifov * targetRange);

        return dimension;
    }


    //=================================== Calculate Target Size ===================================


    private double calcTargetSizeWidth(double dimensionW,
                                       double focalLength,
                                       double sensorPitch,
                                       double targetRange) {

        double targetSizeWidth = calcTargetSize(dimensionW, focalLength, sensorPitch, targetRange);

        return targetSizeWidth;
    }


    private double calcTargetSizeHeight(double dimensionH,
                                        double focalLength,
                                        double sensorPitch,
                                        double targetRange) {

        double targetSizeHeight = calcTargetSize(dimensionH, focalLength, sensorPitch, targetRange);

        return targetSizeHeight;
    }


    private double calcTargetSize(double dimension,
                                  double focalLength,
                                  double sensorPitch,
                                  double targetRange) {

        double targetSize;

        double ifov = calcIfov(sensorPitch, focalLength);

        targetSize = dimension * ifov * targetRange;

        return targetSize;
    }

    //=================================== Calculate Focal Length ===================================


    private double calcFocalLengthViaTarget(double dimension,
                                            double targetSize,
                                            double sensorPitch,
                                            double targetRange) {

        double focalLength = (sensorPitch * dimension * targetRange) / targetSize;
        return focalLength;
    }


    private double calcFocalLengthWidthViaTarget(double dimensionW,
                                                 double targetSizeW,
                                                 double sensorPitch,
                                                 double targetRange) {

        double focalLengthWidth = calcFocalLengthViaTarget(dimensionW, targetSizeW, sensorPitch, targetRange);
        return focalLengthWidth;
    }

    private double calcFocalLengthHeightViaTarget(double dimensionH,
                                                  double targetSizeH,
                                                  double sensorPitch,
                                                  double targetRange) {

        double focalLengthHeight = calcFocalLengthViaTarget(dimensionH, targetSizeH, sensorPitch, targetRange);
        return focalLengthHeight;
    }


    private double calcFocalLengthViaFOV(double dimensionW,
                                         double dimensionH,
                                         double hfov,
                                         double sensorPitch) {

        double focalLength = (sensorPitch * Math.max(dimensionW, dimensionH)) /
                (ConstantsKt.TwoThousand * Math.atan((hfov * Math.PI) / ConstantsKt.Ninety));

        return focalLength;
    }


    //=================================== Calculate FOV ===================================


    /**
     * The formula that calculates the value of instantaneous field of view (IFOV).
     *
     * @return = The value of instantaneous field of view (IFOV).
     */
    public double calcIfov(double sensorPitch, double focalLength) {
        double ifov;

        ifov = sensorPitch / (focalLength * ConstantsKt.OneThousand);

        return ifov;
    }

    /**
     * The formula that calculates the value of horizontal field of view (HFOV).
     *
     * @return = The value of horizontal field of view (HFOV).
     */
    public double calcHfov(double sensorPitch, double dimensionW, double FocalLength) {

        double hfov = Math.atan((sensorPitch * dimensionW) /
                (ConstantsKt.TwoThousand * FocalLength)) * ConstantsKt.Ninety / Math.PI;

        return hfov;
    }

    /**
     * The formula that calculates the value of vertical field of view (VFOV).
     *
     * @return = The value of vertical field of view (VFOV).
     */
    public double calcVfov(double dimensionH, double dimensionW, double hfov) {
        double vfov;
        vfov = hfov * (dimensionH / dimensionW);
        return vfov;
    }


    //=================================== Calculate DRI ===================================


    /**
     * Helper method that calculates the value of target detection/recognition/identify.
     *
     * @param sensorPitch = The user input value of sensor pitch.
     * @param focalLength = The user input value of focal length.
     * @param targetSize  = The value of the target size (height & width).
     * @param line        = The value of the line pair.
     * @return = the calculated value for target detection/recognition/identify (DRI)
     */
    private double calcTarget(double sensorPitch, double focalLength, TargetSize targetSize, double line) {

        double target;

        target = (focalLength / (line * sensorPitch / ConstantsKt.OneMillion)) * (Math.sqrt(targetSize.getWidth() * targetSize.getHeight()) / ConstantsKt.OneThousand);

        return target;
    }

    /**
     * Method that returns the calculated value of target detection, by using the helper method calcTarget.
     *
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target detection.
     */
    private double calcDetection(TargetSize targetSize, double sensorPitch, double focalLength) {

        double detection;

        // Calculate detection for object target
        if (targetSize.getHeight() <= ConstantsKt.MinHeight) {
            detection = calcTarget(sensorPitch, focalLength, targetSize, DbRefrence.getLinePair().getLpDetObj());
        }

        // Calculate detection for nato/human targets
        else {
            detection = calcTarget(sensorPitch, focalLength, targetSize, DbRefrence.getLinePair().getLpDet());
        }

        return detection;
    }

    /**
     * Method that returns the calculated value of target recognition, by using the helper method calcTarget.
     *
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target recognition.
     */
    private double calcRecognition(TargetSize targetSize, double sensorPitch, double focalLength) {

        double recognition;

        recognition = calcTarget(sensorPitch, focalLength, targetSize, DbRefrence.getLinePair().getLpRec());

        return recognition;

    }

    /**
     * Method that returns the calculated value of target identify, by using the helper method calcTarget.
     *
     * @param targetSize = The value of the target size (height & width).
     * @return = The value of target identify.
     */
    private double calcIdentify(TargetSize targetSize, double sensorPitch, double focalLength) {

        double identify;

        // Calculate identify for object target
        if (targetSize.getHeight() <= ConstantsKt.MinHeight) {
            identify = 0;
        }

        // Calculate identify for nato/human targets
        else {
            identify = calcTarget(sensorPitch, focalLength, targetSize, DbRefrence.getLinePair().getLpIdent());
        }


        return identify;
    }


    public HashMap<TargetDRIType, String> calculateDRI(double sensorPitch, double focalLength) {

        HashMap<TargetDRIType, String> result = new HashMap<TargetDRIType, String>();

        TargetSize tsNato = DbRefrence.getTargetSizes().get(TargetType.NATO);
        TargetSize tsHuman = DbRefrence.getTargetSizes().get(TargetType.HUMAN);
        TargetSize tsObj = DbRefrence.getTargetSizes().get(TargetType.OBJECT);

        double natoDet = this.calcDetection(tsNato, sensorPitch, focalLength);
        double natoRec = this.calcRecognition(tsNato, sensorPitch, focalLength);
        double natoIdent = this.calcIdentify(tsNato, sensorPitch, focalLength);

        double humnaDet = this.calcDetection(tsHuman, sensorPitch, focalLength);
        double humnaRec = this.calcRecognition(tsHuman, sensorPitch, focalLength);
        double humnaIdent = this.calcIdentify(tsHuman, sensorPitch, focalLength);

        double objDet = this.calcDetection(tsObj, sensorPitch, focalLength);
        double objRec = this.calcRecognition(tsObj, sensorPitch, focalLength);
        // Convert the output from double to String
        String natoTargetDet = formatOneDig.format(natoDet);
        String natoTargetRec = formatOneDig.format(natoRec);
        String natoTargetIdent = formatOneDig.format(natoIdent);

        String humanTargetDet = formatOneDig.format(humnaDet);
        String humanTargetRec = formatOneDig.format(humnaRec);
        String humanTargetIdent = formatOneDig.format(humnaIdent);

        String objTargetDet = formatOneDig.format(objDet);
        String objTargetRec = formatOneDig.format(objRec);

        result.put(TargetDRIType.NatoDet, natoTargetDet);
        result.put(TargetDRIType.NatoIdent, natoTargetRec);
        result.put(TargetDRIType.NatoRec, natoTargetIdent);

        result.put(TargetDRIType.HumanDet, humanTargetDet);
        result.put(TargetDRIType.HumanRec, humanTargetRec);
        result.put(TargetDRIType.HumanIdent, humanTargetIdent);

        result.put(TargetDRIType.ObjectDet, objTargetDet);
        result.put(TargetDRIType.ObjectIdent, objTargetRec);

        return result;
    }


}
