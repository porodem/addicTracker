package com.porodem.activitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FailsActivity extends AppCompatActivity {

    TextView tvFailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fails);

        tvFailList = findViewById(R.id.tvFails);

        String fails = IventLab.get(this).getFails();
        tvFailList.setText(fails);
    }
}