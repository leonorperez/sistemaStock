package com.project.sistemaStock.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    public Product() {
        this.status = true;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "code", nullable = false, length = 200)
    private String code;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "quantity")
    @ColumnDefault("0")
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_purchase")
    private Purchase purchase;

    @Column(name = "status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;


}
