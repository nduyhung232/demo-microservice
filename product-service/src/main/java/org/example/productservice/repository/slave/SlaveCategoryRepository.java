package org.example.productservice.repository.slave;

import org.example.productservice.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlaveCategoryRepository extends JpaRepository<Category, Long> {
}
