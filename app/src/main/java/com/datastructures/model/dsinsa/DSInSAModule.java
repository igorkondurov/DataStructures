package com.datastructures.model.dsinsa;

import com.datastructures.model.LectureOfModule;
import com.datastructures.model.TestOfModule;
import com.datastructures.model.TrainingModule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель модуля курса "Структуры данных в системной аналитике"
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DSInSAModule extends TrainingModule {
    /**
     * Лекции модуля
     */
    private List<LectureOfModule> lectures;

    /**
     * Тесты модуля
     */
    private List<TestOfModule> tests;
}
