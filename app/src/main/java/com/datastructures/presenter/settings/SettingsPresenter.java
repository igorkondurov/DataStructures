package com.datastructures.presenter.settings;

import com.datastructures.model.User;
import com.datastructures.view.CallbackHelper;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Презентер для работы с пользовательскими настройками
 */
public interface SettingsPresenter {
    /**
     * Получение информации о пользователе из Firebase
     *
     * @param finishedCallback интерфейс обратного вызова
     */
    public void getUserInfo(@NonNull CallbackHelper<User> finishedCallback);

    /**
     * Проверка полей пользовательских данных
     *
     * @param fields пользовательские данные
     * @return true - корректный ввод, false - некорректный
     */
    public boolean checkFields(String... fields);

    /**
     * Редактирование адреса электронной почты
     *
     * @param oldEmail старый адрес
     * @param newEmail новый адрес
     * @param password пароль
     */
    public void editEmail(String oldEmail, String newEmail, String password);

    /**
     * Редактирование пароля
     *
     * @param email       адрес электронной почты
     * @param oldPassword старый пароль
     * @param newPassword новый пароль
     * @param data        объект пользователя
     */
    public void editPassword(String email, String oldPassword, String newPassword, User data);

    /**
     * Редактирование номера телефона
     *
     * @param password пароль
     * @param oldPhone старый номер телефона
     * @param newPhone новый номер телефона
     * @param data     объект пользователя
     */
    public void editPhone(String password, String oldPhone, String newPhone, User data);

    /**
     * Редактирование персональных данных (фамилия, имя)
     *
     * @param name    имя
     * @param surname фамилия
     * @param data    объект пользователя
     */
    public void editNameAndSurname(String name, String surname, User data);

    /**
     * Редактирование персональных данных (пол)
     *
     * @param sexOfAPerson пол
     * @param data         объект пользователя
     */
    public void editSexOfAPerson(String sexOfAPerson, User data);

    /**
     * Редактирование даты рождения
     *
     * @param dateOfBirthday дата рождения
     * @param data           объект пользователя
     */
    public void editDateOfBirthday(String dateOfBirthday, User data);

    /**
     * Редактирование направления профессиональной деятельности пользователя
     *
     * @param fieldOfActivity направление
     * @param data            объект пользователя
     */
    public void editCategory(String fieldOfActivity, User data);

    /**
     * Редактирование пользовательских интересов
     *
     * @param interests пользовательские интересы
     * @param data      объект пользователя
     */
    public void editInterests(List<String> interests, User data);

    public void updatePasswordWindow();

    public void updatePhoneWindow();

    public void updateNamesWindow();

    public void updateSexOfAPersonWindow();

    public void updateDateOfBirthdayWindow();

    public void updateCategoryWindow();

    public void updateInterestsWindow();

    public void updateEMail();
}
