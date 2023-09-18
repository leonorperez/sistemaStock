package com.project.sistemaStock.services;

import com.project.sistemaStock.repository.IPurchaseRepository;
import org.springframework.stereotype.Service;


@Service
public class PurchaseService {
    private final IPurchaseRepository iPurchaseRepository;

    public PurchaseService(IPurchaseRepository iPurchaseRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
    }
}
