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
import com.shirzabolotnyklein.nyquistoptics.R;

public class CalcDimensionActivity extends AppCompatActivity {

    Button btn_calc;
    EditText et_focalLength;
    EditText et_sensorPitch;
    EditText et_targetRange;
    EditText et_resTargetSizeW;
    EditText et_resTargetSizeH;
    TextView tv_resDimensionW;
    TextView tv_dimensionX;
    TextView tv_resDimensionH;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calc_dimension);

        // Set up UI
        setupUI();

        tv_dimensionX.setVisibility(View.INVISIBLE);

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                if(isValid()) {

                    CalculationController calculationController = new CalculationController();

                    double targetSizeW = Double.parseDouble(et_resTargetSizeW.getText().toString());
                    double targetSizeH = Double.parseDouble(et_resTargetSizeH.getText().toString());
                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double targetRange = Double.parseDouble(et_targetRange.getText().toString());

                    String dimensionW = Double.toString(calculationController.calcDimensionWidth(targetSizeW,focalLength,sensorPitch,targetRange));
                    String dimensionH = Double.toString(calculationController.calcDimensionHeight(targetSizeH,focalLength,sensorPitch,targetRange));

                    tv_resDimensionW.setText(dimensionW);
                    tv_resDimensionH.setText(dimensionH);
                    tv_dimensionX.setVisibility(View.VISIBLE);

                }

            }
    });

    }

    private void setupUI() {

        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_targetRange = findViewById(R.id.et_targetRange);
        et_resTargetSizeW = findViewById(R.id.et_resTargetSizeW);
        et_resTargetSizeH = findViewById(R.id.et_resTargetSizeH);
        tv_resDimensionW = findViewById(R.id.tv_resDimensionW);
        tv_dimensionX = findViewById(R.id.tv_dimensionX);
        tv_resDimensionH = findViewById(R.id.tv_resDimensionH);
        btn_calc = findViewById(R.id.btn_calc);
    }

    private boolean isValid() {

        String msg = " is missing.";
        String value = "";

        if (et_focalLength.getText().toString().equals("")){
            value = "Focal length";
            Toast.makeText(CalcDimensionActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_sensorPitch.getText().toString().equals("")) {
            value = "Sensor pitch";
            Toast.makeText(CalcDimensionActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_targetRange.getText().toString().equals("")) {
            value = "Target range";
            Toast.makeText(CalcDimensionActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_resTargetSizeW.getText().toString().equals("")) {
            value = "Target width";
            Toast.makeText(CalcDimensionActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (et_resTargetSizeH.getText().toString().equals("")) {
            value = "Target height";
            Toast.makeText(CalcDimensionActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}