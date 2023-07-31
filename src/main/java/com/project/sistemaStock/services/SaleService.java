package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.Sale;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.ISaleRepository;
import com.project.sistemaStock.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.sistemaStock.security.WebSecurityConfig.passwordEncoder;

@Service
public class SaleService implements ISaleService {

    private final ISaleRepository iSaleRepository;

    public SaleService(ISaleRepository iSaleRepository) {
        this.iSaleRepository = iSaleRepository;
    }

    @Override
    public Map<String, Object> create(Sale sale) {
        Map<String, Object> response = new HashMap<>();
        try {
            Sale newSale = iSaleRepository.save(sale);
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", newSale);
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Sale> optionalSale = iSaleRepository.findById(id);

            if (optionalSale.isPresent()) {
                Sale sale = optionalSale.get();
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", sale);

            } else {
                response.put("errors", Collections.singletonMap("message", "Venta inexistente"));
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
            List<Sale> sales = iSaleRepository.findAllByStatus(true);

//            List<UserDTO> userDTOS = new ArrayList<>();
//            for (Sale sale : sales) {
//                UserDTO userDTO = setUserDto(user);
//                userDTOS.add(userDTO);
//            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", sales);
            response.put("count", sales.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> update(UUID id, Sale sale) {
        return null;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        return null;
    }
}
