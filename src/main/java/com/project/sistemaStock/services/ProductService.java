package com.project.sistemaStock.services;

import com.project.sistemaStock.dto.ProductDTO;
import com.project.sistemaStock.model.Product;
import com.project.sistemaStock.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Map<String, Object> getById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Product> optionalProduct = iProductRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                ProductDTO productDTO = setProductDto(product);

                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", product);

            } else {
                response.put("errors", Collections.singletonMap("message", "Producto Inexistente"));
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
            List<Product> products = iProductRepository.findAllByStatus(true);

            List<ProductDTO> listProductDTO = new ArrayList<>();
            for (Product product : products) {
                ProductDTO productDTO = setProductDto(product);
                listProductDTO.add(productDTO);
            }
            response.put("errors", Collections.singletonMap("message", null));
            response.put("data", listProductDTO);
            response.put("count", listProductDTO.size());
        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;
    }

    @Override
    public Map<String, Object> update(UUID id, ProductDTO productDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Product> optionalProduct = iProductRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                setProduct(productDTO, product);
                iProductRepository.save(product);
                ProductDTO responseProductDTO = setProductDto(product);
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", responseProductDTO);
                response.put("result: ", "User updated successfully");
            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", productDTO);
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
            Optional<Product> optionalProduct = iProductRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setStatus(false);
                iProductRepository.save(product);
                ProductDTO productDTO = setProductDto(product);


                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", productDTO);
                response.put("result: ", "Product delete successfully");

            } else {
                response.put("errors", Collections.singletonMap("message", null));
                response.put("data", optionalProduct);
                response.put("result: ", "User not found");
            }

        } catch (Exception e) {
            response.put("errors", Collections.singletonMap("message", e.getMessage()));
            response.put("count", 0);
        }
        return response;

    }

    private void setProduct(ProductDTO productDTO, Product product) {
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getCode() != null) {
            product.setCode(productDTO.getCode());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getQuantity() != 0) {
            product.setQuantity(productDTO.getQuantity());
        }
    }

    public static List<ProductDTO> productsToProductsDto(List<Product> products){
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(product -> {
            productsDTO.add(setProductDto(product));
        });
        return productsDTO;
    }

    public static ProductDTO setProductDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCode(product.getCode());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        return productDTO;
    }

}
