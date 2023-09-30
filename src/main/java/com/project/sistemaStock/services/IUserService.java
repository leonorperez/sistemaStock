package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
@Service
public interface IUserService {
    Map<String, Object> create(User user);
    Map<String,Object> getAll();
    Map<String, Object> getById(UUID id);
    Map<String, Object> update(UUID id, UserDTO userDTO);
    Map<String, Object> delete(UUID id);

}
