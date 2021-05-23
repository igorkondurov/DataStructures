package com.datastructures.presenter.courses.ds_in_sa;

import android.content.Context;
import android.os.Build;

import com.datastructures.model.CourseStatistics;
import com.datastructures.model.TestOfModule;
import com.datastructures.model.dsinsa.DSInSAModule;
import com.datastructures.model.enums.SectionOfTrainingCatalog;
import com.datastructures.model.enums.dsinsa.DSInSAModuleTerm;
import com.datastructures.presenter.homepage.HomePagePresenterImpl;
import com.datastructures.presenter.services.TrainingService;
import com.datastructures.presenter.services.TrainingServiceImpl;
import com.datastructures.view.courses.ds_in_sa.CourseDSInSAView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.RequiresApi;

public class CourseDSInSAPresenterImpl implements CourseDSInSAPresenter {
    private final CourseDSInSAView courseDSInSAView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;
    private final TrainingService trainingService;

    public CourseDSInSAPresenterImpl(CourseDSInSAView courseDSInSAView) {
        this.courseDSInSAView = courseDSInSAView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        LOGGER = Logger.getLogger(HomePagePresenterImpl.class.getName());
        trainingService = new TrainingServiceImpl();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String getText(SectionOfTrainingCatalog courseTerm, DSInSAModuleTerm module, String jsonText) {
        if (jsonText == null) {
            return null;
        }

        DSInSAModule data = trainingService.readDataOfDSInSATrainingModule(courseTerm, module, jsonText);

        StringBuilder builder = new StringBuilder();

        data.getLectures().forEach(lectureOfModule -> builder.append(lectureOfModule.getText()));

        return builder.toString();
    }

    @Override
    public List<TestOfModule> getQuestions(SectionOfTrainingCatalog courseTerm, DSInSAModuleTerm module, String jsonText) {
        DSInSAModule data = trainingService.readDataOfDSInSATrainingModule(courseTerm, module, jsonText);

        return data.getTests();
    }

    @Override
    public boolean checkAnswer(String answer, TestOfModule question) {
        return question.getCorrectAnswer().equals(answer);
    }

    @Override
    public void addUserStatisticsOfCourse(CourseStatistics statistics, DSInSAModuleTerm moduleTerm) {
        users.child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).child("CourseStatistics")
                .child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")
                        + "_" + SectionOfTrainingCatalog.DATA_STRUCTURES_IN_SYSTEM_ANALYTICS.toString())
                .child("moduleProgress").child(moduleTerm.toString()).setValue(Collections.singletonMap(moduleTerm.toString(), statistics.getModuleProgress().get(moduleTerm.toString())));
        users.child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")).child("CourseStatistics")
                .child(firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")
                        + "_" + SectionOfTrainingCatalog.DATA_STRUCTURES_IN_SYSTEM_ANALYTICS.toString())
                .child("moduleStatistics").child(moduleTerm.toString()).setValue(Collections.singletonMap(moduleTerm.toString(), statistics.getModuleStatistics().get(moduleTerm.toString())));
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " прошёл обучение по "
                + moduleTerm.toString() + " в рамках курса " + firebaseAuth.getCurrentUser().getEmail().replaceAll("[^\\da-zA-Za-яёА-ЯЁ]", "")
                + "_" + SectionOfTrainingCatalog.DATA_STRUCTURES_IN_SYSTEM_ANALYTICS.toString());
        courseDSInSAView.showSnackBarForDialog("Модуль успешно пройден!", Snackbar.LENGTH_LONG);
    }

    @Override
    public String readTextFromJson(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean checkCountFlags(List<Boolean> flags) {
        int counter = 0;

        for (boolean flag : flags) {
            if (flag) {
                counter += 1;
            }
        }
        return counter >= 3;
    }
}
