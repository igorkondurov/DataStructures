package com.datastructures.view.settings;

import com.datastructures.model.User;
import com.datastructures.view.CommonView;

/**
 * Интерфейс для взаимодействия активности и презентера при работе с пользовательскими настройками
 */
public interface SettingsView extends CommonView {
    public void updatePasswordWindow(User data);

    public void updatePhoneWindow(User data);

    public void updateNamesWindow(User data);

    public void updateSexOfAPersonWindow(User data);

    public void updateDateOfBirthdayWindow(User data);

    public void updateCategoryWindow(User data);

    public void updateInterestsWindow(User data);

    public void updateEMail(User data);
}
