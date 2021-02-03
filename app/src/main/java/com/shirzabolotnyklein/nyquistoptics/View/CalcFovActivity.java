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
import com.shirzabolotnyklein.nyquistoptics.Model.FovType;
import com.shirzabolotnyklein.nyquistoptics.R;
import com.shirzabolotnyklein.nyquistoptics.Utils.Util;

import java.util.HashMap;

public class CalcFovActivity extends AppCompatActivity {


    EditText et_focalLength;
    EditText et_sensorPitch;
    EditText et_txtDimensionW;
    EditText et_txtDimensionH;
    TextView tv_resHfov;
    TextView tv_resVfov;
    TextView tv_resIfov;
    Button btn_calc;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calc_fov);

        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);


                if(isValid()) {

                    Util.hideKeyboard(CalcFovActivity.this);

                    MainAppController mainAppController = new MainAppController();

                    String focalLength = (et_focalLength.getText().toString());
                    String sensorPitch = (et_sensorPitch.getText().toString());
                    String dimensionW  = (et_txtDimensionW.getText().toString());
                    String dimensionH  = (et_txtDimensionH.getText().toString());

                    HashMap<FovType,String> res=mainAppController.calcFOV(sensorPitch,focalLength,dimensionH,dimensionW);

                    String hfov =Util.formatDouble(Double.parseDouble(res.get(FovType.HFOV)),2);
                    String vfov = Util.formatDouble(Double.parseDouble(res.get(FovType.VFOV)),2);
                    String ifov = Util.formatDouble(Double.parseDouble(res.get(FovType.IFOV)),2);

                    tv_resHfov.setText(hfov);
                    tv_resVfov.setText(vfov);
                    tv_resIfov.setText(ifov);

                }

            }
        });

    }

    private void setupUI() {

        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_txtDimensionW = findViewById(R.id.et_txtDimensionW);
        et_txtDimensionH = findViewById(R.id.et_txtDimensionH);
        tv_resHfov = findViewById(R.id.tv_resHfov);
        tv_resVfov = findViewById(R.id.tv_resVfov);
        tv_resIfov = findViewById(R.id.tv_resIfov);
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
            Toast.makeText(CalcFovActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_sensorPitch.getText().toString().equals("")) {
            value = "Sensor pitch";
            Toast.makeText(CalcFovActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionW.getText().toString().equals("")) {
            value = "Dimension width";
            Toast.makeText(CalcFovActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionH.getText().toString().equals("")) {
            value = "Dimension height";
            Toast.makeText(CalcFovActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}