package com.datastructures.model.dsinsa;

import com.datastructures.model.TrainingCourse;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель курса "Структуры данных в системной аналитике"
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DSInSACourse extends TrainingCourse implements DSInSAModel {
    /**
     * Модули курса
     */
    private Map<String, DSInSAModule> modules = new HashMap<>();
}
