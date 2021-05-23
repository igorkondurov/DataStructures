package com.datastructures.view.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.datastructures.R;
import com.datastructures.model.User;
import com.datastructures.presenter.settings.SettingsPresenter;
import com.datastructures.presenter.settings.SettingsPresenterImpl;
import com.datastructures.view.CallbackHelper;
import com.datastructures.view.homepage.HomePageActivity;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

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
                settingsPresenter.updatePasswordWindow();
            }
        });

        btnPhoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updatePhoneWindow();
            }
        });

        btnNSEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updateNamesWindow();
            }
        });

        btnSexEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updateSexOfAPersonWindow();
            }
        });

        btnDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updateDateOfBirthdayWindow();
            }
        });

        btnCategoryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updateCategoryWindow();
            }
        });

        btnInterestsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.updateInterestsWindow();
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

    @Override
    public void updatePasswordWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить пароль",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_password);
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
                if (!settingsPresenter.checkFields(email.getText().toString(), oldPassword.getText().toString(), newPassword.getText().toString())) {
                    return;
                }

                settingsPresenter.editPassword(email.getText().toString(), oldPassword.getText().toString(), newPassword.getText().toString(), data);
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    public void updatePhoneWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить номер телефона",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_phone);
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
                if (!settingsPresenter.checkFields(password.getText().toString(), oldPhone.getText().toString(), newPhone.getText().toString())) {
                    return;
                }

                settingsPresenter.editPhone(password.getText().toString(), oldPhone.getText().toString(), newPhone.getText().toString(), data);
                showSnackBarForDialog("Номер телефона успешно обновлен!", Snackbar.LENGTH_LONG);
            }
        });

        dialog.show();
    }

    @Override
    public void updateNamesWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить персональные данные",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_name_and_surname);
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
                if (!settingsPresenter.checkFields(nameForNSEdit.getText().toString(), surnameForNSEdit.getText().toString())) {
                    return;
                }

                settingsPresenter.editNameAndSurname(nameForNSEdit.getText().toString(), surnameForNSEdit.getText().toString(), data);
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    public void updateSexOfAPersonWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить пол",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_sex_of_a_person);
        dialog.setView(registerWindow);

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                switch (rb.getId()) {
                    case R.id.sexMen:
                        settingsPresenter.editSexOfAPerson("Мужской", data);
                        break;
                    case R.id.sexFemale:
                        settingsPresenter.editSexOfAPerson("Женский", data);
                        break;
                    case R.id.sexOther:
                        settingsPresenter.editSexOfAPerson("Другое", data);
                        break;
                    case R.id.sexNotIndicated:
                        settingsPresenter.editSexOfAPerson("Не указано", data);
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
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    public void updateDateOfBirthdayWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить дату рождения",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_date_of_birthday);
        dialog.setView(registerWindow);

        TextView dateTextViewEdit = registerWindow.findViewById(R.id.dateTextViewEdit);
        dateTextViewEdit.setText(data.getDateOfBirth());
        DatePicker datePicker = registerWindow.findViewById(R.id.dateOfBirthdayEdit);

        datePicker.init(2021, 01, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTextViewEdit.setText("Дата рождения: " + view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
                settingsPresenter.editDateOfBirthday(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear(), data);
            }
        });

        dialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    public void updateCategoryWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить профессиональную сферу деятельности",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_category);
        dialog.setView(registerWindow);

        Spinner spinner = registerWindow.findViewById(R.id.categorySpinner);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                settingsPresenter.editCategory(item, data);
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
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    public void updateInterestsWindow(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить профессиональную сферу деятельности",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_interests);
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
                getApplicationContext(), R.array.interests,
                android.R.layout.simple_list_item_multiple_choice);

        listOfInterests.setAdapter(adapter);

        btnInterestSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray sbArray = listOfInterests.getCheckedItemPositions();
                String[] resources = (getApplicationContext().getResources().getStringArray(R.array.interests));
                for (int index = 0; index < sbArray.size(); index++) {
                    int key = sbArray.keyAt(index);
                    if (sbArray.get(key)) {
                        interests.add(resources[key]);
                    }
                }

                if (interests.isEmpty()) {
                    return;
                }

                settingsPresenter.editInterests(interests, data);
                restartCurrentActivity();
            }
        });

        btnInterestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        dialog.show();
    }

    @Override
    @Deprecated
    public void updateEmail(User data) {
        AlertDialog.Builder dialog = getAlertDialog("Изменить почту",
                "Пожалуйста, заполните форму!");
        View registerWindow = getViewForDialog(R.layout.edit_email);
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
                if (!settingsPresenter.checkFields(oldEmail.getText().toString(), newEmail.getText().toString(), password.getText().toString())) {
                    return;
                }

                settingsPresenter.editEmail(oldEmail.getText().toString(), newEmail.getText().toString(), password.getText().toString());
            }
        });

        dialog.show();
    }
}
