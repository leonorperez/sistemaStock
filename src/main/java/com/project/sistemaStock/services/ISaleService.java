package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.SaleDTO;
import com.project.sistemaStock.model.Sale;

import java.util.Map;
import java.util.UUID;

public interface ISaleService {
    Map<String, Object> create(Sale sale);
    Map<String,Object> getAll();
    Map<String, Object> getById(UUID id);
    Map<String, Object> update(UUID id, SaleDTO saleDTO);
    Map<String, Object> delete(UUID id);
}
