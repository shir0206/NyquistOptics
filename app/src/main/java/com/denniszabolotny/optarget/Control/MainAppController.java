package com.denniszabolotny.optarget.Control;

import android.content.Context;


import com.denniszabolotny.optarget.Model.FovType;
import com.denniszabolotny.optarget.Model.TargetDRIType;
import com.denniszabolotny.optarget.Utils.Util;

import java.util.HashMap;

public class MainAppController {
    private Context cont;

    CalculationController calcControll;

    public MainAppController(Context context) {
        super();
        this.cont = context;
    }

    public MainAppController(){
        calcControll=new CalculationController();
    }

    public void calculateIFOV() {
        //use private methods to calculate the ifov
    }
    public void getDetecotrValues(){

    }
    /**
     * Calculates the dimensions of the sensor according to the Height and Width that was sent
     * @param targetSize
     * @param focalLength
     * @param sensorPitch
     * @param targetRange
     * @return
     */
    public double calcDimension( double targetSize,
                                 double focalLength,
                                 double sensorPitch,
                                 double targetRange){
        return  this.calcControll.calcDimension(targetSize,
                focalLength,
                sensorPitch,
                targetRange);
    }
    public double calcualteTargetSize(double dimension,
                                      double focalLength,
                                      double sensorPitch,
                                      double targetRange){
        return this.calcControll.calcTargetSize( dimension,
         focalLength,
         sensorPitch,
         targetRange);
    }
    /**
     * Calcualte focal length via HFOV
     * @param dimensionW
     * @param dimensionH
     * @param hfov
     * @param sensorPitch
     */
    public double calculateFocalLengthViaHfov(double dimensionW,
                                     double dimensionH,
                                     double hfov,
                                     double sensorPitch) {

       return this.calcControll.calcFocalLengthFOV(dimensionW,dimensionH,hfov,sensorPitch);
    }

    /**
     * Calculate focal length via TargetSizes
     * @param dimensionH
     * @param targetSizeH
     * @param sensorPitch
     * @param targetRange
     * @return
     */
    public double calculateFocalLengthViaTarget(double dimensionH,
                                                double targetSizeH,
                                                double sensorPitch,
                                                double targetRange){
        return this.calcControll.calcFocalLengthViaTarget( dimensionH,targetSizeH,sensorPitch,targetRange);
    }



    public HashMap<FovType, String> calcFOV(String SensorPitch, String FocalLength, String SensorSizeH, String SensorSizeW) {
        double sensorPitch = Double.parseDouble(SensorPitch);
        double focalLength = Double.parseDouble(FocalLength);
        double sensorSizeW = Double.parseDouble(SensorSizeW);
        double sensorSizeH = Double.parseDouble(SensorSizeH);

        //Calculate FOV
        HashMap<FovType, String> results = new HashMap<FovType, String>();
        double ifov = this.calcControll.calcIfov(sensorPitch, focalLength);
        double hfov = this.calcControll.calcHfov(sensorPitch, sensorSizeW, focalLength);
        double vfov = this.calcControll.calcVfov(sensorSizeH, sensorSizeW, hfov);

        // Convert the output from double to String and formatting the decimal digits
        String iFOV = Util.formatDouble(ifov,6);
        String hFov = Util.formatDouble(hfov,1);
        String vFov = Util.formatDouble(vfov,1);

        results.put(FovType.IFOV, iFOV);
        results.put(FovType.HFOV, hFov);
        results.put(FovType.VFOV, vFov);

        return results;
    }

    public HashMap<TargetDRIType, Double> calculateDRI(String SensorPitch, String FocalLength) {

        HashMap<TargetDRIType, Double> result;
        double sensorPitch = Double.parseDouble(SensorPitch);
        double focalLength = Double.parseDouble(FocalLength);

        return result = this.calcControll.calculateDRI(sensorPitch, focalLength);
    }


}
