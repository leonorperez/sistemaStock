package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.PurchaseDTO;
import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.repository.IPurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class PurchaseService implements IPurchaseService {
    private final IPurchaseRepository iPurchaseRepository;

    public PurchaseService(IPurchaseRepository iPurchaseRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
    }

    @Override
    public Map<String, Object> create(Purchase purchase) {
        Map<String, Object> response = new HashMap<>();
        try {
            Purchase newPurchase = new Purchase(purchase.getDate(),purchase.getQuantity(), purchase.getTotal(),purchase.getValue());
            newPurchase = iPurchaseRepository.save(newPurchase);
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", newPurchase);
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> getAll() {
        return null;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        return null;
    }

    @Override
    public Map<String, Object> update(UUID id, PurchaseDTO purchaseDTO) {
        return null;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        return null;
    }
}
