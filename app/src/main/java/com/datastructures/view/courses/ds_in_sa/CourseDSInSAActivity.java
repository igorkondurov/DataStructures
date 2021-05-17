package com.datastructures.view.courses.ds_in_sa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.model.enums.dsinsa.DSInSAModule;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenter;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenterImpl;
import com.datastructures.view.training.TrainingActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class CourseDSInSAActivity extends AppCompatActivity implements CourseDSInSAView {

    private static final String MODULE = "МОДУЛЬ";
    private static final String TEST_MODULE = "ТЕСТИРОВАНИЕ ПО МОДУЛЮ";
    private RelativeLayout root;
    private CourseDSInSAPresenter courseDSInSAPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dsinsa_homepage);

        root = findViewById(R.id.root_element);

        courseDSInSAPresenter = new CourseDSInSAPresenterImpl(this);

        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);
        AppCompatButton btnModule1 = findViewById(R.id.btnModule1);
        AppCompatButton btnModule2 = findViewById(R.id.btnModule2);
        AppCompatButton btnModule3 = findViewById(R.id.btnModule3);
        AppCompatButton btnModule4 = findViewById(R.id.btnModule4);
        AppCompatButton btnModule5 = findViewById(R.id.btnModule5);
        AppCompatButton btnModule6 = findViewById(R.id.btnModule6);
        AppCompatButton btnModule7 = findViewById(R.id.btnModule7);
        AppCompatButton btnModule8 = findViewById(R.id.btnModule8);

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });

        btnModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_1);
            }
        });

        btnModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_2);
            }
        });

        btnModule3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_3);
            }
        });

        btnModule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_4);
            }
        });

        btnModule5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_5);
            }
        });

        btnModule6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_6);
            }
        });

        btnModule7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_7);
            }
        });

        btnModule8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toModule(DSInSAModule.MODULE_8);
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
        startActivity(new Intent(CourseDSInSAActivity.this, TrainingActivity.class));
        finish();
    }

    private void toModule(DSInSAModule module) {
        AlertDialog.Builder dialogHomePage = getAlertDialog(MODULE + " " + module.getOrder(),
                module.getName());
        View registerWindow = getViewForDialog(R.layout.dsinsa_template_module);
        dialogHomePage.setView(registerWindow);

        Button btnClose = registerWindow.findViewById(R.id.btnClose);
        Button btnGoToTest = registerWindow.findViewById(R.id.btnGoToTest);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        btnGoToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToModuleTest(module);
            }
        });

        dialogHomePage.show();
    }

    private void goToModuleTest(DSInSAModule module) {
        AlertDialog.Builder dialogTestPage = getAlertDialog(TEST_MODULE + " "
                        + module.getOrder() + "«" + module.getName() + "»",
                "Выберите один правильный вариант ответа в каждом задании и в конце нажмите кнопку \"Перейти к проверке\"");
        View registerWindow = getViewForDialog(R.layout.dsinsa_template_module_test);
        dialogTestPage.setView(registerWindow);

        Button btnCloseTest = registerWindow.findViewById(R.id.btnCloseTest);
        Button btnGoToResult = registerWindow.findViewById(R.id.btnGoToResultTest);

        btnCloseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        btnGoToResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        dialogTestPage.show();
    }
}
