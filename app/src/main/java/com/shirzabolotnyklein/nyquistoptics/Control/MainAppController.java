package com.shirzabolotnyklein.nyquistoptics.Control;

import android.content.Context;

import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;

public class MainAppController {
    private Context cont;
    public MainAppController(Context context){
    CalculationController cc=new CalculationController();
    this.cont=context;
    }
    
    public void calculateIFOV(){
        //use private methods to calculate the ifov
    }


    public void calculateFocalLength(){
        //use private methods to calculate focal length
    }

    public void calculateWidthHeightTarget(){
        //use private methods to calcaulte the height and width of the target
    }

    public void calculateFOVDRI(){

        Properties.setSensorPitch(sensorPitch);
        Properties.setFocalLength(focalLength);
        SensorSize.setWidth(sensorSizeW);
        SensorSize.setHeight(sensorSizeH);

        // PART 1 - Calculate FOV

        // Calculate the output
        Fov.setFov();

        // Convert the output from double to String and formatting the decimal digits
        String ifov = formatSixDig.format(Fov.getIfov());
        String hfov = formatOneDig.format(Fov.getHfov());
        String vfov = formatOneDig.format(Fov.getVfov());

        // Set the String output to TextView
        tv_resIfov.setText(ifov);
        tv_resHfov.setText(hfov);
        tv_resVfov.setText(vfov);



}
public void calculateTargetsDRI(){
    // PART 2 - Calculate Targets

    // Create local TargetSize instances, that contains data derived from targetSizeHolder class (in hashmap targetSizeCollection)
    TargetSize natoTargetSize = targetSizeHolder.getTargetSize("natoTargetSize");
    TargetSize humanTargetSize = targetSizeHolder.getTargetSize("humanTargetSize");
    TargetSize objTargetSize = targetSizeHolder.getTargetSize("objTargetSize");

    // Initialize Target class instances
    Target natoTarget = new Target();
    Target humanTarget = new Target();
    Target objTarget = new Target();

    // Calculate the output
    natoTarget.setTarget(natoTargetSize);
    humanTarget.setTarget(humanTargetSize);
    objTarget.setTarget(objTargetSize);

    // Convert the output from double to String
    String natoTargetDet = formatOneDig.format(natoTarget.getDetection());
    String natoTargetRec = formatOneDig.format(natoTarget.getRecognition());
    String natoTargetIdent = formatOneDig.format(natoTarget.getIdentify());

    String humanTargetDet = formatOneDig.format(humanTarget.getDetection());
    String humanTargetRec = formatOneDig.format(humanTarget.getRecognition());
    String humanTargetIdent = formatOneDig.format(humanTarget.getIdentify());

    String objTargetDet = formatOneDig.format(objTarget.getDetection());
    String objTargetRec = formatOneDig.format(objTarget.getRecognition());

    // Set the String output to TextView
    tv_resNatoDet.setText(natoTargetDet);
    tv_resNatoRec.setText(natoTargetRec);
    tv_resNatoIdent.setText(natoTargetIdent);

    tv_resHumanDet.setText(humanTargetDet);
    tv_resHumanRec.setText(humanTargetRec);
    tv_resHumanIdent.setText(humanTargetIdent);

    tv_resObjDet.setText(objTargetDet);
    tv_resObjRec.setText(objTargetRec);

    // Convert the target size from double to String
    String natoSize = "(" + Double.toString(natoTargetSize.getWidth()) + "x" + Double.toString(natoTargetSize.getHeight()) + ")";
    String humanSize = "(" + Double.toString(humanTargetSize.getWidth()) + "x" + Double.toString(humanTargetSize.getHeight()) + ")";
    String objSize = "(" + Double.toString(objTargetSize.getWidth()) + "x" + Double.toString(objTargetSize.getHeight()) + ")";

    // Set the target size to TextView
    tv_natoSize.setText(natoSize);
    tv_humanSize.setText(humanSize);
    tv_objSize.setText(objSize);
}





}
