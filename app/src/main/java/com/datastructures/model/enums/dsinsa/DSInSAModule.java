package com.datastructures.model.enums.dsinsa;

/**
 * Модуль курса "Структуры данных в системной аналитике"
 */
public enum DSInSAModule {
    MODULE_1(1, "ВВЕДЕНИЕ", ""),

    MODULE_2(2, "СПИСОК", ""),

    MODULE_3(3, "СТЕК", ""),

    MODULE_4(4, "ОЧЕРЕДЬ", ""),

    MODULE_5(5, "ХЭШ-ТАБЛИЦА", ""),

    MODULE_6(6, "МНОЖЕСТВА", ""),

    MODULE_7(7, "ДЕРЕВЬЯ", ""),

    MODULE_8(8, "ГРАФ", "");

    private final int order;
    private final String name;
    private final String description;

    DSInSAModule(int order, String name, String description) {
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
