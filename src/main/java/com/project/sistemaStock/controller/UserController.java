package com.project.sistemaStock.controller;

import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final IUserRepository iUserRepository;

    @GetMapping(value="/users")
    public List<User> listUsers(){
        System.out.println("´paso x aca");
        return iUserRepository.findAll();

    }

    @PostMapping(value = "/user/new")
    public ResponseEntity<?> save(@RequestBody User user){
        User newUser = new User(user.getName(), user.getSurname(), user.getDni(), user.getEmail(), user.getPhone(), passwordEncoder().encode(user.getPassword()));
        return new ResponseEntity<>(iUserRepository.save(newUser), HttpStatus.OK);
    }


}
