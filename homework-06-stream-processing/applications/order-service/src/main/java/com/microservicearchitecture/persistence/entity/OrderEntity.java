package com.microservicearchitecture.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name =  "\"order\"", schema = "public")
public class OrderEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "orderIdSeq", sequenceName = "order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orderIdSeq")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "user_id")
    private Long userId;
}
