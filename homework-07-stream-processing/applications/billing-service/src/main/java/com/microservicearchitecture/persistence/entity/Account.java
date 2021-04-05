package com.microservicearchitecture.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;
}
