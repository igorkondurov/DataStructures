package com.datastructures.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.main.MainPresenter;
import com.datastructures.presenter.main.MainPresenterImpl;
import com.datastructures.view.homepage.HomePageActivity;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Активность для работы с пользователем при аутентификации/ авторизации/ регистрации
 */
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
                showSignUpWindow();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
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

    @Override
    public void transferToHomePage() {
        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        finish();
    }

    private void showSignUpWindow() {
        AlertDialog.Builder dialog = getAlertDialog("Регистрация", "Заполните регистрационную форму!");
        View registerWindow = getViewForDialog(R.layout.register_window);
        dialog.setView(registerWindow);

        MaterialEditText email = registerWindow.findViewById(R.id.emailField);
        MaterialEditText password = registerWindow.findViewById(R.id.passwordField);
        MaterialEditText name = registerWindow.findViewById(R.id.nameField);
        MaterialEditText surname = registerWindow.findViewById(R.id.surnameField);
        MaterialEditText phone = registerWindow.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!mainPresenter.checkFields(email.getText().toString(), password.getText().toString(),
                        name.getText().toString(), phone.getText().toString())) {
                    return;
                }

                mainPresenter.createUser(email.getText().toString().trim(), password.getText().toString().trim(),
                        name.getText().toString().trim(), surname.getText().toString().trim(), phone.getText().toString().trim());
            }
        });

        dialog.show();
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = getAlertDialog("Войти", "Введите данные для авторизации");
        View loginWindow = getViewForDialog(R.layout.login_window);
        dialog.setView(loginWindow);

        MaterialEditText email = loginWindow.findViewById(R.id.emailField);
        MaterialEditText password = loginWindow.findViewById(R.id.passwordField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    showSnackBarForDialog("Введите адрес электронной почты!", Snackbar.LENGTH_LONG);
                    return;
                }
                if (password.getText().toString().length() < 7) {
                    showSnackBarForDialog("Введите пароль, содержащий более 7 символов!", Snackbar.LENGTH_LONG);
                    return;
                }

                mainPresenter.loginUser(email.getText().toString(), password.getText().toString());
            }
        });

        dialog.show();
    }
}
