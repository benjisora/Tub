package com.projects.benjisora.tubapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.projects.benjisora.tubapp.R;

/**
 * Details Activity, created to display the hours of each line's stops
 */
public class DetailsActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
