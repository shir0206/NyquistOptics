package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shirzabolotnyklein.nyquistoptics.Control.CalculationController;
import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteFileControl;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetDRIType;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetType;
import com.shirzabolotnyklein.nyquistoptics.R;

import java.util.HashMap;

public class CalcDRIActivity extends AppCompatActivity {

    EditText et_focalLength;
    EditText et_sensorPitch;
    TextView tv_txtNatoTargetWidth;
    TextView tv_txtNatoTargetHeight;
    TextView tv_txtHumanTargetWidth;
    TextView tv_txtHumanTargetHeight;
    TextView tv_txtObjectTargetWidth;
    TextView tv_txtObjectTargetHeight;
    TextView tv_resNatoDet;
    TextView tv_resNatoRec;
    TextView tv_resNatoIdent;
    TextView tv_resHumanDet;
    TextView tv_resHumanRec;
    TextView tv_resHumanIdent;
    TextView tv_resObjectDet;
    TextView tv_resObjectIdent;
    Button btn_calc;
    Vibrator vibrator;
    ReadWriteFileControl readWriteControll;

    ScrollView sv_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_calc_dri);
        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sv_output.setVisibility(View.INVISIBLE);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);



                if(isValid()) {
                    sv_output.setVisibility(View.VISIBLE);

                    Util.hideKeyboard(CalcDRIActivity.this);

                    CalculationController calculationController = new CalculationController();

                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());

                    setResTitles();


                    HashMap<TargetDRIType, Double> calculateDRI = calculationController.calculateDRI(sensorPitch, focalLength);


                    String natoDet = Util.formatDouble(calculateDRI.get(TargetDRIType.NatoDet),1);
                    String natoRec = Util.formatDouble(calculateDRI.get(TargetDRIType.NatoRec),1);
                    String natoIdent = Util.formatDouble(calculateDRI.get(TargetDRIType.NatoIdent),1);

                    String humanDet = Util.formatDouble(calculateDRI.get(TargetDRIType.HumanDet),1);
                    String humanRec = Util.formatDouble(calculateDRI.get(TargetDRIType.HumanRec),1);
                    String humanIdent = Util.formatDouble(calculateDRI.get(TargetDRIType.NatoIdent),1);

                    String objectDet = Util.formatDouble(calculateDRI.get(TargetDRIType.ObjectDet),1);
                    String objectIdent = Util.formatDouble(calculateDRI.get(TargetDRIType.ObjectIdent),1);


                    tv_resNatoDet.setText(natoDet);
                    tv_resNatoRec.setText(natoRec);
                    tv_resNatoIdent.setText(natoIdent);

                    tv_resHumanDet.setText(humanDet);
                    tv_resHumanRec.setText(humanRec);
                    tv_resHumanIdent.setText(humanIdent);

                    tv_resObjectDet.setText(objectDet);
                    tv_resObjectIdent.setText(objectIdent);

                }

            }
        });

    }

    private void setupUI() {

        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorPitch = findViewById(R.id.et_sensorPitch);

        tv_txtNatoTargetWidth = findViewById(R.id.tv_txtNatoTargetWidth);
        tv_txtNatoTargetHeight = findViewById(R.id.tv_txtNatoTargetHeight);

        tv_txtHumanTargetWidth = findViewById(R.id.tv_txtHumanTargetWidth);
        tv_txtHumanTargetHeight = findViewById(R.id.tv_txtHumanTargetHeight);

        tv_txtObjectTargetWidth = findViewById(R.id.tv_txtObjectTargetWidth);
        tv_txtObjectTargetHeight = findViewById(R.id.tv_txtObjectTargetHeight);

        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);

        tv_resHumanDet = findViewById(R.id.tv_resHumanDet);
        tv_resHumanRec = findViewById(R.id.tv_resHumanRec);
        tv_resHumanIdent = findViewById(R.id.tv_resHumanIdent);

        tv_resObjectDet = findViewById(R.id.tv_resObjectDet);
        tv_resObjectIdent = findViewById(R.id.tv_resObjectIdent);

        btn_calc = findViewById(R.id.btn_calc);

        sv_output = findViewById(R.id.sv_output);
    }

    private void setResTitles(){
        readWriteControll = new ReadWriteFileControl(getApplicationContext());
        readWriteControll.initReadDataFromFile();

        HashMap<TargetType, TargetSize> targetSizes = readWriteControll.getTargetSizesValues();

        tv_txtNatoTargetWidth.setText(String.valueOf((targetSizes.get(TargetType.NATO)).getWidth()));
        tv_txtNatoTargetHeight.setText(String.valueOf((targetSizes.get(TargetType.NATO)).getHeight()));

        tv_txtHumanTargetWidth.setText(String.valueOf((targetSizes.get(TargetType.HUMAN)).getWidth()));
        tv_txtHumanTargetHeight.setText(String.valueOf((targetSizes.get(TargetType.HUMAN)).getHeight()));

        tv_txtObjectTargetWidth.setText(String.valueOf((targetSizes.get(TargetType.OBJECT)).getWidth()));
        tv_txtObjectTargetHeight.setText(String.valueOf((targetSizes.get(TargetType.OBJECT)).getHeight()));

    }

    private boolean isValid() {

        String msg = " is missing.";
        String value = "";

        if (et_focalLength.getText().toString().equals("")){
            value = "Focal length";
            Toast.makeText(CalcDRIActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_sensorPitch.getText().toString().equals("")) {
            value = "Sensor pitch";
            Toast.makeText(CalcDRIActivity.this, value + msg, Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }
}