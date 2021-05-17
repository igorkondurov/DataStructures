package com.datastructures.view;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

/**
 * Общий интерфейс для обращений презентера к активности
 */
public interface CommonView {
    /**
     * Создание диалогового окна
     *
     * @param title   заголовок
     * @param message сообщение
     * @return объект диалогового окна
     */
    public AlertDialog.Builder getAlertDialog(String title, String message);

    /**
     * Получить представление диалогового окна
     *
     * @param resource файл ресурсов
     * @return представление
     */
    public View getViewForDialog(int resource);

    /**
     * Вывод сообщения для пользователя
     *
     * @param message текст сообщения
     * @param length  длительность покаща сообщения
     */
    public void showSnackBarForDialog(String message, int length);

    /**
     * Перезагрузка текущей активити
     */
    public void restartCurrentActivity();
}
