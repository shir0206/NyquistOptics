package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shirzabolotnyklein.nyquistoptics.Control.CalculationController;
import com.shirzabolotnyklein.nyquistoptics.Control.MainAppController;
import com.shirzabolotnyklein.nyquistoptics.Control.ReadWriteFileControl;
import com.shirzabolotnyklein.nyquistoptics.Model.Detector;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetDRIType;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetSize;
import com.shirzabolotnyklein.nyquistoptics.Model.TargetType;
import com.shirzabolotnyklein.nyquistoptics.R;
import com.shirzabolotnyklein.nyquistoptics.Utils.Util;

import java.util.HashMap;

public class CalcDRIActivity extends AppCompatActivity {

    EditText et_focalLength;
    EditText et_sensorPitch;
    TextView tv_txtNatoTarget;
    TextView tv_txtHumanTarget;
    TextView tv_txtObjectTarget;
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
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_calc_dri_table);
        // Set up UI
        readWriteControll = new ReadWriteFileControl(getApplicationContext());
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        tableLayout.setVisibility(View.INVISIBLE);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Vibrate
                vibrator.vibrate(50);



                if(isValid()) {

                    tableLayout.setVisibility(View.VISIBLE);
                    Util.hideKeyboard(CalcDRIActivity.this);

                    MainAppController mainAppController = new MainAppController();

                    String focalLength = (et_focalLength.getText().toString());
                    String sensorPitch = (et_sensorPitch.getText().toString());

                    setResTitles();


                    HashMap<TargetDRIType, Double> calculateDRI = mainAppController.calculateDRI(sensorPitch, focalLength);


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

        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tableLayout=findViewById(R.id.table_tableLayout);

        tv_txtHumanTarget = findViewById(R.id.tv_txtHumanTarget);


        tv_txtObjectTarget = findViewById(R.id.tv_txtObjectTarget);


        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);

        tv_resHumanDet = findViewById(R.id.tv_resHumanDet);
        tv_resHumanRec = findViewById(R.id.tv_resHumanRec);
        tv_resHumanIdent = findViewById(R.id.tv_resHumanIdent);

        tv_resObjectDet = findViewById(R.id.tv_resObjectDet);
        tv_resObjectIdent = findViewById(R.id.tv_resObjectIdent);

        btn_calc = findViewById(R.id.btn_calc);

        Detector values=readWriteControll.getDetectorValues();
        et_sensorPitch.setText(String.valueOf(values.getDetectorPitch()));
        Util.SetActionBarICon(getSupportActionBar());
    }

    private void setResTitles(){

        readWriteControll.initReadDataFromFile();

        HashMap<TargetType, TargetSize> targetSizes = readWriteControll.getTargetSizesValues();
        StringBuilder sb=new StringBuilder();
        sb.append("\t\tNato\n");
        sb.append("("+String.valueOf((targetSizes.get(TargetType.NATO)).getWidth()));
        sb.append("x"+String.valueOf((targetSizes.get(TargetType.NATO)).getHeight())+")");
        tv_txtNatoTarget.setText(sb.toString());

        sb=new StringBuilder();
        sb.append("\tHuman\n");
        sb.append("("+String.valueOf((targetSizes.get(TargetType.HUMAN)).getWidth()));
        sb.append("x"+String.valueOf((targetSizes.get(TargetType.HUMAN)).getHeight())+")");
        tv_txtHumanTarget.setText(sb.toString());

        sb=new StringBuilder();
        sb.append("\tObject\n");
        sb.append("("+String.valueOf((targetSizes.get(TargetType.OBJECT)).getWidth()));
        sb.append("x"+String.valueOf((targetSizes.get(TargetType.OBJECT)).getHeight())+")");
        tv_txtObjectTarget.setText(sb.toString());
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