package com.denniszabolotny.optarget.View;

import android.content.Context;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denniszabolotny.optarget.Control.MainAppController;
import com.denniszabolotny.optarget.Control.ReadWriteFileControl;
import com.denniszabolotny.optarget.Model.Detector;
import com.denniszabolotny.optarget.R;
import com.denniszabolotny.optarget.Utils.Util;

public class CalcFocalLengthFOV extends AppCompatActivity {


    EditText et_txtHfov;
    EditText et_txtDimensionW;
    EditText et_txtDimensionH;
    EditText et_sensorPitch;
    TextView tv_resFocalLengthFov;
    Button btn_calc;
    Vibrator vibrator;
    MainAppController mainAppController;
    LinearLayout resLinearLayout;
    ReadWriteFileControl readWriteFileControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_calc_focal_length_fov);
        mainAppController=new MainAppController();
        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        resLinearLayout.setVisibility(View.INVISIBLE);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);


                if(isValid()) {

                    Util.hideKeyboard(CalcFocalLengthFOV.this);

                    double hfov = Double.parseDouble(et_txtHfov.getText().toString());
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double dimensionW  = Double.parseDouble(et_txtDimensionW.getText().toString());
                    double dimensionH  = Double.parseDouble(et_txtDimensionH.getText().toString());


                    String focalLengthFov = Util.formatDouble(mainAppController.calculateFocalLengthViaHfov(dimensionW,
                     dimensionH,
                     hfov,
                     sensorPitch),2);

                    tv_resFocalLengthFov.setText(focalLengthFov);
                    resLinearLayout.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void setupUI() {

        et_txtHfov = findViewById(R.id.et_txtHfov);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_txtDimensionW = findViewById(R.id.et_txtDimensionW);
        et_txtDimensionH = findViewById(R.id.et_txtDimensionH);
        tv_resFocalLengthFov = findViewById(R.id.tv_resFocalLengthFov);
        btn_calc = findViewById(R.id.btn_calc);
        resLinearLayout=findViewById(R.id.linearLayout2);
        readWriteFileControl=new ReadWriteFileControl(this);

        Detector values=readWriteFileControl.getDetectorValues();

        et_sensorPitch.setText(String.valueOf(values.getDetectorPitch()));
        et_txtDimensionH.setText(String.valueOf(values.getDetectorSizeH()));
        et_txtDimensionW.setText(String.valueOf(values.getDetectorSizeW()));

        Util.SetActionBarICon(getSupportActionBar());
    }

    private boolean isValid() {

        String msg = " is missing.";
        String value = "";

        if (et_txtHfov.getText().toString().equals("")){
            value = "HFOV";
            Toast.makeText(CalcFocalLengthFOV.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_sensorPitch.getText().toString().equals("")) {
            value = "Sensor pitch";
            Toast.makeText(CalcFocalLengthFOV.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionW.getText().toString().equals("")) {
            value = "Dimension width";
            Toast.makeText(CalcFocalLengthFOV.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_txtDimensionH.getText().toString().equals("")) {
            value = "Dimension height";
            Toast.makeText(CalcFocalLengthFOV.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}