package com.CursorHomeWorks13.controller;

import com.CursorHomeWorks13.entity.Product;
import com.CursorHomeWorks13.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/get") // HTTP cache for GET /products endpoint
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
        try {
            List<Product> products = new ArrayList<>();
            if (name == null) {
                products.addAll(productRepository.findAll());
            } else {
                products.addAll(productRepository.findByName(name));
            }
            String version = String.valueOf(products.hashCode());
            return ResponseEntity.ok()
                    .eTag(version)
                    .body(products);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product tempProduct = productRepository
                    .save(new Product(product.getName(), product.getDescription(), product.getPrice()));
            return new ResponseEntity<>(tempProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}") // HTTP cache for GET /products/{id} endpoint
    public Object getProductById(@PathVariable("id") long id) {
        try {
            Optional<Product> findProductById = productRepository.findById(id);
            String version = String.valueOf(findProductById.hashCode());
            return ResponseEntity.ok()
                    .eTag(version)
                    .body(findProductById);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product updProduct = productData.get();
            updProduct.setName(product.getName());
            updProduct.setDescription(product.getDescription());
            updProduct.setPrice(product.getPrice());
            return new ResponseEntity<>(productRepository.save(updProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
