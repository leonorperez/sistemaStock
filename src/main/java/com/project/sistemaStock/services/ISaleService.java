package com.project.sistemaStock.services;

import com.project.sistemaStock.model.Sale;

import java.util.Map;
import java.util.UUID;

public interface ISaleService {
    public Map<String, Object> create(Sale sale);
    public Map<String,Object> getAll();
    public Map<String, Object> getById(UUID id);
    public Map<String, Object> update(UUID id, Sale sale);
    public Map<String, Object> delete(UUID id);
}
