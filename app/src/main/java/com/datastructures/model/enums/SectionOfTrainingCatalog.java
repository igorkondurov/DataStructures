package com.datastructures.model.enums;

/**
 * Каталог учебных курсов
 */
public enum SectionOfTrainingCatalog {
    DATA_STRUCTURES_IN_SYSTEM_ANALYTICS(1, "Структуры данных в системной аналитике",
            "Курс знакомит системного аналитика с одной и очень важной частью технического стека разработчика - структурами данных.");

    private final int order;
    private final String title;
    private final String description;

    SectionOfTrainingCatalog(int order, String title, String description) {
        this.order = order;
        this.title = title;
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
