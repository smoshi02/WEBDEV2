package com.decimo.debweb.controller.api;

import com.decimo.debweb.model.Product;
import com.decimo.debweb.model.ProductDTO;
import com.decimo.debweb.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @PostMapping
    public Product create(@Valid @RequestBody ProductDTO dto) {
        return productService.save(dto);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO dto) {
        Product existing = productService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found.");
        }
        return productService.updateProduct(existing, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        if (productService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found.");
        }
        productService.deleteProduct(id);
    }
}
