package com.shir.nyquistoptics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Button  btn_refresh, btn_save;
    View  v_rectangleBackgroundDown, v_rectangleBackgroundUp, v_rectangleLineDown, v_rectangleLineLp;
    TextView  tv_lp;
    TextView  tv_lpDet, tv_lpDetObj, tv_lpIdent, tv_lpRec;
    EditText  et_lpDet, et_lpDetObj, et_lpIdent, et_lpRec;
    TextView  tv_targetSize;
    TextView  tv_txtHumanTargetSize, tv_txtNatoTargetSize, tv_txtObjTargetSize;
    TextView  tv_natoTargetSizeX, tv_humanTargetSizeX, tv_objTargetSizeX;
    EditText  et_natoTargetW, et_natoTargetH;
    EditText  et_humanTargetW, et_humanTargetH;
    EditText  et_objTargetW, et_objTargetH;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up UI
        setupUI();

        // Hide keyboard on start up app
        hideKeyboardOnStartUp();

        // Display the settings from from SharedPreferences files in the activity.
        displaySettings();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               // Vibrate
               vibrator.vibrate(50);

               // Refresh the current settings and set to default
               setDefaultSettings();

               // Display the default settings
               displaySettings();

               Toast.makeText(SettingsActivity.this, "Settings set to default", Toast.LENGTH_SHORT).show();

           }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                // If all fields are not filled, toast a massage to fill all fields
                if (et_lpDet.getText().toString().isEmpty()
                        || et_lpRec.getText().toString().isEmpty()
                        || et_lpIdent.getText().toString().isEmpty()
                        || et_lpDetObj.getText().toString().isEmpty()
                        || et_humanTargetH.getText().toString().isEmpty()
                        || et_humanTargetW.getText().toString().isEmpty()
                        || et_natoTargetH.getText().toString().isEmpty()
                        || et_natoTargetW.getText().toString().isEmpty()
                        || et_objTargetH.getText().toString().isEmpty()
                        || et_objTargetW.getText().toString().isEmpty()) {
                    Toast.makeText(SettingsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // If all fields are filled, continue
                else {

                    // Set the user settings to settings in SharedPreferences files
                    saveSettings(view);

                    // Set default settings derived from SharedPreferences files to the class
                    SetSettingsToClass(view);
                    Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();

                    // Move from this activity (SettingsActivity) to MainActivity
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));

                    //Close MainActivity
                    finish();

                }
            }

        });

    }


    //------------------------------------- Setup Methods -------------------------------------


    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {

        btn_refresh = findViewById(R.id.btn_refresh);
        btn_save = findViewById(R.id.btn_save);

        v_rectangleBackgroundDown = findViewById(R.id.v_rectangleBackgroundDown);
        v_rectangleBackgroundUp = findViewById(R.id.v_rectangleBackgroundUp);
        v_rectangleLineDown = findViewById(R.id.v_rectangleLineDown);
        v_rectangleLineLp = findViewById(R.id.v_rectangleLineLp);


        tv_lp = findViewById(R.id.tv_lp);

        tv_lpDet = findViewById(R.id.tv_lpDet);
        tv_lpDetObj = findViewById(R.id.tv_lpDetObj);
        tv_lpIdent = findViewById(R.id.tv_lpIdent);
        tv_lpRec = findViewById(R.id.tv_lpRec);

        et_lpDet = findViewById(R.id.et_lpDet);
        et_lpDetObj = findViewById(R.id.et_lpDetObj);
        et_lpIdent = findViewById(R.id.et_lpIdent);
        et_lpRec = findViewById(R.id.et_lpRec);

        tv_targetSize = findViewById(R.id.tv_targetSize);

        tv_txtHumanTargetSize = findViewById(R.id.tv_txtHumanTargetSize);
        tv_txtNatoTargetSize = findViewById(R.id.tv_txtNatoTargetSize);
        tv_txtObjTargetSize = findViewById(R.id.tv_txtObjTargetSize);

        tv_natoTargetSizeX = findViewById(R.id.tv_natoTargetSizeX);
        tv_humanTargetSizeX = findViewById(R.id.tv_humanTargetSizeX);
        tv_objTargetSizeX = findViewById(R.id.tv_objTargetSizeX);

        et_natoTargetW = findViewById(R.id.et_natoTargetW);
        et_natoTargetH = findViewById(R.id.et_natoTargetH);

        et_humanTargetW = findViewById(R.id.et_humanTargetW);
        et_humanTargetH = findViewById(R.id.et_humanTargetH);

        et_objTargetW = findViewById(R.id.et_objTargetW);
        et_objTargetH = findViewById(R.id.et_objTargetH);

    }



    //------------------------------------- Shared Preference Methods -------------------------------------


    /**
     * Set the user settings to settings in SharedPreferences files
     */
    public void saveSettings(View view) {
        saveLinePairSettings(view);
        saveTargetSizeSettings(view);

    }

    /**
     * Display the settings from from SharedPreferences files in the activity.
     */
    public void displaySettings() {
        displayLinePairSettings();
        displayTargetSizeSettings();
    }

    /**
     * Set default settings derived from SharedPreferences files to the class
     */
    public void SetSettingsToClass(View view) {
        SetLinePairSettingsToClass(view);
        SetTargetSizeSettingsToClass(view);
    }

    /**
     * Set the user line pair settings to settings in SharedPreferences file
     */
    public void saveLinePairSettings(View view) {

        // Get the file named "linePairDefaultSettings", private
        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        // Get the editor to edit the file
        SharedPreferences.Editor editor = linePairDefaultSettings.edit();

        // Put key+value to the file
        editor.putString("defaultSettings_lpDet", et_lpDet.getText().toString());
        editor.putString("defaultSettings_lpRec", et_lpRec.getText().toString());
        editor.putString("defaultSettings_lpIdent", et_lpIdent.getText().toString());
        editor.putString("defaultSettings_lpDetObj", et_lpDetObj.getText().toString());

        // Save the changes
        editor.apply();

    }

    /**
     * Display the line pair in the activity.
     * The line pair default settings derived from SharedPreferences file.
     */
    public void displayLinePairSettings() {

        // Get the file named "linePairDefaultSettings", private
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

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    public void SetLinePairSettingsToClass(View view) { //Print the saved settings

        // Get the file named "linePairDefaultSettings", private
        SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

        // Convert the user input from String to double
        double lpDet = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDet", ""));
        double lpRec = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpRec", ""));
        double lpIdent = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpIdent", ""));
        double lpDetObj = Double.parseDouble(linePairDefaultSettings.getString("defaultSettings_lpDetObj", ""));

        // Set the parameters in LinePair classes according to the user input
        LinePair.setLpDet(lpDet);
        LinePair.setLpRec(lpRec);
        LinePair.setLpIdent(lpIdent);
        LinePair.setLpDetObj(lpDetObj);

    }

    /**
     * Set the user target size settings to settings in SharedPreferences file
     */
    public void saveTargetSizeSettings(View view) {

        // Get the file named "targetSizeDefaultSettings", private
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        // Get the editor to edit the file
        SharedPreferences.Editor editor = targetSizeDefaultSettings.edit();
        editor.putString("defaultSettings_natoTargetW", et_natoTargetW.getText().toString());
        editor.putString("defaultSettings_natoTargetH", et_natoTargetH.getText().toString());

        editor.putString("defaultSettings_humanTargetW", et_humanTargetW.getText().toString());
        editor.putString("defaultSettings_humanTargetH", et_humanTargetH.getText().toString());

        editor.putString("defaultSettings_objTargetW", et_objTargetW.getText().toString());
        editor.putString("defaultSettings_objTargetH", et_objTargetH.getText().toString());

        // Save the changes
        editor.apply();

    }

    /**
     * Display for each target type its size in the activity.
     * The target size default settings derived from SharedPreferences file.
     */
    public void displayTargetSizeSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        // Convert the user input to String
        String natoTargetW = targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", "");
        String natoTargetH = targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", "");

        String humanTargetW = targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", "");
        String humanTargetH = targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", "");

        String objTargetW = targetSizeDefaultSettings.getString("defaultSettings_objTargetW", "");
        String objTargetH = targetSizeDefaultSettings.getString("defaultSettings_objTargetH", "");

        // Set the user input to edit text
        et_natoTargetW.setText(natoTargetW);
        et_natoTargetH.setText(natoTargetH);

        et_humanTargetW.setText(humanTargetW);
        et_humanTargetH.setText(humanTargetH);

        et_objTargetW.setText(objTargetW);
        et_objTargetH.setText(objTargetH);

    }

    /**
     * Set default settings derived from SharedPreferences file to the Target Size Holder (Singleton) class
     */
    public void SetTargetSizeSettingsToClass(View view) {

        // Get the file named "targetSizeDefaultSettings", private
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

        //Convert the user input from String to double

        double natoTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetW", ""));
        double natoTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_natoTargetH", ""));

        double humanTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetW", ""));
        double humanTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_humanTargetH", ""));

        double objTargetW = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetW", ""));
        double objTargetH = Double.parseDouble(targetSizeDefaultSettings.getString("defaultSettings_objTargetH", ""));


        // Set the parameters in TargetSizeS classes according to the user input

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

    /**
     * Create SharedPreferences files and set default settings to the files
     */
    private void setDefaultSettings() {
        setLinePairDefaultSettings();
        setTargetSizeDefaultSettings();
    }

    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private void setLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
         SharedPreferences linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

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

    /**
     * Create SharedPreferences file for Target Size settings, and set default settings
     */
    private void setTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        SharedPreferences targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

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


    //------------------------------------- Android Methods -------------------------------------

    /**
     * Hide keyboard on start up app
     */
    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }









}