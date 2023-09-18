package com.project.sistemaStock.services;

import com.project.sistemaStock.repository.IPurchaseRepository;
import com.project.sistemaStock.repository.ISaleRepository;
import org.springframework.stereotype.Service;


@Service
public class PurchaseService {
    private final IPurchaseRepository iPurchaseRepository;

    public SaleService(IPurchaseRepository iPurchaseRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
    }
}
