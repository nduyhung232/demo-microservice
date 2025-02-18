package org.example.productservice.repository.slave;

import org.example.productservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlaveProductRepository extends JpaRepository<Product, Long> {
}
