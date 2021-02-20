package com.denniszabolotny.optarget.View

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.denniszabolotny.optarget.R

class AboutActivity : AppCompatActivity() {
    var multiLine: EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setupUI()
        val sb=StringBuilder()

        val infoLine="This application was designed to calculate DRI (detection/recognition/identification) ranges and other performance data for a variety of cameras, by using Nyquist formulas.\n\n"
        val infoLineTwo= "The user has many options in case he wishes to calculate the Lens that he may need for his equipment then he will use the Required Lens calculator.\n\n"
        val infoLineThree=  "If the user wants to know which detector size to pick for his equipment he will pick the Detector Size calculator.\n\n"
        val infoLineFour= "In case the user wants to calculate the field of view of the equipment he will calculate it according to Field of View calculator.\n\n"
        val infoLineFive= "If the user wishes to calculate the DRI of a certain camera he may choose DRI calculator.\n\n"
        val infoLineSix= "Default values of target sizes, line-pairs and detector dimensions can be configured through the application settings menu.\n\n"
        val infoLineSeven="The information contained in Op Target mobile app is for general information purposes only.\n\n"+ "The app creator assumes no responsibility for errors or omissions in the contents of the Service."

        sb.append(infoLine)
        sb.append(infoLineTwo)
        sb.append(infoLineThree)
        sb.append(infoLineFour)
        sb.append(infoLineFive)
        sb.append(infoLineSix)
        sb.append(infoLineSeven)
        multiLine!!.setText(sb.toString())
        multiLine!!.background = null


    }

    private fun setupUI(){
        multiLine=findViewById(R.id.et_AboutMainText)
    }
}