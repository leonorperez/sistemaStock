package com.project.sistemaStock.controller;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import com.project.sistemaStock.services.IUserService;
import com.project.sistemaStock.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final IUserRepository iUserRepository;
    @Autowired
    private final IUserService iUserService;

    @GetMapping(value = "/users")
    public Map<String, Object> listUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
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
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", userDTOS);
            response.put("count", userDTOS.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("data", new ArrayList<>());
            response.put("count", 0);
        }
        return response;
    }

    @PostMapping(value = "/user/new")
    public ResponseEntity<?> save(@RequestBody User user) {
        return new ResponseEntity<>(iUserService.create(user),HttpStatus.OK);
        try {
            User newUser = new User(user.getName(), user.getSurname(), user.getDni(), user.getEmail(), user.getPhone(), passwordEncoder().encode(user.getPassword()));
            newUser.setId(UUID.fromString(UUID.randomUUID().toString()));
            newUser = iUserRepository.save(newUser);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(newUser.getId());
            userDTO.setName(newUser.getName());
            userDTO.setSurname(newUser.getSurname());
            userDTO.setEmail(newUser.getEmail());
            userDTO.setDni(newUser.getDni());
            userDTO.setPhone(newUser.getPhone());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        UUID userId;
        try {
            userId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = iUserRepository.findById(userId);
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
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        try {
            Optional<User> optionalUser = iUserRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setStatus(false);
                iUserRepository.save(user);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = iUserRepository.findById(id);
        try {
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
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
