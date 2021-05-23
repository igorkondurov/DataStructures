package com.datastructures.presenter.main;

/**
 * Презентер для осуществления взаимодействия между активностью
 * при аутентификации/ авторизации/ регистрации
 * и моделью хранения пользовательских данных
 */
public interface MainPresenter {
    /**
     * Проверка полученных с формы данных, введенных пользователем
     *
     * @param email    почта
     * @param password пароль
     * @param name     имя
     * @param phone    номер телефона
     * @return true - все данные корректные, false - ошибка в данных
     */
    public boolean checkFields(String email, String password, String name, String phone);

    /**
     * Создание документов пользователя в коллекции Firebase
     *
     * @param email    почта
     * @param password пароль
     * @param name     имя
     * @param surname  фамилия
     * @param phone    номер телефона
     */
    public void createUser(String email, String password, String name, String surname, String phone);

    /**
     * Аутентификация и авторизация пользователя
     *
     * @param email    почта
     * @param password пароль
     */
    public void loginUser(String email, String password);
}
