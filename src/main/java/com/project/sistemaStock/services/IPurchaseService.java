package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.PurchaseDTO;
import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.model.Sale;

import java.util.Map;
import java.util.UUID;

public interface IPurchaseService {
    Map<String, Object> create(Purchase purchase);
    Map<String,Object> getAll();
    Map<String, Object> getById(UUID id);
    Map<String, Object> update(UUID id, PurchaseDTO purchaseDTO);
    Map<String, Object> delete(UUID id);
}
