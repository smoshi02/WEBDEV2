package com.decimo.debweb.service;

import com.decimo.debweb.model.Product;
import com.decimo.debweb.model.ProductDTO;
import com.decimo.debweb.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setStock(dto.stock());
        p.setUnit(dto.unit());
        p.setPrice(dto.price());
        return productRepository.save(p);
    }

    public Product updateProduct(Product existing, ProductDTO dto) {
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setStock(dto.stock());
        existing.setUnit(dto.unit());
        existing.setPrice(dto.price());
        return productRepository.save(existing);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
