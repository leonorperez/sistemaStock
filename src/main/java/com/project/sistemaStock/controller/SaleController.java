package com.project.sistemaStock.controller;

import com.project.sistemaStock.dto.SaleDTO;
import com.project.sistemaStock.model.Sale;
import com.project.sistemaStock.services.ISaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SaleController {

    private final ISaleService iSaleService;

    @PostMapping(value = "/sale/new")
    public ResponseEntity<?> save(@RequestBody Sale sale) {
        try {
            Map<String, Object> response = iSaleService.create(sale);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/sales")
    public ResponseEntity<?> listSales() {
        try {
            Map<String, Object> response = iSaleService.getAll();

            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id); // Intenta convertir la cadena a UUID

            Map<String, Object> response = iSaleService.getById(uuid);

            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid UUID", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/sale/{id}")
    public ResponseEntity<?> updateSaleById(@PathVariable UUID id, @RequestBody SaleDTO saleDTO) {
        try {
            Map<String, Object> response = iSaleService.update(id, saleDTO);
            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/sale/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        try {
            Map<String, Object> response = iSaleService.delete(id);
            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
