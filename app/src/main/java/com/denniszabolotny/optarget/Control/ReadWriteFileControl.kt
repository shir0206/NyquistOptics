package com.denniszabolotny.optarget.Control

import android.content.Context
import android.content.SharedPreferences
import com.denniszabolotny.optarget.Model.*
import java.util.*
import kotlin.collections.ArrayList

class ReadWriteFileControl(var context: Context) {

    private var DbRefrence: DB? = null
    private var cont: Context? = null
    private var targetSizeDefaultSettings: SharedPreferences;
    private var linePairDefaultSettings: SharedPreferences;
    private var detecorDefaultSettings:SharedPreferences

    init {
        this.cont = context;
        linePairDefaultSettings = cont!!.getSharedPreferences(linePairFileName, Context.MODE_PRIVATE)
        targetSizeDefaultSettings = cont!!.getSharedPreferences(targetSizeFileName, Context.MODE_PRIVATE)
        detecorDefaultSettings=cont!!.getSharedPreferences(detectorSettingsFileName,Context.MODE_PRIVATE)
        DbRefrence = DB

    }

    public fun initReadDataFromFile() {
           initDataFromFiles() // make sure the default data is available
           SetDefaultSettings() //save the data and store in the db for storage
    }
    public fun getLinePairValues():LinePair{
        return   this.DbRefrence!!.linePair
    }

    public fun getTargetSizesValues():HashMap<TargetType,TargetSize>{
       return  this.DbRefrence!!.targetSizes
    }

    public fun getDetectorValues():Detector{
        return this.DbRefrence!!.detector
    }
    private fun initDataFromFiles() {
                initLinePairDefaultSettings();
                initTargetSizeDefaultSettings();
                initDetectorDefaultSettings();

    }


    private fun SetDefaultSettings() {
                GetLinePairDefaultSettings();
                GetTargetSizeDefaultSettings();
                GetDetectorDefaultSettings();
    }

    //Summary
    //Restores the line pair and target size to default settings
    //Summary
    fun restoreDefaultSettings() {

        var editorLinePair = linePairDefaultSettings.edit()

        // Put default settings
        editorLinePair.putString(lpDetection, java.lang.String.valueOf(lpDet))
        editorLinePair.putString(lpRecognition, java.lang.String.valueOf(lpRec))
        editorLinePair.putString(lpIdentification, java.lang.String.valueOf(lpIdent))
        editorLinePair.putString(lpDetectionObject, java.lang.String.valueOf(lpDetObj))
        DbRefrence!!.initLinePair(lpDet, lpRec, lpIdent, lpDetObj)
        // Save the changes
        editorLinePair.apply()
        val editorTargetSize = targetSizeDefaultSettings.edit()

        // Put default settings
        editorTargetSize.putString(natoTargetSizeW, java.lang.String.valueOf(NatoWidth))
        editorTargetSize.putString(natoTargetSizeH, java.lang.String.valueOf(NatoHeight))
        editorTargetSize.putString(humanTargetSizeW, java.lang.String.valueOf(HumanWidth))
        editorTargetSize.putString(humanTargetSizeH, java.lang.String.valueOf(HumanHeight))
        editorTargetSize.putString(objectTargetSizeW, java.lang.String.valueOf(ObjectWidth))
        editorTargetSize.putString(objectTargetSizeH, java.lang.String.valueOf(ObjectHeight))
        DbRefrence!!.eraseTargetSizes()
        DbRefrence!!.addTargetSize(TargetSize(NatoWidth, NatoHeight), TargetType.NATO)
        DbRefrence!!.addTargetSize(TargetSize(HumanWidth, HumanHeight), TargetType.HUMAN)
        DbRefrence!!.addTargetSize(TargetSize(ObjectWidth, ObjectHeight), TargetType.OBJECT)
        //Save the changes
        editorTargetSize.apply()

        val detectorDefaultSettings=detecorDefaultSettings.edit()
        detectorDefaultSettings.putString(detectorSizeH, detSizeHeight.toString())
        detectorDefaultSettings.putString(detectorSizeW, detSizeWidth.toString())
        detectorDefaultSettings.putString(detectorPitch, detPitch.toString())
        DbRefrence!!.initDetector(detSizeHeight, detSizeWidth, detPitch)
        detectorDefaultSettings.apply()
    }


