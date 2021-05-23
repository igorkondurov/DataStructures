package com.datastructures.view.settings;

import com.datastructures.model.User;
import com.datastructures.view.CommonView;

/**
 * Интерфейс для взаимодействия активности и презентера при работе с пользовательскими настройками
 */
public interface SettingsView extends CommonView {
    /**
     * Вызов окна обновления пароля пользователя
     *
     * @param data объект сущности Пользователь
     */
    public void updatePasswordWindow(User data);

    /**
     * Вызов окна обновления мобильного телефона пользователя
     *
     * @param data объект сущности Пользователь
     */
    public void updatePhoneWindow(User data);

    /**
     * Вызов окна обновления персональных даннных пользователя (фамилия, имя)
     *
     * @param data объект сущности Пользователь
     */
    public void updateNamesWindow(User data);

    /**
     * Вызов окна обновления персональных данных пользователя (пол)
     *
     * @param data объект сущности Пользователь
     */
    public void updateSexOfAPersonWindow(User data);

    /**
     * Вызов окна обновления персональных данных пользователя (дата рождения)
     *
     * @param data объект сущности Пользователь
     */
    public void updateDateOfBirthdayWindow(User data);

    /**
     * Вызов окна обновления пользовательской категории (направления проф. деятельности)
     *
     * @param data объект сущности Пользователь
     */
    public void updateCategoryWindow(User data);

    /**
     * Вызов окна обновления пользователських интересов
     *
     * @param data объект сущности Пользователь
     */
    public void updateInterestsWindow(User data);

    /**
     * @param data объект сущности Пользователь
     * @Deprecated неактуально
     * Вызов окна обновления электронной почты пользователя
     */
    public void updateEmail(User data);
}
