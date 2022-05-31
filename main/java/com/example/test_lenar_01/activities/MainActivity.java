package com.example.test_lenar_01.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.test_lenar_01.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout accountLinLayout;
    private LinearLayout searchLinLayout;
    private LinearLayout createLinLayout;
    private LinearLayout rollLinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        accountLinLayout = findViewById(R.id.accountLinLayout);
        searchLinLayout = findViewById(R.id.searchLinLayout);
        createLinLayout = findViewById(R.id.createLinLayout);
        rollLinLayout = findViewById(R.id.rollLinLayout);


        accountLinLayout.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
        });

        searchLinLayout.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        createLinLayout.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });

        rollLinLayout.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, RollActivity.class);
            startActivity(intent);
        });
    }
}