package com.project.sistemaStock.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customer {

    public Customer(Integer id, String company, String name, String surname, String address, String phone) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.status = true;
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

    @Column(name="status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;

}
