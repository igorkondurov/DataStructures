package com.datastructures.presenter.main;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.datastructures.R;
import com.datastructures.model.User;
import com.datastructures.view.main.MainView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Collections;
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
        users = database.getReference("Users");
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
                if (!checkFields(email, password, name, phone)) {
                    return;
                }

                createUser(email.getText().toString().trim(), password.getText().toString().trim(),
                        name.getText().toString().trim(), surname.getText().toString().trim(), phone.getText().toString().trim());
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

    private void createUser(String email, String password, String name, String surname, String phone) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mainView.showSnackBarForDialog("Регистрация прошла успешно!", Snackbar.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь с именем " + name
                                + " и почтой " + email + " не был зарегистрирован! Ошибка: " + e.getMessage());
                        mainView.showSnackBarForDialog("Ошибка при регистрации: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });

        User user = setDefaultDataForUser(email, name, surname, phone);
        addUserInfo(user);
    }

    private void addUserInfo(User user) {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).setValue(user);
                LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " был добавлен в базу!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private User setDefaultDataForUser(String email, String name, String surname, String phone) {
        return new User() {{
            setId(users.getKey());
            setEmail(email);
            setPhone(phone);
            setName(name);
            setSurname(surname);
            setDateOfBirth("Не указано");
            setSexOfAPerson("Не указано");
            setFieldOfActivity("Не указано");
            setInterests(Collections.singletonList("Не указано"));
        }};
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
