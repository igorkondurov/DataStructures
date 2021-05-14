package com.datastructures.presenter.settings;

import com.datastructures.model.User;
import com.datastructures.view.CallbackHelper;

import androidx.annotation.NonNull;

public interface SettingsPresenter {
    public void showEditEmailWindow();

    public void showEditPasswordWindow();

    public void showEditPhoneWindow();

    public void showEditNamesWindow();

    public void showEditSexOfAPersonWindow();

    public void showEditDateOfBirthdayWindow();

    public void showEditCategoryWindow();

    public void showEditInterestsWindow();

    public void getUserInfo(@NonNull CallbackHelper<User> finishedCallback);
}
