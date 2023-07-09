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
        try {
            UserDTO userDTO = iUserService.create(user);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id); // Intenta convertir la cadena a UUID

            Map<String,Object> response = iUserService.getById(uuid);

            if (response.containsKey("data")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid UUID", HttpStatus.BAD_REQUEST);
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
