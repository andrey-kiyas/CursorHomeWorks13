package com.CursorHomeWorks13.Rrepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.CursorHomeWorks13.Entity.Product;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ProductRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.CursorHomeWorks13.Entity"})
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindByName() {
        Product product = new Product();
        product.setVersion(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");

        Product product1 = new Product();
        product1.setVersion(1L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setDescription("The characteristics of someone or something");
        this.productRepository.save(product);
        this.productRepository.save(product1);
        assertTrue(this.productRepository.findByName("foo").isEmpty());
    }

    @Test
    void testFindByName2() {
        Product product = new Product();
        product.setVersion(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");

        Product product1 = new Product();
        product1.setVersion(1L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setDescription("The characteristics of someone or something");
        this.productRepository.save(product);
        this.productRepository.save(product1);
        assertTrue(this.productRepository.findByName("foo").isEmpty());
    }
}

