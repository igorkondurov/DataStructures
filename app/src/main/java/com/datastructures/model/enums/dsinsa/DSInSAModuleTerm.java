package com.datastructures.model.enums.dsinsa;

import com.datastructures.model.enums.ModuleTerm;

/**
 * Модуль курса "Структуры данных в системной аналитике"
 */
public enum DSInSAModuleTerm implements ModuleTerm {
    MODULE_1(1, "ВВЕДЕНИЕ", ""),

    MODULE_2(2, "СПИСОК", ""),

    MODULE_3(3, "СТЕК", ""),

    MODULE_4(4, "ОЧЕРЕДЬ", ""),

    MODULE_5(5, "ХЭШ-ТАБЛИЦА", ""),

    MODULE_6(6, "МНОЖЕСТВА", ""),

    MODULE_7(7, "ДЕРЕВЬЯ", ""),

    MODULE_8(8, "ГРАФ", "");

    /**
     * Порядковый номер
     */
    private final int order;

    /**
     * Название модуля
     */
    private final String name;

    /**
     * Описание модуля
     */
    private final String description;

    DSInSAModuleTerm(int order, String name, String description) {
        this.order = order;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
