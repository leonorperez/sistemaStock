package com.project.sistemaStock.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    public Sale (){

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "date", nullable = false )
    private LocalDateTime date;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "status", nullable = false)
    @ColumnDefault("true")
    private Boolean status;

}
