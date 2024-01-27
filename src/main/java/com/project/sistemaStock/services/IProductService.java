package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.ProductDTO;
import com.project.sistemaStock.model.Product;
import com.project.sistemaStock.model.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface IProductService {
    Map<String, Object> create(Product product);

    Map<String, Object> getByCodeOrName(String codeOrName);

    Map<String,Object> getAll();
    Map<String, Object> getById(UUID id);
    Map<String, Object> update(UUID id, ProductDTO productDTO);
    Map<String, Object> delete(UUID id);

}
