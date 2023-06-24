package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

public class UserService {
    @Autowired
    private final IUserRepository iUserRepository;


  @Override
   public UserDTO create(User user){
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
            return userDTO;
           // return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            UserDTO userDTO = new UserDTO();
            return userDTO;
//            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
