package com.datastructures.presenter.courses.ds_in_sa;

import android.view.View;
import android.widget.Button;

import com.datastructures.R;
import com.datastructures.model.enums.dsinsa.DSInSAModule;
import com.datastructures.presenter.homepage.HomePagePresenterImpl;
import com.datastructures.view.courses.ds_in_sa.CourseDSInSAView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

import androidx.appcompat.app.AlertDialog;

public class CourseDSInSAPresenterImpl implements CourseDSInSAPresenter {
    private static final String MODULE = "МОДУЛЬ";
    private static final String TEST_MODULE = "ТЕСТИРОВАНИЕ ПО МОДУЛЮ";
    private final CourseDSInSAView courseDSInSAView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;

    public CourseDSInSAPresenterImpl(CourseDSInSAView courseDSInSAView) {
        this.courseDSInSAView = courseDSInSAView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        LOGGER = Logger.getLogger(HomePagePresenterImpl.class.getName());
    }

    @Override
    public void toModule(DSInSAModule module) {
        AlertDialog.Builder dialogHomePage = courseDSInSAView.getAlertDialog(MODULE + " " + module.getOrder(),
                module.getName());
        View registerWindow = courseDSInSAView.getViewForDialog(R.layout.dsinsa_template_module);
        dialogHomePage.setView(registerWindow);

        Button btnClose = registerWindow.findViewById(R.id.btnClose);
        Button btnGoToTest = registerWindow.findViewById(R.id.btnGoToTest);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDSInSAView.restartCurrentActivity();
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
        AlertDialog.Builder dialogTestPage = courseDSInSAView.getAlertDialog(TEST_MODULE + " "
                        + module.getOrder() + "«" + module.getName() + "»",
                "Выберите один правильный вариант ответа в каждом задании и в конце нажмите кнопку \"Перейти к проверке\"");
        View registerWindow = courseDSInSAView.getViewForDialog(R.layout.dsinsa_template_module_test);
        dialogTestPage.setView(registerWindow);

        Button btnCloseTest = registerWindow.findViewById(R.id.btnCloseTest);
        Button btnGoToResult = registerWindow.findViewById(R.id.btnGoToResultTest);

        btnCloseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDSInSAView.restartCurrentActivity();
            }
        });

        btnGoToResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDSInSAView.restartCurrentActivity();
            }
        });

        dialogTestPage.show();
    }
}
