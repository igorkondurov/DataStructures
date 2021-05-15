package com.datastructures.presenter.homepage;

import com.datastructures.view.homepage.HomePageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePagePresenterImpl implements HomePagePresenter {
    private final HomePageView homePageView;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;
    private final DatabaseReference users;
    private final Logger LOGGER;

    public HomePagePresenterImpl(HomePageView homePageView) {
        this.homePageView = homePageView;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        LOGGER = Logger.getLogger(HomePagePresenterImpl.class.getName());
    }

    @Override
    public void toExitTheApp() {
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " вышел из приложения!");
        firebaseAuth.signOut();
        homePageView.transferToMainPage();
    }

    @Override
    public void toAboutApp() {
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " перешёл в раздел \"О приложении\"!");
        homePageView.transferToAboutAppPage();
    }

    @Override
    public void toSettings() {
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " перешёл в раздел \"Настройки\"!");
        homePageView.transferToSettings();
    }

    @Override
    public void toStudy() {
        LOGGER.log(Level.INFO, "Пользователь " + firebaseAuth.getCurrentUser().getEmail() + " перешёл в раздел \"Обучение\"!");
        homePageView.transferToStudy();
    }
}
