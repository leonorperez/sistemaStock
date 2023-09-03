package com.project.sistemaStock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private UUID id;
    private LocalDateTime date;
    private int quantity;
    private BigDecimal value;
    private BigDecimal total;
}

