package com.microservicearchitecture.enumerations;

public enum NotificationType {
    /**
     * Ошибка оплаты
     */
    PAYMENT_ERROR,

    /**
     * Ошибка резервирвоания товара на складе
     */
    RESERVATION_ERROR,

    /**
     * Ошибка при доставке
     */
    DELIVERY_ERROR,

    /**
     * Успешно доставлен
     */
    SUCCESS
}
