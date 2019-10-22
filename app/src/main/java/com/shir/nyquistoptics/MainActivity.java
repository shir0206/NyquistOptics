package com.shir.nyquistoptics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    protected static MainActivity mainActivityRef;
    public static MainActivity updateActivity() {
        return mainActivityRef;
    }

    private ConstraintLayout cl_fov;
    private ConstraintLayout cl_human;
    private ConstraintLayout cl_inner_focal;
    private ConstraintLayout cl_nato;
    private ConstraintLayout cl_object;
    private ConstraintLayout cl_parameters;
    private EditText et_focal_length_input;
    private EditText et_hfov_input;
    private EditText et_sensor_pitch_input;
    private EditText et_sensor_size_h_input;
    private EditText et_sensor_size_w_input;
    private ImageView iv_btn_calc;
    private ImageView iv_focal_length;
    private ImageView iv_focal_length_input;
    private ImageView iv_fov;
    private ImageView iv_hfov;
    private ImageView iv_hfov_input;
    private ImageView iv_human;
    private ImageView iv_human_detection;
    private ImageView iv_human_identification;
    private ImageView iv_human_recognition;
    private ImageView iv_ifov;
    private ImageView iv_nato;
    private ImageView iv_nato_detection;
    private ImageView iv_nato_identification;
    private ImageView iv_nato_recognition;
    private ImageView iv_object;
    private ImageView iv_object_detection;
    private ImageView iv_object_recognition;
    private ImageView iv_parameters;
    private ImageView iv_sensor_pitch_input;
    private ImageView iv_sensor_size_input;
    private ImageView iv_vfov;
    private LinearLayout ll_calc;
    private LinearLayout ll_fov_focal_length;
    private LinearLayout ll_fov_hfov;
    private LinearLayout ll_fov_ifov;
    private LinearLayout ll_fov_title;
    private LinearLayout ll_fov_vfov;
    private LinearLayout ll_human_detection;
    private LinearLayout ll_human_identification;
    private LinearLayout ll_human_recognition;
    private LinearLayout ll_human_title;
    private LinearLayout ll_nato_detection;
    private LinearLayout ll_nato_identification;
    private LinearLayout ll_nato_recognition;
    private LinearLayout ll_nato_title;
    private LinearLayout ll_object_detection;
    private LinearLayout ll_object_recognition;
    private LinearLayout ll_object_title;
    private LinearLayout ll_parameters_focal_length;
    private LinearLayout ll_parameters_hfov;
    private LinearLayout ll_parameters_sensor_pitch;
    private LinearLayout ll_parameters_sensor_size;
    private LinearLayout ll_parameters_title;
    private ScrollView sv_focal;
    private TextView tv_focal_length;
    private TextView tv_focal_length_input;
    private TextView tv_focal_length_output;
    private TextView tv_fov;
    private TextView tv_hfov;
    private TextView tv_hfov_input;
    private TextView tv_hfov_output;
    private TextView tv_human;
    private TextView tv_human_detection;
    private TextView tv_human_detection_output;
    private TextView tv_human_identification;
    private TextView tv_human_identification_output;
    private TextView tv_human_recognition;
    private TextView tv_human_recognition_output;
    private TextView tv_human_size;
    private TextView tv_ifov;
    private TextView tv_ifov_output;
    private TextView tv_nato;
    private TextView tv_nato_detection;
    private TextView tv_nato_detection_output;
    private TextView tv_nato_identification;
    private TextView tv_nato_identification_output;
    private TextView tv_nato_recognition;
    private TextView tv_nato_recognition_output;
    private TextView tv_nato_size;
    private TextView tv_object;
    private TextView tv_object_detection;
    private TextView tv_object_detection_output;
    private TextView tv_object_recognition;
    private TextView tv_object_recognition_output;
    private TextView tv_object_size;
    private TextView tv_parameters;
    private TextView tv_sensor_pitch_input;
    private TextView tv_sensor_size_input;
    private TextView tv_sensor_size_x_input;
    private TextView tv_vfov;
    private TextView tv_vfov_output;

    private DecimalFormat formatOneDig = new DecimalFormat("0.0"); // Initialize decimal format for outputs
    private DecimalFormat formatSixDig = new DecimalFormat("0.000000"); // Initialize decimal format for outputs

    protected SharedPreferences targetSizeDefaultSettings; // Save all target sizes default settings
    protected SharedPreferences linePairDefaultSettings; // Save all line pairs default settings

    private TargetSizeHolder targetSizeHolder; // Initialize target size Singleton

    private Vibrator vibrator;

    protected boolean inputFocalOutputHfov;
    protected boolean inputHfovOutputFocal;

    /**
     * Calculate HFOV
     */
    private void setInputFocalOutputHfov(){

        inputFocalOutputHfov = true;
        inputHfovOutputFocal = false;


        // Set input focal length & output hfov visible
        ll_parameters_focal_length.setVisibility(View.VISIBLE);
        ll_fov_focal_length.setVisibility(View.GONE);

        ll_parameters_hfov.setVisibility(View.GONE);
        et_hfov_input.setText("");
        ll_fov_hfov.setVisibility(View.VISIBLE);

    }

    /**
     * Calculate Focal Length
     */
    private void setInputHfovOutputFocal(){

        inputFocalOutputHfov = false;
        inputHfovOutputFocal = true;

        // Set input hfov & output focal length visible

        ll_parameters_focal_length.setVisibility(View.GONE);
        et_focal_length_input.setText("");
        ll_fov_focal_length.setVisibility(View.VISIBLE);

        ll_parameters_hfov.setVisibility(View.VISIBLE);
        ll_fov_hfov.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_main);

        // Hide keyboard on start up app
        hideKeyboardOnStartUp();

        // Hide keyboard on start up app
        hideKeyboardOnStartUp();

        // Create SharedPreferences files if haven't created yet, and set default settings to the files
        isEmptyDefaultSettings();

        // Set default settings derived from SharedPreferences files to the objects
        SetDefaultSettings();

        // Set up UI
        setupUI();

        setInputFocalOutputHfov();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        iv_btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);


                // If all fields are not filled, toast a massage to fill all fields
                if ((et_focal_length_input.getText().toString().equals("") && et_hfov_input.getText().toString().equals(""))
                        || et_sensor_pitch_input.getText().toString().equals("")
                        || et_sensor_size_w_input.getText().toString().equals("")
                        || et_sensor_size_h_input.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // If all fields are filled, continue
                else {

                    // Hide keyboard once button pressed
                    hideKeyboardOnceBtnPressed();

                    if (inputFocalOutputHfov) {
                        calculateHfov();
                    }
                    else if (inputHfovOutputFocal) {
                        calculateFocalLength();
                    }


                }
            }
        });


    }



    //------------------------------------- Setup Methods -------------------------------------

    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {

        cl_fov = findViewById(R.id.cl_fov);
        cl_human = findViewById(R.id.cl_human);
        cl_inner_focal = findViewById(R.id.cl_inner_focal);
        cl_nato = findViewById(R.id.cl_nato);
        cl_object = findViewById(R.id.cl_object);
        cl_parameters = findViewById(R.id.cl_parameters);
        et_focal_length_input = findViewById(R.id.et_focal_length_input);
        et_hfov_input = findViewById(R.id.et_hfov_input);
        et_sensor_pitch_input = findViewById(R.id.et_sensor_pitch_input);
        et_sensor_size_h_input = findViewById(R.id.et_sensor_size_h_input);
        et_sensor_size_w_input = findViewById(R.id.et_sensor_size_w_input);
        iv_btn_calc = findViewById(R.id.iv_btn_calc);
        iv_focal_length = findViewById(R.id.iv_focal_length);
        iv_focal_length_input = findViewById(R.id.iv_focal_length_input);
        iv_fov = findViewById(R.id.iv_fov);
        iv_hfov = findViewById(R.id.iv_hfov);
        iv_hfov_input = findViewById(R.id.iv_hfov_input);
        iv_human = findViewById(R.id.iv_human);
        iv_human_detection = findViewById(R.id.iv_human_detection);
        iv_human_identification = findViewById(R.id.iv_human_identification);
        iv_human_recognition = findViewById(R.id.iv_human_recognition);
        iv_ifov = findViewById(R.id.iv_ifov);
        iv_nato = findViewById(R.id.iv_nato);
        iv_nato_detection = findViewById(R.id.iv_nato_detection);
        iv_nato_identification = findViewById(R.id.iv_nato_identification);
        iv_nato_recognition = findViewById(R.id.iv_nato_recognition);
        iv_object = findViewById(R.id.iv_object);
        iv_object_detection = findViewById(R.id.iv_object_detection);
        iv_object_recognition = findViewById(R.id.iv_object_recognition);
        iv_parameters = findViewById(R.id.iv_parameters);
        iv_sensor_pitch_input = findViewById(R.id.iv_sensor_pitch_input);
        iv_sensor_size_input = findViewById(R.id.iv_sensor_size_input);
        iv_vfov = findViewById(R.id.iv_vfov);
        ll_calc = findViewById(R.id.ll_calc);
        ll_fov_focal_length = findViewById(R.id.ll_fov_focal_length);
        ll_fov_hfov = findViewById(R.id.ll_fov_hfov);
        ll_fov_ifov = findViewById(R.id.ll_fov_ifov);
        ll_fov_title = findViewById(R.id.ll_fov_title);
        ll_fov_vfov = findViewById(R.id.ll_fov_vfov);
        ll_human_detection = findViewById(R.id.ll_human_detection);
        ll_human_identification = findViewById(R.id.ll_human_identification);
        ll_human_recognition = findViewById(R.id.ll_human_recognition);
        ll_human_title = findViewById(R.id.ll_human_title);
        ll_nato_detection = findViewById(R.id.ll_nato_detection);
        ll_nato_identification = findViewById(R.id.ll_nato_identification);
        ll_nato_recognition = findViewById(R.id.ll_nato_recognition);
        ll_nato_title = findViewById(R.id.ll_nato_title);
        ll_object_detection = findViewById(R.id.ll_object_detection);
        ll_object_recognition = findViewById(R.id.ll_object_recognition);
        ll_object_title = findViewById(R.id.ll_object_title);
        ll_parameters_focal_length = findViewById(R.id.ll_parameters_focal_length);
        ll_parameters_hfov = findViewById(R.id.ll_parameters_hfov);
        ll_parameters_sensor_pitch = findViewById(R.id.ll_parameters_sensor_pitch);
        ll_parameters_sensor_size = findViewById(R.id.ll_parameters_sensor_size);
        ll_parameters_title = findViewById(R.id.ll_parameters_title);
        sv_focal = findViewById(R.id.sv_focal);
        tv_focal_length = findViewById(R.id.tv_focal_length);
        tv_focal_length_input = findViewById(R.id.tv_focal_length_input);
        tv_focal_length_output = findViewById(R.id.tv_focal_length_output);
        tv_fov = findViewById(R.id.tv_fov);
        tv_hfov = findViewById(R.id.tv_hfov);
        tv_hfov_input = findViewById(R.id.tv_hfov_input);
        tv_hfov_output = findViewById(R.id.tv_hfov_output);
        tv_human = findViewById(R.id.tv_human);
        tv_human_detection = findViewById(R.id.tv_human_detection);
        tv_human_detection_output = findViewById(R.id.tv_human_detection_output);
        tv_human_identification = findViewById(R.id.tv_human_identification);
        tv_human_identification_output = findViewById(R.id.tv_human_identification_output);
        tv_human_recognition = findViewById(R.id.tv_human_recognition);
        tv_human_recognition_output = findViewById(R.id.tv_human_recognition_output);
        tv_human_size = findViewById(R.id.tv_human_size);
        tv_ifov = findViewById(R.id.tv_ifov);
        tv_ifov_output = findViewById(R.id.tv_ifov_output);
        tv_nato = findViewById(R.id.tv_nato);
        tv_nato_detection = findViewById(R.id.tv_nato_detection);
        tv_nato_detection_output = findViewById(R.id.tv_nato_detection_output);
        tv_nato_identification = findViewById(R.id.tv_nato_identification);
        tv_nato_identification_output = findViewById(R.id.tv_nato_identification_output);
        tv_nato_recognition = findViewById(R.id.tv_nato_recognition);
        tv_nato_recognition_output = findViewById(R.id.tv_nato_recognition_output);
        tv_nato_size = findViewById(R.id.tv_nato_size);
        tv_object = findViewById(R.id.tv_object);
        tv_object_detection = findViewById(R.id.tv_object_detection);
        tv_object_detection_output = findViewById(R.id.tv_object_detection_output);
        tv_object_recognition = findViewById(R.id.tv_object_recognition);
        tv_object_recognition_output = findViewById(R.id.tv_object_recognition_output);
        tv_object_size = findViewById(R.id.tv_object_size);
        tv_parameters = findViewById(R.id.tv_parameters);
        tv_sensor_pitch_input = findViewById(R.id.tv_sensor_pitch_input);
        tv_sensor_size_input = findViewById(R.id.tv_sensor_size_input);
        tv_sensor_size_x_input = findViewById(R.id.tv_sensor_size_x_input);
        tv_vfov = findViewById(R.id.tv_vfov);
        tv_vfov_output = findViewById(R.id.tv_vfov_output);


        setOutputInvisibie();

    }


    /**
     * Turn invisible all output Views, all output layouts & all output TextViews
     */
    private void setOutputInvisibie() {
        cl_fov.setVisibility(View.INVISIBLE);
        cl_nato.setVisibility(View.INVISIBLE);
        cl_human.setVisibility(View.INVISIBLE);
        cl_object.setVisibility(View.INVISIBLE);

    }

    private void setOutputVisibie() {
        cl_fov.setVisibility(View.VISIBLE);
        cl_nato.setVisibility(View.VISIBLE);
        cl_human.setVisibility(View.VISIBLE);
        cl_object.setVisibility(View.VISIBLE);
    }

    /**
     * Calculate HFOV, input focal length, output hfov
     */
    private void calculateHfov() {

        // Convert the user input from String to double
        double sensorPitch = Double.parseDouble(et_sensor_pitch_input.getText().toString());
        double focalLength = Double.parseDouble(et_focal_length_input.getText().toString());
        double sensorSizeW = Double.parseDouble(et_sensor_size_w_input.getText().toString());
        double sensorSizeH = Double.parseDouble(et_sensor_size_h_input.getText().toString());

        // Set the parameters in Properties & SensorSize classes according to the user input
        Properties.setSensorPitch(sensorPitch);
        SensorSize.setWidth(sensorSizeW);
        SensorSize.setHeight(sensorSizeH);
        Properties.setFocalLength(focalLength);

        // PART 1 - Calculate FOV
        calculateFov(-1);

        // PART 2 - Calculate Targets
        calculateTargets();

        // Turn the output  visible
        setOutputVisibie();

        // Create a toast message calculated successfully
        Toast.makeText(MainActivity.this, "Calculated HFOV & DRI Successfully", Toast.LENGTH_SHORT).show();

    }

    /**
     * Calculate fov
     */
    private void calculateFov(double hfovInput){
        // Calculate the output
        Fov.setFov(hfovInput);

        // Convert the output from double to String and formatting the decimal digits
        String ifov = formatSixDig.format(Fov.getIfov());
        String hfov = formatOneDig.format(Fov.getHfov());
        String vfov = formatOneDig.format(Fov.getVfov());

        String focalLength = formatOneDig.format(Properties.getFocalLength());

        // Set the String output to TextView
        tv_ifov_output.setText(ifov);
        tv_hfov_output.setText(hfov);
        tv_vfov_output.setText(vfov);
        tv_focal_length_output.setText(focalLength);
    }

    /**
     * Calculate targets
     */
    private void calculateTargets(){
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
        tv_nato_detection_output.setText(natoTargetDet);
        tv_nato_recognition_output.setText(natoTargetRec);
        tv_nato_identification_output.setText(natoTargetIdent);

        tv_human_detection_output.setText(humanTargetDet);
        tv_human_recognition_output.setText(humanTargetRec);
        tv_human_identification_output.setText(humanTargetIdent);

        tv_object_detection_output.setText(objTargetDet);
        tv_object_recognition_output.setText(objTargetRec);

        // Convert the target size from double to String
        String natoSize = "(" + Double.toString(natoTargetSize.getWidth()) + "x" + Double.toString(natoTargetSize.getHeight()) + ")";
        String humanSize = "(" + Double.toString(humanTargetSize.getWidth()) + "x" + Double.toString(humanTargetSize.getHeight()) + ")";
        String objSize = "(" + Double.toString(objTargetSize.getWidth()) + "x" + Double.toString(objTargetSize.getHeight()) + ")";

        // Set the target size to TextView
        tv_nato_size.setText(natoSize);
        tv_human_size.setText(humanSize);
        tv_object_size.setText(objSize);
    }

    /**
     * Calculate HFOV, input hfov, output focal length
     */
    private void calculateFocalLength() {

        // Convert the user input from String to double
        double sensorPitch = Double.parseDouble(et_sensor_pitch_input.getText().toString());
        double hfov = Double.parseDouble(et_hfov_input.getText().toString());
        double sensorSizeW = Double.parseDouble(et_sensor_size_w_input.getText().toString());
        double sensorSizeH = Double.parseDouble(et_sensor_size_h_input.getText().toString());

        // Set the parameters in Properties & SensorSize classes according to the user input
        Properties.setSensorPitch(sensorPitch);
        SensorSize.setWidth(sensorSizeW);
        SensorSize.setHeight(sensorSizeH);
        Properties.calcFocalLength(hfov);

        // PART 1 - Calculate FOV
        calculateFov(hfov);

        // PART 2 - Calculate Targets
        calculateTargets();

        // Turn the output  visible
        setOutputVisibie();

        // Create a toast message calculated successfully
        Toast.makeText(MainActivity.this, "Calculated Focal Length & DRI Successfully", Toast.LENGTH_SHORT).show();


    }


    //------------------------------------- Shared Preference Methods -------------------------------------

    /**
     * Create SharedPreferences files and set default settings to the files
     */
    private void isEmptyDefaultSettings() {
        isEmptyLinePairDefaultSettings();
        isEmptyTargetSizeDefaultSettings();
    }

    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private void isEmptyLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

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
    private void isEmptyTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        targetSizeDefaultSettings = getSharedPreferences("targetSizeDefaultSettings", Context.MODE_PRIVATE);

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
     * Set default settings derived from SharedPreferences files to the objects
     */
    private void SetDefaultSettings() {
        SetLinePairDefaultSettingsToClass();
        SetTargetSizeDefaultSettingsToObjects();
    }

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    private void SetLinePairDefaultSettingsToClass() {

        // Get the file named "linePairDefaultSettings", private
        linePairDefaultSettings = getSharedPreferences("linePairDefaultSettings", Context.MODE_PRIVATE);

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

        // Initialize TargetSize class instances (using singleton - TargetSizeHolder class)
        targetSizeHolder = TargetSizeHolder.getInstance();

        // Check if a specific TargetSize instance exists in TargetSizeHolder class (in hashmap targetSizeCollection).
        // If not, add the instance.

        if (!targetSizeHolder.hasTargetSize("natoTargetSize")) {
            targetSizeHolder.addTargetSize("natoTargetSize", new TargetSize(natoTargetW, natoTargetH));
        }

        if (!targetSizeHolder.hasTargetSize("humanTargetSize")) {
            targetSizeHolder.addTargetSize("humanTargetSize", new TargetSize(humanTargetW, humanTargetH));
        }

        if (!targetSizeHolder.hasTargetSize("objTargetSize")) {
            targetSizeHolder.addTargetSize("objTargetSize", new TargetSize(objTargetW, objTargetH));
        }

    }

    //------------------------------------- Android Methods -------------------------------------

    /**
     * Hide keyboard on start up app
     */
    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Create options menu above and inflate it
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    /**
     * Open options menu above
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_input_focal_output_hfov:
                // Move from this activity (MainActivity) to SettingActivity
                setInputFocalOutputHfov();
                setOutputInvisibie();
                //finish(); //Closes MainActivity
                return true;

            case R.id.action_input_hfov_output_focal:
                // Move from this activity (MainActivity) to SettingActivity
                setInputHfovOutputFocal();
                setOutputInvisibie();
                //finish(); //Closes MainActivity
                return true;

            case R.id.action_user_properties:
                // Move from this activity (MainActivity) to SettingActivity
                startActivity(new Intent(this, SettingsActivity.class));
                //finish(); //Closes MainActivity
                return true;

            case R.id.action_settings:
                // Move from this activity (MainActivity) to SettingActivity
                startActivity(new Intent(this, SettingsActivity.class));
                //finish(); //Closes MainActivity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hide keyboard once btn pressed
     */
    private void hideKeyboardOnceBtnPressed() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(iv_btn_calc.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

}




