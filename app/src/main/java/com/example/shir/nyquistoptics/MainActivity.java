package com.example.shir.nyquistoptics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView tv_comment; // initialize comment

    TextView tv_txtSensorPitch, tv_txtFocalLength, tv_txtSensorSize, tv_txtSensorSizeX; //initialize inputs titles
    EditText et_sensorPitch, et_focalLength, et_sensorSizeW, et_sensorSizeH; //initialize inputs values

    Button btn_calculate; // initialize calculate button

    TextView tv_txtFov, tv_txtIfov, tv_txtHfov, tv_txtVfov; //initialize outputs titles
    TextView tv_resIfov, tv_resHfov, tv_resVfov; //initialize outputs results

    TextView tv_txtNatoTarget; //initialize title
    TextView tv_txtNatoDet, tv_txtNatoRec, tv_txtNatoIdent; //initialize outputs titles
    TextView tv_resNatoDet, tv_resNatoRec, tv_resNatoIdent; //initialize outputs results

    TextView tv_txtHumanTarget; //initialize title
    TextView tv_txtHumanDet, tv_txtHumanRec, tv_txtHumanIdent; //initialize outputs titles
    TextView tv_resHumanDet, tv_resHumanRec, tv_resHumanIdent; //initialize outputs results

    TextView tv_txtObjTarget; //initialize title
    TextView tv_txtObjDet, tv_txtObjRec; //initialize outputs titles
    TextView tv_resObjDet, tv_resObjRec; //initialize outputs results

    TextView tv_natoSize, tv_humanSize, tv_objSize;

    ArrayList<TextView> output = new ArrayList<>();

    DecimalFormat formatOneDig = new DecimalFormat("#.0");
    DecimalFormat formatSixDig= new DecimalFormat("#.000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If all fields are not filled, toast a massage to fill all fields
                if (et_focalLength.getText().toString().isEmpty() || et_sensorPitch.getText().toString().isEmpty() || et_sensorSizeW.getText().toString().isEmpty() || et_sensorSizeH.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                //If all fields are filled, continue
                else {

                    //Convert the user input from String to double
                    double sensorPitch = Double.parseDouble(et_sensorPitch.getText().toString());
                    double focalLength = Double.parseDouble(et_focalLength.getText().toString());
                    double sensorSizeW = Double.parseDouble(et_sensorSizeW.getText().toString());
                    double sensorSizeH = Double.parseDouble(et_sensorSizeH.getText().toString());

                    //Set the parameters in Properties & SensorSize classes according to the user input
                    Properties.setSensorPitch(sensorPitch);
                    Properties.setFocalLength(focalLength);
                    SensorSize.setWidth(sensorSizeW);
                    SensorSize.setHeight(sensorSizeH);

                    //PART 1

                    //Calculate the output
                    Fov.setFov();

                    //Convert the output from double to String and formatting the decimal digits
                    String ifov = formatSixDig.format(Fov.getIfov());
                    String hfov = formatOneDig.format(Fov.getHfov());
                    String vfov = formatOneDig.format(Fov.getVfov());

                    //Set the String output to TextView
                    tv_resIfov.setText(ifov);
                    tv_resHfov.setText(hfov);
                    tv_resVfov.setText(vfov);

                    //PART 2

                    //Initialize Target class instances
                    TargetSize natoTargetSize = new TargetSize(2.3, 2.3);
                    Target natoTarget = new Target();

                    TargetSize humanTargetSize = new TargetSize(0.5, 1.7);
                    Target humanTarget = new Target();

                    TargetSize objTargetSize = new TargetSize(0.5, 0.5);
                    Target objTarget = new Target();

                    //Convert the target size from double to String
                    String natoSize = "(" + Double.toString(natoTargetSize.getWidth()) + "x" + Double.toString(natoTargetSize.getHeight()) + ")";
                    String humanSize = "(" + Double.toString(humanTargetSize.getWidth()) + "x" + Double.toString(humanTargetSize.getHeight()) + ")";
                    String objSize = "(" + Double.toString(objTargetSize.getWidth()) + "x" + Double.toString(objTargetSize.getHeight()) + ")";

                    //Set the target size to TextView
                    tv_natoSize.setText(natoSize);
                    tv_humanSize.setText(humanSize);
                    tv_objSize.setText(objSize);

                    //Calculate the output
                    natoTarget.setTarget(natoTargetSize);
                    humanTarget.setTarget(humanTargetSize);
                    objTarget.setTarget(objTargetSize);

                    //Convert the output from double to String
                    String natoTargetDet = formatOneDig.format(natoTarget.getDetection());
                    String natoTargetRec = formatOneDig.format(natoTarget.getRecognition());
                    String natoTargetIdent = formatOneDig.format(natoTarget.getIdentify());

                    String humanTargetDet = formatOneDig.format(humanTarget.getDetection());
                    String humanTargetRec = formatOneDig.format(humanTarget.getRecognition());
                    String humanTargetIdent = formatOneDig.format(humanTarget.getIdentify());

                    String objTargetDet = formatOneDig.format(objTarget.getDetection());
                    String objTargetRec = formatOneDig.format(objTarget.getRecognition());

                    //Set the String output to TextView
                    tv_resNatoDet.setText(natoTargetDet);
                    tv_resNatoRec.setText(natoTargetRec);
                    tv_resNatoIdent.setText(natoTargetIdent);

                    tv_resHumanDet.setText(humanTargetDet);
                    tv_resHumanRec.setText(humanTargetRec);
                    tv_resHumanIdent.setText(humanTargetIdent);

                    tv_resObjDet.setText(objTargetDet);
                    tv_resObjRec.setText(objTargetRec);

                    //Turn the output TextView to visible
                    visibleOutputTextView();
                }
            }
        });


    }

    private void setupUI() {

        tv_comment = findViewById(R.id.tv_comment);

        tv_txtSensorPitch = findViewById(R.id.tv_txtSensorPitch);
        tv_txtFocalLength = findViewById(R.id.tv_txtFocalLength);
        tv_txtSensorSize = findViewById(R.id.tv_txtSensorSize);
        tv_txtSensorSizeX = findViewById(R.id.tv_txtSensorSizeX);

        et_sensorPitch = findViewById(R.id.et_sensorPitch);
        et_focalLength = findViewById(R.id.et_focalLength);
        et_sensorSizeW = findViewById(R.id.et_sensorSizeW);
        et_sensorSizeH = findViewById(R.id.et_sensorSizeH);

        btn_calculate = findViewById(R.id.btn_calculate);

        //From here and out TextViews are added to the output ArrayList (should be invisible)
        tv_txtFov = findViewById(R.id.tv_txtFov);
        tv_txtIfov = findViewById(R.id.tv_txtIfov);
        tv_txtHfov = findViewById(R.id.tv_txtHfov);
        tv_txtVfov = findViewById(R.id.tv_txtVfov);
        tv_resIfov = findViewById(R.id.tv_resIfov);
        tv_resHfov = findViewById(R.id.tv_resHfov);
        tv_resVfov = findViewById(R.id.tv_resVfov);

        tv_txtNatoTarget = findViewById(R.id.tv_txtNatoTarget);
        tv_txtNatoDet = findViewById(R.id.tv_txtNatoDet);
        tv_txtNatoRec = findViewById(R.id.tv_txtNatoRec);
        tv_txtNatoIdent = findViewById(R.id.tv_txtNatoIdent);
        tv_resNatoDet = findViewById(R.id.tv_resNatoDet);
        tv_resNatoRec = findViewById(R.id.tv_resNatoRec);
        tv_resNatoIdent = findViewById(R.id.tv_resNatoIdent);

        tv_txtHumanTarget = findViewById(R.id.tv_txtHumanTarget);
        tv_txtHumanDet = findViewById(R.id.tv_txtHumanDet);
        tv_txtHumanRec = findViewById(R.id.tv_txtHumanRec);
        tv_txtHumanIdent = findViewById(R.id.tv_txtHumanIdent);
        tv_resHumanDet = findViewById(R.id.tv_resHumanDet);
        tv_resHumanRec = findViewById(R.id.tv_resHumanRec);
        tv_resHumanIdent = findViewById(R.id.tv_resHumanIdent);

        tv_txtObjTarget = findViewById(R.id.tv_txtObjTarget);
        tv_txtObjDet = findViewById(R.id.tv_txtObjDet);
        tv_txtObjRec = findViewById(R.id.tv_txtObjRec);
        tv_resObjDet = findViewById(R.id.tv_resObjDet);
        tv_resObjRec = findViewById(R.id.tv_resObjRec);

        tv_natoSize = findViewById(R.id.tv_natoSize);
        tv_humanSize = findViewById(R.id.tv_humanSize);
        tv_objSize = findViewById(R.id.tv_objSize);

        createOutputArrayList(); //Add the output TextView to the ArrayList
        invisibleOutputTextView(); //Turn the output TextView in the ArrayList to invisible

    }

    private void createOutputArrayList() { //Add the output TextView to the ArrayList

        output.add(tv_txtFov);
        output.add(tv_txtIfov);
        output.add(tv_txtHfov);
        output.add(tv_txtVfov);
        output.add(tv_resIfov);
        output.add(tv_resHfov);
        output.add(tv_resVfov);

        output.add(tv_txtNatoTarget);
        output.add(tv_txtNatoDet);
        output.add(tv_txtNatoRec);
        output.add(tv_txtNatoIdent);
        output.add(tv_resNatoDet);
        output.add(tv_resNatoRec);
        output.add(tv_resNatoIdent);

        output.add(tv_txtHumanTarget);
        output.add(tv_txtHumanDet);
        output.add(tv_txtHumanRec);
        output.add(tv_txtHumanIdent);
        output.add(tv_resHumanDet);
        output.add(tv_resHumanRec);
        output.add(tv_resHumanIdent);

        output.add(tv_txtObjTarget);
        output.add(tv_txtObjDet);
        output.add(tv_txtObjRec);
        output.add(tv_resObjDet);
        output.add(tv_resObjRec);

        output.add(tv_natoSize);
        output.add(tv_humanSize);
        output.add(tv_objSize);

    }

    private void invisibleOutputTextView() { //Turn the output TextView in the ArrayList to invisible

        for (TextView tv : output) {
            tv.setVisibility(View.INVISIBLE);
        }

    }

    private void visibleOutputTextView() { //Turn the output TextView in the ArrayList to visible

        for (TextView tv : output) {
            tv.setVisibility(View.VISIBLE);
        }

    }

}




