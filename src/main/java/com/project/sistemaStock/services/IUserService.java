package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
@Service
public interface IUserService {
    public  Map<String, Object> create(User user);
    public Map<String,Object> getAll();
    public Map<String, Object> getById(UUID id);
    public UserDTO update(UUID id, UserDTO userDTO);
    public Map<String, Object> delete(UUID id);

}