    private   fun initDetectorDefaultSettings(){
        if(detecorDefaultSettings.getString(detectorSizeH,"")!!.isEmpty()){
            //Get the editor to edit the file
            val editor=detecorDefaultSettings.edit()
            //put default settings
            editor.putString(detectorSizeH,detSizeHeight.toString())
            editor.putString(detectorSizeW,detSizeWidth.toString())
            editor.putString(detectorPitch, detPitch.toString())
            //save the changes
            editor.apply()
        }
    }
    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private  fun initLinePairDefaultSettings() {

        // Check if SharedPreferences file is empty (linePairDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (linePairDefaultSettings.getString(lpDetection, "")!!.isEmpty()) {

            // Get the editor to edit the file
            val editor = linePairDefaultSettings.edit()

            // Put default settings
            editor.putString(lpDetection, lpDet.toString())
            editor.putString(lpRecognition, lpRec.toString())
            editor.putString(lpIdentification, lpIdent.toString())
            editor.putString(lpDetectionObject, lpDetObj.toString())

            // Save the changes
            editor.apply()
        }
    }

    /**
     * Create SharedPreferences file for Target Size settings, and set default settings
     */
    private  fun initTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        // targetSizeDefaultSettings = cont!!.getSharedPreferences(targetSizeFileName, Context.MODE_PRIVATE)

        // Check if SharedPreferences file is empty (targetSizeDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (targetSizeDefaultSettings.getString(natoTargetSizeW, "")!!.isEmpty()) {

            // Get the editor to edit the file
            val editor = targetSizeDefaultSettings.edit()

            // Put default settings
            editor.putString(natoTargetSizeW, NatoWidth.toString())
            editor.putString(natoTargetSizeH, NatoHeight.toString())
            editor.putString(humanTargetSizeW, HumanWidth.toString())
            editor.putString(humanTargetSizeH, HumanHeight.toString())
            editor.putString(objectTargetSizeW, ObjectWidth.toString())
            editor.putString(objectTargetSizeH, ObjectHeight.toString())

            //Save the changes
            editor.apply()
        }
    }

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    private  fun GetLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        //  linePairDefaultSettings = cont!!.getSharedPreferences(linePairFileName, Context.MODE_PRIVATE)

        // Convert the user input from String to double
        val lpDet = linePairDefaultSettings.getString(lpDetection, "")!!.toDouble()
        val lpRec = linePairDefaultSettings.getString(lpRecognition, "")!!.toDouble()
        val lpIdent = linePairDefaultSettings.getString(lpIdentification, "")!!.toDouble()
        val lpDetObj = linePairDefaultSettings.getString(lpDetectionObject, "")!!.toDouble()
        // Set the parameters in LinePair classes according to the user input
        DbRefrence!!.initLinePair(lpDet, lpRec, lpIdent, lpDetObj)
    }

    /**
     * Set default settings derived from SharedPreferences file
     */
    private  fun GetTargetSizeDefaultSettings() {

        // Get the values from targetSizeDefaultSettings Shared Preferences and convert from String to double
        val natoTargetW = targetSizeDefaultSettings!!.getString(natoTargetSizeW, "")!!.toDouble()
        val natoTargetH = targetSizeDefaultSettings!!.getString(natoTargetSizeH, "")!!.toDouble()
        val humanTargetW = targetSizeDefaultSettings!!.getString(humanTargetSizeW, "")!!.toDouble()
        val humanTargetH = targetSizeDefaultSettings!!.getString(humanTargetSizeH, "")!!.toDouble()
        val objTargetW = targetSizeDefaultSettings!!.getString(objectTargetSizeW, "")!!.toDouble()
        val objTargetH = targetSizeDefaultSettings!!.getString(objectTargetSizeH, "")!!.toDouble()
        DbRefrence!!.addTargetSize(TargetSize(natoTargetW, natoTargetH), TargetType.NATO)
        DbRefrence!!.addTargetSize(TargetSize(humanTargetW, humanTargetH), TargetType.HUMAN)
        DbRefrence!!.addTargetSize(TargetSize(objTargetW, objTargetH), TargetType.OBJECT)
    }

    private  fun GetDetectorDefaultSettings(){

        val detectorSizeH=detecorDefaultSettings!!.getString(detectorSizeH,"")!!.toInt()
        val detectorSizeW=detecorDefaultSettings!!.getString(detectorSizeW,"")!!.toInt()
        val detectorPitch=detecorDefaultSettings!!.getString(detectorPitch,"")!!.toInt()
        DbRefrence!!.initDetector(detectorSizeH,detectorSizeW,detectorPitch)
    }


    fun SaveNewLinePairSettings(vararg Values: String) {
        val newLinepairs = ArrayList<Double>()
        var i = 0
        for (`val` in Values) {
            newLinepairs.add(Values[i].toDouble())
            i++
        }
        //  linePairDefaultSettings = cont!!.getSharedPreferences(linePairFileName, Context.MODE_PRIVATE)
        // Get the editor to edit the file
        val editor = linePairDefaultSettings.edit()

        // Put default settings
        editor.putString(lpDetection, newLinepairs[0].toString())
        editor.putString(lpRecognition, newLinepairs[1].toString())
        editor.putString(lpIdentification, newLinepairs[2].toString())
        editor.putString(lpDetectionObject, newLinepairs[3].toString())

        // Save the changes
        editor.apply()
        DbRefrence!!.initLinePair(newLinepairs[0], newLinepairs[1], newLinepairs[2], newLinepairs[3])
    }

    fun SaveNewDetectorSettings( vararg Values:String){
        val newDetectorSettings=ArrayList<String>()
        Values.forEach { newDetectorSettings.add(it) }
        val editor=detecorDefaultSettings.edit()

        editor.putString(detectorSizeH,Values[0])
        editor.putString(detectorSizeW,Values[1])
        editor.putString(detectorPitch,Values[2])
        editor.apply()
        DbRefrence!!.initDetector(Values[0].toInt(),Values[1].toInt(),Values[2].toInt())

    }
    fun SaveNewTargetSizeSettings(vararg Values: String) {
        val newTargetSizes = ArrayList<Double>()
        var i = 0
        for (`val` in Values) {
            newTargetSizes.add(Values[i].toDouble())
            i++
        }
        //   targetSizeDefaultSettings = cont!!.getSharedPreferences(targetSizeFileName, Context.MODE_PRIVATE)


        // Get the editor to edit the file
        val editor = targetSizeDefaultSettings.edit()

        // Put default settings
        editor.putString(natoTargetSizeW, newTargetSizes[0].toString())
        editor.putString(natoTargetSizeH, newTargetSizes[1].toString())
        editor.putString(humanTargetSizeW, newTargetSizes[2].toString())
        editor.putString(humanTargetSizeH, newTargetSizes[3].toString())
        editor.putString(objectTargetSizeW, newTargetSizes[4].toString())
        editor.putString(objectTargetSizeH, newTargetSizes[5].toString())


        //Save the changes
        editor.apply()
        DbRefrence!!.eraseTargetSizes()
        DbRefrence!!.addTargetSize(TargetSize(newTargetSizes[0], newTargetSizes[1]), TargetType.NATO)
        DbRefrence!!.addTargetSize(TargetSize(newTargetSizes[2], newTargetSizes[3]), TargetType.HUMAN)
        DbRefrence!!.addTargetSize(TargetSize(newTargetSizes[4], newTargetSizes[5]), TargetType.OBJECT)
    }


}