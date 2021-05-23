package com.datastructures.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Статистика прохождения модуля
 */
@Data
public class ModuleStatistics {
    /**
     * ID модуля
     */
    private String id;

    /**
     * Тесты
     */
    private List<TestOfModule> questions = new ArrayList<>();

    /**
     * Ответы пользователя
     */
    private List<String> userResponses = new ArrayList<>();

    /**
     * Признаки правильности пользовательских ответов
     */
    private List<Boolean> flags = new ArrayList<>();
}
