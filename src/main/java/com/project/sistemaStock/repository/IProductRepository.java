package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.Product;
import com.project.sistemaStock.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {


    Optional<Product> findById(UUID id);


    List<Product> findAllByStatus(boolean b);


    //no se usa. trae los productos completos "con la compra" dnd fue comprado. no pareciera necesario x ahora
    @Query("SELECT p, c FROM Product p LEFT JOIN p.purchase c WHERE p.status = true")
    List<Product> findAllWithPurchasesByStatus();

}


