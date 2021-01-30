package com.shirzabolotnyklein.nyquistoptics.View

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.shirzabolotnyklein.nyquistoptics.Control.MainAppController
import com.shirzabolotnyklein.nyquistoptics.R
import com.shirzabolotnyklein.nyquistoptics.Utils.Util

class CalcFocalLengthTarget : AppCompatActivity() {


    var et_targetSizeW: EditText? = null
    var et_targetSizeH: EditText? = null
    var et_targetRange: EditText? = null
    var et_dimensionW: EditText? = null
    var et_dimensionH: EditText? = null
    var et_sensorPitch: EditText? = null
    var smallestDimensionSize:Double?=0.0
    var tv_resFocalLength: TextView? = null
    var btn_calc: Button? = null
    private var vibrator: Vibrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_calc_focal_length_target)

        // Set up UI
        setupUI()


        btn_calc?.setOnClickListener {
            phoneVib()
            if (isValid()) {
                Util.hideKeyboard(this@CalcFocalLengthTarget)
                val mainAppController = MainAppController();

                val dimensionW: Double = et_dimensionW?.text.toString().toDouble()
                val dimensionH: Double = et_dimensionH?.text.toString().toDouble()
                val targetSizeW: Double = et_targetSizeW?.text.toString().toDouble()
                val targetSizeH: Double = et_targetSizeH?.text.toString().toDouble()
                val sensorPitch: Double = et_sensorPitch?.text.toString().toDouble()
                val targetRange: Double = et_targetRange?.text.toString().toDouble()

                val smallestTargetSize= minOf(targetSizeW,targetSizeH);

                if(smallestTargetSize==targetSizeW){
                    smallestDimensionSize=dimensionW
                }else{
                    smallestDimensionSize=dimensionH
                }
                val minFocalLength= (mainAppController.calculateFocalLengthViaTarget(
                        smallestDimensionSize!!,
                        smallestTargetSize,
                        sensorPitch,
                        targetRange))


                tv_resFocalLength?.text = Util.formatDouble(minFocalLength,2);

            }
        }

    }

    private fun setupUI() {
        et_targetSizeW = findViewById(R.id.et_targetSizeW)
        et_targetSizeH = findViewById(R.id.et_targetSizeH)
        et_targetRange = findViewById(R.id.et_targetRange)
        et_dimensionW = findViewById(R.id.et_dimensionW)
        et_dimensionH = findViewById(R.id.et_dimensionH)
        et_sensorPitch = findViewById(R.id.et_sensorPitch)
        tv_resFocalLength = findViewById(R.id.tv_resFocalLength)
        btn_calc = findViewById(R.id.btn_calc)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun phoneVib(){

        if (vibrator!!.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator!!.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator!!.vibrate(50) // Vibrate method for below API Level 26
            }
        }
    }

    private fun isValid(): Boolean {
        val msg = " is missing."
        var value = ""
        when {
            et_targetSizeW?.getText().toString() == "" -> {
                value = "Target Size width"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_targetSizeH?.getText().toString() == "" -> {
                value = "Target Size height"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_targetRange?.getText().toString() == "" -> {
                value = "Target Range"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_dimensionW?.getText().toString() == "" -> {
                value = "Dimension width"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_dimensionH?.getText().toString() == "" -> {
                value = "Dimension height"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_sensorPitch?.getText().toString() == "" -> {
                value = "Sensor Pitch"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }
}