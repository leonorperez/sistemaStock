package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;
@Service
public class UserService implements IUserService{
    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


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

    @Override
    public UserDTO getAll() {
        return null;
    }

    @Override
    public UserDTO getById(UUID id) {
        try {
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

                return userDTO;
            } else {
                UserDTO userDTO = new UserDTO();
                return userDTO;
            }
        } catch (Exception e) {
            // Manejar cualquier excepción o error que pueda ocurrir
            return null;
        }
    }


    @Override
    public UserDTO update(UUID id, UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO delete(UUID id) {
        return null;
    }


}
