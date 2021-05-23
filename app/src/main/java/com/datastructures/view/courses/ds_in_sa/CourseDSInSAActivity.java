package com.datastructures.view.courses.ds_in_sa;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datastructures.R;
import com.datastructures.model.CourseStatistics;
import com.datastructures.model.ModuleStatistics;
import com.datastructures.model.TestOfModule;
import com.datastructures.model.enums.SectionOfTrainingCatalog;
import com.datastructures.model.enums.dsinsa.DSInSAModuleTerm;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenter;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenterImpl;
import com.datastructures.view.training.TrainingActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import lombok.SneakyThrows;

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
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_1);
            }
        });

        btnModule2.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_2);
            }
        });

        btnModule3.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_3);
            }
        });

        btnModule4.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_4);
            }
        });

        btnModule5.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_5);
            }
        });

        btnModule6.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_6);
            }
        });

        btnModule7.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_7);
            }
        });

        btnModule8.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                toModule(DSInSAModuleTerm.MODULE_8);
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

    private void toModule(DSInSAModuleTerm module) throws IOException {
        AlertDialog.Builder dialogHomePage = getAlertDialog(MODULE + " " + module.getOrder(),
                module.getName());
        View registerWindow = getViewForDialog(R.layout.dsinsa_template_module);
        dialogHomePage.setView(registerWindow);

        String jsonText = courseDSInSAPresenter.readTextFromJson(this, R.raw.data);

        String text = courseDSInSAPresenter.getText(SectionOfTrainingCatalog.DATA_STRUCTURES_IN_SYSTEM_ANALYTICS, module, jsonText);

        TextView moduleLectureText = registerWindow.findViewById(R.id.moduleLectureText);
        moduleLectureText.setText(Html.fromHtml(text));
        Button btnClose = registerWindow.findViewById(R.id.btnClose);
        Button btnGoToTest = registerWindow.findViewById(R.id.btnGoToTest);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        btnGoToTest.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                goToModuleTest(module);
            }
        });

        dialogHomePage.show();
    }

    private void goToModuleTest(DSInSAModuleTerm module) throws IOException {
        AlertDialog.Builder dialogTestPage = getAlertDialog(TEST_MODULE + " "
                        + module.getOrder() + "«" + module.getName() + "»",
                "Выберите один правильный вариант ответа в каждом задании и в конце нажмите кнопку \"Перейти к проверке\"");
        View registerWindow = getViewForDialog(R.layout.dsinsa_template_module_test);
        dialogTestPage.setView(registerWindow);

        String jsonText = courseDSInSAPresenter.readTextFromJson(this, R.raw.data);

        List<TestOfModule> questions = courseDSInSAPresenter.getQuestions(SectionOfTrainingCatalog.DATA_STRUCTURES_IN_SYSTEM_ANALYTICS, module, jsonText);
        ModuleStatistics moduleStatistics = new ModuleStatistics();

        Button btnCloseTest = registerWindow.findViewById(R.id.btnCloseTest);
        Button btnGoToResult = registerWindow.findViewById(R.id.btnGoToResultTest);

        // 1 question

        TextView question1 = registerWindow.findViewById(R.id.question1);
        question1.setText(Html.fromHtml("<b>1.</b> " + questions.get(0).getTextOfQuestion()));

        RadioGroup rgQuestion1 = registerWindow.findViewById(R.id.rgQuestion1);

        RadioButton rg1Rb1 = registerWindow.findViewById(R.id.rbQuestion1Option1);
        rg1Rb1.setText(questions.get(0).getAnswerOptions().get(0));

        RadioButton rg1Rb2 = registerWindow.findViewById(R.id.rbQuestion1Option2);
        rg1Rb2.setText(questions.get(0).getAnswerOptions().get(1));

        RadioButton rg1Rb3 = registerWindow.findViewById(R.id.rbQuestion1Option3);
        rg1Rb3.setText(questions.get(0).getAnswerOptions().get(2));

        RadioButton rg1Rb4 = registerWindow.findViewById(R.id.rbQuestion1Option4);
        rg1Rb4.setText(questions.get(0).getAnswerOptions().get(3));

        // 2 question

        TextView question2 = registerWindow.findViewById(R.id.question2);
        question2.setText(Html.fromHtml("<b>2.</b> " + questions.get(1).getTextOfQuestion()));

        RadioGroup rgQuestion2 = registerWindow.findViewById(R.id.rgQuestion2);

        RadioButton rg2Rb1 = registerWindow.findViewById(R.id.rbQuestion2Option1);
        rg2Rb1.setText(questions.get(1).getAnswerOptions().get(0));

        RadioButton rg2Rb2 = registerWindow.findViewById(R.id.rbQuestion2Option2);
        rg2Rb2.setText(questions.get(1).getAnswerOptions().get(1));

        RadioButton rg2Rb3 = registerWindow.findViewById(R.id.rbQuestion2Option3);
        rg2Rb3.setText(questions.get(1).getAnswerOptions().get(2));

        RadioButton rg2Rb4 = registerWindow.findViewById(R.id.rbQuestion2Option4);
        rg2Rb4.setText(questions.get(1).getAnswerOptions().get(3));

        // 3 question

        TextView question3 = registerWindow.findViewById(R.id.question3);
        question3.setText(Html.fromHtml("<b>3.</b> " + questions.get(2).getTextOfQuestion()));

        RadioGroup rgQuestion3 = registerWindow.findViewById(R.id.rgQuestion3);

        RadioButton rg3Rb1 = registerWindow.findViewById(R.id.rbQuestion3Option1);
        rg3Rb1.setText(questions.get(2).getAnswerOptions().get(0));

        RadioButton rg3Rb2 = registerWindow.findViewById(R.id.rbQuestion3Option2);
        rg3Rb2.setText(questions.get(2).getAnswerOptions().get(1));

        RadioButton rg3Rb3 = registerWindow.findViewById(R.id.rbQuestion3Option3);
        rg3Rb3.setText(questions.get(2).getAnswerOptions().get(2));

        RadioButton rg3Rb4 = registerWindow.findViewById(R.id.rbQuestion3Option4);
        rg3Rb4.setText(questions.get(2).getAnswerOptions().get(3));

        // 4 question

        TextView question4 = registerWindow.findViewById(R.id.question4);
        question4.setText(Html.fromHtml("<b>4.</b> " + questions.get(3).getTextOfQuestion()));

        RadioGroup rgQuestion4 = registerWindow.findViewById(R.id.rgQuestion4);

        RadioButton rg4Rb1 = registerWindow.findViewById(R.id.rbQuestion4Option1);
        rg4Rb1.setText(questions.get(3).getAnswerOptions().get(0));

        RadioButton rg4Rb2 = registerWindow.findViewById(R.id.rbQuestion4Option2);
        rg4Rb2.setText(questions.get(3).getAnswerOptions().get(1));

        RadioButton rg4Rb3 = registerWindow.findViewById(R.id.rbQuestion4Option3);
        rg4Rb3.setText(questions.get(3).getAnswerOptions().get(2));

        RadioButton rg4Rb4 = registerWindow.findViewById(R.id.rbQuestion4Option4);
        rg4Rb4.setText(questions.get(3).getAnswerOptions().get(3));

        // 5 question

        TextView question5 = registerWindow.findViewById(R.id.question5);
        question5.setText(Html.fromHtml("<b>5.</b> " + questions.get(4).getTextOfQuestion()));

        RadioGroup rgQuestion5 = registerWindow.findViewById(R.id.rgQuestion5);

        RadioButton rg5Rb1 = registerWindow.findViewById(R.id.rbQuestion5Option1);
        rg5Rb1.setText(questions.get(4).getAnswerOptions().get(0));

        RadioButton rg5Rb2 = registerWindow.findViewById(R.id.rbQuestion5Option2);
        rg5Rb2.setText(questions.get(4).getAnswerOptions().get(1));

        RadioButton rg5Rb3 = registerWindow.findViewById(R.id.rbQuestion5Option3);
        rg5Rb3.setText(questions.get(4).getAnswerOptions().get(2));

        RadioButton rg5Rb4 = registerWindow.findViewById(R.id.rbQuestion5Option4);
        rg5Rb4.setText(questions.get(4).getAnswerOptions().get(3));

        btnCloseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCurrentActivity();
            }
        });

        btnGoToResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedOptionQuestion1 = rgQuestion1.getCheckedRadioButtonId();

                boolean isTrueQuestion1 = false;
                String userOptionQuestion1 = "";

                switch (selectedOptionQuestion1) {
                    case R.id.rbQuestion1Option1:
                        isTrueQuestion1 = courseDSInSAPresenter.checkAnswer(rg1Rb1.getText().toString(), questions.get(0));
                        userOptionQuestion1 = rg1Rb1.getText().toString();

                        break;
                    case R.id.rbQuestion1Option2:
                        isTrueQuestion1 = courseDSInSAPresenter.checkAnswer(rg1Rb2.getText().toString(), questions.get(0));
                        userOptionQuestion1 = rg1Rb2.getText().toString();
                        break;
                    case R.id.rbQuestion1Option3:
                        isTrueQuestion1 = courseDSInSAPresenter.checkAnswer(rg1Rb3.getText().toString(), questions.get(0));
                        userOptionQuestion1 = rg1Rb3.getText().toString();
                        break;
                    case R.id.rbQuestion1Option4:
                        isTrueQuestion1 = courseDSInSAPresenter.checkAnswer(rg1Rb4.getText().toString(), questions.get(0));
                        userOptionQuestion1 = rg1Rb4.getText().toString();
                        break;

                    default:
                        break;
                }

                int selectedOptionQuestion2 = rgQuestion2.getCheckedRadioButtonId();

                boolean isTrueQuestion2 = false;
                String userOptionQuestion2 = "";

                switch (selectedOptionQuestion2) {
                    case R.id.rbQuestion2Option1:
                        isTrueQuestion2 = courseDSInSAPresenter.checkAnswer(rg2Rb1.getText().toString(), questions.get(1));
                        userOptionQuestion2 = rg2Rb1.getText().toString();
                        break;
                    case R.id.rbQuestion2Option2:
                        isTrueQuestion2 = courseDSInSAPresenter.checkAnswer(rg2Rb2.getText().toString(), questions.get(1));
                        userOptionQuestion2 = rg2Rb2.getText().toString();
                        break;
                    case R.id.rbQuestion2Option3:
                        isTrueQuestion2 = courseDSInSAPresenter.checkAnswer(rg2Rb3.getText().toString(), questions.get(1));
                        userOptionQuestion2 = rg2Rb3.getText().toString();
                        break;
                    case R.id.rbQuestion2Option4:
                        isTrueQuestion2 = courseDSInSAPresenter.checkAnswer(rg2Rb4.getText().toString(), questions.get(1));
                        userOptionQuestion2 = rg2Rb4.getText().toString();
                        break;

                    default:
                        break;
                }

                int selectedOptionQuestion3 = rgQuestion3.getCheckedRadioButtonId();

                boolean isTrueQuestion3 = false;
                String userOptionQuestion3 = "";

                switch (selectedOptionQuestion3) {
                    case R.id.rbQuestion3Option1:
                        isTrueQuestion3 = courseDSInSAPresenter.checkAnswer(rg3Rb1.getText().toString(), questions.get(2));
                        userOptionQuestion3 = rg3Rb1.getText().toString();
                        break;
                    case R.id.rbQuestion3Option2:
                        isTrueQuestion3 = courseDSInSAPresenter.checkAnswer(rg3Rb2.getText().toString(), questions.get(2));
                        userOptionQuestion3 = rg3Rb2.getText().toString();
                        break;
                    case R.id.rbQuestion3Option3:
                        isTrueQuestion3 = courseDSInSAPresenter.checkAnswer(rg3Rb3.getText().toString(), questions.get(2));
                        userOptionQuestion3 = rg3Rb3.getText().toString();
                        break;
                    case R.id.rbQuestion3Option4:
                        isTrueQuestion3 = courseDSInSAPresenter.checkAnswer(rg3Rb4.getText().toString(), questions.get(2));
                        userOptionQuestion3 = rg3Rb4.getText().toString();
                        break;

                    default:
                        break;
                }

                int selectedOptionQuestion4 = rgQuestion4.getCheckedRadioButtonId();

                boolean isTrueQuestion4 = false;
                String userOptionQuestion4 = "";

                switch (selectedOptionQuestion4) {
                    case R.id.rbQuestion4Option1:
                        isTrueQuestion4 = courseDSInSAPresenter.checkAnswer(rg4Rb1.getText().toString(), questions.get(3));
                        userOptionQuestion4 = rg4Rb1.getText().toString();
                        break;
                    case R.id.rbQuestion4Option2:
                        isTrueQuestion4 = courseDSInSAPresenter.checkAnswer(rg4Rb2.getText().toString(), questions.get(3));
                        userOptionQuestion4 = rg4Rb2.getText().toString();
                        break;
                    case R.id.rbQuestion4Option3:
                        isTrueQuestion4 = courseDSInSAPresenter.checkAnswer(rg4Rb3.getText().toString(), questions.get(3));
                        userOptionQuestion4 = rg4Rb3.getText().toString();
                        break;
                    case R.id.rbQuestion4Option4:
                        isTrueQuestion4 = courseDSInSAPresenter.checkAnswer(rg4Rb4.getText().toString(), questions.get(3));
                        userOptionQuestion4 = rg4Rb4.getText().toString();
                        break;

                    default:
                        break;
                }

                int selectedOptionQuestion5 = rgQuestion5.getCheckedRadioButtonId();

                boolean isTrueQuestion5 = false;
                String userOptionQuestion5 = "";

                switch (selectedOptionQuestion5) {
                    case R.id.rbQuestion5Option1:
                        isTrueQuestion5 = courseDSInSAPresenter.checkAnswer(rg5Rb1.getText().toString(), questions.get(4));
                        userOptionQuestion5 = rg5Rb1.getText().toString();
                        break;
                    case R.id.rbQuestion5Option2:
                        isTrueQuestion5 = courseDSInSAPresenter.checkAnswer(rg5Rb2.getText().toString(), questions.get(4));
                        userOptionQuestion5 = rg5Rb2.getText().toString();
                        break;
                    case R.id.rbQuestion5Option3:
                        isTrueQuestion5 = courseDSInSAPresenter.checkAnswer(rg5Rb3.getText().toString(), questions.get(4));
                        userOptionQuestion5 = rg5Rb3.getText().toString();
                        break;
                    case R.id.rbQuestion5Option4:
                        isTrueQuestion5 = courseDSInSAPresenter.checkAnswer(rg5Rb4.getText().toString(), questions.get(4));
                        userOptionQuestion5 = rg5Rb4.getText().toString();
                        break;

                    default:
                        break;
                }

                boolean finalIsTrueQuestion = isTrueQuestion1;
                boolean finalIsTrueQuestion1 = isTrueQuestion2;
                boolean finalIsTrueQuestion2 = isTrueQuestion3;
                boolean finalIsTrueQuestion3 = isTrueQuestion4;
                boolean finalIsTrueQuestion4 = isTrueQuestion5;
                String finalUserOptionQuestion = userOptionQuestion1;
                String finalUserOptionQuestion1 = userOptionQuestion2;
                String finalUserOptionQuestion2 = userOptionQuestion3;
                String finalUserOptionQuestion3 = userOptionQuestion4;
                String finalUserOptionQuestion4 = userOptionQuestion5;

                moduleStatistics.setFlags(Arrays.asList(finalIsTrueQuestion, finalIsTrueQuestion1, finalIsTrueQuestion2, finalIsTrueQuestion3, finalIsTrueQuestion4));
                moduleStatistics.setQuestions(questions);
                moduleStatistics.setUserResponses(Arrays.asList(finalUserOptionQuestion, finalUserOptionQuestion1, finalUserOptionQuestion2, finalUserOptionQuestion3, finalUserOptionQuestion4));

                if (courseDSInSAPresenter.checkCountFlags(moduleStatistics.getFlags())) {
                    CourseStatistics courseStatistics = new CourseStatistics() {{
                        setModuleProgress(Collections.singletonMap(module.toString(), true));
                        setModuleStatistics(Collections.singletonMap(module.toString(), moduleStatistics));
                    }};

                    courseDSInSAPresenter.addUserStatisticsOfCourse(courseStatistics, module);
                    restartCurrentActivity();
                }

                restartCurrentActivity();
            }
        });

        dialogTestPage.show();
    }
}
