package com.microservicearchitecture.enumerations;

/**
 * Статус заказа
 *
 * PENDING > PAID > RESERVED_IN_STOCK > IN_DELIVERY > RECEIVED
 * любой статус > CANCELLED
 */
public enum OrderStatus {
    /**
     * Создан, ожидает оплаты
     */
    PENDING,

    /**
     * Оплачен
     */
    PAID,

    /**
     * Зарезервирован на складе
     */
    RESERVED_IN_STOCK,

    /**
     * Доставлен клиенту
     */
    DELIVERED_TO_CLIENT,

    /**
     * Отменён
     */
    CANCELLED
}
