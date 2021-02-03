package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shirzabolotnyklein.nyquistoptics.Control.CalculationController;
import com.shirzabolotnyklein.nyquistoptics.Control.MainAppController;
import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteFileControl;
import com.shirzabolotnyklein.nyquistoptics.Model.Detector;
import com.shirzabolotnyklein.nyquistoptics.R;
import com.shirzabolotnyklein.nyquistoptics.Utils.Util;

public class CalcTargetSizeActivity extends AppCompatActivity {

    EditText et_focalLength;
    EditText et_sensorPitch;
    EditText et_targetRange;
    EditText et_txtDimensionW;
    EditText et_txtDimensionH;

    TextView tv_resTargetSizeW;
    TextView tv_resTargetSizeH;
    TextView tv_resTargetSizeX;
    Button btn_calc;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calc_target_size);


        // Set up UI
        setupUI();

        tv_resTargetSizeX.setVisibility(View.INVISIBLE);

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                if(isValid()) {

                    Util.hideKeyboard(CalcTargetSizeActivity.this);

                    MainAppController mainAppController = new MainAppController();

                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double targetRange = Double.parseDouble(et_targetRange.getText().toString());
                    double dimensionW  = Double.parseDouble(et_txtDimensionW.getText().toString());
                    double dimensionH  = Double.parseDouble(et_txtDimensionH.getText().toString());



                    String targetSizeW =Util.formatDouble(mainAppController.calcualteTargetSize(dimensionW, focalLength,sensorPitch, targetRange),2);


                    String targetSizeH= Util.formatDouble((mainAppController.calcualteTargetSize(dimensionH, focalLength,sensorPitch, targetRange)),2);

                    tv_resTargetSizeW.setText(targetSizeW);
                    tv_resTargetSizeH.setText(targetSizeH);
                    tv_resTargetSizeX.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    private void setupUI() {

        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_targetRange = findViewById(R.id.et_targetRange);
        et_txtDimensionW = findViewById(R.id.et_txtDimensionW);
        et_txtDimensionH = findViewById(R.id.et_txtDimensionH);
        tv_resTargetSizeW = findViewById(R.id.tv_resTargetSizeW);
        tv_resTargetSizeH = findViewById(R.id.tv_resTargetSizeH);
        tv_resTargetSizeX = findViewById(R.id.tv_resTargetSizeX);
        btn_calc = findViewById(R.id.btn_calc);

        ReadWriteFileControl readWriteFileControl=new ReadWriteFileControl(this);
        Detector values=readWriteFileControl.getDetectorValues();
        et_sensorPitch.setText(String.valueOf(values.getDetectorPitch()));
        et_txtDimensionH.setText(String.valueOf(values.getDetectorSizeH()));
        et_txtDimensionW.setText(String.valueOf(values.getDetectorSizeW()));

        Util.SetActionBarICon(getSupportActionBar());
    }

    private boolean isValid() {

        String msg = " is missing.";
        String value = "";

        if (et_focalLength.getText().toString().equals("")){
            value = "Focal length";
            Toast.makeText(CalcTargetSizeActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_sensorPitch.getText().toString().equals("")) {
            value = "Sensor pitch";
            Toast.makeText(CalcTargetSizeActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_targetRange.getText().toString().equals("")) {
            value = "Target range";
            Toast.makeText(CalcTargetSizeActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionW.getText().toString().equals("")) {
            value = "Dimension width";
            Toast.makeText(CalcTargetSizeActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionH.getText().toString().equals("")) {
            value = "Dimension height";
            Toast.makeText(CalcTargetSizeActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}