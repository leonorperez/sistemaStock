package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.PurchaseDTO;
import com.project.sistemaStock.dto.SaleDTO;
import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.model.Sale;
import com.project.sistemaStock.repository.IPurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.*;


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
            Purchase newPurchase = new Purchase(purchase.getDate(), purchase.getQuantity(), purchase.getTotal(), purchase.getValue());
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
        Map<String, Object> response = new HashMap<>();
        try {
            List<Purchase> purchases = iPurchaseRepository.findAllByStatus(true);

            List<PurchaseDTO> listPurchaseDTO = new ArrayList<>();
            for (Purchase purchase : purchases) {
                PurchaseDTO purchaseDTO = setPurchaseDto(purchase);
                listPurchaseDTO.add(purchaseDTO);
            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", listPurchaseDTO);
            response.put("count", listPurchaseDTO.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Purchase> optionalPurchase = iPurchaseRepository.findById(id);

            if (optionalPurchase.isPresent()) {
                Purchase purchase = optionalPurchase.get();
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", purchase);

            } else {
                response.put("errors", Collections.singletonMap("message", "Venta inexistente"));
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));

        }
        return response;
    }

    @Override
    public Map<String, Object> update(UUID id, PurchaseDTO purchaseDTO) {
        return null;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        return null;
    }

    private PurchaseDTO setPurchaseDto(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setDate(purchase.getDate());
        purchaseDTO.setQuantity(purchase.getQuantity());
        purchaseDTO.setValue(purchase.getValue());
        purchaseDTO.setTotal(purchase.getTotal());
        return purchaseDTO;
    }

    private void setPurchase(PurchaseDTO purchaseDTO, Purchase purchase) {
        if (purchaseDTO.getQuantity() > 0) {
            purchase.setQuantity(purchaseDTO.getQuantity());
        }
        if (purchaseDTO.getDate() != null) {
            purchase.setDate(purchaseDTO.getDate());
        }
        if (purchaseDTO.getValue() != null) {
            purchase.setValue(purchaseDTO.getValue());
        }
        if (purchaseDTO.getTotal() != null) {
            purchase.setTotal(purchaseDTO.getTotal());
        }
    }


}
