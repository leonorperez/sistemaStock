package com.project.sistemaStock.repository;

import com.project.sistemaStock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findAllByStatus(boolean status);
}
