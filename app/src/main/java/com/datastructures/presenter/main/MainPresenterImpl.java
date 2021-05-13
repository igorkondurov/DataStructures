package com.datastructures.presenter.main;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.datastructures.R;
import com.datastructures.view.main.MainView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class MainPresenterImpl implements MainPresenter {
    private final MainView mainView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        LOGGER = Logger.getLogger(MainPresenterImpl.class.getName());
    }

    @Override
    public void showSignUpWindow() {
        AlertDialog.Builder dialog = mainView.getAlertDialog("Регистрация", "Заполните регистрационную форму!");
        View registerWindow = mainView.getViewForDialog(R.layout.register_window);
        dialog.setView(registerWindow);

        MaterialEditText email = registerWindow.findViewById(R.id.emailField);
        MaterialEditText password = registerWindow.findViewById(R.id.passwordField);
        MaterialEditText name = registerWindow.findViewById(R.id.nameField);
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
                if (!checkFields(email, password, name, phone)) {
                    return;
                }

                createUser(email, password, name, phone);
            }
        });

        dialog.show();
    }

    @Override
    public void showSignInWindow() {
        AlertDialog.Builder dialog = mainView.getAlertDialog("Войти", "Введите данные для авторизации");
        View loginWindow = mainView.getViewForDialog(R.layout.login_window);
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
                    mainView.showSnackBarForDialog("Введите адрес электронной почты!", Snackbar.LENGTH_LONG);
                    return;
                }
                if (password.getText().toString().length() < 7) {
                    mainView.showSnackBarForDialog("Введите пароль, содержащий более 7 символов!", Snackbar.LENGTH_LONG);
                    return;
                }

                loginUser(email, password);
            }
        });

        dialog.show();
    }

    private boolean checkFields(MaterialEditText email, MaterialEditText password, MaterialEditText name, MaterialEditText phone) {
        if (TextUtils.isEmpty(email.getText().toString())) {
            mainView.showSnackBarForDialog("Введите адрес электронной почты!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (password.getText().toString().length() < 7) {
            mainView.showSnackBarForDialog("Введите пароль, содержащий более 7 символов!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            mainView.showSnackBarForDialog("Введите имя!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            mainView.showSnackBarForDialog("Введите номер телефона!", Snackbar.LENGTH_LONG);
            return false;
        }

        return true;
    }

    private void createUser(MaterialEditText email, MaterialEditText password, MaterialEditText name, MaterialEditText phone) {
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mainView.showSnackBarForDialog("Регистрация прошла успешно!", Snackbar.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь с именем " + name.getText().toString()
                                + " и почтой " + email.getText().toString() + " не был зарегистрирован! Ошибка: " + e.getMessage());
                        mainView.showSnackBarForDialog("Ошибка при регистрации: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void loginUser(MaterialEditText email, MaterialEditText password) {
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email.getText().toString() + " успешно прошёл аутентификацию!");
                        mainView.transferToHomePage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email.getText().toString()
                                + " не прошёл аутентификацию! Ошибка: " + e.getMessage());
                        mainView.showSnackBarForDialog("Ошибка авторизации: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }
}
