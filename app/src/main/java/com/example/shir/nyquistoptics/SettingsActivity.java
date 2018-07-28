package com.example.shir.nyquistoptics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    TextView tv_settingsComment; // initialize comment

    TextView tv_lp, tv_lpDet, tv_lpRec, tv_lpIdent, tv_lpDetObj; //initialize inputs titles
    EditText et_lpDet, et_lpRec, et_lpIdent, et_lpDetObj; //initialize inputs values


    EditText et_natoTargetH, et_natoTargetW, et_humanTargetH, et_humanTargetW, et_objTargetH, et_objTargetW;
    TextView tv_humanTargetSizeX, tv_natoTargetSizeX, tv_objTargetSizeX, tv_targetSize, tv_txtHumanTarget, tv_txtNatoTarget, tv_txtObjTarget;


    Button btn_save;
    //   Button btn_setDefault; // initialize buttons

    //   SharedPreferences targetSizeDefaultSettings; //Copy from MainActivity
    //   SharedPreferences linePairDefaultSettings;//Copy from MainActivity
    //   TargetSizeHolder targetSizeHolder;//Copy from MainActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupUI();
        displaySettings();
        hideKeyboardOnStartUp();

        /*
        btn_setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isEmptyDefaultSettings(); //Create SharedPreferences files if haven't created yet, and set default settings to the files
                SetDefaultSettings(); //Set default settings derived from SharedPreferences files to the objects

                Toast.makeText(SettingsActivity.this, "Default settings set", Toast.LENGTH_SHORT).show();

            }
        });
    */

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //If all fields are not filled, toast a massage to fill all fields
                if (et_lpDet.getText().toString().isEmpty() || et_lpRec.getText().toString().isEmpty() || et_lpIdent.getText().toString().isEmpty() || et_lpDetObj.getText().toString().isEmpty() || et_humanTargetH.getText().toString().isEmpty() || et_humanTargetW.getText().toString().isEmpty() || et_natoTargetH.getText().toString().isEmpty() || et_natoTargetW.getText().toString().isEmpty() || et_objTargetH.getText().toString().isEmpty() || et_objTargetW.getText().toString().isEmpty()) {
                    Toast.makeText(SettingsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


                //If all fields are filled, continue
                else {


                    saveSettings(view);
                    SetSettingsToClass(view);
                    Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class)); //Move from this activity (SettingsActivity) to MainActivity
                    finish(); //Closes MainActivity

                }
            }

        });

    }

    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setupUI() {

        tv_settingsComment = findViewById(R.id.tv_settingsComment);
        tv_lp = findViewById(R.id.tv_lp);
        tv_lpDet = findViewById(R.id.tv_lpDet);
        tv_lpRec = findViewById(R.id.tv_lpRec);
        tv_lpIdent = findViewById(R.id.tv_lpIdent);
        tv_lpDetObj = findViewById(R.id.tv_lpDetObj);


        et_lpDet = findViewById(R.id.et_lpDet);
        et_lpRec = findViewById(R.id.et_lpRec);
        et_lpIdent = findViewById(R.id.et_lpIdent);
        et_lpDetObj = findViewById(R.id.et_lpDetObj);


        et_humanTargetH = findViewById(R.id.et_humanTargetH);
        et_humanTargetW = findViewById(R.id.et_humanTargetW);
        et_natoTargetH = findViewById(R.id.et_natoTargetH);
        et_natoTargetW = findViewById(R.id.et_natoTargetW);
        et_objTargetH = findViewById(R.id.et_objTargetH);
        et_objTargetW = findViewById(R.id.et_objTargetW);
        tv_humanTargetSizeX = findViewById(R.id.tv_humanTargetSizeX);
        tv_natoTargetSizeX = findViewById(R.id.tv_natoTargetSizeX);
        tv_objTargetSizeX = findViewById(R.id.tv_objTargetSizeX);
        tv_targetSize = findViewById(R.id.tv_targetSize);
        tv_txtHumanTarget = findViewById(R.id.tv_txtHumanTarget);
        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tv_txtObjTarget = findViewById(R.id.tv_txtObjTarget);


        btn_save = findViewById(R.id.btn_save);
        // double lpDet = LinePair.getLpDet();
        //  String text = Double.toString(lpDet);
        // et_lpDet.setText(text);
    }

    public void saveSettings(View view) {
        saveLinePairSettings(view);
        saveTargetSizeSettings(view);

    }

    public void displaySettings() {
        displayLinePairSettings();
        displayTargetSizeSettings();
    }

    public void SetSettingsToClass(View view) {
        SetLinePairSettingsToClass(view);
        SetTargetSizeSettingsToClass(view);
    }

    public void saveLinePairSettings(View view) { //Save the user settings
        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE); //Create a file named "defaultSettings", private

        SharedPreferences.Editor editor = linePairDefaultSettings.edit(); //Create editor to edit the file
        editor.putString("defaultSettings_lpDet", et_lpDet.getText().toString()); //Puts key+value to the file
        editor.putString("defaultSettings_lpRec", et_lpRec.getText().toString());
        editor.putString("defaultSettings_lpIdent", et_lpIdent.getText().toString());
        editor.putString("defaultSettings_lpDetObj", et_lpDetObj.getText().toString());

        editor.apply(); //Saves the changes

    }

    public void displayLinePairSettings() { //Print the saved settings
        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        String lpDet = linePairDefaultSettings.getString("defaultSettings_lpDet", "");
        String lpRec = linePairDefaultSettings.getString("defaultSettings_lpRec", "");
        String lpIdent = linePairDefaultSettings.getString("defaultSettings_lpIdent", "");
        String lpDetObj = linePairDefaultSettings.getString("defaultSettings_lpDetObj", "");

        et_lpDet.setText(lpDet);
        et_lpRec.setText(lpRec);
        et_lpIdent.setText(lpIdent);
        et_lpDetObj.setText(lpDetObj);
    }

    public void SetLinePairSettingsToClass(View view) { //Print the saved settings
        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        //Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDet", ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpRec", ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpIdent", ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDetObj", ""));

        //Set the parameters in LinePair classes according to the user input
        LinePair.setLpDet(lpDet);
        LinePair.setLpRec(lpRec);
        LinePair.setLpIdent(lpIdent);
        LinePair.setLpDetObj(lpDetObj);

    }

    public void saveTargetSizeSettings(View view) { //Save the user settings
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE); //Create a file named "defaultSettings", private

        SharedPreferences.Editor editor = targetSizeDefaultSettings.edit(); //Create editor to edit the file
        editor.putString("defaultSettings_natoTargetW", et_natoTargetW.getText().toString());
        editor.putString("defaultSettings_natoTargetH", et_natoTargetH.getText().toString());

        editor.putString("defaultSettings_humanTargetW", et_humanTargetW.getText().toString());
        editor.putString("defaultSettings_humanTargetH", et_humanTargetH.getText().toString());

        editor.putString("defaultSettings_objTargetW", et_objTargetW.getText().toString());
        editor.putString("defaultSettings_objTargetH", et_objTargetH.getText().toString());

        editor.apply(); //Saves the changes

    }

    public void displayTargetSizeSettings() { //Print the saved settings
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        String natoTargetW = targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", "");
        String natoTargetH = targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", "");

        String humanTargetW = targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", "");
        String humanTargetH = targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", "");

        String objTargetW = targetSizeDefaultSettings.getString("defaultSettings_objTargetW", "");
        String objTargetH = targetSizeDefaultSettings.getString("defaultSettings_objTargetH", "");


        et_natoTargetW.setText(natoTargetW);
        et_natoTargetH.setText(natoTargetH);

        et_humanTargetW.setText(humanTargetW);
        et_humanTargetH.setText(humanTargetH);

        et_objTargetW.setText(objTargetW);
        et_objTargetH.setText(objTargetH);


    }

    public void SetTargetSizeSettingsToClass(View view) { //Print the saved settings
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        //Convert the user input from String to double

        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetW", ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetH", ""));


        //Set the parameters in TargetSizeS classes according to the user input

        TargetSizeHolder targetSizeHolder = TargetSizeHolder.getInstance();

        TargetSize natoTargetSize = targetSizeHolder.getTargetSize("natoTargetSize");
        TargetSize humanTargetSize = targetSizeHolder.getTargetSize("humanTargetSize");
        TargetSize objTargetSize = targetSizeHolder.getTargetSize("objTargetSize");

        natoTargetSize.setWidth(natoTargetW);
        natoTargetSize.setHeight(natoTargetH);

        humanTargetSize.setWidth(humanTargetW);
        humanTargetSize.setHeight(humanTargetH);

        objTargetSize.setWidth(objTargetW);
        objTargetSize.setHeight(objTargetH);


    }

    /*
    private void isEmptyDefaultSettings() { //Create SharedPreferences files if haven't created yet, and set default settings to the files
        isEmptyLinePairDefaultSettings();
        isEmptyTargetSizeDefaultSettings();
    }//Copy from MainActivity

    private void isEmptyLinePairDefaultSettings() {
        linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE); //Create a file named "defaultSettings", private

        //Check if SharedPreferences file is empty (linePairDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (linePairDefaultSettings.getString("defaultSettings_lpDet", "").isEmpty()) {

            SharedPreferences.Editor editor = linePairDefaultSettings.edit(); //Create an editor to edit the file

            //Put default settings
            editor.putString("defaultSettings_lpDet", "2");
            editor.putString("defaultSettings_lpRec", "6");
            editor.putString("defaultSettings_lpIdent", "10");
            editor.putString("defaultSettings_lpDetObj", "1.2");

            editor.apply(); //Saves the changes

        }
    }//Copy from MainActivity

    private void isEmptyTargetSizeDefaultSettings() {
        targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE); //Create a file named "defaultSettings", private

        //Check if SharedPreferences file is empty (targetSizeDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", "").isEmpty()) {

            SharedPreferences.Editor editor = targetSizeDefaultSettings.edit(); //Create an editor to edit the file

            //Put default settings
            editor.putString("defaultSettings_natoTargetW", "2.3");
            editor.putString("defaultSettings_natoTargetH", "2.3");

            editor.putString("defaultSettings_humanTargetW", "0.5");
            editor.putString("defaultSettings_humanTargetH", "1.7");

            editor.putString("defaultSettings_objTargetW", "0.5");
            editor.putString("defaultSettings_objTargetH", "0.5");

            editor.apply(); //Saves the changes

        }
    }//Copy from MainActivity

    private void SetDefaultSettings() { //Set default settings derived from SharedPreferences files to the objects
        SetLinePairDefaultSettingsToClass();
        SetTargetSizeDefaultSettingsToObjects();
    }//Copy from MainActivity

    private void SetLinePairDefaultSettingsToClass() { //Print the saved settings
        linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        //Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDet", ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpRec", ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpIdent", ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDetObj", ""));

        //Set the parameters in LinePair classes according to the user input
        LinePair.setLpDet(lpDet);
        LinePair.setLpRec(lpRec);
        LinePair.setLpIdent(lpIdent);
        LinePair.setLpDetObj(lpDetObj);

    }//Copy from MainActivity

    private void SetTargetSizeDefaultSettingsToObjects() {
        //get the values from targetSizeDefaultSettings Shared Preferences and convert from String to double
        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetW", ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetH", ""));


        //Initialize TargetSize class instances (using singleton - TargetSizeHolder class)

        targetSizeHolder = TargetSizeHolder.getInstance();

        //Check if a specific TargetSize instance exists in TargetSizeHolder class (in hashmap targetSizeCollection).
        //If not, add the instance.
        //Every instance initialized with his own unique W*H, as described in SharedPreferences (targetSizeDefaultSettings file)

        if (!targetSizeHolder.hasTargetSize("natoTargetSize")) {
            targetSizeHolder.addTargetSize("natoTargetSize", new TargetSize(natoTargetW, natoTargetH));
        }

        if (!targetSizeHolder.hasTargetSize("humanTargetSize")) {
            targetSizeHolder.addTargetSize("humanTargetSize", new TargetSize(humanTargetW, humanTargetH));
        }

        if (!targetSizeHolder.hasTargetSize("objTargetSize")) {
            targetSizeHolder.addTargetSize("objTargetSize", new TargetSize(objTargetW, objTargetH));
        }

    }//Copy from MainActivity

*/
}