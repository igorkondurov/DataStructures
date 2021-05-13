package com.datastructures.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.settings.SettingsPresenter;
import com.datastructures.presenter.settings.SettingsPresenterImpl;
import com.datastructures.view.homepage.HomePageActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    private RelativeLayout root;
    private SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsPresenter = new SettingsPresenterImpl(this);
        settingsPresenter.checkData();

        Map<String, String> credentials = settingsPresenter.getValues();
        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);
        Button btnEmailEdit = findViewById(R.id.btnEmailEdit);
        btnEmailEdit.setText(credentials.get("email"));
        Button btnPhoneEdit = findViewById(R.id.btnPhoneEdit);
        btnPhoneEdit.setText(credentials.get("phone"));
        Button btnPasswordEdit = findViewById(R.id.btnPasswordEdit);

        root = findViewById(R.id.root_element);

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });

        btnEmailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditEmailWindow();
            }
        });

        btnPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditPasswordWindow();
            }
        });

    }

    @Override
    public AlertDialog.Builder getAlertDialog(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);

        return dialog;
    }

    @Override
    public View getViewForDialog(int resource) {
        LayoutInflater inflater = LayoutInflater.from(this);
        return inflater.inflate(resource, null);
    }

    @Override
    public void showSnackBarForDialog(String message, int length) {
        Snackbar.make(root, message, length).show();
    }

    private void transferToHomePage() {
        startActivity(new Intent(SettingsActivity.this, HomePageActivity.class));
        finish();
    }

}
