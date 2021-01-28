package com.shirzabolotnyklein.nyquistoptics.View;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.shirzabolotnyklein.nyquistoptics.Control.MainAppController;
import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteFileControl;
import com.shirzabolotnyklein.nyquistoptics.R;

import java.io.Serializable;
import java.util.ArrayList;

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
    Dialog focalLenInputType;

    Button btn_fov;
    Button btn_dimension;
    Button btn_targetSize;
    Button btn_settings;
    Button btn_focalLength;
    Button btn_dri;

    Vibrator vibrator;
    ReadWriteFileControl setUp;
    MainAppController mainControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        focalLenInputType = new Dialog(this);


        //must init the ... to get latest data from the shared prefrences
        setUp = new ReadWriteFileControl(getApplicationContext());
        setUp.initReadDataFromFile();



        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        btn_fov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcFovActivity.class);
                startActivity(intent);
            }
        });

        btn_dimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcDimensionActivity.class);
                startActivity(intent);
            }
        });

        btn_targetSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcTargetSizeActivity.class);
                startActivity(intent);
            }
        });

        btn_dri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcDRIActivity.class);
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_focalLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);

                showPopUp(view);

            }
        });

    }


    //------------------------------------- Setup Methods -------------------------------------

    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {


        btn_fov = findViewById(R.id.btn_fov);
        btn_dimension = findViewById(R.id.btn_dimension);
        btn_targetSize = findViewById(R.id.btn_targetSize);
        btn_settings = findViewById(R.id.btn_settings);
        btn_focalLength = findViewById(R.id.btn_focalLength);
        btn_dri = findViewById(R.id.btn_dri);

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
    private void createLineOutputArrayList() {
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
    private void createTableOutputArrayList() {
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
     *
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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                //finish(); //Closes MainActivity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void showPopUp(View v) {
        Button calculateTargetInput;
        Button FOVInput;
        focalLenInputType.setContentView(R.layout.act_home_focal_popup);

        calculateTargetInput = focalLenInputType.findViewById(R.id.btn_TargetInput);
        FOVInput = focalLenInputType.findViewById(R.id.btn_FOVInput);

        calculateTargetInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CalcFocalLengthTarget.class);
                startActivity(intent);
                focalLenInputType.dismiss();
            }
        });

        FOVInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CalcFocalLengthFOV.class);
                startActivity(intent);
                focalLenInputType.dismiss();

            }
        });
        //Set the size of the window
        focalLenInputType.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //Open the window
        focalLenInputType.show();
    }

}




