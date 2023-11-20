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

    //TODO ver si sirve esta query
    @Query("SELECT DISTINCT pur FROM Purchase pur JOIN FETCH pur.products pro WHERE pur.status = true")
    List<Purchase> findAllPurchasesWithProducts();

}
