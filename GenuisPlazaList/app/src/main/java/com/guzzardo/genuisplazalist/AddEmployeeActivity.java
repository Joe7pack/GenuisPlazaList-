package com.guzzardo.genuisplazalist;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddEmployeeActivity extends AppCompatActivity {

    static private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mResources = getResources();

        super.onCreate(savedInstanceState);
    }

}
