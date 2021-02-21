package com.denniszabolotny.optarget.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.denniszabolotny.optarget.Control.ReadWriteFileControl;
import com.denniszabolotny.optarget.R;
import com.denniszabolotny.optarget.Utils.Util;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {


    Dialog focalLenInputType;

    Button btn_fov;
    Button btn_dimension;
    Button btn_focalLength;
    Button btn_dri;

    Vibrator vibrator;
    ReadWriteFileControl setUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.act_home);

        focalLenInputType = new Dialog(this);

        //must init the ... to get latest data from the shared prefrences
        setUp = new ReadWriteFileControl(getApplicationContext());
        setUp.initReadDataFromFile();



        // Set up UI
        setupUI();

        // Get the vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        btn_fov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcFovActivity.class);
                startActivity(intent);
            }
        });

        btn_dimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcDimensionActivity.class);
                startActivity(intent);
            }
        });



        btn_dri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);


                Intent intent = new Intent(MainActivity.this, CalcDRIActivity.class);
                startActivity(intent);
            }
        });



        btn_focalLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrator.vibrate(50);

                showPopUp(view);

            }
        });

    }


    //------------------------------------- Setup Methods -------------------------------------

    /**
     * Initialize all Views, TextViews, EditViews & Button
     */
    private void setupUI() {


        btn_fov = findViewById(R.id.btn_fov);
        btn_dimension = findViewById(R.id.btn_dimension);
        btn_focalLength = findViewById(R.id.btn_focalLength);
        btn_dri = findViewById(R.id.btn_dri);
        Util.SetActionBarICon(getSupportActionBar());
    }

    //------------------------------------- Android Methods -------------------------------------

    /**
     * Hide keyboard on start up app
     */
    private void hideKeyboardOnStartUp() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Create options menu above and inflate it
     *
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        menuBuilder.setOptionalIconsVisible(true);


        return true;
    }

    /**
     * Open options menu above
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                //finish(); //Closes MainActivity
                return true;
            case R.id.action_about:
                startActivity(new Intent(this,AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void showPopUp(View v) {
        Button calculateTargetInput;
        Button FOVInput;
        focalLenInputType.setContentView(R.layout.act_home_focal_popup);

        calculateTargetInput = focalLenInputType.findViewById(R.id.btn_TargetInput);
        FOVInput = focalLenInputType.findViewById(R.id.btn_FOVInput);

        calculateTargetInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CalcFocalLengthTarget.class);
                startActivity(intent);
                focalLenInputType.dismiss();
            }
        });

        FOVInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CalcFocalLengthFOV.class);
                startActivity(intent);
                focalLenInputType.dismiss();

            }
        });
        //Set the size of the window
        focalLenInputType.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //Open the window
        focalLenInputType.show();
    }

}




