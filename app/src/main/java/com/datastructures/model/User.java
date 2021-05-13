package com.datastructures.model;

import java.util.List;

import lombok.Data;

/**
 * Пользователь
 */
@Data
public class User {
    /**
     * id
     */
    private String id;
    
    /**
     * Телефон
     */
    private String phone;

    /**
     * Имя
     */
    private String name;

    /**
     * Фамилия
     */
    private String surname;

    /**
     * Дата рождения
     */
    private String dateOfBirth;

    /**
     * Пол
     */
    private String sexOfAPerson;

    /**
     * Сфера деятельности/ Категория
     */
    private String fieldOfActivity;

    /**
     * Интересы
     */
    private List<String> interests;
}
