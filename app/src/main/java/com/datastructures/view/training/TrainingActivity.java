package com.datastructures.view.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.training.TrainingPresenter;
import com.datastructures.presenter.training.TrainingPresenterImpl;
import com.datastructures.view.courses.ds_in_sa.CourseDSInSAActivity;
import com.datastructures.view.homepage.HomePageActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Активность для работы с учебными курсами приложения
 */
public class TrainingActivity extends AppCompatActivity implements TrainingView {

    private RelativeLayout root;
    private TrainingPresenter trainingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        root = findViewById(R.id.root_element);

        trainingPresenter = new TrainingPresenterImpl(this);
        Button btnToDSCourse = findViewById(R.id.btnToDataStructuresCourse);
        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);

        btnToDSCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainingPresenter.toCourseDSInSA();
            }
        });

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });
    }

    @Override
    public void transferToCourseDSInSA() {
        startActivity(new Intent(TrainingActivity.this, CourseDSInSAActivity.class));
        finish();
    }

    private void transferToHomePage() {
        startActivity(new Intent(TrainingActivity.this, HomePageActivity.class));
        finish();
    }
}
