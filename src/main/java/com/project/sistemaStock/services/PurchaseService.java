package com.project.sistemaStock.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.project.sistemaStock.dto.PurchaseDTO;
import com.project.sistemaStock.model.Product;
import com.project.sistemaStock.model.Purchase;
import com.project.sistemaStock.repository.IProductRepository;
import com.project.sistemaStock.repository.IPurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PurchaseService implements IPurchaseService {
    private final IPurchaseRepository iPurchaseRepository;
    private final IProductRepository iProductRepository;


    public PurchaseService(IPurchaseRepository iPurchaseRepository, IProductRepository iProductRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
        this.iProductRepository = iProductRepository;
    }
    @Override
    public Map<String, Object> create(Purchase purchase) {
        Map<String, Object> response = new HashMap<>();
        try {
            Purchase newPurchase = new Purchase(purchase.getDate(), purchase.getQuantity(),
                    purchase.getTotal(), purchase.getValue(), purchase.getProducts());

            if (newPurchase.getProducts() != null) {
                for (Product product : newPurchase.getProducts()) {
                    product.setPurchase(newPurchase);
                }
            }
            newPurchase = iPurchaseRepository.save(newPurchase);

            PurchaseDTO purchaseDTO = setPurchaseDto(newPurchase);
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", purchaseDTO);
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }
    @Override
    @Transactional
    public Map<String, Object> findOrCreatePurchase(Purchase purchase) {
        Map<String, Object> response = new HashMap<>();
        try {
            Purchase newPurchase = new Purchase(purchase.getDate(), purchase.getQuantity(),
                    purchase.getTotal(), purchase.getValue(), purchase.getProducts());

            if (newPurchase.getProducts() != null) {
                for (Product product : newPurchase.getProducts()) {
                   Optional<Product> existingProduct = iProductRepository.findByCodeOrName(product.getName());
                    if(existingProduct.isPresent()){

                        Product existing = existingProduct.get();
                        existing.setStock(product.getStock());
                        existing.setPrice(product.getPrice());

                        product = existing;



                        System.out.println("exist: "+ existing);

                    }else{
                        product.setPurchase(newPurchase);
                    }
                }
            }
            newPurchase = iPurchaseRepository.save(newPurchase);

            PurchaseDTO purchaseDTO = setPurchaseDto(newPurchase);
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", purchaseDTO);
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }



    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Purchase> purchases = iPurchaseRepository.findAllByStatus(true);

            List<PurchaseDTO> listPurchaseDTO = new ArrayList<>();
            for (Purchase purchase : purchases) {
                PurchaseDTO purchaseDTO = setPurchaseDto(purchase);
                listPurchaseDTO.add(purchaseDTO);
            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", listPurchaseDTO);
            response.put("count", listPurchaseDTO.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> getById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Purchase> optionalPurchase = iPurchaseRepository.findById(id);

            if (optionalPurchase.isPresent()) {
                Purchase purchase = optionalPurchase.get();
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", purchase);
            } else {
                response.put("errors", Collections.singletonMap("message", "Compra inexistente"));
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> update(UUID id, PurchaseDTO purchaseDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Purchase> optionalPurchase = iPurchaseRepository.findById(id);
            if (optionalPurchase.isPresent()) {
                Purchase purchase = optionalPurchase.get();
                setPurchase(purchaseDTO, purchase);
                iPurchaseRepository.save(purchase);
                PurchaseDTO responsePurchaseDTO = setPurchaseDto(purchase);
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", responsePurchaseDTO);
                response.put("result: ", "Purchase updated successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", purchaseDTO);
                response.put("result: ", "Purchase not found");
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
            Optional<Purchase> optionalPurchase = iPurchaseRepository.findById(id);
            if (optionalPurchase.isPresent()) {
                Purchase purchase = optionalPurchase.get();
                purchase.setStatus(false);
                iPurchaseRepository.save(purchase);
                PurchaseDTO purchaseDTO = setPurchaseDto(purchase);

                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", purchaseDTO);
                response.put("result: ", "Purchase delete successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", optionalPurchase);
                response.put("result: ", "Purchase not found");
            }
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    public static PurchaseDTO setPurchaseDto(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setDate(purchase.getDate());
        purchaseDTO.setQuantity(purchase.getQuantity());
        purchaseDTO.setValue(purchase.getValue());
        purchaseDTO.setTotal(purchase.getTotal());
        purchaseDTO.setProducts(ProductService.productsToProductsDto(purchase.getProducts()));
        return purchaseDTO;
    }

    private void setPurchase(PurchaseDTO purchaseDTO, Purchase purchase) {
        if (purchaseDTO.getQuantity() > 0) {
            purchase.setQuantity(purchaseDTO.getQuantity());
        }
        if (purchaseDTO.getDate() != null) {
            purchase.setDate(purchaseDTO.getDate());
        }
        if (purchaseDTO.getValue() != null) {
            purchase.setValue(purchaseDTO.getValue());
        }
        if (purchaseDTO.getTotal() != null) {
            purchase.setTotal(purchaseDTO.getTotal());
        }
    }

}
