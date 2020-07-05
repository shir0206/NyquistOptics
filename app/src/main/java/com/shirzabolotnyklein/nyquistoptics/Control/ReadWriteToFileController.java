package com.shirzabolotnyklein.nyquistoptics.Control;

import android.content.Context;
import android.content.SharedPreferences;

import com.shirzabolotnyklein.nyquistoptics.Model.ConstantsKt;
import com.shirzabolotnyklein.nyquistoptics.Model.DB;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetType;

import java.util.ArrayList;


public class ReadWriteToFileController {

    private DB DbRefrence;
    private Context cont;
    SharedPreferences targetSizeDefaultSettings; // Save all target sizes default settings
    SharedPreferences linePairDefaultSettings; // Save all line pairs default settings

    public ReadWriteToFileController(Context context) {

        this.cont = context;
        initReadDataFromFile();
    }

    //summary
    //This is the base method to call in order to make sure that the most relevant data is avilaible
    //summary
    private void initReadDataFromFile() {

        initDataFromFiles();// make sure the default data is aviliable

        SetDefaultSettings();//save the data and store in the db for storage

    }


    private void initDataFromFiles() {
        initLinePairDefaultSettings();
        initTargetSizeDefaultSettings();
    }


    private void SetDefaultSettings() {
        GetLinePairDefaultSettings();
        GetTargetSizeDefaultSettings();
    }

