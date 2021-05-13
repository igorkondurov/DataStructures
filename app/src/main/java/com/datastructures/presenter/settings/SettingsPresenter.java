package com.datastructures.presenter.settings;

import java.util.Map;

public interface SettingsPresenter {
    public Map<String, String> getValues();

    public void showEditEmailWindow();

    public void showEditPasswordWindow();

    public void showEditPhoneWindow();

    public void checkData();
}
