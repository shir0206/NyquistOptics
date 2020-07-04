package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteToFileController;

import com.shirzabolotnyklein.nyquistoptics.R;


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
    ReadWriteToFileController readWriteControll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_settings);
        readWriteControll=new ReadWriteToFileController(getApplicationContext());
        // Set up UI
        setupUI();

        // Hide keyboard on start up app
        hideKeyboardOnStartUp();

        // Display the settings from from SharedPreferences files in the activity.
        readWriteControll.displaySettings();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               // Vibrate
               vibrator.vibrate(50);

               // Refresh the current settings and set to default
               readWriteControll.SetDefaultSettings();

               // Display the default settings
               readWriteControll.displaySettings();

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
                    readWriteControll.saveSettings(view);
                    //saveSettings(view);

                    // Set default settings derived from SharedPreferences files to the class
                    //SetSettingsToClass(view);
                    readWriteControll.SetSettingsToClass(view);
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

//        btn_refresh = findViewById(R.id.btn_refresh);
//        btn_save = findViewById(R.id.btn_save);
//
//        v_rectangleBackgroundDown = findViewById(R.id.v_rectangleBackgroundDown);
//        v_rectangleBackgroundUp = findViewById(R.id.v_rectangleBackgroundUp);
//        v_rectangleLineDown = findViewById(R.id.v_rectangleLineDown);
//        v_rectangleLineLp = findViewById(R.id.v_rectangleLineLp);
//
//
//        tv_lp = findViewById(R.id.tv_lp);
//
//        tv_lpDet = findViewById(R.id.tv_lpDet);
//        tv_lpDetObj = findViewById(R.id.tv_lpDetObj);
//        tv_lpIdent = findViewById(R.id.tv_lpIdent);
//        tv_lpRec = findViewById(R.id.tv_lpRec);
//
//        et_lpDet = findViewById(R.id.et_lpDet);
//        et_lpDetObj = findViewById(R.id.et_lpDetObj);
//        et_lpIdent = findViewById(R.id.et_lpIdent);
//        et_lpRec = findViewById(R.id.et_lpRec);
//
//        tv_targetSize = findViewById(R.id.tv_targetSize);
//
//        tv_txtHumanTargetSize = findViewById(R.id.tv_txtHumanTargetSize);
//        tv_txtNatoTargetSize = findViewById(R.id.tv_txtNatoTargetSize);
//        tv_txtObjTargetSize = findViewById(R.id.tv_txtObjTargetSize);
//
//        tv_natoTargetSizeX = findViewById(R.id.tv_natoTargetSizeX);
//        tv_humanTargetSizeX = findViewById(R.id.tv_humanTargetSizeX);
//        tv_objTargetSizeX = findViewById(R.id.tv_objTargetSizeX);
//
//        et_natoTargetW = findViewById(R.id.et_natoTargetW);
//        et_natoTargetH = findViewById(R.id.et_natoTargetH);
//
//        et_humanTargetW = findViewById(R.id.et_humanTargetW);
//        et_humanTargetH = findViewById(R.id.et_humanTargetH);
//
//        et_objTargetW = findViewById(R.id.et_objTargetW);
//        et_objTargetH = findViewById(R.id.et_objTargetH);

    }


    //------------------------------------- Android Methods -------------------------------------

    /**
     * Hide keyboard on start up app
     */
    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}