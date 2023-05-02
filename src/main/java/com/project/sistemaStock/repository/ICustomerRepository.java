package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer,Integer> {
}
