package com.microservicearchitecture.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "notification")
@Entity
@Getter
@Setter
public class NotificationEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "notificationIdSeq", sequenceName = "notification_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "notificationIdSeq")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "status")
    private String status;

    @Column(name = "email")
    private String email;
}
