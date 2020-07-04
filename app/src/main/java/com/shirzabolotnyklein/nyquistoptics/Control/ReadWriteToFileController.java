package com.shirzabolotnyklein.nyquistoptics.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.shirzabolotnyklein.nyquistoptics.Model.DB;
import com.shirzabolotnyklein.nyquistoptics.Model.LinePair;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetType;

import java.util.ArrayList;
import java.util.List;

public class ReadWriteToFileController {

    private DB DbRefrence;
    private Context cont;
    SharedPreferences targetSizeDefaultSettings; // Save all target sizes default settings
    SharedPreferences linePairDefaultSettings; // Save all line pairs default settings

    public ReadWriteToFileController(Context context){

        this.cont=context;

    }

    public void initReadDataFromFile(){

        getDefaultSettings();

        SetDefaultSettings();

    }


    private void getDefaultSettings() {
        initLinePairDefaultSettings();
        initTargetSizeDefaultSettings();
    }
    public void SetDefaultSettings() {
        SetLinePairDefaultSettingsToClass();
        SetTargetSizeDefaultSettingsToObjects();
    }


    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private void initLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = cont.getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        // Check if SharedPreferences file is empty (linePairDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (linePairDefaultSettings.getString("defaultSettings_lpDet", "").isEmpty()) {

            // Get the editor to edit the file
            SharedPreferences.Editor editor = linePairDefaultSettings.edit();

            // Put default settings
            editor.putString("defaultSettings_lpDet", "2");
            editor.putString("defaultSettings_lpRec", "6");
            editor.putString("defaultSettings_lpIdent", "10");
            editor.putString("defaultSettings_lpDetObj", "1.2");

            // Save the changes
            editor.apply();

        }
    }

    /**
     * Create SharedPreferences file for Target Size settings, and set default settings
     */
    private void initTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        targetSizeDefaultSettings = cont.getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        // Check if SharedPreferences file is empty (targetSizeDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", "").isEmpty()) {

            // Get the editor to edit the file
            SharedPreferences.Editor editor = targetSizeDefaultSettings.edit();

            // Put default settings
            editor.putString("defaultSettings_natoTargetW", "2.3");
            editor.putString("defaultSettings_natoTargetH", "2.3");

            editor.putString("defaultSettings_humanTargetW", "0.5");
            editor.putString("defaultSettings_humanTargetH", "1.7");

            editor.putString("defaultSettings_objTargetW", "0.5");
            editor.putString("defaultSettings_objTargetH", "0.5");

            //Save the changes
            editor.apply();

        }
    }

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    private void SetLinePairDefaultSettingsToClass() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = cont.getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        // Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDet", ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpRec", ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpIdent", ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDetObj", ""));
        // Set the parameters in LinePair classes according to the user input

        DbRefrence.initLinePair(lpDet,lpRec,lpIdent,lpDetObj);
    }

    /**
     * Set default settings derived from SharedPreferences file to the Target Size Holder (Singleton) class
     */
    private void SetTargetSizeDefaultSettingsToObjects() {

        // Get the values from targetSizeDefaultSettings Shared Preferences and convert from String to double
        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetW", ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetH", ""));

        DbRefrence.addTargetSize(new TargetSize(natoTargetW, natoTargetH),TargetType.NATO );
        DbRefrence.addTargetSize( new TargetSize(humanTargetW, humanTargetH),TargetType.HUMAN);
        DbRefrence.addTargetSize( new TargetSize(objTargetW, objTargetH),TargetType.OBJECT);


    }


    /**
     * Set the user target size settings to settings in SharedPreferences file
     */
    public void saveTargetSizeSettings(View view) {

//        // Get the file named "targetSizeDefaultSettings", private
//        SharedPreferences targetSizeDefaultSettings = cont.getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);
//
//        // Get the editor to edit the file
//        SharedPreferences.Editor editor = targetSizeDefaultSettings.edit();
//        editor.putString("defaultSettings_natoTargetW", et_natoTargetW.getText().toString());
//        editor.putString("defaultSettings_natoTargetH", et_natoTargetH.getText().toString());
//
//        editor.putString("defaultSettings_humanTargetW", et_humanTargetW.getText().toString());
//        editor.putString("defaultSettings_humanTargetH", et_humanTargetH.getText().toString());
//
//        editor.putString("defaultSettings_objTargetW", et_objTargetW.getText().toString());
//        editor.putString("defaultSettings_objTargetH", et_objTargetH.getText().toString());
//
//        // Save the changes
//        editor.apply();

    }


    /**
     * Set the user line pair settings to settings in SharedPreferences file
     */
    public void saveLinePairSettings(View view) {

        // Get the file named "linePairDefaultSettings", private
//        SharedPreferences linePairDefaultSettings = cont.getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);
//
//        // Get the editor to edit the file
//        SharedPreferences.Editor editor = linePairDefaultSettings.edit();
//
//        // Put key+value to the file
//        editor.putString("defaultSettings_lpDet", et_lpDet.getText().toString());
//        editor.putString("defaultSettings_lpRec", et_lpRec.getText().toString());
//        editor.putString("defaultSettings_lpIdent", et_lpIdent.getText().toString());
//        editor.putString("defaultSettings_lpDetObj", et_lpDetObj.getText().toString());
//
//        // Save the changes
//        editor.apply();

    }

    /**
     * Set the user settings to settings in SharedPreferences files
     */
    public void saveSettings(View view) {
        saveLinePairSettings(view);
        saveTargetSizeSettings(view);

    }
    public void SetSettingsToClass(View view){
        SetLinePairSettingsToClass(view);
        SetTargetSizeSettingsToClass(view);
    }
    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    public void SetLinePairSettingsToClass(View view) { //Print the saved settings

        // Get the file named "linePairDefaultSettings", private
        SharedPreferences linePairDefaultSettings = cont.getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        // Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDet", ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpRec", ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpIdent", ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDetObj", ""));

        // Set the parameters in LinePair classes according to the user input

        DbRefrence.setLinePair(new LinePair(lpDet,lpRec,lpIdent,lpDetObj));


    }

    public void SetTargetSizeSettingsToClass(View view) {

        // Get the file named "targetSizeDefaultSettings", private
        SharedPreferences targetSizeDefaultSettings = cont.getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        //Convert the user input from String to double

        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetW", ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetH", ""));


        // Set the parameters in TargetSizeS classes according to the user input

        TargetSize natoTargetSize = DbRefrence.getTargetSizes().get(TargetType.NATO);
        TargetSize humanTargetSize =  DbRefrence.getTargetSizes().get(TargetType.HUMAN);
        TargetSize objTargetSize = DbRefrence.getTargetSizes().get(TargetType.OBJECT);

        natoTargetSize.setWidth(natoTargetW);
        natoTargetSize.setHeight(natoTargetH);

        humanTargetSize.setWidth(humanTargetW);
        humanTargetSize.setHeight(humanTargetH);

        objTargetSize.setWidth(objTargetW);
        objTargetSize.setHeight(objTargetH);


    }
    public void displaySettings() {
        displayLinePairSettings();
        displayTargetSizeSettings();
    }

    /**
     * Display the line pair in the activity.
     * The line pair default settings derived from SharedPreferences file.
     */
    public void displayLinePairSettings() {

//        // Get the file named "linePairDefaultSettings", private
//        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);
//
//        String lpDet = linePairDefaultSettings.getString("defaultSettings_lpDet", "");
//        String lpRec = linePairDefaultSettings.getString("defaultSettings_lpRec", "");
//        String lpIdent = linePairDefaultSettings.getString("defaultSettings_lpIdent", "");
//        String lpDetObj = linePairDefaultSettings.getString("defaultSettings_lpDetObj", "");
//
//        et_lpDet.setText(lpDet);
//        et_lpRec.setText(lpRec);
//        et_lpIdent.setText(lpIdent);
//        et_lpDetObj.setText(lpDetObj);
    }

