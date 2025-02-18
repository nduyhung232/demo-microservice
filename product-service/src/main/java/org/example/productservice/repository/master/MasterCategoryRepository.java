package org.example.productservice.repository.master;

import org.example.productservice.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterCategoryRepository extends JpaRepository<Category, Long> {
}
