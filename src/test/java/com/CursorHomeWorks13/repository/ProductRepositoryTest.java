package com.CursorHomeWorks13.repository;

import static com.CursorHomeWorks13.controller.ProductControllerTest.testProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.CursorHomeWorks13.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {ProductRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.CursorHomeWorks13.Entity"})
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void saveProductTest() {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        entityManager.persistAndFlush(product);
        assertEquals(productRepository.findById(product.getId()), Optional.of(product));
    }

    @Test
    void findProductTest() {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        productRepository.save(product);
        List<Product> shopsResult = new ArrayList<>(productRepository.findAll());
        assertEquals(shopsResult.size(), 1);
    }

    @Test
    void testFindByName() {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Product product1 = new Product();
        product1.setVersion(testProduct().getVersion());
        product1.setName(testProduct().getName());
        product1.setPrice(testProduct().getPrice());
        product1.setDescription(testProduct().getDescription());
        this.productRepository.save(product);
        this.productRepository.save(product1);
        assertTrue(this.productRepository.findByName("foo").isEmpty());
    }

    @Test
    void testFindByName2() {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Product product1 = new Product();
        product1.setVersion(testProduct().getVersion());
        product1.setName(testProduct().getName());
        product1.setPrice(testProduct().getPrice());
        product1.setDescription(testProduct().getDescription());
        this.productRepository.save(product);
        this.productRepository.save(product1);
        assertTrue(this.productRepository.findByName("foo").isEmpty());
    }
}

