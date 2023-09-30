package com.project.sistemaStock.controller;

import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.services.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

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

    @GetMapping(value = "/purchases")
    public ResponseEntity<?> listSales() {
        try {
            Map<String, Object> response = iPurchaseService.getAll();

            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            Map<String, Object> response = iPurchaseService.getById(uuid);

            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid UUID", HttpStatus.BAD_REQUEST);
        }
    }






}