public ArrayList<String> getTargetSizes(){
        ArrayList<String> res=new ArrayList<>();
    //    // Convert the target size from double to String
//    String natoSize = "(" + Double.toString(natoTargetSize.getWidth()) + "x" + Double.toString(natoTargetSize.getHeight()) + ")";
//    String humanSize = "(" + Double.toString(humanTargetSize.getWidth()) + "x" + Double.toString(humanTargetSize.getHeight()) + ")";
//    String objSize = "(" + Double.toString(objTargetSize.getWidth()) + "x" + Double.toString(objTargetSize.getHeight()) + ")";
//
//    // Set the target size to TextView
//    tv_natoSize.setText(natoSize);
//    tv_humanSize.setText(humanSize);
//    tv_objSize.setText(objSize);
    return res;
    }



    /**
     * Display for each target type its size in the activity.
     * The target size default settings derived from SharedPreferences file.
     */
    public void displayTargetSizeSettings() {

//        // Get the file named "targetSizeDefaultSettings", private
//        SharedPreferences targetSizeDefaultSettings = cont.getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);
//
//        // Convert the user input to String
//        String natoTargetW = targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", "");
//        String natoTargetH = targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", "");
//
//        String humanTargetW = targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", "");
//        String humanTargetH = targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", "");
//
//        String objTargetW = targetSizeDefaultSettings.getString("defaultSettings_objTargetW", "");
//        String objTargetH = targetSizeDefaultSettings.getString("defaultSettings_objTargetH", "");
//
//        // Set the user input to edit text
//        et_natoTargetW.setText(natoTargetW);
//        et_natoTargetH.setText(natoTargetH);
//
//        et_humanTargetW.setText(humanTargetW);
//        et_humanTargetH.setText(humanTargetH);
//
//        et_objTargetW.setText(objTargetW);
//        et_objTargetH.setText(objTargetH);

    }
}
