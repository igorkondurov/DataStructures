package com.datastructures.presenter.main;

import android.text.TextUtils;

import com.datastructures.model.User;
import com.datastructures.view.main.MainView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public boolean checkFields(String email, String password, String name, String phone) {
        if (TextUtils.isEmpty(email)) {
            mainView.showSnackBarForDialog("Введите адрес электронной почты!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (password.length() < 7) {
            mainView.showSnackBarForDialog("Введите пароль, содержащий более 7 символов!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            mainView.showSnackBarForDialog("Введите имя!", Snackbar.LENGTH_LONG);
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            mainView.showSnackBarForDialog("Введите номер телефона!", Snackbar.LENGTH_LONG);
            return false;
        }

        return true;
    }

    @Override
    public void createUser(String email, String password, String name, String surname, String phone) {
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

    @Override
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email + " успешно прошёл аутентификацию!");
                        mainView.transferToHomePage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email
                                + " не прошёл аутентификацию! Ошибка: " + e.getMessage());
                        mainView.showSnackBarForDialog("Ошибка авторизации: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void addUserInfo(User user) {
        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).setValue(user);
                LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " был добавлен в базу!");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private User setDefaultDataForUser(String email, String name, String surname, String phone) {
        LOGGER.log(Level.INFO, "В коллекцию \"Users\" Firebase был добавлен документ пользователя " + email + " с дефолтными значениями!");
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
}
