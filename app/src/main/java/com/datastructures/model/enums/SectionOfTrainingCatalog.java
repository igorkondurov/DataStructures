package com.datastructures.model.enums;

/**
 * Каталог учебных курсов
 */
public enum SectionOfTrainingCatalog {
    DATA_STRUCTURES_IN_SYSTEM_ANALYTICS(1, "Структуры данных в системной аналитике",
            "Курс знакомит системного аналитика с одной и очень важной частью технического стека разработчика - структурами данных.");

    /**
     * Порядковый номер
     */
    private final int order;

    /**
     * Название
     */
    private final String name;

    /**
     * Описание
     */
    private final String description;

    SectionOfTrainingCatalog(int order, String name, String description) {
        this.order = order;
        this.name = name;
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
