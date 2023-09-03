package com.project.sistemaStock.controller;

import com.project.sistemaStock.model.Sale;
import com.project.sistemaStock.repository.ISaleRepository;
import com.project.sistemaStock.services.ISaleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SaleController {

    @Autowired
    private final ISaleRepository iSaleRepository;

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
}
