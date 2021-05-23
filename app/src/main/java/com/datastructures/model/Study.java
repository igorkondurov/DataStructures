package com.datastructures.model;

import com.datastructures.model.dsinsa.DSInSACourse;

import lombok.Data;

/**
 * Модель раздела "Обучение"
 */
@Data
public class Study {
    /**
     * Курс "Структуры данных в системной аналитике"
     */
    private DSInSACourse dsInSACourse;
}
