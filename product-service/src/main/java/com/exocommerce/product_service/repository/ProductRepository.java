package com.exocommerce.product_service.repository;

import com.exocommerce.product_service.entity.Category;
import com.exocommerce.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
