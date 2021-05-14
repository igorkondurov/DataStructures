package com.datastructures.presenter.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.datastructures.R;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    public void showEditEmailWindow() {
        AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить почту",
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
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить пароль",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_password);
                dialog.setView(registerWindow);

                MaterialEditText email = registerWindow.findViewById(R.id.emailForEditPassowrd);
                email.setText(data.getEmail());
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

                        editPassword(email.getText().toString(), oldPassword.getText().toString(), newPassword.getText().toString(), data);
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditPhoneWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить номер телефона",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_phone);
                dialog.setView(registerWindow);

                MaterialEditText oldPhone = registerWindow.findViewById(R.id.oldPhoneForEdit);
                oldPhone.setText(data.getPhone());
                MaterialEditText newPhone = registerWindow.findViewById(R.id.newPhoneForEdit);
                MaterialEditText password = registerWindow.findViewById(R.id.passwordForEditPhone);

                dialog.setNegativeButton("НАЗАД", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.setPositiveButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!checkFields(password, oldPhone, newPhone)) {
                            return;
                        }

                        editPhone(password.getText().toString(), oldPhone.getText().toString(), newPhone.getText().toString(), data);
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditNamesWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить персональные данные",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_name_and_surname);
                dialog.setView(registerWindow);

                MaterialEditText nameForNSEdit = registerWindow.findViewById(R.id.nameForNSEdit);
                nameForNSEdit.setText(data.getName());
                MaterialEditText surnameForNSEdit = registerWindow.findViewById(R.id.surnameForNSEdit);
                surnameForNSEdit.setText(data.getSurname());

                dialog.setNegativeButton("НАЗАД", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.setPositiveButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!checkFields(nameForNSEdit, surnameForNSEdit)) {
                            return;
                        }

                        editNameAndSurname(nameForNSEdit.getText().toString(), surnameForNSEdit.getText().toString(), data);
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditSexOfAPersonWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить пол",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_sex_of_a_person);
                dialog.setView(registerWindow);

                View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioButton rb = (RadioButton) v;
                        switch (rb.getId()) {
                            case R.id.sexMen:
                                editSexOfAPerson("Мужской", data);
                                break;
                            case R.id.sexFemale:
                                editSexOfAPerson("Женский", data);
                                break;
                            case R.id.sexOther:
                                editSexOfAPerson("Другое", data);
                                break;
                            case R.id.sexNotIndicated:
                                editSexOfAPerson("Не указано", data);
                                break;

                            default:
                                break;
                        }
                    }
                };

                RadioButton redRadioButton = registerWindow.findViewById(R.id.sexMen);
                redRadioButton.setOnClickListener(radioButtonClickListener);

                RadioButton greenRadioButton = registerWindow.findViewById(R.id.sexFemale);
                greenRadioButton.setOnClickListener(radioButtonClickListener);

                RadioButton blueRadioButton = registerWindow.findViewById(R.id.sexOther);
                blueRadioButton.setOnClickListener(radioButtonClickListener);

                RadioButton grayRadioButton = registerWindow.findViewById(R.id.sexNotIndicated);
                grayRadioButton.setOnClickListener(radioButtonClickListener);

                dialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditDateOfBirthdayWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить дату рождения",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_date_of_birthday);
                dialog.setView(registerWindow);

                TextView dateTextViewEdit = registerWindow.findViewById(R.id.dateTextViewEdit);
                dateTextViewEdit.setText(data.getDateOfBirth());
                DatePicker datePicker = registerWindow.findViewById(R.id.dateOfBirthdayEdit);

                datePicker.init(2021, 01, 01, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateTextViewEdit.setText("Дата рождения: " + view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
                        editDateOfBirthday(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear(), data);
                    }
                });

                dialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditCategoryWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить профессиональную сферу деятельности",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_category);
                dialog.setView(registerWindow);

                Spinner spinner = registerWindow.findViewById(R.id.categorySpinner);

                AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) parent.getItemAtPosition(position);
                        editCategory(item, data);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                };
                spinner.setOnItemSelectedListener(itemSelectedListener);

                dialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void showEditInterestsWindow() {
        getUserDataFromRealDatabase(new CallbackHelper<User>() {
            @Override
            public void callback(User data) {
                AlertDialog.Builder dialog = settingsView.getAlertDialog("Изменить профессиональную сферу деятельности",
                        "Пожалуйста, заполните форму!");
                View registerWindow = settingsView.getViewForDialog(R.layout.edit_interests);
                dialog.setView(registerWindow);

                List<String> interests = new ArrayList<>();

                StringBuilder stringBuilder = new StringBuilder();
                for (String element : data.getInterests()) {
                    stringBuilder.append("#").append(element).append("\n");
                }
                TextView interestTextViewEdit = registerWindow.findViewById(R.id.interestTextViewEdit);
                interestTextViewEdit.setText(stringBuilder);

                Button btnInterestSave = registerWindow.findViewById(R.id.btnInterestSave);
                Button btnInterestCancel = registerWindow.findViewById(R.id.btnInterestCancel);

                ListView listOfInterests = registerWindow.findViewById(R.id.listInterests);
                listOfInterests.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        (Context) settingsView, R.array.interests,
                        android.R.layout.simple_list_item_multiple_choice);

                listOfInterests.setAdapter(adapter);

                btnInterestSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SparseBooleanArray sbArray = listOfInterests.getCheckedItemPositions();
                        String[] resources = ((Context) settingsView).getResources().getStringArray(R.array.interests);
                        for (int index = 0; index < sbArray.size(); index++) {
                            int key = sbArray.keyAt(index);
                            if (sbArray.get(key)) {
                                interests.add(resources[key]);
                            }
                        }

                        if (interests.isEmpty()) {
                            return;
                        }

                        editInterests(interests, data);
                        settingsView.restartCurrentActivity();
                    }
                });

                btnInterestCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingsView.restartCurrentActivity();
                    }
                });

                dialog.show();
            }
        });
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
                        LOGGER.log(Level.INFO, "Повторная аутентификация пользователя " + user.getEmail());

                        user.updateEmail(newEmail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            LOGGER.log(Level.INFO, "Пользователь " + oldEmail.getText().toString()
                                                    + " изменил адрес электронной почты на новый: " + newEmail.getText().toString() + "!");
                                            users.child(oldEmail.getText().toString().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    User oldUser = snapshot.getValue(User.class);
                                                    if (oldUser != null) {
                                                        oldUser.setEmail(newEmail.getText().toString());
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
                        LOGGER.log(Level.WARNING, "Пользователь " + oldEmail.getText().toString()
                                + " ввёл некорректные данные! Ошибка: " + e.getMessage());
                        settingsView.showSnackBarForDialog("Введены некорректные данные: " + e.getMessage(), Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void editPassword(String email, String oldPassword, String newPassword, User data) {
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

    private void editPhone(String password, String oldPhone, String newPhone, User data) {
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

    private void editNameAndSurname(String name, String surname, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("name").setValue(name);
        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("surname").setValue(surname);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    private void editSexOfAPerson(String sexOfAPerson, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("sexOfAPerson").setValue(sexOfAPerson);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные (Пол)");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    private void editDateOfBirthday(String dateOfBirthday, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("dateOfBirth").setValue(dateOfBirthday);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил свои персональные данные (Дата рождения)");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    private void editCategory(String fieldOfActivity, User data) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        users.child(user.getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", ""))
                .child("fieldOfActivity").setValue(fieldOfActivity);
        LOGGER.log(Level.INFO, "Пользователь " + user.getEmail() + " изменил сферу деятельности/ категорию");
        settingsView.showSnackBarForDialog("Данные успешно изменены!", Snackbar.LENGTH_LONG);
    }

    private void editInterests(List<String> interests, User data) {
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
