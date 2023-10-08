package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPurchaseRepository  extends JpaRepository<Purchase, Integer> {
    Optional<Purchase> findById(UUID id);

    List<Purchase> findAllByStatus(boolean status);


    //esto aun n o funca
    @Query("SELECT p, c FROM Product p LEFT JOIN p.purchase c WHERE p.status = true")
    List<Purchase> findAllWithProductsAndStatus();

}
