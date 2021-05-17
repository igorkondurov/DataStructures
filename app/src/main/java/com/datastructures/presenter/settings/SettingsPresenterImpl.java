package com.datastructures.presenter.settings;

import android.text.TextUtils;

import com.datastructures.model.User;
import com.datastructures.view.CallbackHelper;
import com.datastructures.view.settings.SettingsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import lombok.SneakyThrows;

public class SettingsPresenterImpl implements SettingsPresenter {
    private static final String USER_KEY = "Users";
    private final SettingsView settingsView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;

    public SettingsPresenterImpl(SettingsView settingsView) {
        this.settingsView = settingsView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference(USER_KEY);
        LOGGER = Logger.getLogger(SettingsPresenterImpl.class.getName());
    }

    @Override
    public void getUserInfo(@NonNull CallbackHelper<User> finishedCallback) {
        getUserDataFromRealDatabase(finishedCallback);
    }

    private void getUserDataFromRealDatabase(@NonNull CallbackHelper<User> finishedCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @SneakyThrows
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    LOGGER.log(Level.INFO, "Получение данных о пользователе " + user.getEmail());
                    finishedCallback.callback(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        };

        users.child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).addListenerForSingleValueEvent(valueEventListener);
    }

    /**
     * @Deprecated Функционал на текущий момент неактуален
     */
    @Deprecated
    @Override
    public void updateEMail() {

    }

    @Override
    public void updatePasswordWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updatePasswordWindow(data);
            }
        });
    }

    @Override
    public void updatePhoneWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updatePhoneWindow(data);
            }
        });
    }

    @Override
    public void updateNamesWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updateNamesWindow(data);
            }
        });
    }

    @Override
    public void updateSexOfAPersonWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updateSexOfAPersonWindow(data);
            }
        });
    }

    @Override
    public void updateDateOfBirthdayWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updateDateOfBirthdayWindow(data);
            }
        });
    }

    @Override
    public void updateCategoryWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updateCategoryWindow(data);
            }
        });
    }

    @Override
    public void updateInterestsWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                settingsView.updateInterestsWindow(data);
            }
        });
    }

    @Override
    public boolean checkFields(String... fields) {
        for (String field : fields) {
            if (TextUtils.isEmpty(field)) {
                settingsView.showSnackBarForDialog("Пожалуйста, заполните форму редактирования!", Snackbar.LENGTH_LONG);
                return false;
            }
        }

        return true;
    }

    @Override
    public void editEmail(String oldEmail, String newEmail, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(oldEmail, password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        LOGGER.log(Level.INFO, "Повторная аутентификация пользователя " + user.getEmail());

                        user.updateEmail(newEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            LOGGER.log(Level.INFO, "Пользователь " + oldEmail
                                                    + " изменил адрес электронной почты на новый: " + newEmail + "!");
                                            users.child(oldEmail.replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    User oldUser = snapshot.getValue(User.class);
                                                    if (oldUser != null) {
                                                        oldUser.setEmail(newEmail);
                                                        users.child(oldUser.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).setValue(oldUser);
                                                        LOGGER.log(Level.INFO, "Пользователь " + oldUser.getEmail() + " был добавлен в базу!");
                                                    }

                                                    //users.child(oldEmail.getText().toString().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).removeValue();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + oldEmail
                                + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void editPassword(String email, String oldPassword, String newPassword, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldPassword);

        user.reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        LOGGER.log(Level.INFO, "Повторная аутентификация пользователя " + user.getEmail());

                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            LOGGER.log(Level.INFO, "Пользователь " + email + " изменил свой пароль!");
                                            settingsView.showSnackBarForDialog("Пароль успешно изменен!", Snackbar.LENGTH_LONG);
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void editPhone(String password, String oldPhone, String newPhone, User data) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        LOGGER.log(Level.INFO, "Повторная аутентификация пользователя " + user.getEmail());

                        if (data.getPhone().equals(oldPhone)) {
                            users.child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                                    .child("phone").setValue(newPhone);
                            LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свой номер телефона на новый: " + newPhone);
                            settingsView.showSnackBarForDialog("Номер телефона успешно обновлен!", Snackbar.LENGTH_LONG);
                        } else {
                            LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " ввёл некорректные данные!");
                            settingsView.showSnackBarForDialog("Введенные пользовательские данные некорректны!", Snackbar.LENGTH_LONG);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Повторная аутентификация пользователя " + user.getEmail() + " не пройдена! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Повторная аутентификация не пройдена! Ошибка: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void editNameAndSurname(String name, String surname, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("name").setValue(name);
        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("surname").setValue(surname);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    @Override
    public void editSexOfAPerson(String sexOfAPerson, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("sexOfAPerson").setValue(sexOfAPerson);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные (Пол)");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    @Override
    public void editDateOfBirthday(String dateOfBirthday, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("dateOfBirth").setValue(dateOfBirthday);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные (Дата рождения)");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    @Override
    public void editCategory(String fieldOfActivity, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("fieldOfActivity").setValue(fieldOfActivity);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил сферу деятельности/ категорию");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    @Override
    public void editInterests(List<String> interests, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("interests").setValue(interests);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил интересы");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    private User setDefaultDataForUser() {
        return new User() {{
            setId(users.getKey());
            setPhone("88005004545");
            setName("Имя");
            setSurname("Фамилия");
            setDateOfBirth(new Date().toString());
            setSexOfAPerson("Не указано");
            setFieldOfActivity("Не указано");
            setInterests(Collections.singletonList("Не указано"));
        }};
    }
}
