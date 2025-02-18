package org.example.productservice.repository.master;

import org.example.productservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterProductRepository extends JpaRepository<Product, Long> {
}