    //Summary
    //Restores the line pair and target size to default settings
    //Summary
    public void restoreDefaultSettings() {

        linePairDefaultSettings = cont.getSharedPreferences(ConstantsKt.linePairFileName, Context.MODE_PRIVATE);
        targetSizeDefaultSettings = cont.getSharedPreferences(ConstantsKt.targetSizeFileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editorLinePair = linePairDefaultSettings.edit();

        // Put default settings
        editorLinePair.putString(ConstantsKt.lpDetection, String.valueOf(ConstantsKt.lpDet));
        editorLinePair.putString(ConstantsKt.lpRecognition, String.valueOf(ConstantsKt.lpRec));
        editorLinePair.putString(ConstantsKt.lpIdentification, String.valueOf(ConstantsKt.lpIdent));
        editorLinePair.putString(ConstantsKt.lpDetectionObject, String.valueOf(ConstantsKt.lpDetObj));

        DbRefrence.initLinePair(ConstantsKt.lpDet, ConstantsKt.lpRec, ConstantsKt.lpIdent, ConstantsKt.lpDetObj);
        // Save the changes
        editorLinePair.apply();

        SharedPreferences.Editor editorTargetSize = targetSizeDefaultSettings.edit();

        // Put default settings
        editorTargetSize.putString(ConstantsKt.natoTargetSizeW, String.valueOf(ConstantsKt.NatoWidth));
        editorTargetSize.putString(ConstantsKt.natoTargetSizeH, String.valueOf(ConstantsKt.NatoHeight));

        editorTargetSize.putString(ConstantsKt.humanTargetSizeW, String.valueOf(ConstantsKt.HumanWidth));
        editorTargetSize.putString(ConstantsKt.humanTargetSizeH, String.valueOf(ConstantsKt.HumanHeight));

        editorTargetSize.putString(ConstantsKt.objectTargetSizeW, String.valueOf(ConstantsKt.ObjectWidth));
        editorTargetSize.putString(ConstantsKt.objectTargetSizeH, String.valueOf(ConstantsKt.ObjectHeight));

        DbRefrence.eraseTargetSizes();

        DbRefrence.addTargetSize(new TargetSize(ConstantsKt.NatoWidth, ConstantsKt.NatoHeight), TargetType.NATO);
        DbRefrence.addTargetSize(new TargetSize(ConstantsKt.HumanWidth, ConstantsKt.HumanHeight), TargetType.HUMAN);
        DbRefrence.addTargetSize(new TargetSize(ConstantsKt.ObjectWidth, ConstantsKt.ObjectHeight), TargetType.OBJECT);
        //Save the changes
        editorTargetSize.apply();

    }

    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private void initLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = cont.getSharedPreferences(ConstantsKt.linePairFileName, Context.MODE_PRIVATE);

        // Check if SharedPreferences file is empty (linePairDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (linePairDefaultSettings.getString(ConstantsKt.lpDetection, "").isEmpty()) {

            // Get the editor to edit the file
            SharedPreferences.Editor editor = linePairDefaultSettings.edit();

            // Put default settings
            editor.putString(ConstantsKt.lpDetection, String.valueOf(ConstantsKt.lpDet));
            editor.putString(ConstantsKt.lpRecognition, String.valueOf(ConstantsKt.lpRec));
            editor.putString(ConstantsKt.lpIdentification, String.valueOf(ConstantsKt.lpIdent));
            editor.putString(ConstantsKt.lpDetectionObject, String.valueOf(ConstantsKt.lpDetObj));

            // Save the changes
            editor.apply();

        }
    }

    /**
     * Create SharedPreferences file for Target Size settings, and set default settings
     */
    private void initTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        targetSizeDefaultSettings = cont.getSharedPreferences(ConstantsKt.targetSizeFileName, Context.MODE_PRIVATE);

        // Check if SharedPreferences file is empty (targetSizeDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (targetSizeDefaultSettings.getString(ConstantsKt.natoTargetSizeW, "").isEmpty()) {

            // Get the editor to edit the file
            SharedPreferences.Editor editor = targetSizeDefaultSettings.edit();

            // Put default settings
            editor.putString(ConstantsKt.natoTargetSizeW, String.valueOf(ConstantsKt.NatoWidth));
            editor.putString(ConstantsKt.natoTargetSizeH, String.valueOf(ConstantsKt.NatoHeight));

            editor.putString(ConstantsKt.humanTargetSizeW, String.valueOf(ConstantsKt.HumanWidth));
            editor.putString(ConstantsKt.humanTargetSizeH, String.valueOf(ConstantsKt.HumanHeight));

            editor.putString(ConstantsKt.objectTargetSizeW, String.valueOf(ConstantsKt.ObjectWidth));
            editor.putString(ConstantsKt.objectTargetSizeH, String.valueOf(ConstantsKt.ObjectHeight));

            //Save the changes
            editor.apply();

        }
    }

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    private void GetLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = cont.getSharedPreferences(ConstantsKt.linePairFileName, Context.MODE_PRIVATE);

        // Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString(ConstantsKt.lpDetection, ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString(ConstantsKt.lpRecognition, ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString(ConstantsKt.lpIdentification, ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString(ConstantsKt.lpDetectionObject, ""));
        // Set the parameters in LinePair classes according to the user input

        DbRefrence.initLinePair(lpDet, lpRec, lpIdent, lpDetObj);
    }

    /**
     * Set default settings derived from SharedPreferences file
     */
    private void GetTargetSizeDefaultSettings() {

        // Get the values from targetSizeDefaultSettings Shared Preferences and convert from String to double
        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.natoTargetSizeW, ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.natoTargetSizeH, ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.humanTargetSizeW, ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.humanTargetSizeH, ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.objectTargetSizeW, ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString(ConstantsKt.objectTargetSizeH, ""));

        DbRefrence.addTargetSize(new TargetSize(natoTargetW, natoTargetH), TargetType.NATO);
        DbRefrence.addTargetSize(new TargetSize(humanTargetW, humanTargetH), TargetType.HUMAN);
        DbRefrence.addTargetSize(new TargetSize(objTargetW, objTargetH), TargetType.OBJECT);


    }

    public void SaveNewLinePairSettings(String... Values) {
        ArrayList<Double> newLinepairs = new ArrayList<Double>();

        int i = 0;

        for (String val : Values) {
            newLinepairs.add(Double.parseDouble(Values[i]));
            i++;
        }

        linePairDefaultSettings = cont.getSharedPreferences(ConstantsKt.linePairFileName, Context.MODE_PRIVATE);
        // Get the editor to edit the file
        SharedPreferences.Editor editor = linePairDefaultSettings.edit();

        // Put default settings
        editor.putString(ConstantsKt.lpDetection, String.valueOf(newLinepairs.get(0)));
        editor.putString(ConstantsKt.lpRecognition, String.valueOf(newLinepairs.get(1)));
        editor.putString(ConstantsKt.lpIdentification, String.valueOf(newLinepairs.get(2)));
        editor.putString(ConstantsKt.lpDetectionObject, String.valueOf(newLinepairs.get(3)));

        // Save the changes
        editor.apply();

        DbRefrence.initLinePair(newLinepairs.get(0), newLinepairs.get(1), newLinepairs.get(2), newLinepairs.get(3));
    }

    public void SaveNewTargetSizeSettings(String... Values) {
        ArrayList<Double> newTargetSizes = new ArrayList<Double>();

        int i = 0;
        for (String val : Values) {
            newTargetSizes.add(Double.parseDouble(Values[i]));
            i++;
        }
        targetSizeDefaultSettings = cont.getSharedPreferences(ConstantsKt.targetSizeFileName, Context.MODE_PRIVATE);


        // Get the editor to edit the file
        SharedPreferences.Editor editor = targetSizeDefaultSettings.edit();

        // Put default settings
        editor.putString(ConstantsKt.natoTargetSizeW, String.valueOf(newTargetSizes.get(0)));
        editor.putString(ConstantsKt.natoTargetSizeH, String.valueOf(newTargetSizes.get(1)));

        editor.putString(ConstantsKt.humanTargetSizeW, String.valueOf(newTargetSizes.get(2)));
        editor.putString(ConstantsKt.humanTargetSizeH, String.valueOf(newTargetSizes.get(3)));

        editor.putString(ConstantsKt.objectTargetSizeW, String.valueOf(newTargetSizes.get(4)));
        editor.putString(ConstantsKt.objectTargetSizeH, String.valueOf(newTargetSizes.get(5)));


        //Save the changes
        editor.apply();

        DbRefrence.eraseTargetSizes();
        DbRefrence.addTargetSize(new TargetSize(newTargetSizes.get(0), newTargetSizes.get(1)), TargetType.NATO);
        DbRefrence.addTargetSize(new TargetSize(newTargetSizes.get(2), newTargetSizes.get(3)), TargetType.HUMAN);
        DbRefrence.addTargetSize(new TargetSize(newTargetSizes.get(4), newTargetSizes.get(5)), TargetType.OBJECT);
    }


}
