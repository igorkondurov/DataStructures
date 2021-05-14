package com.datastructures.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.model.User;
import com.datastructures.presenter.settings.SettingsPresenter;
import com.datastructures.presenter.settings.SettingsPresenterImpl;
import com.datastructures.view.CallbackHelper;
import com.datastructures.view.homepage.HomePageActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    private RelativeLayout root;
    private SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        root = findViewById(R.id.root_element);
        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);
        Button btnNSEdit = findViewById(R.id.btnNSEdit);
        Button btnEmailEdit = findViewById(R.id.btnEmailEdit);
        Button btnPhoneEdit = findViewById(R.id.btnPhoneEdit);
        Button btnSexEdit = findViewById(R.id.btnSexEdit);
        Button btnDateEdit = findViewById(R.id.btnDateEdit);
        Button btnCategoryEdit = findViewById(R.id.btnCategoryEdit);
        Button btnPasswordEdit = findViewById(R.id.btnPasswordEdit);
        Button btnInterestsEdit = findViewById(R.id.btnInterestEdit);

        settingsPresenter = new SettingsPresenterImpl(this);

        settingsPresenter.getUserInfo(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                btnNSEdit.setText(new StringBuilder().append(data.getName()).append(" ").append(data.getSurname()));
                btnEmailEdit.setText(data.getEmail());
                btnPhoneEdit.setText(data.getPhone());
                btnSexEdit.setText(data.getSexOfAPerson());
                btnDateEdit.setText(data.getDateOfBirth());
                btnCategoryEdit.setText(data.getFieldOfActivity());
            }
        });

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });

//        @deprecated Неактуальный функционал
//        btnEmailEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                settingsPresenter.showEditEmailWindow();
//            }
//        });

        btnPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditPasswordWindow();
            }
        });

        btnPhoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditPhoneWindow();
            }
        });

        btnNSEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditNamesWindow();
            }
        });

        btnSexEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditSexOfAPersonWindow();
            }
        });

        btnDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditDateOfBirthdayWindow();
            }
        });

        btnCategoryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditCategoryWindow();
            }
        });

        btnInterestsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.showEditInterestsWindow();
            }
        });

    }

    @Override
    public void restartCurrentActivity() {
        Intent intent = new Intent();
        intent.setClass(this, this.getClass());
        this.startActivity(intent);
        this.finish();
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
