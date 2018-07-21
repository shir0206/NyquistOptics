package com.example.shir.nyquistoptics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv_txtNatoIdent;
    TextView tv_resNatoIdent;
    TextView tv_txtNatoRec;
    TextView tv_resNatoRec;
    TextView tv_txtNatoDet;
    TextView tv_resNatoDet;
    TextView tv_txtNatoTarget;
    TextView tv_txtVfov;
    TextView tv_resVfov;
    TextView tv_txtFocalLength;
    TextView tv_txtSensorPitch;
    TextView tv_txtSensorSizeW;
    TextView tv_order;
    TextView tv_txtSensorSizeH;
    TextView tv_txtIfov;
    TextView tv_resIfov;
    TextView tv_txtHfov;
    TextView tv_resHfov;

    EditText et_sensorSizeH;
    EditText et_sensorSizeW;
    EditText et_focalLength;
    EditText et_sensorPitch;

    Button btn_calculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_focalLength.getText().toString().isEmpty() || et_sensorPitch.getText().toString().isEmpty() || et_sensorSizeW.getText().toString().isEmpty() || et_sensorSizeH.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });











    }

    private void setupUI() {
        tv_txtNatoIdent = findViewById(R.id.tv_txtNatoIdent);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);
        tv_txtNatoRec = findViewById(R.id.tv_txtNatoRec);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_txtNatoDet = findViewById(R.id.tv_txtNatoDet);
        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tv_txtVfov = findViewById(R.id.tv_txtVfov);
        tv_resVfov = findViewById(R.id.tv_resVfov);
        tv_txtFocalLength = findViewById(R.id.tv_txtFocalLength);
        tv_txtSensorPitch = findViewById(R.id.tv_txtSensorPitch);
        tv_txtSensorSizeW = findViewById(R.id.tv_txtSensorSizeW);
        tv_order = findViewById(R.id.tv_order);
        tv_txtSensorSizeH = findViewById(R.id.tv_txtSensorSizeH);
        tv_txtIfov = findViewById(R.id.tv_txtIfov);
        tv_resIfov = findViewById(R.id.tv_resIfov);
        tv_txtHfov = findViewById(R.id.tv_txtHfov);
        tv_resHfov = findViewById(R.id.tv_resHfov);

        et_sensorSizeH = findViewById(R.id.et_sensorSizeH);
        et_sensorSizeW = findViewById(R.id.et_sensorSizeW);
        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);

        btn_calculate = findViewById(R.id.btn_calculate);
    }
}
