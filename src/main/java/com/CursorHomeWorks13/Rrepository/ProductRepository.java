package com.CursorHomeWorks13.Rrepository;

import com.CursorHomeWorks13.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}
