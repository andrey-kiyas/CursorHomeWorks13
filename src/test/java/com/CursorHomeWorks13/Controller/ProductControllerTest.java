package com.CursorHomeWorks13.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.CursorHomeWorks13.Entity.Product;
import com.CursorHomeWorks13.Rrepository.ProductRepository;
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
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setVersion(1L);
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");
        when(this.productRepository.save((Product) any())).thenReturn(product);

        Product product1 = new Product();
        product1.setVersion(1L);
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(product1);
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
        doNothing().when(this.productRepository).deleteAll();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/deleteAll");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteAllProducts2() throws Exception {
        doNothing().when(this.productRepository).deleteAll();
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/products/deleteAll");
        deleteResult.contentType("Not all who wander are lost");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteProductById() throws Exception {
        doNothing().when(this.productRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/delete/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteProductById2() throws Exception {
        doNothing().when(this.productRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/products/delete/{id}", 123L);
        deleteResult.contentType("Not all who wander are lost");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(this.productRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/get");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllProducts2() throws Exception {
        when(this.productRepository.findByName((String) any())).thenReturn(new ArrayList<>());
        when(this.productRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/get").param("name", "foo");
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
        product.setVersion(1L);
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");
        Optional<Product> ofResult = Optional.of(product);
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/findById/{id}", 123L);
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
        product.setVersion(1L);
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");
        Optional<Product> ofResult = Optional.of(product);

        Product product1 = new Product();
        product1.setVersion(1L);
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setDescription("The characteristics of someone or something");
        when(this.productRepository.save((Product) any())).thenReturn(product1);
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);

        Product product2 = new Product();
        product2.setVersion(1L);
        product2.setId(123L);
        product2.setName("Name");
        product2.setPrice(BigDecimal.valueOf(42L));
        product2.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(product2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/products/update/{id}", 123L)
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
        product.setVersion(1L);
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setDescription("The characteristics of someone or something");
        when(this.productRepository.save((Product) any())).thenReturn(product);
        when(this.productRepository.findById((Long) any())).thenReturn(Optional.empty());

        Product product1 = new Product();
        product1.setVersion(1L);
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(product1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/products/update/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

