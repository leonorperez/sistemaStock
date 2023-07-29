package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

@Service
public class UserService implements IUserService {
    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public Map<String, Object> create(User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User newUser = new User(user.getName(), user.getSurname(), user.getDni(), user.getEmail(), user.getPhone(), passwordEncoder().encode(user.getPassword()));
            newUser = iUserRepository.save(newUser);
            UserDTO userDTO = setUserDto(newUser);

            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", userDTO);

        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> optionalUser = iUserRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                UserDTO userDTO = setUserDto(user);

                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", userDTO);

            } else {
                response.put("errors", Collections.singletonMap("message", "Usuario Inexistente"));
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));

        }
        return response;
    }


    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = iUserRepository.findAllByStatus(true);

            List<UserDTO> userDTOS = new ArrayList<>();
            for (User user : users) {
                UserDTO userDTO = setUserDto(user);
                userDTOS.add(userDTO);
            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", userDTOS);
            response.put("count", userDTOS.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }


    @Override
    public Map<String, Object> update(UUID id, UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> optionalUser = iUserRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                setUser(userDTO, user);
                iUserRepository.save(user);
                UserDTO UresponseUserDTO = setUserDto(user);
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", UresponseUserDTO);
                response.put("result: ", "User updated successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", userDTO);
                response.put("result: ", "User not found");
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> optionalUser = iUserRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setStatus(false);
                iUserRepository.save(user);
                UserDTO userDTO = setUserDto(user);


                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", userDTO);
                response.put("result: ", "User delete successfully");

            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", optionalUser);
                response.put("result: ", "User not found");
            }

        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;

    }

    private UserDTO setUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setDni(user.getDni());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    private void setUser(UserDTO userDTO, User user) {
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getSurname() != null) {
            user.setSurname(userDTO.getSurname());
        }
        if (userDTO.getDni() != null) {
            user.setDni(userDTO.getDni());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
    }


}
