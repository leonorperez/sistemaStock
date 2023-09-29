package com.project.sistemaStock.controller;

import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.services.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PurchaseController {

    private final IPurchaseService iPurchaseService;


    @PostMapping(value = "/purchase/new")
    public ResponseEntity<?> save(@RequestBody Purchase purchase) {
        try {
            Map<String, Object> response = iPurchaseService.create(purchase);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

