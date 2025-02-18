package org.example.productservice.service;

import org.example.productservice.model.dto.ProductCreateDTO;
import org.example.productservice.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> search(String nameParam, String categoryNameParam);

    ProductDTO findById(Long productId);

    ProductDTO create(ProductCreateDTO productCreateDTO);

    void delete(Long id);
}
