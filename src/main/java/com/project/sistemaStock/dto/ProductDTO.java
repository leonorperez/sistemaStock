package com.project.sistemaStock.dto;

import com.project.sistemaStock.model.Purchase;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String code;
    private String name;
    private int quantity;
    private BigDecimal price;
 }
