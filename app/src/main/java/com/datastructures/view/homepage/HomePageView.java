package com.datastructures.view.homepage;

/**
 * Интерфейс взаимодействия между активностью главной страницы и её презентером
 */
public interface HomePageView {
    /**
     * Переход на страницу авторизации
     */
    public void transferToMainPage();

    /**
     * Переход на страницу информации о приложении
     */
    public void transferToAboutAppPage();

    /**
     * Переход на страницу пользовательских настроек
     */
    public void transferToSettings();

    /**
     * Переход на страницу обучения пользователя
     */
    public void transferToStudy();
}
