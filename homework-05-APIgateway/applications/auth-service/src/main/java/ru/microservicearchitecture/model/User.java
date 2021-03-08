package ru.microservicearchitecture.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;
}