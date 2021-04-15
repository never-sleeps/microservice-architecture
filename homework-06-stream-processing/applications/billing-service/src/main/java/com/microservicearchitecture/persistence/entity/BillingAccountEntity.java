package com.microservicearchitecture.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "billing_account")
@Entity
@Setter
@Getter
public class BillingAccountEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "billingAccountIdSeq", sequenceName = "billing_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "billingAccountIdSeq")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;
}
