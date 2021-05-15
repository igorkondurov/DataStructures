package com.datastructures.view.courses.ds_in_sa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.datastructures.R;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenter;
import com.datastructures.presenter.courses.ds_in_sa.CourseDSInSAPresenterImpl;
import com.datastructures.view.training.TrainingActivity;

import androidx.appcompat.app.AppCompatActivity;

public class CourseDSInSAActivity extends AppCompatActivity implements CourseDSInSAView {

    private RelativeLayout root;
    private CourseDSInSAPresenter courseDSInSAPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dsinsa_homepage);

        root = findViewById(R.id.root_element);

        courseDSInSAPresenter = new CourseDSInSAPresenterImpl(this);

        Button btnBackToHomePage = findViewById(R.id.btnBackToHomePage);

        btnBackToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferToHomePage();
            }
        });
    }

    private void transferToHomePage() {
        startActivity(new Intent(CourseDSInSAActivity.this, TrainingActivity.class));
        finish();
    }
}
