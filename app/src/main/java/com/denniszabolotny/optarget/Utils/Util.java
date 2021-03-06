package com.denniszabolotny.optarget.Utils;


import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.denniszabolotny.optarget.R;

import java.text.DecimalFormat;


public class Util {
    /**
     * Hide keyboard once btn pressed
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Formats the double with precison digits according to how many digits were sent as the second param.
     * @param doubleNum
     * @param numberOfDigits
     * @return
     */
    public static String formatDouble(Double doubleNum,int numberOfDigits){
        String formated=null;
        DecimalFormat df=null;
        switch(numberOfDigits){
            case 1:
                df=new DecimalFormat("#.#");
                break;

            case 2:
                df=new DecimalFormat("#.##");
                break;
            case 6:
                df=new DecimalFormat("#.######");
            default:
                //no such digits found
                break;
        }

        formated=df.format(doubleNum);
        return formated;
    }

    public static void SetActionBarICon(androidx.appcompat.app.ActionBar bar){
        bar.setDisplayShowHomeEnabled(true);
        bar.setLogo(R.mipmap.optarget);
        bar.setDisplayUseLogoEnabled(true);

    }



}
