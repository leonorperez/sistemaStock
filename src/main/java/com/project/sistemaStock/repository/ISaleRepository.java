package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISaleRepository extends JpaRepository<Sale, Integer> {
    Optional<Sale> findById(UUID id);

    List<Sale> findAllByStatus(boolean status);
}

