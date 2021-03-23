package com.denniszabolotny.optarget.View

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.denniszabolotny.optarget.Control.MainAppController
import com.denniszabolotny.optarget.Control.ReadWriteFileControl
import com.denniszabolotny.optarget.R
import com.denniszabolotny.optarget.Utils.Util

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
    var resTextView:TextView?=null
    var resImage:ImageView?=null
    private var vibrator: Vibrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.act_calc_focal_length_target)

        // Set up UI
        setupUI()

        resTextView?.visibility=View.GONE
        resImage?.visibility=View.GONE
        btn_calc?.setOnClickListener {
            phoneVib()
            if (isValid()) {
                Util.hideKeyboard(this@CalcFocalLengthTarget)
                resTextView?.visibility=View.VISIBLE
                resImage?.visibility=View.VISIBLE
                val mainAppController = MainAppController();

                val dimensionW: Double = et_dimensionW?.text.toString().toDouble()
                val dimensionH: Double = et_dimensionH?.text.toString().toDouble()
                val targetSizeW: Double = et_targetSizeW?.text.toString().toDouble()
                val targetSizeH: Double = et_targetSizeH?.text.toString().toDouble()
                val sensorPitch: Double = et_sensorPitch?.text.toString().toDouble()
                val targetRange: Double = et_targetRange?.text.toString().toDouble()

                val smallestTargetSize= minOf(targetSizeW, targetSizeH);

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


                tv_resFocalLength?.text = Util.formatDouble(minFocalLength, 2);

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
        resImage=findViewById(R.id.img_focalLengthH)
        resTextView=findViewById(R.id.tv_txtFocalLength)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val fileAccess= ReadWriteFileControl(this)

        val detecorVals=fileAccess.getDetectorValues()
        et_sensorPitch?.setText(detecorVals.detectorPitch.toString())
        Util.SetActionBarICon(supportActionBar)
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
                value = "Target Size Width (m)"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_targetSizeH?.getText().toString() == "" -> {
                value = "Target Size Height (m)"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_targetRange?.getText().toString() == "" -> {
                value = "Target Range"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_dimensionW?.getText().toString() == "" -> {
                value = "Target Size Width (px)"
                Toast.makeText(this@CalcFocalLengthTarget, value + msg, Toast.LENGTH_SHORT).show()
                return false
            }
            et_dimensionH?.getText().toString() == "" -> {
                value = "Target Size Height (px)"
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