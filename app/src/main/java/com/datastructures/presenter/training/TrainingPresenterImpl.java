package com.datastructures.presenter.training;

import com.datastructures.presenter.homepage.HomePagePresenterImpl;
import com.datastructures.view.training.TrainingView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainingPresenterImpl implements TrainingPresenter {
    private final TrainingView trainingView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;

    public TrainingPresenterImpl(TrainingView trainingView) {
        this.trainingView = trainingView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        LOGGER = Logger.getLogger(HomePagePresenterImpl.class.getName());
    }

    @Override
    public void toCourseDSInSA() {
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " перешёл в раздел курса \"Структуры данных в системной аналитике\"!");
        trainingView.transferToCourseDSInSA();
    }
}
