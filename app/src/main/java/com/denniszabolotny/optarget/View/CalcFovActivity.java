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
import com.denniszabolotny.optarget.Model.FovType;
import com.denniszabolotny.optarget.R;
import com.denniszabolotny.optarget.Utils.Util;

import java.util.HashMap;

public class CalcFovActivity extends AppCompatActivity {


    EditText et_focalLength;
    EditText et_sensorPitch;
    EditText et_txtDimensionW;
    EditText et_txtDimensionH;
    TextView tv_resHfov;
    TextView tv_resVfov;
    TextView tv_resIfov;
    LinearLayout ll_hfov,ll_vfov,ll_ifov;
    Button btn_calc;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.act_calc_fov);
        setTitle("Field of View Calculator");
        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ll_hfov.setVisibility(View.INVISIBLE);
        ll_vfov.setVisibility(View.INVISIBLE);
        ll_ifov.setVisibility(View.INVISIBLE);
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

                    tv_resHfov.setText(res.get(FovType.HFOV));
                    tv_resVfov.setText(res.get(FovType.VFOV));
                    tv_resIfov.setText(res.get(FovType.IFOV));

                    ll_hfov.setVisibility(View.VISIBLE);
                    ll_vfov.setVisibility(View.VISIBLE);
                    ll_ifov.setVisibility(View.VISIBLE);

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
        ll_hfov=findViewById(R.id.ll_hfov);
        ll_vfov=findViewById(R.id.ll_vfov);
        ll_ifov=findViewById(R.id.ll_ifov);
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