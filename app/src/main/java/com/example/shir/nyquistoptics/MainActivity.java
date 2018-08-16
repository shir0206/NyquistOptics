package com.example.shir.nyquistoptics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements Serializable {

  //  TextView tv_comment; // initialize comment

    TextView tv_txtSensorPitch, tv_txtFocalLength, tv_txtSensorSize, tv_txtSensorSizeX; //initialize inputs titles
    EditText et_sensorPitch, et_focalLength, et_sensorSizeW, et_sensorSizeH; //initialize inputs values

    Button btn_calculate; // initialize calculate button

    TextView tv_txtFov, tv_txtIfov, tv_txtHfov, tv_txtVfov; //initialize outputs titles
    TextView tv_resIfov, tv_resHfov, tv_resVfov; //initialize outputs results

    TextView tv_txtTargetDri;
    TextView tv_txtNatoTarget; //initialize title
//    TextView tv_txtNatoDet, tv_txtNatoRec, tv_txtNatoIdent; //initialize outputs titles
    TextView tv_resNatoDet, tv_resNatoRec, tv_resNatoIdent; //initialize outputs results

    TextView tv_txtHumanTarget; //initialize title
 //   TextView tv_txtHumanDet, tv_txtHumanRec, tv_txtHumanIdent; //initialize outputs titles
    TextView tv_resHumanDet, tv_resHumanRec, tv_resHumanIdent; //initialize outputs results

    TextView tv_txtObjTarget; //initialize title
 //   TextView tv_txtObjDet, tv_txtObjRec; //initialize outputs titles
    TextView tv_resObjDet, tv_resObjRec; //initialize outputs results

    TextView tv_natoSize, tv_humanSize, tv_objSize;

    TextView tv_txtDet, tv_txtRec,tv_txtIdent;

    ImageView iv_sensorPitch, iv_focalLength, iv_sensorSize;

 //   ImageView iv_sensorPitchBackground, iv_focalLengthBackground, iv_sensorSizeBackground;


   // Table t_tableInput, t_tableFov,     t_tableTargetDri; ///////////////
    View v_rectangleBackgroundUp, v_rectangleLineUp, v_rectangleLineFov,  v_rectangleLineDri; /////////


    ArrayList<TextView> output = new ArrayList<>();

    DecimalFormat formatOneDig = new DecimalFormat("0.0");
    DecimalFormat formatSixDig = new DecimalFormat("0.000000");

    SharedPreferences targetSizeDefaultSettings;
    SharedPreferences linePairDefaultSettings;

    TargetSizeHolder targetSizeHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_t2);

        hideKeyboardOnStartUp(); //Hide keyboard on start up app
        isEmptyDefaultSettings(); //Create SharedPreferences files if haven't created yet, and set default settings to the files
        SetDefaultSettings(); //Set default settings derived from SharedPreferences files to the objects
        setupUI();

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If all fields are not filled, toast a massage to fill all fields
                if (et_focalLength.getText().toString().isEmpty() || et_sensorPitch.getText().toString().isEmpty() || et_sensorSizeW.getText().toString().isEmpty() || et_sensorSizeH.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                //If all fields are filled, continue
                else {

                    hideKeyboardOnceBtnPressed();  //hide keyboard once button pressed

                    //Convert the user input from String to double
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorSizeW = Double.parseDouble(et_sensorSizeW.getText().toString());
                    double sensorSizeH = Double.parseDouble(et_sensorSizeH.getText().toString());

                    //Set the parameters in Properties & SensorSize classes according to the user input
                    Properties.setSensorPitch(sensorPitch);
                    Properties.setFocalLength(focalLength);
                    SensorSize.setWidth(sensorSizeW);
                    SensorSize.setHeight(sensorSizeH);

                    //PART 1 - Calculate FOV

                    //Calculate the output
                    Fov.setFov();

                    //Convert the output from double to String and formatting the decimal digits
                    String ifov = formatSixDig.format(Fov.getIfov());
                    String hfov = formatOneDig.format(Fov.getHfov());
                    String vfov = formatOneDig.format(Fov.getVfov());

                    //Set the String output to TextView
                    tv_resIfov.setText(ifov);
                    tv_resHfov.setText(hfov);
                    tv_resVfov.setText(vfov);

                    //PART 2 - Calculate Targets

                    //Create local TargetSize instances, that contains data derived from targetSizeHolder class (in hashmap targetSizeCollection)
                    TargetSize natoTargetSize = targetSizeHolder.getTargetSize("natoTargetSize");
                    TargetSize humanTargetSize = targetSizeHolder.getTargetSize("humanTargetSize");
                    TargetSize objTargetSize = targetSizeHolder.getTargetSize("objTargetSize");

                    //Initialize Target class instances
                    Target natoTarget = new Target();
                    Target humanTarget = new Target();
                    Target objTarget = new Target();

                    //Calculate the output
                    natoTarget.setTarget(natoTargetSize);
                    humanTarget.setTarget(humanTargetSize);
                    objTarget.setTarget(objTargetSize);

                    //Convert the output from double to String
                    String natoTargetDet = formatOneDig.format(natoTarget.getDetection());
                    String natoTargetRec = formatOneDig.format(natoTarget.getRecognition());
                    String natoTargetIdent = formatOneDig.format(natoTarget.getIdentify());

                    String humanTargetDet = formatOneDig.format(humanTarget.getDetection());
                    String humanTargetRec = formatOneDig.format(humanTarget.getRecognition());
                    String humanTargetIdent = formatOneDig.format(humanTarget.getIdentify());

                    String objTargetDet = formatOneDig.format(objTarget.getDetection());
                    String objTargetRec = formatOneDig.format(objTarget.getRecognition());

                    //Set the String output to TextView
                    tv_resNatoDet.setText(natoTargetDet);
                    tv_resNatoRec.setText(natoTargetRec);
                    tv_resNatoIdent.setText(natoTargetIdent);

                    tv_resHumanDet.setText(humanTargetDet);
                    tv_resHumanRec.setText(humanTargetRec);
                    tv_resHumanIdent.setText(humanTargetIdent);

                    tv_resObjDet.setText(objTargetDet);
                    tv_resObjRec.setText(objTargetRec);

                    //Convert the target size from double to String
                    String natoSize = "(" + Double.toString(natoTargetSize.getWidth()) + "x" + Double.toString(natoTargetSize.getHeight()) + ")";
                    String humanSize = "(" + Double.toString(humanTargetSize.getWidth()) + "x" + Double.toString(humanTargetSize.getHeight()) + ")";
                    String objSize = "(" + Double.toString(objTargetSize.getWidth()) + "x" + Double.toString(objTargetSize.getHeight()) + ")";

                    //Set the target size to TextView
                    tv_natoSize.setText(natoSize);
                    tv_humanSize.setText(humanSize);
                    tv_objSize.setText(objSize);

                    //Turn the output TextView to visible
                    visibleOutputTextView();
                }
            }
        });


    }

    private void hideKeyboardOnStartUp() { //Hide keyboard on start up app
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setupUI() { //Initialize all TextViews, EditViews & Button



       // tv_comment = findViewById(R.id.tv_comment);

        tv_txtSensorPitch = findViewById(R.id.tv_txtSensorPitch);
        tv_txtFocalLength = findViewById(R.id.tv_txtFocalLength);
        tv_txtSensorSize = findViewById(R.id.tv_txtSensorSize);
        tv_txtSensorSizeX = findViewById(R.id.tv_txtSensorSizeX);

        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorSizeW = findViewById(R.id.et_sensorSizeW);
        et_sensorSizeH = findViewById(R.id.et_sensorSizeH);

        iv_sensorPitch = findViewById(R.id.iv_sensorPitch);
        iv_focalLength = findViewById(R.id.iv_focalLength);
        iv_sensorSize = findViewById(R.id.iv_sensorSize);

     //   iv_sensorPitchBackground = findViewById(R.id.iv_sensorPitchBackground);
     //   iv_focalLengthBackground = findViewById(R.id.iv_focalLengthBackground);
      //  iv_sensorSizeBackground = findViewById(R.id.iv_sensorSizeBackground);

        v_rectangleBackgroundUp = findViewById(R.id.v_rectangleBackgroundUp);
        v_rectangleLineUp = findViewById(R.id.v_rectangleLineUp);

        btn_calculate = findViewById(R.id.btn_calculate);

        //From here and out TextViews are added to the output ArrayList (should be invisible)
        tv_txtFov = findViewById(R.id.tv_txtFov);
        tv_txtIfov = findViewById(R.id.tv_txtIfov);
        tv_txtHfov = findViewById(R.id.tv_txtHfov);
        tv_txtVfov = findViewById(R.id.tv_txtVfov);
        tv_resIfov = findViewById(R.id.tv_resIfov);
        tv_resHfov = findViewById(R.id.tv_resHfov);
        tv_resVfov = findViewById(R.id.tv_resVfov);

        tv_txtTargetDri = findViewById(R.id.tv_txtTargetDri);
        tv_txtDet = findViewById(R.id.tv_txtDet);
        tv_txtRec = findViewById(R.id.tv_txtRec);
        tv_txtIdent = findViewById(R.id.tv_txtIdent);

        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);

        tv_txtHumanTarget = findViewById(R.id.tv_txtHumanTarget);
        tv_resHumanDet = findViewById(R.id.tv_resHumanDet);
        tv_resHumanRec = findViewById(R.id.tv_resHumanRec);
        tv_resHumanIdent = findViewById(R.id.tv_resHumanIdent);

        tv_txtObjTarget = findViewById(R.id.tv_txtObjTarget);
        tv_resObjDet = findViewById(R.id.tv_resObjDet);
        tv_resObjRec = findViewById(R.id.tv_resObjRec);

        tv_natoSize = findViewById(R.id.tv_natoSize);
        tv_humanSize = findViewById(R.id.tv_humanSize);
        tv_objSize = findViewById(R.id.tv_objSize);

        v_rectangleLineFov = findViewById(R.id.v_rectangleLineFov);
        v_rectangleLineDri = findViewById(R.id.v_rectangleLineDri);

        createOutputArrayList(); //Add the output TextViews to the ArrayList
        invisibleOutputTextView(); //Turn the output TextViews in the ArrayList to invisible

    }

    private void createOutputArrayList() { //Add the output TextViews to the ArrayList

        output.add(tv_txtFov);
        output.add(tv_txtIfov);
        output.add(tv_txtHfov);
        output.add(tv_txtVfov);
        output.add(tv_resIfov);
        output.add(tv_resHfov);
        output.add(tv_resVfov);

        output.add(tv_txtTargetDri);
        output.add(tv_txtDet);
        output.add(tv_txtRec);
        output.add(tv_txtIdent);

        output.add(tv_txtNatoTarget);
        output.add(tv_resNatoDet);
        output.add(tv_resNatoRec);
        output.add(tv_resNatoIdent);

        output.add(tv_txtHumanTarget);
        output.add(tv_resHumanDet);
        output.add(tv_resHumanRec);
        output.add(tv_resHumanIdent);

        output.add(tv_txtObjTarget);
        output.add(tv_resObjDet);
        output.add(tv_resObjRec);

        output.add(tv_natoSize);
        output.add(tv_humanSize);
        output.add(tv_objSize);




    }

    private void invisibleOutputTextView() { //Turn the output TextViews in the ArrayList to invisible

        for (TextView tv : output) {
            tv.setVisibility(View.INVISIBLE);
        }

    }

    private void visibleOutputTextView() { //Turn the output TextViews in the ArrayList to visible

        for (TextView tv : output) {
            tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Inflate the menu
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Open Settings menu above
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class)); //Move from this activity (MainActivity) to SettingActivity
                //finish(); //Closes MainActivity
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void isEmptyDefaultSettings() { //Create SharedPreferences files if haven't created yet, and set default settings to the files
        isEmptyLinePairDefaultSettings();
        isEmptyTargetSizeDefaultSettings();
    }

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
    }

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
    }

    private void SetDefaultSettings() { //Set default settings derived from SharedPreferences files to the objects
        SetLinePairDefaultSettingsToClass();
        SetTargetSizeDefaultSettingsToObjects();
    }

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

    }

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

    }


    private void hideKeyboardOnceBtnPressed() {    //hide keyboard once btn pressed

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(btn_calculate.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

}




