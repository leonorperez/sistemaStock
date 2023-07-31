package com.project.sistemaStock.services;

import com.project.sistemaStock.model.Product;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface IProductService {
    public  Map<String, Object> create(Product product);
    public Map<String,Object> getAll();
    public Map<String, Object> getById(UUID id);
    public Map<String, Object> update(UUID id, Product product);
    public Map<String, Object> delete(UUID id);

}
