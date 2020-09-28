package com.shirzabolotnyklein.nyquistoptics.View;


import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


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

}
