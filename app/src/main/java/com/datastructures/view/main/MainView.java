package com.datastructures.view.main;

import com.datastructures.view.CommonView;

/**
 * Интерфейс взаимодействия активности и презентера при аутентификации/ авторизации/ регистрации
 */
public interface MainView extends CommonView {
    /**
     * Переход на главную страницу приложения
     */
    public void transferToHomePage();
}
