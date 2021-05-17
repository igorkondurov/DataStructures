package com.datastructures.model;

import java.util.List;

import lombok.Data;

/**
 * Пользователь
 */
@Data
public class User {
    /**
     * id документа
     */
    private String id;

    /**
     * Адрес электронной почты
     */
    private String email;

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
     * Сфера профессиональной деятельности/ категория
     */
    private String fieldOfActivity;

    /**
     * Пользовательские нтересы
     */
    private List<String> interests;
}
