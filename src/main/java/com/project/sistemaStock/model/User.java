package com.project.sistemaStock.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import java.util.UUID;


@Data
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    public User() {

    }
    public User(String name, String surname, String dni, String email, String phone, String password) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.status = true;
    }


    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name="name", nullable = false, length = 200)
    private String name;

    @Column(name="surname", nullable = false, length = 200)
    private String surname;

    @Column(name="dni", nullable = false, length = 10)
    private String dni;

    @Column(name="email", nullable = false, length = 100)
    private String email;

    @Column(name="phone", nullable = false, length =20)
    private String phone;

    @Column(name="password", nullable = false, length = 200)
    private String password;

    @Column(name="status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;

}
