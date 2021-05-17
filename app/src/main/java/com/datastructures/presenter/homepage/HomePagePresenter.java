package com.datastructures.presenter.homepage;

/**
 * Презентер для взаимодействия между активностью главной страницы и обработкой данных
 */
public interface HomePagePresenter {
    /**
     * Выход из учётной записи
     */
    public void toExitTheApp();

    /**
     * Получение информации о приложении
     */
    public void toAboutApp();

    /**
     * Переход к пользовательским настройкам
     */
    public void toSettings();

    /**
     * Переход к обучению пользователя
     */
    public void toStudy();
}
