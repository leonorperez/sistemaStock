package com.project.sistemaStock.services;

import com.project.sistemaStock.model.Product;
import com.project.sistemaStock.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductRepository iProductRepository;

    public ProductService(IProductRepository iUserRepository) {

        this.iProductRepository = iUserRepository;
    }

    @Override
    public Map<String, Object> create(Product product) {
        Map<String, Object> response = new HashMap<>();
        try {

            iProductRepository.save(product);

            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", product);

        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> getAll() {
        return null;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        return null;
    }

    @Override
    public Map<String, Object> update(UUID id, Product product) {
        return null;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        return null;
    }

    /*@Override
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
    public Map<String, Object> update(UUID id, Product product) {
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

     */




}
