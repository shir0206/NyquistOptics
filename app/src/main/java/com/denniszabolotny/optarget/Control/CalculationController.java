package com.denniszabolotny.optarget.Control;

import com.denniszabolotny.optarget.Model.ConstantsKt;
import com.denniszabolotny.optarget.Model.DB;
import com.denniszabolotny.optarget.Model.TargetDRIType;
import com.denniszabolotny.optarget.Model.TargetSize;
import com.denniszabolotny.optarget.Model.TargetType;

import java.util.HashMap;

public class CalculationController {
    private DB DbRefrence = DB.INSTANCE;

    public CalculationController() {

    }


    //=================================== Calculate Dimension ===================================

    public double calcDimension(double targetSize,
                                 double focalLength,
                                 double sensorPitch,
                                 double targetRange) {

        double dimension;

        double ifov = calcIfov(sensorPitch, focalLength);

        dimension = (targetSize) / (ifov * targetRange);

        return dimension;
    }


    //=================================== Calculate Target Size ===================================


    public double calcTargetSize(double dimension,
                                  double focalLength,
                                  double sensorPitch,
                                  double targetRange) {

        double targetSize;

        double ifov = calcIfov(sensorPitch, focalLength);

        targetSize = dimension * ifov * targetRange;

        return targetSize;
    }

    //=================================== Calculate Focal Length ===================================


    public double calcFocalLengthViaTarget(double dimension,
                                            double targetSize,
                                            double sensorPitch,
                                            double targetRange) {

        return( ((sensorPitch/ConstantsKt.OneMillion) * dimension * targetRange) / targetSize)*ConstantsKt.OneThousand;
    }


    public double calcFocalLengthFOV(double dimensionW,
                                     double dimensionH,
                                     double hfov,
                                     double sensorPitch) {

        return (sensorPitch * Math.max(dimensionW, dimensionH)) /
                (ConstantsKt.TwoThousand * Math.atan((hfov * Math.PI) / ConstantsKt.Ninety));
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
    public double calcHfov(double sensorPitch, double dimensionW, double focalLength) {

        return Math.atan((sensorPitch * dimensionW) /
                (ConstantsKt.TwoThousand * focalLength)) * ConstantsKt.Ninety / Math.PI;
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


    public HashMap<TargetDRIType, Double> calculateDRI(double sensorPitch, double focalLength) {

        HashMap<TargetDRIType, Double> result = new HashMap<TargetDRIType, Double>();
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

        result.put(TargetDRIType.NatoDet, natoDet);
        result.put(TargetDRIType.NatoIdent, natoRec);
        result.put(TargetDRIType.NatoRec, natoIdent);

        result.put(TargetDRIType.HumanDet, humnaDet);
        result.put(TargetDRIType.HumanRec, humnaRec);
        result.put(TargetDRIType.HumanIdent, humnaIdent);

        result.put(TargetDRIType.ObjectDet, objDet);
        result.put(TargetDRIType.ObjectIdent, objRec);

        return result;
    }


}
