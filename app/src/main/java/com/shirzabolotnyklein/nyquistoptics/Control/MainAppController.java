package com.shirzabolotnyklein.nyquistoptics.Control;

import android.content.Context;

import com.shirzabolotnyklein.nyquistoptics.Model.FovType;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetDRIType;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MainAppController {
    private Context cont;

    // Initialize decimal format for outputs
    DecimalFormat formatOneDig = new DecimalFormat("0.0");
    DecimalFormat formatSixDig = new DecimalFormat("0.000000");

    CalculationController calcControll;

    public MainAppController(Context context) {
        calcControll = new CalculationController();
        this.cont = context;
    }

    public void calculateIFOV() {
        //use private methods to calculate the ifov
    }


    public void calculateFocalLength() {
        //use private methods to calculate focal length
    }

    public void calculateWidthHeightTarget() {
        //use private methods to calcaulte the height and width of the target
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
        String iFOV = formatSixDig.format(ifov);
        String hFov = formatOneDig.format(hfov);
        String vFov = formatOneDig.format(vfov);

        results.put(FovType.IFOV, iFOV);
        results.put(FovType.HFOV, hFov);
        results.put(FovType.VFOV, vFov);

        return results;
    }

    public HashMap<TargetDRIType, String> calculateDRI(String SensorPitch, String FocalLength) {

        HashMap<TargetDRIType, String> result;
        double sensorPitch = Double.parseDouble(SensorPitch);
        double focalLength = Double.parseDouble(FocalLength);

        return result = this.calcControll.calculateDRI(sensorPitch, focalLength);
    }


}
