package com.project.sistemaStock.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="customers")
public class Customer {
    public Customer() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="company", nullable = false, length = 100)
    private String company;


    @Column(name="name", nullable = false, length = 200)
    private String name;

    @Column(name="surname", nullable = false, length = 200)
    private String surname;

    @Column(name="address", nullable = false, length = 100)
    private String address;

    @Column(name="phone", nullable = false, length =20)
    private String phone;

    @Column(name="state", nullable = false, length = 50)
    private Boolean state;

}
