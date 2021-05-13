package com.datastructures.presenter.settings;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.datastructures.R;
import com.datastructures.model.User;
import com.datastructures.view.settings.SettingsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import static android.content.ContentValues.TAG;

public class SettingsPresenterImpl implements SettingsPresenter {
    private static final String USER_KEY = "User";
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
    public void checkData() {
        Log.d(TAG, "Пользователь " + database.getReference("Default").get() + "!");
        users.push().setValue(setDefaultDataForUser());
//        users.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    users.push().setValue(setDefaultDataForUser());
//                    Log.d(TAG, "Пользователя в БД НЕТ!");
//                }
//            }
//        });
    }

    @Override
    public Map<String, String> getValues() {
        String email = firebaseAuth.getCurrentUser().getEmail() != null ? firebaseAuth.getCurrentUser().getEmail() : "Почта не указана!";
        String phone = firebaseAuth.getCurrentUser().getPhoneNumber() != null ? firebaseAuth.getCurrentUser().getPhoneNumber() : "Телефон не указан!";

        Map<String, String> values = new HashMap<>();
        values.put("email", email);
        values.put("phone", phone);

        return values;
    }

    @Override
    public void showEditEmailWindow() {
        AlertDialog.Builder dialog = settingsView.getAlertDialog("Редактирование электронной почты",
                "Пожалуйста, заполните форму!");
        View registerWindow = settingsView.getViewForDialog(R.layout.edit_email);
        dialog.setView(registerWindow);

        MaterialEditText oldEmail = registerWindow.findViewById(R.id.oldEmailEditField);
        MaterialEditText newEmail = registerWindow.findViewById(R.id.newEmailEditField);
        MaterialEditText password = registerWindow.findViewById(R.id.passFieldForEditEmail);

        dialog.setNegativeButton("НАЗАД", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!checkFields(oldEmail, newEmail, password)) {
                    return;
                }

                editEmail(oldEmail, newEmail, password);
            }
        });

        dialog.show();
    }

    @Override
    public void showEditPasswordWindow() {
        AlertDialog.Builder dialog = settingsView.getAlertDialog("Редактирование пароля",
                "Пожалуйста, заполните форму!");
        View registerWindow = settingsView.getViewForDialog(R.layout.edit_password);
        dialog.setView(registerWindow);

        MaterialEditText email = registerWindow.findViewById(R.id.emailForEditPassowrd);
        MaterialEditText oldPassword = registerWindow.findViewById(R.id.oldPasswordForEditPassword);
        MaterialEditText newPassword = registerWindow.findViewById(R.id.newPasswordForEditPassword);

        dialog.setNegativeButton("НАЗАД", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!checkFields(email, oldPassword, newPassword)) {
                    return;
                }

                editPassword(email, oldPassword, newPassword);
            }
        });

        dialog.show();
    }

    @Override
    public void showEditPhoneWindow() {
        AlertDialog.Builder dialog = settingsView.getAlertDialog("Редактирование номера телефона",
                "Пожалуйста, заполните форму!");
        View registerWindow = settingsView.getViewForDialog(R.layout.edit_phone);
        dialog.setView(registerWindow);

        MaterialEditText email = registerWindow.findViewById(R.id.emailForEditPhone);
        MaterialEditText password = registerWindow.findViewById(R.id.passwordForEditPhone);
        MaterialEditText oldPhone = registerWindow.findViewById(R.id.oldPhoneForEdit);
        MaterialEditText newPhone = registerWindow.findViewById(R.id.newPhoneForEdit);

        dialog.setNegativeButton("НАЗАД", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!checkFields(email, password, oldPhone, newPhone)) {
                    return;
                }

                editPhone(email, password, oldPhone, newPhone);
            }
        });

        dialog.show();
    }

    private boolean checkFields(MaterialEditText... fields) {
        for (MaterialEditText field : fields) {
            if (TextUtils.isEmpty(field.getText().toString())) {
                settingsView.showSnackBarForDialog("Пожалуйста, заполните форму редактирования!", Snackbar.LENGTH_LONG);
                return false;
            }
        }
        return true;
    }

    private void editEmail(MaterialEditText oldEmail, MaterialEditText newEmail, MaterialEditText password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(oldEmail.getText().toString(), password.getText().toString());
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Повторная аутентификация пользователя " + user.getEmail());

                        user.updateEmail(newEmail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Пользователь " + oldEmail.getText().toString()
                                                    + " изменил адрес электронной почты на новый: " + newEmail.getText().toString() + "!");
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + oldEmail.getText().toString()
                                + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void editPassword(MaterialEditText email, MaterialEditText oldPassword, MaterialEditText newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email.getText().toString(), oldPassword.getText().toString());
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Повторная аутентификация пользователя " + user.getEmail());

                        user.updatePassword(newPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Пользователь " + email.getText().toString()
                                                    + " изменил свой пароль!");
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email.getText().toString()
                                + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void editPhone(MaterialEditText email, MaterialEditText password, MaterialEditText oldPhone, MaterialEditText newPhone) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email.getText().toString(), password.getText().toString());
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Повторная аутентификация пользователя " + user.getEmail());

//                        user.updatePhoneNumber(PhoneAuthCredential.zzb())
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Log.d(TAG, "Пользователь " + email.getText().toString()
//                                                    + " изменил свой пароль!");
//                                        }
//                                    }
//                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LOGGER.log(Level.WARNING, "Пользователь " + email.getText().toString()
                                + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
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
