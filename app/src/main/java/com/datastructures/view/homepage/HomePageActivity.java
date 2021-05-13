package com.datastructures.view.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.homepage.HomePagePresenter;
import com.datastructures.presenter.homepage.HomePagePresenterImpl;
import com.datastructures.view.aboutapp.AboutAppActivity;
import com.datastructures.view.main.MainActivity;
import com.datastructures.view.settings.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity implements HomePageView {

    private RelativeLayout root;
    private HomePagePresenter homePagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button btnGoOut = findViewById(R.id.btnGoOut);
        Button btnToAboutApp = findViewById(R.id.btnAboutApp);
        Button btnToSettings = findViewById(R.id.btnSettings);
        root = findViewById(R.id.root_element);

        homePagePresenter = new HomePagePresenterImpl(this);

        btnGoOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePagePresenter.toExitTheApp();
            }
        });

        btnToAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePagePresenter.toAboutApp();
            }
        });

        btnToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePagePresenter.toSettings();
            }
        });
    }

    @Override
    public void transferToMainPage() {
        startActivity(new Intent(HomePageActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void transferToAboutAppPage() {
        startActivity(new Intent(HomePageActivity.this, AboutAppActivity.class));
        finish();
    }

    @Override
    public void transferToSettings() {
        startActivity(new Intent(HomePageActivity.this, SettingsActivity.class));
        finish();
    }
}