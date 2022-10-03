package com.CursorHomeWorks13.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void testConstructor() {
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        Product actualProduct = new Product("Name", "The characteristics of someone or something", valueOfResult);
        BigDecimal price = actualProduct.getPrice();
        assertNull(actualProduct.getVersion());
        assertEquals("The characteristics of someone or something", actualProduct.getDescription());
        assertEquals("Product(id=0, name=Name, description=The characteristics of someone or something, price=42,"
                + " version=null)", actualProduct.toString());
        assertSame(valueOfResult, price);
        assertEquals("Name", actualProduct.getName());
        assertEquals(0L, actualProduct.getId());
        assertEquals(1, price.signum());
        assertEquals(0, price.scale());
        assertEquals("42", price.toString());
        assertSame(price, valueOfResult);
    }
}
