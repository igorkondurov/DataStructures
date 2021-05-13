package com.datastructures.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.main.MainPresenter;
import com.datastructures.presenter.main.MainPresenterImpl;
import com.datastructures.view.homepage.HomePageActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private RelativeLayout root;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        root = findViewById(R.id.root_element);

        mainPresenter = new MainPresenterImpl(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.showSignUpWindow();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.showSignInWindow();
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

    @Override
    public void transferToHomePage() {
        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        finish();
    }
}