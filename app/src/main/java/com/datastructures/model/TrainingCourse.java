package com.datastructures.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

/**
 * Учебный курс
 */
@Data
public class TrainingCourse {
    /**
     * Разделы/ модули
     */
    @NonNull
    private List<TrainingModule> modules;

    /**
     * Флаги доступности модулей
     */
    private List<Boolean> signsOfPassingModules = new ArrayList<>();

    /**
     * Статистика прохождения
     */
    private CourseStatistics courseStatistics;
}
