package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.PurchaseDTO;
import com.project.sistemaStock.model.Purchase;

import java.util.Map;
import java.util.UUID;

public interface IPurchaseService {
    public Map<String, Object> create(Purchase purchase);
    public Map<String,Object> getAll();
    public Map<String, Object> getById(UUID id);
    public Map<String, Object> update(UUID id, PurchaseDTO purchaseDTO);
    public Map<String, Object> delete(UUID id);
}
