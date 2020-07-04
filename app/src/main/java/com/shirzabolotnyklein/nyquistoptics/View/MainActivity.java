package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shirzabolotnyklein.nyquistoptics.Control.MainAppController;
import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteToFileController;
import com.shirzabolotnyklein.nyquistoptics.Model.FovType;
import com.shirzabolotnyklein.nyquistoptics.R;
import com.shirzabolotnyklein.nyquistoptics.Model.SensorSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Serializable {

    View v_rectangleBackgroundUp; // Initialize background for activity design
    View v_rectangleLineUp; // Initialize lines for activity design

    TableLayout t_tableInput; // Initialize input table

    ImageView iv_sensorPitch, iv_focalLength, iv_sensorSize; // Initialize inputs icons
    TextView tv_txtSensorPitch, tv_txtFocalLength, tv_txtSensorSize, tv_txtSensorSizeX; // Initialize inputs titles
    EditText et_sensorPitch, et_focalLength, et_sensorSizeW, et_sensorSizeH; // Initialize inputs values

    Button btn_calculate; //  Initialize calculate button

    View v_rectangleLineFov, v_rectangleLineDri; // Initialize lines for activity design

    TableLayout t_tableFov, t_tableTargetDri; // Initialize 2 output tables

    TextView tv_txtFov; // Initialize table title
    TextView tv_txtIfov, tv_txtHfov, tv_txtVfov; // Initialize outputs titles
    TextView tv_resIfov, tv_resHfov, tv_resVfov; // Initialize outputs results

    TextView tv_txtTargetDri; // Initialize table title
    TextView tv_txtNatoTarget, tv_txtHumanTarget, tv_txtObjTarget; // Initialize target titles
    TextView tv_natoSize, tv_humanSize, tv_objSize; // Initialize target sizes titles
    TextView tv_txtDet, tv_txtRec, tv_txtIdent; // Initialize target DRI titles
    TextView tv_resNatoDet, tv_resNatoRec, tv_resNatoIdent; // Initialize outputs results
    TextView tv_resHumanDet, tv_resHumanRec, tv_resHumanIdent; // Initialize outputs results
    TextView tv_resObjDet, tv_resObjRec; // Initialize outputs results

    ArrayList<View> lineOutput = new ArrayList<>(); // Initialize output array to hide all lines for design before bottom click
    ArrayList<TableLayout> tableOutput = new ArrayList<>(); // Initialize output array to hide all tables output before bottom click
    ArrayList<TextView> textViewOutput = new ArrayList<>(); // Initialize output array to hide all TextViews output before bottom click



    Vibrator vibrator;
    ReadWriteToFileController setUp;
    MainAppController mainControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide keyboard on start up app
        hideKeyboardOnStartUp();
        //Create SharedPreferences files if haven't created yet, and set default settings to the files
        //isEmptyDefaultSettings();
        //Set default settings derived from SharedPreferences files to the objects
        //SetDefaultSettings();

        setUp=new ReadWriteToFileController(getApplicationContext());
        setUp.initReadDataFromFile();
        mainControl=new MainAppController(getApplicationContext());


        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                // If all fields are not filled, toast a massage to fill all fields
                if (et_focalLength.getText().toString().isEmpty()
                        || et_sensorPitch.getText().toString().isEmpty()
                        || et_sensorSizeW.getText().toString().isEmpty()
                        || et_sensorSizeH.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // If all fields are filled, continue
                else {

                    // Hide keyboard once button pressed
                    hideKeyboardOnceBtnPressed();



                    //input
                    String sensorPitch = null,focalLength=null,sensorSizeH=null,sensorSizeW=null;
                    //output
                    HashMap<FovType,String>result=mainControl.calculateFOV(sensorPitch,focalLength,sensorSizeH,sensorSizeW);



                    mainControl.calculateTargetsDRI();


                    // Turn the output  visible
                    turnVisible();

                    // Create a toast message calculated successfully
                    Toast.makeText(MainActivity.this, "Calculated successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    //------------------------------------- Setup Methods -------------------------------------

    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {

        v_rectangleBackgroundUp = findViewById(R.id.v_rectangleBackgroundUp);
        v_rectangleLineUp = findViewById(R.id.v_rectangleLineUp);

        t_tableInput = findViewById(R.id.t_tableInput);

        iv_sensorPitch = findViewById(R.id.iv_sensorPitch);
        iv_focalLength = findViewById(R.id.iv_focalLength);
        iv_sensorSize = findViewById(R.id.iv_sensorSize);
        tv_txtSensorPitch = findViewById(R.id.tv_txtSensorPitch);

        tv_txtFocalLength = findViewById(R.id.tv_txtFocalLength);
        tv_txtSensorSize = findViewById(R.id.tv_txtSensorSize);
        tv_txtSensorSizeX = findViewById(R.id.tv_txtSensorSizeX);

        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorSizeW = findViewById(R.id.et_sensorSizeW);
        et_sensorSizeH = findViewById(R.id.et_sensorSizeH);

        btn_calculate = findViewById(R.id.btn_calculate);

        // From here and out all the object are added to arrays in order to set invisible

        v_rectangleLineFov = findViewById(R.id.v_rectangleLineFov);
        v_rectangleLineDri = findViewById(R.id.v_rectangleLineDri);

        t_tableFov = findViewById(R.id.t_tableFov);
        t_tableTargetDri = findViewById(R.id.t_tableTargetDri);

        tv_txtFov = findViewById(R.id.tv_txtFov);
        tv_txtIfov = findViewById(R.id.tv_txtIfov);
        tv_txtHfov = findViewById(R.id.tv_txtHfov);
        tv_txtVfov = findViewById(R.id.tv_txtVfov);
        tv_resIfov = findViewById(R.id.tv_resIfov);
        tv_resHfov = findViewById(R.id.tv_resHfov);
        tv_resVfov = findViewById(R.id.tv_resVfov);

        tv_txtTargetDri = findViewById(R.id.tv_txtTargetDri);
        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tv_txtHumanTarget = findViewById(R.id.tv_txtHumanTarget);
        tv_txtObjTarget = findViewById(R.id.tv_txtObjTarget);
        tv_natoSize = findViewById(R.id.tv_natoSize);
        tv_humanSize = findViewById(R.id.tv_humanSize);
        tv_objSize = findViewById(R.id.tv_objSize);
        tv_txtDet = findViewById(R.id.tv_txtDet);
        tv_txtRec = findViewById(R.id.tv_txtRec);
        tv_txtIdent = findViewById(R.id.tv_txtIdent);
        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);
        tv_resHumanDet = findViewById(R.id.tv_resHumanDet);
        tv_resHumanRec = findViewById(R.id.tv_resHumanRec);
        tv_resHumanIdent = findViewById(R.id.tv_resHumanIdent);
        tv_resObjDet = findViewById(R.id.tv_resObjDet);
        tv_resObjRec = findViewById(R.id.tv_resObjRec);

        createArrays();
        turnInvisible();

    }

    /**
     * Create array lists for all output Views, all output Tables & all output TextViews
     */
    private void createArrays() {
        createLineOutputArrayList();
        createTableOutputArrayList();
        createOutputArrayList();
    }

    /**
     * Turn invisible all array lists of all output Views, all output Tables & all output TextViews
     */
    private void turnInvisible() {
        invisibleLineOutput();
        invisibleTableOutput();
        invisibleTextViewOutput();
    }

    /**
     * Turn visible all array lists of all output Views, all output Tables & all output TextViews
     */
    private void turnVisible() {
        visibleLineOutput();
        visibleTableOutput();
        visibleTextViewOutput();
    }

    /**
     * Create an array list out of all output Views
     */
    private void createLineOutputArrayList () {
        lineOutput.add(v_rectangleLineFov);
        lineOutput.add(v_rectangleLineDri);
    }

    /**
     * Turn all the objects in the array list of all output Views to invisible
     */
    private void invisibleLineOutput() {
        for (View currentLineOutput : lineOutput) {
            currentLineOutput.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Turn all the objects in the array list of all output Views to visible
     */
    private void visibleLineOutput() {
        for (View currentLineOutput : lineOutput) {
            currentLineOutput.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Create an array list out of all output Tables
     */
    private void createTableOutputArrayList () {
        tableOutput.add(t_tableFov);
        tableOutput.add(t_tableTargetDri);
    }

    /**
     * Turn all the objects in the array list of all output Tables to invisible
     */
    private void invisibleTableOutput() {
        for (TableLayout currentTableOutput : tableOutput) {
            currentTableOutput.setVisibility(View.GONE);
        }
    }

    /**
     * Turn all the objects in the array list of all output Tables to invisible
     */
    private void visibleTableOutput() {
        for (TableLayout currentTableOutput : tableOutput) {
            currentTableOutput.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Create an array list out of all output TextViews
     */
    private void createOutputArrayList() {

        textViewOutput.add(tv_txtFov);

        textViewOutput.add(tv_txtIfov);
        textViewOutput.add(tv_txtHfov);
        textViewOutput.add(tv_txtVfov);

        textViewOutput.add(tv_resIfov);
        textViewOutput.add(tv_resHfov);
        textViewOutput.add(tv_resVfov);

        textViewOutput.add(tv_txtTargetDri);

        textViewOutput.add(tv_txtNatoTarget);
        textViewOutput.add(tv_txtHumanTarget);
        textViewOutput.add(tv_txtObjTarget);

        textViewOutput.add(tv_natoSize);
        textViewOutput.add(tv_humanSize);
        textViewOutput.add(tv_objSize);

        textViewOutput.add(tv_txtDet);
        textViewOutput.add(tv_txtRec);
        textViewOutput.add(tv_txtIdent);

        textViewOutput.add(tv_resNatoDet);
        textViewOutput.add(tv_resNatoRec);
        textViewOutput.add(tv_resNatoIdent);

        textViewOutput.add(tv_resHumanDet);
        textViewOutput.add(tv_resHumanRec);
        textViewOutput.add(tv_resHumanIdent);

        textViewOutput.add(tv_resObjDet);
        textViewOutput.add(tv_resObjRec);

    }

    /**
     * Turn all the objects in the array list of all output TextViews to invisible
     */
    private void invisibleTextViewOutput() {
        for (TextView currentTextViewOutput : textViewOutput) {
            currentTextViewOutput.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Turn all the objects in the array list of all output TextViews to visible
     */
    private void visibleTextViewOutput() {
        for (TextView currentTextViewOutput : textViewOutput) {
            currentTextViewOutput.setVisibility(View.VISIBLE);
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
        imm.hideSoftInputFromWindow(btn_calculate.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

}




