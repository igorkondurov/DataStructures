package com.datastructures.presenter.courses.ds_in_sa;

import com.datastructures.presenter.homepage.HomePagePresenterImpl;
import com.datastructures.view.courses.ds_in_sa.CourseDSInSAView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

public class CourseDSInSAPresenterImpl implements CourseDSInSAPresenter {
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
}
