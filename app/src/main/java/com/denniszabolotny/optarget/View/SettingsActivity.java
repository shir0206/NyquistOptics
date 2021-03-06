package com.denniszabolotny.optarget.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.denniszabolotny.optarget.Control.ReadWriteFileControl;
import com.denniszabolotny.optarget.Model.Detector;
import com.denniszabolotny.optarget.Model.LinePair;
import com.denniszabolotny.optarget.Model.TargetSize;
import com.denniszabolotny.optarget.Model.TargetType;
import com.denniszabolotny.optarget.R;
import com.denniszabolotny.optarget.Utils.Util;

import java.util.HashMap;


public class SettingsActivity extends AppCompatActivity {

    Button btn_def, btn_save;
    TextView tv_lp;
    TextView tv_lpDet, tv_lpDetObj, tv_lpIdent, tv_lpRec;
    EditText et_lpDet, et_lpDetObj, et_lpIdent, et_lpRec;
    TextView tv_targetSize;
    TextView tv_txtHumanTargetSize, tv_txtNatoTargetSize, tv_txtObjTargetSize;
    TextView tv_natoTargetSizeX, tv_humanTargetSizeX, tv_objTargetSizeX;
    EditText et_natoTargetW, et_natoTargetH;
    EditText et_humanTargetW, et_humanTargetH;
    EditText et_objTargetW, et_objTargetH;
    EditText et_detectorSizeW,et_detectorSizeH;
    EditText et_detecorPitch;
    Vibrator vibrator;
    ReadWriteFileControl readWriteControll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.act_settings);
        readWriteControll = new ReadWriteFileControl(getApplicationContext());
        readWriteControll.initReadDataFromFile();


        // Set up UI
        setupUI();
        insertDataToViews();

       // Hide keyboard on start up app
        hideKeyboardOnStartUp();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                Util.hideKeyboard(SettingsActivity.this);


                // Refresh the current settings and set to default
                 readWriteControll.restoreDefaultSettings();


                // Display the default settings
                insertDataToViews();


                //function to parse the data in to the tv and
                Toast.makeText(SettingsActivity.this, "Settings reset to default", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(SettingsActivity.this, MainActivity.class));

                //Close SettingActivity
                finish();
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

                    Util.hideKeyboard(SettingsActivity.this);


                    // Set the user settings to settings in SharedPreferences files
                    readWriteControll.SaveNewLinePairSettings(et_lpDet.getText().toString(),et_lpRec.getText().toString(),et_lpIdent.getText().toString(),et_lpDetObj.getText().toString());
                    readWriteControll.SaveNewTargetSizeSettings(et_natoTargetW.getText().toString(),et_natoTargetH.getText().toString(),et_humanTargetW.getText().toString(), et_humanTargetH.getText().toString(),et_objTargetW.getText().toString(), et_objTargetH.getText().toString());
                    readWriteControll.SaveNewDetectorSettings(et_detectorSizeH.getText().toString(),et_detectorSizeW.getText().toString(),et_detecorPitch.getText().toString());
                    Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();

                    // Move from this activity (SettingsActivity) to MainActivity
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));

                    //Close SettingActivity
                    finish();

                }
            }

        });

    }

    private void insertDataToViews(){
        LinePair lp=readWriteControll.getLinePairValues();
        HashMap<TargetType, TargetSize> targetSizes=readWriteControll.getTargetSizesValues();
        Detector detector=readWriteControll.getDetectorValues();

        et_lpDet.setText(String.valueOf(lp.getLpDet()));
        et_lpDetObj.setText(String.valueOf(lp.getLpDetObj()));
        et_lpRec.setText(String.valueOf(lp.getLpRec()));
        et_lpIdent.setText(String.valueOf(lp.getLpIdent()));

        et_natoTargetW.setText(String.valueOf((targetSizes.get(TargetType.NATO)).getWidth()));
        et_natoTargetH.setText(String.valueOf((targetSizes.get(TargetType.NATO)).getHeight()));

        et_humanTargetW.setText(String.valueOf((targetSizes.get(TargetType.HUMAN)).getWidth()));
        et_humanTargetH.setText(String.valueOf((targetSizes.get(TargetType.HUMAN)).getHeight()));

        et_objTargetW.setText(String.valueOf((targetSizes.get(TargetType.OBJECT)).getWidth()));
        et_objTargetH.setText(String.valueOf((targetSizes.get(TargetType.OBJECT)).getHeight()));

        et_detectorSizeH.setText(String.valueOf(detector.getDetectorSizeH()));
        et_detectorSizeW.setText(String.valueOf(detector.getDetectorSizeW()));
        et_detecorPitch.setText((String.valueOf(detector.getDetectorPitch())));
    }
    //------------------------------------- Setup Methods -------------------------------------


    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {

        btn_def = findViewById(R.id.btn_def);
        btn_save = findViewById(R.id.btn_save);

        tv_lp = findViewById(R.id.tv_txtLinePair);

        tv_lpDet = findViewById(R.id.tv_txtLinePairDet);
        tv_lpDetObj = findViewById(R.id.tv_txtLinePairDetObj);
        tv_lpIdent = findViewById(R.id.tv_txtLinePairIdent);
        tv_lpRec = findViewById(R.id.tv_txtLinePairRec);

        et_lpDet = findViewById(R.id.et_linePairDet);
        et_lpDetObj = findViewById(R.id.et_linePairDetObj);
        et_lpIdent = findViewById(R.id.et_linePairIdent);
        et_lpRec = findViewById(R.id.et_linePairRec);

        tv_targetSize = findViewById(R.id.tv_txtTargetSize);

        tv_txtHumanTargetSize = findViewById(R.id.tv_txtTargetSizeHuman);
        tv_txtNatoTargetSize = findViewById(R.id.tv_txtTargetSizeNato);
        tv_txtObjTargetSize = findViewById(R.id.tv_txtTargetSizeObj);

        tv_natoTargetSizeX = findViewById(R.id.tv_txtTargetSizeNatoX);
        tv_humanTargetSizeX = findViewById(R.id.tv_txtTargetSizeHumanX);
        tv_objTargetSizeX = findViewById(R.id.tv_txtTargetSizeObjX);

        et_natoTargetW = findViewById(R.id.et_targetSizeNatoW);
        et_natoTargetH = findViewById(R.id.et_targetSizeNatoH);

        et_humanTargetW = findViewById(R.id.et_targetSizeHumanW);
        et_humanTargetH = findViewById(R.id.et_targetSizeHumanH);

        et_objTargetW = findViewById(R.id.et_targetSizeObjW);
        et_objTargetH = findViewById(R.id.et_targetSizeObjH);
        et_detectorSizeW=findViewById(R.id.et_detectorSizeW);
        et_detectorSizeH=findViewById(R.id.et_detectorSizeH);
        et_detecorPitch=findViewById(R.id.et_detectorPitch);
        Util.SetActionBarICon(getSupportActionBar());
    }


    //------------------------------------- Android Methods -------------------------------------

    /**
     * Hide keyboard on start up app
     */
    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}