package com.datastructures.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Универсальная модель теста учебного модуля
 */
@Data
public class TestOfModule {
    /**
     * Текст вопроса
     */
    private String textOfQuestion;

    /**
     * Варианты ответа
     */
    private List<String> answerOptions = new ArrayList<>();

    /**
     * Правильный вариант
     */
    private String correctAnswer;
}
