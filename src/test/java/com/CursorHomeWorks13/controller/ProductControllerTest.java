package com.CursorHomeWorks13.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.CursorHomeWorks13.entity.Product;
import com.CursorHomeWorks13.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
public
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public static Product testProduct() {
        Product product = new Product();
        product.setVersion(1L);
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");
        return product;
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setId(testProduct().getId());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Product product1 = new Product();
        product1.setVersion(testProduct().getVersion());
        product1.setId(testProduct().getId());
        product1.setName(testProduct().getName());
        product1.setPrice(testProduct().getPrice());
        product1.setDescription(testProduct().getDescription());
        String content = (new ObjectMapper()).writeValueAsString(product1);
        when(this.productRepository.save((Product) any())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"price\":42,"
                                        + "\"version\":1}"));
    }

    @Test
    void testDeleteAllProducts() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/deleteAll");
        doNothing().when(this.productRepository).deleteAll();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAllProducts2() throws Exception {
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/products/deleteAll");
        doNothing().when(this.productRepository).deleteAll();
        deleteResult.contentType("Not all who wander are lost");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteProductById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/delete/{id}", testProduct().getId());
        doNothing().when(this.productRepository).deleteById((Long) any());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteProductById2() throws Exception {
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/products/delete/{id}", testProduct().getId());
        doNothing().when(this.productRepository).deleteById((Long) any());
        deleteResult.contentType("Not all who wander are lost");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllProducts() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/get");
        when(this.productRepository.findAll()).thenReturn(new ArrayList<>());
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllProducts2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/get").param("name", "foo");
        when(this.productRepository.findByName((String) any())).thenReturn(new ArrayList<>());
        when(this.productRepository.findAll()).thenReturn(new ArrayList<>());
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setId(testProduct().getId());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Optional<Product> ofResult = Optional.of(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/findById/{id}", testProduct().getId());
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"price\":42,"
                                        + "\"version\":1}"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setId(testProduct().getId());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Optional<Product> ofResult = Optional.of(product);
        Product product1 = new Product();
        product1.setVersion(testProduct().getVersion());
        product1.setId(testProduct().getId());
        product1.setName(testProduct().getName());
        product1.setPrice(testProduct().getPrice());
        product1.setDescription(testProduct().getDescription());
        Product product2 = new Product();
        product2.setVersion(testProduct().getVersion());
        product2.setId(testProduct().getId());
        product2.setName(testProduct().getName());
        product2.setPrice(testProduct().getPrice());
        product2.setDescription(testProduct().getDescription());
        String content = (new ObjectMapper()).writeValueAsString(product2);
        when(this.productRepository.save((Product) any())).thenReturn(product1);
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/products/update/{id}", testProduct().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"price\":42,"
                                        + "\"version\":1}"));
    }

    @Test
    void testUpdateProduct2() throws Exception {
        Product product = new Product();
        product.setVersion(testProduct().getVersion());
        product.setId(testProduct().getId());
        product.setName(testProduct().getName());
        product.setPrice(testProduct().getPrice());
        product.setDescription(testProduct().getDescription());
        Product product1 = new Product();
        product1.setVersion(testProduct().getVersion());
        product1.setId(testProduct().getId());
        product1.setName(testProduct().getName());
        product1.setPrice(testProduct().getPrice());
        product1.setDescription(testProduct().getDescription());
        String content = (new ObjectMapper()).writeValueAsString(product1);
        when(this.productRepository.save((Product) any())).thenReturn(product);
        when(this.productRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/products/update/{id}", testProduct().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

