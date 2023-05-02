package com.project.sistemaStock.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name", nullable = false, length = 200)
    private String name;

    @Column(name="surname", nullable = false, length = 200)
    private String surname;

    @Column(name="dni", nullable = false, length = 10)
    private String dni;

    @Column(name="phone", nullable = false, length =20)
    private String phone;

    @Column(name="password", nullable = false, length = 50)
    private String password;

    @Column(name="state", nullable = false, length = 50)
    private Boolean state;
}
