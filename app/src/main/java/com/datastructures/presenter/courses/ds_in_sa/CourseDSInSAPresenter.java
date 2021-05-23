package com.datastructures.presenter.courses.ds_in_sa;

import android.content.Context;

import com.datastructures.model.CourseStatistics;
import com.datastructures.model.TestOfModule;
import com.datastructures.model.enums.SectionOfTrainingCatalog;
import com.datastructures.model.enums.dsinsa.DSInSAModuleTerm;

import java.io.IOException;
import java.util.List;

/**
 * Презентер для обработки взаимодействия пользователя с учебным курсом
 * "Структуры данных в системной аналитике"
 */
public interface CourseDSInSAPresenter {
    /**
     * Получить строковый атрибут текста лекции из json файла
     *
     * @param courseTerm Курс
     * @param module     Модуль
     * @param jsonText   Данные по обучению в формате json
     * @return text
     */
    public String getText(SectionOfTrainingCatalog courseTerm, DSInSAModuleTerm module, String jsonText);

    /**
     * Получить список вопросов для тестирования по модулю
     *
     * @param courseTerm Курс
     * @param module     Модуль
     * @param jsonText   Данные по обучению в формате json
     * @return questions
     */
    public List<TestOfModule> getQuestions(SectionOfTrainingCatalog courseTerm, DSInSAModuleTerm module, String jsonText);

    /**
     * Проверка правильности выбранного пользователем варианта ответа
     *
     * @param answer   выбранный вариант пользователем
     * @param question тестовый вопрос
     * @return флаг, true - пользователь ответил верно, false - неверно
     */
    public boolean checkAnswer(String answer, TestOfModule question);

    /**
     * Добавить данные по прохождению пользователем модуля в базу
     *
     * @param statistics Статистика прохождения
     * @param moduleTerm Учебный модуль
     */
    public void addUserStatisticsOfCourse(CourseStatistics statistics, DSInSAModuleTerm moduleTerm);

    /**
     * Прочитать данные из json файла
     *
     * @param context Контекст
     * @param resId   ID
     * @return text Содержимое json файла
     * @throws IOException исключения
     */
    public String readTextFromJson(Context context, int resId) throws IOException;

    /**
     * Проверить количество верных ответов в тесте по учебному модулю
     *
     * @param flags Список флагов правильных ответов
     * @return Более 50% верных ответов - true, иначе - false
     */
    public boolean checkCountFlags(List<Boolean> flags);
}
