package com.datastructures.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Статистика прохождения  пользователем учебного курса
 */
@Data
public class CourseStatistics {
    /**
     * ID
     */
    private String id;

    /**
     * Статистика прохождения модулей
     */
    private Map<String, ModuleStatistics> moduleStatistics = new HashMap<>();

    /**
     * Прогресс обучения в рамках курса
     */
    private Map<String, Boolean> moduleProgress = new HashMap<>();
}
