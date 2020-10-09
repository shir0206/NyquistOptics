package com.shirzabolotnyklein.nyquistoptics.View

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.shirzabolotnyklein.nyquistoptics.Control.CalculationController
import com.shirzabolotnyklein.nyquistoptics.R

class CalcFocalLengthTarget : AppCompatActivity() {


    var et_targetSizeW: EditText? = null
    var et_targetSizeH: EditText? = null
    var et_targetRange: EditText? = null
    var et_dimensionW: EditText? = null
    var et_dimensionH: EditText? = null
    var et_sensorPitch: EditText? = null
    var tv_resFocalLengthH: TextView? = null
    var tv_resFocalLengthW: TextView? = null
    var btn_calc: Button? = null
    var vibrator: Vibrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_calc_focal_length_target)

        // Set up UI
        setupUI()

        // Get the vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        btn_calc.setOnClickListener(View.OnClickListener { // Vibrate
            vibrator.vibrate(50)
            if (isValid()) {
                Util.hideKeyboard(this@CalcFocalLengthTarget)
                val calculationController = CalculationController()

                val dimensionW: Double = et_dimensionW.getText().toString().toDouble()
                val dimensionH: Double = et_dimensionH.getText().toString().toDouble()
                val targetSizeW: Double = et_targetSizeW.getText().toString().toDouble()
                val targetSizeH: Double = et_targetSizeH.getText().toString().toDouble()
                val sensorPitch: Double = et_sensorPitch.getText().toString().toDouble()
                val targetRange: Double = et_targetRange.getText().toString().toDouble()




                val focalLengthW = java.lang.Double.toString(calculationController.calcFocalLengthWidthTarget(
                        dimensionW,
                        targetSizeW,
                        sensorPitch,
                        targetRange))



                val focalLengthH = java.lang.Double.toString(calculationController.calcFocalLengthHeightTarget(dimensionW,
                        dimensionH,
                        targetSizeH,
                        sensorPitch,
                        targetRange))

                tv_resFocalLengthW.setText(focalLengthW)
                tv_resFocalLengthH.setText(focalLengthH)
            }
        })

    }

    private fun setupUI() {
        et_targetSizeW = findViewById<EditText>(R.id.et_targetSizeW)
        et_targetSizeH = findViewById<EditText>(R.id.et_targetSizeH)
        et_targetRange = findViewById<EditText>(R.id.et_targetRange)
        et_dimensionW = findViewById<EditText>(R.id.et_dimensionW)
        et_dimensionH = findViewById<EditText>(R.id.et_resTargetSizeH)
        et_sensorPitch = findViewById<EditText>(R.id.et_sensorPitch)
        tv_resFocalLengthH = findViewById<TextView>(R.id.tv_resFocalLengthH)
        tv_resFocalLengthW = findViewById<TextView>(R.id.tv_resFocalLengthW)
        btn_calc = findViewById<Button>(R.id.btn_calc)
    }

    private fun isValid(): Boolean {
        val msg = " is missing."
        var value = ""
        if (et_targetSizeW.getText().toString() == "") {
            value = "Target Size width"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        } else if (et_targetSizeH.getText().toString() == "") {
            value = "Target Size height"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        } else if (et_targetRange.getText().toString() == "") {
            value = "Target Range"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        } else if (et_dimensionW.getText().toString() == "") {
            value = "Dimension width"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        } else if (et_dimensionH.getText().toString() == "") {
            value = "Dimension height"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        } else if (et_sensorPitch.getText().toString() == "") {
            value = "Sensor Pitch"
            Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}