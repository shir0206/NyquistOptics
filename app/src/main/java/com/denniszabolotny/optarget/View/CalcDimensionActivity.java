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

public class CalcDimensionActivity extends AppCompatActivity {

    Button btn_calc;
    EditText et_focalLength;
    EditText et_sensorPitch;
    EditText et_targetRange;
    EditText et_resTargetSizeW;
    EditText et_resTargetSizeH;
    LinearLayout table;
    TextView tv_DetectorSizeWidth;
    TextView tv_DetectorSizeHeight;
    TextView tv_DetectorSizeTotal;
    Vibrator vibrator;
    ReadWriteFileControl readWriteFileControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calc_dimension);

        // Set up UI
        setupUI();

        table.setVisibility(View.INVISIBLE);

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);

                if(isValid()) {

                    Util.hideKeyboard(CalcDimensionActivity.this);


                    MainAppController mainAppController = new MainAppController();

                    double targetSizeW = Double.parseDouble(et_resTargetSizeW.getText().toString());
                    double targetSizeH = Double.parseDouble(et_resTargetSizeH.getText().toString());
                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double targetRange = Double.parseDouble(et_targetRange.getText().toString());

                    double dimensionWRes=mainAppController.calcDimension(targetSizeW,focalLength,sensorPitch,targetRange);
                    double dimensionHRes=mainAppController.calcDimension(targetSizeH,focalLength,sensorPitch,targetRange);

                    double totalPixelSize= dimensionWRes*dimensionHRes;

                    String dimensionW = Util.formatDouble(dimensionWRes,1);
                    String dimensionH = Util.formatDouble(dimensionHRes,1);
                    String dimensionTotal=Util.formatDouble(totalPixelSize,1);


                    tv_DetectorSizeHeight.setText(dimensionH);
                    tv_DetectorSizeWidth.setText(dimensionW);
                    tv_DetectorSizeTotal.setText(dimensionTotal);
                    table.setVisibility(View.VISIBLE);

                }

            }
    });

    }

    private void setupUI() {
        table=findViewById(R.id.table_DetectorSize);
        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_targetRange = findViewById(R.id.et_targetRange);
        et_resTargetSizeW = findViewById(R.id.et_resTargetSizeW);
        et_resTargetSizeH = findViewById(R.id.et_resTargetSizeH);
        tv_DetectorSizeWidth = findViewById(R.id.tv_DetectorSizeWidth);
        tv_DetectorSizeHeight=findViewById(R.id.tv_DetectorSizeHeight);
        tv_DetectorSizeTotal=findViewById(R.id.tv_DetectorSizeTotal);
        btn_calc = findViewById(R.id.btn_calc);
        readWriteFileControl=new ReadWriteFileControl(this);
        Detector values=readWriteFileControl.getDetectorValues();
        et_sensorPitch.setText(String.valueOf(values.getDetectorPitch()));

        Util.SetActionBarICon(getSupportActionBar());
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