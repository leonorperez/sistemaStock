package com.project.sistemaStock.dto;


import com.project.sistemaStock.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private UUID id;
    private LocalDateTime date;
    private int quantity;
    private BigDecimal value;
    private BigDecimal total;
    List<ProductDTO> products;
}
