package com.datastructures.view.aboutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.view.homepage.HomePageActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Активность для получнния актуальной информации пользователем о приложении
 */
public class AboutAppActivity extends AppCompatActivity {

    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);
        root = findViewById(R.id.root_element);

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });
    }

    private void transferToHomePage() {
        startActivity(new Intent(AboutAppActivity.this, HomePageActivity.class));
        finish();
    }
}
