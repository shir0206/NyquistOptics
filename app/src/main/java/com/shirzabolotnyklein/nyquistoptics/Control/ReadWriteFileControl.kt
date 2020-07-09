package com.shirzabolotnyklein.nyquistoptics.Control

import android.content.Context
import android.content.SharedPreferences
import com.shirzabolotnyklein.nyquistoptics.Model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import java.util.*

class ReadWriteFileControl(var context: Context) {

    private var DbRefrence: DB? = null
    private var cont: Context? = null
    var targetSizeDefaultSettings: SharedPreferences;
    var linePairDefaultSettings: SharedPreferences;

    init {
        this.cont = context;
        linePairDefaultSettings = cont!!.getSharedPreferences(linePairFileName, Context.MODE_PRIVATE)
        targetSizeDefaultSettings = cont!!.getSharedPreferences(targetSizeFileName, Context.MODE_PRIVATE)
        DbRefrence = DB
        initReadDataFromFile();
    }

    fun initReadDataFromFile() {
        CoroutineScope(Dispatchers.IO).launch {
            initDataFromFiles() // make sure the default data is available
            SetDefaultSettings() //save the data and store in the db for storage

        }


    }


    private fun initDataFromFiles() {
        CoroutineScope(Dispatchers.IO).launch {

            launch {
                initLinePairDefaultSettings();
                //Toast.makeText(cont,"Init Line Pair  done",Toast.LENGTH_LONG).show();
            }

            launch {
                initTargetSizeDefaultSettings();
                // Toast.makeText(cont,"Target Size init done",Toast.LENGTH_LONG).show();
            }
        }
    }


    private fun SetDefaultSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                GetLinePairDefaultSettings();
            }
            launch {
                GetTargetSizeDefaultSettings();
            }
        }


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
    }

    /**
     * Create SharedPreferences file for Line pairs settings, and set default settings
     */
    private suspend fun initLinePairDefaultSettings() {

        // Check if SharedPreferences file is empty (linePairDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (linePairDefaultSettings.getString(lpDetection, "").isEmpty()) {

            // Get the editor to edit the file
            val editor = linePairDefaultSettings.edit()

            // Put default settings
            editor.putString(lpDetection, java.lang.String.valueOf(lpDet))
            editor.putString(lpRecognition, java.lang.String.valueOf(lpRec))
            editor.putString(lpIdentification, java.lang.String.valueOf(lpIdent))
            editor.putString(lpDetectionObject, java.lang.String.valueOf(lpDetObj))

            // Save the changes
            editor.apply()
        }
    }

    /**
     * Create SharedPreferences file for Target Size settings, and set default settings
     */
    private suspend fun initTargetSizeDefaultSettings() {

        // Get the file named "targetSizeDefaultSettings", private
        // targetSizeDefaultSettings = cont!!.getSharedPreferences(targetSizeFileName, Context.MODE_PRIVATE)

        // Check if SharedPreferences file is empty (targetSizeDefaultSettings file).
        // If it's empty (first app usage), put default settings.
        if (targetSizeDefaultSettings.getString(natoTargetSizeW, "").isEmpty()) {

            // Get the editor to edit the file
            val editor = targetSizeDefaultSettings.edit()

            // Put default settings
            editor.putString(natoTargetSizeW, java.lang.String.valueOf(NatoWidth))
            editor.putString(natoTargetSizeH, java.lang.String.valueOf(NatoHeight))
            editor.putString(humanTargetSizeW, java.lang.String.valueOf(HumanWidth))
            editor.putString(humanTargetSizeH, java.lang.String.valueOf(HumanHeight))
            editor.putString(objectTargetSizeW, java.lang.String.valueOf(ObjectWidth))
            editor.putString(objectTargetSizeH, java.lang.String.valueOf(ObjectHeight))

            //Save the changes
            editor.apply()
        }
    }

    /**
     * Set default settings derived from SharedPreferences file to the Line Pair class
     */
    private suspend fun GetLinePairDefaultSettings() {

        // Get the file named "linePairDefaultSettings", private
        //  linePairDefaultSettings = cont!!.getSharedPreferences(linePairFileName, Context.MODE_PRIVATE)

        // Convert the user input from String to double
        val lpDet = linePairDefaultSettings.getString(lpDetection, "").toDouble()
        val lpRec = linePairDefaultSettings.getString(lpRecognition, "").toDouble()
        val lpIdent = linePairDefaultSettings.getString(lpIdentification, "").toDouble()
        val lpDetObj = linePairDefaultSettings.getString(lpDetectionObject, "").toDouble()
        // Set the parameters in LinePair classes according to the user input
        DbRefrence!!.initLinePair(lpDet, lpRec, lpIdent, lpDetObj)
    }

    /**
     * Set default settings derived from SharedPreferences file
     */
    private suspend fun GetTargetSizeDefaultSettings() {

        // Get the values from targetSizeDefaultSettings Shared Preferences and convert from String to double
        val natoTargetW = targetSizeDefaultSettings!!.getString(natoTargetSizeW, "").toDouble()
        val natoTargetH = targetSizeDefaultSettings!!.getString(natoTargetSizeH, "").toDouble()
        val humanTargetW = targetSizeDefaultSettings!!.getString(humanTargetSizeW, "").toDouble()
        val humanTargetH = targetSizeDefaultSettings!!.getString(humanTargetSizeH, "").toDouble()
        val objTargetW = targetSizeDefaultSettings!!.getString(objectTargetSizeW, "").toDouble()
        val objTargetH = targetSizeDefaultSettings!!.getString(objectTargetSizeH, "").toDouble()
        DbRefrence!!.addTargetSize(TargetSize(natoTargetW, natoTargetH), TargetType.NATO)
        DbRefrence!!.addTargetSize(TargetSize(humanTargetW, humanTargetH), TargetType.HUMAN)
        DbRefrence!!.addTargetSize(TargetSize(objTargetW, objTargetH), TargetType.OBJECT)
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