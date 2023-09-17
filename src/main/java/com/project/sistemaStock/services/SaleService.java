package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.SaleDTO;
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
        System.out.println(sale);
        try {
            Sale newSale = new Sale(sale.getDate(),sale.getQuantity(), sale.getTotal(),sale.getValue());
            newSale = iSaleRepository.save(newSale);
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

            List<SaleDTO> listSaleDTO = new ArrayList<>();
            for (Sale sale : sales) {
                SaleDTO saleDto = setSaleDto(sale);
                listSaleDTO.add(saleDto);
            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", listSaleDTO);
            response.put("count", listSaleDTO.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> update(UUID id, SaleDTO saleDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Sale> optionalSale = iSaleRepository.findById(id);
            if (optionalSale.isPresent()) {
                Sale sale = optionalSale.get();
                setSale(saleDTO, sale);
                iSaleRepository.save(sale);
                SaleDTO responseSaleDTO = setSaleDto(sale);
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", responseSaleDTO);
                response.put("result: ", "Sale updated successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", saleDTO);
                response.put("result: ", "Sale not found");
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
            Optional<Sale> optionalSale = iSaleRepository.findById(id);
            if (optionalSale.isPresent()) {
                Sale sale = optionalSale.get();
                sale.setStatus(false);
                iSaleRepository.save(sale);
                SaleDTO saleDTO = setSaleDto(sale);

                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", saleDTO);
                response.put("result: ", "Sale delete successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", optionalSale);
                response.put("result: ", "Sale not found");
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }


    private SaleDTO setSaleDto(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setDate(sale.getDate());
        saleDTO.setQuantity(sale.getQuantity());
        saleDTO.setValue(sale.getValue());
        saleDTO.setTotal(sale.getTotal());
        return saleDTO;
    }

    private void setSale(SaleDTO saleDTO, Sale sale) {
        if (saleDTO.getQuantity() > 0) {
            sale.setQuantity(saleDTO.getQuantity());
        }
        if (saleDTO.getDate() != null) {
            sale.setDate(saleDTO.getDate());
        }
        if (saleDTO.getValue() != null) {
            sale.setValue(saleDTO.getValue());
        }
        if (saleDTO.getTotal() != null) {
            sale.setTotal(saleDTO.getTotal());
        }
    }

}