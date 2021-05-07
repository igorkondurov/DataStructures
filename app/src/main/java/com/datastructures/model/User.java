package com.datastructures.model;

import lombok.Data;

/**
 * Пользователь
 */
@Data
public class User {
    /**
     * Почта
     */
    private String email;

    /**
     * Пароль
     */
    private String password;

    /**
     * Имя
     */
    private String name;

    /**
     * Телефон
     */
    private String phone;
}
