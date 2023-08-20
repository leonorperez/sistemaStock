package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {


    Optional<Product> findById(UUID id);


    List<Product> findAllByStatus(boolean b);
}
