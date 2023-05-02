package com.project.sistemaStock.controller;

import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final IUserRepository iUserRepository;

    @GetMapping(value="/users")
    public List<User> listUsers(){
        return iUserRepository.findAll();

    }
}
