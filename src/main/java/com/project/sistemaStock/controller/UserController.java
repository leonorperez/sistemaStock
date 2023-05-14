package com.project.sistemaStock.controller;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final IUserRepository iUserRepository;

    @GetMapping(value = "/users")
    public List<UserDTO> listUsers() {
        List<User> users = iUserRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setDni(user.getDni());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @PostMapping(value = "/user/new")
    public ResponseEntity<?> save(@RequestBody User user) {
        User newUser = new User(user.getName(), user.getSurname(), user.getDni(), user.getEmail(), user.getPhone(), passwordEncoder().encode(user.getPassword()));
        newUser = iUserRepository.save(newUser);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(newUser.getId());
        userDTO.setName(newUser.getName());
        userDTO.setSurname(newUser.getSurname());
        userDTO.setEmail(newUser.getEmail());
        userDTO.setDni(newUser.getDni());
        userDTO.setPhone(newUser.getPhone());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<User> optionalUser = iUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setEmail(user.getEmail());
            userDTO.setDni(user.getDni());
            userDTO.setPhone(user.getPhone());

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        Optional<User> optionalUser = iUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(false);
            iUserRepository.save(user);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable int id, @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = iUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setDni(userDTO.getDni());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            iUserRepository.save(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }



}
