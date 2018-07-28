package com.guzzardo.genuisplazalist;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AddEmployeeActivity extends AppCompatActivity {

    static private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mResources = getResources();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.add_employee_tool_bar);
        setSupportActionBar(toolbar);

    }

}
